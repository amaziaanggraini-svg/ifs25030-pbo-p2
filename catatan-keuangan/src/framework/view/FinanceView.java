package framework.view;

import adapter.presenter.FinancePresenter;
import domain.entity.SortOption;
import domain.entity.TransactionType;
import framework.util.InputUtil;
import usecase.FinanceUseCase;

/**
 * Layer framework: UI konsol.
 * Menerima input user, memanggil use case, lalu mencetak hasil format dari presenter.
 */
public class FinanceView {
    private final FinanceUseCase financeUseCase;
    private final FinancePresenter financePresenter;

    public FinanceView(FinanceUseCase financeUseCase, FinancePresenter financePresenter) {
        this.financeUseCase = financeUseCase;
        this.financePresenter = financePresenter;
    }

    public void show() {
        while (true) {
            System.out.println(financePresenter.formatTransactionList(
                    financeUseCase.getAllTransactions(),
                    financeUseCase.getBalance()
            ));

            System.out.println("Menu:");
            System.out.println("1. Tambah Pemasukan");
            System.out.println("2. Tambah Pengeluaran");
            System.out.println("3. Cari");
            System.out.println("4. Urutkan");
            System.out.println("5. Lihat Saldo");
            System.out.println("6. Hapus");
            System.out.println("x. Keluar");

            String option = InputUtil.input("Pilih");

            if (option.equals("1")) {
                addTransactionView(TransactionType.INCOME);
            } else if (option.equals("2")) {
                addTransactionView(TransactionType.EXPENSE);
            } else if (option.equals("3")) {
                searchTransactionView();
            } else if (option.equals("4")) {
                sortTransactionView();
            } else if (option.equals("5")) {
                System.out.println(financePresenter.formatBalance(financeUseCase.getBalance()));
                System.out.println();
            } else if (option.equals("6")) {
                deleteTransactionView();
            } else if (InputUtil.isCancel(option)) {
                break;
            } else {
                System.out.println(financePresenter.formatInvalidChoice());
                System.out.println();
            }
        }
    }

    private void addTransactionView(TransactionType type) {
        System.out.println("[Tambah " + type.getLabel() + "]");

        String description = InputUtil.input("Keterangan (x Jika Batal)");
        if (InputUtil.isCancel(description)) {
            System.out.println();
            return;
        }

        String amountStr = InputUtil.input("Jumlah");
        if (InputUtil.isCancel(amountStr)) {
            System.out.println();
            return;
        }

        long amount;
        try {
            amount = Long.parseLong(amountStr);
        } catch (NumberFormatException e) {
            System.out.println(financePresenter.formatInvalidAmount());
            System.out.println();
            return;
        }

        try {
            System.out.println(financePresenter.formatAddSuccess(
                    financeUseCase.addTransaction(description, amount, type)));
        } catch (IllegalArgumentException e) {
            // Domain menolak data (mis. jumlah <= 0)
            System.out.println(financePresenter.formatInvalidAmount());
        }
        System.out.println();
    }

    private void searchTransactionView() {
        System.out.println("[Cari Transaksi]");

        String keyword = InputUtil.input("Kata Kunci (x Jika Batal)");
        if (InputUtil.isCancel(keyword)) {
            System.out.println();
            return;
        }

        System.out.println(financePresenter.formatSearchResult(
                keyword, financeUseCase.searchTransactions(keyword)));
        System.out.println();
    }

    private void sortTransactionView() {
        System.out.println("[Urutkan Transaksi]");

        // Menu dibangun dari enum, sehingga nomor menu selalu sesuai opsi yang ada
        SortOption[] options = SortOption.values();
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i].getLabel());
        }
        System.out.println("x. Batal");

        String choice = InputUtil.input("Pilih");
        if (InputUtil.isCancel(choice)) {
            System.out.println();
            return;
        }

        try {
            int number = Integer.parseInt(choice);
            if (number >= 1 && number <= options.length) {
                System.out.println(financePresenter.formatSortedList(
                        financeUseCase.sortTransactions(options[number - 1])));
            } else {
                System.out.println(financePresenter.formatInvalidSortOption());
            }
        } catch (NumberFormatException e) {
            System.out.println(financePresenter.formatInvalidSortOption());
        }
        System.out.println();
    }

    private void deleteTransactionView() {
        System.out.println("[Hapus Transaksi]");

        String idInput = InputUtil.input("ID Transaksi (x Jika Batal)");
        if (InputUtil.isCancel(idInput)) {
            System.out.println();
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idInput);
        } catch (NumberFormatException e) {
            System.out.println(financePresenter.formatInvalidId());
            System.out.println();
            return;
        }

        if (financeUseCase.deleteTransaction(id)) {
            System.out.println(financePresenter.formatDeleteSuccess());
        } else {
            System.out.println(financePresenter.formatDeleteFailed(id));
        }
        System.out.println();
    }
}
