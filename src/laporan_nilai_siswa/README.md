# Program Pengelola Nilai Siswa

**Tugas Pertemuan 06 Input/Output - Praktikum Pemograman Berorientasi Objek**

## Data Diri

| | |
|---|---|
| Nama | Faradilla Zahrotul Ashifa |
| NIM | L0325023 |
| Kelas | B |
| Program Studi | Informatika |
| Universitas | Universitas Sebelas Maret |

## Deskripsi Kasus

Dibuat sebuah program Java bergaya procedural/fungsional sederhana untuk mengelola data nilai siswa. Program bertugas:

1. Membaca data siswa (nama dan nilai) dari konsol menggunakan `Scanner`.
2. Menentukan status kelulusan setiap siswa. Nilai lebih dari atau sama dengan 70 berstatus **Lulus**, selain itu **Tidak Lulus**.
3. Menulis data setiap siswa beserta ringkasan (rata-rata kelas, jumlah lulus, dan jumlah tidak lulus) ke file teks `laporan_nilai.txt` menggunakan `FileWriter` dan `BufferedWriter`.
4. Membaca kembali isi file tersebut menggunakan `FileReader` dan `BufferedReader`, lalu menampilkannya ke konsol.
5. Menangani error file dengan `try-with-resources` dan `IOException`.

## Deskripsi Program

### Struktur Project

```
ParktikumOOP/
└── src/
    └── laporan_nilai_siswa/
        └── LaporanNilaiApp.java
```

File `laporan_nilai.txt` dibuat otomatis saat program dijalankan, dan tersimpan di folder utama project.

| Komponen | Keterangan |
|---|---|
| Package | `laporan_nilai_siswa` |
| Class | `LaporanNilaiApp` |

### Konstanta

| Nama | Nilai | Fungsi |
|---|---|---|
| `NAMA_FILE` | `laporan_nilai.txt` | Nama file output |
| `NILAI_KELULUSAN` | `70.0` | Batas nilai lulus |

### Penjelasan Method

| Method | Fungsi |
|---|---|
| `main` | Alur utama program: panggil `tulisLaporan`, lalu `bacaLaporan`, lalu tutup `Scanner`. |
| `tulisLaporan` | Membaca input tiap siswa, menentukan status, mengakumulasi total nilai dan jumlah lulus/tidak lulus, lalu menulis tabel dan ringkasan ke file. |
| `bacaLaporan` | Membuka file dan membaca baris demi baris dengan `readLine()` sampai `null`, lalu mencetaknya ke konsol. |
| `tentukanStatus` | Mengembalikan `"Lulus"` jika nilai >= 70, selain itu `"Tidak Lulus"`. |
| `bacaJumlahSiswa` | Meminta jumlah siswa dan mengulang input sampai valid (lebih dari 0). |
| `bacaNilai` | Meminta nilai dan mengulang input sampai valid (angka antara 0 sampai 100). |

### Alur Program

```
Input jumlah siswa
-> Loop tiap siswa: input nama dan nilai, tentukan status, tulis ke file
-> Tulis ringkasan ke file (writer otomatis tertutup)
-> Baca ulang file dan tampilkan ke konsol
```

### Penanganan Error dan Validasi

- Jumlah siswa harus lebih dari 0.
- Nilai harus berupa angka dan berada di rentang 0 sampai 100. Input salah akan diminta ulang.
- `BufferedWriter` dan `BufferedReader` memakai `try-with-resources`, sehingga otomatis tertutup.
- `IOException` ditangkap dan ditampilkan sebagai pesan error.

## Cara Menjalankan

**Lewat NetBeans:** klik kanan file `LaporanNilaiApp.java`, lalu pilih **Run File** (Shift+F6).

**Lewat terminal** (dari folder `src`):

```
javac laporan_nilai_siswa/LaporanNilaiApp.java
java laporan_nilai_siswa.LaporanNilaiApp
```

## Contoh Output

```
=== PROGRAM LAPORAN NILAI SISWA ===
Masukkan jumlah siswa: 3

Siswa ke-1
Nama  : Firly
Nilai : 85

Siswa ke-2
Nama  : Dino
Nilai : 62.5

Siswa ke-3
Nama  : Syawala
Nilai : 70

Laporan berhasil ditulis ke laporan_nilai.txt

=== ISI FILE laporan_nilai.txt ===
LAPORAN NILAI SISWA
========================================
No  | Nama       | Nilai  | Status
----+------------+--------+------------
1   | Firly      | 85.00  | Lulus
2   | Dino       | 62.50  | Tidak Lulus
3   | Syawala    | 70.00  | Lulus
========================================
RINGKASAN
Rata-rata nilai kelas : 72.50
Jumlah siswa lulus    : 2
Jumlah tidak lulus    : 1
=== SELESAI ===
```