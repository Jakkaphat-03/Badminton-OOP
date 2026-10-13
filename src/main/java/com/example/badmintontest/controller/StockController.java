package com.example.badmintontest.controller;

import com.example.badmintontest.model.Product;
import com.example.badmintontest.service.Session;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.List;

// controller ของ Stock.fxml (แท็บจัดการสินค้า - ผู้ดูแลระบบ)
public class StockController {
    @FXML
    private TextArea productArea;
    @FXML
    private TextField nameField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField stockField;
    @FXML
    private TextField numberField;
    @FXML
    private TextField addQtyField;

    @FXML
    private void initialize() {
        showProducts();
    }

    // เพิ่มสินค้าใหม่
    @FXML
    private void onAddProductClick() {
        String name = nameField.getText().trim();
        if (name.equals("")) {
            Alerts.show("กรุณากรอกชื่อสินค้า");
            return;
        }
        int price;
        int stock;
        try {
            price = Integer.parseInt(priceField.getText().trim());
            stock = Integer.parseInt(stockField.getText().trim());
        } catch (Exception e) {
            Alerts.show("ราคาและจำนวนต้องเป็นตัวเลข");
            return;
        }
        if (price < 1 || stock < 0) {
            Alerts.show("ราคาต้องมากกว่า 0 และจำนวนต้องไม่ติดลบ");
            return;
        }
        Session.system.addProduct(name, price, stock);
        Alerts.show("เพิ่มสินค้า " + name + " แล้ว");
        nameField.setText("");
        priceField.setText("");
        stockField.setText("");
        showProducts();
    }

    // เติมสต็อกสินค้าที่มีอยู่แล้ว
    @FXML
    private void onAddStockClick() {
        int number;
        int qty;
        try {
            number = Integer.parseInt(numberField.getText().trim());
            qty = Integer.parseInt(addQtyField.getText().trim());
        } catch (Exception e) {
            Alerts.show("หมายเลขสินค้าและจำนวนต้องเป็นตัวเลข");
            return;
        }
        List<Product> products = Session.system.getProducts();
        if (number < 1 || number > products.size()) {
            Alerts.show("ไม่มีสินค้าหมายเลขนี้");
            return;
        }
        if (qty < 1) {
            Alerts.show("จำนวนที่เติมต้องมากกว่า 0");
            return;
        }
        Product product = products.get(number - 1);
        product.addStock(qty);
        Alerts.show("เติม " + product.getName() + " อีก " + qty + " ชิ้น (เหลือ " + product.getStock() + ")");
        numberField.setText("");
        addQtyField.setText("");
        showProducts();
    }

    // แสดงสินค้าทั้งหมด พร้อมหมายเลข 1, 2, 3, ...
    private void showProducts() {
        List<Product> products = Session.system.getProducts();
        String text = "";
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            text = text + (i + 1) + ". " + p.getName()
                    + " | ราคา " + p.getPrice() + " บาท"
                    + " | เหลือ " + p.getStock() + " ชิ้น"
                    + "\n";
        }
        productArea.setText(text);
    }
}
