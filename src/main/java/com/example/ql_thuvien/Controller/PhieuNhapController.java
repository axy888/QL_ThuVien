package com.example.ql_thuvien.Controller;

import com.example.ql_thuvien.Entity.*;
import com.example.ql_thuvien.Repositories.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

@Controller
public class PhieuNhapController {
    @Autowired
    private PhieuNhapRepository phieunhapRe;
    @Autowired
    private CT_PhieuNhapRepository ct_phieunhapRe;
    @Autowired
    private NhaCungCapRepository nccRe;
    @Autowired
    private SachRepositories saRe;
    Iterable<PhieuNhap> listPn;
    Iterable<CT_PhieuNhap> listCtPn;
    Iterable<NhaCungCap> listNcc;
    Iterable<Sach> listSa;
    private static ArrayList<PhieuNhap> ArraylistPn = new ArrayList();
    private static ArrayList<CT_PhieuNhap> Arraylistct_Pn = new ArrayList();
    LocalDate currentDate = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public PhieuNhapController() {
    }

    @RequestMapping({"/QLPhieuNhap"})
    public String toPhieuNhap() {
        return "PhieuNhapView";
    }

    @GetMapping({"/QLPhieuNhap"})
    public String getAll(Model m) {
        listPn=this.phieunhapRe.findAll();
        ArraylistPn=(ArrayList<PhieuNhap>)listPn;
        m.addAttribute("data", listPn);
        return "PhieuNhapView";
    }

    @RequestMapping(value = "addImport")
    public String showAddForm(Model model) {
        PhieuNhap pn = new PhieuNhap();
        listNcc=this.nccRe.findAll();
        model.addAttribute("phieunhap", pn);
        model.addAttribute("listNcc",listNcc);
//        model.addAttribute("listNcc",listNhaCungCap);
        return "crud/addImport";
    }

    @PostMapping("/saveImport")
    public String save(@ModelAttribute("phieunhap") PhieuNhap pn, Model model, HttpSession session) {
        // Process the form data and save to the database
        NhaCungCap ncc=pn.getNcc();
        String ten_nv=(String) session.getAttribute("username");
        // Create a new ThanhVien object with the data
        PhieuNhap pnNew = new PhieuNhap(ncc,currentDate.format(formatter),ten_nv,0,1);
        phieunhapRe.save(pnNew);

        // Retrieve all ThanhVien objects and add them to the model
        // Redirect to the ThanhVien management page
        return "redirect:/QLPhieuNhap";
    }

    @GetMapping(value = {"QLPhieuNhap/detail/{id}"})
    public String showEditForm(@PathVariable("id") int id, Model model)
    {
        int trang_thai=0;
        Optional <PhieuNhap> optionalPn=phieunhapRe.findById((long)id);
        if (optionalPn.isPresent())
        {
            PhieuNhap pn=optionalPn.get();
            trang_thai=pn.getTrang_thai();
        }
        listCtPn=this.ct_phieunhapRe.findAll();
        Arraylistct_Pn=(ArrayList<CT_PhieuNhap>)listCtPn;
       ArrayList<CT_PhieuNhap> Arraylistct_Pn_found=new ArrayList();
        for(CT_PhieuNhap ctpn:Arraylistct_Pn)
            if(ctpn.getPhieunhap().getMa_phieunhap()==id)Arraylistct_Pn_found.add(ctpn);
        model.addAttribute("data",Arraylistct_Pn_found);
        model.addAttribute("ma_phieu_hientai",id);
        model.addAttribute("trang_thai_hientai",trang_thai);
        return "CTPNView";
    }

    @RequestMapping(value = "addImportDetail/{id}")
    public String showAddImportForm(@PathVariable("id") int id,Model model) {
        CT_PhieuNhap ctpn = new CT_PhieuNhap();
        Optional<PhieuNhap> optionalPn = phieunhapRe.findById((long)id);
        PhieuNhap pn=new PhieuNhap();
        if (optionalPn.isPresent())
        {
            pn=optionalPn.get();
        }
        ctpn.setPhieunhap(pn);
        listSa=saRe.findAll();
        model.addAttribute("ct_phieunhap", ctpn);
        model.addAttribute("listSa",listSa);
//        model.addAttribute("listNcc",listNhaCungCap);
        return "crud/addImportDetail";
    }

    @PostMapping("/saveImportDetail")
    public String saveImportDetail(@ModelAttribute("ct_phieunhap") CT_PhieuNhap ctpn, Model model) {
        // Process the form data and save to the database
        PhieuNhap pn=ctpn.getPhieunhap();
        Sach sa=ctpn.getSa();
        int so_luong=ctpn.getSo_luong();
        sa.setSo_luong(sa.getSo_luong()+so_luong);
        saRe.save(sa);
        int don_gia=ctpn.getDon_gia();
        Optional<PhieuNhap> optionalPn = phieunhapRe.findById((long)pn.getMa_phieunhap());
        PhieuNhap pn2=new PhieuNhap();
        if (optionalPn.isPresent())
        {
            pn2=optionalPn.get();
            pn2.setTong_tien(don_gia+ pn2.getTong_tien());
            CT_PhieuNhap ctpnNew = new CT_PhieuNhap(pn.getMa_phieunhap(),sa.getMa_sach(),so_luong,don_gia);
            pn2.addCT_PhieuNhap(ctpnNew);
            // Lưu phiếu nhập và chi tiết phiếu nhập
            phieunhapRe.save(pn2);  // Sẽ tự động lưu CT_PhieuNhap do CascadeType.ALL
        }


//        ct_phieunhapRe.save(ctpnNew);

        return "redirect:/QLPhieuNhap/detail/" +pn.getMa_phieunhap();
    }

