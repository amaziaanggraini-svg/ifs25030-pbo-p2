package framework.util;

import java.util.Scanner;

/**
 * Utility untuk membaca input string dari keyboard.
 */
public class InputUtil {
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Menampilkan prompt dan membaca satu baris input.
     * Jika input habis, mengembalikan "x" agar aplikasi keluar dengan aman.
     */
    public static String input(String info) {
        System.out.print(info + " : ");
        if (!scanner.hasNextLine()) {
            return "x";
        }
        return scanner.nextLine();
    }
}