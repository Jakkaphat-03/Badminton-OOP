package com.example.badmintontest.model;

// สมาชิก (ต้องสมัครสมาชิกก่อนจึงจะจองสนามได้)
public class Member {
    private String id;
    private String name;
    private String phone;
    private int totalHours;         // จำนวนชั่วโมงที่จองสะสมทั้งหมด (ใช้นับชั่วโมงที่ 20)
    private int packageHoursLeft;   // ชั่วโมงแพ็กเกจที่เหลือ

    public Member(String id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.totalHours = 0;
        this.packageHoursLeft = 0;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public int getTotalHours() {
        return totalHours;
    }

    public int getPackageHoursLeft() {
        return packageHoursLeft;
    }

    public void addTotalHours(int hours) {
        totalHours += hours;
    }

    public void addPackageHours(int hours) {
        packageHoursLeft += hours;
    }

    public void usePackageHours(int hours) {
        packageHoursLeft -= hours;
    }

    // ใช้แสดงใน ComboBox
    @Override
    public String toString() {
        return id + " - " + name;
    }
}
