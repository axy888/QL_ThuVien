package com.example.ql_thuvien.Controller;

import com.example.ql_thuvien.Entity.TaiKhoan;
import com.example.ql_thuvien.Repositories.TaiKhoanReposiroty;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class LoginController {

    private static final String PHONE_REGEX = "^[0-9]{10}$";
    private static final Pattern PHONE_PATTERN = Pattern.compile(PHONE_REGEX);
    LocalDate currentDate = LocalDate.now();

    // Format the date as a string (e.g., "dd/MM/yyyy")
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    @Autowired
    private TaiKhoanReposiroty taikhoanRe;
    @RequestMapping("/login")
    public String toLogin(){return "login";}

    @RequestMapping("/logout")
    public String toLogout(HttpSession session){
        session.invalidate();
        return "login";}

    @RequestMapping("/signup")
    public String toSignup(){return "signup";}

    @PostMapping("checksignup")
    public String checksignup(Model m,
                              @RequestParam("taikhoan") String taikhoan,
                              @RequestParam("matkhau") String matkhau,
                              @RequestParam("nhaplaimatkhau") String nhaplaimatkhau,
                              @RequestParam("name") String name,
                              @RequestParam("sdt") String sdt,
                              @RequestParam("diachi") String diachi,
                              @RequestParam("hinh") String hinh){
        if(taikhoan.trim().isEmpty() ||matkhau.trim().isEmpty() ||nhaplaimatkhau.trim().isEmpty() ||
                name.trim().isEmpty() ||sdt.trim().isEmpty() ||diachi.trim().isEmpty())
        {
            m.addAttribute("message","Không được để trống thông tin!!");
            m.addAttribute("type", "error");
            return "signup";
        }

        if(matkhau.length()<6)
        {
            m.addAttribute("message","Mật khẩu phải có ít nhất 6 ký tự!!");
            m.addAttribute("type", "error");
            return "signup";
        }

        if(!matkhau.equals(nhaplaimatkhau))
        {
            m.addAttribute("message","Mật khẩu không trùng khớp với mật khẩu nhập lại!!");
            m.addAttribute("type", "error");
            return "signup";
        }

        if(name.length()<=3)
        {
            m.addAttribute("message","Họ và tên phải có ít nhất 4 ký tự!!");
            m.addAttribute("type", "error");
            return "signup";
        }

        if(!isValidPhoneNumber(sdt))
        {
            m.addAttribute("message","Không đúng định dạng số điện thoại!");
            m.addAttribute("type", "error");
            return "signup";
        }
        else
        {
            Iterable<TaiKhoan>list = taikhoanRe.findAll();
            for(TaiKhoan tk: list)
            {
                if(tk.getEmail().equals(taikhoan))
                {
                    m.addAttribute("message","Email đã được sử dụng!!");
                    m.addAttribute("type", "error");
                    return "signup";
                }
            }
            // Hash the password before saving
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(matkhau);
            String ngay_tao=currentDate.format(formatter);
            TaiKhoan tk=new TaiKhoan(2,taikhoan,hashedPassword,name,sdt,diachi,ngay_tao,1,hinh);
            taikhoanRe.save(tk);
            m.addAttribute("message","Đăng ký thành công!!");
            m.addAttribute("type", "success");
            return "login";
        }

    }

    @PostMapping("checklogin")
    public String checklogin(HttpSession session,
                             Model m,
                             @RequestParam("taikhoan") String taikhoan,
                             @RequestParam("matkhau") String matkhau)
    {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if(taikhoan.trim().isEmpty() ||matkhau.trim().isEmpty())
        {
            m.addAttribute("message","Không được dể trống thông tin!!");
            m.addAttribute("type", "error");
            return "login";
        }
        else
        {
            Iterable<TaiKhoan>list = taikhoanRe.findAll();
            for(TaiKhoan tk : list)
            {
                if(tk.getEmail().equals(taikhoan) && passwordEncoder.matches(matkhau, tk.getPassword()))
                {
                    if(tk.getMa_quyen()==1)
                    {
                        session.setAttribute("username", tk.getHoten());
                        session.setAttribute("ma_taikhoan", tk.getMa_taikhoan());
                        m.addAttribute("type", "success");
                        return "admin";
                    }
                    if(tk.getMa_quyen()==2)
                    {
                        session.setAttribute("username", tk.getHoten());
                        session.setAttribute("ma_taikhoan", tk.getMa_taikhoan());
                        session.setAttribute("temp_password", matkhau);
                        m.addAttribute("type", "success");
                        return "redirect:/";
                    }
                }
            }
        }
        m.addAttribute("message","Tài khoản hoặc mật khẩu không đúng!!");
        m.addAttribute("type", "error");
        return "login";
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        Matcher matcher = PHONE_PATTERN.matcher(phoneNumber);
        return matcher.matches();
    }
}
