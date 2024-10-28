package com.example.datdoanonline.Domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class items_order {
    String madonhang;
    Date ngaydathang;
    String trangthai;
    String tongtien;

    public items_order(String madonhang, Date ngaydathang, String trangthai, String tongtien) {
        this.madonhang = madonhang;
        this.ngaydathang = ngaydathang;
        this.trangthai = trangthai;
        this.tongtien = tongtien;
    }

    public String getTongtien() {
        return tongtien;
    }

    public void setTongtien(String tongtien) {
        this.tongtien = tongtien;
    }

    public String getMadonhang() {
        return madonhang;
    }

    public Date getNgaydathang() {
        return ngaydathang;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setMadonhang(String madonhang) {
        this.madonhang = madonhang;
    }

    public void setNgaydathang(Date ngaydathang) {
        this.ngaydathang = ngaydathang;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }

    @Override
    public String toString() {
        return madonhang + new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(ngaydathang)  + trangthai ;
    }
}
