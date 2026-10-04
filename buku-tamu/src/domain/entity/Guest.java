package domain.entity;

import java.util.Objects;

/**
 * Entity inti yang merepresentasikan satu tamu.
 * Berada di layer domain, tidak bergantung pada layer lain.
 * Immutable: semua field final dan tidak ada setter.
 */
public class Guest {
    /** ID unik tamu, tidak boleh diubah setelah dibuat. */
    private final int id;

    /** Nama tamu. */
    private final String name;

    /** Tujuan kunjungan. */
    private final String purpose;

    /**
     * @throws NullPointerException jika nama atau tujuan null
     */
    public Guest(int id, String name, String purpose) {
        this.id = id;
        this.name = Objects.requireNonNull(name, "Nama tidak boleh null");
        this.purpose = Objects.requireNonNull(purpose, "Tujuan tidak boleh null");
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPurpose() {
        return purpose;
    }
}
