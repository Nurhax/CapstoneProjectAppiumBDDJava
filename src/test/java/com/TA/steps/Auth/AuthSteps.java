/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.TA.steps.Auth;

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
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.Random;

/**
 *
 * @author M.Iqbal Nurhaq
 */
public class AuthSteps {
    
    private AndroidDriver driver;
    public String Username;
    public String Password;
    
    
    public static String usernameCoach;
    public static String usernameCustomer;
    public Random rand = new Random();

    //Setup sebelum testing, bakalan ada di setiap file steps
    @Before
    public void setup() throws MalformedURLException {
        // Setup Appium Options (Pengganti DesiredCapabilities di Appium 2)
        UiAutomator2Options options = new UiAutomator2Options();
        
        // Konfigurasi Device
        options.setPlatformName("Android");
        options.setDeviceName("Android Emulator"); // Ganti jika pakai device asli
        options.setAutomationName("UiAutomator2");
        
        // Gunain browser chrome dengan driver windows dengan versi sesuai API level device dalam hal ini versi ~121
        options.withBrowserName("Chrome");
        
        // Set path dari chomedriver di disk C
        options.setCapability("appium:chromedriverExecutable", "C:\\Drivers\\chromedriver.exe");

        // Inisialisasi Driver dengan port default: 4723
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
        
        // Tunggu sampai setup selesai kalau kelamaan bubar
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }
    
    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    // BAGIAN DATA PREPARASI
    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    
    @Given("pengguna sudah memiliki akun yang terdaftar dan aktif")
    public void penggunaSudahMemilikiAkunCustomer(){
        //Akun mock yang udah lama dibuat
        if(usernameCustomer == null){
            usernameCustomer = "iqbaltesting";
            System.out.println("Test login berjalan lebih dahulu sebelum daftar/register");
        }
        
        //Pake akun yang baru didaftarin
        Username = usernameCustomer;
        Password = "testing123!";
        System.out.println("Data preparasi customer auth");
    }
    
    @Given("pengguna belum memiliki akun")
    public void penggunaBelumMemilikiAkun(){
        //Kalo user gak punya akun sama sekali
        System.out.println("User akan menggunakan data untuk registrasi akun baru");
    }
    
    @Given("pengguna sudah memiliki akun yang terdaftar dan aktif dari developer")
    public void adminSudahMempunyaiAkun(){
        Username = "minimalist@admin.com";
        Password = "minimalist123";
        System.out.println("Data preparasi admin auth");
    }
    
    @Given("pengguna sudah memiliki akun yang terdaftar dan aktif dari admin")
    public void penggunaSudahMemilikiAkunCoach(){
        //Akun mock yang udah lama dibuat coach
        if (usernameCoach == null){
            usernameCoach = "iqbaltest";
        }
        
        Username = usernameCoach + "@coach.com";
        Password = "test123";
        System.out.println("Data preparasi coach auth sama dengan data registrasi");
    }
    
    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    // BAGIAN NAVIGASI DAN INTERAKSI
    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    
    @And("pengguna berada di Landing Page")
    public void penggunaBeradaDiLandingPage(){
        //Masih local host nanti diganti
        driver.get("http://10.0.2.2:8000");
    }
    
