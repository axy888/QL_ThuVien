package com.example.ql_thuvien.Entity;

import jakarta.persistence.*;

import java.util.List;

@Entity(
        name = "PhieuNhap"
)
@Table(
        name = "phieunhap"
)
public class PhieuNhap {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private int ma_phieunhap;
    @ManyToOne
    @JoinColumn(name = "ma_ncc", referencedColumnName = "ma_ncc", insertable = false, updatable = false)
    private NhaCungCap ncc;
    private String ngay_nhap;
    private String nguoi_nhap;
    private int tong_tien;
    private int trang_thai;

    @OneToMany(mappedBy = "Phieunhap", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CT_PhieuNhap> chiTietPhieuNhap;

    public PhieuNhap(){}
    public  PhieuNhap(NhaCungCap ncc,String ngay_nhap,String nguoi_nhap,int tong_tien,int trang_thai)
    {
        this.ncc=ncc;
        this.ngay_nhap=ngay_nhap;
        this.nguoi_nhap=nguoi_nhap;
        this.tong_tien=tong_tien;
        this.trang_thai=trang_thai;
    }

    public int getMa_phieunhap() {
        return ma_phieunhap;
    }

    public void setMa_phieunhap(int ma_phieunhap) {
        this.ma_phieunhap = ma_phieunhap;
    }



    public String getNgay_nhap() {
        return ngay_nhap;
    }

    public void setNgay_nhap(String ngay_nhap) {
        this.ngay_nhap = ngay_nhap;
    }

    public String getNguoi_nhap() {
        return nguoi_nhap;
    }

    public void setNguoi_nhap(String nguoi_nhap) {
        this.nguoi_nhap = nguoi_nhap;
    }

    public int getTong_tien() {
        return tong_tien;
    }

    public void setTong_tien(int tong_tien) {
        this.tong_tien = tong_tien;
    }

    public int getTrang_thai() {
        return trang_thai;
    }

    public void setTrang_thai(int trang_thai) {
        this.trang_thai = trang_thai;
    }

    public NhaCungCap getNcc() {
        return ncc;
    }

    public void setNcc(NhaCungCap ncc) {
        this.ncc = ncc;
    }

    public List<CT_PhieuNhap> getChiTietPhieuNhap() {
        return chiTietPhieuNhap;
    }

    public void setChiTietPhieuNhap(List<CT_PhieuNhap> chiTietPhieuNhap) {
        this.chiTietPhieuNhap = chiTietPhieuNhap;
    }

    public void addCT_PhieuNhap(CT_PhieuNhap ctPhieuNhap) {
        chiTietPhieuNhap.add(ctPhieuNhap);
        ctPhieuNhap.setPhieunhap(this);
    }

    public void removeCT_PhieuNhap(CT_PhieuNhap ctPhieuNhap) {
        chiTietPhieuNhap.remove(ctPhieuNhap);
        ctPhieuNhap.setPhieunhap(null);
    }
}
