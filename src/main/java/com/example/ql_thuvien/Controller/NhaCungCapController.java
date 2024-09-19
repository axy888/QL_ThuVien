package com.example.ql_thuvien.Controller;

import com.example.ql_thuvien.Entity.NhaCungCap;
import com.example.ql_thuvien.Entity.Sach;
import com.example.ql_thuvien.Repositories.NhaCungCapRepository;
import com.example.ql_thuvien.Repositories.TheLoaiRepository;
import jakarta.servlet.http.HttpServletRequest;
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



        }
        if (listFound.isEmpty()) {
            model.addAttribute("message", "Không tìm thấy nhà cung cấp nào!");
            model.addAttribute("type", "error");
        } else {
            model.addAttribute("data", listFound);
        }
        return "NhaCungCapView";
    }

    @GetMapping("/QLNcc/sort")
    public String sortSupplier(@RequestParam(defaultValue = "ten_ncc") String sortField,
                            @RequestParam(defaultValue = "asc") String sortDir, Model m) {

        ArrayListNcc = (ArrayList<NhaCungCap>)listNhaCungCap;
        listNhaCungCap=sortList(ArrayListNcc,sortField,sortDir);
        m.addAttribute("data", listNhaCungCap);
        m.addAttribute("sortField",sortField);
        m.addAttribute("sortDir",sortDir);

        return "NhaCungCapView";
    }

    private ArrayList<NhaCungCap> sortList(ArrayList<NhaCungCap> listNcc, String sortField, String sortDir) {
        Comparator<NhaCungCap> comparator = null;
        Collator collator = Collator.getInstance(new Locale("vi", "VN"));
        collator.setStrength(Collator.TERTIARY);

        comparator = Comparator.comparing(NhaCungCap::getTen_ncc, collator);
        if (sortDir.equals("desc")) {
            comparator = comparator.reversed();
        }

        // Sort the list
        return listNcc.stream()
                .sorted(comparator)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @RequestMapping(value = "addSupplier")
    public String showAddForm(Model model) {
        NhaCungCap ncc = new NhaCungCap();
        model.addAttribute("nhacungcap", ncc);
        return "crud/addSupplier";
    }

    @PostMapping("/saveSupplier")
    public String save(@ModelAttribute("nhacungcap") NhaCungCap ncc, Model model) {
        String ten_ncc = ncc.getTen_ncc();
        String dia_chi = ncc.getDia_chi();
        String sdt = ncc.getSdt();
        String email = ncc.getEmail();

        NhaCungCap nccNew = new NhaCungCap(ten_ncc,dia_chi,sdt,email);
        nccRe.save(nccNew);

        listNhaCungCap = nccRe.findAll();
        model.addAttribute("data", listNhaCungCap);

        return "redirect:/QLNcc";
    }

    @GetMapping(value = {"QLNcc/edit/{id}"})
    public String showEditForm(@PathVariable("id") int id, Model model) {
        NhaCungCap ncc = ArrayListNcc.get(id);
        model.addAttribute("nhacungcap", ncc);
        return "crud/editSupplier";
    }

    @PostMapping("/updateSupplier")
    public String updateTV(@ModelAttribute("nhacungcap") NhaCungCap nccNew) {
        Optional<NhaCungCap> optionalNcc = nccRe.findById((long)nccNew.getMa_ncc());

        if (optionalNcc.isPresent()) {
            NhaCungCap ncc = optionalNcc.get();

            ncc.setTen_ncc(nccNew.getTen_ncc());
            ncc.setDia_chi(nccNew.getDia_chi());
            ncc.setSdt(nccNew.getSdt());
            ncc.setEmail(nccNew.getEmail());
            nccRe.save(ncc);
        }

        return "redirect:/QLNcc";
    }

}