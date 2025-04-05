package com.example.datdoanonline.Domain;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.datdoanonline.Adapter.Menu_Food_Adapter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "RestaurantDB";
    private static final int DATABASE_VERSION = 21;

    // Bảng NguoiDung
    private static final String TABLE_NGUOIDUNG = "NguoiDung";
    private static final String COLUMN_MA_NGUOIDUNG = "MaNguoiDung";
    private static final String COLUMN_TEN_DANGNHAP = "TenDangNhap";
    private static final String COLUMN_EMAIL = "Email";
    private static final String COLUMN_MAT_KHAU = "MatKhau";
    private static final String COLUMN_HO_TEN = "HoTen";
    private static final String COLUMN_SO_DIEN_THOAI = "SoDienThoai";
    private static final String COLUMN_DIA_CHI = "DiaChi";
    private static final String COLUMN_PHAN_QUYEN = "PhanQuyen";


    // Bảng ThucDon
    private static final String TABLE_THUC_DON = "ThucDon";
    private static final String COLUMN_MA_MON_AN = "MaMonAn";
    private static final String COLUMN_TEN_MON_AN = "TenMonAn";
    private static final String COLUMN_MO_TA = "MoTa";
    private static final String COLUMN_GIA = "Gia";
    private static final String COLUMN_DANH_MUC = "DanhMuc";
    private static final String COLUMN_SAO_DANH_GIA = "SaoDanhGia";
    private static final String COLUMN_NANG_LUONG = "NangLuong";
    private static final String COLUMN_THOI_GIAN_LAM = "ThoiGianLam";
    private static final String COLUMN_ANH_MINH_HOA = "AnhMinhHoa";
    private static final String COLUMN_SO_LUONG = "SoLuong";

    //gio hang
    private static final String TABLE_GIO_HANG = "GioHang";
    private static final String COLUMN_MA_GIO_HANG = "MaGioHang";
    private static final String COLUMN_MA_MON_AN_GH = "MaMonAn";
    private static final String COLUMN_MA_NGUOIDUNG_GH = "MaNguoiDung";
    private static final String COLUMN_SO_LUONG_GH = "SoLuong";


    // Bảng DonHang
    private static final String TABLE_DON_HANG = "DonHang";
    private static final String COLUMN_MA_DON_HANG = "MaDonHang";
    private static final String COLUMN_MA_NGUOIDUNG_DH = "MaNguoiDung"; // Thêm _DH để rõ ràng hơn
    private static final String COLUMN_NGAY_DAT_HANG = "NgayDatHang";
    private static final String COLUMN_TONG_TIEN = "TongTien";
    private static final String COLUMN_TRANG_THAI = "TrangThai";
    private static final String COLUMN_MA_GIAM_GIA_DH = "MaGiamGia"; // Thêm _DH để rõ hơn
    private static final String COLUMN_DIA_CHI_GIAO_HANG = "DiaChiGiaoHang";
    private static final String COLUMN_THONG_TIN_LIEN_LAC = "ThongTinLienLac";

    // Bảng ChiTietDonHang
    private static final String TABLE_CHI_TIET_DON_HANG = "ChiTietDonHang";
    private static final String COLUMN_MA_CHI_TIET_DON_HANG = "MaChiTietDonHang";
    private static final String COLUMN_MA_DON_HANG_CTHD = "MaDonHang"; // Thêm _CTHD để rõ ràng hơn
    private static final String COLUMN_MA_MON_AN_CTHD = "MaMonAn"; // Sử dụng rõ ràng hơn

    // Bảng DanhGia
    private static final String TABLE_DANH_GIA = "DanhGia";
    private static final String COLUMN_MA_DANH_GIA = "MaDanhGia";
    private static final String COLUMN_MA_NGUOIDUNG_DG = "MaNguoiDung"; // Sử dụng rõ hơn
    private static final String COLUMN_DIEM_DANH_GIA = "DiemDanhGia";
    private static final String COLUMN_NHAN_XET = "NhanXet";
    private static final String COLUMN_NGAY_DANH_GIA = "NgayDanhGia";

    // Bảng MaGiamGia
    private static final String TABLE_MA_GIAM_GIA = "MaGiamGia";
    private static final String COLUMN_MA_GIAM_GIA_MGG = "MaGiamGia";
    private static final String COLUMN_MO_TA_MGG = "MoTa";
    private static final String COLUMN_PHAN_TRAM_GIAM_GIA = "PhanTramGiamGia";
    private static final String COLUMN_NGAY_BAT_DAU = "NgayBatDau";
    private static final String COLUMN_NGAY_KET_THUC = "NgayKetThuc";
    private static final String COLUMN_SO_LUONG_TOI_DA = "SoLuongToiDa";
    private static final String COLUMN_SO_LUONG_DA_SU_DUNG = "SoLuongDaSuDung";
    private static final String COLUMN_TRANG_THAI_MGG = "TrangThai";

    // Bảng BaoCaoThongKe
    private static final String TABLE_BAO_CAO_THONG_KE = "BaoCaoThongKe";
    private static final String COLUMN_MA_BAO_CAO = "MaBaoCao";
    private static final String COLUMN_NGAY_BAO_CAO = "NgayBaoCao";
    private static final String COLUMN_DOANH_THU = "DoanhThu";
    private static final String COLUMN_SO_DON_HANG = "SoDonHang";
    private static final String COLUMN_MON_AN_BAN_CHAY_NHAT = "MonAnBanChayNhat";
    private static final String COLUMN_SO_LUONG_MON_BAN_CHAY = "SoLuongMonBanChay";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    // Phương thức thực thi các câu truy vấn không trả về dữ liệu (CREATE, INSERT, UPDATE, DELETE)
    public void QueryData(String sql){
        SQLiteDatabase DATA = getWritableDatabase();
        DATA.execSQL(sql);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng NguoiDung
        String createNguoiDungTable = "CREATE TABLE " + TABLE_NGUOIDUNG + " (" +
                COLUMN_MA_NGUOIDUNG + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TEN_DANGNHAP + " VARCHAR(50) NOT NULL, " +
                COLUMN_EMAIL + " VARCHAR(100) NOT NULL, " +
                COLUMN_MAT_KHAU + " VARCHAR(255) NOT NULL, " +
                COLUMN_HO_TEN + " VARCHAR(100), " +
                COLUMN_SO_DIEN_THOAI + " VARCHAR(20), " +
                COLUMN_DIA_CHI + " TEXT, " +
                COLUMN_PHAN_QUYEN + " VARCHAR(10) NOT NULL CHECK (" + COLUMN_PHAN_QUYEN + " IN ('Admin', 'User')) DEFAULT 'User');"; // Cột PhanQuyen với giá trị mặc định là 'User'
        db.execSQL(createNguoiDungTable);
        // Chèn dữ liệu mẫu vào bảng NguoiDung
        db.execSQL("INSERT INTO NguoiDung (TenDangNhap, Email, MatKhau, HoTen, SoDienThoai, DiaChi, PhanQuyen) VALUES " +
                "('user1', 'user1@example.com', 'user1', 'Nguyen Van A', '0909123456', 'Hà Nội', 'User')," +
                "('user2', 'user2@example.com', 'user2', 'Tran Thi B', '0909345678', 'TP Hồ Chí Minh', 'User')," +
                "('admin', 'admin@example.com', 'admin', 'Le Van C', '0910234567', 'Đà Nẵng', 'Admin')," +
                "('user3', 'user3@example.com', 'user3', 'Pham Thi D', '0909456789', 'Huế', 'User')," +
                "('user4', '4@example.com', 'user4', 'Pham Thi D', '0909456789', 'Huế', 'User')," +
                "('user5', 'user5@example.com', 'user5', 'Hoang Van E', '0909567890', 'Cần Thơ', 'User');");

        // Tạo bảng ThucDon
        String createThucDonTable = "CREATE TABLE " + TABLE_THUC_DON + " (" +
                COLUMN_MA_MON_AN + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TEN_MON_AN + " VARCHAR(100) NOT NULL, " +
                COLUMN_MO_TA + " TEXT, " +
                COLUMN_GIA + " DECIMAL(10, 2) NOT NULL, " +
                COLUMN_DANH_MUC + " VARCHAR(50), " +
                COLUMN_SAO_DANH_GIA + " DECIMAL(2, 1) CHECK (" + COLUMN_SAO_DANH_GIA + " >= 0 AND " + COLUMN_SAO_DANH_GIA + " <= 5), " +
                COLUMN_NANG_LUONG + " INT, " +
                COLUMN_THOI_GIAN_LAM + " INT, " +
                COLUMN_ANH_MINH_HOA + " VARCHAR(255), " +
                COLUMN_SO_LUONG + " INT NOT NULL CHECK (" + COLUMN_SO_LUONG + " >= 0));";
        db.execSQL(createThucDonTable);
        // Chèn sẵn 10 món ăn
        String insertThucDon = "INSERT INTO " + TABLE_THUC_DON + " (" +
                COLUMN_TEN_MON_AN + ", " + COLUMN_MO_TA + ", " + COLUMN_GIA + ", " + COLUMN_DANH_MUC + ", " +
                COLUMN_SAO_DANH_GIA + ", " + COLUMN_NANG_LUONG + ", " + COLUMN_THOI_GIAN_LAM + ", " + COLUMN_ANH_MINH_HOA + ", " + COLUMN_SO_LUONG + ") VALUES " +
                "('Gà sốt mắm ngọt', 'gà ta 100%', 50000, 'Món mặn', 4.5, 350, 15, 'drawable/gatay', 10)," +
                "('Cheese Pizza', 'ngon nhức nách', 45000, 'Món ăn nhanh', 4.2, 400, 20, 'drawable/pizzacheese', 15)";
        db.execSQL(insertThucDon);
//giohang
        String createGioHangTable = "CREATE TABLE " + TABLE_GIO_HANG + " (" +
                COLUMN_MA_GIO_HANG + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_MA_NGUOIDUNG_GH + " INT NOT NULL, " +
                COLUMN_MA_MON_AN_GH + " INT NOT NULL, " +
                COLUMN_SO_LUONG_GH + " INT NOT NULL CHECK (" + COLUMN_SO_LUONG_GH + " > 0), " +
                "FOREIGN KEY (" + COLUMN_MA_NGUOIDUNG_GH + ") REFERENCES " + TABLE_NGUOIDUNG + "(" + COLUMN_MA_NGUOIDUNG + "), " +
                "FOREIGN KEY (" + COLUMN_MA_MON_AN_GH + ") REFERENCES " + TABLE_THUC_DON + "(" + COLUMN_MA_MON_AN + "));";
        db.execSQL(createGioHangTable);
//donhang
        String createDonHangTable = "CREATE TABLE " + TABLE_DON_HANG + " (" +
                COLUMN_MA_DON_HANG + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_MA_NGUOIDUNG_DH + " INT, " +
                COLUMN_NGAY_DAT_HANG + " DATETIME NOT NULL, " +
                COLUMN_TONG_TIEN + " DECIMAL(10, 2) NOT NULL, " +
                COLUMN_TRANG_THAI + " VARCHAR(20) NOT NULL CHECK (" + COLUMN_TRANG_THAI + " IN ('Chờ xử lý', 'Đang chuẩn bị', 'Đang giao', 'Giao thành công','Đã Hủy')), " +
                COLUMN_MA_GIAM_GIA_DH + " VARCHAR(20), " +
                COLUMN_DIA_CHI_GIAO_HANG + " TEXT, " +
                COLUMN_THONG_TIN_LIEN_LAC + " TEXT, " +
                "FOREIGN KEY (" + COLUMN_MA_NGUOIDUNG_DH + ") REFERENCES " + TABLE_NGUOIDUNG + "(" + COLUMN_MA_NGUOIDUNG + "), " +
                "FOREIGN KEY (" + COLUMN_MA_GIAM_GIA_DH + ") REFERENCES " + TABLE_MA_GIAM_GIA + "(" + COLUMN_MA_GIAM_GIA_MGG + "));";
        db.execSQL(createDonHangTable);
        // Chèn sẵn 5 đơn hàng
        String insertDonHang = "INSERT INTO " + TABLE_DON_HANG + " (" +
                COLUMN_MA_NGUOIDUNG_DH + ", " + COLUMN_NGAY_DAT_HANG + ", " + COLUMN_TONG_TIEN + ", " +
                COLUMN_TRANG_THAI + ", " + COLUMN_MA_GIAM_GIA_DH + ", " + COLUMN_DIA_CHI_GIAO_HANG + ", " + COLUMN_THONG_TIN_LIEN_LAC + ") VALUES " +
                "(1, '2025-04-03 10:00:00', 500000, 'Chờ xử lý', 'MGG2024A', '123 Đường A, TP.HCM', '0123456789')," +
                "(2, '2025-04-02 14:30:00', 750000, 'Đang chuẩn bị', 'MGG2024B', '456 Đường B, Hà Nội', '0987654321')," +
                "(4, '2025-04-01 09:15:00', 1200000, 'Đang giao', 'MGG2024D', '789 Đường C, Đà Nẵng', '0345678912')," +
                "(5, '2025-03-31 18:45:00', 900000, 'Giao thành công', NULL, '321 Đường D, Hải Phòng', '0765432109')," +
                "(2, '2025-03-30 21:10:00', 650000, 'Chờ xử lý', 'MGG2024A', '567 Đường E, Cần Thơ', '0556677889');";
        db.execSQL(insertDonHang);

        // Tạo bảng ChiTietDonHang
        String createChiTietDonHangTable = "CREATE TABLE " + TABLE_CHI_TIET_DON_HANG + " (" +
                COLUMN_MA_CHI_TIET_DON_HANG + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_MA_DON_HANG_CTHD + " INT, " +
                COLUMN_MA_MON_AN_CTHD + " INT, " +
                "FOREIGN KEY (" + COLUMN_MA_DON_HANG_CTHD + ") REFERENCES " + TABLE_DON_HANG + "(" + COLUMN_MA_DON_HANG + "), " +
                "FOREIGN KEY (" + COLUMN_MA_MON_AN_CTHD + ") REFERENCES " + TABLE_THUC_DON + "(" + COLUMN_MA_MON_AN + "));";
        db.execSQL(createChiTietDonHangTable);

        // Bảng DanhGia
        String createDanhGiaTable = "CREATE TABLE " + TABLE_DANH_GIA + " (" +
                COLUMN_MA_DANH_GIA + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_MA_NGUOIDUNG_DG + " INT, " +
                COLUMN_MA_MON_AN + " INT, " +  // Thêm cột MaMonAn để liên kết đến bảng ThucDon
                COLUMN_DIEM_DANH_GIA + " INT NOT NULL, " +
                COLUMN_NHAN_XET + " TEXT, " +
                COLUMN_NGAY_DANH_GIA + " DATETIME NOT NULL, " +
                "FOREIGN KEY (" + COLUMN_MA_NGUOIDUNG_DG + ") REFERENCES " + TABLE_NGUOIDUNG + "(" + COLUMN_MA_NGUOIDUNG + "), " +
                "FOREIGN KEY (" + COLUMN_MA_MON_AN + ") REFERENCES " + TABLE_THUC_DON + "(" + COLUMN_MA_MON_AN + "));";
        db.execSQL(createDanhGiaTable);


        // Tạo bảng MaGiamGia
        String createMaGiamGiaTable = "CREATE TABLE " + TABLE_MA_GIAM_GIA + " (" +
                COLUMN_MA_GIAM_GIA_MGG + " VARCHAR(20) PRIMARY KEY, " +
                COLUMN_MO_TA_MGG + " TEXT, " +
                COLUMN_PHAN_TRAM_GIAM_GIA + " DECIMAL(5, 2) NOT NULL, " +
                COLUMN_NGAY_BAT_DAU + " DATE NOT NULL, " +
                COLUMN_NGAY_KET_THUC + " DATE NOT NULL, " +
                COLUMN_SO_LUONG_TOI_DA + " INT, " +
                COLUMN_SO_LUONG_DA_SU_DUNG + " INT DEFAULT 0, " +
                COLUMN_TRANG_THAI_MGG + " VARCHAR(20) CHECK (" + COLUMN_TRANG_THAI_MGG + " IN ('Hoạt động', 'Hết', 'Hủy')) DEFAULT 'Hoạt động');";
        db.execSQL(createMaGiamGiaTable);
        // Thêm mã giảm giá vào bảng MaGiamGia
        // Danh sách các mã giảm giá để chèn
        String[][] discountData = {
                {"MGG2024A", "Giảm giá mùa hè", "10.00", "2024-10-01", "2024-10-31", "100", "10", "Hoạt động"},
                {"MGG2024B", "Giảm giá 20%", "20.00", "2024-10-01", "2024-10-15", "50", "5", "Hoạt động"},
                {"MGG2024C", "Khuyến mãi cuối tuần", "15.00", "2024-10-05", "2024-10-07", "20", "3", "Hết"},
                {"MGG2024D", "Giảm giá 5% cho đơn hàng đầu tiên", "5.00", "2024-10-01", "2024-10-30", "200", "50", "Hoạt động"},
                {"MGG2024E", "Giảm giá sinh nhật", "25.00", "2024-10-10", "2024-10-20", "10", "0", "Hủy"}
        };

        // Chèn dữ liệu vào bảng
        for (String[] data : discountData) {
            ContentValues discountValues = new ContentValues();
            discountValues.put(COLUMN_MA_GIAM_GIA_MGG, data[0]);
            discountValues.put(COLUMN_MO_TA_MGG, data[1]);
            discountValues.put(COLUMN_PHAN_TRAM_GIAM_GIA, data[2]);
            discountValues.put(COLUMN_NGAY_BAT_DAU, data[3]);
            discountValues.put(COLUMN_NGAY_KET_THUC, data[4]);
            discountValues.put(COLUMN_SO_LUONG_TOI_DA, data[5]);
            discountValues.put(COLUMN_SO_LUONG_DA_SU_DUNG, data[6]);
            discountValues.put(COLUMN_TRANG_THAI_MGG, data[7]);

            // Chèn dữ liệu vào bảng
            db.insert(TABLE_MA_GIAM_GIA, null, discountValues);
        }

        // Tạo bảng BaoCaoThongKe
        String createBaoCaoThongKeTable = "CREATE TABLE " + TABLE_BAO_CAO_THONG_KE + " (" +
                COLUMN_MA_BAO_CAO + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NGAY_BAO_CAO + " DATE NOT NULL, " +
                COLUMN_DOANH_THU + " DECIMAL(12, 2) NOT NULL, " +
                COLUMN_SO_DON_HANG + " INT NOT NULL, " +
                COLUMN_MON_AN_BAN_CHAY_NHAT + " INT, " +
                COLUMN_SO_LUONG_MON_BAN_CHAY + " INT, " +
                "FOREIGN KEY (" + COLUMN_MON_AN_BAN_CHAY_NHAT + ") REFERENCES " + TABLE_THUC_DON + "(" + COLUMN_MA_MON_AN + "));";
        db.execSQL(createBaoCaoThongKeTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xóa các bảng cũ nếu chúng đã tồn tại
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BAO_CAO_THONG_KE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MA_GIAM_GIA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DANH_GIA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHI_TIET_DON_HANG);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DON_HANG);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_THUC_DON);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NGUOIDUNG);
        db.execSQL("DROP TABLE IF EXISTS "  + TABLE_GIO_HANG);

        // Gọi lại phương thức onCreate để tạo lại bảng mới
        onCreate(db);
    }
    // Phương thức trả về SQLiteDatabase
    public SQLiteDatabase getDatabase() {
        return this.getWritableDatabase();
    }


    // Các hàm CRUD (Create, Read, Update, Delete)
    public void addUser(UserDomain user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TEN_DANGNHAP, user.getUsername());
        values.put(COLUMN_EMAIL, user.getEmail());
        values.put(COLUMN_MAT_KHAU, user.getPassword());
        values.put(COLUMN_HO_TEN, user.getFullName());
        values.put(COLUMN_SO_DIEN_THOAI, user.getPhone());
        values.put(COLUMN_DIA_CHI, user.getAddress());
        db.insert(TABLE_NGUOIDUNG, null, values);
    }
    public UserDomain getUser(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        UserDomain user = null;
        Cursor cursor = db.query(
                "NguoiDung", // Tên bảng
                new String[]{"MaNguoiDung", "TenDangNhap", "Email", "MatKhau", "HoTen", "SoDienThoai", "DiaChi"}, // Các cột cần lấy
                "TenDangNhap = ?", // Điều kiện WHERE
                new String[]{username}, // Tham số của WHERE
                null, null, null
        );
        if (cursor != null && cursor.moveToFirst()) {
            // Nếu tìm thấy người dùng, tạo đối tượng UserDomain
            user = new UserDomain();
            user.setId(cursor.getInt(0));
            user.setUsername(cursor.getString(1));
            user.setEmail(cursor.getString(2));
            user.setPassword(cursor.getString(3));
            user.setFullName(cursor.getString(4));
            user.setPhone(cursor.getString(5));
            user.setAddress(cursor.getString(6));
        }
        if (cursor != null) {
            cursor.close();
        }
        return user;
    }

    // Phương thức trong DatabaseHelper để lấy thông tin người dùng
    public UserDomain getUserByUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NGUOIDUNG,
                new String[]{COLUMN_HO_TEN, COLUMN_SO_DIEN_THOAI, COLUMN_EMAIL, COLUMN_DIA_CHI, COLUMN_TEN_DANGNHAP, COLUMN_MAT_KHAU, COLUMN_PHAN_QUYEN},
                COLUMN_TEN_DANGNHAP + "=?", new String[]{username},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            UserDomain user = new UserDomain();
            user.setFullName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HO_TEN)));
            user.setPhone(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SO_DIEN_THOAI)));
            user.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL)));
            user.setAddress(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DIA_CHI)));
            user.setUsername(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TEN_DANGNHAP)));
            user.setPassword(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MAT_KHAU)));
            user.setRole(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHAN_QUYEN)));
            cursor.close();
            return user;
        }
        cursor.close();
        return null;
    }

    // Kiểm tra người dùng dựa trên tên đăng nhập và mật khẩu
    public boolean checkUser(String username, String password) {
        // Kiểm tra nếu tên đăng nhập hoặc mật khẩu trống
        if (username.isEmpty() || password.isEmpty()) {
            return false;
        }
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_NGUOIDUNG,
                    new String[]{COLUMN_MA_NGUOIDUNG},
                    COLUMN_TEN_DANGNHAP + "=? AND " + COLUMN_MAT_KHAU + "=?",
                    new String[]{username, password},
                    null, null, null);
            // Kiểm tra số lượng kết quả trả về
            return cursor.getCount() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
    }

    public void updateOrderStatus(int maDonHang, String newStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TRANG_THAI, newStatus);
        // Cập nhật trạng thái đơn hàng
        db.update(TABLE_DON_HANG, values, COLUMN_MA_DON_HANG + " = ?", new String[]{String.valueOf(maDonHang)});
    }
    public void deleteOrder(int maDonHang) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DON_HANG, COLUMN_MA_DON_HANG + " = ?", new String[]{String.valueOf(maDonHang)});
    }
    //dem so  don hang
    public  int dem_don_hang(){
        SQLiteDatabase db = this.getReadableDatabase();
        int soLuongDonHang = 0;
        String query = "SELECT COUNT(*) FROM " + TABLE_DON_HANG;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                soLuongDonHang = cursor.getInt(0);
            }
            cursor.close();
        }
        return soLuongDonHang;
    }
    //đếm số mặt hàng trong giỏ hàng
    public int dem_mat_hang(int ma_nguoi_dung){
        SQLiteDatabase db = this.getReadableDatabase();
        int so_luong_mat_hang = 0;
        String sql = "SELECT COUNT(*) FROM " + TABLE_GIO_HANG +
                " WHERE " + COLUMN_MA_NGUOIDUNG_GH + " = " + ma_nguoi_dung;

        Cursor cur = db.rawQuery(sql,null);
        if (cur != null){
            if (cur.moveToFirst()){
                so_luong_mat_hang = cur.getInt(0);
            }
            cur.close();
        }
        return so_luong_mat_hang;
    }
    //dem so mgg
    public  int dem_MGG(){
        SQLiteDatabase db = this.getReadableDatabase();
        int soLuongMGG = 0;
        String query = "SELECT COUNT(*) FROM " + TABLE_MA_GIAM_GIA;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                soLuongMGG = cursor.getInt(0);
            }
            cursor.close();
        }
        return soLuongMGG;
    }
    //dem so mon an
    public  int dem_so_mon(){
        SQLiteDatabase db = this.getReadableDatabase();
        int soLuongmon = 0;
        String query = "SELECT COUNT(*) FROM " + TABLE_THUC_DON;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                soLuongmon = cursor.getInt(0);
            }
            cursor.close();
        }
        return soLuongmon;
    }
    //bac
    public  int getusername(String tendangnhap)
    {
        SQLiteDatabase DB = this.getReadableDatabase();
        int  manguoidung = 0; //Mặc định nếu không tìm thấy
        // Truy vấn mã người dùng dựa trên tên đăng nhập
        String query_manguoidung = "SELECT " + COLUMN_MA_NGUOIDUNG + " FROM " + TABLE_NGUOIDUNG +
                " WHERE " + COLUMN_TEN_DANGNHAP + " = ?";
        Cursor cursor = DB.rawQuery(query_manguoidung,new String[]{tendangnhap});
        // Kiểm tra nếu có kết quả trả về
        if (cursor != null && cursor.moveToFirst()) {
            // Lấy giá trị cột maNguoiDung từ cursor
            manguoidung = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MA_NGUOIDUNG));
            cursor.close();
        }
        return manguoidung; // Trả về mã người dùng
    }

    public  int getmagiohang(int  manguoidung)
    {
        SQLiteDatabase DB = this.getReadableDatabase();
        int  magiohang = 0; //Mặc định nếu không tìm thấy
        // Truy vấn mã giohang dựa trên manguoidung
        String query_magiohang = "SELECT " + COLUMN_MA_GIO_HANG + " FROM " + TABLE_GIO_HANG +
                " WHERE " + COLUMN_MA_NGUOIDUNG_GH + " = ?";
        Cursor cursor = DB.rawQuery(query_magiohang,new String[]{String.valueOf(manguoidung)});
        // Kiểm tra nếu có kết quả trả về
        if (cursor != null && cursor.moveToFirst()) {
            // Lấy giá trị cột maNguoiDung từ cursor
            magiohang = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MA_GIO_HANG));
            cursor.close();
        }
        return magiohang; // Trả về magiohang
    }
    public int getAvailableQuantity(int maMonAn) {
        SQLiteDatabase DB = this.getReadableDatabase();
        int max_soluong = 0;
        Cursor cursor = DB.rawQuery(
                "SELECT " + COLUMN_SO_LUONG + " FROM " + TABLE_THUC_DON +
                        " WHERE " + COLUMN_MA_MON_AN + " = ?",
                new String[]{String.valueOf(maMonAn)}
        );

        if (cursor != null && cursor.moveToFirst()) {
            max_soluong = cursor.getInt(0);
            cursor.close();
        }
        return max_soluong;
    }
    //kiểm tra xem sản phẩm muốn thêm đã có trong giỏ chưa
    public boolean kiemTraMonAnTrongGioHang(int maMonAn, int maNguoiDung) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM GioHang WHERE MaMonAn = " + maMonAn +
                " AND MaNguoiDung = " + maNguoiDung;
        android.database.Cursor cursor = db.rawQuery(sql, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // Phương thức lấy phân quyền của người dùng
    public String getUserRole(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT PhanQuyen FROM NguoiDung WHERE TenDangNhap = ? AND MatKhau = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username, password});

        if (cursor.moveToFirst()) {
            String role = cursor.getString(0);  // Lấy giá trị của cột PhanQuyen
            cursor.close();
            return role;
        } else {
            cursor.close();
            return null;  // Trả về null nếu không tìm thấy người dùng
        }
    }


    public void updatePassword(String username, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MAT_KHAU, newPassword);
        db.update(TABLE_NGUOIDUNG, values, COLUMN_TEN_DANGNHAP + " = ?", new String[]{username});
        db.close();
    }
    // Thêm bình luận
    public void addComment(DanhGiaDomain comment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MA_NGUOIDUNG, comment.getMaNguoiDung());
        values.put(COLUMN_MA_MON_AN, comment.getMaMonAn());  // Thêm mã món ăn dưới dạng int
        values.put(COLUMN_DIEM_DANH_GIA, comment.getDiemDanhGia());
        values.put(COLUMN_NHAN_XET, comment.getNhanXet());
        values.put(COLUMN_NGAY_DANH_GIA, comment.getNgayDanhGia());

        db.insert(TABLE_DANH_GIA, null, values);
