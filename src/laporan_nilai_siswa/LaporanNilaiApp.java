/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package laporan_nilai_siswa;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

public class LaporanNilaiApp {

    private static final String NAMA_FILE = "laporan_nilai.txt";
    private static final double NILAI_KELULUSAN = 70.0;

    private static final String GARIS_UTAMA = "========================================";
    private static final String FORMAT_BARIS = "%-3s | %-10s | %-6s | %s";
    private static final String GARIS_HEADER = "----+------------+--------+------------";

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("=== PROGRAM LAPORAN NILAI SISWA ===");

        tulisLaporan(input);

        System.out.println();
        System.out.println("=== ISI FILE " + NAMA_FILE + " ===");
        bacaLaporan();
        System.out.println("=== SELESAI ===");

        input.close();
    }

    // Tahap 1: input dari konsol, lalu tulis ke file
    private static void tulisLaporan(Scanner input) {
        int jumlahSiswa = bacaJumlahSiswa(input);

        double totalNilai = 0;
        int jumlahLulus = 0;
        int jumlahTidakLulus = 0;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(NAMA_FILE))) {
            writer.write("LAPORAN NILAI SISWA");
            writer.newLine();
            writer.write(GARIS_UTAMA);
            writer.newLine();
            writer.write(String.format(FORMAT_BARIS, "No", "Nama", "Nilai", "Status"));
            writer.newLine();
            writer.write(GARIS_HEADER);
            writer.newLine();

            for (int i = 1; i <= jumlahSiswa; i++) {
                System.out.println();
                System.out.println("Siswa ke-" + i);
                System.out.print("Nama  : ");
                String nama = input.nextLine().trim();
                if (nama.isEmpty()) {
                    nama = "(tanpa nama)";
                }
                if (nama.length() > 10) {
                    nama = nama.substring(0, 10);
                }

                double nilai = bacaNilai(input);
                String status = tentukanStatus(nilai);

                totalNilai += nilai;
                if (nilai >= NILAI_KELULUSAN) {
                    jumlahLulus++;
                } else {
                    jumlahTidakLulus++;
                }

                writer.write(String.format(Locale.US, FORMAT_BARIS,
                        String.valueOf(i), nama, String.format(Locale.US, "%.2f", nilai), status));
                writer.newLine();
            }

            double rataRata = totalNilai / jumlahSiswa;

            writer.write(GARIS_UTAMA);
            writer.newLine();
            writer.write("RINGKASAN");
            writer.newLine();
            writer.write(String.format(Locale.US, "Rata-rata nilai kelas : %.2f", rataRata));
            writer.newLine();
            writer.write("Jumlah siswa lulus    : " + jumlahLulus);
            writer.newLine();
            writer.write("Jumlah tidak lulus    : " + jumlahTidakLulus);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Gagal menulis file: " + e.getMessage());
            return;
        }

        System.out.println();
        System.out.println("Laporan berhasil ditulis ke " + NAMA_FILE);
    }

    // Tahap 2: baca file baris demi baris, tampilkan ke konsol
    private static void bacaLaporan() {
        try (BufferedReader reader = new BufferedReader(new FileReader(NAMA_FILE))) {
            String baris;
            while ((baris = reader.readLine()) != null) {
                System.out.println(baris);
            }
        } catch (IOException e) {
            System.out.println("Gagal membaca file: " + e.getMessage());
        }
    }

    private static String tentukanStatus(double nilai) {
        return nilai >= NILAI_KELULUSAN ? "Lulus" : "Tidak Lulus";
    }

    // Input jumlah siswa, ulangi sampai valid (> 0)
    private static int bacaJumlahSiswa(Scanner input) {
        while (true) {
            System.out.print("Masukkan jumlah siswa: ");
            try {
                int jumlah = Integer.parseInt(input.nextLine().trim());
                if (jumlah > 0) {
                    return jumlah;
                }
                System.out.println("Jumlah siswa harus lebih dari 0.");
            } catch (NumberFormatException e) {
                System.out.println("Input harus berupa angka bulat.");
            }
        }
    }

    // Input nilai, ulangi sampai valid (0 - 100). Koma desimal juga diterima.
    private static double bacaNilai(Scanner input) {
        while (true) {
            System.out.print("Nilai : ");
            try {
                double nilai = Double.parseDouble(input.nextLine().trim().replace(',', '.'));
                if (nilai >= 0 && nilai <= 100) {
                    return nilai;
                }
                System.out.println("Nilai harus antara 0 sampai 100.");
            } catch (NumberFormatException e) {
                System.out.println("Input harus berupa angka.");
            }
        }
    }
}
