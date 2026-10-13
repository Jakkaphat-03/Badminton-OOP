package com.example.badmintontest.controller;

import com.example.badmintontest.model.Member;
import com.example.badmintontest.model.Product;
import com.example.badmintontest.model.ProductSale;
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
    private TextField memberField;

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

        // ถ้ากรอกรหัสสมาชิก → ต้องเป็นสมาชิกจริงและยังไม่หมดอายุ ถึงจะได้ลด 5%
        boolean memberDiscount = false;
        String memberId = memberField.getText().trim();
        if (!memberId.equals("")) {
            Member member = Session.system.findMember(memberId);
            if (member == null) {
                Alerts.show("ไม่พบสมาชิก (ถ้าไม่ใช่สมาชิกให้เว้นช่องว่าง)");
                return;
            }
            if (member.isExpired()) {
                Alerts.show("สมาชิกหมดอายุแล้ว ไม่ได้ส่วนลด");
                return;
            }
            memberDiscount = true;
        }

        ProductSale sale = Session.system.sellProduct(product, qty, memberDiscount);
        String text = "ขาย " + product.getName() + " " + qty + " ชิ้น รวม " + sale.getAmount() + " บาท";
        if (memberDiscount) {
            text = text + " (สมาชิกลด 5%)";
        }
        Alerts.show(text);
        qtyField.setText("1");
        memberField.setText("");
        showProducts();
    }

    // ปุ่มรีเฟรช : โหลดรายการสินค้าใหม่ (เช่น หลังผู้ดูแลเพิ่มสินค้า/เติมสต็อก)
    @FXML
    private void onRefreshClick() {
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
