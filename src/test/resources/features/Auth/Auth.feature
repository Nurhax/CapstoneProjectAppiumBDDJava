Feature: User Authentication
  Sebagai pengguna aplikasi
  Saya ingin bisa mendaftar dan masuk ke dalam sistem sesuai dengan role/peran saya
  Agar saya bisa menggunakan fitur aplikasi yoga

  Scenario: Pengguna Berhasil Registrasi Sebagai Customer (TC-01)
    Given pengguna belum memiliki akun
    And pengguna berada di Landing Page
    When pengguna memilih opsi "Register"
    And pengguna memasukkan Username, Nomer telpon, dan Password yang valid pada Halaman Register
    And pengguna menekan tombol "Buat Akun"
    Then sistem menampilkan notifikasi "Akun berhasil dibuat! Silakan login."

  Scenario: Pengguna Berhasil Login Sebagai Customer (TC-02)
    Given pengguna sudah memiliki akun yang terdaftar dan aktif
    And pengguna berada di Landing Page
    When pengguna memilih opsi "Log In"
    And pengguna memasukkan Username dan Password yang valid pada Halaman Login
    And pengguna menekan tombol "Masuk"
    And pengguna diarahkan ke halaman utama (Dashboard)

  Scenario: Pengguna Berhasil Login Sebagai Admin (TC-03)
    Given pengguna sudah memiliki akun yang terdaftar dan aktif dari developer
    And pengguna berada di Landing Page
    When pengguna memilih opsi "Log In" 
    And pengguna memasukkan Username dan Password yang valid pada Halaman Login
    And pengguna menekan tombol "Masuk"
    Then pengguna diarahkan ke halaman utama admin (Dashboard)

  Scenario: Pengguna Berhasil Mendaftarkan Coach Baru (TC-04)
    Given pengguna sudah memiliki akun yang terdaftar dan aktif dari developer
    And pengguna berada di Landing Page
    When pengguna memilih opsi "Log In"
    And pengguna memasukkan Username dan Password yang valid pada Halaman Login
    And pengguna menekan tombol "Masuk"
    Then pengguna diarahkan ke halaman utama admin (Dashboard)
    And pengguna menekan tab "Coach"
    And pengguna menekan tombol "Tambah Coach"
    And pengguna mengisi nama coach, keahlian, nomor hp, deskripsi, rate per kelas, pengalaman tahun dan password
    And pengguna menekan tombol "Tambah Coach" lagi
    Then sistem menampilkan notifikasi "Coach berhasil ditambahkan!" 
    
   Scenario: Pengguna Berhasil Login Sebagai Coach (TC-05)
    Given pengguna sudah memiliki akun yang terdaftar dan aktif dari admin
    And pengguna berada di Landing Page
    When pengguna memilih opsi "Log In"
    And pengguna memasukkan Username dan Password yang valid pada Halaman Login
    And pengguna menekan tombol "Masuk"
    Then pengguna diarahkan ke halaman utama coach (Dashboard)