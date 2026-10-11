# ระบบจองสนามแบดมินตัน (Badminton-OOP)

โปรแกรมตัวอย่างสำหรับฝึกเขียน OOP ด้วย Java + JavaFX (ออกแบบหน้าจอด้วย Scene Builder)

- มีสนาม 12 สนาม ราคา 180 บาท/ชั่วโมง
- วันธรรมดาเปิด 13.00–21.00 น. / เสาร์ อาทิตย์ วันหยุดนักขัตฤกษ์ เปิด 10.00–20.00 น.
- แพ็กเกจ 15 ชั่วโมง 2,600 บาท / ชั่วโมงที่ 20 ของสมาชิกคิดแค่ 30%
- เช่ารองเท้า/ไม้ 50 บาท / ขายอุปกรณ์ / สรุปรายได้รายวัน-รายเดือน

> ⚠️ ข้อมูลทั้งหมดเก็บในหน่วยความจำเท่านั้น (ไม่มีฐานข้อมูล) ปิดโปรแกรมแล้วข้อมูลหายหมด

---

## 1. สิ่งที่ต้องมีในเครื่อง

| โปรแกรม | เวอร์ชัน | หมายเหตุ |
|---|---|---|
| JDK | 17 ขึ้นไป | ถ้ายังไม่มี ให้ดาวน์โหลดผ่าน IntelliJ ได้ (ดูข้อ 3.2) |
| IntelliJ IDEA | Community หรือ Ultimate | |
| Git | เวอร์ชันไหนก็ได้ | ใช้ clone โปรเจกต์ |
| Scene Builder | ไม่บังคับ | ใช้เมื่ออยากแก้หน้าจอแบบลากวาง |

ไม่ต้องติดตั้ง JavaFX และ Maven เอง — Maven จะดาวน์โหลด JavaFX ให้อัตโนมัติ และโปรเจกต์มี Maven Wrapper (`mvnw`) มาให้แล้ว

---

## 2. Clone โปรเจกต์

```bash
git clone https://github.com/Jakkaphat-03/Badminton-OOP.git
```

หรือใน IntelliJ: หน้าแรก **Get from VCS** → วางลิงก์ด้านบน → **Clone**

---

## 3. ตั้งค่า IntelliJ

### 3.1 เปิดโปรเจกต์
**File → Open** → เลือกโฟลเดอร์ `Badminton-OOP` (โฟลเดอร์ที่มีไฟล์ `pom.xml`) → ถ้าถามให้เลือก **Trust Project**

IntelliJ จะรู้เองว่าเป็นโปรเจกต์ Maven และเริ่มดาวน์โหลด library (ดูแถบความคืบหน้าด้านล่างขวา รอจนเสร็จ)

### 3.2 ตั้งค่า JDK (สำคัญ)
ไฟล์ตั้งค่าในโปรเจกต์ระบุ JDK ชื่อ `openjdk-26` ซึ่งเครื่องของคุณอาจไม่มี ถ้าขึ้นแถบเหลือง **"Project JDK is not defined"** หรือ **"Setup SDK"** ให้ทำดังนี้

1. **File → Project Structure** (`Ctrl + Alt + Shift + S`)
2. เมนูซ้ายเลือก **Project**
3. ช่อง **SDK** → เลือก JDK ที่มีในเครื่อง (17 ขึ้นไป)
   - ถ้าไม่มีเลย เลือก **Add SDK → Download JDK...** → เลือกเวอร์ชัน 17 หรือ 21 → **Download**
4. ช่อง **Language level** → เลือก **SDK default**
5. กด **OK**

### 3.3 โหลด Maven ใหม่
เปิดแท็บ **Maven** ทางขวาของหน้าจอ → กดปุ่ม 🔄 **Reload All Maven Projects**

(หรือคลิกขวาที่ `pom.xml` → **Maven → Reload project**)

### 3.4 ตั้งค่าภาษาไทย (ถ้าภาษาไทยในโค้ดเป็น ??? หรืออ่านไม่ออก)
**File → Settings → Editor → File Encodings** → ตั้งทุกช่องเป็น **UTF-8**

### 3.5 ไฟล์ในโฟลเดอร์ `.idea` คืออะไร
`.idea` คือโฟลเดอร์ตั้งค่าของ IntelliJ ไม่ใช่โค้ดโปรแกรม

| ไฟล์ | ใช้ทำอะไร |
|---|---|
| `.idea/misc.xml` | บอกว่าโปรเจกต์ใช้ JDK ตัวไหน และเป็นโปรเจกต์ Maven |
| `.idea/encodings.xml` | บอกให้อ่านไฟล์โค้ดเป็น UTF-8 (ภาษาไทยจะได้ไม่เพี้ยน) |
| `.idea/.gitignore` | บอก Git ว่าไม่ต้องเก็บไฟล์ชั่วคราวของ IntelliJ |

