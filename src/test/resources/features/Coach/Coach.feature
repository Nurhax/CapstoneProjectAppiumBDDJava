@Coach
Feature: Operasional dan Manajemen Coach
  Sebagai seorang coach
  Saya ingin melihat jadwal kelas, memverifikasi peserta, menunggah bukti kelas dan mengecek gaji saya
  Agar saya dapat mengajar kelas dan melacak pendapatan dengan baik

  @TC-13 @US-17
  Scenario: Coach melihat jadwal mengajar hari ini (US17)
    Given seorang coach sudah login ke sistem
    When coach meminta untuk melihat jadwal mengajar hari ini
    Then sistem harus menampilkan daftar kelas yang dijadwalkan untuk coach tersebut hari ini

  @TC-14 @US-20
  Scenario: Coach melihat daftar peserta pada kelas yang akan diajar (US20)
    Given seorang coach sudah login ke sistem dan melihat jadwal kelasnya
    When coach memilih kelas yang akan diajar
    Then sistem harus menampilkan daftar peserta yang terdaftar untuk kelas tersebut
  
  @TC-15 @US-21 @US-24
  Scenario: Coach memverifikasi kehadiran peserta kelas (US21)
    Given seorang coach sudah login dan melihat daftar peserta untuk kelas yang akan diajar
    When coach menandai peserta sebagai 'Hadir'
    Then sistem harus memperbarui status kehadiran peserta tersebut menjadi 'Hadir'
    
  @TC-16 @US-23 @US-25
  Scenario: Coach upload foto bukti kehadiran kelas (US23)
    Given seorang coach sudah login dan telah memverifikasi kehadiran peserta kelas
    When coach mengunggah foto sebagai bukti kehadiran kelas
    Then sistem harus menyimpan bukti kehadiran and memperbarui status kelas
  
  @TC-17 @US-27
  Scenario: Coach melihat laporan gaji (US27)
    Given coach sudah login dan berada di halaman coach
    When coach menekan tab "Profile" pada navbar
    Then coach dapat melihat laporan gaji dari kelas yang telah selesai