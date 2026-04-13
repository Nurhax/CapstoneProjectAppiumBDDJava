/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.TA.steps;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

/**
 *
 * @author M.Iqbal Nurhaq
 */
public class AuthSteps {
    
    private AndroidDriver driver;

    @Before
    public void setup() throws MalformedURLException {
        // Setup Appium Options (Pengganti DesiredCapabilities di Appium 2)
        UiAutomator2Options options = new UiAutomator2Options();
        
        // Konfigurasi Device
        options.setPlatformName("Android");
        options.setDeviceName("Android Emulator"); // Ganti jika pakai device asli
        options.setAutomationName("UiAutomator2");
        
        // PENTING UNTUK PWA: Gunakan Browser Chrome
        options.withBrowserName("Chrome");
        
        // Ganti path yang error tadi dengan path baru yang lebih simple
        options.setCapability("appium:chromedriverExecutable", "C:\\Drivers\\chromedriver.exe");

        // Inisialisasi Driver (Pastikan URL sesuai port appium server, default 4723)
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/"), options);
        
        // Implicit wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

// --- SCENARIO 1 & 2: SETUP AWAL ---

    @Given("pengguna sudah memiliki akun yang terdaftar dan aktif")
    public void penggunaSudahMemilikiAkun() {
        // Langkah ini biasanya bersifat asumsi data (Data Preparation).
        // Bisa dibiarkan kosong, atau diisi log agar terbaca di report.
        System.out.println("Data Preparation: Akun test sudah ada di database.");
    }

    @Given("pengguna belum memiliki akun")
    public void penggunaBelumMemilikiAkun() {
        System.out.println("Data Preparation: Menggunakan data akun baru.");
    }

    @And("pengguna berada di Halaman Pembuka")
    public void penggunaBeradaDiHalamanPembuka() {
        // Karena ini PWA, arahkan driver browser ke URL aplikasi Yoga kamu
        driver.get("https://url-pwa-yoga-kamu.com"); 
    }

    // --- SCENARIO 1 & 2: INTERAKSI PILIHAN ---
    
    // Menggunakan parameter {string} agar 1 fungsi bisa dipakai untuk 2 skenario ("Sudah punya akun" & "Belum punya akun")
    @When("pengguna memilih opsi {string}")
    public void penggunaMemilihOpsi(String opsi) {
        if (opsi.equals("Sudah punya akun")) {
            driver.findElement(By.id("btn-pilih-login")).click(); // Ganti ID sesuai elemen aslinya
        } else if (opsi.equals("Belum punya akun")) {
            driver.findElement(By.id("btn-pilih-register")).click(); // Ganti ID sesuai elemen aslinya
        }
    }

    // --- SCENARIO 1: FORM LOGIN ---

    @And("pengguna memasukkan Username dan Password yang valid pada Halaman Login")
    public void inputDataLoginValid() {
        // Ganti locator (By.id) sesuai dengan Inspect Element di PWA kamu
        driver.findElement(By.id("input-username-login")).sendKeys("user_yoga_test");
        driver.findElement(By.id("input-password-login")).sendKeys("password123");
    }

    // --- SCENARIO 2: FORM REGISTER ---

    @And("pengguna memasukkan Username, Nomer telpon, dan Password yang valid pada Halaman Register")
    public void inputDataRegisterValid() {
        driver.findElement(By.id("input-username-reg")).sendKeys("member_baru");
        driver.findElement(By.id("input-phone-reg")).sendKeys("081234567890");
        driver.findElement(By.id("input-password-reg")).sendKeys("pass_baru123");
    }

    // --- SCENARIO 1 & 2: SUBMIT BUTTON ---

    @And("pengguna menekan tombol {string}")
    public void penggunaMenekanTombol(String namaTombol) {
        // Trik efisien: Menggunakan XPath untuk mencari tombol berdasarkan Teks-nya secara dinamis
        String xpathTombol = String.format("//button[text()='%s']", namaTombol);
        driver.findElement(By.xpath(xpathTombol)).click();
    }

    // --- SCENARIO 1 & 2: VERIFIKASI NOTIFIKASI ---

    @Then("sistem menampilkan notifikasi {string}")
    public void sistemMenampilkanNotifikasi(String pesanNotifHarapan) {
        // Cari elemen notifikasi (misal: alert, toast message, atau modal popup)
        String teksMuncul = driver.findElement(By.className("toast-message")).getText(); // Ganti class-nya
        
        // Assert akan menggagalkan test jika notifikasi tidak sesuai
        Assert.assertTrue("Pesan notifikasi salah atau tidak muncul!", teksMuncul.contains(pesanNotifHarapan));
    }

    // --- SCENARIO 1: VERIFIKASI DASHBOARD ---

    @And("pengguna diarahkan ke halaman utama \\(Dashboard)")
    public void penggunaDiarahkanKeDashboard() {
        // Cara terbaik memastikan pindah halaman adalah mengecek elemen khas di halaman Dashboard
        // Misalnya judul halaman atau menu navigasi
        boolean isDashboardMuncul = driver.findElement(By.id("dashboard-header-title")).isDisplayed();
        Assert.assertTrue("Gagal masuk ke dashboard!", isDashboardMuncul);
    }
    
    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
