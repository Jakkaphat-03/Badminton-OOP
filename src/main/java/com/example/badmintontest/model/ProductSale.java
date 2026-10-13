package com.example.badmintontest.model;

import com.example.badmintontest.interfaces.Income;

import java.time.LocalDate;

// การขายสินค้า 1 ครั้ง
public class ProductSale implements Income {
    private Product product;
    private int qty;
    private LocalDate date;
    private boolean memberDiscount;     // true = ผู้ซื้อเป็นสมาชิก ได้ลด 5%

    public ProductSale(Product product, int qty, LocalDate date, boolean memberDiscount) {
        this.product = product;
        this.qty = qty;
        this.date = date;
        this.memberDiscount = memberDiscount;
    }

    public Product getProduct() {
        return product;
    }

    public int getQty() {
        return qty;
    }

    public boolean isMemberDiscount() {
        return memberDiscount;
    }

    // ---- ส่วนที่มาจาก interface Income ----
    @Override
    public LocalDate getDate() {
        return date;
    }

    @Override
    public int getAmount() {
        int fullPrice = product.getPrice() * qty;
        if (memberDiscount) {
            return fullPrice * 95 / 100;    // สมาชิกจ่าย 95% (ลด 5%)
        }
        return fullPrice;
    }
}
