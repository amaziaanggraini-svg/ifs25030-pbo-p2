package domain.repository;

import domain.entity.Transaction;
import java.util.List;

public interface ITransactionRepository {
    Transaction save(String description, long amount, String type);
    List<Transaction> findAll();
    List<Transaction> findByKeyword(String keyword);
    boolean deleteById(String id);
    long getBalance();
    List<Transaction> getSorted(int sortType);
}