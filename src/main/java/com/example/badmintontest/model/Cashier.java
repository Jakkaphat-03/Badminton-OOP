package com.example.badmintontest.model;

// พนักงานแคชเชียร์ : กรอกข้อมูลการจอง, ค้นหาการจอง, ขายสินค้า, สมัครสมาชิก
public class Cashier extends User {

    public Cashier(String username, String password, String fullName) {
        super(username, password, fullName);
    }

    @Override
    public String getRoleName() {
        return "พนักงานแคชเชียร์";
    }

    @Override
    public boolean canManageBooking() {
        return true;
    }

    @Override
    public boolean canViewReport() {
        return false;
    }
}
