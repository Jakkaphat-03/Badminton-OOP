package com.example.badmintontest.model;

// ผู้ดูแลระบบ : ดูประวัติการจองของสมาชิก, ดูสรุปรายได้รายวัน/รายเดือน
public class Admin extends User {

    public Admin(String username, String password, String fullName) {
        super(username, password, fullName);
    }

    @Override
    public String getRoleName() {
        return "ผู้ดูแลระบบ";
    }

    @Override
    public boolean canManageBooking() {
        return false;
    }

    @Override
    public boolean canViewReport() {
        return true;
    }
}
