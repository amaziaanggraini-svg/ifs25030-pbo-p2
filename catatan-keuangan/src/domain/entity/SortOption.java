package domain.entity;

import java.util.Comparator;

/**
 * Kriteria pengurutan transaksi.
 * Setiap opsi membawa label dan comparator-nya sendiri sehingga logika
 * pengurutan terpusat di domain.
 */
public enum SortOption {
    /** Jumlah dari yang terkecil. */
    AMOUNT_ASC("Jumlah (Terkecil)",
            Comparator.comparingLong(Transaction::getAmount)),

    /** Jumlah dari yang terbesar. */
    AMOUNT_DESC("Jumlah (Terbesar)",
            Comparator.comparingLong(Transaction::getAmount).reversed()),

    /** Pemasukan di atas, pengeluaran di bawah. */
    INCOME_FIRST("Pemasukan Dulu",
            Comparator.comparing((Transaction t) -> t.getType() != TransactionType.INCOME)),

    /** Pengeluaran di atas, pemasukan di bawah. */
    EXPENSE_FIRST("Pengeluaran Dulu",
            Comparator.comparing((Transaction t) -> t.getType() != TransactionType.EXPENSE));

    private final String label;
    private final Comparator<Transaction> comparator;

    SortOption(String label, Comparator<Transaction> comparator) {
        this.label = label;
        this.comparator = comparator;
    }

    /** Teks yang ditampilkan di menu. */
    public String getLabel() {
        return label;
    }

    /** Comparator yang sesuai dengan opsi ini. */
    public Comparator<Transaction> comparator() {
        return comparator;
    }
}
