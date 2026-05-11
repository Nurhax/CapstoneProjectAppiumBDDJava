package com.TA.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class CoachSteps {

    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    // PRE-CONDITION: LOGIN COACH
    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX

    @Given("^coach sudah login dan berada di halaman coach$")
    public void coachSudahLogin() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(10));
        
        SetupSteps.driver.get("http://10.0.2.2:8000");
        
        try {
            wait.until(ExpectedConditions.elementToBeClickable(By.className("btn-primary"))).click();
            WebElement userField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
            userField.sendKeys("iqbalu"); 
            SetupSteps.driver.findElement(By.id("password")).sendKeys("test123");
            SetupSteps.driver.findElement(By.className("btn-submit")).click();
        } catch (Exception e) {
            System.out.println("Sesi login Coach sudah aktif.");
        }
        
        // Memastikan sudah masuk ke halaman Coach
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(@class, 'bottom-nav-item')]")));
    }

    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    // NAVIGASI NAVBAR
    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX

    @When("coach menekan tab {string} pada navbar")
    public void coachMenekanTabNavbar(String namaTab) {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(10));
        String xpath = String.format("//a[contains(normalize-space(), '%s')]", namaTab);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath))).click();
    }

    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    // MODUL JADWAL & PESERTA (FR 17 & 20)
    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX

    @Then("coach dapat melihat jadwal kelas yang harus diajar hari ini")
    public void coachMelihatJadwalHariIni() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(10));
        boolean isJadwalVisible = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("coach-schedule-container"))).isDisplayed();
        Assert.assertTrue("Jadwal mengajar Coach tidak ditemukan!", isJadwalVisible);
    }

    @When("coach memilih jadwal kelas yang tersedia hari ini")
    public void coachMemilihJadwal() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(By.className("card-class"))).click();
    }

    @And("coach menekan tombol {string}")
    public void coachMenekanTombol(String namaTombol) {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(10));
        String xpath = String.format("//button[contains(normalize-space(), '%s')] | //a[contains(normalize-space(), '%s')]", namaTombol, namaTombol);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath))).click();
    }

    @Then("coach dapat melihat daftar peserta untuk kelas tersebut")
    public void coachMelihatDaftarPeserta() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(10));
        boolean isListVisible = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("participant-list"))).isDisplayed();
        Assert.assertTrue("Daftar peserta tidak muncul!", isListVisible);
    }

    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    // VERIFIKASI KEHADIRAN (FR 21)
    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX

    // Pre-condition lama (disimpan untuk backup jika Gherkin direvisi lagi)
    @Given("coach sudah login dan berada di halaman daftar peserta kelas")
    public void beradaDiHalamanPeserta() {
        SetupSteps.driver.get("http://10.0.2.2:8000/coach/schedule/37");
    }

    @When("coach mengklik logo checklist pada peserta di bagian {string}")
    public void klikChecklistPeserta(String status) {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(10));
        String xpath = String.format("//div[contains(@id, 'not-present')]//button[contains(@class, 'btn-check')]");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath))).click();
    }

    // Teks disesuaikan dengan feature file yang baru
    @Then("data peserta tersebut berubah menjadi ke bagian {string}")
    public void dataPesertaBerubah(String statusTujuan) {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        String xpath = String.format("//div[contains(@id, 'present')]//*[contains(text(), 'Hadir')]");
        boolean isMoved = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath))).isDisplayed();
        Assert.assertTrue("Peserta gagal dipindahkan ke bagian Hadir!", isMoved);
    }

    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    // UPLOAD BUKTI HADIR (FR 23) - Teks disesuaikan
    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX

    @When("coach menekan tombol select file pada upload bukti hadir")
    public void klikSelectFile() {
        System.out.println("Mempersiapkan upload file...");
    }

    @And("coach memilih foto bukti kelas")
    public void memilihFotoBukti() {
        String filePath = "C:\\DataTA\\bukti_hadir.jpg";
        WebElement fileInput = SetupSteps.driver.findElement(By.id("file-upload-input"));
        fileInput.sendKeys(filePath);
    }

    @And("foto berhasil tersimpan ke dalam form bukti hadir")
    public void fotoTersimpan() {
        boolean isPreviewVisible = SetupSteps.driver.findElement(By.id("image-preview")).isDisplayed();
        Assert.assertTrue("Preview foto tidak muncul setelah dipilih!", isPreviewVisible);
    }

    @Then("sistem berhasil menyimpan pembaruan kelas beserta bukti kehadiran")
    public void sistemSimpanUpdate() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(10));
        boolean isSuccess = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("alert-success"))).isDisplayed();
        Assert.assertTrue("Gagal mengupdate kelas!", isSuccess);
    }

    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    // LAPORAN GAJI (FR 27)
    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX

    // Teks disesuaikan dengan feature file yang baru
    @Then("coach dapat melihat laporan gaji dari kelas yang telah selesai")
    public void coachMelihatLaporanGaji() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(10));
        WebElement salaryTable = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("salary-report-table")));
        Assert.assertTrue("Laporan gaji tidak ditemukan di halaman profil!", salaryTable.isDisplayed());
    }
}