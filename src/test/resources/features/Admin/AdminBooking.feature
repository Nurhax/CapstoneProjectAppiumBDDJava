@Admin
Feature: Operasional dan Manajemen Admin
  Sebagai seorang admin sistem
  Saya ingin mengelola jadwal kelas, memantau kuota membership, memproses pembatalan, serta menginput pembayaran
  Agar operasional studio yoga dapat berjalan dengan tertib dan tercatat dengan baik

  @TC-19 @FR-07 @US-15
  Scenario: Admin menambahkan jadwal kelas yoga baru beserta batas kuota peserta
    Given admin telah login ke dalam sistem
    When admin menambahkan jadwal kelas yoga baru dengan data dan kuota yang valid
    Then jadwal kelas baru beserta pembatasan kuotanya berhasil tersimpan di sistem

  @TC-20 @FR-08 @US-09
  Scenario: Admin memantau sisa kuota paket membership milik salah satu pelanggan
    Given admin telah login ke dalam sistem
    When admin memeriksa profil keanggotaan milik seorang pelanggan
    Then sistem menampilkan aktivitas kelas yang diikuti beserta sisa kuota membership pelanggan tersebut

  @TC-21 @US-10
  Scenario: Admin melakukan pembatalan pesanan booking kelas yoga milik pelanggan
    Given admin telah login ke dalam sistem
    When admin membatalkan booking kelas yoga milik seorang pelanggan
    Then sistem berhasil memproses pembatalan dan mengembalikan status kuota kelas tersebut

  @TC-22 @US-11
  Scenario: Admin menginput data pembayaran tunai secara manual untuk peserta kelas
    Given admin telah login ke dalam sistem
    When admin mendaftarkan peserta ke suatu kelas menggunakan metode pembayaran tunai
    Then sistem berhasil mencatat transaksi tunai tersebut dan peserta terdaftar ke dalam kelas

  @TC-23 @US-13
  Scenario: Admin memverifikasi status validasi pembayaran online milik customer
    Given admin telah login ke dalam sistem
    When admin memantau rincian pendaftar pada suatu jadwal kelas
    Then sistem menampilkan status validasi pembayaran online milik peserta secara akurat