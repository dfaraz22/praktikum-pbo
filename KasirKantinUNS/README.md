# Sistem Kasir Kantin UNS

## Identitas

| | |
|---|---|
| Nama | Faradilla Zahrotul Ashifa |
| NIM | L0325023 |
| Kelas | 3B |
| Program Studi | Informatika |
| Mata Kuliah | Pemrograman Berorientasi Objek (PBO) |

## Deskripsi Program

Sistem Kasir Kantin UNS adalah aplikasi kasir sederhana berbasis Java yang dibuat untuk memenuhi praktikum PBO pertemuan 11 dan 12. Program ini menerapkan enkapsulasi, multithreading, dan GUI Programming menggunakan Java Swing.

Pengguna memasukkan nama menu, harga, dan jumlah pesanan. Program menghitung subtotal dan total pembayaran. Jika subtotal lebih dari Rp50.000, pelanggan mendapat diskon 10%. Program juga memiliki simulasi dua kasir yang melayani pelanggan secara bersamaan dan simulasi pencetakan struk yang berjalan pada thread terpisah.

## Struktur Project

Seluruh class berada dalam package `lab.kasir`.

```text
KasirKantinUNS/
├── src/
│   └── lab/
│       └── kasir/
│           ├── Pesanan.java
│           ├── Kasir.java
│           ├── MainKasir.java
│           └── KasirFrame.java
├── screenshots/
├── nbproject/
├── build.xml
├── manifest.mf
└── README.md
```

## Penjelasan Class

### Pesanan.java

Class ini mengelola perhitungan transaksi. Atribut `subtotal` bersifat `private` sebagai penerapan enkapsulasi, sehingga nilainya hanya dapat diakses melalui method di dalam class.

- `tambahItem()` menambahkan hasil perkalian harga dan jumlah ke subtotal.
- `getSubtotal()` mengembalikan nilai subtotal.
- `hitungTotal()` menghitung total pembayaran. Jika subtotal lebih dari Rp50.000, total dikurangi diskon 10%.
- `reset()` mengembalikan subtotal menjadi nol.

### Kasir.java

Class ini merupakan turunan dari class `Thread` dan digunakan untuk menyimulasikan kasir yang melayani pelanggan. Setiap objek kasir memiliki nama.

Method `run()` berisi proses melayani lima pelanggan. Setiap pelanggan dilayani dengan menampilkan nama kasir dan nomor pelanggan, kemudian `Thread.sleep(500)` memberikan jeda 500 milidetik.

### MainKasir.java

Class ini adalah class utama yang dijalankan pertama kali. Isinya terbagi menjadi tiga bagian:

1. Pengujian class `Pesanan` dengan data Nasi Goreng, Es Teh, dan Ayam Bakar.
2. Simulasi dua kasir, yaitu Dina dan Eko, yang dijalankan dengan `start()`. Method `join()` dipakai agar program menunggu kedua thread selesai sebelum menampilkan pesan bahwa seluruh pelanggan selesai dilayani.
3. Membuka jendela aplikasi `KasirFrame` melalui `SwingUtilities.invokeLater()`.

### KasirFrame.java

Class ini membuat tampilan aplikasi menggunakan Java Swing dan merupakan turunan dari `JFrame`. Komponen disusun menggunakan `setBounds()` dengan tema warna biru muda dan putih.

Komponen yang digunakan:

- `JLabel` untuk judul, keterangan, total bayar, dan status.
- `JTextField` untuk input nama menu, harga, dan jumlah.
- `JButton` untuk tombol Tambah, Hitung Total, dan Cetak Struk.
- `JTextArea` di dalam `JScrollPane` untuk menampilkan struk.
- `JOptionPane` untuk menampilkan pesan peringatan.

Pengelolaan data pesanan:

- Setiap pesanan yang ditambahkan disimpan sebagai objek `ItemPesanan` (inner class berisi nama, harga, dan jumlah) di dalam `ArrayList`. Daftar ini digunakan untuk menampilkan rincian pada struk.
- Perhitungan subtotal dan total tetap dilakukan oleh class `Pesanan`.
- Setiap jendela memiliki nomor transaksi otomatis dengan format `TRX-001`, `TRX-002`, dan seterusnya.

Method pada class ini:

- `tambahPesanan()` memvalidasi input, menambahkan item ke pesanan, lalu mengosongkan kolom input.
- `tampilkanDaftarSementara()` menampilkan daftar pesanan sementara pada area struk setiap kali item ditambahkan.
- `hitungTotal()` menghitung total pembayaran dan menampilkannya pada label total.
- `buatIsiStruk()` menyusun isi struk lengkap.
- `cetakStruk()` menjalankan simulasi pencetakan menggunakan thread.

## Fitur Program

