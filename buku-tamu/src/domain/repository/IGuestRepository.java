package domain.repository;

import domain.entity.Guest;
import java.util.List;
import java.util.Optional;

/**
 * Port (kontrak) penyimpanan data tamu.
 * Hanya mengurus persistensi, tanpa logika bisnis.
 */
public interface IGuestRepository {
    /** Menyimpan tamu baru. Implementasi bertanggung jawab memberi ID unik. */
    Guest save(String name, String purpose);

    /** Mengambil semua tamu. */
    List<Guest> findAll();

    /** Mencari satu tamu berdasarkan ID. Mengembalikan empty jika tidak ditemukan. */
    Optional<Guest> findById(int id);

    /** Menghapus tamu berdasarkan ID. Mengembalikan true jika berhasil. */
    boolean deleteById(int id);
}
