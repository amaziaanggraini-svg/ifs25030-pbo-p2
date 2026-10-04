package framework.view;

import adapter.presenter.FinancePresenter;
import domain.entity.Transaction;
import framework.util.InputUtil;
import usecase.FinanceUseCase;

public class FinanceView {
    private final FinanceUseCase financeUseCase;
    private final FinancePresenter financePresenter;

    public FinanceView(FinanceUseCase financeUseCase, FinancePresenter financePresenter) {
        this.financeUseCase = financeUseCase;
        this.financePresenter = financePresenter;
    }

    public void show() {
        while (true) {
            financePresenter.printTransactionList(
                    financeUseCase.getAllTransactions(),
                    financeUseCase.getBalance()
            );

            System.out.println("Menu:");
            System.out.println("1. Tambah Pemasukan");
            System.out.println("2. Tambah Pengeluaran");
            System.out.println("3. Cari");
            System.out.println("4. Urutkan");
            System.out.println("5. Lihat Saldo");
            System.out.println("6. Hapus");
            System.out.println("x. Keluar");

            String option = InputUtil.input("Pilih");

            if (option.equalsIgnoreCase("1")) {
                addTransactionView("Pemasukan");
            } else if (option.equalsIgnoreCase("2")) {
                addTransactionView("Pengeluaran");
            } else if (option.equalsIgnoreCase("3")) {
                searchTransactionView();
            } else if (option.equalsIgnoreCase("4")) {
                sortTransactionView();
            } else if (option.equalsIgnoreCase("5")) {
                financePresenter.printBalanceOnly(financeUseCase.getBalance());
                System.out.println();
            } else if (option.equalsIgnoreCase("6")) {
                deleteTransactionView();
            } else if (option.equalsIgnoreCase("x")) {
                break;
            } else {
                System.out.println("[!] Pilihan tidak dimengerti.");
                System.out.println();
            }
        }
    }

    private void addTransactionView(String type) {
        System.out.println("[" + (type.equals("Pemasukan") ? "Tambah Pemasukan" : "Tambah Pengeluaran") + "]");

        String description = InputUtil.input("Keterangan (x Jika Batal)");
        if (description.equalsIgnoreCase("x")) {
            System.out.println();
            return;
        }

        String amountStr = InputUtil.input("Jumlah");
        if (amountStr.equalsIgnoreCase("x")) {
            System.out.println();
            return;
        }

        long amount;
        try {
            amount = Long.parseLong(amountStr);
            if (amount <= 0) {
                System.out.println("[!] Jumlah tidak valid!");
                System.out.println();
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("[!] Jumlah tidak valid!");
            System.out.println();
            return;
        }

        Transaction transaction = financeUseCase.addTransaction(description, amount, type);
        financePresenter.printSuccessAdd(transaction);
        System.out.println();
    }

    private void searchTransactionView() {
        System.out.println("[Cari Transaksi]");

        String keyword = InputUtil.input("Kata Kunci (x Jika Batal)");
        if (keyword.equalsIgnoreCase("x")) {
            System.out.println();
            return;
        }

        financePresenter.printSearchResult(keyword, financeUseCase.searchTransactions(keyword));
        System.out.println();
    }

    private void sortTransactionView() {
        System.out.println("[Urutkan Transaksi]");
        System.out.println("1. Jumlah (Terkecil)");
        System.out.println("2. Jumlah (Terbesar)");
        System.out.println("3. Pemasukan Dulu");
        System.out.println("4. Pengeluaran Dulu");
        System.out.println("x. Batal");

        String choice = InputUtil.input("Pilih");
        if (choice.equalsIgnoreCase("x")) {
            System.out.println();
            return;
        }

        try {
            int sortOption = Integer.parseInt(choice);
            if (sortOption >= 1 && sortOption <= 4) {
                financePresenter.printSortedList(financeUseCase.getSortedTransactions(sortOption));
            } else {
                System.out.println("[!] Pilihan tidak valid!");
            }
        } catch (NumberFormatException e) {
            System.out.println("[!] Pilihan tidak valid!");
        }
        System.out.println();
    }

    private void deleteTransactionView() {
        System.out.println("[Hapus Transaksi]");

        String idInput = InputUtil.input("ID Transaksi (x Jika Batal)");
        if (idInput.equalsIgnoreCase("x")) {
            System.out.println();
            return;
        }

        try {
            Integer.parseInt(idInput);
        } catch (NumberFormatException e) {
            System.out.println("[!] ID tidak valid!");
            System.out.println();
            return;
        }

        boolean success = financeUseCase.deleteTransaction(idInput);
        if (success) {
            System.out.println("Berhasil menghapus transaksi.");
        } else {
            System.out.println("[!] Gagal menghapus transaksi dengan ID: " + idInput + ".");
        }
        System.out.println();
    }
}