package com.example.badmintontest.controller;

import com.example.badmintontest.model.Booking;
import com.example.badmintontest.model.Member;
import com.example.badmintontest.service.BookingSystem;
import com.example.badmintontest.service.Session;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.time.LocalDate;

// controller ของ Booking.fxml (แท็บจองสนาม)
public class BookingController {
    @FXML
    private TextField memberField;
    @FXML
    private TextField courtField;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Label openTimeLabel;
    @FXML
    private ComboBox<Integer> startBox;
    @FXML
    private TextField hoursField;
    @FXML
    private TextField shoesField;
    @FXML
    private TextField racketField;
    @FXML
    private Label slipLabel;
    @FXML
    private ImageView slipImage;

    private File slipFile = null;   // ไฟล์สลิปที่เลือก (เก็บแค่ตอนโปรแกรมเปิดอยู่)

    @FXML
    private void initialize() {
        datePicker.setValue(LocalDate.now());
        onDateChange();
    }

    // เมื่อเลือกวันที่ : เปลี่ยนเวลาเปิด-ปิด และรายการเวลาเริ่มให้ตรงกับวันนั้น
    @FXML
    private void onDateChange() {
        LocalDate date = datePicker.getValue();
        if (date == null) {
            return;
        }
        BookingSystem system = Session.system;
        int open = system.getOpenHour(date);     // เสาร์ อาทิตย์ วันหยุด = 10 / วันธรรมดา = 13
        int close = system.getCloseHour(date);   // เสาร์ อาทิตย์ วันหยุด = 20 / วันธรรมดา = 21

        if (system.isWeekendOrHoliday(date)) {
            openTimeLabel.setText("เสาร์-อาทิตย์/วันหยุด เปิด " + open + ".00 - " + close + ".00 น.");
        } else {
            openTimeLabel.setText("วันธรรมดา เปิด " + open + ".00 - " + close + ".00 น.");
        }

        // เวลาเริ่มที่เลือกได้ คือ ตั้งแต่เปิด จนถึง 1 ชั่วโมงก่อนปิด
        startBox.getItems().clear();
        for (int h = open; h < close; h++) {
            startBox.getItems().add(h);
        }
        startBox.setValue(open);
    }

    // ปุ่มอัปโหลดสลิป : เลือกรูปจากเครื่อง แล้วแสดงรูปให้ดู
    @FXML
    private void onUploadSlipClick() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("เลือกรูปสลิป");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("รูปภาพ", "*.png", "*.jpg", "*.jpeg"));

        File file = chooser.showOpenDialog(slipImage.getScene().getWindow());
        if (file != null) {
            slipFile = file;
            slipLabel.setText(file.getName());
            slipImage.setImage(new Image(file.toURI().toString()));
        }
    }

    @FXML
    private void onBookClick() {
        BookingSystem system = Session.system;

        // 1. หาสมาชิก
        Member member = system.findMember(memberField.getText().trim());
        if (member == null) {
            Alerts.show("ไม่พบสมาชิก (ต้องสมัครสมาชิกก่อนจึงจะจองได้)");
            return;
        }

        // 2. อ่านค่า
        LocalDate date = datePicker.getValue();
        Integer start = startBox.getValue();
        if (date == null || start == null) {
            Alerts.show("กรุณาเลือกวันที่และเวลาเริ่ม");
            return;
        }
        int court;
        int hours;
        int shoes;
        int rackets;
        try {
            court = Integer.parseInt(courtField.getText().trim());
            hours = Integer.parseInt(hoursField.getText().trim());
            shoes = Integer.parseInt(shoesField.getText().trim());
            rackets = Integer.parseInt(racketField.getText().trim());
        } catch (Exception e) {
            Alerts.show("กรุณากรอกตัวเลขให้ถูกต้อง");
            return;
        }
        String slip = "";
        if (slipFile != null) {
            slip = slipFile.getAbsolutePath();
        }

        // 3. ตรวจว่าจองได้ไหม (เช็คเวลาเปิด-ปิด สนามว่าง และสลิป)
        String error = system.checkBooking(court, date, start, hours, slip);
        if (!error.equals("")) {
            Alerts.show(error);
            return;
        }

        // 4. แสดงราคา ถามยืนยัน
        int courtPrice = system.calculateCourtPrice(member, hours);
        int rentPrice = system.calculateRentPrice(shoes, rackets);
        boolean ok = Alerts.confirm("ค่าสนาม " + courtPrice + " + ค่าเช่า " + rentPrice
                + " = " + (courtPrice + rentPrice) + " บาท\nยืนยันการจอง?");
        if (!ok) {
            return;
        }

        // 5. จองจริง
        Booking booking = system.createBooking(Session.user, member, court, date, start, hours, shoes, rackets, slip);
        Alerts.show("จองสำเร็จ รหัสจอง " + booking.getId());

        // ล้างฟอร์ม
        shoesField.setText("0");
        racketField.setText("0");
        slipFile = null;
        slipLabel.setText("ยังไม่ได้แนบสลิป");
        slipImage.setImage(null);
    }
}
