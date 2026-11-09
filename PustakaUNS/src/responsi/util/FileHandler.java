package responsi.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import responsi.model.Anggota;
import responsi.model.Buku;

/**
 * Semua operasi file: katalog.txt (teks) dan data.obj (serialization).
 * Pesan error ditampilkan di sini agar program tidak crash.
 */
public class FileHandler {
    public static final String FILE_KATALOG = "katalog.txt";
    public static final String FILE_ANGGOTA = "data.obj";
    private static final String PEMISAH = ";";
    private static final int JUMLAH_KOLOM = 6;

    /** Simpan katalog, satu buku per baris: kode;judul;pengarang;kategori;tahun;stok */
    public static boolean simpanKatalog(List<Buku> katalog) {
        // try-with-resources: writer otomatis ditutup walau terjadi error
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_KATALOG))) {
            for (Buku b : katalog) {
                writer.write(bersihkan(b.getKodeBuku()) + PEMISAH
                        + bersihkan(b.getJudul()) + PEMISAH
                        + bersihkan(b.getPengarang()) + PEMISAH
                        + bersihkan(b.getKategori()) + PEMISAH
                        + b.getTahunTerbit() + PEMISAH
                        + b.getStok());
                writer.newLine();
            }
            return true;
        } catch (IOException e) {
            System.out.println("[ERROR] Gagal menyimpan " + FILE_KATALOG + ": " + e.getMessage());
            return false;
        }
    }

    /** Baca katalog.txt. Mengembalikan null jika file tidak ada / gagal dibaca. */
    public static List<Buku> muatKatalog() {
        List<Buku> hasil = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_KATALOG))) {
            String baris;
            int nomorBaris = 0;
            while ((baris = reader.readLine()) != null) {
                nomorBaris++;
                if (baris.trim().isEmpty()) {
                    continue;
                }
                String[] f = baris.split(PEMISAH);
                if (f.length != JUMLAH_KOLOM) {
                    System.out.println("[PERINGATAN] Baris " + nomorBaris + " dilewati (format salah).");
                    continue;
                }
                try {
                    hasil.add(new Buku(f[0].trim(), f[1].trim(), f[2].trim(), f[3].trim(),
                            Integer.parseInt(f[4].trim()), Integer.parseInt(f[5].trim())));
                } catch (NumberFormatException e) {
                    System.out.println("[PERINGATAN] Baris " + nomorBaris + " dilewati (tahun/stok bukan angka).");
                }
            }
            return hasil;
        } catch (FileNotFoundException e) {
            System.out.println("[ERROR] File " + FILE_KATALOG + " belum ada. Simpan data terlebih dahulu.");
            return null;
        } catch (IOException e) {
            System.out.println("[ERROR] Gagal membaca " + FILE_KATALOG + ": " + e.getMessage());
            return null;
        }
    }

    /** Serialization: simpan daftar anggota ke data.obj. */
    public static boolean simpanAnggota(List<Anggota> daftar) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_ANGGOTA))) {
            out.writeObject(new ArrayList<>(daftar));
            return true;
        } catch (IOException e) {
            System.out.println("[ERROR] Gagal menyimpan " + FILE_ANGGOTA + ": " + e.getMessage());
            return false;
        }
    }

    /** Deserialization: muat anggota dari data.obj. Mengembalikan null jika gagal. */
    @SuppressWarnings("unchecked")
    public static List<Anggota> muatAnggota() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_ANGGOTA))) {
            return (List<Anggota>) in.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("[INFO] File " + FILE_ANGGOTA + " belum ada, data anggota tidak dimuat.");
            return null;
        } catch (ClassNotFoundException e) {
            System.out.println("[ERROR] Isi " + FILE_ANGGOTA + " tidak dikenali (class tidak ditemukan).");
            return null;
        } catch (IOException e) {
            System.out.println("[ERROR] Gagal membaca " + FILE_ANGGOTA + ": " + e.getMessage());
            return null;
        }
    }

    /** Titik koma pada data diganti koma agar tidak merusak format file. */
    private static String bersihkan(String teks) {
        return teks.replace(PEMISAH, ",");
    }
}
