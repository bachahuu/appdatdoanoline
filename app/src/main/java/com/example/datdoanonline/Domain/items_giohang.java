package com.example.datdoanonline.Domain;

public class items_giohang {
    private String tenSanPham;  // Tên sản phẩm
    private String hinhAnhSanPham;  // Hình ảnh sản phẩm (dưới dạng URI)
    private int giaSanPham;  // Giá sản phẩm
    private int soLuong;  // Số lượng sản phẩm
    private int maMonAn;  // Mã món ăn

    public items_giohang(String tenSanPham, String hinhAnhSanPham, int giaSanPham, int soLuong, int maMonAn) {
        this.tenSanPham = tenSanPham;
        this.hinhAnhSanPham = hinhAnhSanPham;  // Gán URI hình ảnh
        this.giaSanPham = giaSanPham;
        this.soLuong = soLuong;
        this.maMonAn = maMonAn;  // Gán mã món ăn
    }

    public int getMaMonAn() {
        return maMonAn;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public String getHinhAnhSanPham() {
        return hinhAnhSanPham;  // Trả về URI hình ảnh
    }

    public int getGiaSanPham() {
        return giaSanPham;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public void setHinhAnhSanPham(String hinhAnhSanPham) {
        this.hinhAnhSanPham = hinhAnhSanPham;  // Thiết lập URI hình ảnh
    }

    public void setGiaSanPham(int giaSanPham) {
        this.giaSanPham = giaSanPham;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    @Override
    public String toString() {
        return tenSanPham + " " + hinhAnhSanPham + " " + giaSanPham + " " + soLuong;
    }
}
