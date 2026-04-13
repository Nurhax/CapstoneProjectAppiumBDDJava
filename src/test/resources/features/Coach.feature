Feature: Modul Operasional Coach
  Sebagai Instruktur (Coach)
  Saya ingin melakukan absensi kehadiran peserta
  Agar data riwayat kehadiran kelas tercatat di sistem

  Scenario: Coach berhasil mencatat kehadiran peserta kelas (TC-COACH-01)
    Given coach sudah login ke dalam sistem
    And terdapat jadwal kelas yang memiliki peserta terdaftar
    And coach berada di Halaman Home
    When coach memilih kelas yang dituju
    And coach mencentang nama-nama peserta yang hadir
    Then sistem secara visual memindahkan peserta ke kolom hadir
    When coach menekan tombol "Update Kelas"
    Then sistem berhasil menyimpan data kehadiran
    And status daftar hadir kelas diperbarui di database