ถ้าตั้งค่าแล้วยังมีปัญหาแปลก ๆ ให้ปิด IntelliJ → ลบโฟลเดอร์ `.idea` ทิ้ง → เปิดโปรเจกต์ใหม่ (IntelliJ จะสร้าง `.idea` ใหม่ให้เอง) แล้วทำข้อ 3.2–3.3 อีกครั้ง

---

## 4. วิธีรันโปรแกรม

### วิธีที่ 1: รันใน IntelliJ (ง่ายที่สุด)
เปิดไฟล์ `src/main/java/com/example/badmintontest/Main.java` → กดปุ่ม ▶ สีเขียวข้าง `public static void main` → **Run 'Main.main()'**

> ต้องรัน **`Main`** ไม่ใช่ `MainApp` ถ้ารัน `MainApp` จะขึ้น error `JavaFX runtime components are missing`

### วิธีที่ 2: รันด้วยคำสั่ง Maven
เปิด Terminal ใน IntelliJ (`Alt + F12`) แล้วพิมพ์

Windows:
```bash
mvnw.cmd javafx:run
```

Mac / Linux:
```bash
./mvnw javafx:run
```

ถ้าขึ้น error ว่าหา Java ไม่เจอ (`JAVA_HOME not found`) ให้ใช้วิธีที่ 1 แทน หรือตั้งค่า environment variable `JAVA_HOME` ให้ชี้ไปที่โฟลเดอร์ JDK

---

## 5. บัญชีสำหรับเข้าสู่ระบบ

| บทบาท | ชื่อผู้ใช้ | รหัสผ่าน | ทำอะไรได้ |
|---|---|---|---|
| พนักงานแคชเชียร์ | `cashier` | `1234` | จองสนาม, ค้นหาการจอง, สมัครสมาชิก/ซื้อแพ็กเกจ, ขายสินค้า |
| ผู้ดูแลระบบ | `admin` | `admin` | ดูประวัติการจองของสมาชิก, สรุปรายได้รายวัน/รายเดือน |

### ลองใช้งานตามลำดับนี้
1. เข้าระบบด้วย `cashier` → แท็บ **สมาชิก** → สมัครสมาชิก (จะได้รหัส เช่น `M1`)
2. แท็บ **จองสนาม** → ใส่รหัส `M1` → เลือกสนาม วันที่ เวลา → กด **อัปโหลดสลิป** เลือกรูปอะไรก็ได้ → **จองสนาม**
3. แท็บ **ขายสินค้า** → คลิกเลือกสินค้าในตาราง → ใส่จำนวน → **ขายสินค้าที่เลือก**
4. กด **ออกจากระบบ** → เข้าด้วย `admin` → แท็บ **สรุปรายได้** → กดดูรายได้รายวัน/รายเดือน

---

## 6. แก้หน้าจอด้วย Scene Builder

### 6.1 บอก IntelliJ ว่า Scene Builder อยู่ที่ไหน (ทำครั้งเดียว)
**File → Settings → Languages & Frameworks → JavaFX** → ช่อง **Path to SceneBuilder** → เลือกไฟล์

- Windows: `C:\Users\<ชื่อผู้ใช้>\AppData\Local\SceneBuilder\SceneBuilder.exe`
- Mac: `/Applications/SceneBuilder.app`

### 6.2 เปิดไฟล์หน้าจอ
ไฟล์หน้าจออยู่ที่ `src/main/resources/view/` → คลิกขวาที่ไฟล์ `.fxml` → **Open In SceneBuilder**

| ไฟล์หน้าจอ | Controller (โค้ดที่ทำงานเมื่อกดปุ่ม) |
|---|---|
| `Login.fxml` | `LoginController.java` |
| `Main.fxml` (หน้าที่มีแท็บ) | `MainController.java` |
| `Booking.fxml` | `BookingController.java` |
| `Search.fxml` | `SearchController.java` |
| `Member.fxml` | `MemberController.java` |
| `Product.fxml` | `ProductController.java` |
| `History.fxml` | `HistoryController.java` |
| `Revenue.fxml` | `RevenueController.java` |

### 6.3 ถ้าเพิ่มปุ่ม / ช่องกรอกใหม่
1. ใน Scene Builder เลือกตัวที่เพิ่ม → แผง **Code** ทางขวา
   - ช่องกรอก/Label: ใส่ **fx:id** เช่น `phoneField`
   - ปุ่ม: ใส่ **On Action** เช่น `onSaveClick`
