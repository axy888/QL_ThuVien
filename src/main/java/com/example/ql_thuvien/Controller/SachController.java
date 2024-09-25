package com.example.ql_thuvien.Controller;

import com.example.ql_thuvien.Entity.NhaCungCap;
import com.example.ql_thuvien.Entity.PhieuMuon;
import com.example.ql_thuvien.Entity.Sach;
import com.example.ql_thuvien.Entity.TheLoai;
import com.example.ql_thuvien.Repositories.NhaCungCapRepository;
import com.example.ql_thuvien.Repositories.PhieuMuonRepository;
import com.example.ql_thuvien.Repositories.SachRepositories;
import com.example.ql_thuvien.Repositories.TheLoaiRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.Collator;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class SachController {

    @Autowired
    private SachRepositories sachRe;
    @Autowired
    private TheLoaiRepository theloaiRe;
    @Autowired
    private PhieuMuonRepository phieumuonRe;
    @Autowired
    private NhaCungCapRepository nccRe;
    Iterable<Sach> list;
    Iterable<TheLoai> listTheLoai;
    Iterable<PhieuMuon> listPhieuMuon;
    Iterable<NhaCungCap> listNhaCungCap;

    //listSach là list sách full lấy từ sql, còn listMuonSach là list sách có sl>0
    private static ArrayList<Sach> listSach = new ArrayList();

    private static ArrayList<Sach> listMuonSach = new ArrayList();

    public SachController() {
    }

    @RequestMapping({"/QLSach"})
    public String toSach() {
        return "SachView";
    }

    @GetMapping({"/QLSach"})
    public String getAll(Model m) {
        list=this.sachRe.findAll();
        listTheLoai=this.theloaiRe.findAll();
        listNhaCungCap=this.nccRe.findAll();
        listSach = (ArrayList<Sach>)list;
        m.addAttribute("data", list);
        m.addAttribute("listTheLoai",listTheLoai);
        m.addAttribute("listNcc",listNhaCungCap);
            return "SachView";
    }

    @GetMapping({"/QLSach/sort","/QLMuonSach/sort"})
    public String sortBooks(@RequestParam(defaultValue = "ten_sach") String sortField,
                         @RequestParam(defaultValue = "asc") String sortDir, Model m,
                            HttpServletRequest request) {

        listSach = (ArrayList<Sach>)list;
        list=sortList(listSach,sortField,sortDir);
        m.addAttribute("data", list);
        m.addAttribute("sortField",sortField);
        m.addAttribute("sortDir",sortDir);
        String requestURI = request.getRequestURI();
        if (requestURI.contains("/QLSach")) {
            return "SachView";
        } else if (requestURI.contains("/QLMuonSach")) {
            return "MuonSachView";
        }

        return "error";
    }

//    thêm sách
    @RequestMapping(value = "addBook")
    public String showAddForm(Model model) {
        Sach sa = new Sach();
        model.addAttribute("sach", sa);
        model.addAttribute("theloaiList",listTheLoai);
        model.addAttribute("listNcc",listNhaCungCap);
        return "addBook";
    }

    @PostMapping("/saveBook")
    public String save(@ModelAttribute("sach") Sach sa, Model model) {
        // Process the form data and save to the database
        TheLoai theloia = sa.getTheLoai();
        NhaCungCap ncc=sa.getNhaCungCap();
        String ten_sach = sa.getTen_sach();
        String tac_gia = sa.getTac_gia();
        String mo_ta = sa.getMo_ta();
        String nxb = sa.getNXB();
        String ngay_xuat_ban = sa.getNgay_xuat_ban();
        int gia=sa.getGia();
        String hinh = sa.getHinh();

        // Create a new ThanhVien object with the data
        Sach saNew = new Sach(theloia, ncc,ten_sach, tac_gia, mo_ta, nxb, ngay_xuat_ban,0,gia,hinh);
        sachRe.save(saNew);

        // Retrieve all ThanhVien objects and add them to the model
        list = sachRe.findAll();
        model.addAttribute("list", list);

        // Redirect to the ThanhVien management page
        return "redirect:/QLSach";
    }

//    Sửa sách
    @GetMapping(value = {"QLSach/edit/{id}"})
    public String showEditForm(@PathVariable("id") int id, Model model) {
        Sach sa = listSach.get(id);
        model.addAttribute("sach", sa);
        return "editBook";
    }

    @PostMapping("/updateBook")
    public String updateTV(@ModelAttribute("sach") Sach saNew) {
        // Retrieve existing ThanhVien object from the database
        Optional<Sach> optionalSa = sachRe.findById((long)saNew.getMa_sach());

        if (optionalSa.isPresent()) {
            Sach sa = optionalSa.get();
            // Update properties of the existing ThanhVien object
            sa.setMo_ta(saNew.getMo_ta());
            if (saNew.getHinh() != null && !saNew.getHinh().isEmpty()) {
                sa.setHinh(saNew.getHinh());
            }
            // Save the updated ThanhVien object back to the database
            sachRe.save(sa);
        }

        return "redirect:/QLSach";
    }

//    Tìm kiếm sách
    public Sach getBookByID(int id){
        for(Sach sa : sachRe.findAll()){
            if(sa.getMa_sach() == id)
                return sa;
        }
        return null;
    }

    @PostMapping("/QLSach/searchBook")
    public String searchBook(
            @RequestParam("searchBy") String type,
            @RequestParam("searchText") String searchText,
            @RequestParam(value = "fromDate", required = false) String fromDateStr,
            @RequestParam(value = "toDate", required = false) String toDateStr,
            Model model) {

        ArrayList<Sach> listFound = new ArrayList<>();
        LocalDate fromDate = null;
        LocalDate toDate = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            if (fromDateStr != null && !fromDateStr.isEmpty()) {
                fromDate = LocalDate.parse(fromDateStr, formatter);
            }
            if (toDateStr != null && !toDateStr.isEmpty()) {
                toDate = LocalDate.parse(toDateStr, formatter);
            }

        for (Sach sa : listSach) {
            boolean matches = false;

            switch (type) {
                case "all":
                    matches = true;
                    break;
                case "ma_sach": {
                    int ma_sach = Integer.parseInt(searchText);
                    matches = sa.getMa_sach() == ma_sach;
                    break;
                }
                case "ma_loai":
                    {
                        int ma_loai = Integer.parseInt(searchText);
                        matches = sa.getTheLoai().getMa_loai() == ma_loai;
                        break;
                    }
                case "ma_ncc":
                {
                    int ma_ncc = Integer.parseInt(searchText);
                    matches = sa.getNhaCungCap().getMa_ncc() == ma_ncc;
                    break;
                }
                case "ten_sach":
                    matches = sa.getTen_sach().toLowerCase().contains(searchText.toLowerCase());
                    break;
                case "tac_gia":
                    matches = sa.getTac_gia().toLowerCase().contains(searchText.toLowerCase());
                    break;
                case "NXB":
                    matches = sa.getNXB().toLowerCase().contains(searchText.toLowerCase());
                    break;
                case "so_luong": {
                    int so_luong = Integer.parseInt(searchText);
                    matches = sa.getSo_luong() == so_luong;
                    break;
                }
                case "gia": {
                    int gia = Integer.parseInt(searchText);
                    matches = sa.getGia() == gia;
                    break;
                }
            }

            // Check date range
            if (matches) {
                LocalDate ngayXuatBan = LocalDate.parse(sa.getNgay_xuat_ban(), formatter);
                if ((fromDate == null || !ngayXuatBan.isBefore(fromDate)) &&
                        (toDate == null || !ngayXuatBan.isAfter(toDate))) {
                    listFound.add(sa);
                }
            }
        }

        // Return results
        if (listFound.isEmpty()) {
            model.addAttribute("message", "Không tìm thấy quyển sách nào!");
            model.addAttribute("type", "error");
        } else {
            model.addAttribute("data", listFound);
        }

        return "SachView";
    }


    @PostMapping("/QLMuonSach/searchBook")
    public String searchBorrowBook(
            @RequestParam("searchBy") String type,
            @RequestParam("searchText") String searchText,
            @RequestParam(value = "fromDate", required = false) String fromDateStr,
            @RequestParam(value = "toDate", required = false) String toDateStr,
            Model model) {

        ArrayList<Sach> listFound = new ArrayList<>();
        LocalDate fromDate = null;
        LocalDate toDate = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if (fromDateStr != null && !fromDateStr.isEmpty()) {
            fromDate = LocalDate.parse(fromDateStr, formatter);
        }
        if (toDateStr != null && !toDateStr.isEmpty()) {
            toDate = LocalDate.parse(toDateStr, formatter);
        }

        for (Sach sa : listMuonSach) {
            boolean matches = false;

            switch (type) {
                case "all":
                    matches = true;
                    break;
                case "ma_sach": {
                    int ma_sach = Integer.parseInt(searchText);
                    matches = sa.getMa_sach() == ma_sach;
                    break;
                }
                case "ma_loai":
                {
                    int ma_loai = Integer.parseInt(searchText);
                    matches = sa.getTheLoai().getMa_loai() == ma_loai;
                    break;
                }
                case "ten_sach":
                    matches = sa.getTen_sach().toLowerCase().contains(searchText.toLowerCase());
                    break;
                case "tac_gia":
                    matches = sa.getTac_gia().toLowerCase().contains(searchText.toLowerCase());
                    break;
                case "NXB":
                    matches = sa.getNXB().toLowerCase().contains(searchText.toLowerCase());
                    break;
                case "so_luong": {
                    int so_luong = Integer.parseInt(searchText);
                    matches = sa.getSo_luong() == so_luong;
                    break;
                }
                case "gia": {
                    int gia = Integer.parseInt(searchText);
                    matches = sa.getGia() == gia;
                    break;
                }
            }

            // Check date range
            if (matches) {
                LocalDate ngayXuatBan = LocalDate.parse(sa.getNgay_xuat_ban(), formatter);
                if ((fromDate == null || !ngayXuatBan.isBefore(fromDate)) &&
                        (toDate == null || !ngayXuatBan.isAfter(toDate))) {
                    listFound.add(sa);
                }
            }
        }

        // Return results
        if (listFound.isEmpty()) {
            model.addAttribute("message", "Không tìm thấy quyển sách nào!");
            model.addAttribute("type", "error");
        } else {
            model.addAttribute("data", listFound);
            model.addAttribute("listTheLoai",listTheLoai);
        }

        return "MuonSachView";
    }


    private ArrayList<Sach> sortList(ArrayList<Sach> listSach, String sortField, String sortDir) {
        Comparator<Sach> comparator = null;
        Collator collator = Collator.getInstance(new Locale("vi", "VN"));
        collator.setStrength(Collator.TERTIARY);
        switch (sortField) {
            case "ten_sach":
                comparator = Comparator.comparing(Sach::getTen_sach, collator);
                break;
            case "tac_gia":
                comparator = Comparator.comparing(Sach::getTac_gia, collator);
                break;
            case "ngay_xuat_ban":
                comparator = Comparator.comparing(Sach::getNgay_xuat_ban, collator);
                break;
            case "so_luong":
                comparator = Comparator.comparingInt(Sach::getSo_luong);
                break;
            case "gia":
                comparator = Comparator.comparingInt(Sach::getGia);
                break;
            default:
                throw new IllegalArgumentException("Invalid sort field: " + sortField);
        }
        if (sortDir.equals("desc")) {
            comparator = comparator.reversed();
        }

        // Sort the list
        return listSach.stream()
                .sorted(comparator)
                .collect(Collectors.toCollection(ArrayList::new));
    }

//    Mượn sách bên user
    @RequestMapping({"/QLMuonSach"})
    public String toMuonSach() {
        return "MuonSachView";
    }

    @GetMapping({"QLMuonSach"})
    public String getAllMuonSach(Model m) {
        list=this.sachRe.findAll();
        listSach = (ArrayList<Sach>)list;
        listMuonSach=new ArrayList();
            for(Sach sa:listSach)
            {
                if(sa.getSo_luong()>0)listMuonSach.add(sa);
            }


        listTheLoai=this.theloaiRe.findAll();

        m.addAttribute("data", listMuonSach);
        m.addAttribute("listTheLoai",listTheLoai);
            return "MuonSachView";
    }

    @GetMapping(value = {"QLMuonSach/searchByTheLoai/{ma_loai}"})
    public String searchByTheLoai(@PathVariable("ma_loai") int ma_loai, Model model) {
        ArrayList<Sach> listFound=new ArrayList<>();
        if(ma_loai==0)
        {
            model.addAttribute("data",listMuonSach);
            model.addAttribute("listTheLoai",listTheLoai);
            return "MuonSachView";
        }
        else
        {
            for(Sach sa: listMuonSach)
            {
                if(sa.getTheLoai().getMa_loai()==ma_loai)listFound.add(sa);
            }
            model.addAttribute("data",listFound);
            model.addAttribute("listTheLoai",listTheLoai);
            return "MuonSachView";
        }

    }



    @GetMapping(value = {"QLMuonSach/borrow/{ma_sach}"})
    public String borrow(@PathVariable("ma_sach") int ma_sach, HttpSession session, Model model)
    {
        int ma_taikhoan=(int) session.getAttribute("ma_taikhoan");
        int dem=0;
        System.out.println(ma_taikhoan);
        listPhieuMuon=this.phieumuonRe.findAll();
        for(PhieuMuon pma: listPhieuMuon)
        {
            if(pma.getMa_taikhoan()==ma_taikhoan)
            {
                if(pma.getMa_sach()==ma_sach &&(pma.getTrang_thai()==1 || pma.getTrang_thai()==2 ))
                {
                    model.addAttribute("data", listMuonSach);
                    model.addAttribute("listTheLoai", listTheLoai);
                    model.addAttribute("message","Sách này bạn đang mượn");
                    model.addAttribute("type", "error");
                    return "MuonSachView";
                }
                if(pma.getTrang_thai()==1 || pma.getTrang_thai()==2)
                {
                    dem++;
                    if(dem==3)
                    {
                        model.addAttribute("data", listMuonSach);
                        model.addAttribute("listTheLoai", listTheLoai);
                        model.addAttribute("message","Bạn chỉ được mượn tối đa 3 quyển sách");
                        model.addAttribute("type", "error");
                        return "MuonSachView";
                    }
                }
            }
        }
            PhieuMuon pm=new PhieuMuon(ma_taikhoan,ma_sach,null,null,null,null,1);
            phieumuonRe.save(pm);
            list = sachRe.findAll();

            model.addAttribute("data", listMuonSach);
            model.addAttribute("listTheLoai", listTheLoai);
            model.addAttribute("message","Mượn thành công");
            model.addAttribute("type", "success");
            return "MuonSachView";

    }

    @RequestMapping({"/QLCTSach/{id}"})
    public String toCTSach(@PathVariable("id") int id, Model model)
    {
        Sach sa = new Sach();
        for(Sach sa2: listMuonSach)
            if(sa2.getMa_sach()==id)sa=sa2;
        ArrayList<TheLoai> tempTheLoai=(ArrayList<TheLoai>)listTheLoai;
        TheLoai theloai=new TheLoai();
        for(TheLoai tl :tempTheLoai)
            if(tl.getMa_loai()==sa.getTheLoai().getMa_loai())theloai=tl;
        model.addAttribute("sach", sa);
        model.addAttribute("theloai",theloai);
        return "CTSachView";
    }
}
