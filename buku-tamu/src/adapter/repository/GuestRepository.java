package adapter.repository;

import domain.entity.Guest;
import domain.repository.IGuestRepository;

import java.util.ArrayList;
import java.util.List;

public class GuestRepository implements IGuestRepository {
    private final List<Guest> guests = new ArrayList<>();
    private int idCounter = 1;

    @Override
    public Guest save(String name, String purpose) {
        String id = String.valueOf(idCounter++);
        Guest guest = new Guest(id, name, purpose);
        guests.add(guest);
        return guest;
    }

    @Override
    public List<Guest> findAll() {
        return new ArrayList<>(guests);
    }

    @Override
    public List<Guest> findByKeyword(String keyword) {
        List<Guest> result = new ArrayList<>();
        String lower = keyword.toLowerCase();
        for (Guest g : guests) {
            if (g.getName().toLowerCase().contains(lower) || g.getPurpose().toLowerCase().contains(lower)) {
                result.add(g);
            }
        }
        return result;
    }

    @Override
    public boolean deleteById(String id) {
        return guests.removeIf(g -> g.getId().equalsIgnoreCase(id));
    }
}