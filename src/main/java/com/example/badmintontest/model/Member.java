package com.example.badmintontest.model;

import java.time.LocalDate;

// สมาชิก (ต้องสมัครสมาชิกก่อนจึงจะจองสนามได้)
public class Member {
    private String id;
    private String name;
    private String phone;
    private int totalHours;             // จำนวนชั่วโมงที่จองสะสมทั้งหมด (ใช้นับชั่วโมงที่ 20)
    private int packageHoursLeft;       // ชั่วโมงแพ็กเกจที่เหลือ
    private LocalDate expireDate;       // วันหมดอายุสมาชิก (สมัครแล้วใช้ได้ 1 ปี)
    private LocalDate packageExpireDate; // วันหมดอายุแพ็กเกจ (null = ยังไม่เคยซื้อแพ็กเกจ)

    public Member(String id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.totalHours = 0;
        this.packageHoursLeft = 0;
        this.expireDate = LocalDate.now().plusYears(1);   // วันนี้ + 1 ปี
        this.packageExpireDate = null;
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

    public LocalDate getExpireDate() {
        return expireDate;
    }

    public LocalDate getPackageExpireDate() {
        return packageExpireDate;
    }

    public void setPackageExpireDate(LocalDate date) {
        packageExpireDate = date;
    }

    // สมาชิกหมดอายุหรือยัง (เลยวันหมดอายุไปแล้ว = หมดอายุ)
    public boolean isExpired() {
        return LocalDate.now().isAfter(expireDate);
    }

    // ชั่วโมงแพ็กเกจที่ "ใช้ได้จริง" ในวันที่เล่น
    // ถ้ายังไม่เคยซื้อ หรือวันที่เล่นเลยวันหมดอายุแพ็กเกจแล้ว = ใช้ไม่ได้ (0)
    public int getUsablePackageHours(LocalDate playDate) {
        if (packageExpireDate == null || playDate.isAfter(packageExpireDate)) {
            return 0;
        }
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
}
