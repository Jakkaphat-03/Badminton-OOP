package com.example.badmintontest.service;

import com.example.badmintontest.interfaces.Income;
import com.example.badmintontest.model.Admin;
import com.example.badmintontest.model.Booking;
import com.example.badmintontest.model.Cashier;
import com.example.badmintontest.model.Member;
import com.example.badmintontest.model.PackagePurchase;
import com.example.badmintontest.model.Product;
import com.example.badmintontest.model.ProductSale;
import com.example.badmintontest.model.RentItem;
import com.example.badmintontest.model.User;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// คลาสหลักของระบบ เก็บข้อมูลทั้งหมด (เก็บใน List ไม่ได้บันทึกลงไฟล์) และมีกฎต่าง ๆ ของร้าน
public class BookingSystem {

    // ค่าคงที่ของร้าน
    public static final int COURT_COUNT = 12;       // จำนวนสนาม
    public static final int PRICE_PER_HOUR = 180;   // ราคา/ชั่วโมง
    public static final int PACKAGE_HOURS = 15;     // แพ็กเกจ 15 ชั่วโมง
    public static final int PACKAGE_PRICE = 2600;   // ราคาแพ็กเกจ

    // ข้อมูลทั้งหมด
    private List<User> users = new ArrayList<>();
    private List<Member> members = new ArrayList<>();
    private List<Booking> bookings = new ArrayList<>();
    private List<Product> products = new ArrayList<>();
    private List<ProductSale> productSales = new ArrayList<>();  // รายการขายสินค้าทั้งหมด
    private List<Income> incomes = new ArrayList<>();       // รายได้ทุกชนิดเก็บรวมกันที่นี่
    private List<LocalDate> holidays = new ArrayList<>();

    private RentItem shoes = new RentItem("รองเท้า", 50);
    private RentItem racket = new RentItem("ไม้แบดมินตัน", 50);

    public BookingSystem() {
        // ผู้ใช้
        users.add(new Cashier("cashier", "1234", "สมศรี"));
        users.add(new Admin("admin", "admin", "สมชาย"));

        // สินค้า (ตั้งราคาเอง)
        products.add(new Product("ไม้แบดมินตัน", 890, 10));
        products.add(new Product("ลูกขนไก่", 150, 30));
        products.add(new Product("เทปพันไม้", 40, 50));

        // วันหยุดนักขัตฤกษ์ (ตัวอย่าง)
        holidays.add(LocalDate.of(2026, 10, 13));
        holidays.add(LocalDate.of(2026, 10, 23));
        holidays.add(LocalDate.of(2026, 12, 5));
        holidays.add(LocalDate.of(2026, 12, 10));
        holidays.add(LocalDate.of(2026, 12, 31));
    }

    // ---------- เข้าสู่ระบบ ----------
    public User login(String username, String password) {
        for (User u : users) {
            if (u.getUsername().equals(username) && u.checkPassword(password)) {
                return u;
            }
        }
        return null;    // หาไม่เจอ
    }

    // ---------- สมาชิก ----------
    public Member registerMember(String name, String phone) {
        String id = "M" + (members.size() + 1);
        Member member = new Member(id, name, phone);
        members.add(member);
        return member;
    }

