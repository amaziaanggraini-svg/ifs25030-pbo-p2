package adapter.presenter;

import domain.entity.Activity;
import java.util.ArrayList;
import java.util.List;

/**
 * Presenter yang memformat data dari use case menjadi String (tidak mencetak; pencetakan dilakukan oleh View).
 * Format tampilan dipisahkan dari entity (Activity tidak punya toString()).
 */
public class ActivityPresenter {

    /** Memformat satu kegiatan menjadi baris teks: id | judul | hari | waktu. */
    private String format(Activity activity) {
        return String.format("%d | %s | %s | %s",
                activity.getId(), activity.getTitle(), activity.getDay(), activity.getTime());
    }

    /**
     * Helper umum untuk memformat daftar kegiatan.
     * Mengembalikan pesan kosong jika list tidak berisi data.
     */
    private String formatList(List<Activity> activities, String header, String emptyMessage) {
        List<String> lines = new ArrayList<>();
        lines.add(header);

        if (activities.isEmpty()) {
            lines.add(emptyMessage);
        } else {
            for (Activity item : activities) {
                lines.add(format(item));
            }
        }
        return String.join(System.lineSeparator(), lines);
    }

    /** Memformat daftar semua kegiatan. */
    public String formatActivities(List<Activity> activities) {
        return formatList(activities, "Daftar Kegiatan:", "- Data kegiatan belum tersedia!");
    }

    /** Memformat hasil pencarian berdasarkan kata kunci. */
    public String formatSearchResults(List<Activity> activities, String keyword) {
        return formatList(activities, "Hasil Pencarian: \"" + keyword + "\"", "- Kegiatan tidak ditemukan!");
    }

    /** Memformat daftar kegiatan yang sudah diurutkan. */
    public String formatSortedActivities(List<Activity> activities) {
        return formatList(activities, "Daftar Kegiatan (Terurut):", "- Data kegiatan belum tersedia!");
    }

    /** Memformat pesan sukses setelah menambah kegiatan. */
    public String formatAddSuccess(Activity activity) {
        return String.format("Berhasil menambah kegiatan: %s", format(activity));
    }

    /** Memformat pesan sukses setelah menghapus kegiatan. */
    public String formatRemoveSuccess() {
        return "Berhasil menghapus kegiatan.";
    }

    /** Memformat pesan gagal saat menghapus kegiatan. */
    public String formatRemoveFailed(int id) {
        return String.format("[!] Gagal menghapus kegiatan dengan ID: %d.", id);
    }

    /** Memformat pesan sukses setelah mengubah kegiatan. */
    public String formatUpdateSuccess() {
        return "Berhasil mengubah kegiatan.";
    }

    /** Memformat pesan gagal saat mengubah kegiatan. */
    public String formatUpdateFailed(int id) {
        return String.format("[!] Gagal mengubah kegiatan dengan ID: %d.", id);
    }

    /** Memformat pesan saat pilihan menu tidak dikenali. */
    public String formatInvalidChoice() {
        return "[!] Pilihan tidak dimengerti.";
    }

    /** Memformat pesan saat ID yang dimasukkan tidak valid. */
    public String formatInvalidId() {
        return "[!] ID tidak valid!";
    }

    /** Memformat pesan saat opsi pengurutan tidak valid. */
    public String formatInvalidSortOption() {
        return "[!] Pilihan tidak valid!";
    }
}