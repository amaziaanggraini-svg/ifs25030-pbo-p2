package usecase;

import domain.entity.Guest;
import domain.repository.IGuestRepository;

import java.util.List;

/**
 * Use case yang menangani logika bisnis buku tamu.
 * Tidak melakukan I/O, hanya memproses data dan mengembalikan hasil.
 */
public class GuestUseCase {
    private final IGuestRepository guestRepository;

    public GuestUseCase(IGuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    /** Mendaftarkan tamu baru dan mengembalikan entity yang tersimpan. */
    public Guest registerGuest(String name, String purpose) {
        return guestRepository.save(name, purpose);
    }

    /** Mengambil semua tamu. */
    public List<Guest> getAllGuests() {
        return guestRepository.findAll();
    }

    /** Mencari tamu yang nama atau tujuannya mengandung kata kunci (case-insensitive). */
    public List<Guest> searchGuests(String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return guestRepository.findAll().stream()
                .filter(g -> g.getName().toLowerCase().contains(lowerKeyword)
                        || g.getPurpose().toLowerCase().contains(lowerKeyword))
                .toList();
    }

    /** Menghapus tamu berdasarkan ID. */
    public boolean deleteGuest(int id) {
        return guestRepository.deleteById(id);
    }
}
