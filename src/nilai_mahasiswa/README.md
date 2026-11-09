# Program Pengelola Nilai Mahasiswa Berorientasi Objek

**Tugas Pengganti Pertemuan 7**

## Data Diri

| | |
|---|---|
| Nama | Faradilla Zahrotul Ashifa |
| NIM | L0325023 |
| Kelas | B |
| Program Studi | Informatika |
| Universitas | Universitas Sebelas Maret |

## Materi yang Digunakan

| Pertemuan | Materi | Penerapan dalam program |
|---|---|---|
| 2 | Package, Class, Constructor, Variabel | Pembagian package `model`, `exception`, `service`, `app`; class `Mahasiswa` dengan constructor; konstanta `final` |
| 3 | Kondisional, Looping, Primitive dan Reference Types | If-else untuk grade dan status; for-each untuk menghitung statistik; tipe `double` dan `boolean` (primitif) serta `Mahasiswa` dan `String` (reference) |
| 4 | Exception Handling, Assertion, Character, String | `NilaiInvalidException`, `assert` pada constructor, `Character.isDigit()`, `trim()`, `toUpperCase()` |
| 5 | Array dan Collection Framework | `ArrayList<Mahasiswa>` dengan `add()`, `size()`, dan for-each |
| 6 | Input/Output dan File Handling | `Scanner`, `BufferedWriter`, `FileWriter`, `BufferedReader`, `FileReader`, try-with-resources |

## Deskripsi Kasus

Dibuat program Java berorientasi objek untuk mengelola data nilai mahasiswa. Program membaca data mahasiswa dari konsol, memvalidasi input, menyimpan seluruh data dalam sebuah koleksi, menghitung statistik kelas, lalu menyimpan rekapitulasinya secara permanen ke file teks `laporan_nilai.txt`. Isi file kemudian dibaca kembali dan ditampilkan di konsol sebagai bukti data tersimpan.

## Fitur Program

1. Input data beberapa mahasiswa (NIM, nama, nilai) lewat konsol.
2. Validasi NIM: tidak boleh kosong, hanya berisi angka, dan tidak boleh duplikat.
3. Validasi nama: tidak boleh kosong. Nama dirapikan otomatis menjadi huruf kapital tanpa spasi berlebih.
4. Validasi nilai: harus angka dan berada pada rentang 0 sampai 100. Di luar rentang tersebut, program melempar `NilaiInvalidException`.
5. Penentuan grade huruf dan status kelulusan untuk setiap mahasiswa.
6. Perhitungan statistik kelas: jumlah mahasiswa, rata-rata nilai, jumlah lulus, dan jumlah tidak lulus.
7. Penyimpanan laporan ke file `laporan_nilai.txt` dan pembacaan ulang file ke konsol.
8. Penanganan error file dengan `IOException`.

## Aturan Grade dan Kelulusan

| Nilai | Grade | Status |
|---|---|---|
| 85 sampai 100 | A | Lulus |
| 75 sampai kurang dari 85 | B | Lulus |
| 70 sampai kurang dari 75 | C | Lulus |
| 0 sampai kurang dari 70 | D | Tidak Lulus |

Batas kelulusan disimpan pada konstanta `FINAL_PASSING_GRADE = 70.0`.

## Struktur Project

```
ParktikumOOP/
└── src/
    └── nilai_mahasiswa/
        ├── model/
        │   └── Mahasiswa.java
        ├── exception/
        │   └── NilaiInvalidException.java
        ├── service/
        │   └── LaporanService.java
        └── app/
            └── MainApp.java
```

## Penjelasan per Package

### `nilai_mahasiswa.model`

Berisi class data.

**Mahasiswa**
- Atribut `private final`: `nama` (String), `nim` (String), `nilai` (double).
- Konstanta `FINAL_PASSING_GRADE = 70.0`.
- Constructor menerima nama, NIM, dan nilai. Nama dan NIM dirapikan dengan `trim()`, nama diubah ke huruf kapital dengan `toUpperCase()`. Terdapat `assert` untuk memastikan NIM tidak kosong (aktif jika program dijalankan dengan opsi `-ea`).
- `getGrade()` memetakan nilai ke huruf A, B, C, atau D.
- `isLulus()` mengembalikan `true` jika nilai lebih dari atau sama dengan batas kelulusan.
- `getStatus()` mengembalikan teks "Lulus" atau "Tidak Lulus".
- Getter untuk nama, NIM, dan nilai.

### `nilai_mahasiswa.exception`

Berisi exception buatan sendiri.

**NilaiInvalidException**
- Turunan dari `Exception` (checked exception), sehingga wajib ditangani oleh pemanggilnya.
- Dilempar ketika nilai kurang dari 0 atau lebih dari 100, dengan pesan yang menyebutkan nilai yang salah.

### `nilai_mahasiswa.service`

Berisi logika pengolahan data dan file.

**LaporanService** (seluruh method bersifat `static`)

