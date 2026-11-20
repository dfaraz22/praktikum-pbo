package lab.kasir;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ACER
 */

public class Pesanan {

    private double subtotal;

    public void tambahItem(double harga, int jumlah) {
        subtotal += harga * jumlah;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public double hitungTotal() {
        if (subtotal > 50000) {
            return subtotal - (subtotal * 0.10);
        } else {
            return subtotal;
        }
    }

    public void reset() {
        subtotal = 0;
    }
}
