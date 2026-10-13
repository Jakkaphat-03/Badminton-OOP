package com.example.badmintontest.model;

import com.example.badmintontest.interfaces.Income;

import java.time.LocalDate;

// การจองสนาม 1 ครั้ง (เป็นรายได้ชนิดหนึ่ง จึง implements Income)
public class Booking implements Income {
    private String id;
    private Member member;
    private int court;              // หมายเลขสนาม 1-12
    private LocalDate playDate;     // วันที่เล่น
    private int startHour;          // ชั่วโมงเริ่ม เช่น 13
    private int hours;              // จำนวนชั่วโมง
    private int courtPrice;         // ค่าสนาม (หลังหักแพ็กเกจและส่วนลดแล้ว)
    private int packageHoursUsed;   // ใช้ชั่วโมงแพ็กเกจไปกี่ชั่วโมงในการจองครั้งนี้
    private int shoes;              // จำนวนรองเท้าที่เช่า
    private int rackets;            // จำนวนไม้ที่เช่า
    private int towels;             // จำนวนผ้าเช็ดตัวที่เช่า
    private int rentPrice;          // ค่าเช่าอุปกรณ์รวม
    private String slipPath;        // ไฟล์สลิปที่แนบ
    private LocalDate paidDate;     // วันที่ชำระเงิน
    private String cashierName;     // พนักงานที่กรอกข้อมูล

    public Booking(String id, Member member, int court, LocalDate playDate, int startHour, int hours,
                   int courtPrice, int packageHoursUsed, int shoes, int rackets, int towels, int rentPrice,
                   String slipPath, LocalDate paidDate, String cashierName) {
        this.id = id;
        this.member = member;
        this.court = court;
        this.playDate = playDate;
        this.startHour = startHour;
        this.hours = hours;
        this.courtPrice = courtPrice;
        this.packageHoursUsed = packageHoursUsed;
        this.shoes = shoes;
        this.rackets = rackets;
        this.towels = towels;
        this.rentPrice = rentPrice;
        this.slipPath = slipPath;
        this.paidDate = paidDate;
        this.cashierName = cashierName;
    }

    public String getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public int getCourt() {
        return court;
    }

    public LocalDate getPlayDate() {
        return playDate;
    }

    public int getStartHour() {
        return startHour;
    }

    public int getEndHour() {
        return startHour + hours;
    }

    public int getHours() {
        return hours;
    }

    public int getCourtPrice() {
        return courtPrice;
    }

    public int getPackageHoursUsed() {
        return packageHoursUsed;
    }

    public int getShoes() {
        return shoes;
    }

    public int getRackets() {
        return rackets;
    }

    public int getTowels() {
        return towels;
    }

    public int getRentPrice() {
        return rentPrice;
    }

    public String getSlipPath() {
        return slipPath;
    }

    public String getCashierName() {
        return cashierName;
    }

    // เช็คว่าการจองนี้ชนกับช่วงเวลาที่ขอมาหรือไม่ (สนามเดียวกัน วันเดียวกัน เวลาทับกัน)
    public boolean isOverlap(int court, LocalDate date, int start, int end) {
        if (this.court != court || !this.playDate.equals(date)) {
            return false;
        }
        return start < getEndHour() && end > this.startHour;
    }

    // ใช้ตอนค้นหา : ค้นจากชื่อสมาชิก หรือเบอร์โทร (พิมพ์แค่บางส่วนก็เจอ)
    public boolean matches(String keyword) {
        boolean nameMatch = member.getName().toLowerCase().contains(keyword.toLowerCase());
        boolean phoneMatch = member.getPhone().contains(keyword);
        return nameMatch || phoneMatch;
    }

    // ---- ส่วนที่มาจาก interface Income ----
    @Override
    public LocalDate getDate() {
        return paidDate;
    }

    @Override
    public int getAmount() {
        return courtPrice + rentPrice;
    }
}
