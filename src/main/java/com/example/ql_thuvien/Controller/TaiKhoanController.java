package com.example.ql_thuvien.Controller;

import com.example.ql_thuvien.Entity.NhaCungCap;
import com.example.ql_thuvien.Entity.TaiKhoan;
import com.example.ql_thuvien.Repositories.NhaCungCapRepository;
import com.example.ql_thuvien.Repositories.QuyenReposiroty;
import com.example.ql_thuvien.Repositories.TaiKhoanReposiroty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
public class TaiKhoanController {

    @Autowired
    private TaiKhoanReposiroty taikhoanRe;
    Iterable<TaiKhoan> listTaiKhoan;
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
        ArrayListTaikhoan = (ArrayList<TaiKhoan>)listTaiKhoan;
        m.addAttribute("data",listTaiKhoan);
        return "TaiKhoanView";
    }
}
