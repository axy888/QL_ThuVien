package com.example.ql_thuvien.Entity;

import jakarta.persistence.*;

@Entity(
        name = "PhieuPhat"
)
@Table(
        name = "phieuphat"
)

public class PhieuPhat {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private int ma_phieuphat;
    private int ma_phieumuon;
    private  int ma_taikhoan;
    private int ma_sach;
    private String ten_nv;
    private String ngay_tao;
    private int tien_phat;
    private String mo_ta;
    private int trang_thai;
    public PhieuPhat(){}

    public PhieuPhat(int ma_phieumuon,int ma_taikhoan,int ma_sach,String ten_nv,
                     String ngay_tao,int tien_phat,String mo_ta,int trang_thai)
    {
        this.ma_phieumuon = ma_phieumuon;
        this.ma_taikhoan=ma_taikhoan;
        this.ma_sach = ma_sach;
        this.ten_nv = ten_nv;
        this.ngay_tao = ngay_tao;
        this.tien_phat = tien_phat;
        this.mo_ta = mo_ta;
        this.trang_thai = trang_thai;
    }

    public int getMa_phieuphat() {
        return ma_phieuphat;
    }

    public void setMa_phieuphat(int ma_phieuphat) {
        this.ma_phieuphat = ma_phieuphat;
    }

    public int getMa_phieumuon() {
        return ma_phieumuon;
    }

    public void setMa_phieumuon(int ma_phieumuon) {
        this.ma_phieumuon = ma_phieumuon;
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

    public String getNgay_tao() {
        return ngay_tao;
    }

    public void setNgay_tao(String ngay_tao) {
        this.ngay_tao = ngay_tao;
    }

    public int getTien_phat() {
        return tien_phat;
    }

    public void setTien_phat(int tien_phat) {
        this.tien_phat = tien_phat;
    }

    public String getMo_ta() {
        return mo_ta;
    }

    public void setMo_ta(String mo_ta) {
        this.mo_ta = mo_ta;
    }

    public int getTrang_thai() {
        return trang_thai;
    }

    public void setTrang_thai(int trang_thai) {
        this.trang_thai = trang_thai;
    }

    public int getMa_taikhoan() {
        return ma_taikhoan;
    }

    public void setMa_taikhoan(int ma_taikhoan) {
        this.ma_taikhoan = ma_taikhoan;
    }
}
