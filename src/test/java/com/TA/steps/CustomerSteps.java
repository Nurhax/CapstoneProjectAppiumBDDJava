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
    public static String membershipClassName = "";
    public static String expectedPackageName = "";
    
    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    // BAGIAN DATA PREPARASI
    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    
    @Given("customer sudah login dengan akun berbeda dan berada di halaman home")
    public void customerSudahLoginDenganAkunBerbeda(){
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        JavascriptExecutor js = (JavascriptExecutor) SetupSteps.driver;

        SetupSteps.driver.get("http://10.0.2.2:8000/");

        WebElement btnPrimary = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("btn-primary")));
        js.executeScript("arguments[0].click();", btnPrimary);

        WebElement usernameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
        usernameInput.sendKeys("nurhax5"); 

        SetupSteps.driver.findElement(By.id("password")).sendKeys("test123");

        WebElement btnSubmit = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("btn-submit")));
        js.executeScript("arguments[0].click();", btnSubmit);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("promo-scroll")));
        System.out.println("Customer (Akun Berbeda) berhasil login dan sudah berada di home page");
    }
    
    @Given("^customer sudah login dan berada di halaman [Hh]ome$")
    public void customerSudahLogin() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        JavascriptExecutor js = (JavascriptExecutor) SetupSteps.driver;

        SetupSteps.driver.get("http://10.0.2.2:8000/");

        WebElement btnPrimary = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("btn-primary")));
        js.executeScript("arguments[0].click();", btnPrimary);

        WebElement usernameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
        usernameInput.sendKeys("nurhax1");

        SetupSteps.driver.findElement(By.id("password")).sendKeys("test123");

        WebElement btnSubmit = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("btn-submit")));
        js.executeScript("arguments[0].click();", btnSubmit);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("promo-scroll")));
        System.out.println("Customer berhasil login dan sudah berada di home page");
    }
    
    @And("customer menekan tab {string}")
    public void customerMenekanTab(String namaTab){
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        JavascriptExecutor js = (JavascriptExecutor) SetupSteps.driver;

        try {
            js.executeScript("var splash = document.getElementById('splash-circle'); if(splash) { splash.remove(); }");
            js.executeScript("var spinner = document.getElementById('splash-spinner'); if(spinner) { spinner.remove(); }");
            Thread.sleep(500); 
        } catch (Exception e) {}

        String xpathSelector = String.format("//a[contains(normalize-space(), '%s')]", namaTab);
        WebElement tabElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathSelector)));

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
        expectedFilterValue = nilaiFilter; 
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        
        String idTombol = "";
        if (namaTombol.equalsIgnoreCase("Kelas")) {
            idTombol = "btn-kelas";
        } else if (namaTombol.equalsIgnoreCase("Waktu")) {
            idTombol = "btn-waktu"; 
        } else if (namaTombol.equalsIgnoreCase("Coach")) {
            idTombol = "btn-coach"; 
        }
        
        wait.until(ExpectedConditions.elementToBeClickable(By.id(idTombol))).click();

        String opsiXpath = String.format("//label[contains(normalize-space(), '%s')]", nilaiFilter);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(opsiXpath))).click();
    }
    
    @Then("sistem hanya menampilkan daftar kelas yang sesuai dengan filter yang diterapkan")
    public void sistemMenampilkanFilterMembership() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        String xpath = String.format("//*[contains(normalize-space(), '%s')]", expectedFilterValue);
        boolean isFilteredMembershipMuncul = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath))).isDisplayed();
        Assert.assertTrue("Hasil filter membership tidak muncul! Tidak menemukan teks: " + expectedFilterValue, isFilteredMembershipMuncul);
    }
    
    @When("customer melihat salah satu kelas yang tersedia")
    public void customerMelihatSalahSatuKelas() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
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
        // Kita naikkan WebDriverWait menjadi 15 detik khusus untuk validasi ini
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(15));
        JavascriptExecutor js = (JavascriptExecutor) SetupSteps.driver;

        try {
            // 1. JURUS BYPASS ANIMASI: Hancurkan splash screen pengganggu koordinat DOM
            try {
                js.executeScript("var splash = document.getElementById('splash-circle'); if(splash) { splash.remove(); }");
                js.executeScript("var spinner = document.getElementById('splash-spinner'); if(spinner) { spinner.remove(); }");
                Thread.sleep(1500); // Beri jeda 1.5 detik agar PWA selesai melakukan state-update data
            } catch (Exception e) {}

            // 2. SANITY CHECK: Pastikan data inputan tidak kosong
            Assert.assertFalse("Variabel updatedName kosong!", updatedName.isEmpty());
            Assert.assertFalse("Variabel updatedPhone kosong!", updatedPhone.isEmpty());

            System.out.println("--- PROSES VALIDASI REAL-TIME PROFIL ---");

            // =========================================================================
            // LAKUKAN VALIDASI NAMA LENGKAP (Gunakan presenceOfElementLocated agar lebih toleran)
            // =========================================================================
            String nameLower = updatedName.toLowerCase();
            String xpathNama = String.format("//*[contains(translate(normalize-space(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '%s')]", nameLower);
            
            WebElement elementNama = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathNama)));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", elementNama);
            Assert.assertTrue("Nama baru tidak kelihatan di halaman profil!", elementNama.isDisplayed());
            System.out.println("Nama berhasil divalidasi: " + elementNama.getText());

            // =========================================================================
            // LAKUKAN VALIDASI NOMOR HP (UPGRADE JURUS PALING AMAN)
            // =========================================================================
            String stringHp = updatedPhone.trim();
            String empatAngkaTerakhir = stringHp.substring(Math.max(0, stringHp.length() - 4));
            System.out.println("Mencari Nomor HP menggunakan potongan digit belakang: " + empatAngkaTerakhir);

            // Kita perluas XPath-nya menggunakan normalize-space() pada text() untuk mengantisipasi whitespace HTML formatting
            String xpathPhoneSakti = String.format(
                "//*[contains(normalize-space(), '%s')] | //input[contains(@value, '%s')]", 
                empatAngkaTerakhir, empatAngkaTerakhir
            );

            WebElement elementPhone;
            try {
                // Percobaan pertama mencari nomor HP
                elementPhone = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathPhoneSakti)));
            } catch (TimeoutException te) {
                // JIKA TIMEOUT (TETAP TIDAK KETEMU): PWA kemungkinan stuck di state lama. 
                // Kita trigger paksa refresh halaman via driver untuk mereload data terbaru dari database
                System.out.println("Nomor HP belum ter-render sempurna. Mencoba merefresh halaman profil...");
                SetupSteps.driver.navigate().refresh();
                Thread.sleep(2000); // Tunggu reload selesai
                
                // Hancurkan splash screen lagi pasca-refresh
                try { js.executeScript("var splash = document.getElementById('splash-circle'); if(splash) { splash.remove(); }"); } catch (Exception e) {}
                
                // Coba cari ulang setelah halaman bersih ter-refresh
                elementPhone = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathPhoneSakti)));
            }
            
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", elementPhone);
            Assert.assertTrue("Nomor HP baru tidak kelihatan di halaman profil!", elementPhone.isDisplayed());
            
            String teksHpDiUI = elementPhone.getText().isEmpty() ? elementPhone.getAttribute("value") : elementPhone.getText();
            System.out.println("Nomor HP berhasil divalidasi. Tercatat di UI: " + teksHpDiUI);

            System.out.println("STATUS: VALIDASI SUKSES (HIJAU)!");

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

        try {
            js.executeScript("var splash = document.getElementById('splash-circle'); if(splash) { splash.remove(); }");
            js.executeScript("var spinner = document.getElementById('splash-spinner'); if(spinner) { spinner.remove(); }");
            Thread.sleep(500); 
        } catch (InterruptedException e) {}

        String xpathTabAktivitas = "//a[contains(normalize-space(), 'Aktivitas')]";
        WebElement tabAktivitas = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathTabAktivitas)));
        js.executeScript("arguments[0].click();", tabAktivitas);

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

        try {
            js.executeScript("var splash = document.getElementById('splash-circle'); if(splash) { splash.remove(); }");
            js.executeScript("var spinner = document.getElementById('splash-spinner'); if(spinner) { spinner.remove(); }");
            Thread.sleep(500);
        } catch (Exception e) {}

        String xpathTabHome = "//a[contains(normalize-space(), 'Home')]";
        WebElement tabHome = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathTabHome)));
        js.executeScript("arguments[0].click();", tabHome);

        List<WebElement> availableClasses = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("card-class")));
        boolean berhasilPilihKelas = false;

        for (WebElement card : availableClasses) {
            try {
                String classTitle = card.findElement(By.className("card-class-title")).getText().trim();

                if (!bookedClasses.contains(classTitle)) {
                    expectedClassName = classTitle; 
                    System.out.println("Menemukan kelas yang belum dibooking: " + expectedClassName);

                    js = (JavascriptExecutor) SetupSteps.driver;
                    js.executeScript("arguments[0].scrollIntoView({block: 'center'});", card); 
                    js.executeScript("arguments[0].click();", card);
                    
                    berhasilPilihKelas = true;
                    break; 
                }
            } catch (Exception e) {
                System.out.println("Nemu card tanpa judul, skip ke card sebelahnya...");
                continue; 
            }
        }

        if (!berhasilPilihKelas) {
            Assert.fail("Skenario gagal: Semua kelas yang ada di Home sudah pernah di-booking oleh user ini!");
        }
    }

    @And("customer menekan tombol {string}")
    public void customerTekanTombolDiDetail(String namaTombol) {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        JavascriptExecutor js = (JavascriptExecutor) SetupSteps.driver;

        try {
            js.executeScript("var splash = document.getElementById('splash-circle'); if(splash) { splash.remove(); }");
            Thread.sleep(1000);

            String targetTitle = expectedClassName.toLowerCase();
            String xpathSniper = String.format(
                "//*[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '%s')]" +
                "/ancestor::*[contains(@class, 'card-class')]//a[contains(@class, 'btn-pesan')]", 
                targetTitle
            );

            System.out.println("Menembak tombol untuk kelas (Case-Insensitive): " + targetTitle);

            WebElement btnPesan = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathSniper)));

            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", btnPesan);
            Thread.sleep(1000);

            js.executeScript("arguments[0].click();", btnPesan);
            System.out.println("Tombol berhasil diklik!");
            Thread.sleep(3000);

        } catch (Exception e) {
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
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5)); 
        JavascriptExecutor js = (JavascriptExecutor) SetupSteps.driver;

        try {
            WebElement btnLanjutPembayaran = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("pay-btn")));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", btnLanjutPembayaran);
            js.executeScript("arguments[0].click();", btnLanjutPembayaran);
            System.out.println("Tombol Lanjut Pembayaran (Aplikasi) ditekan, menunggu gateway Xendit...");

            String xpathEwallet = "//*[contains(normalize-space(text()), 'E-Wallet') or contains(normalize-space(text()), 'e-wallet')]";
            WebElement btnEwallet = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathEwallet)));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", btnEwallet);
            js.executeScript("arguments[0].click();", btnEwallet);
            System.out.println("Kategori E-Wallet dipilih.");

            String xpathGopaySakti = "//img[contains(translate(@alt, 'GOPAY', 'gopay'), 'gopay')] | //*[contains(text(), 'GoPay')]";
            System.out.println("Mencari logo GoPay di UI Xendit...");
            WebElement btnGopay = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathGopaySakti)));
            
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", btnGopay);
            Thread.sleep(1000); 

            js.executeScript("arguments[0].click();", btnGopay);
            System.out.println("Metode GoPay berhasil dipilih!");
            
            WebElement btnLanjutPembayaranLagi = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("pay-btn")));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", btnLanjutPembayaranLagi);
            js.executeScript("arguments[0].click();", btnLanjutPembayaranLagi);

            System.out.println("Mencari tombol Proceed to Pay...");
            String xpathProceed = "//*[@id='proceed-button'] | //button[contains(normalize-space(), 'Proceed to Pay')]";
            WebElement btnProceed = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathProceed)));
            
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", btnProceed);
            Thread.sleep(1000); 

            js.executeScript("arguments[0].click();", btnProceed);
            System.out.println("Tombol Proceed to Pay ditekan.");

            System.out.println("Menunggu pengalihan ke halaman sukses...");
            Thread.sleep(3000);
            
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
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        
        Assert.assertFalse("Variabel expectedClassName kosong!", expectedClassName.isEmpty());
        String targetClassLower = expectedClassName.toLowerCase();
        System.out.println("Validasi di halaman Aktivitas untuk: " + targetClassLower);

        String xpathKelasDipesan = String.format(
            "//*[contains(translate(normalize-space(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '%s')]", 
            targetClassLower
        );
        
        try {
            WebElement elementKelas = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathKelasDipesan)));
            Assert.assertTrue("Gawat! Kelas '" + expectedClassName + "' tidak ditemukan di halaman aktivitas!", elementKelas.isDisplayed());
            System.out.println("Validasi Sukses: Berhasil menemukan kelas '" + expectedClassName + "' di daftar aktivitas!");
        } catch (Exception e) {
            Assert.fail("Gagal memvalidasi kelas '" + expectedClassName + "' di halaman aktivitas. Error: " + e.getMessage());
        }
    }

    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    // BAGIAN BARU: SKENARIO KUOTA MEMBERSHIP (FR-08 & FR-09)
    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX

    @When("customer membeli membership untuk kelas {string} pada tab {string}")
    public void customerMembeliMembership(String namaKelasMembership, String namaTab) {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(10));
        JavascriptExecutor js = (JavascriptExecutor) SetupSteps.driver;

        membershipClassName = namaKelasMembership;
        System.out.println("Membeli membership untuk kelas: " + membershipClassName);

        try {
            // 1. Bypass animasi splash screen
            js.executeScript("var splash = document.getElementById('splash-circle'); if(splash) { splash.remove(); }");
            js.executeScript("var spinner = document.getElementById('splash-spinner'); if(spinner) { spinner.remove(); }");
            Thread.sleep(500);
            
            // 2. Masuk ke tab secara DINAMIS
            String xpathTabMembership = String.format("//a[contains(normalize-space(), '%s')]", namaTab);
            WebElement tabMembership = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathTabMembership)));
            js.executeScript("arguments[0].click();", tabMembership);
            Thread.sleep(1000);

            try { js.executeScript("var splash = document.getElementById('splash-circle'); if(splash) { splash.remove(); }"); } catch (Exception e) {}

            // 3. JURUS AMBIL NAMA PAKET OTOMATIS SEBELUM KLIK
            String targetMembershipLower = membershipClassName.toLowerCase();
            String xpathCard = String.format(
                "//*[contains(translate(normalize-space(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '%s')]/ancestor::*[contains(@class, 'card') or contains(@class, 'member')]",
                targetMembershipLower
            );
            WebElement containerCard = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathCard)));
            
            // Cari element judul paket di dalam card (misal class nya mengandung 'title')
            try {
                WebElement titleElement = containerCard.findElement(By.xpath(".//*[contains(@class, 'title') or contains(@class, 'name')]"));
                expectedPackageName = titleElement.getText().trim();
            } catch (Exception ex) {
                // Fallback kalau class title ga ketemu, pakai default teks skenario
                expectedPackageName = "Starter Pack"; 
            }
            System.out.println("BERHASIL MENGUNCI NAMA PAKET MEMBERSHIP: " + expectedPackageName);

            // 4. Klik Tombol Pesan Sekarang
            String xpathPesanSekarangSakti = String.format(
                "//*[contains(translate(normalize-space(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '%s')]" +
                "/ancestor::*[contains(@class, 'card') or contains(@class, 'member')]//*[contains(normalize-space(), 'Pesan')]", 
                targetMembershipLower
            );
            WebElement btnPesanSekarang = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathPesanSekarangSakti)));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", btnPesanSekarang);
            Thread.sleep(500);
            js.executeScript("arguments[0].click();", btnPesanSekarang);
            
            // =========================================================================
            // ALUR PROSES PEMBAYARAN GATEWAY XENDIT (GOPAY)
            // =========================================================================
            WebElement btnKonfirmasiBeli = wait.until(ExpectedConditions.elementToBeClickable(By.id("pay-btn")));
            js.executeScript("arguments[0].click();", btnKonfirmasiBeli);
            
            String xpathEwallet = "//*[contains(normalize-space(text()), 'E-Wallet') or contains(normalize-space(text()), 'e-wallet')]";
            WebElement btnEwallet = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathEwallet)));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", btnEwallet);
            js.executeScript("arguments[0].click();", btnEwallet);

            String xpathGopaySakti = "//img[contains(translate(@alt, 'GOPAY', 'gopay'), 'gopay')] | //*[contains(text(), 'GoPay')]";
            WebElement btnGopay = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathGopaySakti)));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", btnGopay);
            Thread.sleep(1000); 
            js.executeScript("arguments[0].click();", btnGopay);

            WebElement btnLanjutPembayaranLagi = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("pay-btn")));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", btnLanjutPembayaranLagi);
            js.executeScript("arguments[0].click();", btnLanjutPembayaranLagi);

            String xpathProceed = "//*[@id='proceed-button'] | //button[contains(normalize-space(), 'Proceed to Pay')]";
            WebElement btnProceed = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathProceed)));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", btnProceed);
            Thread.sleep(1000); 
            js.executeScript("arguments[0].click();", btnProceed);

            Thread.sleep(3000);
            WebElement btnSelesai = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("btn-selesai")));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", btnSelesai);
            js.executeScript("arguments[0].click();", btnSelesai);

        } catch (Exception e) {
            Assert.fail("Gagal pada proses pembelian membership kelas atau proses pembayaran. Error: " + e.getMessage());
        }
    }

    @And("customer memilih jadwal kelas yoga yang sesuai dengan membershipnya pada tab {string}")
    public void customerPilihJadwalSesuaiMembership(String namaTab) {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(10));
        JavascriptExecutor js = (JavascriptExecutor) SetupSteps.driver;

        Assert.assertFalse("Variabel membershipClassName kosong!", membershipClassName.isEmpty());
        System.out.println("Mencari jadwal kelas yang sesuai membership: " + membershipClassName);

        try {
            // 1. Jalankan bypass animasi splash screen sebelum klik tab
            js.executeScript("var splash = document.getElementById('splash-circle'); if(splash) { splash.remove(); }");
            js.executeScript("var spinner = document.getElementById('splash-spinner'); if(spinner) { spinner.remove(); }");
            Thread.sleep(500);

            // 2. Kembali ke halaman utama secara DINAMIS berdasarkan parameter 'namaTab' (misal: "Home")
            System.out.println("Mengeklik tab kembali ke halaman: " + namaTab);
            String xpathTabHome = String.format("//a[contains(normalize-space(), '%s')]", namaTab);
            WebElement tabHome = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathTabHome)));
            js.executeScript("arguments[0].click();", tabHome);
            Thread.sleep(1000);

            // Hancurkan splash screen gaib yang sering muncul pas kelar pindah/load page tab baru
            js.executeScript("var splash = document.getElementById('splash-circle'); if(splash) { splash.remove(); }");

            // 3. Cari card kelas menggunakan teknik Case-Insensitive + Kebal Spasi Liar HTML
            String targetClassLower = membershipClassName.toLowerCase();
            String xpathCardSesuai = String.format(
                "//div[contains(translate(normalize-space(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '%s')]" +
                "/ancestor::div[contains(@class, 'card-class')]",
                targetClassLower
            );
            
            WebElement cardTarget = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathCardSesuai)));
            expectedClassName = membershipClassName; // Set sebagai expected class

            // Scroll dan klik masuk ke detail card
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", cardTarget);
            Thread.sleep(500);
            js.executeScript("arguments[0].click();", cardTarget);
            System.out.println("Masuk ke detail kelas yang sesuai kuota membership.");
            Thread.sleep(1500);

            // 4. Klik tombol "Pesan Sekarang" menggunakan XPath Sniper yang aman
            String targetTitle = expectedClassName.toLowerCase();
            String xpathBtnPesan = String.format(
                "//*[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '%s')]" +
                "/ancestor::*[contains(@class, 'card-class')]//a[contains(@class, 'btn-pesan')]", 
                targetTitle
            );
            WebElement btnPesan = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathBtnPesan)));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", btnPesan);
            Thread.sleep(500);
            js.executeScript("arguments[0].click();", btnPesan);
            System.out.println("Tombol pesan sekarang diklik, menunggu pop-up peringatan kuota.");

        } catch (Exception e) {
            Assert.fail("Gagal memilih jadwal kelas yang sesuai dengan membership pada tab " + namaTab + ". Error: " + e.getMessage());
        }
    }

    @And("customer menekan tombol {string} ketika diingatkan memiliki kuota membership")
    public void customerMenekanTombolKuotaMembership(String namaTombolKonfirmasi) {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(15));
        JavascriptExecutor js = (JavascriptExecutor) SetupSteps.driver;

        try {
            // 1. KLIK TOMBOL LANJUT PEMBAYARAN UTAMA TERLEBIH DAHULU
            System.out.println("Mencari dan mengklik tombol Lanjut Pembayaran (#pay-btn)...");
            WebElement btnLanjutPembayaran = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("pay-btn")));
            
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", btnLanjutPembayaran);
            Thread.sleep(500);
            js.executeScript("arguments[0].click();", btnLanjutPembayaran);
            System.out.println("Tombol Lanjut Pembayaran berhasil diklik.");
            
            // Beri jeda 2 detik penuh agar rendering modal selesai sempurna
            Thread.sleep(2000);

            // 2. PROSES BERBURU TOMBOL "Ya" (MENGGUNAKAN KLIK KONDISIONAL)
            String tombolLower = namaTombolKonfirmasi.toLowerCase();
            
            // XPath Sniper: Cari tag button yang teks bersihnya adalah 'ya'
            String xpathModalBtn = String.format(
                "//button[contains(translate(normalize-space(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '%s')]",
                tombolLower
            );

            System.out.println("Menunggu tombol '" + namaTombolKonfirmasi + "' siap untuk diklik...");
            
            // GUARD 1: Pastikan elemen bener-bener siap berinteraksi secara visual
            WebElement btnKonfirmasi = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathModalBtn)));
            
            // Scroll agar posisinya pas di tengah layar emulator
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", btnKonfirmasi);
            Thread.sleep(500);
            
            // GUARD 2: Kita coba klik normal bawaan Appium/Selenium dulu
            try {
                btnKonfirmasi.click();
                System.out.println("Berhasil diklik menggunakan metode Standar.");
            } catch (Exception clickException) {
                // FALLBACK: Kalau gagal karena terhalang layout, tembak pakai JS Executor
                System.out.println("Klik standar gagal, mencoba menembak via JS Executor...");
                js.executeScript("arguments[0].click();", btnKonfirmasi);
                System.out.println("Berhasil diklik menggunakan metode JS Executor.");
            }
            
            System.out.println("Berhasil menyetujui pemakaian kuota dengan menekan: " + namaTombolKonfirmasi);
            
            // Jeda transisi ke halaman rincian gratis (Rp 0)
            Thread.sleep(3000);

        } catch (Exception e) {
            // FALLBACK TERAKHIR: Kalau XPath teks "Ya" macet, tembak button terakhir yang muncul di halaman
            try {
                System.out.println("XPath teks gagal, mencoba fallback klik button type='submit' terakhir...");
                java.util.List<WebElement> buttons = SetupSteps.driver.findElements(By.xpath("//button[@type='submit' or text()='Ya']"));
                if (!buttons.isEmpty()) {
                    WebElement lastButton = buttons.get(buttons.size() - 1);
                    js.executeScript("arguments[0].click();", lastButton);
                    System.out.println("Tombol submit terakhir berhasil dieksekusi!");
                    Thread.sleep(3000);
                    return;
                }
            } catch (Exception ex) {}
            
            Assert.fail("Gagal pada flow modal kuota membership. Error: " + e.getMessage());
        }
    }
    
    @And("customer mengikuti kelas {string} secara gratis")
    public void customerMengikutiKelasSecaraGratis(String namaKelas) {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(10));
        JavascriptExecutor js = (JavascriptExecutor) SetupSteps.driver;

        try {
            // Bersihkan splash screen bawaan PWA
            try { js.executeScript("var splash = document.getElementById('splash-circle'); if(splash) { splash.remove(); }"); } catch (Exception e) {}

            // 1. XPath Sakti nyari teks Rp 0, Gratis, atau Free di mana pun di dalam layar rincian invoice
            String xpathTotalGratis = "//*[contains(normalize-space(), 'Rp 0') or contains(translate(normalize-space(), 'GRATISFREE', 'gratisfree'), 'gratis') or contains(translate(normalize-space(), 'GRATISFREE', 'gratisfree'), 'free')]";
            WebElement infoGratis = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathTotalGratis)));
            
            Assert.assertTrue("Harusnya total harga gratis (Rp 0), tapi elemen tidak terlihat!", infoGratis.isDisplayed());
            System.out.println("Terverifikasi Aman: Skenario invoice bernilai Rp 0 (Gratis) memanfaatkan kuota!");

        } catch (Exception e) {
            Assert.fail("Gagal menyelesaikan proses pemesanan kelas gratis via membership. Error: " + e.getMessage());
        }
    }

    @Then("kuota membership {string} berkurang")
    public void kuotaMembershipBerkurang(String namaKelas) {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(10));
        JavascriptExecutor js = (JavascriptExecutor) SetupSteps.driver;

        try {
            // 1. Jalankan bypass animasi splash screen biar ga ganggu rendering UI
            try { 
                js.executeScript("var splash = document.getElementById('splash-circle'); if(splash) { splash.remove(); }"); 
                Thread.sleep(500);
            } catch (Exception e) {}

            System.out.println("--- VALIDASI KUOTA SIMPEL (CARI ANGKA < 8) ---");

            // 2. XPATH SAKTI: Langsung cari .pertemuan-badge yang teks bersihnya mengandung sisa: 0, 1, 2, 3, 4, 5, 6, atau 7 sebelum garis miring (/)
            // normalize-space() akan membabat habis semua spasi hantu di dalam tag span tersebut
            String xpathKuotaBerkurang = 
                "//span[@class='pertemuan-badge' and (" +
                "contains(normalize-space(), 'Sisa: 0 /') or " +
                "contains(normalize-space(), 'Sisa: 1 /') or " +
                "contains(normalize-space(), 'Sisa: 2 /') or " +
                "contains(normalize-space(), 'Sisa: 3 /') or " +
                "contains(normalize-space(), 'Sisa: 4 /') or " +
                "contains(normalize-space(), 'Sisa: 5 /') or " +
                "contains(normalize-space(), 'Sisa: 6 /') or " +
                "contains(normalize-space(), 'Sisa: 7 /'))]";

            System.out.println("Menunggu badge sisa kuota yang bernilai < 8 muncul di layar...");
            WebElement infoKuotaReal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathKuotaBerkurang)));
            
            // Scroll ke tengah emulator buat mastiin keliatan pas validasi
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", infoKuotaReal);
            
            String teksKuotaUI = infoKuotaReal.getText().trim();
            System.out.println("SUKSES SAKTI (HIJAU)! Ditemukan badge kuota terpotong di UI: " + teksKuotaUI);

        } catch (Exception e) {
            Assert.fail("Validasi gagal! Tidak ditemukan elemen .pertemuan-badge dengan sisa kuota di bawah 8 (masih penuh atau tidak terdeteksi). Error: " + e.getMessage());
        }
    }
}