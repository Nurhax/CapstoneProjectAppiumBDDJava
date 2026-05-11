@Admin
Feature: Manajemen Data dan Gaji Coach
  Sebagai Admin
  Saya ingin mengelola profil coach dan menghitung pendapatan mereka
  Agar hak keuangan instruktur terpenuhi dengan akurat

  @TC-28 @FR-16 @FR-26
  Scenario: Admin menambahkan data coach baru beserta rate pendapatannya
    Given admin sudah login dan berada di Halaman Admin
    And admin menekan tab "Coach" pada navbar
    When admin menekan tombol "Tambah Coach"
    And admin mengisi data profil coach
    And admin menginput pendapatan coach berdasarkan kelas
    And admin menekan tombol "Tambah Coach" untuk menyimpan
    Then data coach baru berhasil ditambahkan ke sistem

  @TC-29 @FR-16
  Scenario: Admin mengubah data coach yang sudah ada
    Given admin sudah login dan berada di Halaman Admin
    And admin menekan tab "Coach" pada navbar
    When admin memilih salah satu coach
    And admin menekan logo "Edit"
    And admin mengklik logo checklist untuk mengubah data
    Then data profil coach berhasil diperbarui

  @TC-30 @FR-31
  Scenario: Admin menambahkan bonus atau pendapatan manual untuk Coach
    Given admin sudah login dan berada di Halaman Admin
    And admin menekan tab "Coach" pada navbar
    When admin menekan logo "Edit" di salah satu coach
    And admin menekan tombol "Pendapatan"
    And admin memasukkan nominal pendapatan tambahan
    And admin menekan tombol "Tambah Pendapatan"
    Then total pendapatan coach tersebut bertambah