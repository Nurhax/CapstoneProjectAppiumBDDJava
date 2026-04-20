package com.ta.steps;

import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;

public class AdminSteps {

    private AndroidDriver driver;

    @Given("admin sudah login dengan hak akses {string}")
    public void adminSudahLoginDenganHakAkses(String role) {
        // Logika login khusus admin
        System.out.println("Login sebagai: " + role);
    }

    @And("admin berada di Halaman Menu Jadwal")
    public void adminBeradaDiHalamanMenuJadwal() {
        driver.get("https://url-pwa-yoga.com/admin/jadwal");
    }

    @And("admin berada di Halaman Data Coach")
    public void adminBeradaDiHalamanDataCoach() {
        driver.get("https://url-pwa-yoga.com/admin/coach");
    }

    @When("admin menekan tombol {string}")
    public void adminMenekanTombol(String namaTombol) {
        String xpath = String.format("//button[contains(text(), '%s')]", namaTombol);
        driver.findElement(By.xpath(xpath)).click();
    }

    @And("admin mengisi form data kelas dengan informasi yang valid")
    public void adminMengisiFormDataKelas() {
        driver.findElement(By.id("input-nama-kelas")).sendKeys("Vinyasa Yoga Pagi");
        driver.findElement(By.id("input-waktu")).sendKeys("08:00");
    }

    @Then("sistem berhasil menyimpan data kelas")
    public void sistemBerhasilMenyimpanDataKelas() {
        boolean isSuccess = driver.findElement(By.className("alert-success")).isDisplayed();
        Assert.assertTrue("Gagal menyimpan data kelas", isSuccess);
    }

    @And("jadwal kelas baru muncul di dalam daftar jadwal")
    public void jadwalKelasBaruMuncul() {
        boolean isDisplayed = driver.findElement(By.xpath("//td[contains(text(), 'Vinyasa Yoga Pagi')]")).isDisplayed();
        Assert.assertTrue("Kelas tidak ada di daftar", isDisplayed);
    }

    @And("terdapat minimal satu jadwal kelas yang aktif")
    public void terdapatMinimalSatuJadwalKelasYangAktif() {
        System.out.println("Asumsi: Data jadwal sudah tersedia di database");
    }

    @When("admin memilih kelas dan menekan tombol {string}")
    public void adminMemilihKelasDanMenekanTombol(String namaTombol) {
        driver.findElement(By.id("row-kelas-aktif")).click();
        adminMenekanTombol(namaTombol); // Reusable step
    }

    @And("admin mengisi data peserta dengan informasi yang valid")
    public void adminMengisiDataPeserta() {
        driver.findElement(By.id("input-nama-peserta")).sendKeys("Budi Santoso");
    }

    @Then("sistem berhasil menyimpan data peserta")
    public void sistemBerhasilMenyimpanDataPeserta() {
        boolean isSuccess = driver.findElement(By.className("alert-success")).isDisplayed();
        Assert.assertTrue("Gagal menyimpan data peserta", isSuccess);
    }

    @And("nama peserta terdaftar ke dalam jadwal kelas tersebut")
    public void namaPesertaTerdaftar() {
        boolean isDisplayed = driver.findElement(By.xpath("//td[contains(text(), 'Budi Santoso')]")).isDisplayed();
        Assert.assertTrue("Peserta tidak terdaftar", isDisplayed);
    }

    @And("admin mengisi form data coach dengan informasi yang valid")
    public void adminMengisiFormDataCoach() {
        driver.findElement(By.id("input-nama-coach")).sendKeys("Coach Sarah");
    }

    @Then("sistem berhasil menyimpan data coach")
    public void sistemBerhasilMenyimpanDataCoach() {
        boolean isSuccess = driver.findElement(By.className("alert-success")).isDisplayed();
        Assert.assertTrue("Gagal menyimpan data coach", isSuccess);
    }

    @And("profil coach baru muncul di dalam daftar direktori coach")
    public void profilCoachBaruMuncul() {
        boolean isDisplayed = driver.findElement(By.xpath("//h4[contains(text(), 'Coach Sarah')]")).isDisplayed();
        Assert.assertTrue("Coach tidak ditemukan", isDisplayed);
    }
}