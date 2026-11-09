# PustakaUNS - Sistem Perpustakaan Mini

Responsi Praktikum Pemrograman Berorientasi Objek (Java, aplikasi console).

## Data Diri

| | |
|---|---|
| Nama | Faradilla Zahrotul Ashifa |
| NIM |L0325023 |
| Kelas |3B |
| Program Studi |Informatika |
| Universitas | Universitas Sebelas Maret |
| Mata Kuliah | Praktikum Pemrograman Berorientasi Objek |
| Tugas | Responsi 1|

## Deskripsi Program

PustakaUNS adalah aplikasi console sederhana untuk mengelola perpustakaan program studi. Aplikasi ini dipakai untuk mengelola katalog buku, mendaftarkan anggota, serta mencatat peminjaman dan pengembalian buku lengkap dengan perhitungan denda. Data dapat disimpan ke file dan dimuat kembali pada sesi berikutnya.

Program dijalankan dari sebuah menu utama dan dirancang agar tidak berhenti mendadak ketika pengguna salah memasukkan data. Aplikasi dibuat dengan Java standar (JDK) tanpa library tambahan.

Konsep yang diterapkan: class dan object, konstanta, reference types, kondisional dan looping, String dan Character, exception dan assertion, array dan collection, serta Input/Output (console, file teks, dan serialization).

## Struktur Program

```
PustakaUNS/
|-- src/
|   `-- responsi/
|       |-- model/
|       |   |-- Buku.java
|       |   `-- Anggota.java
|       |-- exception/
|       |   |-- BukuTidakTersediaException.java
|       |   `-- BatasPinjamException.java
|       |-- service/
|       |   `-- Perpustakaan.java
|       |-- util/
|       |   |-- FileHandler.java
|       |   `-- Validator.java
|       `-- main/
|           `-- Main.java
|-- katalog.txt
|-- data.obj
`-- screenshot/
```

| Package | Class | Fungsi |
|---|---|---|
| responsi.model | Buku, Anggota | Menyimpan data buku dan anggota beserta daftar buku yang dipinjam |
| responsi.exception | BukuTidakTersediaException, BatasPinjamException | Custom exception untuk stok habis dan batas pinjam tercapai |
| responsi.service | Perpustakaan | Logika bisnis: konstanta, koleksi data, peminjaman, pengembalian, denda, antrian, rekap |
| responsi.util | Validator, FileHandler | Validasi dan pengolahan String, serta operasi baca dan tulis file |
| responsi.main | Main | Menu utama, input pengguna, dan penanganan exception |

Alur singkat: `Main` menerima input dan menampilkan menu, lalu memanggil `Perpustakaan` untuk memproses transaksi. `Perpustakaan` memakai `Buku` dan `Anggota` sebagai data, `Validator` untuk pengolahan teks, dan melempar custom exception bila transaksi tidak dapat dilakukan. `FileHandler` dipanggil `Main` untuk menyimpan dan memuat data.

## Fitur dan Penjelasan

| No | Menu | Penjelasan |
|---|---|---|
| 1 | Tambah Buku | Menambah buku baru. Judul dirapikan otomatis (spasi tepi dihapus, huruf pertama tiap kata kapital). Program menampilkan kode verifikasi (kode buku dibalik), jumlah vokal judul, serta jumlah huruf dan angka pada kode. Kode ganda ditolak. |
| 2 | Tampilkan Katalog | Menampilkan seluruh buku dalam bentuk tabel, jumlah judul, dan daftar kategori unik tanpa duplikat. |
| 3 | Cari Buku (judul) | Mencari buku berdasarkan sebagian judul tanpa membedakan huruf besar dan kecil. |
| 4 | Daftarkan Anggota | Mendaftarkan anggota dengan ID berformat AGT diikuti tiga digit (contoh AGT001). ID yang formatnya salah ditolak dan pengguna diminta mengulang. |
| 5 | Pinjam Buku | Meminjam buku dengan batas maksimal 3 buku per anggota. Bila batas tercapai, program menampilkan pesan gagal. Bila stok buku 0, anggota masuk antrian dan posisinya ditampilkan. Sebelum memilih, program menampilkan daftar buku tersedia, dan transaksi meminta konfirmasi y/n. |
| 6 | Kembalikan Buku | Mengembalikan buku dan menghitung denda berdasarkan lama keterlambatan. Bila ada anggota yang mengantri untuk buku tersebut, anggota terdepan mendapat notifikasi. |
| 7 | Rekap Peminjaman Mingguan | Menampilkan jumlah peminjaman per hari (Senin sampai Minggu), total transaksi, dan hari tersibuk. |
| 8 | Simpan Data | Menyimpan katalog ke `katalog.txt` dan daftar anggota ke `data.obj`. |
| 9 | Muat Data | Memuat kembali data dari kedua file. Bila file belum ada, program menampilkan pesan dan tidak berhenti. |
| 10 | Keluar | Mengakhiri program, dengan pilihan menyimpan data terlebih dahulu. |
| 11 | Demo Reference dan Assertion | Menampilkan demo reference types dan contoh assertion berhasil dan gagal (tambahan di luar soal). |

### Aturan Denda

| Keterlambatan | Denda |
|---|---|
| Tidak terlambat | Rp0 |
| 1 sampai 7 hari | hari x Rp1.000 |
| 8 sampai 14 hari | hari x Rp2.000 |
| Lebih dari 14 hari | hari x Rp3.000, disertai peringatan anggota diblokir sementara |

Lama pinjam standar adalah 7 hari. Lama keterlambatan diisi manual saat pengembalian.

### Penanganan Kesalahan

- Input huruf pada isian angka ditangkap dan menu tampil kembali.
- Stok habis dan batas pinjam ditangani dengan custom exception, dan pesan "Transaksi selesai." selalu tampil melalui blok finally.
- File yang belum ada atau isinya rusak ditangani dengan pesan yang ramah.
- Assertion dipakai untuk mendeteksi bug logika (judul null, stok negatif, pinjaman melebihi batas) dan hanya aktif bila program dijalankan dengan opsi `-ea`.

### Format File

`katalog.txt` berisi satu buku per baris, dipisah tanda titik koma:

```
kode;judul;pengarang;kategori;tahun;stok
```

`data.obj` berisi daftar anggota hasil serialization dan tidak dapat dibaca sebagai teks biasa.

## Cara Menjalankan

Dengan NetBeans: buka project, atur Main Class ke `responsi.main.Main`, isi `-ea` pada VM Options (Properties > Run), lalu jalankan.

Dengan terminal, dari folder PustakaUNS:

```
mkdir out
javac -d out src/responsi/model/*.java src/responsi/exception/*.java src/responsi/service/*.java src/responsi/util/*.java src/responsi/main/*.java
java -ea -cp out responsi.main.Main
```

Opsi `-ea` mengaktifkan assertion. File `katalog.txt` dan `data.obj` dibuat di folder tempat program dijalankan.

## Pengujian

Program diuji dengan 10 skenario wajib dari soal (validasi ID, normalisasi judul, batas pinjam, stok habis dan antrian, denda 10 dan 16 hari, input huruf pada angka, simpan dan muat data, file belum ada, dan assertion). Bukti pengujian ada pada folder `screenshot` dan laporan.