/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nilai_mahasiswa.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import nilai_mahasiswa.exception.NilaiInvalidException;
import nilai_mahasiswa.model.Mahasiswa;
import nilai_mahasiswa.service.LaporanService;

public class MainApp {

    private static final String NAMA_FILE = "laporan_nilai.txt";

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        List<Mahasiswa> daftarMahasiswa = new ArrayList<>();

        System.out.println("=== PROGRAM LAPORAN NILAI MAHASISWA ===");
        int jumlah = bacaJumlah(input);

        for (int i = 1; i <= jumlah; i++) {
            System.out.println();
            System.out.println("Mahasiswa ke-" + i);

            String nim = bacaNim(input, daftarMahasiswa);
            String nama = bacaNama(input);

            double nilai;
            while (true) {
                try {
                    nilai = bacaNilai(input);
                    break;
                } catch (NilaiInvalidException e) {
                    System.out.println("[ERROR] " + e.getMessage());
                }
            }

            daftarMahasiswa.add(new Mahasiswa(nama, nim, nilai));
        }

        try {
            LaporanService.tulisLaporan(daftarMahasiswa, NAMA_FILE);
            System.out.println();
            System.out.println("Laporan berhasil ditulis ke " + NAMA_FILE);

            System.out.println();
            System.out.println("=== ISI FILE " + NAMA_FILE + " ===");
            LaporanService.bacaLaporan(NAMA_FILE);
            System.out.println("=== SELESAI ===");
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan file: " + e.getMessage());
        }

        input.close();
    }

    private static int bacaJumlah(Scanner input) {
        while (true) {
            System.out.print("Masukkan jumlah mahasiswa: ");
            try {
                int jumlah = Integer.parseInt(input.nextLine().trim());
                if (jumlah > 0) {
                    return jumlah;
                }
                System.out.println("[ERROR] Jumlah mahasiswa harus lebih dari 0.");
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Input harus berupa angka bulat.");
            }
        }
    }

    // NIM: tidak boleh kosong, hanya angka (Character.isDigit), dan tidak duplikat
    private static String bacaNim(Scanner input, List<Mahasiswa> daftar) {
        while (true) {
            System.out.print("NIM   : ");
            String nim = input.nextLine().trim();

            if (nim.isEmpty()) {
                System.out.println("[ERROR] NIM tidak boleh kosong.");
                continue;
            }

            boolean semuaAngka = true;
            for (char c : nim.toCharArray()) {
                if (!Character.isDigit(c)) {
                    semuaAngka = false;
                    break;
                }
            }
            if (!semuaAngka) {
                System.out.println("[ERROR] NIM hanya boleh berisi angka.");
                continue;
            }

            boolean duplikat = false;
            for (Mahasiswa m : daftar) {
                if (m.getNim().equals(nim)) {
                    duplikat = true;
                    break;
                }
            }
            if (duplikat) {
                System.out.println("[ERROR] NIM sudah terdaftar.");
                continue;
            }

            return nim;
        }
    }

    private static String bacaNama(Scanner input) {
        while (true) {
            System.out.print("Nama  : ");
            String nama = input.nextLine().trim();
            if (!nama.isEmpty()) {
                return nama;
            }
            System.out.println("[ERROR] Nama tidak boleh kosong.");
        }
    }

    // Melempar NilaiInvalidException jika nilai di luar 0 - 100
    private static double bacaNilai(Scanner input) throws NilaiInvalidException {
        while (true) {
            System.out.print("Nilai : ");
            try {
                double nilai = Double.parseDouble(input.nextLine().trim().replace(',', '.'));
                if (nilai < 0 || nilai > 100) {
                    throw new NilaiInvalidException(
                            "Nilai " + nilai + " tidak valid. Harus 0 sampai 100.");
                }
                return nilai;
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Nilai harus berupa angka.");
            }
        }
    }
}
