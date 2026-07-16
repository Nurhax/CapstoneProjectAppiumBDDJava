@Customer
Feature: Booking dan Pembayaran Kelas Yoga
  Sebagai customer
  Saya ingin memesan kelas yoga dan melakukan pembayaran
  Agar saya dapat memastikan saya bisa mengikuti kelas sesuai jadwal yang telah saya pilih

  @TC-12 @FR-06 @FR-12 @FR-13
  Scenario: Customer berhasil booking dan bayar kelas yang tersedia (FR12) (FR06)
    Given customer memilih jadwal kelas yoga dengan kuota yang masih tersedia
    When customer melakukan booking dan pembayaran digital
    Then booking tersimpan dengan status terkonfirmasi setelah pembayaran valid
    And kelas yang dipesan muncul pada riwayat aktivitas customer

  @TC-18 @FR-06 @FR-08 @FR-09
  Scenario: Customer berhasil booking dengan kuota membership kelas (FR08) (FR09)
    Given customer memiliki membership with sisa kuota yang mencukupi untuk suatu kelas
    When customer melakukan booking kelas menggunakan kuota membershipnya
    Then booking tersimpan tanpa perlu pembayaran tambahan
    And sisa kuota membership customer berkurang secara otomatis