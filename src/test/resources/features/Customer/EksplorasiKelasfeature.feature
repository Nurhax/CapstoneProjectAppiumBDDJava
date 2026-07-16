@Customer
Feature: Eksplorasi Kelas dan Coach
  Sebagai customer
  Saya ingin melihat jadwal, memfilter kelas, dan melihat profil coach
  Agar saya bisa memilih kelas yoga yang sesuai sebelum melakukan booking
    
  @TC-06 @US-04
  Scenario: Customer Melihat Daftar Jadwal Kelas Yoga Yang Tersedia Di Halaman Home(US04)
    Given customer sudah login dan berada di halaman Home
    When customer membuka daftar jadwal kelas
    Then sistem menampilkan daftar jadwal kelas yoga yang tersedia secara real-time

  @TC-07 @US-05
  Scenario: Customer menggunakan filter saat mencari kelas di halaman home (US05)
    Given customer berada di halaman daftar jadwal kelas
    When customer menerapkan filter berdasarkan kelas, waktu, dan coach
    Then sistem hanya menampilkan jadwal yang sesuai dengan kriteria filter yang diterapkan

  @TC-08 @US-05
  Scenario: Customer menggunakan filter saat mencari membership di halaman member (US05)
    Given customer berada di halaman membership
    When customer menerapkan filter berdasarkan jenis kelas
    Then sistem hanya menampilkan paket membership yang sesuai dengan kriteria filter

  @TC-09 @US-18
  Scenario: Customer melihat profil detail seorang Coach di halaman home (US18)
    Given customer sedang melihat daftar jadwal kelas yang tersedia 
    When customer memilih untuk melihat profil salah satu coach 
    Then sistem menampilkan halaman profil coach beserta nama, spesialisasi, dan jadwal mengajarnya.