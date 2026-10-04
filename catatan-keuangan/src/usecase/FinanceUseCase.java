package usecase;

import domain.entity.Transaction;
import domain.repository.ITransactionRepository;

import java.util.List;

public class FinanceUseCase {
    private final ITransactionRepository transactionRepository;

    public FinanceUseCase(ITransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction addTransaction(String description, long amount, String type) {
        return transactionRepository.save(description, amount, type);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public List<Transaction> searchTransactions(String keyword) {
        return transactionRepository.findByKeyword(keyword);
    }

    public boolean deleteTransaction(String id) {
        return transactionRepository.deleteById(id);
    }

    public long getBalance() {
        return transactionRepository.getBalance();
    }

    public List<Transaction> getSortedTransactions(int sortType) {
        return transactionRepository.getSorted(sortType);
    }
}