/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nilai_mahasiswa.model;

public class Mahasiswa {

    public static final double FINAL_PASSING_GRADE = 70.0;

    private final String nama;
    private final String nim;
    private final double nilai;

    public Mahasiswa(String nama, String nim, double nilai) {
        this.nama = nama.trim().toUpperCase();
        this.nim = nim.trim();

        // Pengaman logika programmer (aktif jika dijalankan dengan -ea)
        assert !this.nim.isEmpty() : "NIM tidak boleh kosong!";

        this.nilai = nilai;
    }

    public String getNama() {
        return nama;
    }

    public String getNim() {
        return nim;
    }

    public double getNilai() {
        return nilai;
    }

    public String getGrade() {
        if (nilai >= 85) {
            return "A";
        } else if (nilai >= 75) {
            return "B";
        } else if (nilai >= FINAL_PASSING_GRADE) {
            return "C";
        } else {
            return "D";
        }
    }

    public boolean isLulus() {
        return nilai >= FINAL_PASSING_GRADE;
    }

    public String getStatus() {
        return isLulus() ? "Lulus" : "Tidak Lulus";
    }
}