//        db.close();
    }

    public List<ThucDonDomain> getAllThucDon() {
        List<ThucDonDomain> foodList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        // Truy vấn lấy tất cả món ăn từ bảng ThucDon
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_THUC_DON, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range")
                String imgPath = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ANH_MINH_HOA));
                ThucDonDomain food = new ThucDonDomain(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MA_MON_AN)), // Lấy mã món ăn (integer)
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TEN_MON_AN)), // Kiểu string
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MO_TA)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_GIA)), // Kiểu double
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DANH_MUC)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_SAO_DANH_GIA)), // Kiểu double
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NANG_LUONG)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_THOI_GIAN_LAM)),
                        imgPath,
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SO_LUONG))
                );
                foodList.add(food);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return foodList;
    }


    // Lấy tất cả bình luận
    public List<DanhGiaDomain> getCommentsByProductId(int maMonAn) {
        List<DanhGiaDomain> commentList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Truy vấn tất cả các bình luận liên quan đến món ăn có mã là maMonAn
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_DANH_GIA + " WHERE " + COLUMN_MA_MON_AN + " = ?", new String[]{String.valueOf(maMonAn)});
        if (cursor.moveToFirst()) {
            do {
                // Tạo đối tượng DanhGiaDomain từ dữ liệu trong cursor
                DanhGiaDomain comment = new DanhGiaDomain(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MA_DANH_GIA)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MA_NGUOIDUNG)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MA_MON_AN)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_DIEM_DANH_GIA)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NHAN_XET)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NGAY_DANH_GIA))
                );
                commentList.add(comment);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return commentList;
    }
    // Cập nhật bình luận
    public void updateComment(DanhGiaDomain comment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DIEM_DANH_GIA, comment.getDiemDanhGia());
        values.put(COLUMN_NHAN_XET, comment.getNhanXet());
        db.update(TABLE_DANH_GIA, values, COLUMN_MA_DANH_GIA + " = ?", new String[]{String.valueOf(comment.getMaDanhGia())});

    }
    // Xóa bình luận
    public void deleteComment(int maDanhGia) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DANH_GIA, COLUMN_MA_DANH_GIA + " = ?", new String[]{String.valueOf(maDanhGia)});
    }
    public BigDecimal getTotalRevenue(String startDate, String endDate) {
        // Truy vấn chỉ lấy phần ngày và so sánh
        String selectQuery = "SELECT SUM(" + COLUMN_TONG_TIEN + ") AS TongDoanhThu FROM " + TABLE_DON_HANG +
                " WHERE DATE(" + COLUMN_NGAY_DAT_HANG + ") BETWEEN ? AND ? AND " + COLUMN_TRANG_THAI + " = 'Giao thành công'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{startDate, endDate});

        BigDecimal totalRevenue = BigDecimal.ZERO;

        if (cursor.moveToFirst()) {
            @SuppressLint("Range") double totalRevenueDouble = cursor.getDouble(cursor.getColumnIndex("TongDoanhThu"));
            totalRevenue = BigDecimal.valueOf(totalRevenueDouble);
        }

        cursor.close();
        db.close();
        return totalRevenue;
    }

    public long insertThucDon(ThucDonDomain thucDon) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TEN_MON_AN, thucDon.getTenMonAn());
        values.put(COLUMN_MO_TA, thucDon.getMoTa());
        values.put(COLUMN_GIA, thucDon.getGia());
        values.put(COLUMN_DANH_MUC, thucDon.getDanhMuc());
        values.put(COLUMN_SAO_DANH_GIA, thucDon.getSaoDanhGia());
        values.put(COLUMN_NANG_LUONG, thucDon.getNangLuong());
        values.put(COLUMN_THOI_GIAN_LAM, thucDon.getThoiGianLam());
        values.put(COLUMN_ANH_MINH_HOA, thucDon.getAnhMinhHoa());
        values.put(COLUMN_SO_LUONG, thucDon.getSoLuong());

        // Chèn món ăn vào bảng và lấy mã món ăn mới được tạo
        long maMonAn = db.insert(TABLE_THUC_DON, null, values);
        db.close();

        // Trả về mã món ăn vừa chèn
        return maMonAn;
    }
    public boolean add_Food(Menu_Food_Domain menu_food) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TEN_MON_AN, menu_food.getTenmonan());
        values.put(COLUMN_MO_TA, menu_food.getMota());
        values.put(COLUMN_GIA, menu_food.getGia());
        values.put(COLUMN_DANH_MUC, menu_food.getDanhmuc());
        values.put(COLUMN_NANG_LUONG, menu_food.getNangluong());
        values.put(COLUMN_THOI_GIAN_LAM, menu_food.getTime());
        values.put(COLUMN_ANH_MINH_HOA, menu_food.getImg());
        values.put(COLUMN_SO_LUONG, menu_food.getSl());

        long result = db.insert(TABLE_THUC_DON, null, values);
        db.close();
        return result != -1; // Trả về true nếu thêm thành công
    }

    public void update_Food(Menu_Food_Domain menu_food) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TEN_MON_AN, menu_food.getTenmonan());
        values.put(COLUMN_MO_TA, menu_food.getMota());
        values.put(COLUMN_GIA, menu_food.getGia());
        values.put(COLUMN_DANH_MUC, menu_food.getDanhmuc());
        values.put(COLUMN_NANG_LUONG, menu_food.getNangluong());
        values.put(COLUMN_THOI_GIAN_LAM, menu_food.getTime());
        values.put(COLUMN_ANH_MINH_HOA, menu_food.getImg());
        values.put(COLUMN_SO_LUONG, menu_food.getSl());
        db.update(TABLE_THUC_DON, values, COLUMN_MA_MON_AN + " = ?", new String[]{String.valueOf(menu_food.getId())});
        db.close();
    }
    // Xóa bình luận
    public void delete_Food(int maMonan) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_THUC_DON, COLUMN_MA_MON_AN + " = ?", new String[]{String.valueOf(maMonan)});
        db.close();
    }

    public List<Menu_Food_Domain> get_Food_ByProductId(int maMonAn) {
        List<Menu_Food_Domain> foodList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_THUC_DON + " WHERE " + COLUMN_MA_MON_AN + " = ?", new String[]{String.valueOf(maMonAn)});
        if (cursor.moveToFirst()) {
            do {
                int indexId = cursor.getColumnIndex(COLUMN_MA_MON_AN);
                int indexTenmonan = cursor.getColumnIndex(COLUMN_TEN_MON_AN);
                int indexMoTa = cursor.getColumnIndex(COLUMN_MO_TA);
                int indexGia = cursor.getColumnIndex(COLUMN_GIA);
                int indexDanhmuc = cursor.getColumnIndex(COLUMN_DANH_MUC);
                int indexNangluong = cursor.getColumnIndex(COLUMN_NANG_LUONG);
                int indexThoigian = cursor.getColumnIndex(COLUMN_THOI_GIAN_LAM);
                int indexAnh = cursor.getColumnIndex(COLUMN_ANH_MINH_HOA);
                int indexSoluong = cursor.getColumnIndex(COLUMN_SO_LUONG);

                // Tạo đối tượng DanhGiaDomain từ dữ liệu trong cursor
                Menu_Food_Domain menu_food = new Menu_Food_Domain(
                        cursor.getInt(indexId), // id
                        cursor.getString(indexDanhmuc), // Danh mục
                        cursor.getInt(indexThoigian), // Thời gian
                        cursor.getInt(indexSoluong), // Số lượng
                        cursor.getString(indexMoTa), // Mô tả
                        cursor.getString(indexAnh), // Hình ảnh
                        cursor.getDouble(indexGia), // Giá
                        cursor.getInt(indexNangluong), // Năng lượng
                        cursor.getString(indexTenmonan)
                );
                foodList.add(menu_food);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return foodList;
    }

    public List<Menu_Food_Domain> get_Food() {
        List<Menu_Food_Domain> foodList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        // Truy vấn tất cả dữ liệu từ bảng
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_THUC_DON, null);
        if (cursor != null) {
            // Duyệt qua tất cả các bản ghi
            if (cursor.moveToFirst()) {
                do {
                    int indexId = cursor.getColumnIndex(COLUMN_MA_MON_AN);
                    int indexTenmonan = cursor.getColumnIndex(COLUMN_TEN_MON_AN);
                    int indexMoTa = cursor.getColumnIndex(COLUMN_MO_TA);
                    int indexGia = cursor.getColumnIndex(COLUMN_GIA);
                    int indexDanhmuc = cursor.getColumnIndex(COLUMN_DANH_MUC);
                    int indexNangluong = cursor.getColumnIndex(COLUMN_NANG_LUONG);
                    int indexThoigian = cursor.getColumnIndex(COLUMN_THOI_GIAN_LAM);
                    int indexAnh = cursor.getColumnIndex(COLUMN_ANH_MINH_HOA);
                    int indexSoluong = cursor.getColumnIndex(COLUMN_SO_LUONG);

                    if (indexTenmonan != -1 && indexMoTa != -1 && indexGia != -1 &&
                            indexDanhmuc != -1 && indexNangluong != -1 &&
                            indexThoigian != -1 && indexAnh != -1 &&
                            indexSoluong != -1) {

                        Menu_Food_Domain food = new Menu_Food_Domain(
                                cursor.getInt(indexId), // id
                                cursor.getString(indexDanhmuc), // Danh mục
                                cursor.getInt(indexThoigian), // Thời gian
                                cursor.getInt(indexSoluong), // Số lượng
                                cursor.getString(indexMoTa), // Mô tả
                                cursor.getString(indexAnh), // Hình ảnh
                                cursor.getDouble(indexGia), // Giá
                                cursor.getInt(indexNangluong), // Năng lượng
                                cursor.getString(indexTenmonan) // Tên món ăn
                        );
                        foodList.add(food); // Thêm vào danh sách
                    }

                } while (cursor.moveToNext()); // Chuyển đến bản ghi tiếp theo
            }
            cursor.close(); // Đóng con trỏ
        }
        db.close(); // Đóng cơ sở dữ liệu
        return foodList; // Trả về danh sách
    }
    public void addSampleThucDonAndComments() {
        ThucDonDomain phoBo = new ThucDonDomain(0, "Phở bò", "Phở bò truyền thống", 45000, "Món chính", 4.5, 300, 15, "pho_bo.jpg", 20);

        // Chèn món ăn và lấy mã món ăn
        long maMonAn = insertThucDon(phoBo);

        // Kiểm tra xem mã món ăn có hợp lệ không
        if (maMonAn > 0) {
            // Chèn bình luận liên kết với món ăn
            DanhGiaDomain comment = new DanhGiaDomain(1, 5, 1, 4,"Ngon Tuyệt","2024-10-3");
            addComment(comment);
        } else {
            // Xử lý lỗi nếu không chèn được món ăn
            throw new IllegalArgumentException("Không thể chèn món ăn vào cơ sở dữ liệu.");
        }
    }
