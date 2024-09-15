package com.example.ql_thuvien.Entity;

import jakarta.persistence.*;

@Entity
@IdClass(CT_PhieuNhapId.class)
@Table(
        name = "ct_phieunhap"
)
public class CT_PhieuNhap {
    @Id
    @Column(name = "ma_phieunhap", insertable = false, updatable = false)
    private int ma_phieunhap;  // Add field matching IdClass

    @Id
    @Column(name = "ma_sach", insertable = false, updatable = false)
    private int ma_sach;

    @ManyToOne
    @JoinColumn(name = "ma_phieunhap", referencedColumnName = "ma_phieunhap",insertable = false, updatable = false)
    private PhieuNhap Phieunhap;

    @ManyToOne
    @JoinColumn(name = "ma_sach", referencedColumnName = "ma_sach",insertable = false, updatable = false)
    private Sach sa;

    private int so_luong;
    private int don_gia;
    public CT_PhieuNhap(){}
    public CT_PhieuNhap(int ma_phieunhap, int ma_sach,int so_luong, int don_gia)
    {
        this.ma_phieunhap = ma_phieunhap;
        this.ma_sach = ma_sach;
        this.so_luong = so_luong;
        this.don_gia = don_gia;
    }

    public PhieuNhap getPhieunhap() {
        return Phieunhap;
    }

    public void setPhieunhap(PhieuNhap phieunhap) {
        Phieunhap = phieunhap;
    }

    public int getSo_luong() {
        return so_luong;
    }

    public void setSo_luong(int so_luong) {
        this.so_luong = so_luong;
    }

    public int getDon_gia() {
        return don_gia;
    }

    public void setDon_gia(int don_gia) {
        this.don_gia = don_gia;
    }

    public Sach getSa() {
        return sa;
    }

    public void setSa(Sach sa) {
        this.sa = sa;
    }

    public int getMa_sach() {
        return ma_sach;
    }

    public void setMa_sach(int ma_sach) {
        this.ma_sach = ma_sach;
    }

    public int getMa_phieunhap() {
        return ma_phieunhap;
    }

    public void setMa_phieunhap(int ma_phieunhap) {
        this.ma_phieunhap = ma_phieunhap;
    }
}