    public Member findMember(String id) {
        for (Member m : members) {
            if (m.getId().equalsIgnoreCase(id)) {
                return m;
            }
        }
        return null;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void buyPackage(Member member) {
        member.addPackageHours(PACKAGE_HOURS);
        incomes.add(new PackagePurchase(member, PACKAGE_HOURS, PACKAGE_PRICE, LocalDate.now()));
    }

    // ---------- เวลาเปิด-ปิด ----------
    // เสาร์ อาทิตย์ วันหยุด เปิด 10.00 - 20.00 / วันธรรมดา เปิด 13.00 - 21.00
    public boolean isWeekendOrHoliday(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY || holidays.contains(date);
    }

    public int getOpenHour(LocalDate date) {
        if (isWeekendOrHoliday(date)) {
            return 10;
        }
        return 13;
    }

    public int getCloseHour(LocalDate date) {
        if (isWeekendOrHoliday(date)) {
            return 20;
        }
        return 21;
    }

    // ---------- คิดราคา ----------
    // คิดค่าสนามทีละชั่วโมง
    //  - ถ้ามีชั่วโมงแพ็กเกจเหลือ ใช้แพ็กเกจ (ไม่ต้องจ่ายเพิ่ม)
    //  - ถ้าเป็นชั่วโมงที่ 20, 40, 60 ... ของสมาชิก คิดแค่ 30%
    //  - นอกนั้นคิดเต็ม 180 บาท
    public int calculateCourtPrice(Member member, int hours) {
        int packageLeft = member.getPackageHoursLeft();
        int total = 0;
        for (int i = 1; i <= hours; i++) {
            int hourNumber = member.getTotalHours() + i;   // ชั่วโมงนี้เป็นชั่วโมงที่เท่าไหร่ของสมาชิก
            if (packageLeft > 0) {
                packageLeft = packageLeft - 1;
            } else if (hourNumber % 20 == 0) {
                total = total + PRICE_PER_HOUR * 30 / 100;
            } else {
                total = total + PRICE_PER_HOUR;
            }
        }
        return total;
    }

    public int calculateRentPrice(int shoesCount, int racketCount) {
        return shoesCount * shoes.getPrice() + racketCount * racket.getPrice();
    }

    // ---------- จองสนาม ----------
    // ตรวจว่าจองได้ไหม ถ้าจองได้ return "" (ข้อความว่าง) ถ้าจองไม่ได้ return เหตุผล
    public String checkBooking(int court, LocalDate date, int startHour, int hours, String slip) {
        if (court < 1 || court > COURT_COUNT) {
            return "สนามต้องเป็นหมายเลข 1-" + COURT_COUNT;
        }
        if (date.isBefore(LocalDate.now())) {
            return "จองวันที่ผ่านมาแล้วไม่ได้";
        }
        if (hours < 1 || startHour < getOpenHour(date) || startHour + hours > getCloseHour(date)) {
            return "วันนั้นเปิด " + getOpenHour(date) + ".00 - " + getCloseHour(date)
                    + ".00 น. เวลาที่เลือกอยู่นอกเวลาทำการ";
        }
        if (slip.trim().isEmpty()) {
            return "ต้องแนบสลิปการชำระเงิน";
        }
        for (Booking b : bookings) {
            if (b.isOverlap(court, date, startHour, startHour + hours)) {
                return "สนาม " + court + " ถูกจองในช่วงเวลานี้แล้ว";
            }
        }
        return "";
    }

    // สร้างการจอง (เรียก checkBooking ให้ผ่านก่อนเสมอ)
    public Booking createBooking(User cashier, Member member, int court, LocalDate date, int startHour,
                                 int hours, int shoesCount, int racketCount, String slip) {
        int courtPrice = calculateCourtPrice(member, hours);
        int rentPrice = calculateRentPrice(shoesCount, racketCount);

        // ตัดชั่วโมงแพ็กเกจ และบวกชั่วโมงสะสมให้สมาชิก
        int packageUsed = Math.min(member.getPackageHoursLeft(), hours);
        member.usePackageHours(packageUsed);
        member.addTotalHours(hours);

        String id = "B" + (bookings.size() + 1);
        Booking booking = new Booking(id, member, court, date, startHour, hours, courtPrice,
                shoesCount, racketCount, rentPrice, slip, LocalDate.now(), cashier.getFullName());
        bookings.add(booking);
        incomes.add(booking);
        return booking;
    }

    // ค้นหาการจองจากคำค้น (ชื่อ, รหัสสมาชิก, เบอร์โทร, รหัสจอง, หมายเลขสนาม, วันที่)
    public List<Booking> searchBookings(String keyword) {
        List<Booking> result = new ArrayList<>();
        for (Booking b : bookings) {
            if (b.matches(keyword)) {
                result.add(b);
            }
        }
        return result;
    }

    public List<Booking> getBookingsOfMember(Member member) {
        List<Booking> result = new ArrayList<>();
        for (Booking b : bookings) {
            if (b.getMember() == member) {
                result.add(b);
            }
        }
        return result;
    }

    // ---------- สินค้า ----------
    public List<Product> getProducts() {
        return products;
    }

    public void sellProduct(Product product, int qty) {
        product.reduceStock(qty);
        ProductSale sale = new ProductSale(product, qty, LocalDate.now());
        productSales.add(sale);     // เก็บไว้ดูรายละเอียดการขาย
        incomes.add(sale);          // เก็บไว้รวมรายได้
    }

    // รายการขายสินค้าของวันที่เลือก
    public List<ProductSale> getProductSalesOfDay(LocalDate date) {
        List<ProductSale> result = new ArrayList<>();
        for (ProductSale s : productSales) {
            if (s.getDate().equals(date)) {
                result.add(s);
            }
        }
        return result;
    }

    // รายการขายสินค้าของเดือนที่เลือก
    public List<ProductSale> getProductSalesOfMonth(int year, int month) {
        List<ProductSale> result = new ArrayList<>();
        for (ProductSale s : productSales) {
            if (s.getDate().getYear() == year && s.getDate().getMonthValue() == month) {
                result.add(s);
            }
        }
        return result;
    }

    // ---------- สรุปรายได้ ----------
    public int getIncomeOfDay(LocalDate date) {
        int total = 0;
        for (Income i : incomes) {
            if (i.getDate().equals(date)) {
                total += i.getAmount();
            }
        }
        return total;
    }

    public int getIncomeOfMonth(int year, int month) {
        int total = 0;
        for (Income i : incomes) {
            if (i.getDate().getYear() == year && i.getDate().getMonthValue() == month) {
                total += i.getAmount();
            }
        }
        return total;
    }
}
