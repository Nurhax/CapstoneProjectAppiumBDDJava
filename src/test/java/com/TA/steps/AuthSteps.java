package com.TA.steps;

import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.Select;
import java.time.Duration;
import java.util.Random;

/**
 * @author M.Iqbal Nurhaq
 */
public class AuthSteps {
    // Variabel untuk testing random and out of bounds data
    public String Username;
    public String Password;
    
    public static String usernameCoach;
    public static String usernameCustomer;
    public static String namaLengkapCustomer;
    public Random rand = new Random();

    // Helper untuk menyembunyikan keyboard Android secara aman
    private void amankanKeyboard() {
        try {
            AndroidDriver driver = (AndroidDriver) SetupSteps.driver;
            if (driver.isKeyboardShown()) {
                driver.hideKeyboard();
                System.out.println("Keyboard native Android berhasil disembunyikan secara paksa.");
                Thread.sleep(800); // Jeda transisi pasca keyboard turun
            }
        } catch (Exception e) {
            System.out.println("Keyboard sudah tertutup atau tidak mendukung hideKeyboard otomatis.");
        }
    }

    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    // BAGIAN DATA PREPARASI
    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    
    @Given("pengguna sudah memiliki akun yang terdaftar dan aktif")
    public void penggunaSudahMemilikiAkunCustomer(){
        if(usernameCustomer == null){
            usernameCustomer = "nurhaqtesting";
            System.out.println("Test login berjalan lebih dahulu sebelum daftar/register");
        }
        Username = usernameCustomer;
        Password = "test123";
        System.out.println("Data preparasi customer auth");
    }
    
