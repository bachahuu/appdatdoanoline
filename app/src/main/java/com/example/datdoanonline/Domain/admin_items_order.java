package com.example.datdoanonline.Domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class admin_items_order {
    private int maDonHang;
    private int maNguoiDung;
    private Date ngaydathang;
    private double tongTien;
    private String trangThai;
    private String maGiamGia;
    private String diachigiaohang;
    private String thongtinlienlac;

    public admin_items_order(int maDonHang, int maNguoiDung, Date ngaydathang, double tongTien, String trangThai, String maGiamGia , String diachigiaohang , String thongtinlienlac ) {
        this.maDonHang = maDonHang;
        this.maNguoiDung = maNguoiDung;
        this.ngaydathang = ngaydathang;
        this.tongTien = tongTien;
        this.trangThai = trangThai;
        this.maGiamGia = maGiamGia;
        this.diachigiaohang = diachigiaohang;
        this.thongtinlienlac = thongtinlienlac;
    }

    public String getDiachigiaohang() {
        return diachigiaohang;
    }

    public String getThongtinlienlac() {
        return thongtinlienlac;
    }

    public int getMaDonHang() {
        return maDonHang;
    }

    public int getMaNguoiDung() {
        return maNguoiDung;
    }

    public Date getNgaydathang() {
        return ngaydathang;
    }

    public double getTongTien() {
        return tongTien;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public String getMaGiamGia() {
        return maGiamGia;
    }

    public void setMaDonHang(int maDonHang) {
        this.maDonHang = maDonHang;
    }

    public void setMaNguoiDung(int maNguoiDung) {
        this.maNguoiDung = maNguoiDung;
    }

    public void setNgaydathang(Date ngaydathang) {
        this.ngaydathang = ngaydathang;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public void setDiachigiaohang(String diachigiaohang) {
        this.diachigiaohang = diachigiaohang;
    }

    public void setThongtinlienlac(String thongtinlienlac) {
        this.thongtinlienlac = thongtinlienlac;
    }

    public void setMaGiamGia(String maGiamGia) {
        this.maGiamGia = maGiamGia;
    }

    @Override
    public String toString() {
        return maDonHang + maNguoiDung + new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(ngaydathang) + tongTien + trangThai + maGiamGia;
    }
}
