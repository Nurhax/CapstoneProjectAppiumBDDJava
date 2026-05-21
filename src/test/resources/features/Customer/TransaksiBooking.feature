@Customer
Feature: Booking dan Pembayaran Kelas Yoga
  Sebagai customer
  Saya ingin memesan kelas yoga dan melakukan pembayaran
  Agar saya dapat memastikan saya bisa mengikuti kelas sesuai jadwal yang telah saya pilih

  @TC-12 @FR-06 @FR-12 @FR-13
  Scenario: Customer berhasil booking dan bayar kelas yang tersedia (FR12) (FR06)
    Given customer sudah login dan berada di halaman Home
    When customer memilih jadwal kelas yoga yang tersedia
    And customer menekan tombol "Pesan Sekarang"
    And customer melakukan proses pembayaran
    And customer menekan tab "Aktivitas"
    Then jadwal kelas yang dipesan akan muncul di halaman aktivitas

  @TC-18 @FR-06 @FR-08 @FR-09
  Scenario: Customer berhasil booking dengan kuota membership kelas (FR08) (FR09)
    Given customer sudah login dan berada di halaman home
    When customer membeli membership untuk kelas "Hatha Yoga" pada tab "Member"
    And customer memilih jadwal kelas yoga yang sesuai dengan membershipnya pada tab "Home"
    And customer menekan tombol "Ya" ketika diingatkan memiliki kuota membership
    And customer mengikuti kelas "Hatha Yoga" secara gratis
    Then kuota membership "Hatha Yoga" berkurang 