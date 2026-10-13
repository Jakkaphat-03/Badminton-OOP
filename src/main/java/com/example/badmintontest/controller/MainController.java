package com.example.badmintontest.controller;

import com.example.badmintontest.MainApp;
import com.example.badmintontest.model.User;
import com.example.badmintontest.service.Session;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

// controller ของ Main.fxml (หน้าหลักที่มีแท็บ)
public class MainController {
    @FXML
    private Label userLabel;
    @FXML
    private TabPane tabPane;
    @FXML
    private Tab bookingTab;
    @FXML
    private Tab searchTab;
    @FXML
    private Tab memberTab;
    @FXML
    private Tab productTab;
    @FXML
    private Tab historyTab;
    @FXML
    private Tab revenueTab;
    @FXML
    private Tab stockTab;

    // initialize() จะถูกเรียกเองอัตโนมัติหลังโหลดไฟล์ fxml เสร็จ
    @FXML
    private void initialize() {
        User user = Session.user;
        userLabel.setText("ผู้ใช้: " + user.getFullName() + " (" + user.getRoleName() + ")");

        // ซ่อนแท็บที่ผู้ใช้ไม่มีสิทธิ์
        if (!user.canManageBooking()) {
            tabPane.getTabs().removeAll(bookingTab, searchTab, memberTab, productTab);
        }
        if (!user.canViewReport()) {
            tabPane.getTabs().removeAll(historyTab, revenueTab, stockTab);
        }
    }

    @FXML
    private void onLogoutClick() {
        Session.user = null;
        MainApp.changeScene("/view/Login.fxml");
    }
}
