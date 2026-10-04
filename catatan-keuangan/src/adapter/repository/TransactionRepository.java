package adapter.repository;

import domain.entity.Transaction;
import domain.repository.ITransactionRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TransactionRepository implements ITransactionRepository {
    private final List<Transaction> transactions = new ArrayList<>();
    private int idCounter = 1;

    @Override
    public Transaction save(String description, long amount, String type) {
        String id = String.valueOf(idCounter++);
        Transaction transaction = new Transaction(id, description, amount, type);
        transactions.add(transaction);
        return transaction;
    }

    @Override
    public List<Transaction> findAll() {
        return new ArrayList<>(transactions);
    }

    @Override
    public List<Transaction> findByKeyword(String keyword) {
        List<Transaction> result = new ArrayList<>();
        String lower = keyword.toLowerCase();
        for (Transaction t : transactions) {
            if (t.getDescription().toLowerCase().contains(lower) ||
                t.getType().toLowerCase().contains(lower)) {
                result.add(t);
            }
        }
        return result;
    }

    @Override
    public boolean deleteById(String id) {
        return transactions.removeIf(t -> t.getId().equalsIgnoreCase(id));
    }

    @Override
    public long getBalance() {
        long balance = 0;
        for (Transaction t : transactions) {
            if (t.getType().equalsIgnoreCase("Pemasukan")) {
                balance += t.getAmount();
            } else if (t.getType().equalsIgnoreCase("Pengeluaran")) {
                balance -= t.getAmount();
            }
        }
        return balance;
    }

    @Override
    public List<Transaction> getSorted(int sortType) {
        List<Transaction> sortedList = new ArrayList<>(transactions);
        switch (sortType) {
            case 1: // Jumlah (Terkecil)
                sortedList.sort(Comparator.comparingLong(Transaction::getAmount));
                break;
            case 2: // Jumlah (Terbesar)
                sortedList.sort((a, b) -> Long.compare(b.getAmount(), a.getAmount()));
                break;
            case 3: // Pemasukan Dulu
                sortedList.sort((a, b) -> {
                    if (a.getType().equalsIgnoreCase(b.getType())) return 0;
                    return a.getType().equalsIgnoreCase("Pemasukan") ? -1 : 1;
                });
                break;
            case 4: // Pengeluaran Dulu
                sortedList.sort((a, b) -> {
                    if (a.getType().equalsIgnoreCase(b.getType())) return 0;
                    return a.getType().equalsIgnoreCase("Pengeluaran") ? -1 : 1;
                });
                break;
        }
        return sortedList;
    }
}