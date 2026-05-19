package com.TA.steps;

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
import java.time.Duration;

public class CoachSteps {
    public static String checkedParticipantName = "";
    
    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    // PRE-CONDITION: LOGIN COACH + BYPASS SPLASH
    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX

    @Given("^coach sudah login dan berada di halaman coach$")
    public void coachSudahLogin() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        JavascriptExecutor js = (JavascriptExecutor) SetupSteps.driver;
        
        SetupSteps.driver.get("http://10.0.2.2:8000");
        
        try {
            // Bersihkan animasi awal jika ada sebelum login
            js.executeScript("var splash = document.getElementById('splash-circle'); if(splash) { splash.remove(); }");
            js.executeScript("var spinner = document.getElementById('splash-spinner'); if(spinner) { spinner.remove(); }");
            
            WebElement btnLoginUtama = wait.until(ExpectedConditions.elementToBeClickable(By.className("btn-primary")));
            js.executeScript("arguments[0].click();", btnLoginUtama);
            
            WebElement userField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
            userField.sendKeys("iqbaltest@coach.com"); 
            SetupSteps.driver.findElement(By.id("password")).sendKeys("test123");
            
            WebElement btnSubmit = SetupSteps.driver.findElement(By.className("btn-submit"));
            js.executeScript("arguments[0].click();", btnSubmit);
            
            System.out.println("Form login Coach disubmit.");
        } catch (Exception e) {
            System.out.println("Sesi login Coach terdeteksi sudah aktif atau langsung redirect.");
        }
        
        // Kasih jeda napas pasca-login buat transisi page
        try { Thread.sleep(1500); } catch (Exception e) {}
        
        // Hancurkan splash screen pasca-login agar tidak mengintersep navigasi berikutnya
        try {
            js.executeScript("var splash = document.getElementById('splash-circle'); if(splash) { splash.remove(); }");
            js.executeScript("var spinner = document.getElementById('splash-spinner'); if(spinner) { spinner.remove(); }");
        } catch (Exception e) {}

