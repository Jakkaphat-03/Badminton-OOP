package com.example.badmintontest;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

// ตัวเปิดหน้าต่างโปรแกรม และมีเมธอดเปลี่ยนหน้า (เปลี่ยนไฟล์ .fxml)
public class MainApp extends Application {
    private static Stage stage;

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        stage.setTitle("ระบบจองสนามแบดมินตัน");
        changeScene("/view/Login.fxml");
        stage.show();
    }

    // เปลี่ยนหน้า เช่น changeScene("/view/Main.fxml")
    public static void changeScene(String fxmlFile) {
        try {
            Parent root = FXMLLoader.load(MainApp.class.getResource(fxmlFile));
            stage.setScene(new Scene(root));
            stage.sizeToScene();
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
