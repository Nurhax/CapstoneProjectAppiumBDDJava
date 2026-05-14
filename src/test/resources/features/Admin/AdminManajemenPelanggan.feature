@Admin
Feature: Manajemen Pelanggan, Membership, dan Pembayaran
  Sebagai Admin
  Saya ingin mengelola data pelanggan, membership, dan memproses pembayaran
  Agar administrasi peserta dan keuangan studio tertata dengan baik

  @TC-22 @FR-30
  Scenario: Admin melihat daftar kumpulan data pelanggan (FR30)
    Given admin sudah login dan berada di Halaman Admin
    When admin menekan tab "Pelanggan" pada navbar
    Then admin dapat melihat kumpulan data pelanggan

  @TC-23 @FR-08 @FR-09
  Scenario: Admin melihat aktivitas dan sisa kuota membership pelanggan (FR08)
    Given admin sudah login dan berada di Halaman Admin
    And admin menekan tab "Pelanggan" pada navbar
    When admin menekan tombol "Edit" pada salah satu user
    Then sistem menampilkan aktivitas kelas yang diikuti serta sisa membership pelanggan tersebut

  @TC-24 @FR-10
  Scenario: Admin melakukan pembatalan booking kelas untuk pelanggan
    Given admin sudah login dan berada di Halaman Admin
    And admin menekan tab "Pelanggan" pada navbar
    When admin menekan tombol "Edit" pada salah satu user
    And admin memilih salah satu kelas yang ingin dibatalkan
    And admin menekan tombol "Batalkan Pemesanan"
    Then pemesanan kelas pelanggan tersebut berhasil dibatalkan

  @TC-25 @FR-11
  Scenario: Admin melakukan input pembayaran tunai (Cash) untuk peserta
    Given admin sudah login dan berada di Halaman Admin
    And admin menekan tab "Home" pada navbar
    When admin memilih salah satu jadwal kelas
    And admin menekan tombol "Lihat Jadwal"
    And admin menekan tombol "Input Peserta"
    And admin mengisi data peserta yang membayar cash
    And admin menekan tombol "Tambah Peserta"
    Then peserta berhasil didaftarkan ke dalam kelas

  @TC-26 @FR-13
  Scenario: Admin mengelola status validasi pembayaran customer
    Given admin sudah login dan berada di Halaman Admin
    And admin menekan tab "Home" pada navbar
    When admin memilih salah satu jadwal kelas
    And admin menekan tombol "Lihat Jadwal"
    Then sistem menampilkan status pembayaran pelanggan valid atau tidak valid

  @TC-27 @FR-22
  Scenario: Admin menambahkan paket membership baru
    Given admin sudah login dan berada di Halaman Admin
    And admin menekan tab "Home" pada navbar
    When admin menekan tombol "Tambah Member"
    And admin mengisi data membership
    And admin menekan tombol "Tambah Membership"
    Then sistem menyimpan data membership agar dapat dibeli oleh customer