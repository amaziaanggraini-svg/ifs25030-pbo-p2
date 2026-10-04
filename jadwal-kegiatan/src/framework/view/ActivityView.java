package framework.view;

import adapter.presenter.ActivityPresenter;
import domain.entity.SortOption;
import framework.util.InputUtil;
import usecase.ActivityUseCase;

/**
 * Tampilan konsol aplikasi jadwal kegiatan.
 * Menerima input user, memanggil use case, dan menampilkan hasil via presenter.
 * Layer ini tidak mengandung logika bisnis, hanya menangani interaksi user.
 */
public class ActivityView {
    /** Use case yang menjalankan operasi bisnis. */
    private final ActivityUseCase activityUseCase;

    /** Presenter yang memformat hasil ke output layar. */
    private final ActivityPresenter presenter;

    public ActivityView(ActivityUseCase activityUseCase, ActivityPresenter presenter) {
        this.activityUseCase = activityUseCase;
        this.presenter = presenter;
    }

    /** Menampilkan menu utama dan loop interaksi user. */
    public void show() {
        boolean running = true;
        while (running) {
            // Tampilkan daftar kegiatan terkini sebelum menu
            System.out.println(presenter.formatActivities(activityUseCase.getAllActivities()));
            printMenu();

            String input = InputUtil.input("Pilih");
            switch (input) {
                case "1" -> addActivity();
                case "2" -> updateActivity();
                case "3" -> searchActivity();
                case "4" -> sortActivity();
                case "5" -> removeActivity();
                default -> {
                    if (InputUtil.isCancel(input)) {
                        running = false;
                    } else {
                        System.out.println(presenter.formatInvalidChoice());
                    }
                }
            }

            if (running) {
                System.out.println();
            }
        }
    }

    /** Mencetak opsi menu ke layar. */
    private void printMenu() {
        System.out.println("Menu:");
        System.out.println("1. Tambah");
        System.out.println("2. Ubah");
        System.out.println("3. Cari");
        System.out.println("4. Urutkan");
        System.out.println("5. Hapus");
        System.out.println("x. Keluar");
    }

    /** Form tambah kegiatan baru. */
        /** Form tambah kegiatan baru. Mengetik x di kolom mana pun membatalkan proses. */
    private void addActivity() {
        System.out.println("[Menambah Kegiatan]");
        String title = InputUtil.input("Judul (x Jika Batal)");
        if (InputUtil.isCancel(title)) {
            return;
        }

        String day = InputUtil.input("Hari (x Jika Batal)");
        if (InputUtil.isCancel(day)) {
            return;
        }

        String time = InputUtil.input("Waktu (x Jika Batal)");
        if (InputUtil.isCancel(time)) {
            return;
        }

        System.out.println(presenter.formatAddSuccess(activityUseCase.addActivity(title, day, time)));
    }

    /** Form ubah judul, hari, dan/atau waktu kegiatan (parsial). */
    private void updateActivity() {
        System.out.println("[Mengubah Kegiatan]");
        String strId = InputUtil.input("ID Kegiatan yang diubah (x Jika Batal)");

        if (InputUtil.isCancel(strId)) {
            return;
        }

        Integer id = parseId(strId);
        if (id == null) {
            return;
        }

        String newTitle = InputUtil.input("Judul Baru (Kosongkan jika tidak ingin mengubah)");
        String newDay = InputUtil.input("Hari Baru (Kosongkan jika tidak ingin mengubah)");
        String newTime = InputUtil.input("Waktu Baru (Kosongkan jika tidak ingin mengubah)");

        // Input kosong = tidak diubah; aturan ini ditangani oleh use case
        if (activityUseCase.updateActivity(id, newTitle, newDay, newTime)) {
            System.out.println(presenter.formatUpdateSuccess());
        } else {
            System.out.println(presenter.formatUpdateFailed(id));
        }
    }

    /** Form cari kegiatan berdasarkan kata kunci judul. */
    private void searchActivity() {
        System.out.println("[Mencari Kegiatan]");
        String keyword = InputUtil.input("Kata Kunci (x Jika Batal)");

        if (!InputUtil.isCancel(keyword)) {
            System.out.println(presenter.formatSearchResults(activityUseCase.searchActivities(keyword), keyword));
        }
    }

    /** Form urutkan kegiatan berdasarkan pilihan user. */
    private void sortActivity() {
        System.out.println("[Mengurutkan Kegiatan]");
        System.out.println("Pilihan Pengurutan:");
        System.out.println("1. Hari (Senin -> Minggu)");
        System.out.println("2. Waktu (Awal -> Akhir)");
        System.out.println("3. Judul (A-Z)");
        System.out.println("4. Judul (Z-A)");
        System.out.println("x. Batal");

        String input = InputUtil.input("Pilih");
        if (InputUtil.isCancel(input)) {
            return;
        }

        // Konversi input angka ke enum domain
        SortOption option = mapSortOption(input);
        if (option == null) {
            System.out.println(presenter.formatInvalidSortOption());
            return;
        }

        System.out.println(presenter.formatSortedActivities(activityUseCase.sortActivities(option)));
    }

    /** Form hapus kegiatan berdasarkan ID. */
    private void removeActivity() {
        System.out.println("[Menghapus Kegiatan]");
        String strId = InputUtil.input("[ID Kegiatan] yang dihapus (x Jika Batal)");

        if (InputUtil.isCancel(strId)) {
            return;
        }

        Integer id = parseId(strId);
        if (id == null) {
            return;
        }

        if (activityUseCase.removeActivity(id)) {
            System.out.println(presenter.formatRemoveSuccess());
        } else {
            System.out.println(presenter.formatRemoveFailed(id));
        }
    }

    /**
     * Mengonversi input string menjadi ID.
     *
     * @return ID jika valid, null jika tidak valid (error sudah ditampilkan)
     */
    private Integer parseId(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            System.out.println(presenter.formatInvalidId());
            return null;
        }
    }

    /** Memetakan pilihan menu (1-4) ke {@link SortOption} domain. */
    private SortOption mapSortOption(String input) {
        return switch (input) {
            case "1" -> SortOption.DAY;
            case "2" -> SortOption.TIME;
            case "3" -> SortOption.TITLE_ASC;
            case "4" -> SortOption.TITLE_DESC;
            default -> null;
        };
    }
}