        // Memastikan bottom navbar sudah ter-render sempurna
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(@class, 'coach-nav-item')]")));
    }

    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    // NAVIGASI NAVBAR
    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX

    @When("coach menekan tab {string} pada navbar")
    public void coachMenekanTabNavbar(String namaTab) {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        JavascriptExecutor js = (JavascriptExecutor) SetupSteps.driver;

        // Bersihkan animasi sebelum berinteraksi dengan navbar
        try {
            js.executeScript("var splash = document.getElementById('splash-circle'); if(splash) { splash.remove(); }");
            js.executeScript("var spinner = document.getElementById('splash-spinner'); if(spinner) { spinner.remove(); }");
            Thread.sleep(500);
        } catch (Exception e) {}

        // Menggunakan normalize-space() agar kebal terhadap whitespace HTML formatting
        String xpath = String.format("//a[contains(normalize-space(), '%s')]", namaTab);
        WebElement tabElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
        
        // Klik menggunakan JavascriptExecutor biar anti-intercepted
        js.executeScript("arguments[0].click();", tabElement);
        System.out.println("Coach menekan tab: " + namaTab);
    }

    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    // MODUL JADWAL & PESERTA (FR 17 & 20)
    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX

    @Then("coach dapat melihat jadwal kelas yang harus diajar hari ini")
    public void coachMelihatJadwalHariIni() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        JavascriptExecutor js = (JavascriptExecutor) SetupSteps.driver;
        
        // Clear splash lagi karena perpindahan tab memicu rendering splash baru
        try {
            js.executeScript("var splash = document.getElementById('splash-circle'); if(splash) { splash.remove(); }");
        } catch (Exception e) {}

        boolean isJadwalVisible = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("schedule-card"))).isDisplayed();
        Assert.assertTrue("Jadwal mengajar Coach tidak ditemukan!", isJadwalVisible);
    }

    @When("coach memilih jadwal kelas yang tersedia hari ini")
    public void coachMemilihJadwal() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        JavascriptExecutor js = (JavascriptExecutor) SetupSteps.driver;
        
        // Temukan card-class jadwal mengajar
        WebElement cardJadwal = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("btn-cek-jadwal")));
        
        // Scroll dan klik pakai JS
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", cardJadwal);
        js.executeScript("arguments[0].click();", cardJadwal);
    }

    @And("coach menekan tombol {string}")
    public void coachMenekanTombol(String namaTombol) {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        JavascriptExecutor js = (JavascriptExecutor) SetupSteps.driver;

        // Mendukung pencarian tag <button> maupun tag hyperlink <a>
        String xpath = String.format("//button[contains(normalize-space(), '%s')] | //a[contains(normalize-space(), '%s')]", namaTombol, namaTombol);
        WebElement tombol = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
        
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", tombol);
        js.executeScript("arguments[0].click();", tombol);
        System.out.println("Coach menekan tombol: " + namaTombol);
    }

    @Then("coach dapat melihat daftar peserta untuk kelas tersebut")
    public void coachMelihatDaftarPeserta() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        JavascriptExecutor js = (JavascriptExecutor) SetupSteps.driver;
        
        // Bersihkan splash screen sesampainya di halaman detail jadwal
        try {
            js.executeScript("var splash = document.getElementById('splash-circle'); if(splash) { splash.remove(); }");
            js.executeScript("var spinner = document.getElementById('splash-spinner'); if(spinner) { spinner.remove(); }");
            Thread.sleep(500);
        } catch (Exception e) {}

        boolean isListVisible = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("absen-table"))).isDisplayed();
        Assert.assertTrue("Daftar peserta tidak muncul!", isListVisible);
    }

    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    // VERIFIKASI KEHADIRAN (FR 21)
    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX

    @When("coach mengklik logo checklist pada peserta di bagian {string}")
    public void klikChecklistPeserta(String status) {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        JavascriptExecutor js = (JavascriptExecutor) SetupSteps.driver;

        try {
            // 1. Cari baris pertama (tr) di dalam tabel yang statusnya masih 'Tidak Hadir'
            // Kita kunci tr yang di dalamnya mengandung class 'status-badge-tidak'
            String xpathRowTidakHadir = "//table[@class='absen-table']/tbody/tr[td//span[@class='status-badge-tidak']][1]";
            WebElement rowTarget = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathRowTidakHadir)));

            // 2. Ambil teks nama pelanggan dari td pertama di baris tersebut
            WebElement elementNama = rowTarget.findElement(By.xpath("./td[1]"));
            checkedParticipantName = elementNama.getText().trim();
            System.out.println("Menyimpan nama peserta yang akan diabsen: " + checkedParticipantName);

            // 3. Temukan tombol (.btn-check) yang berada di dalam baris yang SAMA
            WebElement btnChecklist = rowTarget.findElement(By.xpath(".//button[contains(@class, 'btn-check')]"));
            
            // Scroll dan eksekusi klik via JS
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", btnChecklist);
            js.executeScript("arguments[0].click();", btnChecklist);
            System.out.println("Berhasil mencentang kehadiran untuk: " + checkedParticipantName);
            Thread.sleep(500);

            // 4. Klik tombol Update Kelas (.btn-update) untuk submit data
            WebElement btnUpdate = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("btn-update")));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", btnUpdate);
            js.executeScript("arguments[0].click();", btnUpdate);
            System.out.println("Tombol Update Kelas (.btn-update) berhasil ditekan!");
            
            // Beri jeda proses submit database
            Thread.sleep(2500);

        } catch (Exception e) {
            Assert.fail("Gagal memproses centang tabel absen atau update kelas. Error: " + e.getMessage());
        }
    }

    @Then("data peserta tersebut berubah menjadi ke bagian {string}")
    public void dataPesertaBerubah(String statusTujuan) {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        JavascriptExecutor js = (JavascriptExecutor) SetupSteps.driver;

        // Validasi awal variabel global
        Assert.assertFalse("Variabel checkedParticipantName kosong!", checkedParticipantName.isEmpty());

        try {
            // 1. Bersihkan splash screen pasca-redirect/reload
            try {
                js.executeScript("var splash = document.getElementById('splash-circle'); if(splash) { splash.remove(); }");
            } catch (Exception e) {}

            // 2. Klik tombol "Cek Jadwal" dulu untuk masuk kembali ke detail daftar
            String xpathCekJadwal = "//button[contains(normalize-space(), 'Cek Jadwal')] | //a[contains(normalize-space(), 'Cek Jadwal')]";
            WebElement btnCekJadwal = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathCekJadwal)));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", btnCekJadwal);
            js.executeScript("arguments[0].click();", btnCekJadwal);
            System.out.println("Membuka ulang halaman jadwal untuk verifikasi...");
            Thread.sleep(1500);

            // Bersihkan splash screen lagi kalau halamannya memuat ulang DOM
            try {
                js.executeScript("var splash = document.getElementById('splash-circle'); if(splash) { splash.remove(); }");
            } catch (Exception e) {}

            // 3. Pengecekan tabel: Cari baris (tr) yang kolom pertamanya berisi nama si target (Case-Insensitive)
            // Dan pastikan kolom keduanya sekarang memiliki elemen dengan class 'status-badge-hadir'
            String nameLower = checkedParticipantName.toLowerCase();
            
            String xpathValidasiTabelHadir = String.format(
                "//table[@class='absen-table']/tbody/tr[td[1][contains(translate(normalize-space(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '%s')]]" +
                "/td[2]//*[contains(@class, 'status-badge-hadir') or contains(text(), 'Hadir')]",
                nameLower
            );

            System.out.println("Memeriksa baris tabel untuk nama '" + checkedParticipantName + "' dengan badge status hadir...");
            
            WebElement badgeHadir = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathValidasiTabelHadir)));
            
            Assert.assertTrue("Gawat! Status peserta '" + checkedParticipantName + "' di tabel belum berubah menjadi Hadir!", badgeHadir.isDisplayed());
            System.out.println("SUKSES SAKTI! Teks/Badge Hadir ditemukan untuk peserta: " + checkedParticipantName);

        } catch (Exception e) {
            Assert.fail("Validasi gagal! Baris tabel dengan nama '" + checkedParticipantName + "' tidak memiliki badge 'status-badge-hadir'. Error: " + e.getMessage());
        }
    }

    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    // BACKUP METHOD / UNUSED STEPS REMAINING
    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX

//    @Given("coach sudah login dan berada di halaman daftar peserta kelas")
//    public void beradaDiHalamanPeserta() {
//        SetupSteps.driver.get("http://10.0.2.2:8000/coach/schedule/37");
//    }
}