Feature: Manajemen Membership
  Sebagai pengguna aplikasi
  Saya ingin membeli atau memperpanjang paket membership
  Agar saya bisa menikmati fasilitas member

  Scenario: Pembelian atau Perpanjangan Membership Berhasil (TC-MEMB-01)
    Given pengguna sudah login ke dalam aplikasi
    And pengguna berada di Halaman Home
    When pengguna masuk ke Halaman Detail Member
    And pengguna menekan tombol "Pesan Member"
    And pengguna menyelesaikan proses pembayaran dengan sukses
    Then jadwal atau status member yang dipesan masuk ke halaman aktivitas pengguna