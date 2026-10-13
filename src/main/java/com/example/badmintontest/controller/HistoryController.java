package com.example.badmintontest.controller;

import com.example.badmintontest.model.Booking;
import com.example.badmintontest.model.Member;
import com.example.badmintontest.service.Session;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.List;

// controller ของ History.fxml (แท็บประวัติการจองของสมาชิก - ผู้ดูแลระบบ)
public class HistoryController {
    @FXML
    private TextField memberField;
    @FXML
    private TextArea resultArea;

    @FXML
    private void onShowClick() {
        Member member = Session.system.findMember(memberField.getText().trim());
        if (member == null) {
            resultArea.setText("");     // ล้างข้อมูลของสมาชิกคนก่อนหน้า
            Alerts.show("ไม่พบสมาชิก");
            return;
        }

        // บรรทัดแรก : สรุปของสมาชิก
        String text = member.getName() + " จองสะสม " + member.getTotalHours()
                + " ชม. | แพ็กเกจเหลือ " + member.getPackageHoursLeft() + " ชม.\n\n";

        // บรรทัดต่อ ๆ ไป : การจองทีละรายการ
        List<Booking> list = Session.system.getBookingsOfMember(member);
        if (list.isEmpty()) {
            text = text + "ยังไม่มีประวัติการจอง";
        }
        for (Booking b : list) {
            text = text + b.getId()
                    + " | สนาม " + b.getCourt()
                    + " | " + b.getPlayDate()
                    + " | " + b.getStartHour() + ":00 - " + b.getEndHour() + ":00"
                    + " | " + b.getHours() + " ชม."
                    + " | ค่าสนาม " + b.getCourtPrice()
                    + " + ค่าเช่า " + b.getRentPrice()
                    + " = " + b.getAmount() + " บาท"
                    + " | ชำระวันที่ " + b.getDate()
                    + "\n";
        }
        resultArea.setText(text);
    }
}
