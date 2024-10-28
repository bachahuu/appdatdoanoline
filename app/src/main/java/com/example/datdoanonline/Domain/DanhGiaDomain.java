package com.example.datdoanonline.Domain;

public class DanhGiaDomain {
    private int maDanhGia;
    private int maNguoiDung;
    private int maMonAn; // Chuyển từ String sang int
    private int diemDanhGia;
    private String nhanXet;
    private String ngayDanhGia;

    // Constructor đầy đủ
    public DanhGiaDomain(int maDanhGia, int maNguoiDung, int maMonAn, int diemDanhGia, String nhanXet, String ngayDanhGia) {
        this.maDanhGia = maDanhGia;
        this.maNguoiDung = maNguoiDung;
        this.maMonAn = maMonAn; // Khởi tạo mã món ăn
        this.diemDanhGia = diemDanhGia;
        this.nhanXet = nhanXet;
        this.ngayDanhGia = ngayDanhGia;
    }

    // Getters và Setters
    public int getMaDanhGia() {
        return maDanhGia;
    }

    public void setMaDanhGia(int maDanhGia) {
        this.maDanhGia = maDanhGia;
    }

    public int getMaNguoiDung() {
        return maNguoiDung;
    }

    public void setMaNguoiDung(int maNguoiDung) {
        this.maNguoiDung = maNguoiDung;
    }

    public int getMaMonAn() {
        return maMonAn; // Chuyển từ String sang int
    }

    public void setMaMonAn(int maMonAn) {
        this.maMonAn = maMonAn; // Thiết lập mã món ăn
    }

    public int getDiemDanhGia() {
        return diemDanhGia;
    }

    public void setDiemDanhGia(int diemDanhGia) {
        this.diemDanhGia = diemDanhGia;
    }

    public String getNhanXet() {
        return nhanXet;
    }

    public void setNhanXet(String nhanXet) {
        this.nhanXet = nhanXet;
    }

    public String getNgayDanhGia() {
        return ngayDanhGia;
    }

    public void setNgayDanhGia(String ngayDanhGia) {
        this.ngayDanhGia = ngayDanhGia;
    }
}
