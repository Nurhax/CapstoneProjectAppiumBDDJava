@Auth
Feature: User Authentication
  Sebagai pengguna aplikasi
  Saya ingin bisa mendaftar dan masuk ke dalam sistem sesuai dengan role/peran saya
  Agar saya bisa menggunakan fitur aplikasi yoga

  @TC-01 @FR-01
  Scenario: Pengguna Berhasil Registrasi Sebagai Customer (FR01) 
    Given pengguna belum memiliki akun
    And pengguna berada di landing page
    When pengguna memilih opsi "Register"
    And pengguna memasukkan username, nomer telpon, dan password yang valid pada halaman register
    And pengguna menekan tombol "Buat Akun"
    Then sistem menampilkan notifikasi "Akun berhasil dibuat! Silakan login."

  @TC-02 @FR-02
  Scenario: Pengguna Berhasil Login Sebagai Customer (FR02)
    Given pengguna sudah memiliki akun yang terdaftar dan aktif
    And pengguna berada di landing page
    When pengguna memilih opsi "Log In"
    And pengguna memasukkan username dan password yang valid pada halaman login
    And pengguna menekan tombol "Masuk"
    And pengguna diarahkan ke halaman utama (Dashboard)
  
  @TC-03 @FR-02
  Scenario: Pengguna Berhasil Login Sebagai Admin (FR02)
    Given pengguna sudah memiliki akun yang terdaftar dan aktif dari developer
    And pengguna berada di landing page
    When pengguna memilih opsi "Log In" 
    And pengguna memasukkan username dan password yang valid pada halaman login
    And pengguna menekan tombol "Masuk"
    Then pengguna diarahkan ke halaman utama admin (Dashboard)
  
  @TC-03 @FR-16
  Scenario: Pengguna Berhasil Mendaftarkan Coach Baru (FR-16)
    Given pengguna sudah memiliki akun yang terdaftar dan aktif dari developer
    And pengguna berada di landing page
    When pengguna memilih opsi "Log In"
    And pengguna memasukkan username dan password yang valid pada halaman login
    And pengguna menekan tombol "Masuk"
    Then pengguna diarahkan ke halaman utama admin (Dashboard)
    And pengguna menekan tab "Coach"
    And pengguna menekan tombol "Tambah Coach"
    And pengguna mengisi nama coach, keahlian, nomor hp, deskripsi, rate per kelas, pengalaman tahun dan password
    And pengguna menekan tombol "Tambah Coach" lagi
    Then sistem menampilkan notifikasi "Coach berhasil ditambahkan!" 
  
   @TC-05 @FR-02  
   Scenario: Pengguna Berhasil Login Sebagai Coach (FR02)
    Given pengguna sudah memiliki akun yang terdaftar dan aktif dari admin
    And pengguna berada di landing page
    When pengguna memilih opsi "Log In"
    And pengguna memasukkan username dan password yang valid pada halaman login
    And pengguna menekan tombol "Masuk"
    Then pengguna diarahkan ke halaman utama coach (Dashboard)