package domain.entity;

import java.util.Objects;

/**
 * Entity inti yang merepresentasikan satu barang di inventaris.
 * Berada di layer domain, tidak bergantung pada layer lain.
 * Immutable: perubahan data dilakukan dengan membuat objek baru lewat {@code withX(...)}.
 */
public class Item {
    /** ID unik barang, tidak boleh diubah setelah dibuat. */
    private final int id;

    /** Nama barang. */
    private final String name;

    /** Jumlah stok barang (selalu positif). */
    private final int quantity;

    /** Kategori barang. */
    private final String category;

    /**
     * @throws IllegalArgumentException jika jumlah stok tidak valid (lihat {@link #isValidQuantity(int)})
     * @throws NullPointerException     jika nama atau kategori null
     */
    public Item(int id, String name, int quantity, String category) {
        if (!isValidQuantity(quantity)) {
            throw new IllegalArgumentException("Jumlah stok harus lebih dari 0");
        }
        this.id = id;
        this.name = Objects.requireNonNull(name, "Nama tidak boleh null");
        this.quantity = quantity;
        this.category = Objects.requireNonNull(category, "Kategori tidak boleh null");
    }

    /** Aturan bisnis: jumlah stok harus lebih dari 0. */
    public static boolean isValidQuantity(int quantity) {
        return quantity > 0;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getCategory() {
        return category;
    }

    /** Mengembalikan salinan barang dengan jumlah stok baru (ID tetap sama). */
    public Item withQuantity(int newQuantity) {
        return new Item(id, name, newQuantity, category);
    }
}
