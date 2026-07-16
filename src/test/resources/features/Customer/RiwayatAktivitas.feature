@Customer
Feature: Riwayat Booking dan Aktivitas
  Sebagai Customer
  Saya ingin melihat daftar kelas yang sudah saya pesan
  Agar saya bisa memantau jadwal yoga yang harus saya ikuti

  @TC-11 @FR-14[cite: 8]
  Scenario: Customer melihat riwayat kelas (US14)
    Given customer sudah login dan berada di halaman Home
    When customer membuka halaman aktivitas
    Then sistem menampilkan jadwal aktivitas hari ini dan riwayat aktivitas yang telah lewat