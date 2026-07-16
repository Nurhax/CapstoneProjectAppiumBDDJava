@Customer
Feature: Manajemen Profil Pribadi Customer
  Sebagai customer
  Saya ingin mengubah data profil pribadi saya
  Agar informasi dan kontak saya selalu terbarui

  @TC-10 @FR-19
  Scenario: Customer berhasil memperbarui data profil (US19)
    Given customer sudah login dan berada di halaman profil
    When customer memperbarui nama lengkap, nomor HP, dan password miliknya masing-masing
    Then setiap perubahan tersimpan sesuai field yang diedit tanpa memengaruhi data lain
    And sistem menampilkan data profil terbaru setelah perubahan berhasil disimpan