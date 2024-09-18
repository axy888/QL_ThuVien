package com.example.ql_thuvien.Controller;

import com.example.ql_thuvien.Entity.NhaCungCap;
import com.example.ql_thuvien.Entity.Sach;
import com.example.ql_thuvien.Repositories.NhaCungCapRepository;
import com.example.ql_thuvien.Repositories.TheLoaiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Controller
public class NhaCungCapController {

    @Autowired
    private NhaCungCapRepository nccRe;
    Iterable<NhaCungCap> listNhaCungCap;

    private static ArrayList<NhaCungCap> ArrayListNcc = new ArrayList();
    public NhaCungCapController() {
    }

    @RequestMapping({"/QLNcc"})
    public String toNCC() {
        return "NhaCungCapView";
    }

    @GetMapping({"/QLNcc"})
    public String getAll(Model m) {
        listNhaCungCap=this.nccRe.findAll();
        ArrayListNcc = (ArrayList<NhaCungCap>)listNhaCungCap;
        m.addAttribute("data",listNhaCungCap);
        return "NhaCungCapView";
    }

    @PostMapping("/QLNcc/searchSupplier")
    public String searchSupplier(
            @RequestParam("searchBy") String type,
            @RequestParam("searchText") String searchText,
            Model model) {

        ArrayList<NhaCungCap> listFound = new ArrayList<>();

        for (NhaCungCap ncc : ArrayListNcc) {
            boolean matches = false;

            switch (type) {
                case "all":
                    matches = true;
                    break;
                case "ma_ncc": {
                    int ma_ncc = Integer.parseInt(searchText);
                    matches = ncc.getMa_ncc() == ma_ncc;
                    break;
                }

                case "ten_ncc":
                    matches = ncc.getTen_ncc().toLowerCase().contains(searchText.toLowerCase());
                    break;
                case "diachi":
                    matches = ncc.getDia_chi().toLowerCase().contains(searchText.toLowerCase());
                    break;
                case "email":
                    matches = ncc.getEmail().toLowerCase().contains(searchText.toLowerCase());
                    break;
                case "sdt": {
                    matches = ncc.getSdt().toLowerCase().contains(searchText.toLowerCase());
                    break;
                }
            }

            if (matches) {
                listFound.add(ncc);
            }

            if (listFound.isEmpty()) {
                model.addAttribute("message", "Không tìm thấy nhà cung cấp nào!");
                model.addAttribute("type", "error");
            } else {
                model.addAttribute("data", listFound);
            }

        }
        return "NhaCungCapView";
    }}