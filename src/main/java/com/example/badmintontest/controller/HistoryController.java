package com.example.badmintontest.controller;

import com.example.badmintontest.model.Booking;
import com.example.badmintontest.model.Member;
import com.example.badmintontest.service.Session;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

// controller ของ History.fxml (แท็บประวัติการจองของสมาชิก - ผู้ดูแลระบบ)
public class HistoryController {
    @FXML
    private TextField memberField;
    @FXML
    private Label summaryLabel;
    @FXML
    private TableView<Booking> bookingTable;

    @FXML
    private void onShowClick() {
        Member member = Session.system.findMember(memberField.getText().trim());
        if (member == null) {
            Alerts.show("ไม่พบสมาชิก");
            return;
        }
        bookingTable.getItems().setAll(Session.system.getBookingsOfMember(member));
        summaryLabel.setText(member.getName() + " จองสะสม " + member.getTotalHours()
                + " ชม. | แพ็กเกจเหลือ " + member.getPackageHoursLeft() + " ชม.");
    }
}
