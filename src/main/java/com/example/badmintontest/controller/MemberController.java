package com.example.badmintontest.controller;

import com.example.badmintontest.model.Member;
import com.example.badmintontest.service.Session;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
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
    private TableView<Member> memberTable;

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

    private void showMembers() {
        memberTable.getItems().setAll(Session.system.getMembers());
        memberTable.refresh();      // ให้ตารางวาดตัวเลขชั่วโมงใหม่
    }
}
