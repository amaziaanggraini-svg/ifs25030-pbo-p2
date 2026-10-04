package adapter.repository;

import domain.entity.Item;
import domain.repository.IItemRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementasi repository menggunakan penyimpanan in-memory berbasis {@link List}.
 * Berada di layer adapter, mengimplementasikan port dari domain.
 */
public class ItemRepository implements IItemRepository {
    /** Penyimpanan data barang di memori. */
    private final List<Item> data = new ArrayList<>();

    /** Penghitung ID otomatis, bertambah setiap kali barang baru disimpan. */
    private int idCounter = 0;

    @Override
    public List<Item> findAll() {
        // Salinan defensif agar data internal tidak bisa diubah dari luar
        return new ArrayList<>(data);
    }

    @Override
    public Optional<Item> findById(int id) {
        return data.stream()
                .filter(item -> item.getId() == id)
                .findFirst();
    }

    @Override
    public Item save(String name, int quantity, String category) {
        Item item = new Item(idCounter + 1, name, quantity, category);
        idCounter++;
        data.add(item);
        return item;
    }

    @Override
    public boolean deleteById(int id) {
        return data.removeIf(item -> item.getId() == id);
    }

    @Override
    public void update(Item item) {
        // Entity immutable: elemen lama harus diganti dengan objek baru yang ber-ID sama.
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getId() == item.getId()) {
                data.set(i, item);
                return;
            }
        }
    }
}