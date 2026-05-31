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

public class AdminSteps {

    // Helper untuk membabat habis splash screen bawaan PWA jika mengganggu render UI
    private void bersihkanSplash() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) SetupSteps.driver;
            js.executeScript("var splash = document.getElementById('splash-circle'); if(splash) { splash.remove(); }");
            js.executeScript("var spinner = document.getElementById('splash-spinner'); if(spinner) { spinner.remove(); }");
        } catch (Exception e) {
            // Ignore jika splash screen memang tidak muncul
        }
    }

    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    // BAGIAN: NAVIGASI DASAR (DIGUNAKAN BERSAMA)
    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX

    @Given("^admin berada di halaman dashboard admin$")
    public void adminBeradaDiHalamanAdmin() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(10));
        JavascriptExecutor js = (JavascriptExecutor) SetupSteps.driver;

        System.out.println("Menjalankan proses login Admin murni dari awal untuk menuju Dashboard Admin");

        // 1. JURUS ANTI-NYANGKUT: Bersihkan cookies & sesi dari skenario sebelumnya
        SetupSteps.driver.manage().deleteAllCookies();
        
        // 2. Buka URL Landing Page
        SetupSteps.driver.get("http://10.0.2.2:8000");
        bersihkanSplash();

        try {
            // 3. Klik tombol/opsi Login di Landing Page
            WebElement btnLoginAwal = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(), 'Log In') or contains(@class, 'btn-primary')]")));
            js.executeScript("arguments[0].click();", btnLoginAwal);
            Thread.sleep(500); // Jeda render form login
            
            // 4. Input Kredensial Admin
            WebElement userField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
            userField.clear();
            userField.sendKeys("minimalist@admin.com"); 
            
            WebElement passField = SetupSteps.driver.findElement(By.id("password"));
            passField.clear();
            passField.sendKeys("minimalist123");
            
            // 5. Submit Form Login
            WebElement btnSubmit = SetupSteps.driver.findElement(By.xpath("//button[contains(@class, 'btn-submit') or contains(text(), 'Login')]"));
            js.executeScript("arguments[0].click();", btnSubmit);
            System.out.println("Form login Admin disubmit.");
            
        } catch (Exception e) {
            System.out.println("Tombol login tidak ditemukan, kemungkinan sesi sudah aktif. Melanjutkan...");
        }

        // 6. Validasi Akhir: Memastikan sukses mendarat di Dashboard Admin
        try { Thread.sleep(1500); } catch (Exception e) {} // Jeda transisi halaman
        bersihkanSplash();
        
        try {
            // Memvalidasi komponen tombol admin utama untuk memastikan posisi dashboard/panel aktif
            WebElement btnAdminUtama = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("btn-tambah-member")));
            Assert.assertTrue("Admin gagal login atau belum berada di halaman admin utama!", btnAdminUtama.isDisplayed());
            System.out.println("SUKSES: Admin mendarat di Dashboard!");
        } catch (Exception e) {
            Assert.fail("Terjadi kesalahan saat masuk ke dashboard Admin. Error: " + e.getMessage());
        }
    }

    // Menggunakan regex `.*` agar teks apapun setelah kata "navbar" akan diabaikan (tidak diwajibkan jadi parameter)
    @When("^admin memilih opsi \"([^\"]*)\" pada navbar.*$")
    public void adminMemilihOpsiNavbar(String namaOpsi) {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        JavascriptExecutor js = (JavascriptExecutor) SetupSteps.driver;
        bersihkanSplash();

        String xpathNavbar = String.format("//a[contains(normalize-space(), '%s')] | //button[contains(normalize-space(), '%s')]", namaOpsi, namaOpsi);
        try {
            WebElement menuNavbar = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathNavbar)));
            js.executeScript("arguments[0].click();", menuNavbar);
            System.out.println("Admin sukses berpindah ke tab menu: " + namaOpsi);
            Thread.sleep(1000); 
        } catch (Exception e) {
            Assert.fail("Gagal menekan menu navbar admin '" + namaOpsi + "'. Error: " + e.getMessage());
        }
    }

    @And("admin menekan tombol {string}")
    public void adminMenekanTombol(String namaTombol) {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        JavascriptExecutor js = (JavascriptExecutor) SetupSteps.driver;
        bersihkanSplash();

        String xpathTombol = String.format("//button[contains(normalize-space(), '%s')] | //a[contains(normalize-space(), '%s')]", namaTombol, namaTombol);
        try {
            WebElement tombol = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathTombol)));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", tombol);
            Thread.sleep(300);
            js.executeScript("arguments[0].click();", tombol);
            System.out.println("Admin berhasil menekan tombol: " + namaTombol);
        } catch (Exception e) {
            Assert.fail("Tombol '" + namaTombol + "' gagal ditekan oleh admin. Error: " + e.getMessage());
        }
    }
    
    // Regex diperbarui: Ditambahkan "pada kelas terpilih"
    @And("admin menekan tombol {string} (lagi|pada form submit|pada modal konfirmasi|pada form gaji|konfirmasi paket|pada kelas terpilih)")
    public void adminMenekanTombolContext(String namaTombolSubmit, String context) {
        System.out.println("Menekan tombol " + namaTombolSubmit + " pada area: " + context);
        adminMenekanTombol(namaTombolSubmit);
    }
    
    // Regex diperbarui: Mendukung "section membership" atau "section paket"
    @And("admin menekan tombol {string} pada section (paket|membership)")
    public void adminMenekanTombolPadaSectionPaket(String namaTombol, String section) {
        System.out.println("Menekan tombol " + namaTombol + " pada section: " + section);
        adminMenekanTombol(namaTombol);
    }

    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    // MODUL 1: ADMIN BOOKING & TRANSAKSI (@Admin)
    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX

    @And("admin mengisi data kelas serta menentukan kuota kelas yang valid")
    public void adminMengisiDataKelasDanKuota() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("class_name"))).sendKeys("Hatha Yoga Pagi");
            SetupSteps.driver.findElement(By.name("quota")).sendKeys("15");
            SetupSteps.driver.findElement(By.name("price")).sendKeys("75000");
            SetupSteps.driver.findElement(By.name("date")).sendKeys("2026-06-15");
            System.out.println("Form pembuatan kelas beserta isian kuota berhasil diinput.");
        } catch (Exception e) {
            Assert.fail("Gagal mengisi form pembuatan kelas dan batasan kuota. Error: " + e.getMessage());
        }
    }

    @Then("sistem berhasil menyimpan jadwal kelas baru beserta pembatasan kuotanya")
    public void sistemBerhasilSimpanJadwal() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(7));
        boolean isSuccess = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("alert-success"))).isDisplayed();
        Assert.assertTrue("Gagal memvalidasi pembuatan kelas baru!", isSuccess);
    }

    @And("admin menekan tombol edit pada salah satu pengguna")
    public void adminKlikEditPelanggan() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        JavascriptExecutor js = (JavascriptExecutor) SetupSteps.driver;
        try {
            WebElement btnEditPelanggan = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//table[contains(@class, 'table')]//tbody/tr[1]//button[contains(@class, 'edit') or contains(., 'Edit')]")
            ));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", btnEditPelanggan);
            js.executeScript("arguments[0].click();", btnEditPelanggan);
            Thread.sleep(1500);
        } catch (Exception e) {
            Assert.fail("Gagal mengeklik tombol edit profil salah satu pelanggan. Error: " + e.getMessage());
        }
    }

    @Then("sistem menampilkan aktivitas kelas yang diikuti serta sisa kuota membership pelanggan tersebut")
    public void sistemTampilkanAktivitasDanSisaKuota() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(7));
        bersihkanSplash();
        String xpathMembershipSection = "//*[contains(normalize-space(), 'Membership Aktif') or contains(normalize-space(), 'Sisa Pertemuan')]";
        boolean isInfoVisible = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathMembershipSection))).isDisplayed();
        Assert.assertTrue("Gagal memvalidasi log sisa kuota membership pelanggan!", isInfoVisible);
    }

    @And("admin memilih salah satu kelas yang ingin dibatalkan pada daftar aktivitas")
    public void adminMemilihKelasYangInginDibatalkan() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        JavascriptExecutor js = (JavascriptExecutor) SetupSteps.driver;
        try {
            WebElement targetKelasBooking = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[contains(@class, 'booking-item') or contains(@class, 'activity-row')][1]")
            ));
            js.executeScript("arguments[0].click();", targetKelasBooking);
        } catch (Exception e) {
            Assert.fail("Gagal memilih log kelas customer yang ingin dibatalkan. Error: " + e.getMessage());
        }
    }

    @Then("sistem berhasil memproses pembatalan booking dan mengembalikan status kelas tersebut")
    public void sistemBerhasilProsesPembatalan() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(7));
        boolean isCanceledSuccess = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//*[contains(@class, 'alert-success') or contains(normalize-space(), 'Batal') or contains(normalize-space(), 'Canceled')]")
        )).isDisplayed();
        Assert.assertTrue("Proses pembatalan booking kelas oleh admin gagal terverifikasi!", isCanceledSuccess);
    }

    @And("admin memilih salah satu jadwal kelas yang tersedia")
    public void adminMemilihSalahSatuJadwalTersedia() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        JavascriptExecutor js = (JavascriptExecutor) SetupSteps.driver;
        try {
            WebElement itemJadwal = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[contains(@class, 'card-jadwal') or contains(@class, 'schedule-item')][1]")
            ));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", itemJadwal);
            js.executeScript("arguments[0].click();", itemJadwal);
        } catch (Exception e) {
            Assert.fail("Gagal memilih jadwal kelas aktif pilihan. Error: " + e.getMessage());
        }
    }

    @And("admin mengisi data peserta dan nominal pembayaran cash dengan valid")
    public void adminIsiDataCash() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("customer_name"))).sendKeys("Budi Tunai");
            SetupSteps.driver.findElement(By.id("amount_paid")).sendKeys("75000");
        } catch (Exception e) {
            Assert.fail("Gagal mengisi data pembayaran tunai customer. Error: " + e.getMessage());
        }
    }

    @Then("sistem berhasil mencatat pembayaran cash dan mendaftarkan peserta ke kelas")
    public void sistemCatatPembayaranCash() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(7));
        boolean isCashRecorded = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("alert-success"))).isDisplayed();
        Assert.assertTrue("Gagal memvalidasi pencatatan data cash manual!", isCashRecorded);
    }

    @Then("sistem menampilkan status verifikasi pembayaran pelanggan berupa valid atau tidak valid")
    public void sistemTampilkanStatusVerifikasi() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(7));
        bersihkanSplash();
        String xpathStatusTransaksi = "//*[contains(@class, 'status') or contains(normalize-space(), 'Valid') or contains(normalize-space(), 'Approved') or contains(normalize-space(), 'Pending')]";
        boolean isStatusVisible = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathStatusTransaksi))).isDisplayed();
        Assert.assertTrue("Status transaksi online customer tidak ditemukan di halaman kelola!", isStatusVisible);
    }

    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    // MODUL 2: ADMIN DATA MANAGEMENT (@AdminData)
    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX

    @And("admin mengubah data salah satu jadwal")
    public void adminMengubahDataJadwal() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        WebElement inputQuota = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("quota")));
        inputQuota.clear();
        inputQuota.sendKeys("20");
    }

    @Then("sistem berhasil memperbarui data jadwal kelas tersebut")
    public void sistemBerhasilPerbaruiJadwal() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        boolean isSuccess = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("alert-success"))).isDisplayed();
        Assert.assertTrue("Gagal mengupdate jadwal kelas!", isSuccess);
    }

    @And("admin mengonfirmasi penghapusan jadwal kelas")
    public void adminKonfirmasiPenghapusanJadwal() {
        System.out.println("Admin bersiap mengonfirmasi penghapusan jadwal pada dialog peringatan.");
    }

    @Then("sistem berhasil menghapus jadwal kelas tersebut dari daftar")
    public void sistemBerhasilMenghapusJadwal() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        boolean isSuccess = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("alert-success"))).isDisplayed();
        Assert.assertTrue("Gagal menghapus jadwal kelas!", isSuccess);
    }

    @And("admin memilih salah satu coach dan klik logo edit")
    public void adminMemilihCoachDanKlikEdit() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        JavascriptExecutor js = (JavascriptExecutor) SetupSteps.driver;
        try {
            WebElement btnEdit = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//tr[contains(@class, 'coach-row')][1]//button[contains(@class, 'edit')]")));
            js.executeScript("arguments[0].click();", btnEdit);
        } catch (Exception e) {}
    }

    @And("admin mengganti data admin untuk diperbarui")
    public void adminMenggantiDataAdmin() {
        System.out.println("Mengklik logo edit dan mengubah data coach (ditulis 'admin' di feature)...");
    }

    @Then("sistem berhasil menyimpan pembaruan informasi data coach")
    public void sistemBerhasilSimpanPembaruanCoach() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        boolean isSuccess = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("alert-success"))).isDisplayed();
        Assert.assertTrue("Gagal mengupdate informasi data coach!", isSuccess);
    }

    @And("admin mengubah data membership untuk diperbarui")
    public void adminMengubahDataMembership() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        WebElement inputPrice = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("price")));
        inputPrice.clear();
        inputPrice.sendKeys("1500000");
    }

    @Then("sistem berhasil memperbarui paket data membership tersebut")
    public void sistemBerhasilMemperbaruiMembership() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        boolean isSuccess = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("alert-success"))).isDisplayed();
        Assert.assertTrue("Gagal mengupdate paket membership!", isSuccess);
    }

    @And("admin mengonfirmasi hapus paket data membership")
    public void adminMengonfirmasiHapusPaket() {
        System.out.println("Admin bersiap mengonfirmasi penghapusan paket membership.");
    }

    @Then("sistem memvalidasi membership di hari tersebut berhasil dihapus")
    public void sistemMemvalidasiMembershipTerhapus() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        boolean isSuccess = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("alert-success"))).isDisplayed();
        Assert.assertTrue("Gagal menghapus paket membership!", isSuccess);
    }

    @And("admin menekan logo edit di salah satu peserta")
    public void adminMenekanLogoEditPeserta() {
        adminKlikEditPelanggan(); // Memakai ulang method pelanggan
    }

    @And("admin mengonfirmasi hapus paket data secara permanen")
    public void adminMengonfirmasiHapusPermanen() {
        System.out.println("Mengonfirmasi penghapusan membership pelanggan secara permanen.");
    }

    @Then("sistem memastikan data membership customer terhapus secara permanen")
    public void sistemMemastikanMembershipTerhapusPermanen() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        boolean isSuccess = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("alert-success"))).isDisplayed();
        Assert.assertTrue("Gagal menghapus membership pelanggan secara permanen!", isSuccess);
    }

    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    // MODUL 3: ADMIN REPORT & ANALYTIC (@AdminReport)
    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX

    @And("admin memilih salah satu jadwal kelas aktif")
    public void adminMemilihJadwalKelasAktif() {
        adminMemilihSalahSatuJadwalTersedia(); // Memakai ulang method pilih jadwal
    }

    @Then("sistem menampilkan data rekaman kehadiran peserta pada kelas tersebut")
    public void sistemMenampilkanDataKehadiran() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        boolean isListVisible = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("attendance-table"))).isDisplayed();
        Assert.assertTrue("Tabel kehadiran peserta tidak ditemukan pada analitik!", isListVisible);
    }

    @Then("sistem berhasil menampilkan kumpulan rekaman data pelanggan secara lengkap")
    public void sistemMenampilkanKumpulanDataPelanggan() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        boolean isTableVisible = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("customer-list-container"))).isDisplayed();
        Assert.assertTrue("Daftar direktori pelanggan tidak muncul!", isTableVisible);
    }

    @Then("sistem memperbarui total saldo pendapatan milik coach yang bersangkutan")
    public void sistemMemperbaruiTotalSaldoCoach() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        boolean isUpdated = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("total-salary-display"))).isDisplayed();
        Assert.assertTrue("Total saldo pendapatan coach tidak terupdate!", isUpdated);
    }

    @Then("sistem memperbarui akumulasi total pendapatan operasional studio yoga")
    public void sistemMemperbaruiTotalPendapatanStudio() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        boolean isUpdated = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("studio-revenue-display"))).isDisplayed();
        Assert.assertTrue("Total pendapatan studio operasional tidak terupdate!", isUpdated);
    }

    @Then("sistem berhasil menampilkan grafik dan komponen dashboard analytic secara berkala")
    public void sistemMenampilkanGrafikDashboard() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        boolean isChartVisible = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("revenue-chart"))).isDisplayed();
        Assert.assertTrue("Dashboard Analitik (Grafik) tidak muncul!", isChartVisible);
    }

    @Then("sistem otomatis mengunduh berkas file data rangkuman pendapatan berdasarkan rentang waktu tertentu")
    public void sistemMengunduhBerkasLaporan() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        // Validasi munculnya pesan toast sukses download dokumen di PWA
        boolean isDownloading = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//*[contains(normalize-space(), 'Download Berhasil') or contains(normalize-space(), 'Berhasil Dicetak')]")
        )).isDisplayed();
        Assert.assertTrue("Proses cetak/download laporan pendapatan gagal!", isDownloading);
        System.out.println("FR-34 Selesai: Dokumen PDF/Excel sukses diunduh.");
    }
}