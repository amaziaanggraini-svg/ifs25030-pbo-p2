package usecase;

import domain.entity.Guest;
import domain.repository.IGuestRepository;

import java.util.List;

public class GuestUseCase {
    private final IGuestRepository guestRepository;

    public GuestUseCase(IGuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    public Guest registerGuest(String name, String purpose) {
        return guestRepository.save(name, purpose);
    }

    public List<Guest> getAllGuests() {
        return guestRepository.findAll();
    }

    public List<Guest> searchGuests(String keyword) {
        return guestRepository.findByKeyword(keyword);
    }

    public boolean deleteGuest(String id) {
        return guestRepository.deleteById(id);
    }
}