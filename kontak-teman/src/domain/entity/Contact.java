package domain.entity;

import java.util.Objects;

/**
 * Entity inti yang merepresentasikan satu kontak teman.
 * Immutable: perubahan data dilakukan dengan membuat objek baru lewat {@code withX(...)}.
 */
public class Contact {
    /** ID unik kontak, tidak boleh diubah setelah dibuat. */
    private final int id;

    private final String name;
    private final String phone;
    private final String email;

    /**
     * @throws NullPointerException jika nama, telepon, atau email null
     */
    public Contact(int id, String name, String phone, String email) {
        this.id = id;
        this.name = Objects.requireNonNull(name, "Nama tidak boleh null");
        this.phone = Objects.requireNonNull(phone, "Telepon tidak boleh null");
        this.email = Objects.requireNonNull(email, "Email tidak boleh null");
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    /** Mengembalikan salinan kontak dengan nama baru. */
    public Contact withName(String newName) {
        return new Contact(id, newName, phone, email);
    }

    /** Mengembalikan salinan kontak dengan telepon baru. */
    public Contact withPhone(String newPhone) {
        return new Contact(id, name, newPhone, email);
    }

    /** Mengembalikan salinan kontak dengan email baru. */
    public Contact withEmail(String newEmail) {
        return new Contact(id, name, phone, newEmail);
    }
}
