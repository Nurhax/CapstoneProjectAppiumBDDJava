@Admin
Feature: Manajemen Pelaporan dan Analitik oleh Admin
  Sebagai seorang admin sistem
  Saya ingin melihat data kehadiran, mengelola pendapatan, menghitung insentif coach, serta memantau analitik studio
  Agar pembukuan keuangan dan performa bisnis studio yoga dapat dipantau secara akurat

  @TC-30 @FR-24
  Scenario: Admin memantau rekaman log data kehadiran peserta kelas
    Given admin berada di halaman dashboard admin
    When admin memilih opsi "Beranda" pada navbar
    And admin memilih salah satu jadwal kelas aktif
    And admin menekan tombol "Lihat Jadwal"
    Then sistem menampilkan data rekaman kehadiran peserta pada kelas tersebut

  @TC-31 @FR-30
  Scenario: Admin memantau direktori kumpulan data pelanggan aktif
    Given admin berada di halaman dashboard admin
    When admin memilih opsi "Pelanggan" pada navbar
    Then sistem berhasil menampilkan kumpulan rekaman data pelanggan secara lengkap

  #@TC-32 @FR-31
  #Scenario: Admin menambahkan nominal pendapatan tambahan melalui detail profil coach
    #Given admin berada di halaman dashboard admin
    #When admin memilih opsi "Coach" pada navbar
    #And admin menekan logo edit pada salah satu data coach
    #And admin menekan tombol "Pendapatan"
    #And admin memasukkan nominal data pendapatan kustom
    #And admin menekan tombol "Tambah Pendapatan"
    #Then sistem memperbarui total saldo pendapatan milik coach yang bersangkutan

  #@TC-33 @FR-31
  #Scenario: Admin menginput langsung data transaksi pendapatan studio melalui menu profil
    #Given admin berada di halaman dashboard admin
    #When admin memilih opsi "Keuangan" pada navbar
    #And admin memasukkan nominal data pendapatan kustom
    #And admin menekan tombol "Tambah Pendapatan"
    #Then sistem memperbarui akumulasi total pendapatan operasional studio yoga

  @TC-34 @FR-32
  Scenario: Admin memantau visualisasi grafik performa bisnis pada ringkasan analitik
    Given admin berada di halaman dashboard admin
    When admin memilih opsi "Keuangan" pada navbar
    Then sistem berhasil menampilkan grafik dan komponen dashboard analytic secara berkala

  @TC-35 @FR-34
  Scenario: Admin mengunduh rangkuman dokumen cetak laporan keuangan pendapatan
    Given admin berada di halaman dashboard admin
    When admin memilih opsi "Keuangan" pada navbar
    And admin menekan tombol "CETAK PDF"
    Then sistem otomatis mengunduh berkas file data rangkuman pendapatan berdasarkan rentang waktu tertentu