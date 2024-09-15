package com.example.ql_thuvien.Controller;

import com.example.ql_thuvien.Entity.PhieuMuon;
import com.example.ql_thuvien.Entity.PhieuPhat;
import com.example.ql_thuvien.Entity.Sach;
import com.example.ql_thuvien.Entity.TaiKhoan;
import com.example.ql_thuvien.Repositories.PhieuMuonRepository;
import com.example.ql_thuvien.Repositories.PhieuPhatRepository;
import com.example.ql_thuvien.Repositories.SachRepositories;
import com.example.ql_thuvien.Repositories.TaiKhoanReposiroty;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.Collator;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class PhieuMuonController {
    @Autowired
    private SachRepositories sachRe;
    @Autowired
    private TaiKhoanReposiroty taikhoanRe;
    @Autowired
    private PhieuMuonRepository phieumuonRe;
    @Autowired
    private PhieuPhatRepository phieuphatRe;
    Iterable<Sach> list;
    Iterable<TaiKhoan> listTaiKhoan;
    Iterable<PhieuMuon> listPhieuMuon;
    Iterable<PhieuPhat> listPhieuPhat;
    private static ArrayList<PhieuMuon> ArraylistPm = new ArrayList();
    LocalDate currentDate = LocalDate.now();
    LocalDate futureDate = currentDate.plusDays(14);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public PhieuMuonController() {
    }

    @RequestMapping({"/QLPhieuMuon"})
    public String toPhieuMuon() {
        return "PhieuMuonView";
    }

    @GetMapping({"/QLPhieuMuon"})
    public String getAll(Model m) {
        listPhieuMuon=this.phieumuonRe.findAll();
        ArraylistPm=(ArrayList<PhieuMuon>)listPhieuMuon;
        m.addAttribute("data", listPhieuMuon);
        return "PhieuMuonView";
    }

    @GetMapping({"/QLPhieuMuon/sort"})
    public String sortBooks(@RequestParam(defaultValue = "ten_sach") String sortField,
                            @RequestParam(defaultValue = "asc") String sortDir, Model m,
                            HttpServletRequest request) {

        ArraylistPm = (ArrayList<PhieuMuon>)listPhieuMuon;
        listPhieuMuon=sortList(ArraylistPm,sortField,sortDir);
        m.addAttribute("data", listPhieuMuon);
        m.addAttribute("sortField",sortField);
        m.addAttribute("sortDir",sortDir);
        String requestURI = request.getRequestURI();
            return "PhieuMuonView";

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

    //    Duyệt  phiếu mượn
    @GetMapping(value = {"QLPhieuMuon/duyet/{id}"})
    public String DuyetPhieuMuon(@PathVariable("id") int id,
                               HttpSession session,
                               Model model) {
        String ten_nv=(String) session.getAttribute("username");
        Optional<PhieuMuon> optionalPm = phieumuonRe.findById((long)id);

        if (optionalPm.isPresent()) {
            PhieuMuon pm = optionalPm.get();
            Optional<Sach> optionalSa = sachRe.findById((long)pm.getMa_sach());
            if (optionalSa.isPresent()) {
                Sach sa = optionalSa.get();
                // Update properties of the existing ThanhVien object
                sa.setSo_luong(sa.getSo_luong()-1);
                // Save the updated ThanhVien object back to the database
                sachRe.save(sa);
            }
                pm.setNgay_muon(currentDate.format(formatter));
                pm.setTen_nv(ten_nv);
                pm.setNgay_han(futureDate.format(formatter));
                pm.setTrang_thai(2);
            phieumuonRe.save(pm);
        }
        return "redirect:/QLPhieuMuon";
    }

    //    Sửa phiếu mượn
    @GetMapping(value = {"QLPhieuMuon/edit/{id}"})
    public String showEditForm(@PathVariable("id") int id, Model model) {
        PhieuMuon pm = ArraylistPm.get(id);
        model.addAttribute("phieumuon", pm);
        return "editPm";
    }

    @PostMapping("/updatePm")
    public String updatePM(@ModelAttribute("phieumuon") PhieuMuon pmNew,HttpSession session) {
        // Retrieve existing ThanhVien object from the database
        Optional<PhieuMuon> optionalPm = phieumuonRe.findById((long)pmNew.getMa_phieumuon());
        Optional<Sach> optionalSa = sachRe.findById((long)pmNew.getMa_sach());
        String ten_nv=(String) session.getAttribute("username");
        if (optionalPm.isPresent()) {
            PhieuMuon pm = optionalPm.get();
            pm.setNgay_tra(pmNew.getNgay_tra());
            pm.setTrang_thai(pmNew.getTrang_thai());

            if(pmNew.getTrang_thai()!=7)
            {
                Sach sa=optionalSa.get();
                sa.setSo_luong(sa.getSo_luong()+1);
                sachRe.save(sa);
            }
            if(pmNew.getTrang_thai()!=3)
            {
                PhieuPhat pp=new PhieuPhat(pmNew.getMa_phieumuon(),pmNew.getMa_taikhoan(),pmNew.getMa_sach(),ten_nv,
                        currentDate.format(formatter),0,"",1);
                if(pmNew.getTrang_thai()==4)
                {
                    LocalDate ngayHan = LocalDate.parse(pmNew.getNgay_han(), DateTimeFormatter.ISO_LOCAL_DATE);
                    LocalDate ngayTra = LocalDate.parse(pmNew.getNgay_tra(), DateTimeFormatter.ISO_LOCAL_DATE);
                    long soNgayTre = ChronoUnit.DAYS.between(ngayHan, ngayTra);
                    pp.setTien_phat((int)soNgayTre * 2000);
                    pp.setMo_ta("Trả trễ hạn");
                    phieuphatRe.save(pp);
                }
                if(pmNew.getTrang_thai()==5)
                {
                    Sach sa=optionalSa.get();
                    pp.setTien_phat((int)(sa.getGia()*0.1));
                    pp.setMo_ta("Làm hỏng sách ít");
                    phieuphatRe.save(pp);
                }
                if(pmNew.getTrang_thai()==6)
                {
                    Sach sa=optionalSa.get();
                    pp.setTien_phat((int)(sa.getGia()*0.5));
                    pp.setMo_ta("Làm hỏng sách nhiều");
                    phieuphatRe.save(pp);
                }
                if(pmNew.getTrang_thai()==7)
                {
                    Sach sa=optionalSa.get();
                    pp.setTien_phat(sa.getGia());
                    pp.setMo_ta("Làm mất sách");
                    phieuphatRe.save(pp);
                }
            }

            phieumuonRe.save(pm);
        }

        return "redirect:/QLPhieuMuon";
    }

    @PostMapping("/QLPhieuMuon/searchPm")
    public String searchPm(
            @RequestParam("searchBy") String type,
            @RequestParam("searchText") String searchText,
            @RequestParam(value = "fromDate", required = false) String fromDateStr,
            @RequestParam(value = "toDate", required = false) String toDateStr,
            @RequestParam(value = "status", required = false) String status,
            Model model) {

        ArrayList<PhieuMuon> listFound = new ArrayList<>();
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

        for (PhieuMuon pm : listPhieuMuon) {

            boolean matches = false;
            switch (type) {
                case "all":
                    matches = true;
                    break;
                case "ma_phieu": {
                    int ma_phieu = Integer.parseInt(searchText);
                    matches =pm.getMa_phieumuon() == ma_phieu;
                    break;
                }
                case "ma_sach":
                {
                    int ma_sach = Integer.parseInt(searchText);
                    matches =pm.getMa_sach() == ma_sach;
                    break;
                }
                case "ma_taikhoan":
                {
                    int ma_taikhoan = Integer.parseInt(searchText);
                    matches =pm.getMa_taikhoan() == ma_taikhoan;
                    break;
                }
                case "ten_nv":
                    matches =pm.getTen_nv().toLowerCase().contains(searchText.toLowerCase());
                    break;
                case "ngay_muon":
                {
                    if(pm.getNgay_muon() != null && !pm.getNgay_muon().isEmpty())
                    {
                        LocalDate ngay_muon = LocalDate.parse(pm.getNgay_muon(), formatter);
                        matches =(fromDate == null || !ngay_muon.isBefore(fromDate)) &&
                                (toDate == null || !ngay_muon.isAfter(toDate));

                        break;
                    }

                }
                case "ngay_tra":
                {
                    if(pm.getNgay_tra() != null && !pm.getNgay_tra().isEmpty()) {
                        LocalDate ngay_tra = LocalDate.parse(pm.getNgay_tra(), formatter);
                        matches =(fromDate == null || !ngay_tra.isBefore(fromDate)) &&
                                (toDate == null || !ngay_tra.isAfter(toDate));

                    }
                    break;
                }
                case "ngay_han":
                {
                    if(pm.getNgay_han() != null && !pm.getNgay_han().isEmpty()) {
                        LocalDate ngay_han = LocalDate.parse(pm.getNgay_han(), formatter);
                        matches =(fromDate == null || !ngay_han.isBefore(fromDate)) &&
                                (toDate == null || !ngay_han.isAfter(toDate));
                    }
                    break;
                }

            }

            if (matches) {
                if(statusInt!=0)
                {
                    if (statusInt == pm.getTrang_thai() ||
                            (statusInt == 4 && (pm.getTrang_thai() == 5 || pm.getTrang_thai() == 6 || pm.getTrang_thai() == 7))) {
                        listFound.add(pm);
                    }
                }
                else listFound.add(pm);
            }

        }


        // Return results
        if (listFound.isEmpty()) {
            model.addAttribute("message", "Không tìm thấy phiếu nào!");
            model.addAttribute("type", "error");
        } else {
            model.addAttribute("data", listFound);
        }

        return "PhieuMuonView";
    }






}
