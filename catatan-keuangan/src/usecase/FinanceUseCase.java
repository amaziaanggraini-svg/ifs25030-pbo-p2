package usecase;

import domain.entity.SortOption;
import domain.entity.Transaction;
import domain.entity.TransactionType;
import domain.repository.ITransactionRepository;

import java.util.List;

/**
 * Use case yang menangani logika bisnis catatan keuangan.
 * Tidak melakukan I/O, hanya memproses data dan mengembalikan hasil.
 */
public class FinanceUseCase {
    private final ITransactionRepository transactionRepository;

    public FinanceUseCase(ITransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    /**
     * Menambah transaksi baru.
     *
     * @throws IllegalArgumentException jika data transaksi tidak valid (mis. jumlah <= 0)
     */
    public Transaction addTransaction(String description, long amount, TransactionType type) {
        return transactionRepository.save(description, amount, type);
    }

    /** Mengambil semua transaksi. */
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    /** Mencari transaksi yang keterangan atau jenisnya mengandung kata kunci (case-insensitive). */
    public List<Transaction> searchTransactions(String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return transactionRepository.findAll().stream()
                .filter(t -> t.getDescription().toLowerCase().contains(lowerKeyword)
                        || t.getType().getLabel().toLowerCase().contains(lowerKeyword))
                .toList();
    }

    /** Menghapus transaksi berdasarkan ID. */
    public boolean deleteTransaction(int id) {
        return transactionRepository.deleteById(id);
    }

    /** Menghitung saldo: total pemasukan dikurangi total pengeluaran. */
    public long getBalance() {
        long balance = 0;
        for (Transaction t : transactionRepository.findAll()) {
            if (t.getType() == TransactionType.INCOME) {
                balance += t.getAmount();
            } else {
                balance -= t.getAmount();
            }
        }
        return balance;
    }

    /** Mengurutkan transaksi sesuai kriteria {@link SortOption}. */
    public List<Transaction> sortTransactions(SortOption option) {
        return transactionRepository.findAll().stream()
                .sorted(option.comparator())
                .toList();
    }
}
