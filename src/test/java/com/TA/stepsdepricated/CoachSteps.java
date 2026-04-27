package com.TA.stepsdepricated;

//package com.ta.steps;
//
//import io.appium.java_client.android.AndroidDriver;
//import io.cucumber.java.en.And;
//import io.cucumber.java.en.Given;
//import io.cucumber.java.en.Then;
//import io.cucumber.java.en.When;
//import org.junit.Assert;
//import org.openqa.selenium.By;
//
//public class CoachSteps {
//
//    private AndroidDriver driver;
//
//    @Given("coach sudah login ke dalam sistem")
//    public void coachSudahLogin() {
//        System.out.println("Login sebagai: Coach");
//    }
//
//    @And("terdapat jadwal kelas yang memiliki peserta terdaftar")
//    public void terdapatJadwalKelasDenganPeserta() {
//        System.out.println("Data Preparation: Kelas dengan minimal 1 peserta");
//    }
//
//    @And("coach berada di Halaman Home")
//    public void coachBeradaDiHalamanHome() {
//        driver.get("https://url-pwa-yoga.com/coach/home");
//    }
//
//    @When("coach memilih kelas yang dituju")
//    public void coachMemilihKelasYangDituju() {
//        driver.findElement(By.id("jadwal-hari-ini")).click();
//    }
//
//    @And("coach mencentang nama-nama peserta yang hadir")
//    public void coachMencentangPesertaHadir() {
//        driver.findElement(By.id("checkbox-peserta-1")).click();
//    }
//
//    @Then("sistem secara visual memindahkan peserta ke kolom hadir")
//    public void visualMemindahkanPeserta() {
//        boolean moved = driver.findElement(By.id("kolom-hadir-peserta-1")).isDisplayed();
//        Assert.assertTrue("Peserta tidak pindah ke kolom hadir", moved);
//    }
//
//    @When("coach menekan tombol {string}")
//    public void coachMenekanTombol(String namaTombol) {
//        String xpath = String.format("//button[contains(text(), '%s')]", namaTombol);
//        driver.findElement(By.xpath(xpath)).click();
//    }
//
//    @Then("sistem berhasil menyimpan data kehadiran")
//    public void sistemBerhasilMenyimpanDataKehadiran() {
//        boolean isSuccess = driver.findElement(By.className("toast-success")).isDisplayed();
//        Assert.assertTrue("Notifikasi sukses tidak muncul", isSuccess);
//    }
//
//    @And("status daftar hadir kelas diperbarui di database")
//    public void statusDaftarHadirDiperbarui() {
//        // Dalam UI Automation, biasanya kita memverifikasi UI/Indikator layar akhir
//        boolean isUpdated = driver.findElement(By.className("badge-attendance-completed")).isDisplayed();
//        Assert.assertTrue("Status kelas belum diperbarui", isUpdated);
//    }
//}