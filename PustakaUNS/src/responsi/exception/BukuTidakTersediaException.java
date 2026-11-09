package responsi.exception;

// Custom exception (checked): dilempar ketika buku yang ingin dipinjam stoknya 0.

public class BukuTidakTersediaException extends Exception {
    private static final long serialVersionUID = 1L;

    public BukuTidakTersediaException(String pesan) {
        super(pesan);
    }
}
