package com.example.ql_thuvien.Entity;

import jakarta.persistence.*;

@Entity(
        name = "TaiKhoan"
)
@Table(
        name = "taikhoan"
)
public class TaiKhoan {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private int ma_taikhoan;
    private int ma_quyen;
    private String email;
    private String password;
    private String hoten;
    private String sdt;
    private String diachi;
    private String ngay_tao;
    private int trang_thai;
    private String avatar;
    public TaiKhoan(){}
    public TaiKhoan(int ma_quyen,String email, String password,String hoten, String sdt, String diachi,String ngay_tao,int trang_thai,String avatar)
    {
        this.ma_quyen=ma_quyen;
        this.email=email;
        this.password=password;
        this.hoten=hoten;
        this.sdt=sdt;
        this.diachi=diachi;
        this.ngay_tao=ngay_tao;
        this.trang_thai=trang_thai;
        this.avatar=avatar;
    }

    public int getMa_taikhoan() {
        return ma_taikhoan;
    }

    public void setMa_taikhoan(int ma_taikhoan) {
        this.ma_taikhoan = ma_taikhoan;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public int getMa_quyen() {
        return ma_quyen;
    }

    public void setMa_quyen(int ma_quyen) {
        this.ma_quyen = ma_quyen;
    }

    public int getTrang_thai() {
        return trang_thai;
    }

    public void setTrang_thai(int trang_thai) {
        this.trang_thai = trang_thai;
    }

    public String getNgay_tao() {
        return ngay_tao;
    }

    public void setNgay_tao(String ngay_tao) {
        this.ngay_tao = ngay_tao;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
