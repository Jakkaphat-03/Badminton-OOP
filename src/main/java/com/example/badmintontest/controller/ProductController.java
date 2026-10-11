package com.example.badmintontest.controller;

import com.example.badmintontest.model.Product;
import com.example.badmintontest.service.Session;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

// controller ของ Product.fxml (แท็บขายสินค้า)
public class ProductController {
    @FXML
    private TableView<Product> productTable;
    @FXML
    private TextField qtyField;

    @FXML
    private void initialize() {
        showProducts();
    }

    @FXML
    private void onSellClick() {
        Product product = productTable.getSelectionModel().getSelectedItem();   // แถวที่คลิกเลือก
        if (product == null) {
            Alerts.show("กรุณาคลิกเลือกสินค้าในตารางก่อน");
            return;
        }
        int qty;
        try {
            qty = Integer.parseInt(qtyField.getText().trim());
        } catch (Exception e) {
            Alerts.show("จำนวนต้องเป็นตัวเลข");
            return;
        }
        if (qty < 1 || qty > product.getStock()) {
            Alerts.show("จำนวนไม่ถูกต้อง หรือสินค้าไม่พอ");
            return;
        }
        Session.system.sellProduct(product, qty);
        Alerts.show("ขายแล้ว รวม " + (product.getPrice() * qty) + " บาท");
        qtyField.setText("1");
        showProducts();
    }

    private void showProducts() {
        productTable.getItems().setAll(Session.system.getProducts());
        productTable.refresh();
    }
}