    @When("pengguna memilih opsi {string}")
    public void penggunaMemilihOpsi(String opsi){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        //Nyari teks menggunakan feature file
        String xpathSelector = String.format("//*[contains(text(), '%s')]", opsi);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathSelector))).click();
    }
    
    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    // INPUT FORM PADA PWA
    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX

    @And("pengguna memasukkan username dan password yang valid pada halaman login")
    public void inputDataLoginValid(){
        driver.findElement(By.id("username")).sendKeys(Username);
        driver.findElement(By.id("password")).sendKeys(Password);
    }
    
    @And("pengguna memasukkan username, nomer telpon, dan password yang valid pada halaman register")
    public void inputDataRegisterCustomerValid(){
        usernameCustomer = "iqbaltesting" + rand.nextInt(1000);
        driver.findElement(By.id("username")).sendKeys(usernameCustomer);
        driver.findElement(By.name("phone")).sendKeys("081234567890");
        driver.findElement(By.id("password")).sendKeys("testing123!");
    }
    
    @And("pengguna mengisi nama coach, keahlian, nomor hp, deskripsi, rate per kelas, pengalaman tahun dan password")
    public void inputDataCoachBaru(){
        usernameCoach = "iqbaltesting" + rand.nextInt(1000);
        //Rate coach random dan +1 biar gak 0 ratenya
        int rateCoach = (rand.nextInt(10000) + 1) * 1000;
        
        driver.findElement(By.name("name")).sendKeys(usernameCoach);
        driver.findElement(By.name("specialization")).sendKeys("Yoga Biasa");
        driver.findElement(By.name("phone")).sendKeys("089988887777");
        driver.findElement(By.name("bio")).sendKeys("Instruktur yoga bersertifikat dengan pengalaman internasional. passnya:test123");
        driver.findElement(By.name("rate_per_class")).sendKeys(Integer.toString(rateCoach));
        driver.findElement(By.name("years_experience")).sendKeys("5");
        driver.findElement(By.name("password")).sendKeys("test123");
        
        System.out.println("Berhasil tambah coach!");
    }
    
    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    // TOMBOL DAN TAB PWA
    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    
    @And("pengguna menekan tombol {string}")
    public void penggunaMenekanTombol(String namaTombol) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        String xpathSelector = "";

        // Jika tombolnya selain login dan regist pake class aja, pake enum kalo button lain gini juga
        if (namaTombol.equalsIgnoreCase("Tambah Coach")) {
            xpathSelector = "//button[contains(@class, 'btn-tambah-coach')]";
        } else {
            // Tombol lain tetep dicari berdasarkan text
            xpathSelector = String.format("//button[contains(., '%s')]", namaTombol);
        }

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathSelector))).click();
    }
    
    @And("pengguna menekan tombol {string} lagi")
    public void penggunaMenekanTombolLagi(String tombolLama){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        // Menggunakan tag 'a' atau semacamnya sesuai struktur navigasi tab PWA kamu
        String xpathSelector = String.format("//button[contains(@class, 'btn-modal-submit')]", tombolLama);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathSelector))).click();
    }
    
    @And("pengguna menekan tab {string}")
    public void penggunaMenekanTab(String namaTab) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        // Menggunakan tag 'a' atau semacamnya sesuai struktur navigasi tab PWA kamu
        String xpathSelector = String.format("//a[contains(., '%s')]", namaTab);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathSelector))).click();
    }
    
    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    // BAGIAN VERIFIKASI PER SKENARIO
    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    
    // Skenario 1: Dashboard Customer
    @And("^pengguna diarahkan ke halaman utama \\(Dashboard\\)$")
    public void penggunaDiarahkanKeDashboardCustomer() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        // Ganti 'card-promo' dengan elemen unik di dashboard customer
        boolean isDashboardMuncul = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("card-promo"))).isDisplayed();
        Assert.assertTrue("Gagal masuk ke dashboard customer!", isDashboardMuncul);
    }

    // Skenario 3 & 4: Dashboard Admin
    @Then("^pengguna diarahkan ke halaman utama admin \\(Dashboard\\)$")
    public void penggunaDiarahkanKeDashboardAdmin() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        // Ganti dengan elemen unik di dashboard admin
        boolean isAdminMuncul = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("btn-tambah-member"))).isDisplayed();
        Assert.assertTrue("Gagal masuk ke dashboard admin!", isAdminMuncul);
    }

    // Skenario 5: Dashboard Coach
    @Then("^pengguna diarahkan ke halaman utama coach \\(Dashboard\\)$")
    public void penggunaDiarahkanKeDashboardCoach() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        // Ganti dengan elemen unik di dashboard coach
        boolean isCoachMuncul = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("coach-content"))).isDisplayed();
        Assert.assertTrue("Gagal masuk ke dashboard coach!", isCoachMuncul);
    }

    // Verifikasi Notifikasi (Registrasi Customer & Tambah Coach)
    @Then("sistem menampilkan notifikasi {string}")
    public void sistemMenampilkanNotifikasi(String expectedMessage) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        // Cari elemen toast/notifikasi (Pastikan class 'alert-success' atau class sejenisnya benar)
        WebElement notification = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("alert-success"))); 
        
        String actualMessage = notification.getText();
        Assert.assertTrue("Notifikasi tidak sesuai! Munculnya: " + actualMessage, 
                          actualMessage.contains(expectedMessage));
    }
    
    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    // BUBARIN SESSION
    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
