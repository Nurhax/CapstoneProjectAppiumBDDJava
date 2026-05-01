@Customer
Feature: Riwayat Booking dan Aktivitas
  Sebagai Customer
  Saya ingin melihat daftar kelas yang sudah saya pesan
  Agar saya bisa memantau jadwal yoga yang harus saya ikuti

  @TC-12 @FR-14
  Scenario: Customer melihat riwayat kelas (FR14)
    Given customer sudah login dan berada di halaman home
    When customer menekan tab "Aktivitas"
    Then sistem mengarahkan customer ke halaman riawayat booking customer
    And customer melihat teks "Aktivitas Anda" berupa jadwal yang akan dilakukan hari ini
    And customer melihat teks "Riwayat Aktivitas" berupa riwayat aktivitas yang telah lewat