package adapter.repository;

import domain.entity.Contact;
import domain.repository.IContactRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementasi repository in-memory berbasis {@link List}.
 */
public class ContactRepository implements IContactRepository {
    private final List<Contact> data = new ArrayList<>();

    /** Penghitung ID otomatis, hanya naik saat kontak benar-benar disimpan. */
    private int idCounter = 0;

    @Override
    public List<Contact> findAll() {
        // Salinan defensif agar data internal tidak bisa diubah dari luar
        return new ArrayList<>(data);
    }

    @Override
    public Optional<Contact> findById(int id) {
        return data.stream()
                .filter(contact -> contact.getId() == id)
                .findFirst();
    }

    @Override
    public Contact save(String name, String phone, String email) {
        Contact contact = new Contact(idCounter + 1, name, phone, email);
        idCounter++;
        data.add(contact);
        return contact;
    }

    @Override
    public boolean deleteById(int id) {
        return data.removeIf(contact -> contact.getId() == id);
    }

    @Override
    public void update(Contact contact) {
        // Entity immutable: elemen lama harus diganti dengan objek baru yang ber-ID sama.
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getId() == contact.getId()) {
                data.set(i, contact);
                return;
            }
        }
    }
}