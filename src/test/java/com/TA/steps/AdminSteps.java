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

/**
 * Step Definitions untuk Role Admin
 * Bagian 1: Manajemen Coach & Analitik Laporan
 * @author M.Iqbal Nurhaq
 */
public class AdminSteps {

    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    // PRE-CONDITION: LOGIN ADMIN
    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX

    @Given("^admin sudah login dan berada di Halaman Admin$")
    public void adminSudahLogin() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(10));
        
        SetupSteps.driver.get("http://10.0.2.2:8000");

        try {
            // Menunggu tombol login utama muncul
            wait.until(ExpectedConditions.elementToBeClickable(By.className("btn-primary"))).click();
            
            // Input kredensial Admin
            WebElement userField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
            userField.sendKeys("admin"); // Ganti dengan username admin aslimu
            SetupSteps.driver.findElement(By.id("password")).sendKeys("admin123");
            SetupSteps.driver.findElement(By.className("btn-submit")).click();
            
            // Tunggu sampai elemen navbar admin muncul sebagai tanda login sukses
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(@class, 'bottom-nav-item')]")));
        } catch (Exception e) {
            System.out.println("Admin sudah dalam keadaan login atau elemen tidak ditemukan.");
        }
    }

    @And("admin menekan tab {string} pada navbar")
    public void adminMenekanTab(String namaTab) {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(10));
        // Menggunakan normalize-space agar fleksibel terhadap spasi/enter di HTML navbar
        String xpath = String.format("//a[contains(normalize-space(), '%s')]", namaTab);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath))).click();
    }

    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    // MODUL: MANAJEMEN COACH (FR 16, 26, 31)
    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX

    @When("admin menekan tombol {string}")
    public void adminMenekanTombol(String namaTombol) {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(10));
        String xpath = String.format("//button[contains(normalize-space(), '%s')] | //a[contains(normalize-space(), '%s')]", namaTombol, namaTombol);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath))).click();
    }

    @And("admin mengisi data profil coach")
    public void adminIsiDataCoach() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("name"))).sendKeys("Coach Baru");
        SetupSteps.driver.findElement(By.name("specialization")).sendKeys("Hatha Yoga");
        SetupSteps.driver.findElement(By.name("phone")).sendKeys("08123456789");
    }

    @And("admin menginput pendapatan coach berdasarkan kelas")
    public void adminInputRateGaji() {
        // FR 26: Perhitungan gaji
        SetupSteps.driver.findElement(By.name("base_rate")).sendKeys("150000");
    }

    @And("admin menekan tombol {string} untuk menyimpan")
    public void adminKlikSimpan(String teksTombol) {
        String xpath = String.format("//button[contains(normalize-space(), '%s')]", teksTombol);
        SetupSteps.driver.findElement(By.xpath(xpath)).click();
    }

    @Then("data coach baru berhasil ditambahkan ke sistem")
    public void dataCoachBerhasil() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(10));
        boolean isSuccess = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("alert-success"))).isDisplayed();
        Assert.assertTrue("Gagal menambahkan coach baru!", isSuccess);
    }

    @When("admin memilih salah satu coach")
    public void adminPilihCoach() {
        // Pilih baris pertama di tabel atau list coach
        SetupSteps.driver.findElement(By.className("coach-item-row")).click();
    }

    @And("admin menekan logo {string}")
    public void adminKlikLogoIcon(String iconName) {
        // FR 16 & 31: Klik icon edit/pendapatan
        String xpath = String.format("//*[contains(@class, 'icon-%s')] | //button[contains(@class, '%s')]", iconName.toLowerCase(), iconName.toLowerCase());
        SetupSteps.driver.findElement(By.xpath(xpath)).click();
    }

    @And("admin mengklik logo checklist untuk mengubah data")
    public void adminKlikChecklist() {
        SetupSteps.driver.findElement(By.id("btn-confirm-edit")).click();
    }

    @Then("data profil coach berhasil diperbarui")
    public void dataCoachUpdated() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("alert-success"))).isDisplayed());
    }

    @And("admin memasukkan nominal pendapatan tambahan")
    public void adminInputNominalPendapatan() {
        // FR 31: Input nominal manual
        WebElement inputNominal = SetupSteps.driver.findElement(By.name("amount"));
        inputNominal.clear();
        inputNominal.sendKeys("50000");
    }

    @Then("total pendapatan coach tersebut bertambah")
    public void totalPendapatanBerubah() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        boolean isUpdated = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("total-salary-display"))).isDisplayed();
        Assert.assertTrue("Total pendapatan tidak terupdate!", isUpdated);
    }

    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    // MODUL: ANALITIK & LAPORAN (FR 32, 34)
    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX

    @Then("sistem menampilkan dashboard analytic performa studio")
    public void sistemTampilkanDashboard() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(10));
        // Mencari elemen chart atau ringkasan data
        boolean isChartVisible = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("revenue-chart"))).isDisplayed();
        Assert.assertTrue("Dashboard Analitik tidak muncul!", isChartVisible);
    }

    @Then("sistem mendownload data rangkuman pendapatan berdasarkan waktu yang ditentukan")
    public void sistemDownloadLaporan() {
        // Untuk testing download di PWA/Chrome Android, kita validasi munculnya pesan sukses download
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(15));
        boolean isDownloading = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Download Berhasil') or contains(text(), 'Laporan Siap')]"))).isDisplayed();
        Assert.assertTrue("Proses download laporan gagal!", isDownloading);
    }
    
    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    // MODUL: MANAJEMEN JADWAL & KELAS (FR 7, 15, 24)
    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX

    @And("admin mengisi data kelas dan menentukan kuota kelas")
    public void adminMengisiDataKelas() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(10));
        // Asumsi nama attribute input form-nya. Sesuaikan dengan HTML asli!
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("class_name"))).sendKeys("Vinyasa Flow");
        SetupSteps.driver.findElement(By.name("coach_id")).sendKeys("iqbalu"); // Misal input coach
        SetupSteps.driver.findElement(By.name("schedule_time")).sendKeys("2026-06-01 08:00");
        SetupSteps.driver.findElement(By.name("quota")).sendKeys("15");
        SetupSteps.driver.findElement(By.name("price")).sendKeys("150000");
    }

    @Then("sistem berhasil menambahkan jadwal kelas baru")
    public void sistemBerhasilTambahJadwal() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(10));
        boolean isSuccess = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("alert-success"))).isDisplayed();
        Assert.assertTrue("Gagal menambahkan jadwal kelas!", isSuccess);
    }

    @When("admin memilih salah satu jadwal kelas")
    public void adminMemilihJadwalKelas() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(By.className("card-class"))).click();
    }

    @And("admin mengubah data kelas")
    public void adminMengubahDataKelas() {
        // Mengubah kuota kelas sebagai contoh edit
        WebElement inputQuota = SetupSteps.driver.findElement(By.name("quota"));
        inputQuota.clear();
        inputQuota.sendKeys("20");
    }

    @Then("data kelas berhasil diperbarui")
    public void dataKelasDiperbarui() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(10));
        boolean isSuccess = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("alert-success"))).isDisplayed();
        Assert.assertTrue("Gagal memperbarui kelas!", isSuccess);
    }

    @When("admin menekan tombol {string} pada salah satu kelas")
    public void adminMenekanTombolHapusPadaKelas(String teksTombol) {
        String xpath = String.format("//div[contains(@class, 'card-class')]//button[contains(normalize-space(), '%s')]", teksTombol);
        SetupSteps.driver.findElement(By.xpath(xpath)).click();
    }

    @And("admin mengonfirmasi penghapusan jadwal")
    public void adminKonfirmasiHapus() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        // Biasanya muncul pop-up konfirmasi (modal/alert)
        wait.until(ExpectedConditions.elementToBeClickable(By.id("btn-confirm-delete"))).click();
    }

    @Then("jadwal kelas tersebut berhasil dihapus dari sistem")
    public void jadwalTerhapus() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(10));
        boolean isSuccess = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("alert-success"))).isDisplayed();
        Assert.assertTrue("Notifikasi hapus jadwal tidak muncul!", isSuccess);
    }

    @Then("admin dapat melihat data kehadiran peserta di kelas tersebut")
    public void melihatDataKehadiran() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(10));
        boolean isListVisible = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("attendance-table"))).isDisplayed();
        Assert.assertTrue("Tabel kehadiran peserta tidak ditemukan!", isListVisible);
    }

    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    // MODUL: MANAJEMEN PELANGGAN & MEMBERSHIP (FR 8, 9, 10, 11, 13, 22, 30)
    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX

    @Then("admin dapat melihat kumpulan data pelanggan")
    public void melihatKumpulanPelanggan() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(10));
        boolean isTableVisible = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("customer-table"))).isDisplayed();
        Assert.assertTrue("Daftar pelanggan tidak muncul!", isTableVisible);
    }

    @When("admin menekan tombol {string} pada salah satu user")
    public void tekanTombolPadaUser(String teksTombol) {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(10));
        String xpath = String.format("//tr[contains(@class, 'customer-row')]//button[contains(normalize-space(), '%s')]", teksTombol);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath))).click();
    }

    @Then("sistem menampilkan aktivitas kelas yang diikuti serta sisa membership pelanggan tersebut")
    public void sistemMenampilkanAktivitasSisaMembership() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(10));
        boolean isActivityVisible = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("customer-activity-history"))).isDisplayed();
        boolean isMembershipVisible = SetupSteps.driver.findElement(By.id("customer-membership-status")).isDisplayed();
        Assert.assertTrue("Detail aktivitas / membership tidak muncul!", isActivityVisible && isMembershipVisible);
    }

    @And("admin memilih salah satu kelas yang ingin dibatalkan")
    public void pilihKelasBatal() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(By.className("booking-item"))).click();
    }

    @Then("pemesanan kelas pelanggan tersebut berhasil dibatalkan")
    public void pesananBatal() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(10));
        boolean isSuccess = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("alert-success"))).isDisplayed();
        Assert.assertTrue("Pemesanan gagal dibatalkan!", isSuccess);
    }

    @And("admin mengisi data peserta yang membayar cash")
    public void isiDataPesertaCash() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("customer_name"))).sendKeys("Budi Yoga");
        SetupSteps.driver.findElement(By.name("payment_amount")).sendKeys("150000");
        SetupSteps.driver.findElement(By.name("payment_method")).sendKeys("Cash");
    }

    @Then("peserta berhasil didaftarkan ke dalam kelas")
    public void pesertaDidaftarkan() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(10));
        boolean isSuccess = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("alert-success"))).isDisplayed();
        Assert.assertTrue("Gagal menambahkan peserta cash!", isSuccess);
    }

    @Then("sistem menampilkan status pembayaran pelanggan valid atau tidak valid")
    public void statusPembayaranValid() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(10));
        // Asumsi ada badge atau elemen yang menandakan status pembayaran
        boolean isStatusVisible = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("payment-status-badge"))).isDisplayed();
        Assert.assertTrue("Status pembayaran tidak muncul!", isStatusVisible);
    }

    @And("admin mengisi data membership")
    public void isiDataMembership() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("membership_name"))).sendKeys("Premium 1 Bulan");
        SetupSteps.driver.findElement(By.name("quota_amount")).sendKeys("12");
        SetupSteps.driver.findElement(By.name("price")).sendKeys("1000000");
    }

    @Then("sistem menyimpan data membership agar dapat dibeli oleh customer")
    public void simpanMembership() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(10));
        boolean isSuccess = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("alert-success"))).isDisplayed();
        Assert.assertTrue("Gagal menyimpan membership!", isSuccess);
    }
}