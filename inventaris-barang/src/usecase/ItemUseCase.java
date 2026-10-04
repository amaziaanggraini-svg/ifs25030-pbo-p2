package usecase;

import domain.entity.Item;
import domain.entity.SortOption;
import domain.repository.IItemRepository;
import java.util.List;
import java.util.Optional;

/**
 * Use case yang menangani logika bisnis aplikasi inventaris barang.
 * Tidak melakukan I/O, hanya memproses data dan mengembalikan hasil.
 */
public class ItemUseCase {
    /** Port repository yang di-inject dari luar (Dependency Inversion). */
    private final IItemRepository itemRepository;

    public ItemUseCase(IItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    /** Mengambil semua barang yang tersedia. */
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    /** Aturan bisnis jumlah stok (harus lebih dari 0). View memakainya untuk validasi dini. */
    public boolean isValidQuantity(int quantity) {
        return Item.isValidQuantity(quantity);
    }

    /**
     * Menambahkan barang baru dan mengembalikan entity yang tersimpan.
     *
     * @throws IllegalArgumentException jika jumlah stok tidak valid
     */
    public Item addItem(String name, int quantity, String category) {
        return itemRepository.save(name, quantity, category);
    }

    /** Menghapus barang berdasarkan ID. */
    public boolean removeItem(int id) {
        return itemRepository.deleteById(id);
    }

    /**
     * Mengubah stok barang (update parsial).
     * Parameter {@code null} berarti stok tidak diubah.
     *
     * @return true jika barang ditemukan dan diperbarui
     * @throws IllegalArgumentException jika jumlah stok baru tidak valid
     */
    public boolean updateStock(int id, Integer quantity) {
        // Validasi lebih dulu agar input salah tidak tertutup oleh "barang tidak ditemukan"
        if (quantity != null && !Item.isValidQuantity(quantity)) {
            throw new IllegalArgumentException("Jumlah stok harus lebih dari 0");
        }

        Optional<Item> found = itemRepository.findById(id);
        if (found.isEmpty()) {
            return false;
        }

        Item item = found.get();

        // Entity immutable: buat objek baru untuk field yang diubah
        if (quantity != null) {
            item = item.withQuantity(quantity);
        }

        itemRepository.update(item);
        return true;
    }

    /** Mencari barang yang namanya mengandung kata kunci (case-insensitive). */
    public List<Item> searchItems(String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return itemRepository.findAll().stream()
                .filter(item -> item.getName().toLowerCase().contains(lowerKeyword))
                .toList();
    }

    /** Mengurutkan barang sesuai kriteria {@link SortOption} yang dipilih. */
    public List<Item> sortItems(SortOption option) {
        return itemRepository.findAll().stream()
                .sorted(option.comparator())
                .toList();
    }
}