2. บันทึก (`Ctrl + S`)
3. ไปที่ Controller แล้วเพิ่มโค้ดชื่อเดียวกัน
   ```java
   @FXML
   private TextField phoneField;

   @FXML
   private void onSaveClick() {
       // โค้ดที่ทำงานเมื่อกดปุ่ม
   }
   ```

> ใน `Main.fxml` แต่ละแท็บจะเห็นเป็นกล่อง include เฉย ๆ ถ้าจะแก้หน้าในแท็บ ให้เปิดไฟล์ของแท็บนั้นโดยตรง

---

## 7. โครงสร้างโปรเจกต์

```
src/main/
├── java/com/example/badmintontest/
│   ├── Main.java              จุดเริ่มโปรแกรม (กดรันไฟล์นี้)
│   ├── MainApp.java           เปิดหน้าต่าง + เปลี่ยนหน้า (changeScene)
│   ├── interfaces/
│   │   ├── Sellable.java      interface ของที่ขาย/เช่าได้ (มีชื่อ + ราคา)
│   │   └── Income.java        interface รายการที่เป็นรายได้
│   ├── model/
│   │   ├── User.java          abstract class ผู้ใช้ระบบ
│   │   ├── Cashier.java       แคชเชียร์ (extends User)
│   │   ├── Admin.java         ผู้ดูแลระบบ (extends User)
│   │   ├── Member.java        สมาชิก
│   │   ├── Product.java       สินค้า (implements Sellable)
│   │   ├── RentItem.java      ของให้เช่า (implements Sellable)
│   │   ├── Booking.java       การจอง (implements Income)
│   │   ├── ProductSale.java   การขายสินค้า (implements Income)
│   │   └── PackagePurchase.java  การซื้อแพ็กเกจ (implements Income)
│   ├── service/
│   │   ├── BookingSystem.java เก็บข้อมูลทั้งหมด + กฎของร้าน (ราคา เวลาเปิด-ปิด ฯลฯ)
│   │   └── Session.java       เก็บระบบ และคนที่ login อยู่
│   └── controller/            โค้ดของแต่ละหน้าจอ
└── resources/view/            ไฟล์หน้าจอ .fxml (เปิดด้วย Scene Builder)
```

### ส่วนที่เป็น OOP
- **abstract class** — `User` มีเมธอด abstract `getRoleName()`, `canManageBooking()`, `canViewReport()` ให้ `Cashier` และ `Admin` เขียนเอง → หน้าหลักใช้เมธอดเหล่านี้ตัดสินว่าจะโชว์แท็บไหน
- **interface** — `Income` ถูก implements โดย `Booking`, `ProductSale`, `PackagePurchase` → `BookingSystem` รวมรายได้ทุกชนิดใน `List<Income>` เดียว โดยเรียก `getAmount()` เหมือนกันหมด
- **interface** — `Sellable` ถูก implements โดย `Product` และ `RentItem`

### อยากแก้ค่าต่าง ๆ ของร้าน
เปิด `service/BookingSystem.java`
- ราคา/จำนวนสนาม/แพ็กเกจ → ค่าคงที่ด้านบนของไฟล์ (`PRICE_PER_HOUR`, `COURT_COUNT`, ...)
- ชื่อผู้ใช้/รหัสผ่าน, สินค้าเริ่มต้น, วันหยุดนักขัตฤกษ์ → ใน constructor `BookingSystem()`
- เวลาเปิด-ปิด → เมธอด `getOpenHour()` และ `getCloseHour()`

---

## 8. ปัญหาที่พบบ่อย

| อาการ | วิธีแก้ |
|---|---|
| `JavaFX runtime components are missing` | รัน `Main.java` ไม่ใช่ `MainApp.java` |
| `package javafx... does not exist` / import เป็นสีแดง | แท็บ Maven → กด 🔄 Reload และรอดาวน์โหลดให้เสร็จ |
| `Project JDK is not defined` | ทำข้อ 3.2 |
| `release version 17 not supported` | JDK ในเครื่องเก่ากว่า 17 → ดาวน์โหลด JDK 17 ขึ้นไป (ข้อ 3.2) |
| ภาษาไทยเป็น ??? | ทำข้อ 3.4 |
| `Location is not set` ตอนเปิดหน้า | ชื่อไฟล์ `.fxml` ในโค้ดไม่ตรงกับไฟล์จริงใน `resources/view` (ตัวพิมพ์เล็ก-ใหญ่ต้องตรงกัน) |
| แก้ใน Scene Builder แล้วกดปุ่มไม่ทำงาน | ชื่อใน **On Action** / **fx:id** ต้องตรงกับชื่อใน Controller และต้องมี `@FXML` |
| อยากลบโฟลเดอร์ `target` | ลบได้เลย เป็นไฟล์ที่ Maven สร้างตอน compile จะถูกสร้างใหม่ตอนรัน |
