# Implementation of Automated Testing Based on Behavior Driven Development (BDD) Using Appium on a PWA Booking Yoga Application

[![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)](https://www.java.com/)
[![Appium](https://img.shields.io/badge/Appium-662d91?style=for-the-badge&logo=appium&logoColor=white)](http://appium.io/)
[![Cucumber](https://img.shields.io/badge/Cucumber-23D96C?style=for-the-badge&logo=cucumber&logoColor=white)](https://cucumber.io/)
[![Selenium](https://img.shields.io/badge/Selenium-43B02A?style=for-the-badge&logo=selenium&logoColor=white)](https://www.selenium.dev/)

Repository ini berisi proyek **Capstone/Tugas Akhir** yang berfokus pada otomatisasi pengujian aplikasi **Progressive Web App (PWA)** Booking Yoga. Framework ini dibangun menggunakan pendekatan **Behavior-Driven Development (BDD)** untuk memastikan skenario pengujian dapat dipahami oleh pemangku kepentingan teknis maupun non-teknis.

## 🚀 Fitur Utama
- **Pendekatan BDD**: Menggunakan Cucumber dan Gherkin untuk mendefinisikan skenario pengujian dalam bahasa alami.
- **Cross-Platform Mobile Testing**: Automasi pada perangkat Android menggunakan Appium 2.0.
- **PWA Support**: Pengujian khusus untuk Progressive Web Apps yang berjalan di atas engine Google Chrome (WebView).
- **Page Object Model (POM)**: Arsitektur kode yang bersih dan mudah dirawat (maintainable).

## 🛠️ Stack Teknologi
- **Bahasa Pemrograman**: Java 25 (LTS)
- **Otomatisasi Mobile**: Appium Server v2.x & UiAutomator2 Driver
- **BDD Framework**: Cucumber
- **Build Tool**: Maven
- **Driver Browser**: Selenium WebDriver & Chromedriver (v109+)

## 📋 Prasyarat Sistem
Sebelum menjalankan pengujian, pastikan lingkungan Anda telah terkonfigurasi:
1. **Node.js** (Versi terbaru)
2. **Appium Server 2.0**: `npm install -g appium`
3. **Appium Driver**: `appium driver install uiautomator2`
4. **Android SDK**: Terinstal dan terkonfigurasi di Environment Variables (`ANDROID_HOME`)
5. **Java JDK**: Terinstal dan terkonfigurasi di Environment Variables (`JAVA_HOME`)
6. **Android Emulator**: API Level 31-34 (Disarankan API 33 untuk stabilitas)

## 🔧 Konfigurasi Lingkungan
Pastikan variabel lingkungan Windows Anda sudah benar:
```bash
ANDROID_HOME = C:\Users\<User>\AppData\Local\Android\Sdk
JAVA_HOME    = C:\Program Files\Java\jdk-<version>
Path         = %ANDROID_HOME%\platform-tools, %ANDROID_HOME%\tools
```

## 🏃 Cara Menjalankan Pengujian

1. **Nyalakan Server Appium**
   Buka terminal/CMD dan jalankan:
   ```bash
   appium --allow-insecure chromedriver_autodownload
   ```

2. **Jalankan Aplikasi Target**
   Pastikan aplikasi PWA Anda sudah berjalan di `localhost` (misalnya port 8000).

3. **Eksekusi Pengujian via Maven**
   Gunakan perintah berikut di folder proyek:
   ```bash
   mvn test
   ```

## 📂 Struktur Proyek
```text
src/test/
├── java/com/TA/
│   ├── runners/      # Konfigurasi Cucumber Test Runner
│   └── steps/        # Implementasi Step Definitions (Logika Java)
└── resources/
    └── features/     # File .feature (Skenario Gherkin)
```

## 🔍 Detail Pengujian
Skenario pengujian mencakup:
- **Authentication**: Login dan Registrasi pengguna baru.
- **Admin Management**: Penambahan jadwal kelas baru oleh administrator.
- **Validation**: Verifikasi notifikasi alert dan sukses pada aplikasi.

---

## 👨‍💻 Penulis
* **Nama**: Muhammad Iqbal Nurhaq
* **NIM**: 130223050
* **Prodi**: S1 Informatika - Telkom University
* **LinkedIn**: https://www.linkedin.com/in/muhammad-iqbal-nurhaq-99b2a1297/

---

**Catatan:** Proyek ini dikembangkan untuk memenuhi syarat kelulusan mata kuliah Capstone Project/Tugas Akhir. Segala bentuk kontribusi atau saran sangat diapresiasi.
