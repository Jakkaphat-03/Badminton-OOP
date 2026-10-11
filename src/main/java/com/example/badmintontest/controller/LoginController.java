package com.example.badmintontest.controller;

import com.example.badmintontest.MainApp;
import com.example.badmintontest.model.User;
import com.example.badmintontest.service.Session;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

// controller ของ Login.fxml
public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    @FXML
    private void onLoginClick() {
        User user = Session.system.login(usernameField.getText(), passwordField.getText());
        if (user == null) {
            Alerts.show("ชื่อผู้ใช้หรือรหัสผ่านไม่ถูกต้อง");
        } else {
            Session.user = user;
            MainApp.changeScene("/view/Main.fxml");
        }
    }
}
