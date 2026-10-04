package adapter.presenter;

import domain.entity.Item;
import java.util.ArrayList;
import java.util.List;

/**
 * Presenter yang memformat data dari use case menjadi String (tidak mencetak; pencetakan dilakukan oleh View).
 * Format tampilan dipisahkan dari entity (Item tidak punya toString()).
 */
public class ItemPresenter {

    /** Memformat satu barang menjadi baris teks: id | nama | jumlah | kategori. */
    private String format(Item item) {
        return String.format("%d | %s | %d | %s",
                item.getId(), item.getName(), item.getQuantity(), item.getCategory());
    }

    /**
     * Helper umum untuk memformat daftar barang.
     * Mengembalikan pesan kosong jika list tidak berisi data.
     */
    private String formatList(List<Item> items, String header, String emptyMessage) {
        List<String> lines = new ArrayList<>();
        lines.add(header);

        if (items.isEmpty()) {
            lines.add(emptyMessage);
        } else {
            for (Item item : items) {
                lines.add(format(item));
            }
        }
        return String.join(System.lineSeparator(), lines);
    }

    /** Memformat daftar semua barang. */
    public String formatItems(List<Item> items) {
        return formatList(items, "Daftar Barang:", "- Data barang belum tersedia!");
    }

    /** Memformat hasil pencarian berdasarkan kata kunci. */
    public String formatSearchResults(List<Item> items, String keyword) {
        return formatList(items, "Hasil Pencarian: \"" + keyword + "\"", "- Barang tidak ditemukan!");
    }

    /** Memformat daftar barang yang sudah diurutkan. */
    public String formatSortedItems(List<Item> items) {
        return formatList(items, "Daftar Barang (Terurut):", "- Data barang belum tersedia!");
    }

    /** Memformat pesan sukses setelah menambah barang. */
    public String formatAddSuccess(Item item) {
        return String.format("Berhasil menambah barang: %s", format(item));
    }

    /** Memformat pesan sukses setelah menghapus barang. */
    public String formatRemoveSuccess() {
        return "Berhasil menghapus barang.";
    }

    /** Memformat pesan gagal saat menghapus barang. */
    public String formatRemoveFailed(int id) {
        return String.format("[!] Gagal menghapus barang dengan ID: %d.", id);
    }

    /** Memformat pesan sukses setelah mengubah stok barang. */
    public String formatUpdateSuccess() {
        return "Berhasil mengubah stok barang.";
    }

    /** Memformat pesan gagal saat mengubah barang. */
    public String formatUpdateFailed(int id) {
        return String.format("[!] Gagal mengubah stok barang dengan ID: %d.", id);
    }

    /** Memformat pesan saat pilihan menu tidak dikenali. */
    public String formatInvalidChoice() {
        return "[!] Pilihan tidak dimengerti.";
    }

    /** Memformat pesan saat ID yang dimasukkan tidak valid. */
    public String formatInvalidId() {
        return "[!] ID tidak valid!";
    }

    /** Memformat pesan saat jumlah stok tidak valid. */
    public String formatInvalidQuantity() {
        return "[!] Jumlah stok tidak valid!";
    }

    /** Memformat pesan saat opsi pengurutan tidak valid. */
    public String formatInvalidSortOption() {
        return "[!] Pilihan tidak valid!";
    }
}