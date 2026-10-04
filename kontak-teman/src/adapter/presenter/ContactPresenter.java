package adapter.presenter;

import domain.entity.Contact;
import java.util.ArrayList;
import java.util.List;

/**
 * Presenter yang memformat data kontak menjadi String (tidak mencetak; pencetakan dilakukan oleh View).
 */
public class ContactPresenter {

    /** Format: id | nama | telepon | email. */
    private String format(Contact contact) {
        return String.format("%d | %s | %s | %s",
                contact.getId(), contact.getName(), contact.getPhone(), contact.getEmail());
    }

    private String formatList(List<Contact> contacts, String header, String emptyMessage) {
        List<String> lines = new ArrayList<>();
        lines.add(header);

        if (contacts.isEmpty()) {
            lines.add(emptyMessage);
        } else {
            for (Contact item : contacts) {
                lines.add(format(item));
            }
        }
        return String.join(System.lineSeparator(), lines);
    }

    public String formatContacts(List<Contact> contacts) {
        return formatList(contacts, "Daftar Kontak:", "- Data kontak belum tersedia!");
    }

    public String formatSearchResults(List<Contact> contacts, String keyword) {
        return formatList(contacts, "Hasil Pencarian: \"" + keyword + "\"", "- Kontak tidak ditemukan!");
    }

    public String formatSortedContacts(List<Contact> contacts) {
        return formatList(contacts, "Daftar Kontak (Terurut):", "- Data kontak belum tersedia!");
    }

    public String formatAddSuccess(Contact contact) {
        return String.format("Berhasil menambah kontak: %s", format(contact));
    }

    public String formatRemoveSuccess() {
        return "Berhasil menghapus kontak.";
    }

    public String formatRemoveFailed(int id) {
        return String.format("[!] Gagal menghapus kontak dengan ID: %d.", id);
    }

    public String formatUpdateSuccess() {
        return "Berhasil mengubah kontak.";
    }

    public String formatUpdateFailed(int id) {
        return String.format("[!] Gagal mengubah kontak dengan ID: %d.", id);
    }

    public String formatInvalidChoice() {
        return "[!] Pilihan tidak dimengerti.";
    }

    public String formatInvalidId() {
        return "[!] ID tidak valid!";
    }

    public String formatInvalidSortOption() {
        return "[!] Pilihan tidak valid!";
    }
}