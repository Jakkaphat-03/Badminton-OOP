package com.example.badmintontest.interfaces;

import java.time.LocalDate;

// interface: รายการที่ทำให้ร้านมีรายได้ (การจอง, ขายสินค้า, ซื้อแพ็กเกจ)
// ผู้ดูแลระบบใช้ interface ตัวนี้ตัวเดียวในการรวมรายได้ โดยไม่ต้องสนใจว่าเป็นรายได้ชนิดไหน
public interface Income {
    LocalDate getDate();        // วันที่ชำระเงิน

    int getAmount();            // จำนวนเงิน
}
