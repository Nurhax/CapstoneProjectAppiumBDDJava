@Authentication
Feature: Autentikasi dan Registrasi Pengguna
  Sebagai pengguna aplikasi
  Saya ingin mendaftar dan masuk ke dalam sistem
  Agar saya bisa menggunakan fitur sesuai peran saya (Customer, Admin, atau Coach)

  @TC-01 @US-01
  Scenario: Pengguna Berhasil Registrasi Sebagai Customer
    Given pengguna belum terdaftar di sistem
    When pengguna mendaftar dengan username dan password yang valid
    Then sistem harus mengkonfirmasi pembuatan akun dan mengarahkan untuk login

  @TC-02 @US-02
  Scenario: Pengguna Berhasil Login Sebagai Customer
    Given seorang customer memiliki akun yang terdaftar dan aktif
    When customer melakukan login dengan kredensial yang valid
    Then customer harus diarahkan ke halaman utama (Dashboard) mereka

  @TC-03 @US-02
  Scenario: Pengguna Berhasil Login Sebagai Admin
    Given seorang admin memiliki akun yang terdaftar dan aktif
    When admin melakukan login dengan kredensial yang valid
    Then admin harus diarahkan ke halaman utama admin (Dashboard)

  @TC-04 @US-16
  Scenario: Pengguna Berhasil Mendaftarkan Coach Baru
    Given seorang admin sudah login ke sistem
    When admin menambahkan detail coach baru yang valid
    Then sistem harus mengkonfirmasi penambahan coach dan menampilkan notifikasi keberhasilan

  @TC-05 @US-02
  Scenario: Pengguna Berhasil Login Sebagai Coach
    Given seorang coach memiliki akun yang terdaftar dan aktif
    When coach melakukan login dengan kredensial yang valid
    Then coach harus diarahkan ke halaman utama coach (Dashboard)