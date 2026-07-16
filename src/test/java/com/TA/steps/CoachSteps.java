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
    public static boolean isAlreadyUploaded = false;
    
    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    // PRE-CONDITION: LOGIN COACH
    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX

    @Given("^coach sudah login dan berada di halaman coach$")
    @Given("^seorang coach sudah login ke sistem$")
    public void coachSudahLogin() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        JavascriptExecutor js = (JavascriptExecutor) SetupSteps.driver;
        
        SetupSteps.driver.get("http://10.0.2.2:8000");
        
        try {
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
        
        try { Thread.sleep(1500); } catch (Exception e) {}
        try {
            js.executeScript("var splash = document.getElementById('splash-circle'); if(splash) { splash.remove(); }");
            js.executeScript("var spinner = document.getElementById('splash-spinner'); if(spinner) { spinner.remove(); }");
        } catch (Exception e) {}

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(@class, 'coach-nav-item')]")));
    }

    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    // MODUL: JADWAL & PESERTA (US17 & US20)
    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX

    @When("coach meminta untuk melihat jadwal mengajar hari ini")
    public void coachMemintaMelihatJadwal() {
        System.out.println("Coach berada di beranda (Home) yang menampilkan jadwal hari ini.");
    }

    @Then("sistem harus menampilkan daftar kelas yang dijadwalkan untuk coach tersebut hari ini")
    public void coachMelihatJadwalHariIni() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        JavascriptExecutor js = (JavascriptExecutor) SetupSteps.driver;
        try { js.executeScript("var splash = document.getElementById('splash-circle'); if(splash) { splash.remove(); }"); } catch (Exception e) {}

        boolean isJadwalVisible = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("schedule-card"))).isDisplayed();
        Assert.assertTrue("Jadwal mengajar Coach tidak ditemukan!", isJadwalVisible);
    }

    @Given("seorang coach sudah login ke sistem dan melihat jadwal kelasnya")
    public void loginDanMelihatJadwal() {
        coachSudahLogin();
        coachMelihatJadwalHariIni();
    }

    @When("coach memilih kelas yang akan diajar")
    public void coachMemilihJadwal() {
        // Navigasi langsung ke URL detil kelas
        SetupSteps.driver.get("http://10.0.2.2:8000/coach/schedule/749");
        try {
            JavascriptExecutor js = (JavascriptExecutor) SetupSteps.driver;
            js.executeScript("var splash = document.getElementById('splash-circle'); if(splash) { splash.remove(); }");
            Thread.sleep(500);
        } catch (Exception e) {}
    }

    @Then("sistem harus menampilkan daftar peserta yang terdaftar untuk kelas tersebut")
    public void coachMelihatDaftarPeserta() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        JavascriptExecutor js = (JavascriptExecutor) SetupSteps.driver;
        try {
            js.executeScript("var splash = document.getElementById('splash-circle'); if(splash) { splash.remove(); }");
            js.executeScript("var spinner = document.getElementById('splash-spinner'); if(spinner) { spinner.remove(); }");
            Thread.sleep(500);
        } catch (Exception e) {}

        boolean isListVisible = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("absen-table"))).isDisplayed();
        Assert.assertTrue("Daftar peserta tidak muncul!", isListVisible);
    }

    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    // MODUL: VERIFIKASI KEHADIRAN (US21 & US24)
    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX

    @Given("seorang coach sudah login dan melihat daftar peserta untuk kelas yang akan diajar")
    public void beradaDiHalamanPeserta() {
        coachSudahLogin();
        coachMemilihJadwal();
        coachMelihatDaftarPeserta();
    }

    @When("coach menandai peserta sebagai {string}")
    public void klikChecklistPeserta(String statusTarget) {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(10));
        JavascriptExecutor js = (JavascriptExecutor) SetupSteps.driver;

        try {
            String xpathRowTarget = "//tr[.//input[contains(@class, 'toggle-input') and not(@checked)]]";
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathRowTarget)));
            
            java.util.List<WebElement> rows = SetupSteps.driver.findElements(By.xpath(xpathRowTarget));
            if (rows.isEmpty()) Assert.fail("Tidak ada peserta yang bisa diabsen.");
            
            WebElement rowTarget = rows.get(0);
            checkedParticipantName = rowTarget.findElement(By.xpath("./td[1]")).getText().trim();
            
            WebElement btnChecklist = rowTarget.findElement(By.xpath(".//label[contains(@class, 'toggle-label')]"));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", btnChecklist);
            Thread.sleep(500); 
            js.executeScript("arguments[0].click();", btnChecklist);
            Thread.sleep(1000); 

            String xpathUpdate = "//*[contains(@class, 'btn-update') or @type='submit']";
            WebElement btnUpdate = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathUpdate)));
            js.executeScript("arguments[0].click();", btnUpdate);
            Thread.sleep(3000);
        } catch (Exception e) {
            Assert.fail("Gagal menandai kehadiran peserta. Error: " + e.getMessage());
        }
    }

    @Then("sistem harus memperbarui status kehadiran peserta tersebut menjadi {string}")
    public void dataPesertaBerubah(String statusTujuan) {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(10));
        JavascriptExecutor js = (JavascriptExecutor) SetupSteps.driver;

        Assert.assertFalse("Variabel checkedParticipantName kosong!", checkedParticipantName.isEmpty());
        try {
            try { js.executeScript("var splash = document.getElementById('splash-circle'); if(splash) { splash.remove(); }"); } catch (Exception e) {}

            String xpathValidasiTabelHadir = String.format(
                "//table[@class='absen-table']/tbody/tr[td[1][contains(translate(normalize-space(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '%s')]]" +
                "/td[2]//*[contains(@class, 'status-badge-hadir') or contains(text(), 'Hadir')]",
                checkedParticipantName.toLowerCase()
            );

            WebElement badgeHadir = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathValidasiTabelHadir)));
            Assert.assertTrue("Status peserta belum berubah di tabel!", badgeHadir.isDisplayed());
        } catch (Exception e) {
            Assert.fail("Gagal memvalidasi status peserta menjadi Hadir. Error: " + e.getMessage());
        }
    }

    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    // MODUL: UPLOAD BUKTI (US23 & US25)
    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX

    @Given("seorang coach sudah login dan telah memverifikasi kehadiran peserta kelas")
    public void loginDanVerifikasiSelesai() {
        beradaDiHalamanPeserta();
    }

    @When("coach mengunggah foto sebagai bukti kehadiran kelas")
    public void mengunggahFotoBuktiKelas() {
        // Kombinasi proses Select File & Native Context Picker dari source code lama
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(12));
        JavascriptExecutor js = (JavascriptExecutor) SetupSteps.driver;
        String currentContext = ((io.appium.java_client.android.AndroidDriver) SetupSteps.driver).getContext();
        isAlreadyUploaded = false;

        try {
            if (currentContext.contains("NATIVE_APP")) {
                for (String contextName : ((io.appium.java_client.android.AndroidDriver) SetupSteps.driver).getContextHandles()) {
                    if (contextName.contains("WEBVIEW")) {
                        ((io.appium.java_client.android.AndroidDriver) SetupSteps.driver).context(contextName);
                        currentContext = contextName;
                        break;
                    }
                }
            }
            
            WebElement uploadAreaBox = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='uploadArea'] | //*[contains(@class, 'photo-box')]")));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", uploadAreaBox);
            Thread.sleep(1000); 

            ((io.appium.java_client.android.AndroidDriver) SetupSteps.driver).context("NATIVE_APP");
            
            WebElement nativeTarget = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//*[@text='Select file' or @content-desc='Select file'] | " +
                "//*[contains(@text, 'tap untuk ganti') or contains(@text, 'Foto sudah diupload')] | " +
                "//*[contains(@resource-id, 'uploadText')] | //*[contains(@resource-id, 'replaceCaption')]"
            )));
            
            String teksTerdeteksi = nativeTarget.getText();
            if (teksTerdeteksi.toLowerCase().contains("ganti") || teksTerdeteksi.toLowerCase().contains("sudah")) {
                isAlreadyUploaded = true;
            }

            nativeTarget.click();
            Thread.sleep(3000); 

            // Eksekusi klik murni koordinat untuk memilih gambar dari galeri Android
            org.openqa.selenium.Dimension screenSize = SetupSteps.driver.manage().window().getSize();
            int clickX = (int) (screenSize.getWidth() * 0.25); 
            int clickY = (int) (screenSize.getHeight() * 0.32); 
            
            for (int i = 1; i <= 2; i++) {
                org.openqa.selenium.interactions.PointerInput finger = new org.openqa.selenium.interactions.PointerInput(org.openqa.selenium.interactions.PointerInput.Kind.TOUCH, "finger");
                org.openqa.selenium.interactions.Sequence tapSequence = new org.openqa.selenium.interactions.Sequence(finger, 1);
                tapSequence.addAction(finger.createPointerMove(Duration.ofMillis(0), org.openqa.selenium.interactions.PointerInput.Origin.viewport(), clickX, clickY));
                tapSequence.addAction(finger.createPointerDown(org.openqa.selenium.interactions.PointerInput.MouseButton.LEFT.asArg()));
                tapSequence.addAction(finger.createPointerUp(org.openqa.selenium.interactions.PointerInput.MouseButton.LEFT.asArg()));
                ((io.appium.java_client.android.AndroidDriver) SetupSteps.driver).perform(java.util.Collections.singletonList(tapSequence));
                Thread.sleep(300); 
            }
            Thread.sleep(4000); 

        } catch (Exception e) {
            Assert.fail("Proses unggah gagal: " + e.getMessage());
        } finally {
            try { ((io.appium.java_client.android.AndroidDriver) SetupSteps.driver).context(currentContext); } catch (Exception e) {}
        }
    }

    @Then("sistem harus menyimpan bukti kehadiran and memperbarui status kelas")
    public void sistemSimpanUpdate() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(10));
        try {
            Thread.sleep(1000); 
            String xpathToast = "//*[contains(@class, 'toast-success') or contains(@class, 'alert-success') or contains(translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'berhasil')]";
            
            WebElement toastNotif = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathToast)));
            Assert.assertTrue("Notifikasi berhasil tidak ditampilkan!", toastNotif.isDisplayed());
        } catch (Exception e) {
            Assert.fail("Gagal mendeteksi notifikasi sukses. Error: " + e.getMessage());
        }
    }

    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    // MODUL: LAPORAN GAJI COACH (US27)
    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX

    @When("coach menekan tab {string} pada navbar")
    public void coachMenekanTabNavbar(String namaTab) {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        JavascriptExecutor js = (JavascriptExecutor) SetupSteps.driver;

        try {
            js.executeScript("var splash = document.getElementById('splash-circle'); if(splash) { splash.remove(); }");
            Thread.sleep(500);
        } catch (Exception e) {}

        String xpath = String.format("//a[contains(normalize-space(), '%s')]", namaTab);
        WebElement tabElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
        js.executeScript("arguments[0].click();", tabElement);
    }

    @Then("coach dapat melihat laporan gaji dari kelas yang telah selesai")
    public void coachMelihatLaporanGaji() {
        WebDriverWait wait = new WebDriverWait(SetupSteps.driver, Duration.ofSeconds(5));
        JavascriptExecutor js = (JavascriptExecutor) SetupSteps.driver;

        try {
            try { js.executeScript("var splash = document.getElementById('splash-circle'); if(splash) { splash.remove(); }"); } catch (Exception e) {}

            String xpathLaporanGaji = "//*[contains(@class, 'graph-title') or contains(@class, 'pendapatan-value') or contains(@id, 'salary')] | //*[contains(normalize-space(), 'Total Pendapatan') or contains(normalize-space(), 'Laporan Gaji')]";
            
            WebElement elementGaji = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathLaporanGaji)));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", elementGaji);
            
            Assert.assertTrue("Laporan gaji gagal ditampilkan!", elementGaji.isDisplayed());
        } catch (Exception e) {
            Assert.fail("Gagal menampilkan laporan gaji. Error: " + e.getMessage());
        }
    }
}