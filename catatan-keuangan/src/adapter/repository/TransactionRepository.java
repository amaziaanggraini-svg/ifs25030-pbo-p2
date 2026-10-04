package adapter.repository;

import domain.entity.Transaction;
import domain.entity.TransactionType;
import domain.repository.ITransactionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementasi repository in-memory berbasis {@link List}.
 * Hanya menyimpan dan mengambil data.
 */
public class TransactionRepository implements ITransactionRepository {
    private final List<Transaction> transactions = new ArrayList<>();
    private int idCounter = 0;

    @Override
    public Transaction save(String description, long amount, TransactionType type) {
        // Validasi dilakukan oleh konstruktor Transaction; ID baru dipakai
        // hanya jika objek berhasil dibuat.
        Transaction transaction = new Transaction(idCounter + 1, description, amount, type);
        idCounter++;
        transactions.add(transaction);
        return transaction;
    }

    @Override
    public List<Transaction> findAll() {
        // Salinan defensif agar data internal tidak bisa diubah dari luar
        return new ArrayList<>(transactions);
    }

    @Override
    public Optional<Transaction> findById(int id) {
        return transactions.stream()
                .filter(t -> t.getId() == id)
                .findFirst();
    }

    @Override
    public boolean deleteById(int id) {
        return transactions.removeIf(t -> t.getId() == id);
    }
}
