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

    // getter ด้านล่างนี้ใช้แสดงในตาราง (TableView) หน้าสรุปรายได้
    public String getProductName() {
        return product.getName();
    }

    public int getPrice() {
        return product.getPrice();
    }

    public int getQty() {
        return qty;
    }

    @Override
    public LocalDate getDate() {
        return date;
    }

    @Override
    public int getAmount() {
        return product.getPrice() * qty;
    }

    @Override
    public String getType() {
        return "ขายสินค้า";
    }

    @Override
    public String getDescription() {
        return product.getName() + " x " + qty;
    }
}
