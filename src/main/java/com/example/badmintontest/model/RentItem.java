package com.example.badmintontest.model;

import com.example.badmintontest.interfaces.Sellable;

// อุปกรณ์ให้เช่า (รองเท้า / ไม้แบดมินตัน) คู่ละ 50 บาท/ครั้ง/วัน
public class RentItem implements Sellable {
    private String name;
    private int price;

    public RentItem(String name, int price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getPrice() {
        return price;
    }
}
