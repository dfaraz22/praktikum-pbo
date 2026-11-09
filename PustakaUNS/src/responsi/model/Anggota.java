package responsi.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import responsi.service.Perpustakaan;

public class Anggota implements Serializable {
    private static final long serialVersionUID = 1L;

    // Instance variable
    private String idAnggota;
    private String nama;
    private char status; 
    private List<Buku> daftarPinjam;

    // Parameter variable pada constructor
    public Anggota(String idAnggota, String nama, char status) {
        this.idAnggota = idAnggota;
        this.nama = nama;
        this.status = status;
        this.daftarPinjam = new ArrayList<>();
    }

    public String getIdAnggota() { return idAnggota; }
    public String getNama() { return nama; }
    public char getStatus() { return status; }
    public void setStatus(char status) { this.status = status; }
    public List<Buku> getDaftarPinjam() { return daftarPinjam; }

    public void tambahPinjam(Buku buku) {
        daftarPinjam.add(buku);
    }

    public boolean hapusPinjam(Buku buku) {
        return daftarPinjam.remove(buku);
    }

    public Buku cariDiDaftarPinjam(String kode) {
        for (Buku b : daftarPinjam) {
            if (b.getKodeBuku().equalsIgnoreCase(kode)) {
                return b;
            }
        }
        return null;
    }

    public void tampilkanInfo() {
        // Local variable untuk teks status anggota
        String teksStatus = (status == Perpustakaan.STATUS_AKTIF) ? "Aktif" : "Nonaktif (diblokir sementara)";
        System.out.println("ID     : " + idAnggota);
        System.out.println("Nama   : " + nama);
        System.out.println("Status : " + teksStatus);
        System.out.println("Pinjam : " + daftarPinjam.size() + "/" + Perpustakaan.MAX_PINJAM + " buku");
        
        for (Buku b : daftarPinjam) {
            System.out.println("   - [" + b.getKodeBuku() + "] " + b.getJudul());
        }
    }
}