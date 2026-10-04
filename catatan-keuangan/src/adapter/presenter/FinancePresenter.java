package adapter.presenter;

import domain.entity.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Presenter yang HANYA memformat data menjadi String.
 * Tidak mencetak apa pun; pencetakan dilakukan oleh View.
 */
public class FinancePresenter {

    /** Format satu transaksi: id | keterangan | Rp jumlah | jenis. */
    private String format(Transaction t) {
        return String.format("%d | %s | Rp %d | %s",
                t.getId(), t.getDescription(), t.getAmount(), t.getType().getLabel());
    }

    /** Header + baris transaksi. Jika emptyMessage tidak null, dipakai saat daftar kosong. */
    private List<String> formatLines(String header, List<Transaction> transactions, String emptyMessage) {
        List<String> lines = new ArrayList<>();
        lines.add(header);

        if (transactions.isEmpty() && emptyMessage != null) {
            lines.add(emptyMessage);
        }
        for (Transaction t : transactions) {
            lines.add(format(t));
        }
        return lines;
    }

    private String join(List<String> lines) {
        return String.join(System.lineSeparator(), lines);
    }

    public String formatTransactionList(List<Transaction> transactions, long balance) {
        List<String> lines = formatLines("Daftar Transaksi:", transactions, "- Belum ada transaksi!");
        lines.add("Saldo: Rp " + balance);
        return join(lines);
    }

    public String formatSortedList(List<Transaction> transactions) {
        return join(formatLines("Daftar Transaksi (Terurut):", transactions, null));
    }

    public String formatSearchResult(String keyword, List<Transaction> transactions) {
        return join(formatLines("Hasil Pencarian: \"" + keyword + "\"", transactions,
                "- Transaksi tidak ditemukan!"));
    }

    public String formatAddSuccess(Transaction transaction) {
        return "Berhasil menambah transaksi: " + format(transaction);
    }

    public String formatBalance(long balance) {
        return "Saldo saat ini: Rp " + balance;
    }

    public String formatDeleteSuccess() {
        return "Berhasil menghapus transaksi.";
    }

    public String formatDeleteFailed(int id) {
        return "[!] Gagal menghapus transaksi dengan ID: " + id + ".";
    }

    public String formatInvalidChoice() {
        return "[!] Pilihan tidak dimengerti.";
    }

    public String formatInvalidId() {
        return "[!] ID tidak valid!";
    }

    public String formatInvalidAmount() {
        return "[!] Jumlah tidak valid!";
    }

    public String formatInvalidSortOption() {
        return "[!] Pilihan tidak valid!";
    }
}
