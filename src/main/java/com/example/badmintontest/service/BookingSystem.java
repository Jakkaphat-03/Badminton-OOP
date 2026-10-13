package com.example.badmintontest.service;

import com.example.badmintontest.interfaces.Income;
import com.example.badmintontest.model.Admin;
import com.example.badmintontest.model.Booking;
import com.example.badmintontest.model.Cashier;
import com.example.badmintontest.model.Manager;
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
    public static final int PACKAGE_MONTHS = 3;     // แพ็กเกจใช้ได้ 3 เดือนนับจากวันซื้อ

    // ข้อมูลทั้งหมด
    private List<User> users = new ArrayList<>();
    private List<Member> members = new ArrayList<>();
    private List<Booking> bookings = new ArrayList<>();
    private List<Product> products = new ArrayList<>();
    private List<ProductSale> productSales = new ArrayList<>();  // รายการขายสินค้าทั้งหมด
    private List<Income> incomes = new ArrayList<>();       // รายได้ทุกชนิดเก็บรวมกันที่นี่
    private List<LocalDate> holidays = new ArrayList<>();

    // ของให้เช่า
    private RentItem shoes = new RentItem("รองเท้า", 50);
    private RentItem racket = new RentItem("ไม้แบดมินตัน", 50);
    private RentItem towel = new RentItem("ผ้าเช็ดตัว", 20);

    public BookingSystem() {
        // ผู้ใช้ (แคชเชียร์มีได้หลายคน)
        users.add(new Cashier("cashier", "1234", "สมศรี"));
        users.add(new Cashier("cashier2", "1234", "สมปอง"));
        users.add(new Admin("admin", "admin", "สมชาย"));
        users.add(new Manager("manager", "1234", "สมใจ"));

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

    // ---------- เข้าสู่ระบบ / ผู้ใช้ ----------
    public User login(String username, String password) {
        for (User u : users) {
            if (u.getUsername().equals(username) && u.checkPassword(password)) {
                return u;
            }
        }
        return null;    // หาไม่เจอ
    }

    public List<User> getUsers() {
        return users;
    }

    // นับว่าพนักงานคนนี้กรอกการจองไปแล้วกี่รายการ
    public int countBookingsBy(User user) {
        int count = 0;
        for (Booking b : bookings) {
            if (b.getCashierName().equals(user.getFullName())) {
                count++;
            }
        }
        return count;
    }

    // ---------- สมาชิก ----------
    public Member registerMember(String name, String phone) {
        String id = "M" + (members.size() + 1);
        Member member = new Member(id, name, phone);
        members.add(member);
        return member;
    }

    // เช็คว่าเบอร์โทรนี้มีคนสมัครไปแล้วหรือยัง (ห้ามสมัครเบอร์ซ้ำ)
    public boolean isPhoneUsed(String phone) {
        for (Member m : members) {
            if (m.getPhone().equals(phone)) {
                return true;
            }
        }
        return false;
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

    // หาสมาชิกที่จองชั่วโมงสะสมมากที่สุด (ยังไม่มีสมาชิก = null)
    public Member getTopMember() {
        Member top = null;
        for (Member m : members) {
            if (top == null || m.getTotalHours() > top.getTotalHours()) {
                top = m;
            }
        }
        return top;
    }

    // ซื้อแพ็กเกจ : ได้ 15 ชั่วโมง และใช้ได้ 3 เดือนนับจากวันซื้อ
    // (ถ้าซื้อเพิ่ม ชั่วโมงจะบวกเพิ่ม และวันหมดอายุนับใหม่จากวันที่ซื้อล่าสุด)
    public void buyPackage(Member member) {
        member.addPackageHours(PACKAGE_HOURS);
        member.setPackageExpireDate(LocalDate.now().plusMonths(PACKAGE_MONTHS));
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
    // จองครั้งนี้จะใช้ชั่วโมงแพ็กเกจกี่ชั่วโมง (ใช้ได้เฉพาะแพ็กเกจที่ยังไม่หมดอายุในวันที่เล่น)
    public int countPackageHoursUsed(Member member, LocalDate playDate, int hours) {
        return Math.min(member.getUsablePackageHours(playDate), hours);
    }

    // คิดค่าสนามทีละชั่วโมง
    //  - ถ้ามีชั่วโมงแพ็กเกจ (ที่ยังไม่หมดอายุ) เหลือ ใช้แพ็กเกจ (ไม่ต้องจ่ายเพิ่ม)
    //  - ถ้าเป็นชั่วโมงที่ 20, 40, 60 ... ของสมาชิก คิดแค่ 30%
    //  - นอกนั้นคิดเต็ม 180 บาท
    public int calculateCourtPrice(Member member, LocalDate playDate, int hours) {
        int packageLeft = member.getUsablePackageHours(playDate);
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

    // อีกกี่ชั่วโมงจะถึงชั่วโมงที่ได้ส่วนลด 30%
    // เช่น จองสะสม 19 ชม. → 1 (ชั่วโมงถัดไปได้ลดเลย), จองสะสม 0 ชม. → 20
    public int hoursUntilDiscount(Member member) {
        return 20 - (member.getTotalHours() % 20);
    }

    public int calculateRentPrice(int shoesCount, int racketCount, int towelCount) {
        return shoesCount * shoes.getPrice()
                + racketCount * racket.getPrice()
                + towelCount * towel.getPrice();
    }

    // ---------- จองสนาม ----------
    // สนามนี้ ว่างในช่วงเวลานี้ไหม (ไม่ชนกับการจองที่มีอยู่)
    public boolean isCourtFree(int court, LocalDate date, int startHour, int hours) {
        for (Booking b : bookings) {
            if (b.isOverlap(court, date, startHour, startHour + hours)) {
                return false;
            }
        }
        return true;
    }

    // ชั่วโมงที่ยังว่างของสนามนี้ในวันที่เลือก เช่น [13, 14, 17, 18]
    public List<Integer> getFreeHours(int court, LocalDate date) {
        List<Integer> result = new ArrayList<>();
        for (int h = getOpenHour(date); h < getCloseHour(date); h++) {
            if (isCourtFree(court, date, h, 1)) {
                result.add(h);
            }
        }
        return result;
    }

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
        if (!isCourtFree(court, date, startHour, hours)) {
            return "สนาม " + court + " ถูกจองในช่วงเวลานี้แล้ว";
        }
        return "";
    }

    // สร้างการจอง (เรียก checkBooking ให้ผ่านก่อนเสมอ)
    public Booking createBooking(User cashier, Member member, int court, LocalDate date, int startHour,
                                 int hours, int shoesCount, int racketCount, int towelCount, String slip) {
        int courtPrice = calculateCourtPrice(member, date, hours);
        int rentPrice = calculateRentPrice(shoesCount, racketCount, towelCount);
        int packageUsed = countPackageHoursUsed(member, date, hours);

        // ตัดชั่วโมงแพ็กเกจ และบวกชั่วโมงสะสมให้สมาชิก
        member.usePackageHours(packageUsed);
        member.addTotalHours(hours);

        String id = "B" + (bookings.size() + 1);
        Booking booking = new Booking(id, member, court, date, startHour, hours, courtPrice, packageUsed,
                shoesCount, racketCount, towelCount, rentPrice, slip, LocalDate.now(), cashier.getFullName());
        bookings.add(booking);
        incomes.add(booking);
        return booking;
    }

    // ค้นหาการจองจากชื่อสมาชิก หรือเบอร์โทร
    public List<Booking> searchBookings(String keyword) {
        List<Booking> result = new ArrayList<>();
        for (Booking b : bookings) {
            if (b.matches(keyword)) {
                result.add(b);
            }
        }
        return result;
    }

    // ค้นหาการจองทั้งหมดของวันที่เล่นที่เลือก
    public List<Booking> searchByDate(LocalDate date) {
        List<Booking> result = new ArrayList<>();
        for (Booking b : bookings) {
            if (b.getPlayDate().equals(date)) {
                result.add(b);
            }
        }
        return result;
    }

    // หาการจองจากรหัสจอง เช่น B1 (ไม่เจอคืน null)
    public Booking findBooking(String id) {
        for (Booking b : bookings) {
            if (b.getId().equalsIgnoreCase(id)) {
                return b;
            }
        }
        return null;
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

    // ผู้ดูแลเพิ่มสินค้าใหม่
    public void addProduct(String name, int price, int stock) {
        products.add(new Product(name, price, stock));
    }

    // ขายสินค้า (memberDiscount = true ถ้าผู้ซื้อเป็นสมาชิก ได้ลด 5%)
    public ProductSale sellProduct(Product product, int qty, boolean memberDiscount) {
        product.reduceStock(qty);
        ProductSale sale = new ProductSale(product, qty, LocalDate.now(), memberDiscount);
        productSales.add(sale);     // เก็บไว้ดูรายละเอียดการขาย
        incomes.add(sale);          // เก็บไว้รวมรายได้
        return sale;
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

    // รายการขายสินค้าของปีที่เลือก
    public List<ProductSale> getProductSalesOfYear(int year) {
        List<ProductSale> result = new ArrayList<>();
        for (ProductSale s : productSales) {
            if (s.getDate().getYear() == year) {
                result.add(s);
            }
        }
        return result;
    }

    // ---------- สรุปรายได้ ----------
    // รายการรายได้ทั้งหมดของวันที่เลือก
    public List<Income> getIncomesOfDay(LocalDate date) {
        List<Income> result = new ArrayList<>();
        for (Income i : incomes) {
            if (i.getDate().equals(date)) {
                result.add(i);
            }
        }
        return result;
    }

    // รายการรายได้ทั้งหมดของเดือนที่เลือก
    public List<Income> getIncomesOfMonth(int year, int month) {
        List<Income> result = new ArrayList<>();
        for (Income i : incomes) {
            if (i.getDate().getYear() == year && i.getDate().getMonthValue() == month) {
                result.add(i);
            }
        }
        return result;
    }

    // รายการรายได้ทั้งหมดของปีที่เลือก
    public List<Income> getIncomesOfYear(int year) {
        List<Income> result = new ArrayList<>();
        for (Income i : incomes) {
            if (i.getDate().getYear() == year) {
                result.add(i);
            }
        }
        return result;
    }

    // รวมเงินของรายการรายได้ (ไม่สนว่าเป็นรายได้ชนิดไหน เรียก getAmount() เหมือนกันหมด)
    public int sumIncome(List<Income> list) {
        int total = 0;
        for (Income i : list) {
            total += i.getAmount();
        }
        return total;
    }
}
