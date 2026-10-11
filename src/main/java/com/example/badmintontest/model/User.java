package com.example.badmintontest.model;

// abstract class: ผู้ใช้ระบบ (สร้าง object จาก User ตรง ๆ ไม่ได้ ต้องเป็น Cashier หรือ Admin)
public abstract class User {
    private String username;
    private String password;
    private String fullName;

    public User(String username, String password, String fullName) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public String getFullName() {
        return fullName;
    }

    public boolean checkPassword(String inputPassword) {
        return password.equals(inputPassword);
    }

    // abstract method: ลูกแต่ละตัวต้องเขียนเอง
    public abstract String getRoleName();

    public abstract boolean canManageBooking();   // จอง/ค้นหา/ขายของ/สมัครสมาชิก

    public abstract boolean canViewReport();      // ดูประวัติสมาชิก/สรุปรายได้
}