1. Input nama menu, harga, dan jumlah pesanan.
2. Perhitungan subtotal otomatis untuk setiap item yang ditambahkan.
3. Diskon 10% jika subtotal lebih dari Rp50.000.
4. Daftar pesanan sementara pada area struk.
5. Struk lengkap berisi identitas Kantin UNS, nomor transaksi, tanggal dan waktu, daftar pesanan, subtotal, diskon, dan total bayar.
6. Validasi input menggunakan `JOptionPane`:
   - Semua kolom wajib diisi.
   - Harga dan jumlah harus lebih dari 0.
   - Harga harus berupa angka dan jumlah harus berupa bilangan bulat.
   - Pesanan tidak boleh kosong saat menghitung total atau mencetak struk.
7. Jika tombol Cetak Struk ditekan sebelum Hitung Total, total dihitung otomatis terlebih dahulu.
8. Simulasi pencetakan struk dengan status yang berubah selama proses.
9. Simulasi dua kasir yang bekerja bersamaan.

## Implementasi Materi Praktikum

### Pertemuan 11 - Multithreading

Multithreading diterapkan pada dua bagian.

1. **Simulasi dua kasir.** Class `Kasir` mewarisi `Thread`. Kasir Dina dan Kasir Eko dijalankan dengan `start()`, masing-masing melayani lima pelanggan dengan jeda `sleep()` 500 milidetik. Method `join()` digunakan agar program utama menunggu kedua kasir selesai.
2. **Simulasi cetak struk.** Saat tombol Cetak Struk ditekan, sebuah thread baru dibuat untuk menunggu selama dua detik. Selama proses ini tombol Cetak Struk dinonaktifkan dan status berubah menjadi "Mencetak struk...". GUI tetap responsif karena proses menunggu tidak berjalan pada thread utama. Setelah dua detik, perubahan tampilan dikembalikan ke thread GUI melalui `SwingUtilities.invokeLater()`, kemudian status menjadi "Struk selesai" dan struk lengkap ditampilkan.

### Pertemuan 12 - GUI Programming

GUI dibuat dengan Java Swing menggunakan `JFrame`, `JLabel`, `JTextField`, `JTextArea`, `JButton`, `JScrollPane`, dan `JOptionPane`. Setiap tombol memiliki event handler (`ActionListener`) yang memanggil method tambah pesanan, hitung total, atau cetak struk. Kesalahan format angka ditangani menggunakan `try-catch` pada `NumberFormatException`.

## Cara Menjalankan Program

1. Buka project `KasirKantinUNS` di NetBeans.
2. Pastikan keempat class berada dalam package `lab.kasir`.
3. Jalankan `MainKasir.java` (klik kanan, pilih Run File).
4. Lihat hasil pengujian dan simulasi dua kasir pada tab Output.
5. Setelah simulasi selesai, jendela Kasir Kantin UNS akan terbuka.
6. Isi nama menu, harga (angka tanpa titik atau koma), dan jumlah, lalu tekan **Tambah**.
7. Tekan **Hitung Total** untuk melihat total pembayaran.
8. Tekan **Cetak Struk** untuk menjalankan simulasi pencetakan.

## Output Program

### 1. Pengujian Perhitungan (Tahap 1)

Data pengujian:

| Menu | Harga | Jumlah |
|---|---:|---:|
| Nasi Goreng | Rp15.000 | 2 |
| Es Teh | Rp5.000 | 3 |
| Ayam Bakar | Rp25.000 | 1 |

Hasil perhitungan:

- Setelah Nasi Goreng, subtotal Rp30.000.
- Setelah Es Teh, subtotal Rp45.000 dan belum mendapat diskon.
- Setelah Ayam Bakar, subtotal Rp70.000. Diskon 10% sebesar Rp7.000, sehingga total bayar Rp63.000.

### 2. Simulasi Dua Kasir (Tahap 2)

Output NetBeans menampilkan Kasir Dina dan Kasir Eko yang melayani pelanggan ke-1 sampai ke-5. Urutan tampilan keduanya dapat berbeda pada setiap eksekusi karena kedua thread berjalan bersamaan. Setelah keduanya selesai, program menampilkan pesan "Semua pelanggan selesai dilayani".

![Output NetBeans](screenshots/output-netbeans.png)

### 3. Tampilan GUI (Tahap 3)

Jendela aplikasi berisi form input pesanan, tombol Tambah dan Hitung Total, area struk, label total bayar, tombol Cetak Struk, dan label status.

Tampilan setelah beberapa pesanan ditambahkan:

![Tampilan GUI setelah pesanan ditambahkan](screenshots/gui-pesanan.png)

Tampilan setelah tombol Hitung Total ditekan:

![Tampilan GUI setelah hitung total](screenshots/gui-total.png)

### 4. Cetak Struk dengan Thread (Tahap 4)

Saat tombol Cetak Struk ditekan, status berubah menjadi "Mencetak struk...". Setelah dua detik, status menjadi "Struk selesai" dan area struk menampilkan struk lengkap.

Tampilan saat proses mencetak:

![Status mencetak struk](screenshots/gui-mencetak.png)

Tampilan struk setelah selesai dicetak:

![Struk pembelian](screenshots/struk-pembelian.png)

## Catatan

Fitur Cetak Struk merupakan simulasi menggunakan multithreading dan belum terhubung dengan printer fisik.
