@Coach
Feature: Operasional dan Manajemen Coach
  Sebagai seorang coach
  Saya ingin melihat jadwal kelas, memverifikasi peserta, menunggah bukti kelas dan mengecek gaji saya
  Agar saya dapat mengajar kelas dan melacak pendapatan dengan baik 

  @TC-13 @FR-17
  Scenario: Coach melihat jadwal mengajar hari ini (FR17)
    Given coach sudah login dan berada di halaman coach 
    When coach menekan tab "Beranda" pada navbar 
    Then coach dapat melihat jadwal kelas yang harus diajar hari ini 

  @TC-14 @FR-20
  Scenario: Coach melihat daftar peserta pada kelas yang akan diajar (FR20)
    Given coach sudah login dan berada di halaman coach 
    And coach menekan tab "Beranda" pada navbar 
    When coach memilih jadwal kelas yang tersedia hari ini 
    Then coach dapat melihat daftar peserta untuk kelas tersebut 
  
  @TC-15 @FR-21 @FR-24
  Scenario: Coach memverifikasi kehadiran peserta kelas (FR21)
    Given coach sudah login dan berada di halaman coach 
    And coach menekan tab "Beranda" pada navbar 
    And coach memilih jadwal kelas yang tersedia hari ini
    When coach mengklik logo checklist pada peserta di bagian "Tidak Hadir" 
    Then data peserta tersebut berubah menjadi ke bagian "Hadir"
    
  @TC-16 @FR-23 @FR-25
  Scenario: Coach upload foto bukti kehadiran kelas (FR23)
    Given coach sudah login dan berada di halaman daftar peserta kelas
    When coach menekan tombol select file pada upload bukti hadir
    And coach memilih foto bukti kelas
    And foto berhasil tersimpan ke dalam form bukti hadir
    And coach menekan tombol "Update Kelas"
    Then sistem berhasil menyimpan pembaruan kelas beserta bukti kehadiran
  
   @TC-17 @FR-27  
   Scenario: Coach melihat laporan gaji (FR27)
    Given coach sudah login dan berada di halaman coach
    When coach menekan tab "Profil" pada navbar
    Then coach dapat melihat laporan gaji dari kelas yang telah selesai