package adapter.repository;

import domain.entity.Guest;
import domain.repository.IGuestRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementasi repository in-memory berbasis {@link List}.
 * Hanya menyimpan dan mengambil data.
 */
public class GuestRepository implements IGuestRepository {
    private final List<Guest> guests = new ArrayList<>();
    private int idCounter = 0;

    @Override
    public Guest save(String name, String purpose) {
        Guest guest = new Guest(idCounter + 1, name, purpose);
        idCounter++;
        guests.add(guest);
        return guest;
    }

    @Override
    public List<Guest> findAll() {
        // Salinan defensif agar data internal tidak bisa diubah dari luar
        return new ArrayList<>(guests);
    }

    @Override
    public Optional<Guest> findById(int id) {
        return guests.stream()
                .filter(g -> g.getId() == id)
                .findFirst();
    }

    @Override
    public boolean deleteById(int id) {
        return guests.removeIf(g -> g.getId() == id);
    }
}
