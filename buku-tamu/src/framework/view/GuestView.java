package framework.view;

import adapter.presenter.GuestPresenter;
import framework.util.InputUtil;
import usecase.GuestUseCase;

/**
 * Layer framework: UI konsol.
 * Menerima input user, memanggil use case, lalu mencetak hasil format dari presenter.
 */
public class GuestView {
    private final GuestUseCase guestUseCase;
    private final GuestPresenter guestPresenter;

    public GuestView(GuestUseCase guestUseCase, GuestPresenter guestPresenter) {
        this.guestUseCase = guestUseCase;
        this.guestPresenter = guestPresenter;
    }

    public void show() {
        while (true) {
            System.out.println(guestPresenter.formatGuestList(guestUseCase.getAllGuests()));

            System.out.println("Menu:");
            System.out.println("1. Daftarkan");
            System.out.println("2. Cari");
            System.out.println("3. Hapus");
            System.out.println("x. Keluar");

            String option = InputUtil.input("Pilih");

            if (option.equals("1")) {
                registerGuestView();
            } else if (option.equals("2")) {
                searchGuestView();
            } else if (option.equals("3")) {
                deleteGuestView();
            } else if (InputUtil.isCancel(option)) {
                break;
            } else {
                System.out.println(guestPresenter.formatInvalidChoice());
                System.out.println();
            }
        }
    }

    private void registerGuestView() {
        System.out.println("[Mendaftarkan Tamu]");

        String name = InputUtil.input("Nama (x Jika Batal)");
        if (InputUtil.isCancel(name)) {
            System.out.println();
            return;
        }

        String purpose = InputUtil.input("Tujuan Kunjungan (x Jika Batal)");
        if (InputUtil.isCancel(purpose)) {
            System.out.println();
            return;
        }

        System.out.println(guestPresenter.formatRegisterSuccess(guestUseCase.registerGuest(name, purpose)));
        System.out.println();
    }

    private void searchGuestView() {
        System.out.println("[Mencari Tamu]");

        String keyword = InputUtil.input("Nama (x Jika Batal)");
        if (InputUtil.isCancel(keyword)) {
            System.out.println();
            return;
        }

        System.out.println(guestPresenter.formatSearchResult(keyword, guestUseCase.searchGuests(keyword)));
        System.out.println();
    }

    private void deleteGuestView() {
        System.out.println("[Menghapus Tamu]");

        String idInput = InputUtil.input("[ID Tamu] yang dihapus (x Jika Batal)");
        if (InputUtil.isCancel(idInput)) {
            System.out.println();
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idInput);
        } catch (NumberFormatException e) {
            System.out.println(guestPresenter.formatInvalidId());
            System.out.println();
            return;
        }

        if (guestUseCase.deleteGuest(id)) {
            System.out.println(guestPresenter.formatDeleteSuccess());
        } else {
            System.out.println(guestPresenter.formatDeleteFailed(id));
        }
        System.out.println();
    }
}
