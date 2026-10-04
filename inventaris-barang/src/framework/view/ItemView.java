package framework.view;

import adapter.presenter.ItemPresenter;
import domain.entity.SortOption;
import framework.util.InputUtil;
import usecase.ItemUseCase;

/**
 * Tampilan konsol aplikasi inventaris barang.
 * Menerima input user, memanggil use case, dan menampilkan hasil via presenter.
 * Layer ini tidak mengandung logika bisnis, hanya menangani interaksi user.
 */
public class ItemView {
    /** Use case yang menjalankan operasi bisnis. */
    private final ItemUseCase itemUseCase;

    /** Presenter yang memformat hasil ke output layar. */
    private final ItemPresenter presenter;

    public ItemView(ItemUseCase itemUseCase, ItemPresenter presenter) {
        this.itemUseCase = itemUseCase;
        this.presenter = presenter;
    }

    /** Menampilkan menu utama dan loop interaksi user. */
    public void show() {
        boolean running = true;
        while (running) {
            // Tampilkan daftar barang terkini sebelum menu
            System.out.println(presenter.formatItems(itemUseCase.getAllItems()));
            printMenu();

            String input = InputUtil.input("Pilih");
            switch (input) {
                case "1" -> addItem();
                case "2" -> updateStock();
                case "3" -> searchItem();
                case "4" -> sortItem();
                case "5" -> removeItem();
                case "x" -> running = false;
                default -> System.out.println(presenter.formatInvalidChoice());
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
        System.out.println("2. Ubah Stok");
        System.out.println("3. Cari");
        System.out.println("4. Urutkan");
        System.out.println("5. Hapus");
        System.out.println("x. Keluar");
    }

    /** Form tambah barang baru. */
    private void addItem() {
        System.out.println("[Menambah Barang]");
        String name = InputUtil.input("Nama (x Jika Batal)");

        if (InputUtil.isCancel(name)) {
            return;
        }

        String strQuantity = InputUtil.input("Jumlah");
        Integer quantity = parseQuantity(strQuantity);
        if (quantity == null) {
            return;
        }

        String category = InputUtil.input("Kategori (x Jika Batal)");

        System.out.println(presenter.formatAddSuccess(itemUseCase.addItem(name, quantity, category)));
    }

    /** Form ubah stok barang (parsial: kosongkan = tidak diubah). */
    private void updateStock() {
        System.out.println("[Mengubah Stok]");
        String strIdItem = InputUtil.input("ID Barang yang diubah (x Jika Batal)");

        if (InputUtil.isCancel(strIdItem)) {
            return;
        }

        Integer id = parseId(strIdItem);
        if (id == null) {
            return;
        }

        String strQuantity = InputUtil.input("Jumlah Baru (Kosongkan jika tidak ingin mengubah)");

        // null berarti stok tidak diubah
        Integer quantity = null;
        if (!strQuantity.isBlank()) {
            quantity = parseQuantity(strQuantity);
            if (quantity == null) {
                return;
            }
        }

        if (itemUseCase.updateStock(id, quantity)) {
            System.out.println(presenter.formatUpdateSuccess());
        } else {
            System.out.println(presenter.formatUpdateFailed(id));
        }
    }

    /** Form cari barang berdasarkan kata kunci nama. */
    private void searchItem() {
        System.out.println("[Mencari Barang]");
        String keyword = InputUtil.input("Kata Kunci (x Jika Batal)");

        if (!InputUtil.isCancel(keyword)) {
            System.out.println(presenter.formatSearchResults(itemUseCase.searchItems(keyword), keyword));
        }
    }

    /** Form urutkan barang berdasarkan pilihan user. */
    private void sortItem() {
        System.out.println("[Mengurutkan Barang]");
        System.out.println("Pilihan Pengurutan:");
        System.out.println("1. Nama (A-Z)");
        System.out.println("2. Nama (Z-A)");
        System.out.println("3. Jumlah (Terkecil -> Terbesar)");
        System.out.println("4. Jumlah (Terbesar -> Terkecil)");
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

        System.out.println(presenter.formatSortedItems(itemUseCase.sortItems(option)));
    }

    /** Form hapus barang berdasarkan ID. */
    private void removeItem() {
        System.out.println("[Menghapus Barang]");
        String strIdItem = InputUtil.input("[ID Barang] yang dihapus (x Jika Batal)");

        if (InputUtil.isCancel(strIdItem)) {
            return;
        }

        Integer id = parseId(strIdItem);
        if (id == null) {
            return;
        }

        if (itemUseCase.removeItem(id)) {
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

    /**
     * Mengonversi input string menjadi jumlah stok (harus numerik dan > 0).
     *
     * @return jumlah jika valid, null jika tidak valid (error sudah ditampilkan)
     */
    private Integer parseQuantity(String value) {
        try {
            int quantity = Integer.parseInt(value);
            if (quantity <= 0) {
                System.out.println(presenter.formatInvalidQuantity());
                return null;
            }
            return quantity;
        } catch (NumberFormatException e) {
            System.out.println(presenter.formatInvalidQuantity());
            return null;
        }
    }

    /** Memetakan pilihan menu (1-4) ke {@link SortOption} domain. */
    private SortOption mapSortOption(String input) {
        return switch (input) {
            case "1" -> SortOption.NAME_ASC;
            case "2" -> SortOption.NAME_DESC;
            case "3" -> SortOption.QUANTITY_ASC;
            case "4" -> SortOption.QUANTITY_DESC;
            default -> null;
        };
    }
}