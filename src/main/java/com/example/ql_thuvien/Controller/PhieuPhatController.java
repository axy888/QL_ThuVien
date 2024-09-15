package com.example.ql_thuvien.Controller;

import com.example.ql_thuvien.Entity.PhieuMuon;
import com.example.ql_thuvien.Entity.PhieuPhat;
import com.example.ql_thuvien.Entity.Sach;
import com.example.ql_thuvien.Repositories.PhieuMuonRepository;
import com.example.ql_thuvien.Repositories.PhieuPhatRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.stream.Collectors;

@Controller
public class PhieuPhatController {

    @Autowired
    private PhieuMuonRepository phieumuonRe;
    @Autowired
    private PhieuPhatRepository phieuphatRe;
    Iterable<PhieuMuon> listPhieuMuon;
    Iterable<PhieuPhat> listPhieuPhat;
    private static ArrayList<PhieuPhat> ArraylistPp = new ArrayList();

    public PhieuPhatController() {
    }

    @RequestMapping({"/QLPhieuPhat"})
    public String toPhieuPhat() {
        return "PhieuPhatView";
    }

    @GetMapping({"/QLPhieuPhat"})
    public String getAll(Model m) {
        listPhieuPhat=this.phieuphatRe.findAll();
        ArraylistPp=(ArrayList<PhieuPhat>)listPhieuPhat;
        m.addAttribute("data", listPhieuPhat);
        return "PhieuPhatView";
    }

    @GetMapping({"/QLPhieuPhat/sort"})
    public String sortBooks(@RequestParam(defaultValue = "ngay_tao") String sortField,
                            @RequestParam(defaultValue = "asc") String sortDir, Model m,
                            HttpServletRequest request) {

        ArraylistPp = (ArrayList<PhieuPhat>)listPhieuPhat;
        listPhieuPhat=sortList(ArraylistPp,sortField,sortDir);
        m.addAttribute("data", listPhieuPhat);
        m.addAttribute("sortField",sortField);
        m.addAttribute("sortDir",sortDir);
        String requestURI = request.getRequestURI();
        return "PhieuPhatView";

    }

    private ArrayList<PhieuPhat> sortList(ArrayList<PhieuPhat> ArraylistPp, String sortField, String sortDir) {
        Comparator<PhieuPhat> comparator = null;
        Collator collator = Collator.getInstance(new Locale("vi", "VN"));
        collator.setStrength(Collator.TERTIARY);
        switch (sortField) {
            case "ngay_tao":
                comparator = Comparator.comparing(PhieuPhat::getNgay_tao, Comparator.nullsFirst(collator));
                break;
            case "tien_phat":
                comparator = Comparator.comparingInt(PhieuPhat::getTien_phat);
                break;
            default:
                throw new IllegalArgumentException("Invalid sort field: " + sortField);
        }
        if (sortDir.equals("desc")) {
            comparator = comparator.reversed();
        }

        // Sort the list
        return ArraylistPp.stream()
                .sorted(comparator)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @PostMapping("/QLPhieuPhat/searchPp")
    public String searchPp(
            @RequestParam("searchBy") String type,
            @RequestParam("searchText") String searchText,
            @RequestParam(value = "fromDate", required = false) String fromDateStr,
            @RequestParam(value = "toDate", required = false) String toDateStr,
            @RequestParam(value = "status", required = false) String status,
            Model model) {

        ArrayList<PhieuPhat> listFound = new ArrayList<>();
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

        for (PhieuPhat pp : listPhieuPhat) {

            boolean matches = false;
            switch (type) {
                case "all":
                    matches = true;
                    break;
                case "ma_phieuphat": {
                    int ma_phieuphat = Integer.parseInt(searchText);
                    matches =pp.getMa_phieuphat() == ma_phieuphat;
                    break;
                }
                case "ma_sach":
                {
                    int ma_sach = Integer.parseInt(searchText);
                    matches =pp.getMa_sach() == ma_sach;
                    break;
                }
                case "ma_phieumuon":
                {
                    int ma_phieumuon = Integer.parseInt(searchText);
                    matches =pp.getMa_phieumuon() == ma_phieumuon;
                    break;
                }
                case "ten_nv":
                    matches =pp.getTen_nv().toLowerCase().contains(searchText.toLowerCase());
                    break;
                case "ngay_tao":
                {
                    if(pp.getNgay_tao() != null && !pp.getNgay_tao().isEmpty())
                    {
                        LocalDate ngay_tao = LocalDate.parse(pp.getNgay_tao(), formatter);
                        matches =(fromDate == null || !ngay_tao.isBefore(fromDate)) &&
                                (toDate == null || !ngay_tao.isAfter(toDate));

                        break;
                    }

                }
                case "tien_phat":
                {
                    int tien_phat = Integer.parseInt(searchText);
                    matches =pp.getTien_phat() == tien_phat;
                    break;
                }


            }

            if (matches) {
                if(statusInt!=0)
                {
                    if (statusInt == pp.getTrang_thai()) {
                        listFound.add(pp);
                    }
                }
                else listFound.add(pp);
            }

        }


        // Return results
        if (listFound.isEmpty()) {
            model.addAttribute("message", "Không tìm thấy phiếu nào!");
            model.addAttribute("type", "error");
        } else {
            model.addAttribute("data", listFound);
        }

        return "PhieuPhatView";
    }

    @GetMapping(value = {"QLPhieuPhat/duyet/{id}"})
    public String DuyetPhieuMuon(@PathVariable("id") int id,
                                 HttpSession session,
                                 Model model) {
        String ten_nv=(String) session.getAttribute("username");
        Optional<PhieuPhat> optionalPp = phieuphatRe.findById((long)id);

        if (optionalPp.isPresent()) {
            PhieuPhat pp = optionalPp.get();
            pp.setTrang_thai(2);
            phieuphatRe.save(pp);
        }
        return "redirect:/QLPhieuPhat";
    }


}
