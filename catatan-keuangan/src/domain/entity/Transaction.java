package domain.entity;

/**
 * Entity inti yang merepresentasikan satu transaksi keuangan.
 * Berada di layer domain, tidak bergantung pada layer lain.
 * Immutable: semua field final dan tidak ada setter.
 */
public class Transaction {
    /** ID unik transaksi, tidak boleh diubah setelah dibuat. */
    private final int id;

    /** Keterangan transaksi. */
    private final String description;

    /** Jumlah uang (selalu positif). */
    private final long amount;

    /** Jenis transaksi: pemasukan atau pengeluaran. */
    private final TransactionType type;

    /**
     * @throws IllegalArgumentException jika jumlah tidak positif, atau keterangan/jenis kosong (null)
     */
    public Transaction(int id, String description, long amount, TransactionType type) {
        if (description == null) {
            throw new IllegalArgumentException("Keterangan tidak boleh null");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Jumlah harus lebih dari 0");
        }
        if (type == null) {
            throw new IllegalArgumentException("Jenis transaksi wajib diisi");
        }
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public long getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }
}
