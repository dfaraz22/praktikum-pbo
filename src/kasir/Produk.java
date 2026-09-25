/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kasir;

/**
 *
 * @author ACER
 */

// ================== POIN 1: CLASS & OBJECT ==================
// Class Produk memiliki atribut dan lebih dari 2 method
public class Produk {
    // Atribut (variabel)
    private String kodeProduk;
    private String namaProduk;
    private double harga;
    private int stok;
    private char kategori; // POIN 7: penggunaan tipe data char ('A'=Atasan, 'B'=Bawahan,)

    // POIN 2: CONSTRUCTOR untuk inisialisasi nilai awal atribut
    public Produk(String kodeProduk, String namaProduk, double harga, int stok, char kategori) {
        this.kodeProduk = kodeProduk;
        this.namaProduk = namaProduk;
        this.harga = harga;
        this.stok = stok;
        this.kategori = kategori;
    }

    // Getter sederhana
    // Ditambahkan agar sistem kasir bisa mencari produk berdasarkan kode (seperti scan barcode)
    public String getKodeProduk() {
        return kodeProduk;
    }

    public String getNamaProduk() {
        return namaProduk;
    }

    public double getHarga() {
        return harga;
    }

    public int getStok() {
        return stok;
    }

    public char getKategori() {
        return kategori;
    }

    // Method 1: mengurangi stok setelah transaksi
    public void kurangiStok(int jumlahBeli) {
        this.stok -= jumlahBeli;
    }

    // Method 2: menampilkan info produk
    // POIN 7: menggunakan method String -> toUpperCase() dan length()
    public void tampilkanInfo() {
        String namaUpper = namaProduk.toUpperCase(); // method String ke-1
        System.out.println("[" + kodeProduk + "] " + namaUpper
                + " (" + namaProduk.length() + " karakter)" // method String ke-2 (length)
                + " | Kategori: " + kategori
                + " | Harga: Rp" + harga
                + " | Stok: " + stok);
    }

    // Method 3: menghitung total harga berdasarkan jumlah beli
    // POIN 4: KONDISIONAL (if-else) untuk menentukan diskon
    public double hitungTotalHarga(int jumlahBeli) {
        double subtotal = harga * jumlahBeli;
        double diskon = 0;

        // Kondisional: Beli lebih banyak baju, dapat diskon lebih besar
        if (jumlahBeli >= 5) {
            diskon = 0.15; // Diskon 15% jika beli 5 atau lebih
        } else if (jumlahBeli >= 3) {
            diskon = 0.10; // Diskon 10% jika beli 3 atau lebih
        } else {
            diskon = 0.0;  // Tidak ada diskon
        }

        return subtotal - (subtotal * diskon);
    }
}