package lab.kasir;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

public class KasirFrame extends JFrame {

    private JTextField txtMenu;
    private JTextField txtHarga;
    private JTextField txtJumlah;
    private JTextArea areaStruk;
    private JLabel lblTotal;
    private JLabel lblStatus;
    private JButton btnCetak;

    private final Pesanan pesanan = new Pesanan();
    private final ArrayList<ItemPesanan> daftarPesanan = new ArrayList<>();

    private final NumberFormat formatRupiah =
            NumberFormat.getInstance(new Locale("id", "ID"));

    private static int nomorTransaksi = 1;
    private final String kodeTransaksi;

    private double totalBayar = 0;
    private boolean totalSudahDihitung = false;

    public KasirFrame() {
        kodeTransaksi = String.format("TRX-%03d", nomorTransaksi++);

        setTitle("Kasir Kantin UNS");
        setSize(440, 650);
        setLayout(null);
        getContentPane().setBackground(new Color(240, 247, 255));

        // Judul aplikasi
        JLabel lblJudul = new JLabel("KASIR KANTIN UNS");
        lblJudul.setBounds(20, 15, 360, 35);
        lblJudul.setFont(new Font("Arial", Font.BOLD, 20));
        lblJudul.setForeground(new Color(40, 100, 160));
        add(lblJudul);

        JLabel lblSubjudul = new JLabel("Sistem Kasir Sederhana");
        lblSubjudul.setBounds(20, 48, 360, 20);
        lblSubjudul.setFont(new Font("Arial", Font.PLAIN, 12));
        lblSubjudul.setForeground(new Color(90, 110, 130));
        add(lblSubjudul);

        // Input nama menu
        JLabel lblMenu = new JLabel("Nama Menu:");
        lblMenu.setBounds(20, 85, 100, 25);
        add(lblMenu);

        txtMenu = new JTextField();
        txtMenu.setBounds(130, 85, 270, 28);
        add(txtMenu);

        // Input harga
        JLabel lblHarga = new JLabel("Harga:");
        lblHarga.setBounds(20, 125, 100, 25);
        add(lblHarga);

        txtHarga = new JTextField();
        txtHarga.setBounds(130, 125, 270, 28);
        add(txtHarga);

        // Input jumlah
        JLabel lblJumlah = new JLabel("Jumlah:");
        lblJumlah.setBounds(20, 165, 100, 25);
        add(lblJumlah);

        txtJumlah = new JTextField();
        txtJumlah.setBounds(130, 165, 270, 28);
        add(txtJumlah);

        // Tombol tambah
        JButton btnTambah = new JButton("Tambah");
        btnTambah.setBounds(45, 215, 150, 35);
        btnTambah.setBackground(new Color(100, 170, 230));
        btnTambah.setForeground(Color.WHITE);
        btnTambah.setFocusPainted(false);
        add(btnTambah);

        // Tombol hitung total
        JButton btnHitung = new JButton("Hitung Total");
        btnHitung.setBounds(210, 215, 185, 35);
        btnHitung.setBackground(new Color(60, 130, 200));
        btnHitung.setForeground(Color.WHITE);
        btnHitung.setFocusPainted(false);
        add(btnHitung);

        // Area struk
        JLabel lblStruk = new JLabel("Struk Pembelian");
        lblStruk.setBounds(20, 265, 200, 25);
        lblStruk.setFont(new Font("Arial", Font.BOLD, 14));
        add(lblStruk);

        areaStruk = new JTextArea();
        areaStruk.setEditable(false);
        areaStruk.setFont(new Font("Monospaced", Font.PLAIN, 12));
        areaStruk.setBackground(Color.WHITE);
        areaStruk.setText("             KANTIN UNS\n"
                + "       UNIVERSITAS SEBELAS MARET\n"
                + "----------------------------------------\n"
                + "Belum ada pesanan.\n");

        JScrollPane scrollStruk = new JScrollPane(areaStruk);
        scrollStruk.setBounds(20, 295, 380, 175);
        add(scrollStruk);

        // Label total bayar
        lblTotal = new JLabel("Total Bayar: Rp 0");
        lblTotal.setBounds(20, 480, 380, 30);
        lblTotal.setFont(new Font("Arial", Font.BOLD, 17));
        lblTotal.setForeground(new Color(40, 100, 160));
        add(lblTotal);

        // Tombol cetak struk
        btnCetak = new JButton("Cetak Struk");
        btnCetak.setBounds(20, 525, 380, 35);
        btnCetak.setBackground(new Color(100, 170, 230));
        btnCetak.setForeground(Color.WHITE);
        btnCetak.setFocusPainted(false);
        add(btnCetak);

        // Label status
        lblStatus = new JLabel("Status: Belum mencetak struk");
        lblStatus.setBounds(20, 570, 380, 25);
        lblStatus.setForeground(new Color(40, 100, 160));
        add(lblStatus);

        // Event tombol
        btnTambah.addActionListener(e -> tambahPesanan());
        btnHitung.addActionListener(e -> hitungTotal());
        btnCetak.addActionListener(e -> cetakStruk());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void tambahPesanan() {
        String menu = txtMenu.getText().trim();
        String hargaText = txtHarga.getText().trim();
        String jumlahText = txtJumlah.getText().trim();

        if (menu.isEmpty() || hargaText.isEmpty() || jumlahText.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Semua field harus diisi!",
                    "Peringatan",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            double harga = Double.parseDouble(hargaText);
            int jumlah = Integer.parseInt(jumlahText);

            if (harga <= 0 || jumlah <= 0) {
                JOptionPane.showMessageDialog(this,
                        "Harga dan jumlah harus lebih dari 0!",
                        "Peringatan",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            pesanan.tambahItem(harga, jumlah);
            daftarPesanan.add(new ItemPesanan(menu, harga, jumlah));

            totalSudahDihitung = false;
            lblTotal.setText("Total Bayar: Rp 0");
            tampilkanDaftarSementara();

            txtMenu.setText("");
            txtHarga.setText("");
            txtJumlah.setText("");
            txtMenu.requestFocus();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Harga harus berupa angka dan jumlah harus berupa bilangan bulat!",
                    "Input Tidak Valid",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void tampilkanDaftarSementara() {
        StringBuilder teks = new StringBuilder();

        teks.append("             KANTIN UNS\n");
        teks.append("       UNIVERSITAS SEBELAS MARET\n");
        teks.append("----------------------------------------\n");
        teks.append("Daftar pesanan sementara:\n\n");

        for (ItemPesanan item : daftarPesanan) {
            teks.append(item.nama)
                    .append(" (").append(item.jumlah).append("x) = Rp ")
                    .append(formatRupiah.format(item.harga * item.jumlah))
                    .append("\n");
        }

        teks.append("\nKlik Hitung Total, lalu Cetak Struk.");

        areaStruk.setText(teks.toString());
        areaStruk.setCaretPosition(areaStruk.getDocument().getLength());
    }

    private void hitungTotal() {
        if (daftarPesanan.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Belum ada pesanan yang ditambahkan!",
                    "Peringatan",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        totalBayar = pesanan.hitungTotal();
        totalSudahDihitung = true;

        lblTotal.setText("Total Bayar: Rp "
                + formatRupiah.format(totalBayar));
    }

    private String buatIsiStruk() {
        LocalDateTime sekarang = LocalDateTime.now();

        String tanggal = sekarang.format(
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));

        double subtotal = pesanan.getSubtotal();
        double diskon = subtotal > 50000 ? subtotal * 0.10 : 0;

        StringBuilder struk = new StringBuilder();

        struk.append("             KANTIN UNS\n");
        struk.append("       UNIVERSITAS SEBELAS MARET\n");
        struk.append("          STRUK PEMBELIAN\n");
        struk.append("========================================\n");
        struk.append("No. Transaksi : ").append(kodeTransaksi).append("\n");
        struk.append("Tanggal       : ").append(tanggal).append("\n");
        struk.append("----------------------------------------\n");
        struk.append(String.format("%-18s %3s %12s%n",
                "Nama Menu", "Qty", "Subtotal"));
        struk.append("----------------------------------------\n");

        for (ItemPesanan item : daftarPesanan) {
            String nama = item.nama;
            if (nama.length() > 18) {
                nama = nama.substring(0, 18);
            }

            struk.append(String.format("%-18s %3d %12s%n",
                    nama,
                    item.jumlah,
                    formatRupiah.format(item.harga * item.jumlah)));
        }

        struk.append("----------------------------------------\n");
        struk.append(String.format("%-22s %16s%n",
                "Subtotal", formatRupiah.format(subtotal)));
        struk.append(String.format("%-22s %16s%n",
                "Diskon", "- " + formatRupiah.format(diskon)));
        struk.append("========================================\n");
        struk.append(String.format("%-22s %16s%n",
                "TOTAL BAYAR", formatRupiah.format(totalBayar)));
        struk.append("========================================\n");
        struk.append("          TERIMA KASIH!\n");
        struk.append("     Selamat menikmati pesanan Anda.\n");
        struk.append("              KANTIN UNS\n");

        return struk.toString();
    }

    private void cetakStruk() {
        if (daftarPesanan.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Belum ada pesanan yang dapat dicetak!",
                    "Peringatan",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!totalSudahDihitung) {
            hitungTotal();
            if (!totalSudahDihitung) {
                return;
            }
        }

        final String isiStruk = buatIsiStruk();

        lblStatus.setText("Status: Mencetak struk...");
        btnCetak.setEnabled(false);

        Thread threadCetak = new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();

                SwingUtilities.invokeLater(() -> {
                    lblStatus.setText("Status: Cetak dibatalkan");
                    btnCetak.setEnabled(true);
                });
                return;
            }

            SwingUtilities.invokeLater(() -> {
                areaStruk.setText(isiStruk);
                areaStruk.setCaretPosition(0);
                lblStatus.setText("Status: Struk selesai");
                btnCetak.setEnabled(true);
            });
        });

        threadCetak.start();
    }

    private static class ItemPesanan {
        private final String nama;
        private final double harga;
        private final int jumlah;

        private ItemPesanan(String nama, double harga, int jumlah) {
            this.nama = nama;
            this.harga = harga;
            this.jumlah = jumlah;
        }
    }
}