| Method | Fungsi |
|---|---|
| `hitungRataRata` | Menelusuri daftar dengan for-each, menjumlahkan nilai, lalu membaginya dengan `size()`. Mengembalikan 0 jika daftar kosong. |
| `hitungJumlahLulus` | Menelusuri daftar dan menghitung mahasiswa yang `isLulus()`. |
| `tulisLaporan` | Menulis tabel mahasiswa dan ringkasan statistik ke file menggunakan `BufferedWriter` dan `FileWriter` dalam try-with-resources. |
| `bacaLaporan` | Membaca file baris demi baris menggunakan `BufferedReader` dan `FileReader`, lalu mencetaknya ke konsol. |

### `nilai_mahasiswa.app`

Berisi class utama yang dijalankan.

**MainApp**

| Method | Fungsi |
|---|---|
| `main` | Alur utama program: membuat `Scanner` dan `ArrayList`, membaca data seluruh mahasiswa, menulis laporan, lalu membaca dan menampilkan laporan. |
| `bacaJumlah` | Meminta jumlah mahasiswa dan mengulang input sampai berupa angka bulat lebih dari 0. |
| `bacaNim` | Meminta NIM dan mengulang input jika kosong, mengandung karakter non-angka (dicek dengan `Character.isDigit()`), atau sudah terdaftar. |
| `bacaNama` | Meminta nama dan mengulang input jika kosong. |
| `bacaNilai` | Meminta nilai, mengulang input jika bukan angka, dan melempar `NilaiInvalidException` jika di luar rentang 0 sampai 100. |

## Alur Program

```
Mulai
  |
Input jumlah mahasiswa (diulang sampai valid)
  |
Untuk setiap mahasiswa:
  - Input NIM   (validasi kosong, angka, duplikat)
  - Input nama  (validasi kosong)
  - Input nilai (jika di luar 0 - 100: NilaiInvalidException ditangkap, input diulang)
  - Buat objek Mahasiswa, tambahkan ke ArrayList
  |
Hitung statistik (rata-rata, jumlah lulus, jumlah tidak lulus)
  |
Tulis laporan ke laporan_nilai.txt
  |
Baca ulang laporan_nilai.txt dan tampilkan ke konsol
  |
Selesai
```

## Penanganan Error

| Kondisi | Penanganan |
|---|---|
| Jumlah mahasiswa bukan angka atau tidak lebih dari 0 | Pesan error, input diulang |
| NIM kosong, bukan angka, atau duplikat | Pesan error, input diulang |
| Nama kosong | Pesan error, input diulang |
| Nilai bukan angka | `NumberFormatException` ditangkap, input diulang |
| Nilai kurang dari 0 atau lebih dari 100 | `NilaiInvalidException` dilempar dan ditangkap, input diulang |
| NIM kosong lolos ke constructor | `AssertionError` jika dijalankan dengan `-ea` |
| File gagal ditulis atau dibaca | `IOException` ditangkap, pesan error ditampilkan |

## Cara Menjalankan

**Lewat NetBeans:**
1. Aktifkan assertion: klik kanan project, pilih **Properties**, **Run**, lalu isi **VM Options** dengan `-ea`.
2. Klik kanan file `MainApp.java`, lalu pilih **Run File** (Shift+F6).

**Lewat terminal** (dari folder `src`):

```
javac nilai_mahasiswa/model/Mahasiswa.java nilai_mahasiswa/exception/NilaiInvalidException.java nilai_mahasiswa/service/LaporanService.java nilai_mahasiswa/app/MainApp.java
java -ea nilai_mahasiswa.app.MainApp
```

File `laporan_nilai.txt` dibuat otomatis di folder tempat program dijalankan (folder utama project jika lewat NetBeans).

## Contoh Output

```
=== PROGRAM LAPORAN NILAI MAHASISWA ===
Masukkan jumlah mahasiswa: 3

Mahasiswa ke-1
NIM   : 20A4001
[ERROR] NIM hanya boleh berisi angka.
NIM   : 2024001
Nama  :  budi santoso 
Nilai : 85

Mahasiswa ke-2
NIM   : 2024002
Nama  : siti aminah
Nilai : 150
[ERROR] Nilai 150.0 tidak valid. Harus 0 sampai 100.
Nilai : 62.5

Mahasiswa ke-3
NIM   : 2024003
Nama  : andi wijaya
Nilai : 70

Laporan berhasil ditulis ke laporan_nilai.txt

=== ISI FILE laporan_nilai.txt ===
LAPORAN NILAI MAHASISWA
==================================================================
No  | NIM          | Nama           | Nilai  | Grade | Status
----+--------------+----------------+--------+-------+------------
1   | 2024001      | BUDI SANTOSO   | 85.00  | A     | Lulus
2   | 2024002      | SITI AMINAH    | 62.50  | D     | Tidak Lulus
3   | 2024003      | ANDI WIJAYA    | 70.00  | C     | Lulus
==================================================================
RINGKASAN
Jumlah mahasiswa      : 3
Rata-rata nilai kelas : 72.50
Jumlah lulus          : 2
Jumlah tidak lulus    : 1
=== SELESAI ===
```