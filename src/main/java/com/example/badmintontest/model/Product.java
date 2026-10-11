package com.example.badmintontest.model;

import com.example.badmintontest.interfaces.Sellable;

// สินค้าที่ขาย เช่น ไม้แบดมินตัน ลูกขนไก่ เทปพันไม้
public class Product implements Sellable {
    private String name;
    private int price;
    private int stock;

    public Product(String name, int price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void reduceStock(int qty) {
        stock -= qty;
    }

    public void addStock(int qty) {
        stock += qty;
    }
}
