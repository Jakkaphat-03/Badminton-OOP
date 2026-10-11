package com.example.badmintontest.service;

import com.example.badmintontest.model.User;

// เก็บของที่ทุกหน้าต้องใช้ร่วมกัน
//  - system : ระบบ (มีตัวเดียวทั้งโปรแกรม)
//  - user   : คนที่ login อยู่ตอนนี้
public class Session {
    public static BookingSystem system = new BookingSystem();
    public static User user = null;
}
