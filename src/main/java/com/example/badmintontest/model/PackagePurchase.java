package com.example.badmintontest.model;

import com.example.badmintontest.interfaces.Income;

import java.time.LocalDate;

// การซื้อแพ็กเกจเหมาจ่าย 15 ชั่วโมง 2,600 บาท
public class PackagePurchase implements Income {
    private Member member;
    private int hours;
    private int price;
    private LocalDate date;

    public PackagePurchase(Member member, int hours, int price, LocalDate date) {
        this.member = member;
        this.hours = hours;
        this.price = price;
        this.date = date;
    }

    @Override
    public LocalDate getDate() {
        return date;
    }

    @Override
    public int getAmount() {
        return price;
    }

    @Override
    public String getType() {
        return "ซื้อแพ็กเกจ";
    }

    @Override
    public String getDescription() {
        return member.getName() + " แพ็กเกจ " + hours + " ชั่วโมง";
    }
}
