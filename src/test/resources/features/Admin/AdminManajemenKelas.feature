@Admin
Feature: Manajemen Jadwal dan Kelas Yoga
  Sebagai Admin
  Saya ingin mengelola jadwal kelas, kuota, dan melihat kehadiran
  Agar operasional kelas yoga berjalan dengan teratur

  @TC-18 @FR-07 @FR-15
  Scenario: Admin menambahkan jadwal dan menentukan kuota kelas
    Given admin sudah login dan berada di Halaman Admin
    And admin menekan tab "Home" pada navbar
    When admin menekan tombol "Tambah Kelas"
    And admin mengisi data kelas dan menentukan kuota kelas
    And admin menekan tombol "Tambah Kelas" untuk simpan
    Then sistem berhasil menambahkan jadwal kelas baru

  @TC-19 @FR-15
  Scenario: Admin mengubah data jadwal kelas yang sudah ada
    Given admin sudah login dan berada di Halaman Admin
    And admin menekan tab "Home" pada navbar
    When admin memilih salah satu jadwal kelas
    And admin menekan tombol "Lihat Jadwal"
    And admin menekan logo "Edit"
    And admin mengubah data kelas
    Then data kelas berhasil diperbarui

  @TC-20 @FR-15
  Scenario: Admin menghapus jadwal kelas
    Given admin sudah login dan berada di Halaman Admin
    And admin menekan tab "Home" pada navbar
    When admin menekan tombol "Hapus Jadwal" pada salah satu kelas
    And admin mengonfirmasi penghapusan jadwal
    Then jadwal kelas tersebut berhasil dihapus dari sistem

  @TC-21 @FR-24
  Scenario: Admin melihat data kehadiran peserta
    Given admin sudah login dan berada di Halaman Admin
    And admin menekan tab "Home" pada navbar
    When admin memilih salah satu jadwal kelas
    And admin menekan tombol "Lihat Jadwal"
    Then admin dapat melihat data kehadiran peserta di kelas tersebut