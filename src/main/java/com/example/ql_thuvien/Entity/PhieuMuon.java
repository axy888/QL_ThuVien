package com.example.ql_thuvien.Entity;


import jakarta.persistence.*;

@Entity(
        name = "PhieuMuon"
)
@Table(
        name = "phieumuon"
)
public class PhieuMuon {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private int ma_phieumuon;
    private int ma_taikhoan;
    private int ma_sach;
    private String ten_nv;
    private String ngay_muon;
    private String ngay_han;
    private String ngay_tra;
    private int trang_thai;

    public PhieuMuon(){}

    public PhieuMuon(int ma_taikhoan,int ma_sach,String ten_nv,String ngay_muon,String ngay_han,String ngay_tra,int trang_thai)
    {
        this.ma_taikhoan=ma_taikhoan;
        this.ma_sach=ma_sach;
        this.ten_nv=ten_nv;
        this.ngay_muon=ngay_muon;
        this.ngay_han=ngay_han;
        this.ngay_tra=ngay_tra;
        this.trang_thai=trang_thai;
    }

    public int getMa_phieumuon() {
        return ma_phieumuon;
    }

    public void setMa_phieumuon(int ma_phieumuon) {
        this.ma_phieumuon = ma_phieumuon;
    }

    public int getMa_taikhoan() {
        return ma_taikhoan;
    }

    public void setMa_taikhoan(int ma_taikhoan) {
        this.ma_taikhoan = ma_taikhoan;
    }

    public int getMa_sach() {
        return ma_sach;
    }

    public void setMa_sach(int ma_sach) {
        this.ma_sach = ma_sach;
    }

    public String getTen_nv() {
        return ten_nv;
    }

    public void setTen_nv(String ten_nv) {
        this.ten_nv = ten_nv;
    }

    public String getNgay_muon() {
        return ngay_muon;
    }

    public void setNgay_muon(String ngay_muon) {
        this.ngay_muon = ngay_muon;
    }

    public String getNgay_han() {
        return ngay_han;
    }

    public void setNgay_han(String ngay_han) {
        this.ngay_han = ngay_han;
    }

    public String getNgay_tra() {
        return ngay_tra;
    }

    public void setNgay_tra(String ngay_tra) {
        this.ngay_tra = ngay_tra;
    }

    public int getTrang_thai() {
        return trang_thai;
    }

    public void setTrang_thai(int trang_thai) {
        this.trang_thai = trang_thai;
    }
}
