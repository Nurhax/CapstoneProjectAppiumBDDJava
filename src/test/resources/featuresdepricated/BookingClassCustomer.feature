Feature: Booking Kelas Yoga
  Sebagai member yoga
  Saya ingin mencari, memesan, atau membatalkan kelas
  Agar saya bisa mengatur jadwal latihan saya

  Scenario: Booking Kelas Berhasil & Mengikuti Kelas (TC-YOGA-01)
    Given pengguna sudah login ke dalam aplikasi
    And pengguna berada di Halaman Home
    And kelas yoga yang dipilih masih tersedia kuotanya
    When pengguna masuk ke halaman detail kelas
    And pengguna menekan tombol "Pesan Kelas"
    And pengguna menyelesaikan proses pembayaran dengan sukses
    Then jadwal kelas tersebut masuk ke halaman aktivitas pengguna
    When pengguna memilih status pesanan "Lanjut"
    Then sistem mengonfirmasi pengguna mengikuti kelas tersebut

  Scenario: Pembatalan Booking & Proses Refund (TC-YOGA-02)
    Given pengguna sudah berhasil memesan kelas dan melakukan pembayaran
    And pengguna berada di halaman aktivitas
    When pengguna memilih status pesanan "Batal" pada kelas tersebut
    Then sistem membatalkan pesanan kelas
    And pengguna mendapatkan refund sesuai dengan ketentuan admin

  Scenario: Pencarian Alternatif Saat Kelas Penuh (TC-YOGA-03)
    Given pengguna sudah login ke dalam aplikasi
    And pengguna berada di Halaman Home
    When pengguna memilih kelas yoga yang kuotanya sudah penuh
    Then sistem menginformasikan bahwa kelas tidak tersedia
    And pengguna diarahkan untuk mencari kelas lain yang tersedia