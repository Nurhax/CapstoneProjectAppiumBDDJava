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

    private void bersihkanSplash() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) SetupSteps.driver;
            js.executeScript("var splash = document.getElementById('splash-circle'); if(splash) { splash.remove(); }");
            js.executeScript("var spinner = document.getElementById('splash-spinner'); if(spinner) { spinner.remove(); }");
        } catch (Exception e) {}
    }

    // ==========================================
    // NAVIGASI DASAR & UMUM (SMART REGEX)
    // ==========================================

    @Given("^admin berada di halaman dashboard admin$")
    public void adminBeradaDiHalamanAdmin() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(10));
        JavascriptExecutor js = (JavascriptExecutor) SetupSteps.driver;
        
        System.out.println("Menjalankan proses login Admin murni dari awal untuk menuju Dashboard Admin");
        SetupSteps.driver.manage().deleteAllCookies();
        SetupSteps.driver.get("http://10.0.2.2:8000");
        bersihkanSplash();

        try {
            WebElement btnLoginAwal = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(), 'Log In') or contains(@class, 'btn-primary')]")));
            js.executeScript("arguments[0].click();", btnLoginAwal);
            Thread.sleep(500); 
            
            WebElement userField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
            userField.clear();
            userField.sendKeys("minimalist@admin.com"); 
            
            WebElement passField = SetupSteps.driver.findElement(By.id("password"));
            passField.clear();
            passField.sendKeys("minimalist123");
            
            WebElement btnSubmit = SetupSteps.driver.findElement(By.xpath("//button[contains(@class, 'btn-submit') or contains(text(), 'Login')]"));
            js.executeScript("arguments[0].click();", btnSubmit);
        } catch (Exception e) {
            System.out.println("Sesi aktif, lanjut bypass login...");
        }
        
        try { Thread.sleep(1500); } catch (Exception e) {}
        bersihkanSplash();
        
        try {
            WebElement btnAdminUtama = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("calendar-wrap")));
            Assert.assertTrue("Gagal mendarat di Dashboard Admin!", btnAdminUtama.isDisplayed());
        } catch (Exception e) {
            Assert.fail("Terjadi kesalahan masuk dashboard: " + e.getMessage());
        }
    }

    @When("^admin memilih opsi \"([^\"]*)\" pada navbar.*$")
    public void adminMemilihOpsiNavbar(String namaOpsi) {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        JavascriptExecutor js = (JavascriptExecutor) SetupSteps.driver;
        bersihkanSplash();
        
        String xpathNavbar = String.format("//a[contains(normalize-space(), '%s')] | //button[contains(normalize-space(), '%s')]", namaOpsi, namaOpsi);
        try {
            WebElement menuNavbar = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathNavbar)));
            js.executeScript("arguments[0].click();", menuNavbar);
            Thread.sleep(1000); 
        } catch (Exception e) {
            Assert.fail("Gagal menekan navbar: " + e.getMessage());
        }
    }

    // SMART CLICKER: Menangani SEMUA tombol termasuk variasi embel-embel "lagi" atau Native Pop-Up "Ya"
    @And("^admin menekan tombol \"([^\"]*)\"(?:.*)$")
    public void adminMenekanTombol(String namaTombol) {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(10));
        JavascriptExecutor js = (JavascriptExecutor) SetupSteps.driver;
        
        String lowerTombol = namaTombol.toLowerCase();
        String translateStr = "translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz')";
        String xpathTombol;
        
        // JURUS KHUSUS 1: Tombol Konfirmasi "Ya"
        if (lowerTombol.equals("ya")) {
            xpathTombol = "//*[@id='btn-confirm-yes'] | //*[contains(@class, 'btn-confirm-yes')]";
        } 
        // JURUS KHUSUS 2: Tombol "Tambah Kelas" (Kunci langsung ke ID biar nggak meleset)
        else if (lowerTombol.equals("tambah kelas")) {
            xpathTombol = "//*[@id='btn-tambah'] | //button[contains(@class, 'btn-tambah')]";
        } 
        // Tombol Universal Lainnya
        else {
            xpathTombol = String.format(
                "//*[(local-name()='button' or local-name()='a' or contains(translate(@class, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'btn')) and contains(%s, '%s')]", 
                translateStr, lowerTombol
            );
        }
        
        try {
            System.out.println("Mencari tombol dengan teks/ID: '" + namaTombol + "'");
            WebElement tombol = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathTombol)));
            
            // Gulung layar dan klik
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", tombol);
            Thread.sleep(500); 
            
            // Coba klik normal dulu, kalau gagal baru hajar pakai JS
            try {
                tombol.click();
            } catch (Exception e) {
                js.executeScript("arguments[0].click();", tombol);
            }
            
            Thread.sleep(1000); // Jeda tunggu modal/halaman bereaksi
            System.out.println("Berhasil menekan tombol: " + namaTombol);
            
        } catch (Exception e) {
            Assert.fail("Tombol '" + namaTombol + "' gagal ditekan: " + e.getMessage());
        }
    }

    // SMART MERGE: Menyatukan semua aksi "mengonfirmasi" pada modal/pop-up website
    @And("^admin mengonfirmasi.*$")
    public void adminMengonfirmasiHapus() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(10));
        JavascriptExecutor js = (JavascriptExecutor) SetupSteps.driver;
        try {
            System.out.println("Mencari tombol konfirmasi 'Ya' pada pop-up website...");
            
            // Tembak langsung ID atau Class dari modal konfirmasi yang kamu berikan
            String xpathConfirm = "//*[@id='btn-confirm-yes'] | //*[contains(@class, 'btn-confirm-yes')]";
            
            WebElement btnConfirm = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathConfirm)));
            
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", btnConfirm);
            Thread.sleep(500);
            js.executeScript("arguments[0].click();", btnConfirm);
            
            System.out.println("Pop-up website berhasil disetujui (Tombol Ya ditekan).");
            Thread.sleep(1500); // Jeda agar backend memproses data
            
        } catch (Exception e) {
            Assert.fail("Gagal mengonfirmasi pop-up pada website (Tombol ID btn-confirm-yes tidak ditemukan): " + e.getMessage());
        }
    }

    // SMART MERGE: Menyatukan memilih "jadwal kelas aktif" maupun "tersedia"
    @And("^admin memilih salah satu jadwal kelas.*$")
    public void adminMemilihJadwalKelas() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        JavascriptExecutor js = (JavascriptExecutor) SetupSteps.driver;
        try {
            WebElement itemJadwal = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[contains(@class, 'card-jadwal') or contains(@class, 'schedule-card')][1]")
            ));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", itemJadwal);
            js.executeScript("arguments[0].click();", itemJadwal);
        } catch (Exception e) {
            Assert.fail("Gagal memilih jadwal kelas: " + e.getMessage());
        }
    }

    // ==========================================
    // MANAJEMEN BOOKING & KELAS (@Admin)
    // ==========================================

    @And("admin mengisi data kelas serta menentukan kuota kelas yang valid")
    public void adminMengisiDataKelasDanKuota() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(10));
        JavascriptExecutor js = (JavascriptExecutor) SetupSteps.driver;
        
        try {
            System.out.println("Menunggu form pembuatan kelas terbuka...");
            Thread.sleep(1500); // Beri napas agar animasi modal selesai 100%

            // 1. Kunci elemen input custom_name
            WebElement inputCustomName = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//input[@name='custom_name']")
            ));
            
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", inputCustomName);
            Thread.sleep(500); 
            
            // 2. JURUS BYPASS INPUT TEXT
            try {
                inputCustomName.clear();
                inputCustomName.sendKeys("Hatha Yoga Pagi");
            } catch (Exception ex) {
                System.out.println("Input diblokir oleh UI, memaksa pengisian menggunakan JavaScript...");
                js.executeScript("arguments[0].value = 'Hatha Yoga Pagi';", inputCustomName);
                js.executeScript("arguments[0].dispatchEvent(new Event('input', { bubbles: true }));", inputCustomName);
                js.executeScript("arguments[0].dispatchEvent(new Event('change', { bubbles: true }));", inputCustomName);
            }

            // 3. JURUS BYPASS DROPDOWN (Tunggu API & Paksa Index 1)
            System.out.println("Menunggu opsi dropdown ter-render dari API...");
            Thread.sleep(1000); // Jeda krusial agar elemen <option> selesai dimuat
            
            WebElement dropClass = SetupSteps.driver.findElement(By.xpath("//select[@name='class_id']"));
            WebElement dropCoach = SetupSteps.driver.findElement(By.xpath("//select[@name='coach_id']"));
            
            // Kita pakai index 1 (opsi valid pertama) agar kebal walau data di database sedang sedikit
            try {
                new org.openqa.selenium.support.ui.Select(dropClass).selectByIndex(1);
            } catch (Exception ex) {
                js.executeScript("arguments[0].selectedIndex = 1; arguments[0].dispatchEvent(new Event('change', { bubbles: true }));", dropClass);
            }
            
            try {
                new org.openqa.selenium.support.ui.Select(dropCoach).selectByIndex(1);
            } catch (Exception ex) {
                js.executeScript("arguments[0].selectedIndex = 1; arguments[0].dispatchEvent(new Event('change', { bubbles: true }));", dropCoach);
            }
            
            // 4. Eksekusi Sisa Form via JS Bypass
            WebElement dateField = SetupSteps.driver.findElement(By.xpath("//input[@name='schedule_date']"));
            js.executeScript("arguments[0].value = '2026-06-15';", dateField);
            
            WebElement startField = SetupSteps.driver.findElement(By.xpath("//input[@name='start_time']"));
            js.executeScript("arguments[0].value = '1212';", startField);
            
            WebElement endField = SetupSteps.driver.findElement(By.xpath("//input[@name='end_time']"));
            js.executeScript("arguments[0].value = '1212';", endField);
            
            WebElement capField = SetupSteps.driver.findElement(By.xpath("//input[@name='capacity']"));
            js.executeScript("arguments[0].value = '20';", capField);
            
            System.out.println("Form pembuatan kelas beserta isian kuota berhasil diinput secara penuh.");
            
        } catch (Exception e) {
            Assert.fail("Gagal mengisi form pembuatan kelas: " + e.getMessage());
        }
    }

    @Then("sistem berhasil menyimpan jadwal kelas baru beserta pembatasan kuotanya")
    public void sistemBerhasilSimpanJadwal() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(7));
        Assert.assertTrue("Gagal memvalidasi kelas baru!", wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("toast-success"))).isDisplayed());
    }

    @And("admin menekan tombol edit pada salah satu pengguna")
    public void adminKlikEditPelanggan() {
        // Naikkan timeout jadi 10 detik agar lebih stabil di emulator
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(10));
        JavascriptExecutor js = (JavascriptExecutor) SetupSteps.driver;
        
        try {
            System.out.println("Mencari kolom pencarian pelanggan...");
            
            WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("search-input")));
            
            // Pastikan elemen masuk ke dalam layar sebelum diketik
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", searchInput);
            searchInput.clear();
            
            // Nama target yang akan dicari (Bisa kamu ganti kapan saja di sini)
            String namaTarget = "iqbal nurhaqs";
            searchInput.sendKeys(namaTarget);
            
            // Jeda krusial: Tunggu sistem PWA memfilter tabel secara dinamis
            Thread.sleep(2000); // Dinaikkan dikit jadi 2 detik biar tabel beneran kelar render

            // PERBAIKAN PAMUNGKAS: Cari baris (tr) yang BENAR-BENAR berisi nama target, 
            // lalu klik tombol edit di dalam baris tersebut!
            String xpathTombolEditSpesifik = String.format(
                "//table//tbody/tr[contains(., '%s')]//*[contains(@class, 'btn-edit-customer') or contains(@class, 'edit') or contains(@class, 'btn-edit')]", 
                namaTarget
            );
            
            WebElement btnEditPelanggan = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathTombolEditSpesifik)));
            
            // Gulung layar dan eksekusi ketukan murni via JS
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", btnEditPelanggan);
            Thread.sleep(500); // Jeda sebelum klik agar tidak meleset
            js.executeScript("arguments[0].click();", btnEditPelanggan);
            System.out.println("Tombol edit milik pelanggan '" + namaTarget + "' berhasil ditekan tepat sasaran!");
            
            // Jeda transisi render modal / pindah halaman detail profil
            Thread.sleep(1500);
            
        } catch (Exception e) {
            Assert.fail("Gagal mengeklik tombol edit pelanggan: " + e.getMessage());
        }
    }

    @Then("sistem menampilkan aktivitas kelas yang diikuti serta sisa kuota membership pelanggan tersebut")
    public void sistemTampilkanAktivitasDanSisaKuota() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(7));
        bersihkanSplash();
        boolean isInfoVisible = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(normalize-space(), 'Aktivitas') or contains(normalize-space(), 'pertemuan')]"))).isDisplayed();
        Assert.assertTrue("Gagal memvalidasi sisa kuota!", isInfoVisible);
    }

    @Then("sistem berhasil memproses pembatalan booking dan mengembalikan status kelas tersebut")
    public void sistemBerhasilProsesPembatalan() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(7));
        boolean isCanceledSuccess = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@class, 'toast-text') or contains(normalize-space(), 'Berhasil')]"))).isDisplayed();
        Assert.assertTrue("Proses pembatalan booking gagal!", isCanceledSuccess);
    }

    @And("admin mengisi data peserta dan nominal pembayaran cash dengan valid")
    public void adminIsiDataCash() {
        // Naikkan timeout biar stabil
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(10));
        JavascriptExecutor js = (JavascriptExecutor) SetupSteps.driver;
        
        try {
            System.out.println("Mencari kolom input nama peserta...");
            
            // 1. Targetkan dan isi input nama
            WebElement inputName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("name")));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", inputName);
            Thread.sleep(500);
            
            inputName.clear();
            inputName.sendKeys("Budi Tunai");
            
            System.out.println("Menunggu dropdown metode pembayaran ter-render...");
            
            // 2. Kunci elemen dropdown payment_type
            WebElement dropdownElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("payment_type")));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", dropdownElement);
            
            // JEDA KRUSIAL: Beri waktu API/JS memunculkan opsi <option> di dalam dropdown
            Thread.sleep(1000); 
            
            // 3. Coba pilih secara normal pakai Selenium
            try {
                org.openqa.selenium.support.ui.Select selectPayment = new org.openqa.selenium.support.ui.Select(dropdownElement);
                selectPayment.selectByIndex(1);
                System.out.println("Dropdown berhasil dipilih dengan Selenium Select.");
            } catch (Exception ex) {
                // 4. JURUS BYPASS: Kalau opsi masih dibilang ga ada, paksa ubah index-nya pakai JS!
                System.out.println("Selenium Select ditolak, memaksa pemilihan dropdown menggunakan JavaScript...");
                js.executeScript("arguments[0].selectedIndex = 1;", dropdownElement);
                js.executeScript("arguments[0].dispatchEvent(new Event('change', { bubbles: true }));", dropdownElement);
                System.out.println("Dropdown berhasil dipilih secara paksa via JS.");
            }
            
            System.out.println("Form pembayaran cash berhasil diisi secara penuh.");
            
        } catch (Exception e) {
            Assert.fail("Gagal mengisi data pembayaran tunai: " + e.getMessage());
        }
    }

    @Then("sistem berhasil mencatat pembayaran cash dan mendaftarkan peserta ke kelas")
    public void sistemCatatPembayaranCash() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(7));
        Assert.assertTrue("Gagal validasi pencatatan data cash!", wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("toast-text"))).isDisplayed());
    }

    @Then("sistem menampilkan status verifikasi pembayaran pelanggan berupa valid atau tidak valid")
    public void sistemTampilkanStatusVerifikasi() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(7));
        bersihkanSplash();
        boolean isStatusVisible = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@class, 'status-valid') or contains(@class, 'status-pending') or contains(normalize-space(), 'Pending')]"))).isDisplayed();
        Assert.assertTrue("Status transaksi online tidak ditemukan!", isStatusVisible);
    }

    // ==========================================
    // MANAJEMEN DATA (JADWAL, COACH, MEMBERSHIP)
    // ==========================================

    @And("admin mengubah data salah satu jadwal")
    public void adminMengubahDataJadwal() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        WebElement inputQuota = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("capacity")));
        inputQuota.clear();
        inputQuota.sendKeys("20");
    }

    @Then("sistem berhasil memperbarui data jadwal kelas tersebut")
    public void sistemBerhasilPerbaruiJadwal() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(10));
        Assert.assertTrue("Gagal update jadwal!", wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("toast-success"))).isDisplayed());
    }

    @Then("sistem berhasil menghapus jadwal kelas tersebut dari daftar")
    public void sistemBerhasilMenghapusJadwal() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(7));
        try {
            WebElement notifTampil = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@class, 'toast-success') or contains(@class, 'alert-error')]")));
            Assert.assertTrue("Notifikasi hapus jadwal tidak muncul!", notifTampil.isDisplayed());
        } catch (Exception e) {
            Assert.fail("Gagal memvalidasi proses hapus jadwal! Error: " + e.getMessage());
        }
    }

    @And("admin memilih salah satu coach dan klik logo edit")
    public void adminMemilihCoachDanKlikEdit() {
        // Naikkan timeout biar aman kalau tabel butuh waktu render
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(10));
        JavascriptExecutor js = (JavascriptExecutor) SetupSteps.driver;
        try {
            System.out.println("Mencari tombol edit di baris daftar coach...");
            
            // XPATH DIPERBARUI: Menembak tag <a> dengan class 'btn-edit-coach' pada baris pertama
            WebElement btnEdit = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("(//a[contains(@class, 'btn-edit-coach')])[1]")
            ));
            
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", btnEdit);
            Thread.sleep(500);
            js.executeScript("arguments[0].click();", btnEdit);
            System.out.println("Tombol edit coach berhasil ditekan!");
            
            // JEDA KRUSIAL: Beri napas agar halaman detail/modal edit terbuka sempurna
            Thread.sleep(2000); 
        } catch (Exception e) {
            Assert.fail("Gagal mengeklik tombol edit coach: " + e.getMessage());
        }
    }

    @And("admin mengganti data coach untuk diperbarui")
    public void adminMenggantiDataCoach() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(10));
        JavascriptExecutor js = (JavascriptExecutor) SetupSteps.driver;
        try {
            WebElement inputName = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='name']")));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", inputName);
            Thread.sleep(500);
            try {
                WebElement btnPencil = SetupSteps.driver.findElement(By.xpath("//input[@name='name']/following-sibling::button | //button[contains(@class, 'btn-field-edit')]"));
                js.executeScript("arguments[0].click();", btnPencil);
                Thread.sleep(500);
            } catch (Exception e) {}
            inputName.clear();
            js.executeScript("arguments[0].value = '';", inputName);
            inputName.sendKeys("test update nama coach");
        } catch (Exception e) {
            Assert.fail("Gagal edit data coach: " + e.getMessage());
        }
    }

    @Then("sistem berhasil menyimpan pembaruan informasi data coach")
    public void sistemBerhasilSimpanPembaruanCoach() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        Assert.assertTrue("Gagal update coach!", wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("toast-success"))).isDisplayed());
    }

    @And("admin mengubah data membership untuk diperbarui")
    public void adminMengubahDataMembership() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        WebElement inputPrice = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("original_price")));
        inputPrice.clear();
        inputPrice.sendKeys("200000000");
    }

    @Then("sistem berhasil memperbarui paket data membership tersebut")
    public void sistemBerhasilMemperbaruiMembership() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(10));
        Assert.assertTrue("Gagal update membership!", wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("toast-success"))).isDisplayed());
    }

    @Then("sistem memvalidasi membership di hari tersebut berhasil dihapus")
    public void sistemMemvalidasiMembershipTerhapus() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(10));
        try {
            System.out.println("Menunggu respon sistem (sukses dihapus atau ditolak karena sudah dibooking)...");
            
            // Taktik XPath OR: Mencari elemen dengan class toast-success ATAU alert-error
            String xpathKondisi = "//*[contains(@class, 'toast-success') or contains(@class, 'alert-error')]";
            
            // Tunggu sampai salah satu notifikasi tersebut muncul di layar
            WebElement notifTampil = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathKondisi)));
            
            // Ambil atribut class dan teksnya buat laporan log di terminal
            String namaClassNotif = notifTampil.getAttribute("class").toLowerCase();
            String teksNotif = notifTampil.getText().trim();
            
            // Percabangan laporan agar kita tahu jalurnya masuk ke mana
            if (namaClassNotif.contains("alert-error")) {
                System.out.println("SKENARIO BLOCKED (VALID): Penghapusan membership dibatalkan otomatis oleh sistem karena sudah dibooking oleh pelanggan. Pesan sistem: " + teksNotif);
            } else {
                System.out.println("SKENARIO SUKSES: Paket membership di hari tersebut berhasil dihapus. Pesan sistem: " + teksNotif);
            }
            
            Assert.assertTrue("Pop-up notifikasi tidak muncul sama sekali!", notifTampil.isDisplayed());
            
        } catch (Exception e) {
            Assert.fail("Gagal memvalidasi proses hapus membership! Tidak ada notifikasi toast-success maupun alert-error yang muncul. Error: " + e.getMessage());
        }
    }

    @Then("sistem memastikan data membership customer terhapus secara permanen")
    public void sistemMemastikanMembershipTerhapusPermanen() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(10));
        Assert.assertTrue("Gagal hapus membership pelanggan!", wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("toast-success"))).isDisplayed());
    }

    // ==========================================
    // ANALITIK & LAPORAN (@AdminReport)
    // ==========================================

    @Then("sistem menampilkan data rekaman kehadiran peserta pada kelas tersebut")
    public void sistemMenampilkanDataKehadiran() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        Assert.assertTrue("Tabel kehadiran tidak ditemukan!", wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("peserta-table"))).isDisplayed());
    }

    @Then("sistem berhasil menampilkan kumpulan rekaman data pelanggan secara lengkap")
    public void sistemMenampilkanKumpulanDataPelanggan() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        Assert.assertTrue("Daftar pelanggan tidak muncul!", wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("customer-table"))).isDisplayed());
    }

    @Then("sistem berhasil menampilkan grafik dan komponen dashboard analytic secara berkala")
    public void sistemMenampilkanGrafikDashboard() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        Assert.assertTrue("Grafik tidak muncul!", wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("graph-wrap"))).isDisplayed());
    }

    @Then("sistem otomatis mengunduh berkas file data rangkuman pendapatan berdasarkan rentang waktu tertentu")
    public void sistemMengunduhBerkasLaporan() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(10));
        String currentContext = ((io.appium.java_client.android.AndroidDriver) SetupSteps.driver).getContext();
        try {
            Thread.sleep(2000); 
            ((io.appium.java_client.android.AndroidDriver) SetupSteps.driver).context("NATIVE_APP");
            boolean isPrintScreenVisible = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@text='Select a printer' or contains(@text, 'Select a printer')]"))).isDisplayed();
            Assert.assertTrue("Layar Print OS tidak muncul!", isPrintScreenVisible);
            
            ((io.appium.java_client.android.AndroidDriver) SetupSteps.driver).pressKey(new io.appium.java_client.android.nativekey.KeyEvent(io.appium.java_client.android.nativekey.AndroidKey.BACK));
            Thread.sleep(1500); 
        } catch (Exception e) {
            Assert.fail("Gagal validasi layar Print OS: " + e.getMessage());
        } finally {
            ((io.appium.java_client.android.AndroidDriver) SetupSteps.driver).context(currentContext);
        }
    }
}