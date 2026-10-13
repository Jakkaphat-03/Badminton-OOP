package com.example.badmintontest.controller;

import com.example.badmintontest.model.Booking;
import com.example.badmintontest.model.Member;
import com.example.badmintontest.model.User;
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

        // บรรทัดต่อ ๆ ไป : การจองทีละรายการ และรวมยอดเงินที่จ่ายทั้งหมด
        List<Booking> list = Session.system.getBookingsOfMember(member);
        if (list.isEmpty()) {
            text = text + "ยังไม่มีประวัติการจอง";
        }
        int totalPaid = 0;
        for (Booking b : list) {
            text = text + b.getId()
                    + " | สนาม " + b.getCourt()
                    + " | " + b.getPlayDate()
                    + " | " + b.getStartHour() + ":00 - " + b.getEndHour() + ":00"
                    + " | " + b.getHours() + " ชม."
                    + " | ใช้แพ็กเกจ " + b.getPackageHoursUsed() + " ชม."
                    + " | ค่าสนาม " + b.getCourtPrice()
                    + " + ค่าเช่า " + b.getRentPrice()
                    + " = " + b.getAmount() + " บาท"
                    + " | ชำระวันที่ " + b.getDate()
                    + "\n";
            totalPaid = totalPaid + b.getAmount();
        }
        if (!list.isEmpty()) {
            text = text + "\nรวมเงินที่จ่ายค่าจองทั้งหมด " + totalPaid + " บาท";
        }
        resultArea.setText(text);
    }

    // สมาชิกที่จองชั่วโมงสะสมมากที่สุด
    @FXML
    private void onTopMemberClick() {
        Member top = Session.system.getTopMember();
        if (top == null || top.getTotalHours() == 0) {
            resultArea.setText("ยังไม่มีสมาชิกที่จองสนาม");
            return;
        }
        resultArea.setText("สมาชิกที่จองมากที่สุด : " + top.getId() + " " + top.getName()
                + " จองสะสม " + top.getTotalHours() + " ชม.");
    }

    // พนักงานแต่ละคนกรอกการจองไปกี่รายการ (นับเฉพาะผู้ใช้ที่จองได้)
    @FXML
    private void onStaffClick() {
        String text = "จำนวนการจองที่พนักงานแต่ละคนกรอก\n";
        for (User u : Session.system.getUsers()) {
            if (u.canManageBooking()) {
                text = text + u.getFullName() + " (" + u.getRoleName() + ") : "
                        + Session.system.countBookingsBy(u) + " รายการ\n";
            }
        }
        resultArea.setText(text);
    }
}
