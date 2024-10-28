package com.example.datdoanonline.Domain;

public class maGiamGiaDomain {
    private int id; // id sẽ tự động tăng, nên không cần truyền từ bên ngoài
    private String maGiamGia;
    private String moTa;
    private double phanTramGiamGia;
    private String ngayBatDau;
    private String ngayKetThuc;
    private int soLuongDaSuDung;
    private int soLuongToiDa;
    private String trangThai;

    // Constructor không có id, id sẽ tự động được tăng trong DB
    public maGiamGiaDomain(String maGiamGia, String moTa, double phanTramGiamGia, String ngayBatDau, String ngayKetThuc, int soLuongToiDa, int soLuongDaSuDung, String trangThai) {
        this.maGiamGia = maGiamGia;
        this.moTa = moTa;
        this.phanTramGiamGia = phanTramGiamGia;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.soLuongToiDa = soLuongToiDa;
        this.soLuongDaSuDung = soLuongDaSuDung;
        this.trangThai = trangThai;
    }

    // Getters và setters cho các trường khác
    public String getMaGiamGia() {
        return maGiamGia;
    }

    public String getMoTa() {
        return moTa;
    }

    public double getPhanTramGiamGia() {
        return phanTramGiamGia;
    }

    public String getNgayBatDau() {
        return ngayBatDau;
    }

    public int getId() {
        return id;
    }

    public String getNgayKetThuc() {
        return ngayKetThuc;
    }

    public int getSoLuongDaSuDung() {
        return soLuongDaSuDung;
    }

    public int getSoLuongToiDa() {
        return soLuongToiDa;
    }

    public String getTrangThai() {
        return trangThai;
    }
}