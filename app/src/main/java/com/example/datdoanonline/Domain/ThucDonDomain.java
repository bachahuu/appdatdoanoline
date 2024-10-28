package com.example.datdoanonline.Domain;

public class ThucDonDomain {
    private int maMonAn;
    private String tenMonAn;
    private String moTa;
    private double gia;
    private String danhMuc;
    private double saoDanhGia;
    private int nangLuong;
    private int thoiGianLam;
    private String anhMinhHoa;
    private int soLuong;

    // Constructor của ThucDonDomain
    public ThucDonDomain(int maMonAn,String tenMonAn, String moTa, double gia, String danhMuc, double saoDanhGia, int nangLuong, int thoiGianLam, String anhMinhHoa, int soLuong) {
        this.maMonAn = maMonAn;
        this.tenMonAn = tenMonAn;
        this.moTa = moTa;
        this.gia = gia;
        this.danhMuc = danhMuc;
        this.saoDanhGia = saoDanhGia;
        this.nangLuong = nangLuong;
        this.thoiGianLam = thoiGianLam;
        this.anhMinhHoa = anhMinhHoa;
        this.soLuong = soLuong;
    }
    public int getMaMonAn() {
        return maMonAn;
    }

    public String getTenMonAn() {
        return tenMonAn;
    }

    public String getMoTa() {
        return moTa;
    }

    public double getGia() {
        return gia;
    }

    public String getDanhMuc() {
        return danhMuc;
    }

    public double getSaoDanhGia() {
        return saoDanhGia;
    }

    public int getNangLuong() {
        return nangLuong;
    }

    public int getThoiGianLam() {
        return thoiGianLam;
    }

    public String getAnhMinhHoa() {
        return anhMinhHoa;
    }

    public int getSoLuong() {
        return soLuong;
    }

    // Các getter và setter

    public void setTenMonAn(String tenMonAn) {
        this.tenMonAn = tenMonAn;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public void setDanhMuc(String danhMuc) {
        this.danhMuc = danhMuc;
    }

    public void setSaoDanhGia(double saoDanhGia) {
        this.saoDanhGia = saoDanhGia;
    }

    public void setNangLuong(int nangLuong) {
        this.nangLuong = nangLuong;
    }

    public void setThoiGianLam(int thoiGianLam) {
        this.thoiGianLam = thoiGianLam;
    }

    public void setAnhMinhHoa(String anhMinhHoa) {
        this.anhMinhHoa = anhMinhHoa;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