    @Given("pengguna belum memiliki akun")
    public void penggunaBelumMemilikiAkun(){
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
    
    @And("pengguna berada di landing page")
    public void penggunaBeradaDiLandingPage(){
        SetupSteps.driver.get("http://10.0.2.2:8000");
    }
    
    @When("pengguna memilih opsi {string}")
    public void penggunaMemilihOpsi(String opsi){
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        String xpathSelector = String.format("//*[contains(text(), '%s')]", opsi);
        
        WebElement tombolOpsi = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathSelector)));
        JavascriptExecutor executor = (JavascriptExecutor) SetupSteps.driver;
        executor.executeScript("arguments[0].click();", tombolOpsi);
        
        System.out.println("Berhasil bypass animasi dan klik opsi: " + opsi);
    }
    
    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    // INPUT FORM PADA PWA
    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX

    @And("pengguna memasukkan username dan password yang valid pada halaman login")
    public void inputDataLoginValid(){
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        
        WebElement fieldUser = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
        fieldUser.clear();
        fieldUser.sendKeys(Username);
        
        WebElement fieldPass = SetupSteps.driver.findElement(By.id("password"));
        fieldPass.clear();
        fieldPass.sendKeys(Password);
        
        // Panggil pengaman keyboard setelah isi password
        amankanKeyboard();
    }
    
    @And("pengguna memasukkan nama lengkap, username, nomer telpon, dan password yang valid pada halaman register")
    public void inputDataRegisterCustomerValid(){
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        
        usernameCustomer = "iqbaltesting" + rand.nextInt(1000);
        namaLengkapCustomer = "iqbal nurhaq testing" + rand.nextInt(1000);
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("name"))).sendKeys(namaLengkapCustomer);
        SetupSteps.driver.findElement(By.id("username")).sendKeys(usernameCustomer);
        SetupSteps.driver.findElement(By.name("phone")).sendKeys("081234567890");
        SetupSteps.driver.findElement(By.id("password")).sendKeys("test123");
        
        // Panggil pengaman keyboard setelah isi semua form registrasi
        amankanKeyboard();
    }
    
    @And("pengguna mengisi nama coach, keahlian, nomor hp, deskripsi, rate per kelas, pengalaman tahun dan password")
    public void inputDataCoachBaru(){
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        
        usernameCoach = "iqbaltesting" + rand.nextInt(1000);
        int rateCoach = (rand.nextInt(10000) + 1) * 1000;
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("name"))).sendKeys(usernameCoach);
        WebElement dropdownSpesialisasi = SetupSteps.driver.findElement(By.name("class_id"));
        Select selectSpesialisasi = new Select(dropdownSpesialisasi);
        selectSpesialisasi.selectByVisibleText("Yin Yoga");
        
        SetupSteps.driver.findElement(By.name("phone")).sendKeys("089988887777");
        SetupSteps.driver.findElement(By.name("bio")).sendKeys("Instruktur yoga bersertifikat dengan pengalaman internasional. passnya:test123");
        SetupSteps.driver.findElement(By.name("rate_per_class")).sendKeys(Integer.toString(rateCoach));
        SetupSteps.driver.findElement(By.name("years_experience")).sendKeys("5");
        SetupSteps.driver.findElement(By.name("password")).sendKeys("test123");
        
        System.out.println("Berhasil mengisi data coach baru.");
        // Panggil pengaman keyboard sebelum submit tambah coach
        amankanKeyboard();
    }
    
    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    // TOMBOL DAN TAB PWA
    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    
    @And("pengguna menekan tombol {string}")
    public void penggunaMenekanTombol(String namaTombol) {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(10));
        JavascriptExecutor js = (JavascriptExecutor) SetupSteps.driver;
        String xpathSelector = "";

        if (namaTombol.equalsIgnoreCase("Tambah Coach")) {
            xpathSelector = "//button[contains(@class, 'btn-tambah-coach')]";
        } else if(namaTombol.equalsIgnoreCase("Buat Akun")){
            xpathSelector = "//button[contains(@class, 'btn-submit')]";
        } else {
            xpathSelector = String.format("//button[contains(., '%s')]", namaTombol);
        }

        try {
            // Kita gunakan presenceOfElementLocated agar tidak terkecoh oleh pergeseran keyboard
            WebElement tombol = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathSelector)));
            
            // Scroll dulu biar posisinya pas di layar pasca keyboard ditutup
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", tombol);
            Thread.sleep(500);
            
            // Eksekusi paksa menggunakan JS Click biar bypass interupsi layout keyboard turun
            js.executeScript("arguments[0].click();", tombol);
            System.out.println("Berhasil mengeklik tombol '" + namaTombol + "' via JS Executor.");
        } catch (Exception e) {
            Assert.fail("Gagal menekan tombol '" + namaTombol + "'. Error: " + e.getMessage());
        }
    }
    
    @And("pengguna menekan tombol {string} lagi")
    public void penggunaMenekanTombolLagi(String tombolLama){
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        JavascriptExecutor js = (JavascriptExecutor) SetupSteps.driver;
        
        String xpathSelector = "//button[contains(@class, 'btn-modal-submit')]";
        try {
            WebElement tombol = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathSelector)));
            js.executeScript("arguments[0].click();", tombol);
        } catch (Exception e) {
            Assert.fail("Gagal menekan tombol modal submit lagi. Error: " + e.getMessage());
        }
    }
    
    @And("pengguna menekan tab {string}")
    public void penggunaMenekanTab(String namaTab) {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        JavascriptExecutor js = (JavascriptExecutor) SetupSteps.driver;
        
        String xpathSelector = String.format("//a[contains(., '%s')]", namaTab);
        try {
            WebElement tab = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathSelector)));
            js.executeScript("arguments[0].click();", tab);
        } catch (Exception e) {
            Assert.fail("Gagal menekan tab '" + namaTab + "'. Error: " + e.getMessage());
        }
    }
    
    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    // BAGIAN VERIFIKASI PER SKENARIO
    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    
    @And("^pengguna diarahkan ke halaman utama \\(Dashboard\\)$")
    public void penggunaDiarahkanKeDashboardCustomer() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(10));
        boolean isDashboardMuncul = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("card-promo"))).isDisplayed();
        Assert.assertTrue("Gagal masuk ke dashboard customer!", isDashboardMuncul);
    }

    @Then("^pengguna diarahkan ke halaman utama admin \\(Dashboard\\)$")
    public void penggunaDiarahkanKeDashboardAdmin() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(10));
        boolean isAdminMuncul = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("btn-tambah-member"))).isDisplayed();
        Assert.assertTrue("Gagal masuk ke dashboard admin!", isAdminMuncul);
    }

    @Then("^pengguna diarahkan ke halaman utama coach \\(Dashboard\\)$")
    public void penggunaDiarahkanKeDashboardCoach() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(10));
        boolean isCoachMuncul = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("coach-content"))).isDisplayed();
        Assert.assertTrue("Gagal masuk ke dashboard coach!", isCoachMuncul);
    }

    @Then("sistem menampilkan notifikasi {string}")
    public void sistemMenampilkanNotifikasi(String expectedMessage) {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(10));
        String xpathNotifikasi = String.format("//*[contains(normalize-space(), '%s')]", expectedMessage);
        
        try {
            boolean isNotifikasiMuncul = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathNotifikasi))).isDisplayed();
            Assert.assertTrue("Notifikasi tidak muncul!", isNotifikasiMuncul);
            System.out.println("Berhasil menemukan notifikasi: " + expectedMessage);
        } catch (Exception e) {
            Assert.fail("Gagal menemukan teks notifikasi: '" + expectedMessage + "' dalam waktu yang ditentukan.");
        }
    }
}