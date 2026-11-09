/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nilai_mahasiswa.exception;

/**
 * Exception khusus yang dilempar jika nilai berada di luar rentang 0 - 100.
 */
public class NilaiInvalidException extends Exception {
 
    public NilaiInvalidException(String pesan) {
        super(pesan);
    }
}
 
