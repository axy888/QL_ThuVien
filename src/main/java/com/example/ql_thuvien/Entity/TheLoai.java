package com.example.ql_thuvien.Entity;

import jakarta.persistence.*;

import java.util.List;

@Entity(
        name = "TheLoai"
)
@Table(
        name = "theloai"
)
public class TheLoai {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private int ma_loai;
    private String ten_loai;
    @OneToMany(mappedBy = "theLoai", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Sach> saches;

    public TheLoai(String tenLoai) {
        ten_loai = tenLoai;
    }

    public TheLoai() {

    }

    public int getMa_loai() {
        return ma_loai;
    }

    public void setMa_loai(int ma_loai) {
        this.ma_loai = ma_loai;
    }

    public String getTen_loai() {
        return ten_loai;
    }

    public void setTen_loai(String ten_loai) {
        this.ten_loai = ten_loai;
    }

    public List<Sach> getSaches() {
        return saches;
    }

    public void setSaches(List<Sach> saches) {
        this.saches = saches;
    }
}
