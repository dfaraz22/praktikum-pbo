package detektorpinjol;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final NumberFormat RP = NumberFormat.getInstance(Locale.forLanguageTag("id-ID"));

    static String rp(double angka) {
        return "Rp " + RP.format(Math.round(angka));
    }

    static String bacaTeks(String label) {
        while (true) {
            System.out.print(label);
            String s = sc.nextLine().trim();
            if (!s.isEmpty()) return s;
            System.out.println("   Input tidak boleh kosong.");
        }
    }

    static double bacaAngka(String label, double min) {
        while (true) {
            System.out.print(label);
            try {
                double v = Double.parseDouble(sc.nextLine().trim().replace(",", "."));
                if (v >= min) return v;
                System.out.println("   Nilai minimal " + min + ".");
            } catch (NumberFormatException e) {
                System.out.println("   Masukkan angka yang valid.");
            }
        }
    }

    static int bacaPilihan(String label, int min, int max) {
        while (true) {
            System.out.print(label);
            try {
                int v = Integer.parseInt(sc.nextLine().trim());
                if (v >= min && v <= max) return v;
            } catch (NumberFormatException e) {
                // jatuh ke pesan di bawah
            }
            System.out.println("   Pilih angka " + min + "-" + max + ".");
        }
    }

    static boolean bacaYaTidak(String label) {
        while (true) {
            System.out.print(label);
            String s = sc.nextLine().trim().toLowerCase();
            if (s.equals("y")) return true;
            if (s.equals("n")) return false;
            System.out.println("   Ketik y atau n.");
        }
    }

    static void cekAplikasi(ArrayList<AplikasiPinjaman> daftar) {
        System.out.println();
        String nama = bacaTeks("Nama aplikasi                    : ");
        int izin = bacaPilihan("Status izin (1.Terdaftar OJK 2.Tidak terdaftar 3.Baru/belum jelas): ", 1, 3);
        double bunga = bacaAngka("Bunga per bulan (%)              : ", 0);
        double admin = bacaAngka("Biaya admin (%)                  : ", 0);
        boolean kontak = bacaYaTidak("Minta akses kontak/galeri? (y/n) : ");
        double pinjaman = bacaAngka("Simulasi pinjaman (Rp)           : ", 1);
        int tenor = (int) bacaAngka("Tenor (bulan)                    : ", 1);

        // Runtime polymorphism: referensi superclass, objek subclass
        AplikasiPinjaman app;
        if (izin == 1) {
            app = new AplikasiTerdaftar(nama, bunga, admin, kontak);
        } else if (izin == 2) {
            app = new AplikasiTidakTerdaftar(nama, bunga, admin, kontak);
        } else {
            app = new AplikasiBaru(nama, bunga, admin, kontak);
        }
        daftar.add(app);

        double potonganAdmin = app.hitungPotonganAdmin(pinjaman);
        double biayaTotal = app.hitungBiayaTotal(pinjaman, tenor);
        double persenBiaya = biayaTotal / pinjaman * 100;

        String garis = "------------------------------------------------";
        System.out.println("\n" + garis);
        System.out.println("Aplikasi       : " + app.getNamaAplikasi());
        System.out.println("Status         : " + app.getStatusIzin());
        System.out.println("Skor risiko    : " + app.hitungSkorRisiko() + " / 100");
        System.out.println("Level          : " + app.tentukanLevel());
        System.out.println("Uang diterima  : " + rp(pinjaman - potonganAdmin)
                + (potonganAdmin > 0 ? "   (admin " + rp(potonganAdmin) + " dipotong di muka)" : ""));
        System.out.println("Total bayar    : " + rp(app.hitungTotalBayar(pinjaman, tenor)));
        System.out.printf("Biaya total    : %s (%.1f%% dalam %d bulan)%n", rp(biayaTotal), persenBiaya, tenor);
        System.out.println(garis);
        System.out.println(app.tentukanLevel().equals("AMAN") ? "" : "!! PERINGATAN !!");
        app.tampilkanPeringatan();

        if (!app.tentukanLevel().equals("AMAN") && bacaYaTidak("Laporkan ke OJK? (y/n): ")) {
            app.laporkanKeOJK();
        }
    }

    static void tampilkanRingkasan(ArrayList<AplikasiPinjaman> daftar) {
        if (daftar.isEmpty()) {
            System.out.println("\nBelum ada aplikasi yang dicek.");
            return;
        }
        System.out.println("\n===== RINGKASAN HASIL PENGECEKAN =====");
        System.out.printf("%-4s%-20s%-7s%s%n", "No", "Aplikasi", "Skor", "Level");
        int bahaya = 0, waspada = 0, aman = 0, no = 1;
        for (AplikasiPinjaman app : daftar) {   // polymorphism pada koleksi
            System.out.printf("%-4d%-20s%-7d%s%n", no++, app.getNamaAplikasi(),
                    app.hitungSkorRisiko(), app.tentukanLevel());
            switch (app.tentukanLevel()) {
                case "BAHAYA": bahaya++; break;
                case "WASPADA": waspada++; break;
                default: aman++;
            }
        }
        System.out.println("Total dicek: " + daftar.size() + " | Bahaya: " + bahaya
                + " | Waspada: " + waspada + " | Aman: " + aman);
    }

    public static void main(String[] args) {
        ArrayList<AplikasiPinjaman> daftar = new ArrayList<>();
        boolean jalan = true;

        while (jalan) {
            System.out.println("\n===== DETEKTOR PINJOL MENCURIGAKAN =====");
            System.out.println("1. Cek aplikasi pinjaman");
            System.out.println("2. Lihat ringkasan hasil");
            System.out.println("3. Keluar");
            int menu = bacaPilihan("Pilih menu: ", 1, 3);

            switch (menu) {
                case 1: cekAplikasi(daftar); break;
                case 2: tampilkanRingkasan(daftar); break;
                default:
                    System.out.println("\nTerima kasih. Selalu cek legalitas pinjaman di ojk.go.id atau hubungi 157.");
                    jalan = false;
            }
        }
    }
}
