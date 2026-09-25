# Sistem Kasir Toko Baju (Java)

Program simulasi kasir sederhana untuk toko pakaian, dibuat dengan Java untuk memenuhi Lab Sessions pertama dan menerapkan konsep dasar OOP (*Object-Oriented Programming*).

Kasir memasukkan kode produk satu per satu, lalu program menghitung subtotal, diskon, pajak (PPN), sampai mencetak struk pembayaran ke layar.

## Fitur

- Menampilkan katalog produk beserta stok yang tersedia
- Transaksi berbasis **kode produk**
- Diskon otomatis berdasarkan jumlah pembelian
- Perhitungan PPN 11% pada total belanja
- Validasi input: kode produk tidak ditemukan, jumlah beli tidak valid, stok tidak mencukupi, input bukan angka
- Menampilkan stok akhir setelah semua transaksi selesai

## Struktur Project

```
kasir/
├── Produk.java       # Class model data produk (atribut, constructor, method)
└── SistemKasir.java  # Class utama (main) - alur transaksi & tampilan
```

## Konsep Java yang Diterapkan

| Konsep | Penerapan |
|---|---|
| Class & Object | Class `Produk` dengan atribut & 3 method, diinstansiasi jadi 4 object |
| Constructor | `Produk(kodeProduk, namaProduk, harga, stok, kategori)` |
| Konstanta (`final`) | `PPN = 0.11` (tarif pajak 11%) |
| Kondisional | `if-else` untuk validasi input & perhitungan diskon bertingkat |
| Looping | `for` (tampilkan katalog/stok) dan `do-while` (loop transaksi sampai kasir mengetik "selesai") |
| Exception Handling | `try-catch` untuk kode produk tidak ditemukan, input bukan angka, jumlah beli tidak valid, stok kurang, dan pencegahan pembagian oleh nol |
| Char & String | Atribut `char kategori`; method `toUpperCase()`, `length()`, `equalsIgnoreCase()` |
| Array/Collection | `ArrayList<Produk>` untuk menyimpan seluruh data produk |

## Cara Menjalankan

### Lewat terminal (javac/java)

```bash
javac Produk.java SistemKasir.java
java kasir.SistemKasir
```

### Lewat NetBeans / IDE Java lainnya

1. Buat project baru, lalu tambahkan package bernama `kasir`
2. Masukkan file `Produk.java` dan `SistemKasir.java` ke dalam package tersebut
3. Jalankan (Run) `SistemKasir.java`

## Contoh Penggunaan

```
Masukkan kode produk: B01
Jumlah beli Kaos Hitam (Sisa Stok: 25): 3
-> Berhasil: 3x Kaos Hitam (Subtotal: Rp255000)

Masukkan kode produk: selesai

=========== STRUK PEMBAYARAN TOKO BAJU ===========
Subtotal      : Rp255000.0
PPN (11.0%)  : Rp28050.0
TOTAL BAYAR   : Rp283050.0
```
