package com.example.ql_thuvien.Controller;

import com.example.ql_thuvien.Entity.*;
import com.example.ql_thuvien.Repositories.NhaCungCapRepository;
import com.example.ql_thuvien.Repositories.QuyenReposiroty;
import com.example.ql_thuvien.Repositories.TaiKhoanReposiroty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class TaiKhoanController {

    @Autowired
    private TaiKhoanReposiroty taikhoanRe;
    Iterable<TaiKhoan> listTaiKhoan;
    @Autowired
    private QuyenReposiroty quyenRe;
    Iterable<Quyen> listQuyen;
    private static final String PHONE_REGEX = "^[0-9]{10}$";
    private static final Pattern PHONE_PATTERN = Pattern.compile(PHONE_REGEX);
    LocalDate currentDate = LocalDate.now();

    // Format the date as a string (e.g., "dd/MM/yyyy")
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static ArrayList<TaiKhoan> ArrayListTaikhoan = new ArrayList();
    public TaiKhoanController() {
    }

    @RequestMapping({"/QLTaiKhoan"})
    public String toTaiKhoan() {
        return "TaiKhoanView";
    }

    @GetMapping({"/QLTaiKhoan"})
    public String getAll(Model m) {
        listTaiKhoan=this.taikhoanRe.findAll();
        listQuyen=this.quyenRe.findAll();
        ArrayListTaikhoan = (ArrayList<TaiKhoan>)listTaiKhoan;
        m.addAttribute("data",listTaiKhoan);
        return "TaiKhoanView";
    }

    @RequestMapping(value = "addAccount")
    public String showAddForm(Model model) {
        TaiKhoan tk = new TaiKhoan();
        model.addAttribute("taikhoan", tk);
        model.addAttribute("listQuyen",listQuyen);
        return "crud/addAccount";
    }

    @PostMapping("/saveAccount")
    public String save(@ModelAttribute("taikhoan") TaiKhoan tk, Model model,
                       @RequestParam("nhaplaimatkhau") String nhaplaimatkhau) {
        String matkhau=tk.getPassword();
        if(matkhau.length()<6)
        {
            model.addAttribute("message","Mật khẩu phải có ít nhất 6 ký tự!!");
            model.addAttribute("type", "error");
            return "crud/addAccount";
        }
        if(!matkhau.equals(nhaplaimatkhau))
        {
            model.addAttribute("message","Mật khẩu không trùng khớp với mật khẩu nhập lại!!");
            model.addAttribute("type", "error");
            return "crud/addAccount";
        }
        if(tk.getHoten().length()<=3)
        {
            model.addAttribute("message","Họ và tên phải có ít nhất 4 ký tự!!");
            model.addAttribute("type", "error");
            return "crud/addAccount";
        }
        if(!isValidPhoneNumber(tk.getSdt()))
        {
            model.addAttribute("message","Không đúng định dạng số điện thoại!");
            model.addAttribute("type", "error");
            return "crud/addAccount";
        }
        for(TaiKhoan tk2: taikhoanRe.findAll())
        {
            if(tk2.getEmail().equals(tk.getEmail()))
            {
                model.addAttribute("message","Email đã được sử dụng!!");
                model.addAttribute("type", "error");
                return "crud/addAccount";
            }
        }

        Quyen selectedQuyen=new Quyen();
        Optional<Quyen> optionalQuyen = quyenRe.findById((long)tk.getQuyen().getMa_quyen());
        if(optionalQuyen.isPresent())
        {
            selectedQuyen=optionalQuyen.get();
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(matkhau);
        String ngay_tao=currentDate.format(formatter);
        TaiKhoan tkNew = new TaiKhoan(selectedQuyen,tk.getEmail(),hashedPassword,
                tk.getHoten(),tk.getSdt(),tk.getDiachi(),ngay_tao,1,tk.getAvatar());
        taikhoanRe.save(tkNew);
        return "redirect:/QLTaiKhoan";
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        Matcher matcher = PHONE_PATTERN.matcher(phoneNumber);
        return matcher.matches();
    }

    @GetMapping(value = {"QLTaiKhoan/edit/{id}"})
    public String showEditForm(@PathVariable("id") int id,
                                   Model model) {
        TaiKhoan tk=ArrayListTaikhoan.get(id);
        System.out.println(tk.getQuyen().getMa_quyen());
        model.addAttribute("listQuyen",listQuyen);
        model.addAttribute("taikhoan", tk);
        return "crud/editAccount";
    }

    @PostMapping("/updateAccount")
    public String updateAccount(@ModelAttribute("taikhoan") TaiKhoan tk,
                                     Model model) {
        Optional<TaiKhoan> optionalTk = taikhoanRe.findById((long) tk.getMa_taikhoan());

        if (optionalTk.isPresent()) {
            TaiKhoan tk2 = optionalTk.get();
            tk2.setEmail(tk.getEmail());
            tk2.setSdt(tk.getSdt());
            tk2.setHoten(tk.getHoten());
            tk2.setQuyen(tk.getQuyen());
            tk2.setTrang_thai(tk.getTrang_thai());
                taikhoanRe.save(tk2);

        }

        return "redirect:/QLTaiKhoan";
    }

    @PostMapping("/QLTaiKhoan/searchAccount")
    public String searchPn(
            @RequestParam("searchBy") String type,
            @RequestParam("searchText") String searchText,
            @RequestParam(value = "fromDate", required = false) String fromDateStr,
            @RequestParam(value = "toDate", required = false) String toDateStr,
            @RequestParam(value = "status", required = false) String status,
            Model model) {

        ArrayList<TaiKhoan> listFound = new ArrayList<>();
        LocalDate fromDate = null;
        LocalDate toDate = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if (fromDateStr != null && !fromDateStr.isEmpty()) {
            fromDate = LocalDate.parse(fromDateStr, formatter);
        }
        if (toDateStr != null && !toDateStr.isEmpty()) {
            toDate = LocalDate.parse(toDateStr, formatter);
        }
        int statusInt=0;
        if(status!=null && !status.isEmpty()) {
            statusInt = Integer.parseInt(status);
        }

        for (TaiKhoan tk : listTaiKhoan) {

            boolean matches = false;
            switch (type) {
                case "all":
                    matches = true;
                    break;
                case "ma_tk": {
                    int ma_tk = Integer.parseInt(searchText);
                    matches =tk.getMa_taikhoan() == ma_tk;
                    break;
                }
                case "ten_quyen":
                {
                    matches =tk.getQuyen().getTen_quyen().toLowerCase().contains(searchText.toLowerCase());
                    break;
                }
                case "email":
                    matches =tk.getEmail().toLowerCase().contains(searchText.toLowerCase());
                    break;
                case "hoten":
                {
                    matches =tk.getHoten().toLowerCase().contains(searchText.toLowerCase());
                    break;
                }
                case "sdt":
                {
                    matches =tk.getSdt().toLowerCase().contains(searchText.toLowerCase());
                    break;
                }
                case "diachi":
                {
                    matches =tk.getDiachi().toLowerCase().contains(searchText.toLowerCase());
                    break;
                }
                case "ngay_tao":
                {
                    if(tk.getNgay_tao() != null && !tk.getNgay_tao().isEmpty())
                    {
                        LocalDate ngay_muon = LocalDate.parse(tk.getNgay_tao(), formatter);
                        matches =(fromDate == null || !ngay_muon.isBefore(fromDate)) &&
                                (toDate == null || !ngay_muon.isAfter(toDate));

                        break;
                    }

                }

            }

            if (matches) {
                if(statusInt!=0)
                {
                    if (statusInt == tk.getTrang_thai()) {
                        listFound.add(tk);
                    }
                }
                else listFound.add(tk);
            }

        }


        // Return results
        if (listFound.isEmpty()) {
            model.addAttribute("message", "Không tìm thấy tài khoản nào!");
            model.addAttribute("type", "error");
        } else {
            model.addAttribute("data", listFound);
        }

        return "TaiKhoanView";
    }


}
