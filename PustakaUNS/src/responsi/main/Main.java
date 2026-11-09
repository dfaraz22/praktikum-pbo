package responsi.main;

import java.text.NumberFormat;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;
import responsi.exception.BatasPinjamException;
import responsi.exception.BukuTidakTersediaException;
import responsi.model.Anggota;
import responsi.model.Buku;
import responsi.service.Perpustakaan;
import responsi.util.FileHandler;
import responsi.util.Validator;

// =====================================================================
//MENU UTAMA APLIKASI
// =====================================================================

public class Main {

    // ----- konstanta tampilan & menu -----
    private static final int LEBAR_KOTAK = 40;
    private static final String BATAL = "0";
    private static final String YA = "y";
    private static final int MENU_KELUAR = 10;
    private static final String[] MENU = {
        "Tambah Buku", "Tampilkan Katalog", "Cari Buku (judul)", "Daftarkan Anggota",
        "Pinjam Buku", "Kembalikan Buku", "Rekap Peminjaman Mingguan",
        "Simpan Data", "Muat Data", "Keluar", "Demo Reference & Assertion"
    };

    // seluruh input lewat Scanner(System.in), seluruh output lewat System.out
    private static final Scanner INPUT = new Scanner(System.in);
    private static final Perpustakaan LIB = new Perpustakaan(); // objek dibuat dengan new

    public static void main(String[] args) {
        int pilihan;
        // do-while: menu minimal tampil satu kali
        do {
            pilihan = 0;
            tampilMenu();
            try {
                pilihan = bacaAngka("Pilih menu [1-" + MENU.length + "]: ");
                switch (pilihan) {
                    case 1: menuTambahBuku(); break;
                    case 2: menuKatalog(); break;
                    case 3: menuCari(); break;
                    case 4: menuDaftarAnggota(); break;
                    case 5: menuPinjam(); break;
                    case 6: menuKembali(); break;
                    case 7: menuRekap(); break;
                    case 8: menuSimpan(); break;
                    case 9: menuMuat(); break;
                    case 10: menuKeluar(); break;
                    case 11:
                        LIB.demoReference();
                        LIB.demoAssertion();
                        break;
                    default:
                        System.out.println("[ERROR] Menu tidak tersedia. Pilih 1-" + MENU.length + ".");
                }
            } catch (NumberFormatException | InputMismatchException e) {
                // input angka salah (huruf): menu tetap berjalan
                System.out.println("[ERROR] Input harus berupa angka!");
            } catch (NoSuchElementException e) {
                // input habis (mis. Ctrl+D / input dari file): keluar dengan rapi
                System.out.println("\n[INFO] Input berakhir, program ditutup.");
                pilihan = MENU_KELUAR;
            } catch (Exception e) {
                System.out.println("[ERROR] Terjadi kesalahan: " + e.getMessage());
            }
        } while (pilihan != MENU_KELUAR);
    }
    
    //  TAMPILAN
    private static void tampilMenu() {
        String garis = "+" + "=".repeat(LEBAR_KOTAK) + "+";
        String info = "Buku: " + LIB.getKatalog().size()
                + " | Anggota: " + LIB.getDaftarAnggota().size()
                + " | Antri: " + LIB.totalAntrian();
        System.out.println();
        System.out.println(garis);
        System.out.println("|" + tengah("PUSTAKAUNS") + "|");
        System.out.println("|" + tengah(info) + "|");
        System.out.println(garis);
        for (int i = 0; i < MENU.length; i++) { // for biasa: nomor urut menu
            System.out.println("|" + padKanan(String.format(" %2d. %s", i + 1, MENU[i])) + "|");
        }
        System.out.println(garis);
    }

    private static String padKanan(String s) {
        if (s.length() > LEBAR_KOTAK) {
            s = s.substring(0, LEBAR_KOTAK);
        }
        return String.format("%-" + LEBAR_KOTAK + "s", s);
    }

    private static String tengah(String s) {
        int sisa = Math.max(LEBAR_KOTAK - s.length(), 0);
        return padKanan(" ".repeat(sisa / 2) + s);
    }

    private static String potong(String s, int lebar) {
        return s.length() <= lebar ? s : s.substring(0, lebar - 1) + "~";
    }

    private static String rupiah(int nilai) {
        return "Rp" + NumberFormat.getInstance(Locale.forLanguageTag("id-ID")).format(nilai);
    }
    
    //  INPUT
    private static String bacaBaris(String prompt) {
        System.out.print(prompt);
        return INPUT.nextLine();
    }

    // Baca angka lewat nextLine + parseInt (menghindari masalah Enter tertinggal)
    private static int bacaAngka(String prompt) {
        return Integer.parseInt(bacaBaris(prompt).trim());
    }

