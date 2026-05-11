@Admin
Feature: Analitik dan Laporan Pendapatan Studio
  Sebagai Admin
  Saya ingin melihat dashboard analitik dan mencetak laporan keuangan
  Agar saya bisa menganalisis performa bisnis studio yoga

  @TC-31 @FR-32
  Scenario: Admin melihat Dashboard Analytic studio
    Given admin sudah login dan berada di Halaman Admin
    When admin menekan tab "Profile" pada navbar
    Then sistem menampilkan dashboard analytic performa studio

  @TC-32 @FR-34
  Scenario: Admin mencetak atau mengunduh data pendapatan
    Given admin sudah login dan berada di Halaman Admin
    And admin menekan tab "Profile" pada navbar
    When admin menekan tombol "Cetak Data Pendapatan"
    Then sistem mendownload data rangkuman pendapatan berdasarkan waktu yang ditentukan