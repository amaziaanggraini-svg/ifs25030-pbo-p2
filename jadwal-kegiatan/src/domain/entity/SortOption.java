package domain.entity;

import java.util.Comparator;

/**
 * Kriteria pengurutan kegiatan.
 * Setiap opsi membawa comparator-nya sendiri sehingga logika pengurutan
 * terpusat di domain.
 */
public enum SortOption {
        /** Urutkan berdasarkan hari dalam seminggu (Senin sampai Minggu), lalu waktu jika harinya sama. */
    DAY(Comparator.comparingInt((Activity a) -> dayIndex(a.getDay()))
            .thenComparing(Activity::getTime)),

    /** Urutkan berdasarkan waktu (format HH:mm, dari paling pagi). */
    TIME(Comparator.comparing(Activity::getTime)),

    /** Urutkan judul dari A ke Z (case-insensitive). */
    TITLE_ASC(Comparator.comparing(Activity::getTitle, String.CASE_INSENSITIVE_ORDER)),

    /** Urutkan judul dari Z ke A (case-insensitive). */
    TITLE_DESC(Comparator.comparing(Activity::getTitle, String.CASE_INSENSITIVE_ORDER).reversed());

    /** Comparator yang digunakan untuk mengurutkan daftar kegiatan. */
    private final Comparator<Activity> comparator;

    SortOption(Comparator<Activity> comparator) {
        this.comparator = comparator;
    }

    /** Mengembalikan comparator yang sesuai dengan opsi ini. */
    public Comparator<Activity> comparator() {
        return comparator;
    }

    /** Mengubah nama hari menjadi urutan (Senin = 1 ... Minggu = 7). Hari tak dikenal di urutan terakhir. */
    private static int dayIndex(String day) {
        return switch (day.trim().toLowerCase()) {
            case "senin" -> 1;
            case "selasa" -> 2;
            case "rabu" -> 3;
            case "kamis" -> 4;
            case "jumat" -> 5;
            case "sabtu" -> 6;
            case "minggu" -> 7;
            default -> 8;
        };
    }
}