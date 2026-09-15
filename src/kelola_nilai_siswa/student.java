/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kelola_nilai_siswa;

/**
 *
 * @author ACER
 */
public class student {
    // Instance variable (field milik tiap objek)
    String name;
    double score;
    boolean passed;

    // Constructor untuk mengisi name dan score
    public student(String name, double score) {
        this.name = name;
        this.score = score;
        this.passed = false; // default sebelum dicek
    }

    // Method untuk mengecek kelulusan
    public void checkPassed() {
        if (this.score >= 70) {
            this.passed = true;
        } else {
            this.passed = false;
        }
    }
}

