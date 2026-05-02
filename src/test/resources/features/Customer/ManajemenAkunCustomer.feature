@Customer
Feature: Manajemen Profil Pribadi Customer
  Sebagai customer
  Saya ingin mengubah data profil pribadi saya
  Agar informasi dan kontak saya selalu terbarui

  @TC-11 @FR-19
  Scenario: Customer berhasil memperbarui data profil (FR19)
    Given customer sudah login dengan akun berbeda dan berada di halaman home
    And customer menekan tab "Profil"
    When customer menekan tombol Edit Username
    And customer mengubah nama menjadi "Iqbal updated"
    And customer menekan tombol Save Username
    And customer menekan tombol Edit Nomer HP
    And customer mengubah Nomer HP menjadi "08111222333"
    And customer menekan tombol Save Nomer HP
    And customer menekan tombol Edit Password
    And customer mengubah passwordnya menjadi "testing123!"
    And customer menekan tombol Save Password
    Then data profil customer berubah sesuai dengan inputan yang baru
