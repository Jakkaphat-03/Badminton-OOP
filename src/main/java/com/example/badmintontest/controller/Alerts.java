package com.example.badmintontest.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

// popup แจ้งเตือน ใช้ร่วมกันทุกหน้า
public class Alerts {

    public static void show(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    // ถามยืนยัน กด OK = true
    public static boolean confirm(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message);
        alert.setHeaderText(null);
        return alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK;
    }
}
