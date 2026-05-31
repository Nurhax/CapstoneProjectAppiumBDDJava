@Admin
Feature: Manajemen Data Master oleh Admin
  Sebagai seorang admin sistem
  Saya ingin mengelola data jadwal kelas, data coach, dan paket membership
  Agar seluruh data operasional di dalam aplikasi PWA selalu diperbarui dan valid

  @TC-24 @FR-15
  Scenario: Admin mengubah data jadwal kelas yoga yang sudah ada
    Given admin berada di halaman dashboard admin
    When admin memilih opsi "Home" pada navbar admin data
    And admin menekan tombol "View Jadwal"
    And admin mengubah data salah satu jadwal
    And admin menekan tombol "Simpan Perubahan" pada kelas terpilih
    Then sistem berhasil memperbarui data jadwal kelas tersebut

  @TC-25 @FR-15
  Scenario: Admin menghapus jadwal kelas yoga yang tersedia
    Given admin berada di halaman dashboard admin
    When admin memilih opsi "Home" pada navbar admin data
    And admin menekan tombol "Hapus Jadwal"
    And admin mengonfirmasi penghapusan jadwal kelas
    Then sistem berhasil menghapus jadwal kelas tersebut dari daftar

  @TC-26 @FR-16
  Scenario: Admin mengubah informasi data coach yang sudah terdaftar
    Given admin berada di halaman dashboard admin
    When admin memilih opsi "Coach" pada navbar 
    And admin memilih salah satu coach dan klik logo edit
    And admin mengganti data admin untuk diperbarui
    And admin menekan tombol "Simpan Perubahan"
    Then sistem berhasil menyimpan pembaruan informasi data coach

  @TC-27 @FR-22
  Scenario: Admin mengubah rincian paket data membership
    Given admin berada di halaman dashboard admin
    When admin memilih opsi "Home" pada navbar admin data
    And admin menekan tombol "View Membership"
    And admin mengubah data membership untuk diperbarui
    And admin menekan tombol "Simpan Perubahan"
    Then sistem berhasil memperbarui paket data membership tersebut

  @TC-28 @FR-22
  Scenario: Admin menghapus paket membership pada hari tertentu
    Given admin berada di halaman dashboard admin
    When admin memilih opsi "Home" pada navbar admin data
    And admin menekan tombol "Hapus" pada section membership
    And admin mengonfirmasi hapus paket data membership
    Then sistem memvalidasi membership di hari tersebut berhasil dihapus

  @TC-29 @FR-22
  Scenario: Admin menghapus paket membership pelanggan secara permanen via menu customer
    Given admin berada di halaman dashboard admin
    When admin memilih opsi "Pelanggan" pada navbar admin data
    And admin menekan logo edit di salah satu peserta
    And admin menekan tombol "Hapus Membership"
    And admin mengonfirmasi hapus paket data secara permanen
    Then sistem memastikan data membership customer terhapus secara permanen