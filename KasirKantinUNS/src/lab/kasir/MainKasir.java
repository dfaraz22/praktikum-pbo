/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab.kasir;

/**
 *
 * @author ACER
 */
import javax.swing.SwingUtilities;

public class MainKasir {

    public static void main(String[] args) {

        // TAHAP 1: Pengujian Class Pesanan
        System.out.println("=== KASIR KANTIN UNS ===");

        Pesanan pesanan = new Pesanan();

        pesanan.tambahItem(15000, 2);
        System.out.println("Menu: Nasi Goreng");
        System.out.println("Subtotal: Rp "
                + pesanan.getSubtotal());

        pesanan.tambahItem(5000, 3);
        System.out.println("Menu: Es Teh");
        System.out.println("Subtotal: Rp "
                + pesanan.getSubtotal());
        System.out.println("Total Bayar: Rp "
                + pesanan.hitungTotal());

        pesanan.tambahItem(25000, 1);
        System.out.println("Menu: Ayam Bakar");
        System.out.println("Subtotal: Rp "
                + pesanan.getSubtotal());
        System.out.println("Total Bayar: Rp "
                + pesanan.hitungTotal());

        // TAHAP 2: Pengujian Multithreading
        System.out.println("\n=== SIMULASI DUA KASIR ===");

        Kasir kasir1 = new Kasir("Dina");
        Kasir kasir2 = new Kasir("Eko");

        kasir1.start();
        kasir2.start();

        try {
            kasir1.join();
            kasir2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }

        System.out.println("Semua pelanggan selesai dilayani");

        // TAHAP 3 DAN 4: Menjalankan GUI
        SwingUtilities.invokeLater(() -> {
            new KasirFrame();
        });
    }
}

