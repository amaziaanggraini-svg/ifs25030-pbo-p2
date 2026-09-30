package framework.view;

import adapter.presenter.GuestPresenter;
import domain.entity.Guest;
import framework.util.InputUtil;
import usecase.GuestUseCase;

public class GuestView {
    private final GuestUseCase guestUseCase;
    private final GuestPresenter guestPresenter;

    public GuestView(GuestUseCase guestUseCase, GuestPresenter guestPresenter) {
        this.guestUseCase = guestUseCase;
        this.guestPresenter = guestPresenter;
    }

    public void show() {
        while (true) {
            guestPresenter.printGuestList(guestUseCase.getAllGuests());

            System.out.println("Menu:");
            System.out.println("1. Daftarkan");
            System.out.println("2. Cari");
            System.out.println("3. Hapus");
            System.out.println("x. Keluar");

            String option = InputUtil.input("Pilih");

            if (option.equalsIgnoreCase("1")) {
                registerGuestView();
            } else if (option.equalsIgnoreCase("2")) {
                searchGuestView();
            } else if (option.equalsIgnoreCase("3")) {
                deleteGuestView();
            } else if (option.equalsIgnoreCase("x")) {
                break;
            } else {
                // PERBAIKAN TC-03, TC-07, TC-11: Penanganan pilihan menu tidak valid
                System.out.println("[!] Pilihan tidak dimengerti.");
                System.out.println();
            }
        }
    }

    private void registerGuestView() {
        System.out.println("[Mendaftarkan Tamu]");

        String name = InputUtil.input("Nama (x Jika Batal)");
        if (name.equalsIgnoreCase("x")) {
            System.out.println();
            return;
        }

        String purpose = InputUtil.input("Tujuan Kunjungan (x Jika Batal)");
        if (purpose.equalsIgnoreCase("x")) {
            System.out.println();
            return;
        }

        Guest guest = guestUseCase.registerGuest(name, purpose);
        guestPresenter.printSuccessRegister(guest);
        System.out.println();
    }

    private void searchGuestView() {
        System.out.println("[Mencari Tamu]");

        String keyword = InputUtil.input("Nama (x Jika Batal)");
        if (keyword.equalsIgnoreCase("x")) {
            System.out.println();
            return;
        }

        guestPresenter.printSearchResult(keyword, guestUseCase.searchGuests(keyword));
        System.out.println();
    }

    private void deleteGuestView() {
        System.out.println("[Menghapus Tamu]");

        String idInput = InputUtil.input("[ID Tamu] yang dihapus (x Jika Batal)");
        if (idInput.equalsIgnoreCase("x")) {
            System.out.println();
            return;
        }

        // PERBAIKAN TC-03 & TC-07: Validasi apakah input ID berupa angka
        try {
            Integer.parseInt(idInput);
        } catch (NumberFormatException e) {
            System.out.println("[!] ID tidak valid!");
            System.out.println();
            return;
        }

        boolean success = guestUseCase.deleteGuest(idInput);
        if (success) {
            System.out.println("Berhasil menghapus tamu.");
        } else {
            // PERBAIKAN TC-03 & TC-07: Pesan ID tidak ditemukan di daftar
            System.out.println("[!] Gagal menghapus tamu dengan ID: " + idInput + ".");
        }
        System.out.println();
    }
}