package detektorpinjol;

public abstract class AplikasiPinjaman implements DapatDilaporkan {
    // Encapsulation: semua atribut private
    private String namaAplikasi;
    private double bungaPerBulan;   // dalam persen
    private double biayaAdmin;      // dalam persen, dipotong di muka
    private boolean mintaAksesKontak;

    protected static final double BATAS_BUNGA_TINGGI = 5.0;
    protected static final double BATAS_ADMIN_TINGGI = 10.0;

    public AplikasiPinjaman(String namaAplikasi, double bungaPerBulan,
                            double biayaAdmin, boolean mintaAksesKontak) {
        this.namaAplikasi = namaAplikasi;
        setBungaPerBulan(bungaPerBulan);
        setBiayaAdmin(biayaAdmin);
        this.mintaAksesKontak = mintaAksesKontak;
    }

    // Getter
    public String getNamaAplikasi() { return namaAplikasi; }
    public double getBungaPerBulan() { return bungaPerBulan; }
    public double getBiayaAdmin() { return biayaAdmin; }
    public boolean isMintaAksesKontak() { return mintaAksesKontak; }

    // Setter (dengan validasi)
    public void setNamaAplikasi(String namaAplikasi) {
        if (namaAplikasi != null && !namaAplikasi.trim().isEmpty()) {
            this.namaAplikasi = namaAplikasi;
        }
    }
    public void setBungaPerBulan(double bungaPerBulan) {
        this.bungaPerBulan = Math.max(0, bungaPerBulan);
    }
    public void setBiayaAdmin(double biayaAdmin) {
        this.biayaAdmin = Math.max(0, biayaAdmin);
    }
    public void setMintaAksesKontak(boolean mintaAksesKontak) {
        this.mintaAksesKontak = mintaAksesKontak;
    }

    // Helper untuk subclass
    protected boolean isBungaTinggi() { return bungaPerBulan > BATAS_BUNGA_TINGGI; }
    protected boolean isAdminTinggi() { return biayaAdmin > BATAS_ADMIN_TINGGI; }

    // Method abstract: wajib di-override setiap subclass
    public abstract int hitungSkorRisiko();
    public abstract String getStatusIzin();

    // Overloading 1: tenor default 1 bulan
    public double hitungBiayaTotal(double pinjaman) {
        return hitungBiayaTotal(pinjaman, 1);
    }

    // Overloading 2: dengan tenor
    public double hitungBiayaTotal(double pinjaman, int tenorBulan) {
        double admin = hitungPotonganAdmin(pinjaman);
        double bunga = pinjaman * bungaPerBulan / 100 * tenorBulan;
        return admin + bunga;
    }

    public double hitungPotonganAdmin(double pinjaman) {
        return pinjaman * biayaAdmin / 100;
    }

    public double hitungTotalBayar(double pinjaman, int tenorBulan) {
        return pinjaman + pinjaman * bungaPerBulan / 100 * tenorBulan;
    }

    public String tentukanLevel() {
        int skor = hitungSkorRisiko();
        if (skor <= 30) return "AMAN";
        if (skor <= 60) return "WASPADA";
        return "BAHAYA";
    }

    @Override
    public void laporkanKeOJK() {
        System.out.println(">> Laporan terkirim (simulasi) untuk aplikasi \"" + namaAplikasi + "\".");
        System.out.println("   Kontak resmi: 157 / ojk.go.id");
    }
}
