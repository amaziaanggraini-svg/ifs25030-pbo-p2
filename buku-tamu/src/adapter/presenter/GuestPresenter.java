package adapter.presenter;

import domain.entity.Guest;

import java.util.ArrayList;
import java.util.List;

/**
 * Presenter yang HANYA memformat data menjadi String.
 * Tidak mencetak apa pun; pencetakan dilakukan oleh View.
 */
public class GuestPresenter {

    /** Format satu tamu: id | nama | tujuan. */
    private String format(Guest guest) {
        return String.format("%d | %s | %s",
                guest.getId(), guest.getName(), guest.getPurpose());
    }

    /** Helper umum: header + isi daftar, atau pesan kosong jika tidak ada data. */
    private String formatList(String header, List<Guest> guests, String emptyMessage) {
        List<String> lines = new ArrayList<>();
        lines.add(header);

        if (guests.isEmpty()) {
            lines.add(emptyMessage);
        } else {
            for (Guest guest : guests) {
                lines.add(format(guest));
            }
        }
        return String.join(System.lineSeparator(), lines);
    }

    public String formatGuestList(List<Guest> guests) {
        return formatList("Daftar Tamu:", guests, "- Data tamu belum tersedia!");
    }

    public String formatSearchResult(String keyword, List<Guest> guests) {
        return formatList("Hasil Pencarian: \"" + keyword + "\"", guests, "- Tamu tidak ditemukan!");
    }

    public String formatRegisterSuccess(Guest guest) {
        return "Berhasil mendaftarkan tamu: " + format(guest);
    }

    public String formatDeleteSuccess() {
        return "Berhasil menghapus tamu.";
    }

    public String formatDeleteFailed(int id) {
        return "[!] Gagal menghapus tamu dengan ID: " + id + ".";
    }

    public String formatInvalidChoice() {
        return "[!] Pilihan tidak dimengerti.";
    }

    public String formatInvalidId() {
        return "[!] ID tidak valid!";
    }
}
