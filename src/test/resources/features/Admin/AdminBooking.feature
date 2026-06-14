@Admin
Feature: Operasional dan Manajemen Admin
  Sebagai seorang admin sistem
  Saya ingin mengelola jadwal kelas, memantau kuota membership, memproses pembatalan, serta menginput pembayaran
  Agar operasional studio yoga dapat berjalan dengan tertib dan tercatat dengan baik

  @TC-19 @FR-07 @FR-15
  Scenario: Admin menambahkan jadwal kelas yoga baru beserta batas kuota peserta
    Given admin berada di halaman dashboard admin
    When admin memilih opsi "Home" pada navbar admin
    And admin menekan tombol "Tambah Kelas"
    And admin mengisi data kelas serta menentukan kuota kelas yang valid
    And admin menekan tombol tambah kelas
    Then sistem berhasil menyimpan jadwal kelas baru beserta pembatasan kuotanya

  @TC-20 @FR-08 @FR-09
  Scenario: Admin memantau sisa kuota paket membership milik salah satu pelanggan
    Given admin berada di halaman dashboard admin
    When admin memilih opsi "Pelanggan" pada navbar admin
    And admin menekan tombol edit pada salah satu pengguna
    Then sistem menampilkan aktivitas kelas yang diikuti serta sisa kuota membership pelanggan tersebut

  @TC-21 @FR-10
  Scenario: Admin melakukan pembatalan pesanan booking kelas yoga milik pelanggan
    Given admin berada di halaman dashboard admin
    When admin memilih opsi "Pelanggan" pada navbar admin
    And admin menekan tombol edit pada salah satu pengguna
    And admin menekan tombol "Batalkan Jadwal"
    And admin menekan tombol "Ya"
    Then sistem berhasil memproses pembatalan booking dan mengembalikan status kelas tersebut

  @TC-22 @FR-11
  Scenario: Admin menginput data pembayaran tunai secara manual untuk peserta kelas
    Given admin berada di halaman dashboard admin
    When admin memilih opsi "Home" pada navbar admin
    And admin memilih salah satu jadwal kelas yang tersedia
    And admin menekan tombol "Lihat Jadwal"
    And admin menekan tombol "Input Peserta"
    And admin mengisi data peserta dan nominal pembayaran cash dengan valid
    And admin menekan tombol tambah peserta
    Then sistem berhasil mencatat pembayaran cash dan mendaftarkan peserta ke kelas

  @TC-23 @FR-13
  Scenario: Admin memverifikasi status validasi pembayaran online milik customer
    Given admin berada di halaman dashboard admin
    When admin memilih opsi "Home" pada navbar admin
    And admin memilih salah satu jadwal kelas yang tersedia
    And admin menekan tombol "Lihat Jadwal"
    Then sistem menampilkan status verifikasi pembayaran pelanggan berupa valid atau tidak valid