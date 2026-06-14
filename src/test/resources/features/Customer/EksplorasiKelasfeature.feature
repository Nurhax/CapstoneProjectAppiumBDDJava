@Customer
Feature: Eksplorasi Kelas dan Coach
  Sebagai customer
  Saya ingin melihat jadwal, memfilter kelas, dan melihat profil coach
  Agar saya bisa memilih kelas yoga yang sesuai sebelum melakukan booking
    
  @TC-06 @FR-04
  Scenario: Customer Melihat Daftar Jadwal Kelas Yoga Yang Tersedia Di Halaman Home(FR04)
    Given customer sudah login dan berada di halaman Home
    When customer menekan tab "Home"
    Then sistem menampilkan daftar jadwal kelas yoga yang tersedia

  @TC-07 @FR-05
  Scenario: Customer menggunakan filter saat mencari kelas di halaman home (FR05)
    Given customer sudah login dan berada di halaman Home
    And customer menekan tab "Home"
    When customer memilih filter "Hatha Yoga" pada tombol "Kelas"
    And customer memilih filter "Sabtu" pada tombol "Waktu"
    And customer memilih filter "iqbaltest" pada tombol "Coach"
    Then sistem hanya menampilkan daftar kelas yang sesuai dengan filter yang diterapkan

  @TC-08 @FR-05
  Scenario: Customer menggunakan filter saat mencari membership di halaman member (FR05)
    Given customer sudah login dan berada di halaman Home
    And customer menekan tab "Member"
    When customer memilih filter "Hatha Yoga" pada tombol "Kelas"
    Then sistem hanya menampilkan daftar kelas yang sesuai dengan filter yang diterapkan
    #Gak ada filter waktu dan coach karena jatohnya seperti voucher    

  @TC-09 @FR-18
  Scenario: Customer melihat profil detail seorang Coach di halaman home (FR18)
    Given customer sudah login dan berada di halaman Home
    And customer menekan tab "Home"
    When customer melihat salah satu kelas yang tersedia
    And customer menekan foto profil coach pada kelas yang dilihat
    Then sistem menampilkan halaman informasi detail profil coach tersebut

    