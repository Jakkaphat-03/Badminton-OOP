package com.example.badmintontest;

import javafx.application.Application;

// จุดเริ่มต้นของโปรแกรม (กดรันไฟล์นี้ใน IntelliJ)
// แยกออกมาจาก MainApp เพื่อให้รันได้โดยไม่ติด error "JavaFX runtime components are missing"
public class Main {
    public static void main(String[] args) {
        Application.launch(MainApp.class, args);
    }
}
