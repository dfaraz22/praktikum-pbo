package detektorpinjol;

public class AplikasiTidakTerdaftar extends AplikasiPinjaman {
    public AplikasiTidakTerdaftar(String nama, double bunga, double admin, boolean kontak) {
        super(nama, bunga, admin, kontak);
    }

    @Override
    public int hitungSkorRisiko() {
        int skor = 50;
        if (isBungaTinggi()) skor += 15;
        if (isAdminTinggi()) skor += 15;
        if (isMintaAksesKontak()) skor += 20;
        return Math.min(skor, 100);
    }

    @Override
    public String getStatusIzin() { return "TIDAK TERDAFTAR OJK"; }

    @Override
    public void tampilkanPeringatan() {
        System.out.println(" - Aplikasi tidak terdaftar di OJK, JANGAN dipakai.");
        if (isMintaAksesKontak()) {
            System.out.println(" - Meminta akses kontak, rawan penyebaran data & teror penagihan.");
        }
        if (isBungaTinggi() || isAdminTinggi()) {
            System.out.println(" - Bunga dan admin jauh di atas kewajaran.");
        }
    }
}
