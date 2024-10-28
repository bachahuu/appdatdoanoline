package com.example.datdoanonline.Domain;

public class Menu_Food_Domain {
    int id;
    String tenmonan;
    String mota;
    double gia;
    String danhmuc;
    int nangluong;
    int time;
    String img;
    int sl;

    public Menu_Food_Domain(int id, String danhmuc, int time, int sl, String mota, String img, double gia, int nangluong, String tenmonan) {
        this.id = id;
        this.danhmuc = danhmuc;
        this.time = time;
        this.sl = sl;
        this.mota = mota;
        this.img = img;
        this.gia = gia;
        this.nangluong = nangluong;
        this.tenmonan = tenmonan;
    }

    public String getDanhmuc() {
        return danhmuc;
    }

    public void setDanhmuc(String danhmuc) {
        this.danhmuc = danhmuc;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getTenmonan() {
        return tenmonan;
    }

    public void setTenmonan(String tenmonan) {
        this.tenmonan = tenmonan;
    }

    public int getSl() {
        return sl;
    }

    public void setSl(int sl) {
        this.sl = sl;
    }

    public int getNangluong() {
        return nangluong;
    }

    public void setNangluong(int nangluong) {
        this.nangluong = nangluong;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
