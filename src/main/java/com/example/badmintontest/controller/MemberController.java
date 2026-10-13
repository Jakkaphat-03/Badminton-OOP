package com.example.badmintontest.controller;

import com.example.badmintontest.model.Member;
import com.example.badmintontest.service.Session;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

// controller ของ Member.fxml (แท็บสมาชิก)
public class MemberController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField packageMemberField;
    @FXML
    private TextArea memberArea;

    @FXML
    private void initialize() {
        showMembers();
    }

    @FXML
    private void onRegisterClick() {
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        if (name.equals("") || phone.equals("")) {
            Alerts.show("กรุณากรอกชื่อและเบอร์โทร");
            return;
        }
        // ห้ามสมัครเบอร์โทรซ้ำ
        if (Session.system.isPhoneUsed(phone)) {
            Alerts.show("เบอร์โทรนี้สมัครสมาชิกไปแล้ว");
            return;
        }
        Member member = Session.system.registerMember(name, phone);
        Alerts.show("สมัครสำเร็จ รหัสสมาชิก = " + member.getId()
                + "\nใช้ได้ถึงวันที่ " + member.getExpireDate());
        nameField.setText("");
        phoneField.setText("");
        showMembers();
    }

    @FXML
    private void onBuyPackageClick() {
        Member member = Session.system.findMember(packageMemberField.getText().trim());
        if (member == null) {
            Alerts.show("ไม่พบสมาชิก");
            return;
        }
        if (member.isExpired()) {
            Alerts.show("สมาชิกหมดอายุแล้ว ซื้อแพ็กเกจไม่ได้");
            return;
        }
        Session.system.buyPackage(member);
        Alerts.show("ซื้อแพ็กเกจสำเร็จ " + member.getName() + " เหลือ " + member.getPackageHoursLeft() + " ชม."
                + "\nใช้ได้ถึงวันที่ " + member.getPackageExpireDate());
        packageMemberField.setText("");
        showMembers();
    }

    // ปุ่มรีเฟรช : โหลดรายชื่อใหม่ (เช่น หลังจากไปจองในแท็บอื่นมา)
    @FXML
    private void onRefreshClick() {
        showMembers();
    }

    // แสดงรายชื่อสมาชิกทั้งหมด 1 คน = 1 บรรทัด
    private void showMembers() {
        String text = "";
        for (Member m : Session.system.getMembers()) {
            text = text + m.getId()
                    + " | " + m.getName()
                    + " | " + m.getPhone()
                    + " | จองสะสม " + m.getTotalHours() + " ชม."
                    + " | แพ็กเกจเหลือ " + m.getPackageHoursLeft() + " ชม.";
            if (m.getPackageExpireDate() != null) {
                text = text + " (ถึง " + m.getPackageExpireDate() + ")";
            }
            text = text + " | สมาชิกถึง " + m.getExpireDate() + "\n";
        }
        if (text.equals("")) {
            text = "ยังไม่มีสมาชิก";
        }
        memberArea.setText(text);
    }
}
