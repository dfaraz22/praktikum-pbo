package detektorpinjol;

public class AplikasiBaru extends AplikasiPinjaman {
    public AplikasiBaru(String nama, double bunga, double admin, boolean kontak) {
        super(nama, bunga, admin, kontak);
    }

    @Override
    public int hitungSkorRisiko() {
        int skor = 30;
        if (isBungaTinggi()) skor += 20;
        if (isAdminTinggi()) skor += 15;
        if (isMintaAksesKontak()) skor += 25;
        return Math.min(skor, 100);
    }

    @Override
    public String getStatusIzin() { return "BARU / BELUM TERVERIFIKASI"; }

    @Override
    public void tampilkanPeringatan() {
        System.out.println(" - Izin belum terverifikasi, cek dulu di ojk.go.id atau WA OJK 081-157-157-157.");
        if (isMintaAksesKontak()) {
            System.out.println(" - Meminta akses kontak, sangat berisiko untuk aplikasi baru.");
        }
        if (isBungaTinggi() || isAdminTinggi()) {
            System.out.println(" - Bunga/admin tinggi, hindari sebelum statusnya jelas.");
        }
    }
}
