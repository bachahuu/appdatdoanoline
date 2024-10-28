package com.example.datdoanonline.Domain;

public class UserDomain {
    private int id;
    private String username;
    private String email;
    private String password;
    private String fullName;
    private String phone;
    private String address;
    private String role; // Thêm thuộc tính phân quyền

    // Constructor có tham số
    public UserDomain(int id, String username, String email, String password, String fullName, String phone, String address, String role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.phone = phone;
        this.address = address;
        this.role = role; // Khởi tạo phân quyền
    }

    // Constructor không tham số
    public UserDomain() {
    }

    // Getters và Setters cho các thuộc tính
    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getFullName() { return fullName; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public String getRole() { return role; } // Getter cho phân quyền

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setRole(String role) { // Setter cho phân quyền
        this.role = role;
    }
}

