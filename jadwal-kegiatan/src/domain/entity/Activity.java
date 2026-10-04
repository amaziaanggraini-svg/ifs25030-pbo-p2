package domain.entity;

import java.util.Objects;

/**
 * Entity inti yang merepresentasikan satu kegiatan.
 * Berada di layer domain, tidak bergantung pada layer lain.
 * Immutable: perubahan data dilakukan dengan membuat objek baru lewat {@code withX(...)}.
 */
public class Activity {
    /** ID unik kegiatan, tidak boleh diubah setelah dibuat. */
    private final int id;

    /** Judul kegiatan. */
    private final String title;

    /** Hari pelaksanaan kegiatan. */
    private final String day;

    /** Waktu pelaksanaan kegiatan (contoh: 08:00). */
    private final String time;

    /**
     * @throws NullPointerException jika judul, hari, atau waktu null
     */
    public Activity(int id, String title, String day, String time) {
        this.id = id;
        this.title = Objects.requireNonNull(title, "Judul tidak boleh null");
        this.day = Objects.requireNonNull(day, "Hari tidak boleh null");
        this.time = Objects.requireNonNull(time, "Waktu tidak boleh null");
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDay() {
        return day;
    }

    public String getTime() {
        return time;
    }

    /** Mengembalikan salinan kegiatan dengan judul baru. */
    public Activity withTitle(String newTitle) {
        return new Activity(id, newTitle, day, time);
    }

    /** Mengembalikan salinan kegiatan dengan hari baru. */
    public Activity withDay(String newDay) {
        return new Activity(id, title, newDay, time);
    }

    /** Mengembalikan salinan kegiatan dengan waktu baru. */
    public Activity withTime(String newTime) {
        return new Activity(id, title, day, newTime);
    }
}
