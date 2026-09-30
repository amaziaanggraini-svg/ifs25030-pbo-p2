package adapter.presenter;

import domain.entity.Transaction;
import java.util.List;

public class FinancePresenter {

    public void printTransactionList(List<Transaction> transactions, long balance) {
        System.out.println("Daftar Transaksi:");
        if (transactions.isEmpty()) {
            System.out.println("- Belum ada transaksi!");
        } else {
            for (Transaction t : transactions) {
                System.out.println(t.getId() + " | " + t.getDescription() + " | Rp " + t.getAmount() + " | " + t.getType());
            }
        }
        System.out.println("Saldo: Rp " + balance);
    }

    public void printSortedList(List<Transaction> transactions) {
        System.out.println("Daftar Transaksi (Terurut):");
        for (Transaction t : transactions) {
            System.out.println(t.getId() + " | " + t.getDescription() + " | Rp " + t.getAmount() + " | " + t.getType());
        }
    }

    public void printSearchResult(String keyword, List<Transaction> transactions) {
        System.out.println("Hasil Pencarian: \"" + keyword + "\"");
        if (transactions.isEmpty()) {
            System.out.println("- Transaksi tidak ditemukan!");
        } else {
            for (Transaction t : transactions) {
                System.out.println(t.getId() + " | " + t.getDescription() + " | Rp " + t.getAmount() + " | " + t.getType());
            }
        }
    }

    public void printSuccessAdd(Transaction transaction) {
        System.out.println("Berhasil menambah transaksi: " + transaction.getId() + " | " + transaction.getDescription() + " | Rp " + transaction.getAmount() + " | " + transaction.getType());
    }

    public void printBalanceOnly(long balance) {
        System.out.println("Saldo saat ini: Rp " + balance);
    }
}