package responsi.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;
import responsi.exception.BatasPinjamException;
import responsi.exception.BukuTidakTersediaException;
import responsi.model.Anggota;
import responsi.model.Buku;
import responsi.util.Validator;

/**
 * Logika bisnis perpustakaan: menyimpan koleksi data, proses pinjam/kembali,
 * perhitungan denda, dan beberapa demo konsep.
 */
public class Perpustakaan {

    // ===================== KONSTANTA (satu tempat) =====================
    public static final int MAX_PINJAM = 3;
    public static final int DENDA_PER_HARI = 1000;
    public static final int LAMA_PINJAM = 7;                // hari
    public static final char STATUS_AKTIF = 'A';
    public static final char STATUS_NONAKTIF = 'N';
    public static final int JUMLAH_HARI = 7;                // Senin..Minggu
    public static final int BATAS_DENDA_RINGAN = 7;         // 1-7 hari
    public static final int BATAS_DENDA_SEDANG = 14;        // 8-14 hari, di atasnya diblokir
    public static final int PENGALI_SEDANG = 2;
    public static final int PENGALI_BERAT = 3;
    public static final String[] NAMA_HARI =
            { "Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu", "Minggu" };

    // ===================== KOLEKSI DATA =====================
    // ArrayList dipilih untuk katalog (bukan array biasa) karena jumlah buku
    // berubah-ubah: ArrayList bisa tumbuh otomatis dan punya add/remove/size,
    // sedangkan array biasa ukurannya tetap sejak dibuat.
    private List<Buku> katalog = new ArrayList<>();
    private List<Anggota> daftarAnggota = new ArrayList<>();

