# Studi Kasus: Sistem Manajemen Toko Ritel
Anda diminta untuk membuat sebuah sistem sederhana untuk mengelola data produk di sebuah toko ritel. Sistem ini harus mampu:
1. Menyimpan informasi dasar produk.
2. Menghitung harga akhir (diskon, pajak, dll.).
3. Mengelola berbagai jenis produk (misalnya, produk elektronik dan produk makanan) dengan fitur-fitur spesifik.

### Persyaratan Fungsional
- Setiap produk memiliki ID produk, nama, dan harga dasar.
- Anda harus membuat dua jenis produk:
    - **Elektronik**: Memiliki atribut tambahan yaitu **garansi** (dalam tahun).
    - **Makanan**: Memiliki atribut tambahan yaitu **tanggal kedaluwarsa**.
- Sistem harus dapat menghitung harga akhir produk dengan mempertimbangkan:
    - **Diskon**: Potongan 10% untuk produk makanan yang akan kedaluwarsa dalam 30 hari.
    - **Pajak**: Pajak sebesar 5% untuk semua produk elektronik.
- Anda harus dapat menampilkan detail lengkap dari setiap produk.

## Instruksi Pembuatan Kode
Ikuti langkah-langkah berikut untuk membangun program java:

### 1. Kelas Induk (Abstraksi & Enkapsulasi)
- Buatlah kelas abstrak bernama `Produk`. Kelas ini harus:
    - Memiliki atribut `idProduk`, `nama`, dan `hargaDasar`. Pastikan atribut ini dienkapsulasi (menggunakan `private`) dan sediakan getter dan setter.
    - Mempunyai konstruktor untuk menginisialisasi atribut-atribut tersebut.
    - Memiliki sebuah metode abstrak `hitungHargaAkhir()`. Metode ini akan diimplementasikan oleh kelas anak untuk menghitung harga setelah diskon atau pajak.
    - Memiliki metode ``tampilkanInfo()`` yang mencetak ID, nama, dan harga dasar `produk`.

### 2. Kelas Anak (Pewarisan & Polimorfisme)
- Buatlah dua kelas yang mewarisi (inheritance) dari kelas `Produk`:
    - `Elektronik`
        - Mewarisi dari `Produk`.
        - Memiliki atribut tambahan `garansi` (tipe `int`).
        - Mengimplementasikan metode `hitungHargaAkhir()` dari kelas induk. Dalam implementasi ini, tambahkan pajak 5% ke harga dasar.
        - Override metode ``tampilkanInfo()`` untuk juga menampilkan informasi garansi.

    - `Makanan`
        - Mewarisi dari `Produk`.
        - Memiliki atribut tambahan `tanggalKedaluwarsa` (gunakan `java.time.LocalDate`).
        - Mengimplementasikan metode `hitungHargaAkhir()` dari kelas induk. Dalam implementasi ini, periksa apakah tanggal kedaluwarsa kurang dari atau sama dengan 30 hari dari tanggal saat ini. Jika ya, terapkan diskon 10%. Jika tidak, harganya tetap sama dengan harga dasar.
        - Override metode `tampilkanInfo()` untuk juga menampilkan tanggal kedaluwarsa.

### 3. Kelas Utama (Main Class)
- Buatlah kelas `Main` dengan metode `main()` untuk menguji kode Anda:
    - Buat objek dari kelas `Elektronik` dan `Makanan`.
    - Panggil metode `tampilkanInfo()` untuk setiap objek untuk melihat detail produk.
    - Panggil metode `hitungHargaAkhir()` dan cetak hasilnya untuk setiap objek untuk memverifikasi perhitungan.

- Contoh Pengujian
    - Buat produk elektronik: Laptop, harga dasar 10000000, garansi 2 tahun.
    - Buat produk makanan: Roti, harga dasar 15000, tanggal kedaluwarsa 20 hari dari sekarang.
    - Buat produk makanan lain: Beras, harga dasar 50000, tanggal kedaluwarsa 60 hari dari sekarang.