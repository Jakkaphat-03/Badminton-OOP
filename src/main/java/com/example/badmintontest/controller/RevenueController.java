package com.example.badmintontest.controller;

import com.example.badmintontest.interfaces.Income;
import com.example.badmintontest.model.Booking;
import com.example.badmintontest.model.PackagePurchase;
import com.example.badmintontest.model.ProductSale;
import com.example.badmintontest.service.Session;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
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
    private TextArea summaryArea;
    @FXML
    private TextArea saleArea;

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
        showSummary("รายได้วันที่ " + date, Session.system.getIncomesOfDay(date));
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
        if (month < 1 || month > 12) {
            Alerts.show("เดือนต้องเป็น 1-12");
            return;
        }
        if (year < 1) {
            Alerts.show("ปีต้องมากกว่า 0");
            return;
        }
        showSummary("รายได้เดือน " + month + "/" + year, Session.system.getIncomesOfMonth(year, month));
        showSales(Session.system.getProductSalesOfMonth(year, month));
    }

    @FXML
    private void onYearClick() {
        int year;
        try {
            year = Integer.parseInt(yearField.getText().trim());
        } catch (Exception e) {
            Alerts.show("ปีต้องเป็นตัวเลข");
            return;
        }
        if (year < 1) {
            Alerts.show("ปีต้องมากกว่า 0");
            return;
        }
        showSummary("รายได้ปี " + year, Session.system.getIncomesOfYear(year));
        showSales(Session.system.getProductSalesOfYear(year));
    }

    // แยกรายได้ตามประเภท : ค่าสนาม / ค่าเช่าอุปกรณ์ / ขายสินค้า / แพ็กเกจ
    // ใช้ instanceof เช็คว่ารายได้แต่ละรายการเป็นคลาสไหน
    private void showSummary(String title, List<Income> list) {
        int courtMoney = 0;
        int rentMoney = 0;
        int productMoney = 0;
        int packageMoney = 0;

        for (Income i : list) {
            if (i instanceof Booking) {
                Booking b = (Booking) i;            // แปลงชนิดเป็น Booking เพื่อเรียกเมธอดของ Booking ได้
                courtMoney = courtMoney + b.getCourtPrice();
                rentMoney = rentMoney + b.getRentPrice();
            } else if (i instanceof ProductSale) {
                productMoney = productMoney + i.getAmount();
            } else if (i instanceof PackagePurchase) {
                packageMoney = packageMoney + i.getAmount();
            }
        }

        String text = title + "\n"
                + "ค่าสนาม        " + courtMoney + " บาท\n"
                + "ค่าเช่าอุปกรณ์   " + rentMoney + " บาท\n"
                + "ขายสินค้า       " + productMoney + " บาท\n"
                + "ขายแพ็กเกจ      " + packageMoney + " บาท\n"
                + "รวมทั้งหมด      " + Session.system.sumIncome(list) + " บาท";
        summaryArea.setText(text);
    }

    // แสดงรายการขายสินค้า 1 ครั้ง = 1 บรรทัด แล้วสรุปยอดบรรทัดสุดท้าย
    private void showSales(List<ProductSale> sales) {
        if (sales.isEmpty()) {
            saleArea.setText("ไม่มีการขายสินค้าในช่วงนี้");
            return;
        }

        String text = "";
        int totalQty = 0;
        int totalMoney = 0;
        for (ProductSale s : sales) {
            text = text + s.getDate()
                    + " | " + s.getProduct().getName()
                    + " | ราคา " + s.getProduct().getPrice()
                    + " x " + s.getQty()
                    + " = " + s.getAmount() + " บาท";
            if (s.isMemberDiscount()) {
                text = text + " (สมาชิกลด 5%)";
            }
            text = text + "\n";
            totalQty = totalQty + s.getQty();
            totalMoney = totalMoney + s.getAmount();
        }
        text = text + "\nขายสินค้าได้ทั้งหมด " + totalQty + " ชิ้น รวม " + totalMoney + " บาท";
        saleArea.setText(text);
    }
}
