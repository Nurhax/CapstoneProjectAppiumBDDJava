@Admin
Feature: Manajemen Pelaporan dan Analitik oleh Admin
  Sebagai seorang admin sistem
  Saya ingin melihat data kehadiran, mengelola pendapatan, menghitung insentif coach, serta memantau analitik studio
  Agar pembukuan keuangan dan performa bisnis studio yoga dapat dipantau secara akurat

  @TC-30 @US-24
  Scenario: Admin memantau rekaman log data kehadiran peserta kelas
    Given admin telah login ke dalam sistem
    When admin memantau rekaman data dari suatu kelas yang sudah berjalan
    Then sistem menampilkan log data kehadiran seluruh peserta pada kelas tersebut

  @TC-31 @US-30
  Scenario: Admin memantau direktori kumpulan data pelanggan aktif
    Given admin telah login ke dalam sistem
    When admin membuka direktori manajemen pelanggan
    Then sistem berhasil menampilkan kumpulan rekaman data pelanggan yang aktif secara lengkap

  @TC-32 @US-31
  Scenario: Admin menambahkan nominal pendapatan tambahan melalui detail profil coach
    Given admin telah login ke dalam sistem
    When admin menginput nominal pendapatan tambahan untuk seorang coach
    Then sistem memperbarui total saldo pendapatan milik coach yang bersangkutan

  @TC-33 @US-31
  Scenario: Admin menginput langsung data transaksi pendapatan studio melalui menu profil
    Given admin telah login ke dalam sistem
    When admin menginput transaksi pendapatan operasional studio baru
    Then sistem memperbarui akumulasi total pendapatan operasional studio yoga

  @TC-34 @US-32
  Scenario: Admin memantau visualisasi grafik performa bisnis pada ringkasan analitik
    Given admin telah login ke dalam sistem
    When admin memantau halaman ringkasan keuangan
    Then sistem berhasil menampilkan visualisasi grafik performa bisnis dan analitik studio

  @TC-35 @US-34
  Scenario: Admin mengunduh rangkuman dokumen cetak laporan keuangan pendapatan
    Given admin telah login ke dalam sistem
    When admin meminta cetak dokumen laporan keuangan pendapatan
    Then sistem otomatis mengunduh berkas rangkuman pendapatan berdasarkan rentang waktu yang dipilih