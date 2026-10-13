package com.example.badmintontest.controller;

import com.example.badmintontest.model.Booking;
import com.example.badmintontest.service.Session;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

// controller ของ Search.fxml (แท็บค้นหาการจอง)
public class SearchController {
    @FXML
    private TextField keywordField;
    @FXML
    private DatePicker searchDatePicker;
    @FXML
    private TextArea resultArea;
    @FXML
    private TextField bookingIdField;

    @FXML
    private void initialize() {
        searchDatePicker.setValue(LocalDate.now());
    }

    // ค้นหาจากชื่อหรือเบอร์โทร
    @FXML
    private void onSearchClick() {
        showBookings(Session.system.searchBookings(keywordField.getText().trim()));
    }

    // ค้นหาจากวันที่เล่น
    @FXML
    private void onSearchDateClick() {
        LocalDate date = searchDatePicker.getValue();
        if (date == null) {
            Alerts.show("กรุณาเลือกวันที่");
            return;
        }
        showBookings(Session.system.searchByDate(date));
    }

    // แสดงรายการจอง 1 รายการ = 1 บรรทัด (ใช้ร่วมกันทั้ง 2 แบบการค้นหา)
    private void showBookings(List<Booking> list) {
        if (list.isEmpty()) {
            resultArea.setText("ไม่พบข้อมูลการจอง");
            return;
        }
        String text = "";
        for (Booking b : list) {
            text = text + b.getId()
                    + " | " + b.getMember().getName()
                    + " | " + b.getMember().getPhone()
                    + " | สนาม " + b.getCourt()
                    + " | " + b.getPlayDate()
                    + " | " + b.getStartHour() + ":00 - " + b.getEndHour() + ":00"
                    + " | รวม " + b.getAmount() + " บาท"
                    + " | พนักงาน " + b.getCashierName()
                    + "\n";
        }
        resultArea.setText(text);
    }

    // เปิดรูปสลิปของการจองตามรหัสจองที่พิมพ์
    @FXML
    private void onShowSlipClick() {
        Booking booking = Session.system.findBooking(bookingIdField.getText().trim());
        if (booking == null) {
            Alerts.show("ไม่พบรหัสจองนี้");
            return;
        }
        File file = new File(booking.getSlipPath());
        if (!file.exists()) {
            Alerts.show("ไม่พบไฟล์สลิป (อาจถูกลบหรือย้ายไปแล้ว)");
            return;
        }

        ImageView imageView = new ImageView(new Image(file.toURI().toString()));
        imageView.setFitWidth(400);
        imageView.setFitHeight(500);
        imageView.setPreserveRatio(true);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("สลิปของ " + booking.getId());
        alert.setHeaderText(booking.getMember().getName() + " สนาม " + booking.getCourt() + " " + booking.getPlayDate());
        alert.setGraphic(null);
        alert.getDialogPane().setContent(imageView);
        alert.showAndWait();
    }
}
