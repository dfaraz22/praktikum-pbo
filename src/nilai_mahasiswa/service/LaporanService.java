package nilai_mahasiswa.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import nilai_mahasiswa.model.Mahasiswa;

public class LaporanService {

    private static final String FORMAT_BARIS = "%-3s | %-12s | %-14s | %-6s | %-5s | %s";
    private static final String GARIS_UTAMA =
            "==================================================================";
    private static final String GARIS_HEADER =
            "----+--------------+----------------+--------+-------+------------";

    public static double hitungRataRata(List<Mahasiswa> daftar) {
        if (daftar.isEmpty()) {
            return 0;
        }
        double total = 0;
        for (Mahasiswa m : daftar) {
            total += m.getNilai();
        }
        return total / daftar.size();
    }

    public static int hitungJumlahLulus(List<Mahasiswa> daftar) {
        int jumlah = 0;
        for (Mahasiswa m : daftar) {
            if (m.isLulus()) {
                jumlah++;
            }
        }
        return jumlah;
    }

    public static void tulisLaporan(List<Mahasiswa> daftar, String namaFile) throws IOException {
        int jumlahLulus = hitungJumlahLulus(daftar);
        int jumlahTidakLulus = daftar.size() - jumlahLulus;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(namaFile))) {
            writer.write("LAPORAN NILAI MAHASISWA");
            writer.newLine();
            writer.write(GARIS_UTAMA);
            writer.newLine();
            writer.write(String.format(FORMAT_BARIS, "No", "NIM", "Nama", "Nilai", "Grade", "Status"));
            writer.newLine();
            writer.write(GARIS_HEADER);
            writer.newLine();

            int nomor = 1;
            for (Mahasiswa m : daftar) {
                String nama = m.getNama();
                if (nama.length() > 14) {
                    nama = nama.substring(0, 14);
                }
                writer.write(String.format(Locale.US, FORMAT_BARIS,
                        String.valueOf(nomor), m.getNim(), nama,
                        String.format(Locale.US, "%.2f", m.getNilai()),
                        m.getGrade(), m.getStatus()));
                writer.newLine();
                nomor++;
            }

            writer.write(GARIS_UTAMA);
            writer.newLine();
            writer.write("RINGKASAN");
            writer.newLine();
            writer.write("Jumlah mahasiswa      : " + daftar.size());
            writer.newLine();
            writer.write(String.format(Locale.US, "Rata-rata nilai kelas : %.2f",
                    hitungRataRata(daftar)));
            writer.newLine();
            writer.write("Jumlah lulus          : " + jumlahLulus);
            writer.newLine();
            writer.write("Jumlah tidak lulus    : " + jumlahTidakLulus);
            writer.newLine();
        }
    }

    public static void bacaLaporan(String namaFile) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(namaFile))) {
            String baris;
            while ((baris = reader.readLine()) != null) {
                System.out.println(baris);
            }
        }
    }
}