//    public void insertSampleData() {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        // Chèn dữ liệu mẫu vào bảng NguoiDung
//        ContentValues userValues = new ContentValues();
//        userValues.put(COLUMN_TEN_DANGNHAP, "nguoimua1");
//        userValues.put(COLUMN_EMAIL, "nguoimua1@gmail.com");
//        userValues.put(COLUMN_MAT_KHAU, "123456");
//        userValues.put(COLUMN_HO_TEN, "Nguyen Van A");
//        userValues.put(COLUMN_SO_DIEN_THOAI, "0987654321");
//        userValues.put(COLUMN_DIA_CHI, "123 Hoang Hoa Tham, Ha Noi");
//        db.insert(TABLE_NGUOIDUNG, null, userValues);
//
//        // Chèn dữ liệu mẫu vào bảng ThucDon
//        ContentValues thucDonValues = new ContentValues();
//        thucDonValues.put(COLUMN_TEN_MON_AN, "Phở bò");
//        thucDonValues.put(COLUMN_MO_TA, "Phở bò truyền thống");
//        thucDonValues.put(COLUMN_GIA, 45000);
//        thucDonValues.put(COLUMN_DANH_MUC, "Món chính");
//        thucDonValues.put(COLUMN_SAO_DANH_GIA, 4.5);
//        thucDonValues.put(COLUMN_NANG_LUONG, 300);
//        thucDonValues.put(COLUMN_THOI_GIAN_LAM, 15);
//        thucDonValues.put(COLUMN_ANH_MINH_HOA, "pho_bo.jpg");
//        thucDonValues.put(COLUMN_SO_LUONG, 10);
//        long maMonAn = db.insert(TABLE_THUC_DON, null, thucDonValues);
//
//        // Chèn dữ liệu mẫu vào bảng DonHang
//        ContentValues donHangValues = new ContentValues();
//        donHangValues.put(COLUMN_MA_NGUOIDUNG, 1); // Giả sử NguoiDung với ID 1 đã tồn tại
//        donHangValues.put(COLUMN_NGAY_DAT_HANG, "2024-10-13");
//        donHangValues.put(COLUMN_TONG_TIEN, 90000);
//        donHangValues.put(COLUMN_TRANG_THAI, "Chờ xử lý");
//        donHangValues.put(COLUMN_MA_GIAM_GIA_DH, 200);
//        long maDonHang = db.insert(TABLE_DON_HANG, null, donHangValues);
//
//        // Chèn dữ liệu mẫu vào bảng ChiTietDonHang
//        ContentValues chiTietValues = new ContentValues();
//        chiTietValues.put(COLUMN_MA_DON_HANG, maDonHang); // Mã đơn hàng vừa chèn ở trên
//        chiTietValues.put(COLUMN_MA_MON_AN_CTHD, maMonAn); // Mã món ăn vừa chèn ở trên
//        db.insert(TABLE_CHI_TIET_DON_HANG, null, chiTietValues);
//
//        // Đóng database
//        db.close();
//    }

    // Phương thức lấy tất cả mã giảm giá
    public List<maGiamGiaDomain> getAllCoupons() {
        List<maGiamGiaDomain> couponList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase(); // Tham chiếu đến thể hiện của DatabaseHelper

        // Truy vấn tất cả dữ liệu từ bảng
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_MA_GIAM_GIA, null);

        if (cursor != null) {
            // Duyệt qua tất cả các bản ghi
            if (cursor.moveToFirst()) {
                do {
                    // Lấy chỉ số cột với tên cột
                    int maGiamGiaIndex = cursor.getColumnIndex(COLUMN_MA_GIAM_GIA_MGG);
                    int moTaIndex = cursor.getColumnIndex(COLUMN_MO_TA_MGG);
                    int phanTramGiamGiaIndex = cursor.getColumnIndex(COLUMN_PHAN_TRAM_GIAM_GIA);
                    int ngayBatDauIndex = cursor.getColumnIndex(COLUMN_NGAY_BAT_DAU);
                    int ngayKetThucIndex = cursor.getColumnIndex(COLUMN_NGAY_KET_THUC);
                    int soLuongToiDaIndex = cursor.getColumnIndex(COLUMN_SO_LUONG_TOI_DA);
                    int soLuongDaSuDungIndex = cursor.getColumnIndex(COLUMN_SO_LUONG_DA_SU_DUNG);
                    int trangThaiIndex = cursor.getColumnIndex(COLUMN_TRANG_THAI_MGG);

                    // Kiểm tra xem các chỉ số có hợp lệ không (≥ 0)
                    if (maGiamGiaIndex >= 0 && moTaIndex >= 0 && phanTramGiamGiaIndex >= 0 &&
                            ngayBatDauIndex >= 0 && ngayKetThucIndex >= 0 &&
                            soLuongToiDaIndex >= 0 && soLuongDaSuDungIndex >= 0) {

                        // Tạo đối tượng MaGiamGiaDomain từ dữ liệu trong cursor
                        maGiamGiaDomain maGG = new maGiamGiaDomain(
                                cursor.getString(maGiamGiaIndex),
                                cursor.getString(moTaIndex),
                                cursor.getDouble(phanTramGiamGiaIndex),
                                cursor.getString(ngayBatDauIndex),
                                cursor.getString(ngayKetThucIndex),
                                cursor.getInt(soLuongToiDaIndex),
                                cursor.getInt(soLuongDaSuDungIndex),
                                cursor.getString(trangThaiIndex)
                        );
                        couponList.add(maGG); // Thêm vào danh sách
                    }

                } while (cursor.moveToNext()); // Chuyển đến bản ghi tiếp theo
            }
            cursor.close(); // Đóng con trỏ
        }
        db.close(); // Đóng cơ sở dữ liệu
        return couponList; // Trả về danh sách
    }
    // Thêm mã giảm giá
    public boolean addMaGiamGia(maGiamGiaDomain maGiamGia) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MA_GIAM_GIA_MGG, maGiamGia.getMaGiamGia());
        values.put(COLUMN_MO_TA_MGG, maGiamGia.getMoTa());
        values.put(COLUMN_PHAN_TRAM_GIAM_GIA, maGiamGia.getPhanTramGiamGia());
        values.put(COLUMN_NGAY_BAT_DAU, maGiamGia.getNgayBatDau());
        values.put(COLUMN_NGAY_KET_THUC, maGiamGia.getNgayKetThuc());
        values.put(COLUMN_SO_LUONG_TOI_DA, maGiamGia.getSoLuongToiDa());

        long result = db.insert(TABLE_MA_GIAM_GIA, null, values);
        db.close();
        return result != -1; // Trả về true nếu thêm thành công
    }

    public boolean updateDiscountCode(maGiamGiaDomain maGiamGia) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_MO_TA_MGG, maGiamGia.getMoTa());
        values.put(COLUMN_PHAN_TRAM_GIAM_GIA, maGiamGia.getPhanTramGiamGia());
        values.put(COLUMN_NGAY_BAT_DAU, maGiamGia.getNgayBatDau());
        values.put(COLUMN_NGAY_KET_THUC, maGiamGia.getNgayKetThuc());
        values.put(COLUMN_SO_LUONG_TOI_DA, maGiamGia.getSoLuongToiDa());
        values.put(COLUMN_SO_LUONG_DA_SU_DUNG, maGiamGia.getSoLuongDaSuDung());
        values.put(COLUMN_TRANG_THAI_MGG, maGiamGia.getTrangThai());

        // Update mã giảm giá với điều kiện là mã giảm giá (PRIMARY KEY)
        int result = db.update(TABLE_MA_GIAM_GIA, values, COLUMN_MA_GIAM_GIA_MGG + " = ?", new String[]{maGiamGia.getMaGiamGia()});

        db.close();

        // Nếu số dòng bị ảnh hưởng > 0 thì update thành công
        return result > 0;
    }

    //Xóa mã giảm giá
    public void deleteDiscountCode(String maGiamGia) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MA_GIAM_GIA, COLUMN_MA_GIAM_GIA_MGG + " = ?", new String[]{maGiamGia});
        db.close();
    }

    //Tìm Kiếm mã giảm giá
