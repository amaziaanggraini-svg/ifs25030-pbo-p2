package framework.view;

import adapter.presenter.ContactPresenter;
import domain.entity.SortOption;
import framework.util.InputUtil;
import usecase.ContactUseCase;

/**
 * Tampilan konsol aplikasi kontak teman.
 * Tidak mengandung logika bisnis, hanya menangani interaksi user.
 */
public class ContactView {
    private final ContactUseCase contactUseCase;
    private final ContactPresenter presenter;

    public ContactView(ContactUseCase contactUseCase, ContactPresenter presenter) {
        this.contactUseCase = contactUseCase;
        this.presenter = presenter;
    }

    /** Menampilkan menu utama dan loop interaksi user. */
    public void show() {
        boolean running = true;
        while (running) {
            System.out.println(presenter.formatContacts(contactUseCase.getAllContacts()));
            printMenu();

            String input = InputUtil.input("Pilih");
            switch (input) {
                case "1" -> addContact();
                case "2" -> updateContact();
                case "3" -> searchContact();
                case "4" -> sortContact();
                case "5" -> removeContact();
                default -> {
                    if (InputUtil.isCancel(input)) {
                        running = false;
                    } else {
                        System.out.println(presenter.formatInvalidChoice());
                    }
                }
            }

            if (running) {
                System.out.println();
            }
        }
    }

    private void printMenu() {
        System.out.println("Menu:");
        System.out.println("1. Tambah");
        System.out.println("2. Ubah");
        System.out.println("3. Cari");
        System.out.println("4. Urutkan");
        System.out.println("5. Hapus");
        System.out.println("x. Keluar");
    }

    /** Form tambah kontak. Mengetik x di kolom mana pun membatalkan proses. */
    private void addContact() {
        System.out.println("[Menambah Kontak]");
        String name = InputUtil.input("Nama (x Jika Batal)");
        if (InputUtil.isCancel(name)) {
            return;
        }

        String phone = InputUtil.input("Telepon");
        if (InputUtil.isCancel(phone)) {
            return;
        }

        String email = InputUtil.input("Email");
        if (InputUtil.isCancel(email)) {
            return;
        }

        System.out.println(presenter.formatAddSuccess(contactUseCase.addContact(name, phone, email)));
    }

    /** Form ubah nama, telepon, dan/atau email (parsial). */
    private void updateContact() {
        System.out.println("[Mengubah Kontak]");
        String strId = InputUtil.input("ID Kontak yang diubah (x Jika Batal)");
        if (InputUtil.isCancel(strId)) {
            return;
        }

        Integer id = parseId(strId);
        if (id == null) {
            return;
        }

        String newName = InputUtil.input("Nama Baru (Kosongkan jika tidak ingin mengubah)");
        String newPhone = InputUtil.input("Telepon Baru (Kosongkan jika tidak ingin mengubah)");
        String newEmail = InputUtil.input("Email Baru (Kosongkan jika tidak ingin mengubah)");

        // Input kosong = tidak diubah; aturan ini ditangani oleh use case
        if (contactUseCase.updateContact(id, newName, newPhone, newEmail)) {
            System.out.println(presenter.formatUpdateSuccess());
        } else {
            System.out.println(presenter.formatUpdateFailed(id));
        }
    }

    private void searchContact() {
        System.out.println("[Mencari Kontak]");
        String keyword = InputUtil.input("Kata Kunci (x Jika Batal)");

        if (!InputUtil.isCancel(keyword)) {
            System.out.println(presenter.formatSearchResults(contactUseCase.searchContacts(keyword), keyword));
        }
    }

    private void sortContact() {
        System.out.println("[Mengurutkan Kontak]");
        System.out.println("Pilihan Pengurutan:");
        System.out.println("1. Nama (A-Z)");
        System.out.println("2. Nama (Z-A)");
        System.out.println("x. Batal");

        String input = InputUtil.input("Pilih");
        if (InputUtil.isCancel(input)) {
            return;
        }

        SortOption option = mapSortOption(input);
        if (option == null) {
            System.out.println(presenter.formatInvalidSortOption());
            return;
        }

        System.out.println(presenter.formatSortedContacts(contactUseCase.sortContacts(option)));
    }

    private void removeContact() {
        System.out.println("[Menghapus Kontak]");
        String strId = InputUtil.input("[ID Kontak] yang dihapus (x Jika Batal)");
        if (InputUtil.isCancel(strId)) {
            return;
        }

        Integer id = parseId(strId);
        if (id == null) {
            return;
        }

        if (contactUseCase.removeContact(id)) {
            System.out.println(presenter.formatRemoveSuccess());
        } else {
            System.out.println(presenter.formatRemoveFailed(id));
        }
    }

    /** @return ID jika valid, null jika tidak valid (error sudah ditampilkan) */
    private Integer parseId(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            System.out.println(presenter.formatInvalidId());
            return null;
        }
    }

    private SortOption mapSortOption(String input) {
        return switch (input) {
            case "1" -> SortOption.NAME_ASC;
            case "2" -> SortOption.NAME_DESC;
            default -> null;
        };
    }
}