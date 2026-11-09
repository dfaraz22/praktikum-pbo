package responsi.exception;

// Custom exception (checked): dilempar ketika anggota sudah meminjam
// sebanyak MAX_PINJAM buku.

public class BatasPinjamException extends Exception {
    private static final long serialVersionUID = 1L;

    public BatasPinjamException(String pesan) {
        super(pesan);
    }
}
