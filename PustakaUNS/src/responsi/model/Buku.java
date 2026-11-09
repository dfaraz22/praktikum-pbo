package responsi.model;

import java.io.Serializable;

public class Buku implements Serializable {
    private static final long serialVersionUID = 1L;

    // Class variable: menghitung total buku yang pernah dibuat
    public static int totalBuku = 0;

    // Instance variable: atribut khas tiap objek buku
    private String kodeBuku;
    private String judul;
    private String pengarang;
    private String kategori;
    private int tahunTerbit;
    private int stok;

    public Buku(String kodeBuku, String judul, String pengarang,
                String kategori, int tahunTerbit, int stok) {
        // Assertion: memastikan judul tidak null saat objek dibuat
        assert judul != null : "Judul tidak boleh null";

        this.kodeBuku = kodeBuku;
        this.judul = judul;
        this.pengarang = pengarang;
        this.kategori = kategori;
        this.tahunTerbit = tahunTerbit;
        this.stok = stok;
        totalBuku++; 
    }

    public String getKodeBuku() { return kodeBuku; }
    public String getJudul() { return judul; }
    public String getPengarang() { return pengarang; }
    public String getKategori() { return kategori; }
    public int getTahunTerbit() { return tahunTerbit; }
    public int getStok() { return stok; }

    public void setStok(int jumlahBaru) {
        this.stok = jumlahBaru;
        // Assertion: invarian bahwa stok tidak boleh negatif
        assert this.stok >= 0 : "Stok tidak boleh negatif!";
    }

    public void kurangiStok() {
        stok--;
        assert stok >= 0 : "Stok tidak boleh negatif!";
    }

    public void tambahStok() {
        stok++;
    }

    public void tampilkanInfo() {
        // Local variable: string informasi buku sementara
        System.out.println("[" + kodeBuku + "] " + judul + " - " + pengarang + " (" + tahunTerbit + ") | " + kategori + " | Stok: " + stok);
    }
}