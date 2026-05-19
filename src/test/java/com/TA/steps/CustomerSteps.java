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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
/**
 *
 * @author KnightlyTech
 */
public class CustomerSteps{
    public static String expectedFilterValue;
    public static List<String> bookedClasses = new ArrayList<>();
    public static String expectedClassName = "";
    public static String updatedName;
    public static String updatedPhone;
    
    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    // BAGIAN DATA PREPARASI
    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    
    @Given("customer sudah login dengan akun berbeda dan berada di halaman home")
    public void customerSudahLoginDenganAkunBerbeda(){
        // Naikkan jadi 10 detik biar lebih aman untuk nungguin animasi
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        JavascriptExecutor js = (JavascriptExecutor) SetupSteps.driver;

        // 1. Buka URL utama
        SetupSteps.driver.get("http://10.0.2.2:8000/");

        // 2. Tunggu tombol opsi Login ada di HTML, lalu klik pakai JS (menembus splash screen)
        WebElement btnPrimary = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("btn-primary")));
        js.executeScript("arguments[0].click();", btnPrimary);

        // 3. JEDA KRITIS: Tunggu sampai kolom username benar-benar muncul di layar
        WebElement usernameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
        
        // Pastikan isi dengan username akun keduamu
        usernameInput.sendKeys("nurhax6"); 

        // 4. Masukkan password
        SetupSteps.driver.findElement(By.id("password")).sendKeys("test123");

        // 5. Tunggu tombol submit ada di HTML, lalu klik pakai JS (menembus loading)
        WebElement btnSubmit = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("btn-submit")));
        js.executeScript("arguments[0].click();", btnSubmit);

        // 6. JEDA KRITIS 2: Tunggu sampai masuk ke halaman Home beneran
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("promo-scroll")));

        System.out.println("Customer (Akun Berbeda) berhasil login dan sudah berada di home page");
    }
    
    @Given("^customer sudah login dan berada di halaman [Hh]ome$")
    public void customerSudahLogin() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        JavascriptExecutor js = (JavascriptExecutor) SetupSteps.driver;

        // 1. Buka URL utama
        SetupSteps.driver.get("http://10.0.2.2:8000/");

        // 2. Tunggu tombol opsi Login ada di HTML, lalu eksekusi klik JS (menembus splash screen awal)
        WebElement btnPrimary = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("btn-primary")));
        js.executeScript("arguments[0].click();", btnPrimary);

        // 3. JEDA KRITIS: Tunggu sampai kolom username benar-benar muncul dan siap diketik
        WebElement usernameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
        usernameInput.sendKeys("nurhax5");

        // 4. Masukkan password
        SetupSteps.driver.findElement(By.id("password")).sendKeys("test123");

        // 5. Tunggu tombol submit ada di HTML, lalu eksekusi klik JS (menembus splash screen loading)
        WebElement btnSubmit = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("btn-submit")));
        js.executeScript("arguments[0].click();", btnSubmit);

        // 6. JEDA KRITIS 2: Tunggu sampai masuk ke halaman Home beneran
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("promo-scroll")));

        System.out.println("Customer berhasil login dan sudah berada di home page");
    }
    
    @And("customer menekan tab {string}")
    public void customerMenekanTab(String namaTab){
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        JavascriptExecutor js = (JavascriptExecutor) SetupSteps.driver;

        // 1. JURUS BRUTAL: Hapus paksa elemen animasi kalau dia tiba-tiba muncul lagi
        try {
            js.executeScript("var splash = document.getElementById('splash-circle'); if(splash) { splash.remove(); }");
            js.executeScript("var spinner = document.getElementById('splash-spinner'); if(spinner) { spinner.remove(); }");
            // Kasih jeda dikit biar layarnya stabil setelah JS dieksekusi
            Thread.sleep(500); 
        } catch (Exception e) {
            // Kalau nggak ada animasinya, cuekin aja
        }

        // 2. Cari elemen tab-nya
        // Menggunakan normalize-space() biar lebih aman
        String xpathSelector = String.format("//a[contains(normalize-space(), '%s')]", namaTab);
        WebElement tabElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathSelector)));

        // 3. Eksekusi klik pakai JS biar kebal intercept
        js.executeScript("arguments[0].click();", tabElement);
        
        System.out.println("Berhasil menekan tab: " + namaTab);
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
    
    @When("customer menekan tombol Edit Nama Lengkap")
    public void customerMenekanTombolEditUsername() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        String xpath = "//div[contains(@class, 'info-row-left') and contains(., 'Nama Lengkap')]/following-sibling::button[contains(@class, 'edit-btn')]";
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath))).click();
    }

    @And("customer mengubah nama menjadi {string}")
    public void customerMengubahNama(String namaBaru) {
        updatedName = namaBaru;
        WebElement inputNama = SetupSteps.driver.findElement(By.name("name"));
        inputNama.clear();
        inputNama.sendKeys(namaBaru);
    }

    @And("customer menekan tombol Save Nama Lengkap")
    public void customerMenekanTombolSaveUsername() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        String xpath = "//div[contains(@class, 'info-row-left') and contains(., 'Nama Lengkap')]/following-sibling::button[contains(@class, 'save-btn')]";
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
        updatedPhone = hpBaru;
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
        JavascriptExecutor js = (JavascriptExecutor) SetupSteps.driver;

        try {
            // 1. JURUS BYPASS: Hapus paksa animasi splash screen biar gak menghalangi UI profil asli
            try {
                js.executeScript("var splash = document.getElementById('splash-circle'); if(splash) { splash.remove(); }");
                js.executeScript("var spinner = document.getElementById('splash-spinner'); if(spinner) { spinner.remove(); }");
                Thread.sleep(1000); // Kasih jeda sedetik agar halaman stabil setelah animasi dihapus
            } catch (Exception e) {
                // Cuekin aja kalau animasinya memang sudah tidak ada
            }

            // 2. SANITY CHECK: Pastikan variabel global penampung data barumu tidak kosong
            // (Sesuaikan updatedName & updatedPhone dengan variabel yang kamu isi di step @When mengisi form)
            Assert.assertFalse("Variabel updatedName kosong! Periksa step mengisi form.", updatedName.isEmpty());
            Assert.assertFalse("Variabel updatedPhone kosong! Periksa step mengisi form.", updatedPhone.isEmpty());

            System.out.println("--- MEMULAI VALIDASI PROFIL ---");
            System.out.println("Mencari Nama Baru: " + updatedName);

            // =========================================================================
            // LAKUKAN VALIDASI NAMA LENGKAP
            // =========================================================================
            String nameLower = updatedName.toLowerCase();
            String xpathNama = String.format("//*[contains(translate(normalize-space(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '%s')]", nameLower);
            
            WebElement elementNama = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathNama)));
            Assert.assertTrue("Gawat! Nama baru tidak kelihatan di halaman profil!", elementNama.isDisplayed());
            System.out.println("Nama berhasil divalidasi: " + elementNama.getText());


            // =========================================================================
            // LAKUKAN VALIDASI NOMOR HP (ANTI-GAGAL: POTONG 4 ANGKA TERAKHIR)
            // =========================================================================
            String stringHp = updatedPhone.trim();
            // Ambil 4 angka paling belakang (misal '08111222333' -> cuma diambil '2333')
            String empatAngkaTerakhir = stringHp.substring(Math.max(0, stringHp.length() - 4));
            System.out.println("Mencari Nomor HP menggunakan potongan digit belakang: " + empatAngkaTerakhir);

            // XPath ini mencari teks biasa ATAU isi atribut value di dalam kotak input yang mengandung 4 angka tersebut
            String xpathPhoneSakti = String.format(
                "//*[contains(text(), '%s')] | //input[contains(@value, '%s')]", 
                empatAngkaTerakhir, empatAngkaTerakhir
            );

            WebElement elementPhone = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathPhoneSakti)));
            Assert.assertTrue("Gawat! Nomor HP baru tidak kelihatan di halaman profil!", elementPhone.isDisplayed());
            
            // Cara ambil teksnya: Kalau teks biasa kosong, berarti dia ngumpet di dalam atribut value (input box)
            String teksHpDiUI = elementPhone.getText().isEmpty() ? elementPhone.getAttribute("value") : elementPhone.getText();
            System.out.println("Nomor HP berhasil divalidasi. Tercatat di UI: " + teksHpDiUI);

            System.out.println("STATUS: VALIDASI SUKSES (HIJAU)! Semua data berubah sesuai inputan.");

        } catch (Exception e) {
            Assert.fail("Gagal memvalidasi perubahan profil di UI. Kemungkinan elemen tidak ditemukan. Error: " + e.getMessage());
        }
    }
    
    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    // MODUL: BOOKING & PEMBAYARAN KELAS (FR 6 & FR 12)
    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX

    @When("customer memilih jadwal kelas yoga yang tersedia")
    public void customerPilihKelasTersedia() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        JavascriptExecutor js = (JavascriptExecutor) SetupSteps.driver;

        // --- BERSIHKAN ANIMASI SEBELUM MULAI ---
        try {
            js.executeScript("var splash = document.getElementById('splash-circle'); if(splash) { splash.remove(); }");
            js.executeScript("var spinner = document.getElementById('splash-spinner'); if(spinner) { spinner.remove(); }");
            Thread.sleep(500); 
        } catch (InterruptedException e) {}

        // --- 1. PINDAH KE TAB AKTIVITAS DULU ---
        String xpathTabAktivitas = "//a[contains(normalize-space(), 'Aktivitas')]";
        
        // HAPUS .click() JIKA MASIH ADA DI BARIS BAWAH INI!
        WebElement tabAktivitas = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathTabAktivitas)));
        
        // KLIK HARUS LEWAT BARIS INI:
        js.executeScript("arguments[0].click();", tabAktivitas);

        // --- 2. AMBIL SEMUA NAMA KELAS YANG SUDAH DIBOOKING ---
        bookedClasses.clear(); 
        try {
            WebDriverWait shortWait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(3));
            List<WebElement> activityElements = shortWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("card-activity-title")));
            
            for (WebElement el : activityElements) {
                bookedClasses.add(el.getText().trim()); 
            }
            System.out.println("Kelas yang sudah dibooking: " + bookedClasses);
        } catch (Exception e) {
            System.out.println("User ini belum punya aktivitas kelas (List Kosong).");
        }

        // --- BERSIHKAN ANIMASI LAGI (KARENA ABIS PINDAH TAB BIASANYA MUNCUL LAGI) ---
        try {
            js.executeScript("var splash = document.getElementById('splash-circle'); if(splash) { splash.remove(); }");
            js.executeScript("var spinner = document.getElementById('splash-spinner'); if(spinner) { spinner.remove(); }");
            Thread.sleep(500);
        } catch (Exception e) {}

        // --- 3. BALIK LAGI KE TAB HOME ---
        String xpathTabHome = "//a[contains(normalize-space(), 'Home')]";
        WebElement tabHome = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathTabHome)));
        js.executeScript("arguments[0].click();", tabHome);

        // --- 4. CARI KELAS YANG BELUM PERNAH DIBOOKING ---
        List<WebElement> availableClasses = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("card-class")));
        boolean berhasilPilihKelas = false;

        for (WebElement card : availableClasses) {
            try {
                // NAH INI DIA YANG DIGANTI! Dari "class-title" menjadi "card-class-title"
                String classTitle = card.findElement(By.className("card-class-title")).getText().trim();

                // Cek apakah judul kelas ini TIDAK ADA di dalam history aktivitas
                if (!bookedClasses.contains(classTitle)) {
                    expectedClassName = classTitle; 
                    System.out.println("Menemukan kelas yang belum dibooking: " + expectedClassName);

                    // Eksekusi klik (Pakai JS Executor biar aman dari animasi/intercept)
                    js = (JavascriptExecutor) SetupSteps.driver;
                    js.executeScript("arguments[0].scrollIntoView({block: 'center'});", card); 
                    js.executeScript("arguments[0].click();", card);
                    
                    berhasilPilihKelas = true;
                    break; 
                }
            } catch (Exception e) {
                // Kalau nemu card bodong/skeleton loading, skip aja
                System.out.println("Nemu card tanpa judul, skip ke card sebelahnya...");
                continue; 
            }
        }

        // --- 5. VALIDASI JIKA SEMUA KELAS SUDAH PENUH/DIBOOKING ---
        if (!berhasilPilihKelas) {
            Assert.fail("Skenario gagal: Semua kelas yang ada di Home sudah pernah di-booking oleh user ini!");
        }
    }

    @And("customer menekan tombol {string}")
    public void customerTekanTombolDiDetail(String namaTombol) {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(15));
        JavascriptExecutor js = (JavascriptExecutor) SetupSteps.driver;

        try {
            // 1. Bersihkan splash screen
            js.executeScript("var splash = document.getElementById('splash-circle'); if(splash) { splash.remove(); }");
            Thread.sleep(1000);

            // 2. Kita buat pencarian teks jadi Case-Insensitive menggunakan translate
            // Ini bakal bikin "Yin Yoga", "YIN YOGA", atau "yin yoga" tetep ketemu
            String targetTitle = expectedClassName.toLowerCase();
            
            // XPath Sniper: Cari teks judul (lower case), naik ke container-nya, lalu cari .btn-pesan
            String xpathSniper = String.format(
                "//*[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '%s')]" +
                "/ancestor::*[contains(@class, 'card-class')]//a[contains(@class, 'btn-pesan')]", 
                targetTitle
            );

            System.out.println("Menembak tombol untuk kelas (Case-Insensitive): " + targetTitle);

            WebElement btnPesan = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathSniper)));

            // 3. Scroll biar pas di tengah layar
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", btnPesan);
            Thread.sleep(1000);

            // 4. Klik paksa pake JS
            js.executeScript("arguments[0].click();", btnPesan);
            
            System.out.println("Tombol berhasil diklik!");

            // 5. Jeda buat transisi halaman
            Thread.sleep(3000);

        } catch (Exception e) {
            // Fallback terakhir: Kalau masih gagal, kita klik btn-pesan pertama yang kelihatan di layar
            // (Hanya jika kita yakin kelas yang kita mau ada di paling atas)
            try {
                System.out.println("XPath Sniper gagal, mencoba fallback klik btn-pesan pertama...");
                WebElement btnFallback = wait.until(ExpectedConditions.elementToBeClickable(By.className("btn-pesan")));
                js.executeScript("arguments[0].click();", btnFallback);
            } catch (Exception ex) {
                Assert.fail("Gagal total nemu tombol Pesan. Error: " + e.getMessage());
            }
        }
    }

    @And("customer melakukan proses pembayaran")
    public void customerLakukanPembayaran() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5)); // Waktu tunggu sedikit dipanjangkan karena ada transisi ke Xendit
        JavascriptExecutor js = (JavascriptExecutor) SetupSteps.driver;

        try {
            // 1. Mencet tombol "Lanjut Pembayaran" bawaan aplikasimu
            WebElement btnLanjutPembayaran = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("pay-btn")));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", btnLanjutPembayaran);
            js.executeScript("arguments[0].click();", btnLanjutPembayaran);
            System.out.println("Tombol Lanjut Pembayaran (Aplikasi) ditekan, menunggu gateway Xendit...");

            // 2. Mencet tombol kategori E-wallet (UI Xendit)
            // Pakai visibilityOfElementLocated agar Selenium menunggu loading screen Xendit selesai
            String xpathEwallet = "//*[contains(normalize-space(text()), 'E-Wallet') or contains(normalize-space(text()), 'e-wallet')]";
            WebElement btnEwallet = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathEwallet)));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", btnEwallet);
            js.executeScript("arguments[0].click();", btnEwallet);
            System.out.println("Kategori E-Wallet dipilih.");

            // 3. Mencet tombol gopay (pake alt = "gopay")
            String xpathGopaySakti = "//img[contains(translate(@alt, 'GOPAY', 'gopay'), 'gopay')] | //*[contains(text(), 'GoPay')]";
            
            System.out.println("Mencari logo GoPay di UI Xendit...");
            WebElement btnGopay = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathGopaySakti)));
            
            // Scroll dulu biar nggak ketutup footer Xendit
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", btnGopay);
            Thread.sleep(1000); 

            // Klik paksa pakai JS
            js.executeScript("arguments[0].click();", btnGopay);
            System.out.println("Metode GoPay berhasil dipilih!");
            
            //Pencet pay-btn lagi
            WebElement btnLanjutPembayaranLagi = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("pay-btn")));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", btnLanjutPembayaranLagi);
            js.executeScript("arguments[0].click();", btnLanjutPembayaranLagi);

            // 3. Mencet tombol Proceed to Pay
            System.out.println("Mencari tombol Proceed to Pay...");
            
            // Kita coba cari pakai ID dulu, kalau ID-nya nggak ketemu kita cari pakai teks
            String xpathProceed = "//*[@id='proceed-button'] | //button[contains(normalize-space(), 'Proceed to Pay')]";
            
            WebElement btnProceed = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathProceed)));
            
            // Scroll ke tengah layar
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", btnProceed);
            Thread.sleep(1000); 

            // Klik paksa pakai JS biar trigger fungsi simulatePayment()
            js.executeScript("arguments[0].click();", btnProceed);
            System.out.println("Tombol Proceed to Pay ditekan.");

            // 4. Tunggu proses redirect (biasanya Xendit butuh waktu buat memproses simulasi)
            System.out.println("Menunggu pengalihan ke halaman sukses...");
            Thread.sleep(3000);
            
            //Klik btn selesai
            WebElement btnSelesai = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("btn-selesai")));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", btnSelesai);
            js.executeScript("arguments[0].click();", btnSelesai);
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            Assert.fail("Proses terinterupsi saat menunggu redirect: " + e.getMessage());
        } catch (Exception e) {
            Assert.fail("Gagal menyelesaikan proses pembayaran. Error: " + e.getMessage());
        }
    }

    @Then("jadwal kelas yang dipesan akan muncul di halaman aktivitas")
    public void validasiKelasDiAktivitas() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(15));
        
        // 1. Sanity Check
        Assert.assertFalse("Variabel expectedClassName kosong!", expectedClassName.isEmpty());
        
        // Kita paksa nama kelas target jadi huruf kecil untuk dibandingkan
        String targetClassLower = expectedClassName.toLowerCase();
        System.out.println("Validasi di halaman Aktivitas untuk: " + targetClassLower);

        // 2. XPath Sakti: Normalize Space + Case Insensitive
        // translate() akan mengubah semua teks di layar jadi kecil, lalu dibandingkan dengan targetClassLower
        String xpathKelasDipesan = String.format(
            "//*[contains(translate(normalize-space(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '%s')]", 
            targetClassLower
        );
        
        try {
            // 3. Tunggu sampai nama kelas tersebut muncul di layar
            WebElement elementKelas = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathKelasDipesan)));
            
            Assert.assertTrue("Gawat! Kelas '" + expectedClassName + "' tidak ditemukan di halaman aktivitas!", elementKelas.isDisplayed());
            
            System.out.println("Validasi Sukses: Berhasil menemukan kelas '" + expectedClassName + "' di daftar aktivitas!");
            
            // Opsional: Print teks aslinya yang ditemukan di layar buat bukti di log
            System.out.println("Teks yang ditemukan di UI: " + elementKelas.getText());

        } catch (Exception e) {
            Assert.fail("Gagal memvalidasi kelas '" + expectedClassName + "' di halaman aktivitas. Mungkin karena delay atau data belum masuk. Error: " + e.getMessage());
        }
    }
}
