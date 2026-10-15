package com.example.badmintontest.controller;

import com.example.badmintontest.model.Product;
import com.example.badmintontest.service.Session;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.List;

// controller ของ Product.fxml (แท็บขายสินค้า)
public class ProductController {
    @FXML
    private TextArea productArea;
    @FXML
    private TextField numberField;
    @FXML
    private TextField qtyField;

    @FXML
    private void initialize() {
        showProducts();
    }

    @FXML
    private void onSellClick() {
        int number;
        int qty;
        try {
            number = Integer.parseInt(numberField.getText().trim());
            qty = Integer.parseInt(qtyField.getText().trim());
        } catch (Exception e) {
            Alerts.show("หมายเลขสินค้าและจำนวนต้องเป็นตัวเลข");
            return;
        }

        List<Product> products = Session.system.getProducts();
        if (number < 1 || number > products.size()) {
            Alerts.show("ไม่มีสินค้าหมายเลขนี้");
            return;
        }
        Product product = products.get(number - 1);     // หมายเลข 1 = ตำแหน่งที่ 0 ใน List

        if (qty < 1 || qty > product.getStock()) {
            Alerts.show("จำนวนไม่ถูกต้อง หรือสินค้าไม่พอ");
            return;
        }
        Session.system.sellProduct(product, qty);
        Alerts.show("ขาย " + product.getName() + " " + qty + " ชิ้น รวม " + (product.getPrice() * qty) + " บาท");
        qtyField.setText("1");
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
