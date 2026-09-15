/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kelola_nilai_siswa;

/**
 *
 * @author ACER
 */
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("==========================================");
        System.out.println("     PROGRAM MANAJEMEN NILAI SISWA       ");
        System.out.println("==========================================");

        System.out.print("Masukkan jumlah siswa: ");
        int jumlahSiswa = Integer.parseInt(scanner.nextLine().trim());

        student[] daftarSiswa = new student[jumlahSiswa];

        // Input data siswa
        for (int i = 0; i < jumlahSiswa; i++) {
            System.out.println();
            System.out.println("Data siswa ke-" + (i + 1) + ":");

            System.out.print("Masukkan nama: ");
            String nama = scanner.nextLine();

            System.out.print("Masukkan nilai: ");
            double nilai = Double.parseDouble(scanner.nextLine().trim());

            daftarSiswa[i] = new student(nama, nilai);
        }

        // Cek kelulusan semua siswa (objek Student memanggil method-nya sendiri)
        for (int i = 0; i < jumlahSiswa; i++) {
            daftarSiswa[i].checkPassed();
        }

        // Tampilkan hasil per siswa
        System.out.println("------------------------------------------");
        System.out.println("                   HASIL                  ");
        System.out.println("------------------------------------------");
        System.out.println();
        double totalNilai = 0;
        int jumlahLulus = 0;
        int jumlahTidakLulus = 0;

        for (int i = 0; i < jumlahSiswa; i++) {
            student s = daftarSiswa[i];
            String status = s.passed ? "Lulus" : "Tidak Lulus";

            System.out.println("Siswa: " + s.name + " - Nilai: " + s.score + " - " + status);

            totalNilai += s.score;

            if (s.passed) {
                jumlahLulus++;
            } else {
                jumlahTidakLulus++;
            }
        }

        double rataRata = totalNilai / jumlahSiswa;

        System.out.println();
        System.out.printf("Rata-rata nilai kelas: %.2f%n", rataRata);
        System.out.println("Jumlah siswa lulus: " + jumlahLulus);
        System.out.println("Jumlah siswa tidak lulus: " + jumlahTidakLulus);
        System.out.println("==========================================");
        System.out.println("==========================================");

        scanner.close();
    }
}