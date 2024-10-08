package com.example.ql_thuvien.Entity;

import jakarta.persistence.*;

@Entity(
        name = "Sach"
)
@Table(
        name = "sach"
)
public class Sach {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private int ma_sach;
    @ManyToOne
    @JoinColumn(name = "ma_loai", referencedColumnName = "ma_loai")
    private TheLoai theLoai;

    @ManyToOne
    @JoinColumn(name = "ma_ncc", referencedColumnName = "ma_ncc")
    private NhaCungCap nhaCungCap;
    private String ten_sach;
    private String tac_gia;
    private String mo_ta;
    private String NXB;
    private String ngay_xuat_ban;
    private int so_luong;
    private int gia;
    private String hinh;

    public Sach(TheLoai theLoai, NhaCungCap nhaCungCap,String ten_sach, String tac_gia, String mo_ta,String NXB, String ngay_xuat_ban, int so_luong,int gia,String hinh) {
        this.theLoai = theLoai;
        this.nhaCungCap = nhaCungCap;
        this.ten_sach = ten_sach;
        this.tac_gia = tac_gia;
        this.mo_ta=mo_ta;
        this.NXB = NXB;
        this.ngay_xuat_ban = ngay_xuat_ban;
        this.so_luong = so_luong;
        this.gia=gia;
        this.hinh=hinh;
    }

    public Sach() {

    }

    public int getMa_sach() {
        return ma_sach;
    }

    public void setMa_sach(int ma_sach) {
        this.ma_sach = ma_sach;
    }


    public String getTen_sach() {
        return ten_sach;
    }

    public void setTen_sach(String ten_sach) {
        this.ten_sach = ten_sach;
    }

    public String getTac_gia() {
        return tac_gia;
    }

    public void setTac_gia(String tac_gia) {
        this.tac_gia = tac_gia;
    }

    public String getNXB() {
        return NXB;
    }

    public void setNXB(String NXB) {
        this.NXB = NXB;
    }

    public String getNgay_xuat_ban() {
        return ngay_xuat_ban;
    }

    public void setNgay_xuat_ban(String ngay_xuat_ban) {
        this.ngay_xuat_ban = ngay_xuat_ban;
    }

    public int getSo_luong() {
        return so_luong;
    }

    public void setSo_luong(int so_luong) {
        this.so_luong = so_luong;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    public String getMo_ta() {
        return mo_ta;
    }

    public void setMo_ta(String mo_ta) {
        this.mo_ta = mo_ta;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public TheLoai getTheLoai() {
        return theLoai;
    }

    public void setTheLoai(TheLoai theLoai) {
        this.theLoai = theLoai;
    }

    public NhaCungCap getNhaCungCap() {
        return nhaCungCap;
    }

    public void setNhaCungCap(NhaCungCap nhaCungCap) {
        this.nhaCungCap = nhaCungCap;
    }
}
