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
        Member member = Session.system.registerMember(name, phone);
        Alerts.show("สมัครสำเร็จ รหัสสมาชิก = " + member.getId());
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
        Session.system.buyPackage(member);
        Alerts.show("ซื้อแพ็กเกจสำเร็จ " + member.getName() + " เหลือ " + member.getPackageHoursLeft() + " ชม.");
        packageMemberField.setText("");
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
                    + " | แพ็กเกจเหลือ " + m.getPackageHoursLeft() + " ชม."
                    + "\n";
        }
        if (text.equals("")) {
            text = "ยังไม่มีสมาชิก";
        }
        memberArea.setText(text);
    }
}
