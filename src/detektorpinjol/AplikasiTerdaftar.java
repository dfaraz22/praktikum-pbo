package detektorpinjol;

public class AplikasiTerdaftar extends AplikasiPinjaman {
    public AplikasiTerdaftar(String nama, double bunga, double admin, boolean kontak) {
        super(nama, bunga, admin, kontak);
    }

    @Override
    public int hitungSkorRisiko() {
        int skor = 10;
        if (isBungaTinggi()) skor += 20;
        if (isAdminTinggi()) skor += 15;
        if (isMintaAksesKontak()) skor += 25;
        return Math.min(skor, 100);
    }

    @Override
    public String getStatusIzin() { return "TERDAFTAR OJK"; }

    @Override
    public void tampilkanPeringatan() {
        boolean adaMasalah = false;
        if (isBungaTinggi()) {
            System.out.println(" - Bunga di atas batas wajar meski terdaftar.");
            adaMasalah = true;
        }
        if (isAdminTinggi()) {
            System.out.println(" - Biaya admin terlalu besar.");
            adaMasalah = true;
        }
        if (isMintaAksesKontak()) {
            System.out.println(" - Meminta akses kontak/galeri, tidak sesuai aturan OJK.");
            adaMasalah = true;
        }
        if (!adaMasalah) {
            System.out.println("Catatan: tetap verifikasi izin di ojk.go.id sebelum meminjam.");
        }
    }
}