    private static boolean tanyaYa(String pertanyaan) {
        String jawab = bacaBaris(pertanyaan + " (y/n): ").trim();
        // equalsIgnoreCase (bukan ==): membandingkan ISI String, bukan alamat objeknya
        return jawab.equalsIgnoreCase(YA);
    }

    // while: ulangi sampai ID valid. Mengembalikan null jika pengguna mengetik 0 (batal).
    private static String bacaIdAnggota() {
        while (true) {
            String id = bacaBaris("ID Anggota (contoh AGT001, 0 = batal): ").trim().toUpperCase();
            if (id.equals(BATAL)) {
                return null;
            }
            if (Validator.validasiIdAnggota(id)) {
                return id;
            }
            System.out.println("[ERROR] Format ID salah. Harus AGT + 3 digit (contoh: AGT001).");
        }
    }

    // Minta ID, lalu cari anggotanya. Null jika batal atau tidak ditemukan.
    private static Anggota pilihAnggota() {
        String id = bacaIdAnggota();
        if (id == null) {
            System.out.println("[BATAL] Kembali ke menu.");
            return null;
        }
        Anggota anggota = LIB.cariAnggota(id);
        if (anggota == null) {
            System.out.println("[ERROR] Anggota " + id + " belum terdaftar. Daftarkan lewat menu 4.");
        }
        return anggota;
    }

    //  MENU 1 - 4

    private static void menuTambahBuku() {
        try {
            String kode;
            while (true) { // ulangi sampai kode unik dan tidak kosong
                kode = bacaBaris("Kode Buku (0 = batal): ").trim().toUpperCase();
                if (kode.equals(BATAL)) {
                    System.out.println("[BATAL] Kembali ke menu.");
                    return;
                }
                if (kode.isEmpty()) {
                    System.out.println("[ERROR] Kode tidak boleh kosong.");
                } else if (LIB.cariBuku(kode) != null) {
                    System.out.println("[ERROR] Kode " + kode + " sudah dipakai.");
                } else {
                    break;
                }
            }
            String judul = bacaBaris("Judul      : ");     // sengaja tidak di-trim, dirapikan Validator
            String pengarang = bacaBaris("Pengarang  : ");
            String kategori = bacaBaris("Kategori   : ");
            int tahun = bacaAngka("Tahun      : ");
            int stok = bacaAngka("Stok       : ");

            Buku buku = LIB.tambahBuku(kode, judul, pengarang, kategori, tahun, stok);
            System.out.println("[OK] Buku tersimpan sebagai \"" + buku.getJudul() + "\"");
            System.out.println("Kode verifikasi    : " + Validator.balikString(buku.getKodeBuku()));
            System.out.println("Jumlah vokal judul : " + Validator.hitungVokal(buku.getJudul()));
            int[] hurufAngka = Validator.hitungHurufDanAngka(buku.getKodeBuku());
            System.out.println("Isi kode buku      : " + hurufAngka[0] + " huruf, " + hurufAngka[1] + " angka");
        } catch (NumberFormatException | InputMismatchException e) {
            System.out.println("[ERROR] Tahun dan stok harus berupa angka!");
        } catch (Exception e) {
            System.out.println("[ERROR] " + e.getMessage());
        } finally {
            System.out.println("Transaksi selesai.");
        }
    }

    private static void menuKatalog() {
        List<Buku> katalog = LIB.getKatalog();
        System.out.println("\n=== KATALOG ===");
        if (katalog.isEmpty()) {
            System.out.println("Katalog masih kosong. Tambahkan buku lewat menu 1.");
            return;
        }
        System.out.printf("%-4s%-13s%-31s%-15s%-14s%s%n", "No", "Kode", "Judul", "Pengarang", "Kategori", "Stok");
        int no = 1;
        for (Buku b : katalog) { // for-each untuk isi katalog
            System.out.printf("%-4d%-13s%-31s%-15s%-14s%d%n", no++, potong(b.getKodeBuku(), 12),
                potong(b.getJudul(), 30), potong(b.getPengarang(), 14),
                potong(b.getKategori(), 13), b.getStok());
        }
        // \n dan \t = escape sequence pada format laporan
        System.out.println("\nTotal judul\t: " + katalog.size());
        System.out.println("Kategori unik\t: " + LIB.getKategoriUnik());
    }

