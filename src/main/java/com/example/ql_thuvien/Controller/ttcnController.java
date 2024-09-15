package com.example.ql_thuvien.Controller;

import com.example.ql_thuvien.Entity.PhieuMuon;
import com.example.ql_thuvien.Entity.TaiKhoan;
import com.example.ql_thuvien.Repositories.PhieuMuonRepository;
import com.example.ql_thuvien.Repositories.TaiKhoanReposiroty;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.Collator;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Controller
public class ttcnController {
    @Autowired
    private TaiKhoanReposiroty taikhoanRe;
    @Autowired
    private PhieuMuonRepository phieumuonRe;
    Iterable<TaiKhoan> listTaiKhoan;
    Iterable<PhieuMuon> listPhieuMuon;
    TaiKhoan tkHienTai;
    ArrayList<PhieuMuon> dataPm = new ArrayList();
    private static ArrayList<PhieuMuon> ArraylistPm = new ArrayList();
    private static final String PHONE_REGEX = "^[0-9]{10}$";
    private static final Pattern PHONE_PATTERN = Pattern.compile(PHONE_REGEX);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public ttcnController() {
    }

    @RequestMapping({"/QLttcn"})
    public String toThongTinCaNhan() {
        return "ttcnView";
    }

    @GetMapping({"/QLttcn"})
    public String getAll(Model m,HttpSession session) {
        listPhieuMuon=this.phieumuonRe.findAll();
        ArraylistPm=(ArrayList<PhieuMuon>)listPhieuMuon;
        Integer id = (Integer) session.getAttribute("ma_taikhoan");
        Optional<TaiKhoan> optionalTk = taikhoanRe.findById((long)id);

        tkHienTai=new TaiKhoan();
        if (optionalTk.isPresent()) {
            tkHienTai=optionalTk.get();
        }

        dataPm=new ArrayList();
            for (PhieuMuon pm : ArraylistPm)
            {
                if(pm.getMa_taikhoan()==id)dataPm.add(pm);
            }


        m.addAttribute("data", tkHienTai);
        m.addAttribute("dataPm",dataPm);
        return "ttcnView";
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        Matcher matcher = PHONE_PATTERN.matcher(phoneNumber);
        return matcher.matches();
    }

    //Sửa thông tin tài khoản
    @PostMapping("/saveUser")
    public String updatePM(@ModelAttribute("data") TaiKhoan tkNew, HttpSession session,
    Model m) {
        Optional<TaiKhoan> optionalTk = taikhoanRe.findById((long)tkNew.getMa_taikhoan());
        TaiKhoan tk= new TaiKhoan();
        if (optionalTk.isPresent()) {
             tk= optionalTk.get();
             tk.setSdt(tkNew.getSdt());
             tk.setDiachi(tkNew.getDiachi());
             tk.setHoten(tkNew.getHoten());
             tk.setEmail(tkNew.getEmail());
            if (tkNew.getAvatar() != null && !tkNew.getAvatar().isEmpty()) {
                // Update the avatar only if a new file is selected
                tk.setAvatar(tkNew.getAvatar());
            } else {
                // Retain the existing avatar if no new avatar is selected
                tkNew.setAvatar(tk.getAvatar());
            }
            if(!isValidPhoneNumber(tkNew.getSdt()))
            {
                m.addAttribute("message","Không đúng định dạng số điện thoại!");
                m.addAttribute("type", "error");
                return "ttcnView";
            }
            String temp_password = (String) session.getAttribute("temp_password");
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(temp_password);
            tk.setPassword(hashedPassword);
            taikhoanRe.save(tk);
            m.addAttribute("message","Sửa thông tin thành công");
            m.addAttribute("type", "success");

        }
       return "ttcnView";
        }


