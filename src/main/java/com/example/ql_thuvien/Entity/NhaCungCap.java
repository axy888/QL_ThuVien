package com.example.ql_thuvien.Entity;

import jakarta.persistence.*;

import java.util.List;

@Entity(
        name = "NhaCungCap"
)
@Table(
        name = "nhacungcap"
)
public class NhaCungCap {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private int ma_ncc;
    private String ten_ncc;
    private String dia_chi;
    private String sdt;
    private String email;

    @OneToMany(mappedBy = "ncc", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PhieuNhap> Phieunhap;

    public NhaCungCap(){}

    public NhaCungCap(String ten_ncc, String dia_chi, String sdt, String email)
    {
        this.ten_ncc = ten_ncc;
        this.dia_chi = dia_chi;
        this.sdt = sdt;
        this.email = email;
    }

    public int getMa_ncc() {
        return ma_ncc;
    }

    public void setMa_ncc(int ma_ncc) {
        this.ma_ncc = ma_ncc;
    }

    public String getTen_ncc() {
        return ten_ncc;
    }

    public void setTen_ncc(String ten_ncc) {
        this.ten_ncc = ten_ncc;
    }

    public String getDia_chi() {
        return dia_chi;
    }

    public void setDia_chi(String dia_chi) {
        this.dia_chi = dia_chi;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<PhieuNhap> getPhieunhap() {
        return Phieunhap;
    }

    public void setPhieunhap(List<PhieuNhap> phieunhap) {
        Phieunhap = phieunhap;
    }

    public void addPhieuNhap(PhieuNhap pn) {
        Phieunhap.add(pn);
        pn.setNcc(this);
    }

    public void removeCT_PhieuNhap(PhieuNhap pn) {
        Phieunhap.remove(pn);
        pn.setNcc(this);
    }
}
