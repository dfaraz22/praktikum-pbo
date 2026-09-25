/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kasir;

/**
 *
 * @author ACER
 */
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner; // Digunakan untuk menerima input dari keyboard

// ================== CLASS UTAMA (MAIN) ==================
public class SistemKasir {

    // POIN 3: KONSTANTA (final) - tarif pajak (PPN) toko pakaian
    static final double PPN = 0.11; // 11%

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // POIN 8: ARRAY/COLLECTION - menyimpan data stok baju menggunakan ArrayList
        ArrayList<Produk> daftarProduk = new ArrayList<>();
        daftarProduk.add(new Produk("B01", "Kaos Hitam", 85000, 25, 'A')); // 'A' = Atasan
        daftarProduk.add(new Produk("B02", "Celana Abu", 150000, 15, 'B')); // 'B' = Bawahan
        daftarProduk.add(new Produk("B03", "Celana Putih", 150000, 15, 'B')); // 'B' = Bawahan
        daftarProduk.add(new Produk("B04", "Jaket Denim", 220000, 10, 'A')); // 'A' = Atasan

        System.out.println("=========== DAFTAR KATALOG TOKO BAJU ===========");
        // POIN 5: LOOPING - menampilkan semua produk baju dengan perulangan for
        for (int i = 0; i < daftarProduk.size(); i++) {
            daftarProduk.get(i).tampilkanInfo();
        }

        System.out.println("\n=========== INPUT TRANSAKSI KASIR ===========");
        double totalBelanja = 0;

        // Looping untuk memproses setiap produk satu per satu lewat input keyboard
        for (Produk p : daftarProduk) {
            boolean inputValid = false;

            // Mengulangi input jika terjadi kesalahan/error
            while (!inputValid) {
                // POIN 6: EXCEPTION HANDLING - menangani potensi error input
                try {
                    System.out.print("Masukkan jumlah beli untuk " + p.getNamaProduk() + " (Sisa Stok: " + p.getStok() + "): ");
                    int beli = input.nextInt(); // Menerima input integer dari kasir/user

                    if (beli < 0) {
                        // Melempar error jika nilai input negatif
                        throw new IllegalArgumentException("Jumlah beli tidak boleh kurang dari 0!");
                    }
                    if (beli > p.getStok()) {
                        // Melempar error jika stok baju tidak mencukupi
                        throw new Exception("Stok " + p.getNamaProduk() + " tidak mencukupi!");
                    }

                    // Jika jumlah beli valid (> 0), hitung total harga
                    if (beli > 0) {
                        double totalHarga = p.hitungTotalHarga(beli);
                        p.kurangiStok(beli);
                        totalBelanja += totalHarga;
                        System.out.println("-> Berhasil menambahkan " + beli + "x " + p.getNamaProduk() + " (Subtotal: Rp" + totalHarga + ")\n");
                    } else {
                        System.out.println("-> Melewati " + p.getNamaProduk() + "\n");
                    }

                    inputValid = true; // Keluar dari loop input untuk produk ini jika berhasil

                } catch (InputMismatchException e) {
                    // Menangani error jika user memasukkan huruf/karakter saat meminta angka
                    System.out.println("Error Input: Masukkan angka bulat yang valid!");
                    input.nextLine(); // Membersihkan sisa input yang salah
                } catch (IllegalArgumentException e) {
                    System.out.println("Error Input: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Error Transaksi: " + e.getMessage());
                }
            }
        }

        // Menghitung total akhir dengan pajak (menggunakan KONSTANTA PPN)
        double totalDenganPajak = totalBelanja + (totalBelanja * PPN);

        // POIN 9: Menampilkan struk belanja ke layar
        System.out.println("\n=========== STRUK PEMBAYARAN TOKO BAJU ===========");
        System.out.println("Subtotal      : Rp" + totalBelanja);
        System.out.println("PPN (" + (PPN * 100) + "%)  : Rp" + (totalBelanja * PPN));
        System.out.println("TOTAL BAYAR   : Rp" + totalDenganPajak);

        System.out.println("\n=========== STOK BAJU AKHIR ===========");
        for (Produk p : daftarProduk) {
            p.tampilkanInfo();
        }

        input.close(); // Menutup resource scanner
    }
}