    @PostMapping("/ChangePassword")
    public String ChangePassword( HttpSession session,
                           Model m,@RequestParam("oldpassword") String oldpassword,
                                 @RequestParam("newpassword") String newpassword,
                                 @RequestParam("confirmpassword") String confirmpassword)
    {
        Integer id = (Integer) session.getAttribute("ma_taikhoan");
        Optional<TaiKhoan> optionalTk = taikhoanRe.findById((long)id);
        TaiKhoan tk= new TaiKhoan();
        String temp_password = (String) session.getAttribute("temp_password");
        System.out.println(temp_password);
        if (optionalTk.isPresent())
        {
            tk= optionalTk.get();
            if(oldpassword.equals(temp_password))
            {
                if(newpassword.equals(confirmpassword))
                {
                    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                    String hashedPassword = passwordEncoder.encode(newpassword);
                    tk.setPassword(hashedPassword);
                    taikhoanRe.save(tk);
                    m.addAttribute("message","Sửa thông tin thành công");
                    m.addAttribute("type", "success");
                }
                else
                {
                    m.addAttribute("message","Mật khẩu và mật khẩu xác nhận không trùng khớp");
                    m.addAttribute("type", "error");
                }

            }
            else
            {
                m.addAttribute("message","Sai mật khẩu");
                m.addAttribute("type", "error");
            }
        }
        m.addAttribute("data", tk);
        dataPm=new ArrayList();
        for (PhieuMuon pm : ArraylistPm)
        {
            if(pm.getMa_taikhoan()==id)dataPm.add(pm);
        }
        m.addAttribute("dataPm",dataPm);
        return "ttcnView";
    }

        //Sắp xếp
    @GetMapping({"/QLttcn/sort"})
    public String sortBooks(@RequestParam(defaultValue = "ngay_muon") String sortField,
                            @RequestParam(defaultValue = "asc") String sortDir, Model m,
                            HttpServletRequest request) {

        listPhieuMuon=sortList(dataPm,sortField,sortDir);
        m.addAttribute("data", tkHienTai);
        m.addAttribute("dataPm", listPhieuMuon);
        m.addAttribute("sortField",sortField);
        m.addAttribute("sortDir",sortDir);
        return "ttcnView";

    }

    private ArrayList<PhieuMuon> sortList(ArrayList<PhieuMuon> ArraylistPm, String sortField, String sortDir) {
        Comparator<PhieuMuon> comparator = null;
        Collator collator = Collator.getInstance(new Locale("vi", "VN"));
        collator.setStrength(Collator.TERTIARY);
        switch (sortField) {
            case "ngay_muon":
                comparator = Comparator.comparing(PhieuMuon::getNgay_muon, Comparator.nullsFirst(collator));
                break;
            case "ngay_tra":
                comparator = Comparator.comparing(PhieuMuon::getNgay_tra, Comparator.nullsFirst(collator));
                break;
            case "ngay_han":
                comparator = Comparator.comparing(PhieuMuon::getNgay_han, Comparator.nullsFirst(collator));
                break;
            default:
                throw new IllegalArgumentException("Invalid sort field: " + sortField);
        }
        if (sortDir.equals("desc")) {
            comparator = comparator.reversed();
        }

        // Sort the list
        return ArraylistPm.stream()
                .sorted(comparator)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    //Gia hạn phiếu mượn
    @GetMapping(value = {"QLttcn/giahan/{id}"})
    public String GiaHanPhieuMuon(@PathVariable("id") int id,
                                 HttpSession session,
                                 Model m) {
        Optional<PhieuMuon> optionalPm = phieumuonRe.findById((long)id);
        if(optionalPm.isPresent())
        {
            PhieuMuon pm= optionalPm.get();
            pm.setTrang_thai(8);
            LocalDate ngay_muon=LocalDate.parse(pm.getNgay_han());
            LocalDate ngay_moi=ngay_muon.plusDays(14);
            pm.setNgay_han(ngay_moi.format(formatter));
            phieumuonRe.save(pm);
            listPhieuMuon=this.phieumuonRe.findAll();
            ArraylistPm=(ArrayList<PhieuMuon>)listPhieuMuon;
            dataPm=new ArrayList();
            Integer ma_tk = (Integer) session.getAttribute("ma_taikhoan");
            for (PhieuMuon pm2 : ArraylistPm)
            {
                if(pm2.getMa_taikhoan()==ma_tk)dataPm.add(pm2);
            }
            m.addAttribute("data", tkHienTai);
            m.addAttribute("dataPm",dataPm);
            m.addAttribute("message","Gia hạn thành công!");
            m.addAttribute("type", "success");
        }

        return "ttcnView";
    }

}
