package responsi.util;

/**
 * Kumpulan method validasi dan pengolahan String/Character.
 */
public class Validator {
    private static final String PREFIX_ID = "AGT";
    private static final int JUMLAH_DIGIT_ID = 3;
    private static final String VOKAL = "aeiou";

    /** Format ID anggota harus AGT + 3 digit, contoh AGT001. */
    public static boolean validasiIdAnggota(String id) {
        if (id == null || id.length() != PREFIX_ID.length() + JUMLAH_DIGIT_ID) {
            return false;
        }
        if (!id.startsWith(PREFIX_ID)) {
            return false;
        }
        String bagianAngka = id.substring(PREFIX_ID.length());
        for (int i = 0; i < bagianAngka.length(); i++) {
            if (!Character.isDigit(bagianAngka.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /** Trim spasi, lalu huruf pertama tiap kata dijadikan kapital (sisanya kecil). */
    public static String normalisasiJudul(String judul) {
        if (judul == null) {
            return "";
        }
        String bersih = judul.trim();
        StringBuilder hasil = new StringBuilder();
        boolean awalKata = true;
        for (int i = 0; i < bersih.length(); i++) {
            char c = bersih.charAt(i);
            if (c == ' ') {
                if (hasil.charAt(hasil.length() - 1) != ' ') { // spasi ganda dijadikan satu
                    hasil.append(' ');
                }
                awalKata = true;
            } else {
                hasil.append(awalKata ? Character.toUpperCase(c) : Character.toLowerCase(c));
                awalKata = false;
            }
        }
        return hasil.toString();
    }

    /** Pencarian judul tidak peka huruf besar/kecil. */
    public static boolean cariJudul(String judul, String kataKunci) {
        if (judul == null || kataKunci == null) {
            return false;
        }
        return judul.toLowerCase().contains(kataKunci.toLowerCase());
    }

    /** Menghitung jumlah huruf vokal. */
    public static int hitungVokal(String teks) {
        int jumlah = 0;
        if (teks == null) {
            return jumlah;
        }
        for (int i = 0; i < teks.length(); i++) {
            char c = teks.charAt(i);
            if (Character.isLetter(c) && VOKAL.indexOf(Character.toLowerCase(c)) >= 0) {
                jumlah++;
            }
        }
        return jumlah;
    }

    /** Mengembalikan array {jumlahHuruf, jumlahAngka}. */
    public static int[] hitungHurufDanAngka(String teks) {
        int huruf = 0;
        int angka = 0;
        if (teks != null) {
            for (char c : teks.toCharArray()) {
                if (Character.isLetter(c)) {
                    huruf++;
                } else if (Character.isDigit(c)) {
                    angka++;
                }
            }
        }
        return new int[] { huruf, angka };
    }

    /** Membalik String (dipakai untuk "kode verifikasi"). */
    public static String balikString(String teks) {
        if (teks == null) {
            return "";
        }
        return new StringBuilder(teks).reverse().toString();
    }
}