    private static void menuCari() {
        String kata = bacaBaris("Kata kunci judul: ").trim();
        if (kata.isEmpty()) {
            System.out.println("[ERROR] Kata kunci tidak boleh kosong.");
            return;
        }
        List<Buku> hasil = LIB.cariJudul(kata);
        if (hasil.isEmpty()) {
            System.out.println("Tidak ada buku dengan judul mengandung \"" + kata + "\".");
            return;
        }
        System.out.println("Ditemukan " + hasil.size() + " buku:");
        for (int i = 0; i < hasil.size(); i++) { // for biasa: nomor urut hasil
            System.out.print((i + 1) + ". ");
            hasil.get(i).tampilkanInfo();
        }
    }

    private static void menuDaftarAnggota() {
        try {
            String id = bacaIdAnggota();
            if (id == null) {
                System.out.println("[BATAL] Kembali ke menu.");
                return;
            }
            if (LIB.cariAnggota(id) != null) {
                System.out.println("[ERROR] ID " + id + " sudah terdaftar.");
                return;
            }
            String nama = bacaBaris("Nama       : ");
            while (Validator.normalisasiJudul(nama).isEmpty()) { // while: ulangi sampai nama terisi
                System.out.println("[ERROR] Nama tidak boleh kosong.");
                nama = bacaBaris("Nama       : ");
            }
            Anggota anggota = LIB.daftarkanAnggota(id, nama);
            System.out.println("[OK] Anggota " + anggota.getNama() + " terdaftar.");
        } catch (Exception e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }
    
    //  MENU 5 - PINJAM
   private static void tampilBukuTersedia() {
    List<Buku> katalog = LIB.getKatalog();
    System.out.println("\n--- Buku Tersedia ---");
    
    if (katalog.isEmpty()) {
        System.out.println("(katalog masih kosong)");
        return;
    }
    
    // 1. Cetak Header Tabel dengan lebar kolom yang konsisten (Kode dilebarkan jadi 13)
    System.out.printf("%-4s%-13s%-35s%s%n", "No", "Kode", "Judul", "Stok");
    
    int no = 1; // Deklarasi nomor urut ditaruh di atas perulangan
    
    // 2. Tampilkan buku yang stoknya masih ada (> 0)
    for (Buku b : katalog) {
        if (b.getStok() > 0) {
            System.out.printf("%-4d%-13s%-35s%d%n", 
                no++, 
                potong(b.getKodeBuku(), 12), 
                potong(b.getJudul(), 34), 
                b.getStok());
        }
    }
    
    // 3. Tampilkan informasi buku yang stoknya habis (opsional sesuai programmu)
    for (Buku b : katalog) {
        if (b.getStok() == 0) {
            System.out.println("- " + b.getKodeBuku() + " " + b.getJudul() + " : habis, bisa masuk antrian");
        }
    }
    System.out.println();
}

    private static void menuPinjam() {
        tampilBukuTersedia();
        try { // try luar: seluruh transaksi
            Anggota anggota = pilihAnggota();
            if (anggota == null) {
                return;
            }
            String kode = bacaBaris("Kode Buku (0 = batal): ").trim().toUpperCase();
            if (kode.equals(BATAL)) {
                System.out.println("[BATAL] Kembali ke menu.");
                return;
            }

            Buku buku;
            try { // NESTED try-catch: validasi kode buku di dalam proses pinjam
                buku = LIB.cariBuku(kode);
                if (buku == null) {
                    throw new IllegalArgumentException("Kode buku " + kode + " tidak ditemukan.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("[ERROR] " + e.getMessage());
                return;
            }

            if (!tanyaYa("Pinjam \"" + buku.getJudul() + "\"?")) {
                System.out.println("[BATAL] Peminjaman dibatalkan.");
                return;
            }
            LIB.pinjamBuku(anggota, buku); // bisa melempar exception
            System.out.println("[OK] " + anggota.getNama() + " berhasil meminjam \""
                    + buku.getJudul() + "\". Sisa stok: " + buku.getStok());
        } catch (BukuTidakTersediaException e) {
            System.out.println("[GAGAL] " + e.getMessage());
        } catch (BatasPinjamException e) {
            System.out.println("[GAGAL] " + e.getMessage());
        } catch (NumberFormatException | InputMismatchException e) {
            System.out.println("[ERROR] Input harus berupa angka!");
        } catch (Exception e) {
            System.out.println("[ERROR] Terjadi kesalahan: " + e.getMessage());
        } finally {
            System.out.println("Transaksi selesai."); // selalu tercetak
        }
    }

    //  MENU 6 - KEMBALIKAN
    private static void menuKembali() {
        try {
            Anggota anggota = pilihAnggota();
            if (anggota == null) {
                return;
            }
            if (anggota.getDaftarPinjam().isEmpty()) {
                System.out.println("[INFO] " + anggota.getNama() + " tidak sedang meminjam buku.");
                return;
            }
            anggota.tampilkanInfo();
            String kode = bacaBaris("Kode Buku (0 = batal): ").trim().toUpperCase();
            if (kode.equals(BATAL)) {
                System.out.println("[BATAL] Kembali ke menu.");
                return;
            }
            Buku buku = anggota.cariDiDaftarPinjam(kode);
            if (buku == null) {
                System.out.println("[ERROR] Buku " + kode + " tidak ada dalam daftar pinjam " + anggota.getNama() + ".");
                return;
            }
            System.out.println("Lama pinjam standar: " + Perpustakaan.LAMA_PINJAM + " hari.");
            int terlambat = bacaAngka("Terlambat (hari, 0 = tepat waktu): ");
            if (!tanyaYa("Kembalikan \"" + buku.getJudul() + "\"?")) {
                System.out.println("[BATAL] Pengembalian dibatalkan.");
                return;
            }

            LIB.kembalikanBuku(anggota, buku);
            System.out.println("[OK] " + anggota.getNama() + " mengembalikan \"" + buku.getJudul() + "\".");
            if (terlambat > 0) {
                System.out.println("Denda: " + terlambat + " x " + rupiah(LIB.tarifPerHari(terlambat))
                        + " = " + rupiah(LIB.hitungDenda(terlambat)));
            } else {
                System.out.println("Denda: " + rupiah(0) + " (tidak terlambat)");
            }
            if (LIB.periksaBlokir(anggota, terlambat)) {
                System.out.println("[PERINGATAN] Anggota diblokir sementara!");
            }
            Anggota berikut = LIB.ambilAntrian(buku); // Queue: poll() anggota terdepan
            if (berikut != null) {
                System.out.println("[NOTIF] " + berikut.getNama() + " (antrian terdepan) - buku \""
                        + buku.getJudul() + "\" sudah tersedia!");
            }
        } catch (NumberFormatException | InputMismatchException e) {
            System.out.println("[ERROR] Input harus berupa angka!");
        } catch (Exception e) {
            System.out.println("[ERROR] Terjadi kesalahan: " + e.getMessage());
        } finally {
            System.out.println("Transaksi selesai.");
        }
    }

    //  MENU 7 - 10

    private static void menuRekap() {
        System.out.println("\n=== REKAP PEMINJAMAN MINGGUAN ===");
        int[] data = LIB.getPinjamPerHari();
        for (int i = 0; i < data.length; i++) { // for biasa: tampil indeks
            System.out.printf("[%d] %-7s: %d%n", i, Perpustakaan.NAMA_HARI[i], data[i]);
        }
        int total = LIB.totalPinjam();
        System.out.println("Total transaksi : " + total);
        if (total == 0) {
            System.out.println("Hari tersibuk   : - (belum ada transaksi)");
        } else {
            int idx = LIB.hariTersibuk();
            System.out.println("Hari tersibuk   : " + Perpustakaan.NAMA_HARI[idx] + " (" + data[idx] + " transaksi)");
        }
    }

    private static void menuSimpan() {
        if (FileHandler.simpanKatalog(LIB.getKatalog())) {
            System.out.println("[OK] Katalog disimpan ke " + FileHandler.FILE_KATALOG);
        }
        if (FileHandler.simpanAnggota(LIB.getDaftarAnggota())) {
            System.out.println("[OK] Data anggota disimpan ke " + FileHandler.FILE_ANGGOTA);
        }
    }

    private static void menuMuat() {
        List<Buku> katalogBaru = FileHandler.muatKatalog();
        if (katalogBaru == null) {
            return; // pesan error ramah sudah ditampilkan FileHandler
        }
        List<Anggota> anggotaBaru = FileHandler.muatAnggota();
        LIB.muatData(katalogBaru, anggotaBaru);
        if (anggotaBaru == null) {
            System.out.println("[OK] " + katalogBaru.size() + " buku berhasil dimuat (anggota tidak dimuat).");
        } else {
            System.out.println("[OK] " + katalogBaru.size() + " buku dan "
                    + anggotaBaru.size() + " anggota berhasil dimuat.");
        }
    }

    private static void menuKeluar() {
        if (tanyaYa("Simpan data sebelum keluar?")) {
            menuSimpan();
        }
        System.out.println("Terima kasih, sampai jumpa!");
    }
}

/*
 * CATATAN CODE:
 * - Assertion (assert): Dipakai buat ngecek bug logika pas lagi bikin program (misal stok minus atau judul null). 
 *   Ini cuma bakal jalan kalau program dinyalain pakai argumen -ea.
 * - Exception: Dipakai buat nanganin error runtime yang wajar terjadi pas program jalan (misal stok habis, 
 *   batas pinjam lewat, atau salah input huruf) supaya program gak langsung crash/berhenti tiba-tiba.
 */