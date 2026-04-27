Feature: Modul Manajemen Admin
  Sebagai Admin sistem
  Saya ingin mengelola jadwal, peserta, dan data coach
  Agar operasional studio yoga berjalan lancar

  Scenario: Admin berhasil menambahkan jadwal kelas baru (TC-ADM-01)
    Given admin sudah login dengan hak akses "Role Admin"
    And admin berada di Halaman Menu Jadwal
    When admin menekan tombol "Tambah Jadwal"
    And admin mengisi form data kelas dengan informasi yang valid
    And admin menekan tombol "Simpan Kelas"
    Then sistem berhasil menyimpan data kelas
    And jadwal kelas baru muncul di dalam daftar jadwal

  Scenario: Admin berhasil mendaftarkan peserta ke kelas secara manual (TC-ADM-02)
    Given admin sudah login dengan hak akses "Role Admin"
    And terdapat minimal satu jadwal kelas yang aktif
    And admin berada di Halaman Menu Jadwal
    When admin memilih kelas dan menekan tombol "View Jadwal"
    And admin menekan tombol "Input Peserta Manual"
    And admin mengisi data peserta dengan informasi yang valid
    And admin menekan tombol "Simpan"
    Then sistem berhasil menyimpan data peserta
    And nama peserta terdaftar ke dalam jadwal kelas tersebut

  Scenario: Admin berhasil menambahkan data coach baru (TC-ADM-03)
    Given admin sudah login dengan hak akses "Role Admin"
    And admin berada di Halaman Data Coach
    When admin menekan tombol "Tambah Coach"
    And admin mengisi form data coach dengan informasi yang valid
    And admin menekan tombol "Simpan"
    Then sistem berhasil menyimpan data coach
    And profil coach baru muncul di dalam daftar direktori coach