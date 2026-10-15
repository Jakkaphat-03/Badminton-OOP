package com.example.badmintontest.model;

import com.example.badmintontest.interfaces.Income;

import java.time.LocalDate;

// การขายสินค้า 1 ครั้ง
public class ProductSale implements Income {
    private Product product;
    private int qty;
    private LocalDate date;

    public ProductSale(Product product, int qty, LocalDate date) {
        this.product = product;
        this.qty = qty;
        this.date = date;
    }

    public Product getProduct() {
        return product;
    }

    public int getQty() {
        return qty;
    }

    // ---- ส่วนที่มาจาก interface Income ----
    @Override
    public LocalDate getDate() {
        return date;
    }

    @Override
    public int getAmount() {
        return product.getPrice() * qty;
    }
}
