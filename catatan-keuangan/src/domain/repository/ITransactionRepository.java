package domain.repository;

import domain.entity.Transaction;
import domain.entity.TransactionType;
import java.util.List;
import java.util.Optional;

/**
 * Port (kontrak) penyimpanan data transaksi.
 * Hanya mengurus persistensi; perhitungan saldo, pencarian, dan pengurutan
 * ada di use case.
 */
public interface ITransactionRepository {
    /** Menyimpan transaksi baru. Implementasi bertanggung jawab memberi ID unik. */
    Transaction save(String description, long amount, TransactionType type);

    /** Mengambil semua transaksi. */
    List<Transaction> findAll();

    /** Mencari satu transaksi berdasarkan ID. Mengembalikan empty jika tidak ditemukan. */
    Optional<Transaction> findById(int id);

    /** Menghapus transaksi berdasarkan ID. Mengembalikan true jika berhasil. */
    boolean deleteById(int id);
}
