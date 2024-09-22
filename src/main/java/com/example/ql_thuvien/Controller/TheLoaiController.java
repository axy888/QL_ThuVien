package com.example.ql_thuvien.Controller;

import com.example.ql_thuvien.Entity.NhaCungCap;
import com.example.ql_thuvien.Entity.TheLoai;
import com.example.ql_thuvien.Repositories.TheLoaiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class TheLoaiController {
    @Autowired
    private TheLoaiRepository theloaiRe;
    Iterable<TheLoai> listTheLoai;

    private static ArrayList<TheLoai> ArraylistTheLoai = new ArrayList();

    public TheLoaiController() {
    }

    @RequestMapping({"/QLTheLoai"})
    public String toTheLoai() {
        return "TheLoaiView";
    }

    @GetMapping({"/QLTheLoai"})
    public String getAll(Model m) {
        listTheLoai=this.theloaiRe.findAll();
        ArraylistTheLoai = (ArrayList<TheLoai>)listTheLoai;
        m.addAttribute("data",listTheLoai);
        return "TheLoaiView";
    }

    @PostMapping("/QLTheLoai/searchCategory")
    public String searchSupplier(
            @RequestParam("searchBy") String type,
            @RequestParam("searchText") String searchText,
            Model model) {

        ArrayList<TheLoai> listFound = new ArrayList<>();

        for (TheLoai tl : ArraylistTheLoai) {
            boolean matches = false;

            switch (type) {
                case "all":
                    matches = true;
                    break;
                case "ma_loai": {
                    int ma_loai = Integer.parseInt(searchText);
                    matches = tl.getMa_loai() == ma_loai;
                    break;
                }

                case "ten_loai":
                    matches = tl.getTen_loai().toLowerCase().contains(searchText.toLowerCase());
                    break;
            }

            if (matches) {
                listFound.add(tl);
            }



        }
        if (listFound.isEmpty()) {
            model.addAttribute("message", "Không tìm thấy thể loại nào!");
            model.addAttribute("type", "error");
        } else {
            model.addAttribute("data", listFound);
        }
        return "TheLoaiView";
    }

    @GetMapping("/QLTheLoai/sort")
    public String sortSupplier(@RequestParam(defaultValue = "ten_loai") String sortField,
                               @RequestParam(defaultValue = "asc") String sortDir, Model m) {

        ArraylistTheLoai = (ArrayList<TheLoai>)listTheLoai;
        listTheLoai=sortList(ArraylistTheLoai,sortField,sortDir);
        m.addAttribute("data", listTheLoai);
        m.addAttribute("sortField",sortField);
        m.addAttribute("sortDir",sortDir);

        return "TheLoaiView";
    }

    private ArrayList<TheLoai> sortList(ArrayList<TheLoai> listTl, String sortField, String sortDir) {
        Comparator<TheLoai> comparator = null;
        Collator collator = Collator.getInstance(new Locale("vi", "VN"));
        collator.setStrength(Collator.TERTIARY);

        comparator = Comparator.comparing(TheLoai::getTen_loai, collator);
        if (sortDir.equals("desc")) {
            comparator = comparator.reversed();
        }

        // Sort the list
        return listTl.stream()
                .sorted(comparator)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @RequestMapping(value = "addCategory")
    public String showAddForm(Model model) {
        TheLoai tl = new TheLoai();
        model.addAttribute("theloai", tl);
        return "crud/addCategory";
    }

    @PostMapping("/saveCategory")
    public String save(@ModelAttribute("theloai") TheLoai tl, Model model) {
        String ten_loai = tl.getTen_loai();


        TheLoai tlNew = new TheLoai(ten_loai);
        theloaiRe.save(tlNew);

        listTheLoai = theloaiRe.findAll();
        model.addAttribute("data", listTheLoai);

        return "redirect:/QLTheLoai";
    }

    @GetMapping(value = {"QLTheLoai/edit/{id}"})
    public String showEditForm(@PathVariable("id") int id, Model model) {
        TheLoai tl = ArraylistTheLoai.get(id);
        model.addAttribute("theloai", tl);
        return "crud/editCategory";
    }

    @PostMapping("/updateCategory")
    public String updateTV(@ModelAttribute("theloai") TheLoai tlNew) {
        Optional<TheLoai> optionalTl = theloaiRe.findById((long)tlNew.getMa_loai());

        if (optionalTl.isPresent()) {
            TheLoai tl = optionalTl.get();

            tl.setTen_loai(tlNew.getTen_loai());
            theloaiRe.save(tl);
        }

        return "redirect:/QLTheLoai";
    }

}
