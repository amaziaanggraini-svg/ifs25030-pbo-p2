package domain.entity;

/**
 * Jenis transaksi keuangan.
 * Memakai enum agar tidak rentan typo seperti string biasa.
 */
public enum TransactionType {
    /** Uang masuk. */
    INCOME("Pemasukan"),

    /** Uang keluar. */
    EXPENSE("Pengeluaran");

    /** Teks yang ditampilkan ke pengguna. */
    private final String label;

    TransactionType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
