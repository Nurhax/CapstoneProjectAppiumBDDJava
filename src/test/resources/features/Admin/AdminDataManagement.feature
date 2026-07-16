@Admin
Feature: Manajemen Data Master oleh Admin
  Sebagai seorang admin sistem
  Saya ingin mengelola data jadwal kelas, data coach, dan paket membership
  Agar seluruh data operasional di dalam aplikasi PWA selalu diperbarui dan valid

  @TC-24 @US-15
  Scenario: Admin mengubah data jadwal kelas yoga yang sudah ada
    Given admin telah login ke dalam sistem
    When admin memperbarui informasi dari suatu jadwal kelas yoga
    Then sistem berhasil menyimpan pembaruan data kelas tersebut

  @TC-25 @US-15
  Scenario: Admin menghapus jadwal kelas yoga yang tersedia
    Given admin telah login ke dalam sistem
    When admin menghapus suatu jadwal kelas yoga dari sistem
    Then jadwal kelas tersebut berhasil dihapus dari daftar

  @TC-26 @US-16
  Scenario: Admin mengubah informasi data coach yang sudah terdaftar
    Given admin telah login ke dalam sistem
    When admin memperbarui profil data seorang coach
    Then sistem berhasil menyimpan pembaruan informasi coach tersebut

  @TC-27 @US-22
  Scenario: Admin mengubah rincian paket data membership
    Given admin telah login ke dalam sistem
    When admin memperbarui rincian sebuah paket membership
    Then sistem berhasil menyimpan pembaruan pada paket data membership tersebut

  @TC-28 @US-22
  Scenario: Admin menghapus paket membership pada hari tertentu
    Given admin telah login ke dalam sistem
    When admin menghapus suatu paket membership yang tersedia
    Then sistem memvalidasi bahwa paket membership tersebut berhasil dihapus

  @TC-29 @US-22
  Scenario: Admin menghapus paket membership pelanggan secara permanen via menu customer
    Given admin telah login ke dalam sistem
    When admin mencabut paket membership milik seorang pelanggan
    Then sistem memastikan data membership pelanggan tersebut terhapus secara permanen