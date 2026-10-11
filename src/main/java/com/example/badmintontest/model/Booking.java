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
    private int courtPrice;         // ค่าสนาม
    private int shoes;              // จำนวนรองเท้าที่เช่า
    private int rackets;            // จำนวนไม้ที่เช่า
    private int rentPrice;          // ค่าเช่าอุปกรณ์
    private String slipPath;        // ไฟล์สลิปที่แนบ
    private LocalDate paidDate;     // วันที่ชำระเงิน
    private String cashierName;     // พนักงานที่กรอกข้อมูล

    public Booking(String id, Member member, int court, LocalDate playDate, int startHour, int hours,
                   int courtPrice, int shoes, int rackets, int rentPrice,
                   String slipPath, LocalDate paidDate, String cashierName) {
        this.id = id;
        this.member = member;
        this.court = court;
        this.playDate = playDate;
        this.startHour = startHour;
        this.hours = hours;
        this.courtPrice = courtPrice;
        this.shoes = shoes;
        this.rackets = rackets;
        this.rentPrice = rentPrice;
        this.slipPath = slipPath;
        this.paidDate = paidDate;
        this.cashierName = cashierName;
    }

    public String getId() {
        return id;
    }

    // ใช้แสดงชื่อสมาชิกในตาราง (TableView)
    public String getMemberName() {
        return member.getName();
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

    public int getShoes() {
        return shoes;
    }

    public int getRackets() {
        return rackets;
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

    public String getTimeText() {
        return String.format("%02d:00 - %02d:00", startHour, getEndHour());
    }

    // เช็คว่าการจองนี้ชนกับช่วงเวลาที่ขอมาหรือไม่ (สนามเดียวกัน วันเดียวกัน เวลาทับกัน)
    public boolean isOverlap(int court, LocalDate date, int start, int end) {
        if (this.court != court || !this.playDate.equals(date)) {
            return false;
        }
        return start < getEndHour() && end > this.startHour;
    }

    // ใช้ตอนค้นหา : เจอคำค้นในข้อมูลไหนก็ได้ถือว่าตรง
    public boolean matches(String keyword) {
        String k = keyword.toLowerCase();
        return id.toLowerCase().contains(k)
                || member.getId().toLowerCase().contains(k)
                || member.getName().toLowerCase().contains(k)
                || member.getPhone().contains(k)
                || String.valueOf(court).equals(k)
                || playDate.toString().contains(k);
    }

    // ใช้พิมพ์รายการจอง 1 บรรทัด
    @Override
    public String toString() {
        return id + " | " + member.getName() + " | สนาม " + court + " | " + playDate + " " + getTimeText()
                + " | ค่าสนาม " + courtPrice + " + ค่าเช่า " + rentPrice + " = " + getAmount() + " บาท"
                + " | สลิป: " + slipPath;
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

    @Override
    public String getType() {
        return "จองสนาม";
    }

    @Override
    public String getDescription() {
        return id + " " + member.getName() + " สนาม " + court + " " + playDate + " " + getTimeText();
    }
}
