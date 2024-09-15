package com.example.ql_thuvien.Entity;

import java.io.Serializable;
import java.util.Objects;

public class CT_PhieuNhapId implements Serializable {
    private int ma_phieunhap;
    private int ma_sach;

    // Default constructor
    public CT_PhieuNhapId() {}

    // Constructor
    public CT_PhieuNhapId(int ma_phieunhap, int ma_sach) {
        this.ma_phieunhap = ma_phieunhap;
        this.ma_sach = ma_sach;
    }

    // Getters and Setters
    public int getMa_phieunhap() {
        return ma_phieunhap;
    }

    public void setMa_phieunhap(int ma_phieunhap) {
        this.ma_phieunhap = ma_phieunhap;
    }

    public int getMa_sach() {
        return ma_sach;
    }

    public void setMa_sach(int ma_sach) {
        this.ma_sach = ma_sach;
    }

    // Override equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CT_PhieuNhapId that = (CT_PhieuNhapId) o;
        return Objects.equals(ma_phieunhap, that.ma_phieunhap) &&
                Objects.equals(ma_sach, that.ma_sach);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ma_phieunhap, ma_sach);
    }
}