//    public List<maGiamGiaDomain> searchCouponsByName(String searchKeyword) {
//        List<maGiamGiaDomain> maGiamGiaList = new ArrayList<>();
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        // Câu lệnh SQL tìm kiếm
//        String query = "SELECT * FROM " + TABLE_MA_GIAM_GIA +
//                " WHERE " + COLUMN_MA_GIAM_GIA_MGG + " LIKE ?";
//
//        // Sử dụng '%' để tìm kiếm theo tên
//        Cursor cursor = db.rawQuery(query, new String[] { "%" + searchKeyword + "%" });
//
//        if (cursor.moveToFirst()) {
//            do {
//                // Lấy chỉ số của từng cột
//                int indexMaGiamGia = cursor.getColumnIndex(COLUMN_MA_GIAM_GIA_MGG);
//                int indexMoTa = cursor.getColumnIndex(COLUMN_MO_TA_MGG);
//                int indexPhanTramGiamGia = cursor.getColumnIndex(COLUMN_PHAN_TRAM_GIAM_GIA);
//                int indexNgayBatDau = cursor.getColumnIndex(COLUMN_NGAY_BAT_DAU);
//                int indexNgayKetThuc = cursor.getColumnIndex(COLUMN_NGAY_KET_THUC);
//                int indexSoLuongToiDa = cursor.getColumnIndex(COLUMN_SO_LUONG_TOI_DA);
//                int indexSoLuongDaSuDung = cursor.getColumnIndex(COLUMN_SO_LUONG_DA_SU_DUNG);
//                int indexTrangThai = cursor.getColumnIndex(COLUMN_TRANG_THAI_MGG);
//
//                // Kiểm tra chỉ số cột có hợp lệ không
//                if (indexMaGiamGia != -1 && indexMoTa != -1 && indexPhanTramGiamGia != -1 &&
//                        indexNgayBatDau != -1 && indexNgayKetThuc != -1 &&
//                        indexSoLuongToiDa != -1 && indexSoLuongDaSuDung != -1 &&
//                        indexTrangThai != -1) {
//
//                    String maGiamGia = cursor.getString(indexMaGiamGia);
//                    String moTa = cursor.getString(indexMoTa);
//                    double phanTramGiamGia = cursor.getDouble(indexPhanTramGiamGia);
//                    String ngayBatDau = cursor.getString(indexNgayBatDau);
//                    String ngayKetThuc = cursor.getString(indexNgayKetThuc);
//                    int soLuongToiDa = cursor.getInt(indexSoLuongToiDa);
//                    int soLuongDaSuDung = cursor.getInt(indexSoLuongDaSuDung);
//                    String trangThai = cursor.getString(indexTrangThai);
//
//                    maGiamGiaDomain coupon = new maGiamGiaDomain(maGiamGia, moTa, phanTramGiamGia, ngayBatDau, ngayKetThuc, soLuongToiDa, soLuongDaSuDung, trangThai);
//                    maGiamGiaList.add(coupon);
//                }
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        db.close();
//        return maGiamGiaList;
//    }

    public List<maGiamGiaDomain> searchCouponsByName(String searchKeyword) {
        List<maGiamGiaDomain> maGiamGiaList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Tách từ khóa tìm kiếm thành từng từ và thêm '%'
        String[] keywords = searchKeyword.trim().split("\\s+");
        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM " + TABLE_MA_GIAM_GIA + " WHERE ");
        for (int i = 0; i < keywords.length; i++) {
            queryBuilder.append(COLUMN_MA_GIAM_GIA_MGG).append(" LIKE ? ");
            if (i < keywords.length - 1) {
                queryBuilder.append("OR ");
            }
        }
        String query = queryBuilder.toString();

        // Thay thế từ khóa với các ký tự '%'
        String[] searchArgs = new String[keywords.length];
        for (int i = 0; i < keywords.length; i++) {
            searchArgs[i] = "%" + keywords[i] + "%";
        }

        Cursor cursor = db.rawQuery(query, searchArgs);
        if (cursor.moveToFirst()) {
            do {
                int indexMaGiamGia = cursor.getColumnIndex(COLUMN_MA_GIAM_GIA_MGG);
                int indexMoTa = cursor.getColumnIndex(COLUMN_MO_TA_MGG);
                int indexPhanTramGiamGia = cursor.getColumnIndex(COLUMN_PHAN_TRAM_GIAM_GIA);
                int indexNgayBatDau = cursor.getColumnIndex(COLUMN_NGAY_BAT_DAU);
                int indexNgayKetThuc = cursor.getColumnIndex(COLUMN_NGAY_KET_THUC);
                int indexSoLuongToiDa = cursor.getColumnIndex(COLUMN_SO_LUONG_TOI_DA);
                int indexSoLuongDaSuDung = cursor.getColumnIndex(COLUMN_SO_LUONG_DA_SU_DUNG);
                int indexTrangThai = cursor.getColumnIndex(COLUMN_TRANG_THAI_MGG);

                if (indexMaGiamGia != -1 && indexMoTa != -1 && indexPhanTramGiamGia != -1 &&
                        indexNgayBatDau != -1 && indexNgayKetThuc != -1 &&
                        indexSoLuongToiDa != -1 && indexSoLuongDaSuDung != -1 &&
                        indexTrangThai != -1) {

                    String maGiamGia = cursor.getString(indexMaGiamGia);
                    String moTa = cursor.getString(indexMoTa);
                    double phanTramGiamGia = cursor.getDouble(indexPhanTramGiamGia);
                    String ngayBatDau = cursor.getString(indexNgayBatDau);
                    String ngayKetThuc = cursor.getString(indexNgayKetThuc);
                    int soLuongToiDa = cursor.getInt(indexSoLuongToiDa);
                    int soLuongDaSuDung = cursor.getInt(indexSoLuongDaSuDung);
                    String trangThai = cursor.getString(indexTrangThai);

                    maGiamGiaDomain coupon = new maGiamGiaDomain(maGiamGia, moTa, phanTramGiamGia, ngayBatDau, ngayKetThuc, soLuongToiDa, soLuongDaSuDung, trangThai);
                    maGiamGiaList.add(coupon);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return maGiamGiaList;
    }



}