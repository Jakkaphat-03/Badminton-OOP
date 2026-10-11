package com.example.badmintontest.controller;

import com.example.badmintontest.model.ProductSale;
import com.example.badmintontest.service.Session;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.util.List;

// controller ของ Revenue.fxml (แท็บสรุปรายได้ - ผู้ดูแลระบบ)
public class RevenueController {
    @FXML
    private TextField dayField;
    @FXML
    private TextField yearField;
    @FXML
    private TextField monthField;
    @FXML
    private Label resultLabel;
    @FXML
    private TableView<ProductSale> saleTable;
    @FXML
    private Label saleSummaryLabel;

    @FXML
    private void initialize() {
        // ใส่ค่าเริ่มต้นเป็นวันนี้
        LocalDate today = LocalDate.now();
        dayField.setText(today.toString());
        yearField.setText(String.valueOf(today.getYear()));
        monthField.setText(String.valueOf(today.getMonthValue()));
    }

    @FXML
    private void onDayClick() {
        LocalDate date;
        try {
            date = LocalDate.parse(dayField.getText().trim());
        } catch (Exception e) {
            Alerts.show("รูปแบบวันที่ไม่ถูกต้อง (ปปปป-ดด-วว)");
            return;
        }
        resultLabel.setText("รายได้รวมวันที่ " + date + " = " + Session.system.getIncomeOfDay(date) + " บาท");
        showSales(Session.system.getProductSalesOfDay(date));
    }

    @FXML
    private void onMonthClick() {
        int year;
        int month;
        try {
            year = Integer.parseInt(yearField.getText().trim());
            month = Integer.parseInt(monthField.getText().trim());
        } catch (Exception e) {
            Alerts.show("ปีและเดือนต้องเป็นตัวเลข");
            return;
        }
        resultLabel.setText("รายได้รวมเดือน " + month + "/" + year + " = "
                + Session.system.getIncomeOfMonth(year, month) + " บาท");
        showSales(Session.system.getProductSalesOfMonth(year, month));
    }

    // เอารายการขายสินค้าใส่ตาราง และรวมยอด
    private void showSales(List<ProductSale> sales) {
        saleTable.getItems().setAll(sales);

        int totalQty = 0;
        int totalMoney = 0;
        for (ProductSale s : sales) {
            totalQty = totalQty + s.getQty();
            totalMoney = totalMoney + s.getAmount();
        }

        if (sales.isEmpty()) {
            saleSummaryLabel.setText("ไม่มีการขายสินค้าในช่วงนี้");
        } else {
            saleSummaryLabel.setText("ขายสินค้าได้ทั้งหมด " + totalQty + " ชิ้น รวม " + totalMoney + " บาท");
        }
    }
}
