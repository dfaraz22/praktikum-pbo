# Program Manajemen Nilai Siswa

**Tugas Pengganti Pertemuan 7 - Materi XX**

## Daftar Materi Praktikum
* Class, Object, Method, dan Package
* Constructor
* Variabel dan Konstanta
* Primitive dan Reference Types
* Kontrol Alur: Kondisional dan Looping

## Data Diri

| | |
|---|---|
| Nama | Faradilla Zahrotul Ashifa |
| NIM | L0325023 |
| Kelas | B |
| Program Studi | Informatika |
| Universitas | Universitas Sebelas Maret |

## Materi yang Digunakan

| Materi | Penerapan dalam program |
|---|---|
| Class dan Object | Class `student` sebagai cetak biru data siswa, objek dibuat dengan `new student(...)` |
| Constructor | Constructor `student(String name, double score)` untuk mengisi nama dan nilai |
| Variabel dan Tipe Data | Tipe primitif `double` dan `boolean`, serta reference type `String` dan objek `student` |
| Method | Method `checkPassed()` pada class `student` untuk menentukan kelulusan |
| Array | Array of object `student[]` untuk menyimpan sekumpulan siswa |
| Looping | `for` loop untuk input data, pengecekan kelulusan, dan penampilan hasil |
| Kondisional | `if-else` untuk menentukan status lulus dan untuk menampilkan teks "Lulus" atau "Tidak Lulus" |
| Console I/O | `Scanner` untuk membaca input dari keyboard dan `System.out` untuk menampilkan hasil |

## Deskripsi Kasus

Dibuat program Java sederhana untuk mengelola data nilai siswa di sebuah kelas. Program membaca nama dan nilai beberapa siswa, menentukan status kelulusan setiap siswa, menghitung rata-rata nilai kelas, lalu menampilkan daftar hasil beserta jumlah siswa yang lulus dan tidak lulus. Siswa dinyatakan lulus jika nilainya lebih dari atau sama dengan 70.

## Fitur Program

1. Input jumlah siswa dan data tiap siswa (nama dan nilai) lewat konsol.
2. Penyimpanan data siswa dalam array objek `student`.
3. Pengecekan kelulusan setiap siswa melalui method `checkPassed()` milik objek masing-masing.
4. Penampilan daftar hasil: nama, nilai, dan status kelulusan.
5. Perhitungan rata-rata nilai kelas dengan looping.
6. Penghitungan jumlah siswa lulus dan tidak lulus.

## Struktur Project

```
ParktikumOOP/
└── src/
    └── kelola_nilai_siswa/
        ├── main.java
        └── student.java
```

| Komponen | Keterangan |
|---|---|
| Package | `kelola_nilai_siswa` |
| Class data | `student` |
| Class utama | `main` |

## Penjelasan per Class

### Class `student`

Class ini mewakili satu siswa.

| Bagian | Keterangan |
|---|---|
| `String name` | Nama siswa (reference type) |
| `double score` | Nilai siswa (primitif) |
| `boolean passed` | Status kelulusan (primitif), bernilai `false` secara default |
| Constructor `student(name, score)` | Mengisi `name` dan `score`, serta mengatur `passed` ke `false` sebelum dicek |
| Method `checkPassed()` | Jika `score >= 70` maka `passed = true`, selain itu `passed = false` |

### Class `main`

Class ini berisi method `main` sebagai titik awal program. Tahapannya:

| Tahap | Keterangan |
|---|---|
| 1. Input jumlah siswa | Membaca jumlah siswa dengan `Scanner`, lalu membuat array `student[]` seukuran jumlah tersebut |
| 2. Input data siswa | `for` loop membaca nama dan nilai tiap siswa, lalu membuat objek `student` dan menyimpannya di array |
| 3. Cek kelulusan | `for` loop memanggil `checkPassed()` pada setiap objek |
| 4. Tampilkan hasil | `for` loop menampilkan nama, nilai, dan status. Status ditentukan dengan kondisional berdasarkan `passed`. Di loop yang sama, total nilai dan jumlah lulus/tidak lulus dihitung |
| 5. Ringkasan | Menghitung rata-rata (total nilai dibagi jumlah siswa) dan menampilkan jumlah siswa lulus dan tidak lulus |
| 6. Selesai | `Scanner` ditutup |

## Alur Program

```
Mulai
  |
Input jumlah siswa, buat array student[]
  |
Untuk setiap siswa:
  - Input nama
  - Input nilai
  - Buat objek student, simpan di array
  |
Untuk setiap siswa: panggil checkPassed()
  |
Untuk setiap siswa:
  - Tampilkan nama, nilai, status (Lulus / Tidak Lulus)
  - Tambahkan nilai ke total
  - Tambah hitungan lulus atau tidak lulus
  |
Hitung rata-rata, tampilkan rata-rata dan jumlah lulus / tidak lulus
  |
Selesai
```

## Cara Menjalankan

**Lewat NetBeans:** klik kanan file `main.java`, lalu pilih **Run File** (Shift+F6).

**Lewat terminal** (dari folder `src`):

```
javac kelola_nilai_siswa/student.java kelola_nilai_siswa/main.java
java kelola_nilai_siswa.main
```

## Contoh Output

```
==========================================
     PROGRAM MANAJEMEN NILAI SISWA       
==========================================
Masukkan jumlah siswa: 3

Data siswa ke-1:
Masukkan nama: Alice
Masukkan nilai: 85

Data siswa ke-2:
Masukkan nama: Bob
Masukkan nilai: 60

Data siswa ke-3:
Masukkan nama: Charlie
Masukkan nilai: 75
------------------------------------------
                   HASIL                  
------------------------------------------

Siswa: Alice - Nilai: 85.0 - Lulus
Siswa: Bob - Nilai: 60.0 - Tidak Lulus
Siswa: Charlie - Nilai: 75.0 - Lulus

Rata-rata nilai kelas: 73.33
Jumlah siswa lulus: 2
Jumlah siswa tidak lulus: 1
==========================================
==========================================
```

Hitungan: (85 + 60 + 75) / 3 = 73,33. Nilai 70 ke atas dinyatakan lulus.

## Catatan

- Program ini sengaja dibuat sederhana sesuai soal, sehingga belum memiliki validasi input. Jika jumlah siswa atau nilai diisi dengan non-angka, program berhenti dengan `NumberFormatException`.
- Hasil ditampilkan setelah seluruh data siswa selesai diinput, bukan satu per satu setelah tiap input.
