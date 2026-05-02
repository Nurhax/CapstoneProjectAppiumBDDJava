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
import java.util.Random;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
/**
 *
 * @author KnightlyTech
 */
public class CustomerSteps{
    public static String expectedFilterValue;
    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    // BAGIAN DATA PREPARASI
    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    
    @Given("customer sudah login dengan akun berbeda dan berada di halaman home")
    public void customerSudahLoginDenganAkunBerbeda(){
        // Siapkan 'satpam' untuk menunggu maksimal 10 detik
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));

        // 1. Buka URL utama
        SetupSteps.driver.get("http://10.0.2.2:8000/");

        // 2. Tunggu tombol opsi Login bisa diklik, lalu klik
        wait.until(ExpectedConditions.elementToBeClickable(By.className("btn-primary"))).click();

        // 3. JEDA KRITIS: Tunggu sampai kolom username benar-benar muncul di layar
        WebElement usernameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
        usernameInput.sendKeys("nurhaqtest");

        // 4. Masukkan password (karena username sudah muncul, password pasti sudah ada)
        SetupSteps.driver.findElement(By.id("password")).sendKeys("testing123!");

        // 5. Tunggu tombol submit bisa diklik, lalu klik
        wait.until(ExpectedConditions.elementToBeClickable(By.className("btn-submit"))).click();

        // 6. JEDA KRITIS 2: Tunggu sampai masuk ke halaman Home beneran
        // Cari elemen unik yang ADA di halaman home, misalnya tab navigation
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("promo-scroll")));

        System.out.println("Customer berhasil login dan sudah berada di home page");
    }
    
    @Given("^customer sudah login dan berada di halaman [Hh]ome$")
    public void customerSudahLogin(){
        // Siapkan 'satpam' untuk menunggu maksimal 10 detik
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));

        // 1. Buka URL utama
        SetupSteps.driver.get("http://10.0.2.2:8000/");

        // 2. Tunggu tombol opsi Login bisa diklik, lalu klik
        wait.until(ExpectedConditions.elementToBeClickable(By.className("btn-primary"))).click();

        // 3. JEDA KRITIS: Tunggu sampai kolom username benar-benar muncul di layar
        WebElement usernameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
        usernameInput.sendKeys("nurhaqtest2");

        // 4. Masukkan password (karena username sudah muncul, password pasti sudah ada)
        SetupSteps.driver.findElement(By.id("password")).sendKeys("test123");

        // 5. Tunggu tombol submit bisa diklik, lalu klik
        wait.until(ExpectedConditions.elementToBeClickable(By.className("btn-submit"))).click();

        // 6. JEDA KRITIS 2: Tunggu sampai masuk ke halaman Home beneran
        // Cari elemen unik yang ADA di halaman home, misalnya tab navigation
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("promo-scroll")));

        System.out.println("Customer berhasil login dan sudah berada di home page");
    }
    
    @And("customer menekan tab {string}")
    public void customerMenekanTab(String namaTab){
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        // Menggunakan tag 'a' buat mencari elementnya
        String xpathSelector = String.format("//a[contains(., '%s')]", namaTab);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathSelector))).click();
    }

    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    // MODUL EKSPLORASI KELAS (FR4, FR5, FR18)
    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    
    @Then("sistem menampilkan daftar jadwal kelas yoga yang tersedia")
    public void sistemMenampilkanDaftarJadwal(){
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        boolean isListMuncul = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("cards-grid"))).isDisplayed();
        Assert.assertTrue("Daftar jadwal kelas tidak muncul!", isListMuncul);
    }
    
    @When("customer memilih filter {string} pada tombol {string}")
    public void customerMemilihFilter(String nilaiFilter, String namaTombol) {
        // Simpan nilai input filter ke variabel global (seperti yang kita bahas sebelumnya)
        expectedFilterValue = nilaiFilter; 
        
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        
        // 1. Tentukan ID tombol berdasarkan namaTombol
        String idTombol = "";
        if (namaTombol.equalsIgnoreCase("Kelas")) {
            idTombol = "btn-kelas";
        } else if (namaTombol.equalsIgnoreCase("Waktu")) {
            idTombol = "btn-waktu"; // Asumsi ID-nya ini, sesuaikan dengan HTML aslinya
        } else if (namaTombol.equalsIgnoreCase("Coach")) {
            idTombol = "btn-coach"; // Asumsi ID-nya ini, sesuaikan dengan HTML aslinya
        }
        
        // Klik tombol dropdown-nya
        wait.until(ExpectedConditions.elementToBeClickable(By.id(idTombol))).click();

        // 2. Klik opsi checkbox berdasarkan teks/valuenya (nilaiFilter)
        // Karena struktur checkbox biasanya dibungkus label <label><input type="checkbox"> Teks</label>
        // Kita cari elemen yang mengandung teks nilaiFilter dan mengkliknya.
        String opsiXpath = String.format("//label[contains(normalize-space(), '%s')]", nilaiFilter);
        
        // Jika opsiXpath menggunakan <label> gagal, coba gunakan XPath alternatif ini:
        // String opsiXpath = String.format("//*[contains(text(), '%s')]/preceding-sibling::input[@type='checkbox']", nilaiFilter);
        
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(opsiXpath))).click();
        
        // Opsional: Klik di luar menu untuk menutup dropdown (misal klik judul 'JADWAL KELAS')
        // Ini berguna jika dropdown menutupi elemen lain yang mau di-klik selanjutnya.
        // SetupSteps.driver.findElement(By.xpath("//*[contains(text(), 'JADWAL KELAS')]")).click();
    }
    
    @Then("sistem hanya menampilkan daftar kelas yang sesuai dengan filter yang diterapkan")
    public void sistemMenampilkanFilterMembership() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        
        // Mencari elemen apa pun di layar yang mengandung teks dari expectedFilterValue
        String xpath = String.format("//*[contains(normalize-space(), '%s')]", expectedFilterValue);
        
        // Tunggu sampai teks tersebut visibel/muncul di layar
        boolean isFilteredMembershipMuncul = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath))).isDisplayed();
        
        // Assertion dengan pesan error yang lebih informatif
        Assert.assertTrue(
            "Hasil filter membership tidak muncul! Tidak menemukan teks: " + expectedFilterValue, 
            isFilteredMembershipMuncul
        );
    }
    
    @When("customer melihat salah satu kelas yang tersedia")
    public void customerMelihatSalahSatuKelas() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        // Memastikan ada card kelas yang terlihat di layar
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("card-class")));
    }

    @And("customer menekan foto profil coach pada kelas yang dilihat")
    public void customerMenekanFotoProfilCoach() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(By.className("coach-avatar"))).click();
    }

    @Then("sistem menampilkan halaman informasi detail profil coach tersebut")
    public void sistemMenampilkanDetailCoach() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        boolean isDetailCoachMuncul = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("coach-hero-name"))).isDisplayed();
        Assert.assertTrue("Gagal membuka detail coach!", isDetailCoachMuncul);
    }
    
    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    // MODUL RIWAYAT BOOKING (FR14)
    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    
    @Then("sistem mengarahkan customer ke halaman riwayat booking customer")
    public void sistemMengarahkanKeHalamanAktivitas() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        
        String xpath = String.format("//*[text()='%s']", "Riwayat Aktivitas");
        
        boolean isTextMuncul = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath))).isDisplayed();
        Assert.assertTrue("Teks '" + "Riwayat Aktivitas" + "' tidak ditemukan di layar!", isTextMuncul);
    }
    

    @And("customer melihat teks {string} berupa jadwal yang akan dilakukan hari ini")
    public void customerMelihatTeksJadwalHariIni(String teksDiharapkan) {
        String xpath = String.format("//*[contains(text(), '%s')]", teksDiharapkan);
        boolean isTextMuncul = SetupSteps.driver.findElement(By.xpath(xpath)).isDisplayed();
        Assert.assertTrue("Teks jadwal hari ini tidak ditemukan!", isTextMuncul);
    }

    @And("customer melihat teks {string} berupa riwayat aktivitas yang telah lewat")
    public void customerMelihatTeksRiwayatLewat(String teksDiharapkan) {
        String xpath = String.format("//*[contains(text(), '%s')]", teksDiharapkan);
        boolean isTextMuncul = SetupSteps.driver.findElement(By.xpath(xpath)).isDisplayed();
        Assert.assertTrue("Teks riwayat lewat tidak ditemukan!", isTextMuncul);
    }

    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    // MODUL MANAJEMEN PROFIL (FR19)
    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    
    @When("customer menekan tombol Edit Username")
    public void customerMenekanTombolEditUsername() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        String xpath = "//div[contains(@class, 'info-row-left') and contains(., 'Username')]/following-sibling::button[contains(@class, 'edit-btn')]";
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath))).click();
    }

    @And("customer mengubah nama menjadi {string}")
    public void customerMengubahNama(String namaBaru) {
        WebElement inputNama = SetupSteps.driver.findElement(By.name("name"));
        inputNama.clear();
        inputNama.sendKeys(namaBaru);
    }

    @And("customer menekan tombol Save Username")
    public void customerMenekanTombolSaveUsername() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        String xpath = "//div[contains(@class, 'info-row-left') and contains(., 'Username')]/following-sibling::button[contains(@class, 'save-btn')]";
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath))).click();
    }

    @And("customer menekan tombol Edit Nomer HP")
    public void customerMenekanTombolEditNomerHP() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        String xpath = "//div[contains(@class, 'info-row-left') and contains(., 'Nomor HP')]/following-sibling::button[contains(@class, 'edit-btn')]";
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath))).click();
    }

    @And("customer mengubah Nomer HP menjadi {string}")
    public void customerMengubahNomerHP(String hpBaru) {
        WebElement inputHp = SetupSteps.driver.findElement(By.name("phone_number"));
        inputHp.clear();
        inputHp.sendKeys(hpBaru);
    }

    @And("customer menekan tombol Save Nomer HP")
    public void customerMenekanTombolSaveNomerHP() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        String xpath = "//div[contains(@class, 'info-row-left') and contains(., 'Nomor HP')]/following-sibling::button[contains(@class, 'save-btn')]";
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath))).click();
    }

    @And("customer menekan tombol Edit Password")
    public void customerMenekanTombolEditPassword() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        String xpath = "//div[contains(@class, 'info-row-left') and contains(., 'Password')]/following-sibling::button[contains(@class, 'edit-btn')]";
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath))).click();
    }

    @And("customer mengubah passwordnya menjadi {string}")
    public void customerMengubahPassword(String passwordBaru) {
        WebElement inputPass = SetupSteps.driver.findElement(By.id("pw-new"));
        inputPass.sendKeys(passwordBaru);
        inputPass = SetupSteps.driver.findElement(By.id("pw-confirm"));
        inputPass.sendKeys(passwordBaru);
    }

    @And("customer menekan tombol Save Password")
    public void customerMenekanTombolSavePassword() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        String xpath = "//div[contains(@class, 'info-row-left') and contains(., 'Password')]/following-sibling::button[contains(@class, 'save-btn')]";
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath))).click();
    }

    @Then("data profil customer berubah sesuai dengan inputan yang baru")
    public void dataProfilCustomerBerubah() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        // Validasi toast success atau perubahan teks pada UI profil
        boolean isSuccess = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("alert-success"))).isDisplayed();
        Assert.assertTrue("Profil gagal diperbarui!", isSuccess);
    }
}
