Feature: User Authentication
  Sebagai pengguna aplikasi
  Saya ingin bisa mendaftar dan masuk ke dalam sistem
  Agar saya bisa menggunakan fitur aplikasi yoga

  Scenario: Pengguna Berhasil Login (TC-01)
    Given pengguna sudah memiliki akun yang terdaftar dan aktif
    And pengguna berada di Halaman Pembuka
    When pengguna memilih opsi "Sudah punya akun"
    And pengguna memasukkan Username dan Password yang valid pada Halaman Login
    And pengguna menekan tombol "Login"
    Then sistem menampilkan notifikasi "Login Berhasil"
    And pengguna diarahkan ke halaman utama (Dashboard)

  Scenario: Pengguna Berhasil Registrasi (TC-02)
    Given pengguna belum memiliki akun
    And pengguna berada di Halaman Pembuka
    When pengguna memilih opsi "Belum punya akun"
    And pengguna memasukkan Username, Nomer telpon, dan Password yang valid pada Halaman Register
    And pengguna menekan tombol "Register"
    Then sistem menampilkan notifikasi "Registrasi akun berhasil"