    // Set: kategori unik, otomatis tanpa duplikat (TreeSet = terurut, tidak peka huruf besar/kecil)
    private Set<String> kategoriUnik = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);

    // Queue (LinkedList): antrian peminjam per kode buku untuk buku yang stoknya habis
    private Map<String, Queue<Anggota>> antrian = new HashMap<>();

    // Array: jumlah transaksi peminjaman per hari (indeks 0 = Senin)
    private int[] pinjamPerHari = new int[JUMLAH_HARI];

    // ===================== AKSES DATA =====================
    public List<Buku> getKatalog() { return katalog; }
    public List<Anggota> getDaftarAnggota() { return daftarAnggota; }
    public Set<String> getKategoriUnik() { return kategoriUnik; }
    public int[] getPinjamPerHari() { return pinjamPerHari.clone(); }

    /** Jumlah seluruh anggota yang sedang mengantri (semua buku). */
    public int totalAntrian() {
        int total = 0;
        for (Queue<Anggota> q : antrian.values()) {
            total += q.size();
        }
        return total;
    }

    // ===================== BUKU & ANGGOTA =====================

    /** Menambah buku ke katalog (judul, pengarang, kategori dirapikan oleh Validator). */
    public Buku tambahBuku(String kode, String judul, String pengarang,
                           String kategori, int tahun, int stok) {
        assert judul != null : "Judul tidak boleh null";
        if (cariBuku(kode) != null) {
            throw new IllegalArgumentException("Kode buku " + kode + " sudah dipakai.");
        }
        if (tahun <= 0) {
            throw new IllegalArgumentException("Tahun terbit harus lebih dari 0.");
        }
        if (stok < 0) {
            throw new IllegalArgumentException("Stok tidak boleh negatif.");
        }
        String judulRapi = Validator.normalisasiJudul(judul);
        if (judulRapi.isEmpty()) {
            throw new IllegalArgumentException("Judul tidak boleh kosong.");
        }
        String kategoriRapi = Validator.normalisasiJudul(kategori);
        Buku buku = new Buku(kode, judulRapi, Validator.normalisasiJudul(pengarang),
                kategoriRapi, tahun, stok);
        katalog.add(buku);              // List: add
        kategoriUnik.add(kategoriRapi); // Set: duplikat otomatis diabaikan
        return buku;
    }

    /** List: pencarian buku berdasarkan kode. Null jika tidak ada. */
    public Buku cariBuku(String kode) {
        for (Buku b : katalog) {
            // equalsIgnoreCase (bukan ==) karena yang dibandingkan isi String, bukan alamat objek
            if (b.getKodeBuku().equalsIgnoreCase(kode)) {
                return b;
            }
        }
        return null;
    }

    /** Pencarian buku berdasarkan sebagian judul (tidak peka huruf besar/kecil). */
    public List<Buku> cariJudul(String kataKunci) {
        List<Buku> hasil = new ArrayList<>();
        for (Buku b : katalog) {
            if (Validator.cariJudul(b.getJudul(), kataKunci)) {
                hasil.add(b);
            }
        }
        return hasil;
    }

    public Anggota daftarkanAnggota(String id, String nama) {
        if (cariAnggota(id) != null) {
            throw new IllegalArgumentException("ID " + id + " sudah terdaftar.");
        }
        Anggota anggota = new Anggota(id, Validator.normalisasiJudul(nama), STATUS_AKTIF);
        daftarAnggota.add(anggota);
        return anggota;
    }

    public Anggota cariAnggota(String id) {
        for (Anggota a : daftarAnggota) {
            if (a.getIdAnggota().equalsIgnoreCase(id)) {
                return a;
            }
        }
        return null;
    }

    // ===================== PINJAM & KEMBALI =====================

    /**
     * Meminjam buku. Melempar exception jika batas pinjam tercapai atau stok habis
     * (dalam kasus stok habis, anggota otomatis dimasukkan ke antrian).
     */
    public void pinjamBuku(Anggota anggota, Buku buku)
            throws BatasPinjamException, BukuTidakTersediaException {
        assert anggota != null : "Anggota tidak boleh null";
        assert buku != null : "Buku tidak boleh null";

        if (anggota.getDaftarPinjam().size() >= MAX_PINJAM) {
            throw new BatasPinjamException(anggota.getNama() + " sudah meminjam "
                    + MAX_PINJAM + " buku (batas maksimal " + MAX_PINJAM + ").");
        }
        if (buku.getStok() <= 0) {
            int posisi = masukAntrian(anggota, buku);
            throw new BukuTidakTersediaException("Buku \"" + buku.getJudul()
                    + "\" tidak tersedia. Anda masuk antrian (posisi " + posisi + ").");
        }

        buku.kurangiStok();
        anggota.tambahPinjam(buku);
        pinjamPerHari[indeksHariIni()]++;

        // ASSERTION 3 (logika internal): pinjaman anggota tidak boleh melebihi MAX_PINJAM
        assert anggota.getDaftarPinjam().size() <= MAX_PINJAM
                : "Jumlah pinjaman melebihi MAX_PINJAM!";
    }

    /** Mengembalikan buku. False jika anggota memang tidak meminjam buku itu. */
    public boolean kembalikanBuku(Anggota anggota, Buku buku) {
        boolean ada = anggota.hapusPinjam(buku); // List: remove
        if (ada) {
            buku.tambahStok();
        }
        return ada;
    }

    /** Menambah anggota ke antrian buku (jika belum ada) dan mengembalikan posisinya. */
    private int masukAntrian(Anggota anggota, Buku buku) {
        Queue<Anggota> q = antrian.get(buku.getKodeBuku());
        if (q == null) {
            q = new LinkedList<>();
            antrian.put(buku.getKodeBuku(), q);
        }
        if (!q.contains(anggota)) {
            q.offer(anggota);
        }
        int posisi = 1;
        for (Anggota a : q) {
            if (a == anggota) {
                break;
            }
            posisi++;
        }
        return posisi;
    }

    /** Mengambil anggota terdepan di antrian buku dengan poll(). Null jika antrian kosong. */
    public Anggota ambilAntrian(Buku buku) {
        Queue<Anggota> q = antrian.get(buku.getKodeBuku());
        if (q == null) {
            return null;
        }
        return q.poll();
    }

    // ===================== DENDA =====================

    /** Tarif denda per hari sesuai tingkat keterlambatan (if - else if - else). */
    public int tarifPerHari(int hariTerlambat) {
        if (hariTerlambat <= 0) {
            return 0;
        } else if (hariTerlambat <= BATAS_DENDA_RINGAN) {
            return DENDA_PER_HARI;
        } else if (hariTerlambat <= BATAS_DENDA_SEDANG) {
            return DENDA_PER_HARI * PENGALI_SEDANG;
        } else {
            return DENDA_PER_HARI * PENGALI_BERAT;
        }
    }

    public int hitungDenda(int hariTerlambat) {
        return Math.max(hariTerlambat, 0) * tarifPerHari(hariTerlambat);
    }

    /** Jika terlambat lebih dari batas, anggota dinonaktifkan sementara. True jika diblokir. */
    public boolean periksaBlokir(Anggota anggota, int hariTerlambat) {
        if (hariTerlambat > BATAS_DENDA_SEDANG) {
            anggota.setStatus(STATUS_NONAKTIF);
            return true;
        }
        return false;
    }

    // ===================== REKAP MINGGUAN =====================

    private int indeksHariIni() {
        return LocalDate.now().getDayOfWeek().getValue() - 1; // Senin = 1 -> indeks 0
    }

    public int totalPinjam() {
        int total = 0;
        for (int jumlah : pinjamPerHari) {
            total += jumlah;
        }
        return total;
    }

    /** Indeks hari dengan transaksi terbanyak. */
    public int hariTersibuk() {
        int terbanyak = 0;
        for (int i = 1; i < pinjamPerHari.length; i++) {
            if (pinjamPerHari[i] > pinjamPerHari[terbanyak]) {
                terbanyak = i;
            }
        }
        return terbanyak;
    }

    // ===================== LOAD DATA =====================

    /** Mengganti isi katalog (dan anggota jika tidak null) dengan data hasil muat file. */
    public void muatData(List<Buku> katalogBaru, List<Anggota> anggotaBaru) {
        katalog.clear();
        katalog.addAll(katalogBaru);
        kategoriUnik.clear();
        for (Buku b : katalog) {
            kategoriUnik.add(b.getKategori());
        }
        if (anggotaBaru != null) {
            daftarAnggota.clear();
            daftarAnggota.addAll(anggotaBaru);
        }
        antrian.clear();
        // Hubungkan lagi buku pinjaman anggota ke objek buku di katalog
        // (hasil deserialization adalah salinan, bukan objek katalog yang sama)
        for (Anggota a : daftarAnggota) {
            List<Buku> pinjam = a.getDaftarPinjam();
            for (int i = 0; i < pinjam.size(); i++) {
                Buku asli = cariBuku(pinjam.get(i).getKodeBuku());
                if (asli != null) {
                    pinjam.set(i, asli);
                }
            }
        }
    }

    // ===================== DEMO =====================

    /** Class kecil khusus demo nilai default atribut. */
    private static class Contoh {
        String teks;  // reference type -> default null
        int angka;    // primitive      -> default 0
    }

    /** Demo reference types: b1 dan b2, ==, nilai default, dan cegah NullPointerException. */
    public void demoReference() {
        System.out.println("\n=== DEMO REFERENCE TYPES ===");
        Buku b1 = new Buku("DEMO01", "Demo Referensi", "Penulis", "Demo", 2024, 5);
        Buku b2 = b1; // yang disalin hanya "alamat" objek, bukan objeknya
        b2.setStok(9);
        System.out.println("Stok b1 setelah diubah lewat b2 : " + b1.getStok());

        // true: == pada reference type membandingkan ALAMAT objek.
        // b1 dan b2 menunjuk objek yang sama, jadi alamatnya sama.
        System.out.println("b1 == b2 : " + (b1 == b2));

        Buku b3 = new Buku("DEMO01", "Demo Referensi", "Penulis", "Demo", 2024, 9);
        // false: isinya sama tetapi b3 adalah objek baru di alamat lain
        System.out.println("b1 == b3 : " + (b1 == b3));

        // String: == membandingkan alamat, equals() membandingkan isi
        String s1 = new String("java");
        String s2 = "java";
        System.out.println("s1 == s2      : " + (s1 == s2));
        System.out.println("s1.equals(s2) : " + s1.equals(s2));

        Contoh c = new Contoh();
        System.out.println("Default reference (String) : " + c.teks);
        System.out.println("Default primitive (int)    : " + c.angka);
        if (c.teks != null) {
            System.out.println("Panjang teks : " + c.teks.length());
        } else {
            System.out.println("teks masih null, length() tidak dipanggil (aman dari NullPointerException).");
        }
        c.teks = "Halo";
        if (c.teks != null) {
            System.out.println("Setelah diisi, panjang teks : " + c.teks.length());
        }
        System.out.println("Total objek Buku yang pernah dibuat (static totalBuku) : " + Buku.totalBuku);
    }

    /** Demo assertion: satu kasus berhasil dan dua kasus gagal. Perlu dijalankan dengan -ea. */
    public void demoAssertion() {
        System.out.println("\n=== DEMO ASSERTION ===");
        boolean aktif = false;
        assert aktif = true; // trik: baris ini hanya dijalankan jika -ea aktif
        if (!aktif) {
            System.out.println("[INFO] Assertion NONAKTIF. Jalankan dengan: java -ea responsi.main.Main");
            return;
        }
        System.out.println("[INFO] Assertion AKTIF (-ea).");

        Buku b = new Buku("DEMO02", "Demo Assertion", "Penulis", "Demo", 2024, 1);
        b.kurangiStok();
        System.out.println("[ASSERT BERHASIL] stok setelah dikurangi = " + b.getStok() + " (masih >= 0)");

        try {
            b.kurangiStok(); // stok jadi -1 -> assertion gagal
        } catch (AssertionError e) {
            System.out.println("[ASSERT GAGAL] java.lang.AssertionError: " + e.getMessage());
        }

        try {
            new Buku("DEMO03", null, "Penulis", "Demo", 2024, 1); // judul null -> assertion gagal
        } catch (AssertionError e) {
            System.out.println("[ASSERT GAGAL] java.lang.AssertionError: " + e.getMessage());
        }
    }
}
