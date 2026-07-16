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
 * Refactored to match Authentication.feature
 */
public class AuthSteps {
    
    // Variabel state untuk antar-step
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
    // SCENARIO 1: Pengguna Berhasil Registrasi Sebagai Customer (US-01)
    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX

    @Given("pengguna belum terdaftar di sistem")
    public void penggunaBelumTerdaftar() {
        System.out.println("User akan menggunakan data untuk registrasi akun baru");
        SetupSteps.driver.get("http://10.0.2.2:8000"); // Landing page
        
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        JavascriptExecutor executor = (JavascriptExecutor) SetupSteps.driver;
        
        // Asumsi: memilih tombol daftar di landing page
        WebElement btnDaftar = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(), 'Daftar') or contains(text(), 'Register')]")));
        executor.executeScript("arguments[0].click();", btnDaftar);
    }

    @When("pengguna mendaftar dengan username dan password yang valid")
    public void penggunaMendaftarAkunValid() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        JavascriptExecutor executor = (JavascriptExecutor) SetupSteps.driver;
        
        usernameCustomer = "iqbaltesting" + rand.nextInt(1000);
        namaLengkapCustomer = "iqbal nurhaq testing" + rand.nextInt(1000);
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("name"))).sendKeys(namaLengkapCustomer);
        SetupSteps.driver.findElement(By.id("username")).sendKeys(usernameCustomer);
        SetupSteps.driver.findElement(By.name("phone")).sendKeys("081234567890");
        SetupSteps.driver.findElement(By.id("password")).sendKeys("test123");
        
        amankanKeyboard();

        // Klik tombol buat akun / register
        WebElement btnSubmit = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[contains(@class, 'btn-submit') or contains(text(), 'Buat Akun')]")));
        executor.executeScript("arguments[0].scrollIntoView({block: 'center'});", btnSubmit);
        executor.executeScript("arguments[0].click();", btnSubmit);
    }

    @Then("sistem harus mengkonfirmasi pembuatan akun dan mengarahkan untuk login")
    public void sistemKonfirmasiAkunDanArahkanLogin() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(10));
        try {
            // Verifikasi notifikasi berhasil
            boolean isNotifikasiMuncul = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(normalize-space(), 'berhasil') or contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'success')]"))).isDisplayed();
            Assert.assertTrue("Notifikasi sukses register tidak muncul!", isNotifikasiMuncul);
            
            // Verifikasi elemen halaman login (seperti form username login)
            boolean isDiHalamanLogin = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username"))).isDisplayed();
            Assert.assertTrue("Sistem tidak mengarahkan ke halaman login!", isDiHalamanLogin);
        } catch (Exception e) {
            Assert.fail("Gagal memvalidasi konfirmasi pembuatan akun. Error: " + e.getMessage());
        }
    }

    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    // SCENARIO 2: Pengguna Berhasil Login Sebagai Customer (US-02)
    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX

    @Given("seorang customer memiliki akun yang terdaftar dan aktif")
    public void customerMemilikiAkunAktif() {
        if(usernameCustomer == null){
            usernameCustomer = "nurhaqtesting"; // Fallback data
        }
        Username = usernameCustomer;
        Password = "test123";
        
        SetupSteps.driver.get("http://10.0.2.2:8000");
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        JavascriptExecutor js = (JavascriptExecutor) SetupSteps.driver;
        
        try { js.executeScript("var splash = document.getElementById('splash-circle'); if(splash) { splash.remove(); }"); } catch (Exception e) {}
        
        // Asumsi: Klik Login utama
        WebElement btnPrimary = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("btn-primary")));
        js.executeScript("arguments[0].click();", btnPrimary);
    }

    @When("customer melakukan login dengan kredensial yang valid")
    @When("admin melakukan login dengan kredensial yang valid")
    @When("coach melakukan login dengan kredensial yang valid")
    public void loginKredensialValid() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        JavascriptExecutor js = (JavascriptExecutor) SetupSteps.driver;
        
        WebElement fieldUser = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
        fieldUser.clear();
        fieldUser.sendKeys(Username);
        
        WebElement fieldPass = SetupSteps.driver.findElement(By.id("password"));
        fieldPass.clear();
        fieldPass.sendKeys(Password);
        
        amankanKeyboard();

        WebElement btnSubmit = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("btn-submit")));
        js.executeScript("arguments[0].click();", btnSubmit);
    }

    @Then("^customer harus diarahkan ke halaman utama \\(Dashboard\\) mereka$")
    public void customerDiarahkanKeDashboard() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(10));
        boolean isDashboardMuncul = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("card-promo"))).isDisplayed();
        Assert.assertTrue("Gagal masuk ke dashboard customer!", isDashboardMuncul);
    }

    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    // SCENARIO 3: Pengguna Berhasil Login Sebagai Admin (US-02)
    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX

    @Given("seorang admin memiliki akun yang terdaftar dan aktif")
    public void adminMemilikiAkunAktif() {
        Username = "minimalist@admin.com";
        Password = "minimalist123";
        
        SetupSteps.driver.get("http://10.0.2.2:8000");
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        JavascriptExecutor js = (JavascriptExecutor) SetupSteps.driver;
        
        try { js.executeScript("var splash = document.getElementById('splash-circle'); if(splash) { splash.remove(); }"); } catch (Exception e) {}
        
        // Asumsi: Klik tombol Login Admin di landing page jika ada (atau login universal)
        WebElement btnAdmin = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(), 'Login Admin') or @class='btn-primary']")));
        js.executeScript("arguments[0].click();", btnAdmin);
    }

    @Then("^admin harus diarahkan ke halaman utama admin \\(Dashboard\\)$")
    public void adminDiarahkanKeDashboard() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(10));
        boolean isAdminMuncul = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("calendar-wrap"))).isDisplayed();
        Assert.assertTrue("Gagal masuk ke dashboard admin!", isAdminMuncul);
    }

    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    // SCENARIO 4: Pengguna Berhasil Mendaftarkan Coach Baru (US-16)
    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX

    @Given("seorang admin sudah login ke sistem")
    public void adminSudahLogin() {
        adminMemilikiAkunAktif();
        loginKredensialValid();
        adminDiarahkanKeDashboard(); // Pastikan sudah di dalam sistem
    }

    @When("admin menambahkan detail coach baru yang valid")
    public void adminTambahCoachBaru() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(10));
        JavascriptExecutor js = (JavascriptExecutor) SetupSteps.driver;

        try {
            // 1. Pindah ke Tab Coach (Menu Admin)
            WebElement tabCoach = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(., 'Coach') or contains(., 'Daftar Coach')]")));
            js.executeScript("arguments[0].click();", tabCoach);
            Thread.sleep(1000);

            // 2. Klik Tambah Coach
            WebElement btnTambah = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[contains(@class, 'btn-tambah-coach')]")));
            js.executeScript("arguments[0].click();", btnTambah);
            Thread.sleep(500);

            // 3. Isi Data (Dari InputDataCoachBaru)
            usernameCoach = "iqbaltesting" + rand.nextInt(1000);
            int rateCoach = (rand.nextInt(10000) + 1) * 1000;
            
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("name"))).sendKeys(usernameCoach);
            WebElement dropdownSpesialisasi = SetupSteps.driver.findElement(By.name("class_id"));
            new Select(dropdownSpesialisasi).selectByVisibleText("Yin Yoga");
            
            SetupSteps.driver.findElement(By.name("phone")).sendKeys("089988887777");
            SetupSteps.driver.findElement(By.name("bio")).sendKeys("Instruktur yoga bersertifikat dengan pengalaman internasional.");
            SetupSteps.driver.findElement(By.name("rate_per_class")).sendKeys(Integer.toString(rateCoach));
            SetupSteps.driver.findElement(By.name("years_experience")).sendKeys("5");
            SetupSteps.driver.findElement(By.name("password")).sendKeys("test123");
            
            amankanKeyboard();

            // 4. Submit Modal/Form
            WebElement btnSubmitModal = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[contains(@class, 'btn-modal-submit') or contains(text(), 'Simpan')]")));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", btnSubmitModal);
            js.executeScript("arguments[0].click();", btnSubmitModal);
            
            System.out.println("Form Coach berhasil disubmit.");
            Thread.sleep(2000); // Tunggu proses backend
        } catch (Exception e) {
            Assert.fail("Gagal menambahkan coach baru. Error: " + e.getMessage());
        }
    }

    @Then("sistem harus mengkonfirmasi penambahan coach dan menampilkan notifikasi keberhasilan")
    public void sistemKonfirmasiPenambahanCoach() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(10));
        try {
            boolean isNotifMuncul = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'berhasil')]"))).isDisplayed();
            Assert.assertTrue("Notifikasi penambahan coach gagal muncul!", isNotifMuncul);
        } catch (Exception e) {
            Assert.fail("Sistem tidak mengkonfirmasi penambahan coach: " + e.getMessage());
        }
    }

    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    // SCENARIO 5: Pengguna Berhasil Login Sebagai Coach (US-02)
    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX

    @Given("seorang coach memiliki akun yang terdaftar dan aktif")
    public void coachMemilikiAkunAktif() {
        if (usernameCoach == null){
            usernameCoach = "iqbaltest";
        }
        Username = usernameCoach + "@coach.com";
        Password = "test123";
        
        SetupSteps.driver.get("http://10.0.2.2:8000");
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        JavascriptExecutor js = (JavascriptExecutor) SetupSteps.driver;
        
        try { js.executeScript("var splash = document.getElementById('splash-circle'); if(splash) { splash.remove(); }"); } catch (Exception e) {}
        
        // Asumsi: Klik Login universal/coach
        WebElement btnLoginUtama = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(), 'Login') or @class='btn-primary']")));
        js.executeScript("arguments[0].click();", btnLoginUtama);
    }

    @Then("^coach harus diarahkan ke halaman utama coach \\(Dashboard\\)$")
    public void coachDiarahkanKeDashboard() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(10));
        boolean isCoachMuncul = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("coach-content"))).isDisplayed();
        Assert.assertTrue("Gagal masuk ke dashboard coach!", isCoachMuncul);
    }
}