    @GetMapping(value = {"/QLPhieuNhap/delete/{ma_phieunhap}/{ma_sach}"})
    public String deleteImportDetail(@PathVariable("ma_phieunhap") int ma_phieunhap,
                                     @PathVariable("ma_sach") int ma_sach,
                                     Model model) {
        // Tìm phiếu nhập dựa trên ma_phieunhap
        Optional<PhieuNhap> optionalPn = phieunhapRe.findById((long) ma_phieunhap);

        if (optionalPn.isPresent()) {
            PhieuNhap pn2 = optionalPn.get();

            // Tìm chi tiết phiếu nhập (CT_PhieuNhap) trong danh sách của PhieuNhap
            CT_PhieuNhap ctPhieuNhapToRemove = null;
            for (CT_PhieuNhap ct : pn2.getChiTietPhieuNhap()) {
                if (ct.getMa_phieunhap() == ma_phieunhap && ct.getMa_sach() == ma_sach) {
                    ctPhieuNhapToRemove = ct;
                    break;
                }
            }

            if (ctPhieuNhapToRemove != null) {
                // Cập nhật số lượng sách
                Sach sa = ctPhieuNhapToRemove.getSa();
                int so_luong = ctPhieuNhapToRemove.getSo_luong();
                sa.setSo_luong(sa.getSo_luong() - so_luong);
                saRe.save(sa);

                // Cập nhật tổng tiền trong phiếu nhập
                int don_gia = ctPhieuNhapToRemove.getDon_gia();
                pn2.setTong_tien(pn2.getTong_tien() - don_gia);

                // Xóa CT_PhieuNhap khỏi danh sách chiTietPhieuNhap của PhieuNhap
                pn2.removeCT_PhieuNhap(ctPhieuNhapToRemove);

                // Lưu phiếu nhập (Hibernate sẽ tự động xóa CT_PhieuNhap nhờ orphanRemoval = true)
                phieunhapRe.save(pn2);
            }
        }

        return "redirect:/QLPhieuNhap/detail/" + ma_phieunhap;
    }

    @GetMapping(value = {"QLPhieuNhap/edit/{ma_phieunhap}/{ma_sach}"})
    public String showEditFormCTPN(@PathVariable("ma_phieunhap") int ma_phieunhap,
                                   @PathVariable("ma_sach") int ma_sach,
                                   Model model) {
        CT_PhieuNhap ctpn=new CT_PhieuNhap();
        for(CT_PhieuNhap ct : Arraylistct_Pn)
            if(ct.getMa_phieunhap()==ma_phieunhap && ct.getMa_sach()==ma_sach)
                ctpn=ct;
        model.addAttribute("ct_phieunhap", ctpn);
        return "crud/editImportDetail";
    }

    @PostMapping("/updateImportDetail")
    public String updateImportDetail(@ModelAttribute("ct_phieunhap") CT_PhieuNhap ctpn,
                                     @RequestParam("ma_phieunhap") int ma_phieunhap,
                                     @RequestParam("ma_sach") int ma_sach,
                                     Model model) {
        // Tìm phiếu nhập dựa trên ma_phieunhap
        Optional<PhieuNhap> optionalPn = phieunhapRe.findById((long) ctpn.getMa_phieunhap());

        if (optionalPn.isPresent()) {
            PhieuNhap pn2 = optionalPn.get();
            // Tìm chi tiết phiếu nhập (CT_PhieuNhap) trong danh sách của PhieuNhap
            CT_PhieuNhap ctPhieuNhapToEdit = null;
            for (CT_PhieuNhap ct : pn2.getChiTietPhieuNhap()) {
                if (ct.getMa_phieunhap() == ma_phieunhap && ct.getSa().getMa_sach() == ma_sach) {
                    ctPhieuNhapToEdit = ct;
                    System.out.println(ctPhieuNhapToEdit.getMa_phieunhap());
                    break;
                }
            }

            if (ctPhieuNhapToEdit != null) {
                // Cập nhật số lượng sách trong kho
                Sach sa = ctPhieuNhapToEdit.getSa();
                int so_luong_cu = ctPhieuNhapToEdit.getSo_luong();
                int so_luong_moi = ctpn.getSo_luong();
                System.out.println(so_luong_cu);
                System.out.println(so_luong_moi);
                sa.setSo_luong(sa.getSo_luong() - so_luong_cu + so_luong_moi);
                saRe.save(sa);

                // Cập nhật tổng tiền trong phiếu nhập
                int don_gia_cu = ctPhieuNhapToEdit.getDon_gia();
                int don_gia_moi = ctpn.getDon_gia();
                pn2.setTong_tien(pn2.getTong_tien() - don_gia_cu*so_luong_cu + so_luong_moi*don_gia_moi);

                // Cập nhật chi tiết phiếu nhập
                ctPhieuNhapToEdit.setSo_luong(so_luong_moi);
                ctPhieuNhapToEdit.setDon_gia(don_gia_moi);

                // Lưu phiếu nhập (Hibernate sẽ tự động lưu chi tiết nhờ CascadeType.ALL)
                phieunhapRe.save(pn2);
            }
        }

        return "redirect:/QLPhieuNhap/detail/" + ctpn.getMa_phieunhap();
    }

    @GetMapping(value = {"QLPhieuNhap/change_status/{id}"})
    public String change_statusPn(@PathVariable("id") int id, Model model)
    {
        Optional<PhieuNhap> optionalPn = phieunhapRe.findById((long) id);

        PhieuNhap pn2=new PhieuNhap();
        if (optionalPn.isPresent()) {
            pn2 = optionalPn.get();
            pn2.setTrang_thai(2);
            phieunhapRe.save(pn2);
        }
        return "PhieuNhapView";
    }
}
