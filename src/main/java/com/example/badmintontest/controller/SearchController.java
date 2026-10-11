package com.example.badmintontest.controller;

import com.example.badmintontest.model.Booking;
import com.example.badmintontest.service.Session;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

// controller ของ Search.fxml (แท็บค้นหาการจอง)
public class SearchController {
    @FXML
    private TextField keywordField;
    @FXML
    private TableView<Booking> bookingTable;

    @FXML
    private void onSearchClick() {
        bookingTable.getItems().setAll(Session.system.searchBookings(keywordField.getText().trim()));
    }

    // เปิดรูปสลิปของการจองที่คลิกเลือกในตาราง
    @FXML
    private void onShowSlipClick() {
        Booking booking = bookingTable.getSelectionModel().getSelectedItem();
        if (booking == null) {
            Alerts.show("กรุณาคลิกเลือกรายการจองในตารางก่อน");
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
        alert.setHeaderText(booking.getMemberName() + " สนาม " + booking.getCourt() + " " + booking.getPlayDate());
        alert.setGraphic(null);
        alert.getDialogPane().setContent(imageView);
        alert.showAndWait();
    }
}
