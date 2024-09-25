package com.example.ql_thuvien.Entity;

import jakarta.persistence.*;

import java.util.List;

@Entity(
        name = "Quyen"
)
@Table(
        name = "quyen"
)
public class Quyen {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private int ma_quyen;
    private String ten_quyen;
    @OneToMany(mappedBy = "quyen", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TaiKhoan> taiKhoans;

    public Quyen() {}

    public Quyen(String ten_quyen) {
        this.ten_quyen = ten_quyen;
    }

    public int getMa_quyen() {
        return ma_quyen;
    }

    public void setMa_quyen(int ma_quyen) {
        this.ma_quyen = ma_quyen;
    }

    public String getTen_quyen() {
        return ten_quyen;
    }

    public void setTen_quyen(String ten_quyen) {
        this.ten_quyen = ten_quyen;
    }

    public List<TaiKhoan> getTaiKhoans() {
        return taiKhoans;
    }

    public void setTaiKhoans(List<TaiKhoan> taiKhoans) {
        this.taiKhoans = taiKhoans;
    }
}
