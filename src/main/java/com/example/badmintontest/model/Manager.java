package com.example.badmintontest.model;

// ผู้จัดการ : ทำได้ทุกอย่าง ทั้งงานของแคชเชียร์ และงานของผู้ดูแลระบบ
// (เพิ่มผู้ใช้ชนิดใหม่ได้ โดยแค่ extends User แล้วเขียน 3 เมธอดนี้ ไม่ต้องแก้หน้าจอเลย)
public class Manager extends User {

    public Manager(String username, String password, String fullName) {
        super(username, password, fullName);
    }

    @Override
    public String getRoleName() {
        return "ผู้จัดการ";
    }

    @Override
    public boolean canManageBooking() {
        return true;
    }

    @Override
    public boolean canViewReport() {
        return true;
    }
}
