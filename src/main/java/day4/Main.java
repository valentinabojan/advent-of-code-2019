package day4;

public class Main {

    public static void main(String[] args) {
        System.out.println(isPasswordCompliantForPartOne("111111"));  // true
        System.out.println(isPasswordCompliantForPartOne("111122"));  // true
        System.out.println(isPasswordCompliantForPartOne("122345"));  // true
        System.out.println(isPasswordCompliantForPartOne("123444"));  // true
        System.out.println(isPasswordCompliantForPartOne("223450"));  // false
        System.out.println(isPasswordCompliantForPartOne("123789"));  // false
        System.out.println(partOne(372037, 905157));                  // 481

        System.out.println(isPasswordCompliantForPartTwo("112233"));    // true
        System.out.println(isPasswordCompliantForPartTwo("123444"));    // false
        System.out.println(isPasswordCompliantForPartTwo("111122"));    // true
        System.out.println(isPasswordCompliantForPartTwo("113444"));    // true
        System.out.println(partTwo(372037, 905157));                    // 299
    }

    private static int partOne(int from, int to) {
        int count = 0;

        for (int i = from; i <= to; i++) {
            if (!isPasswordCompliantForPartOne(i + "")) {
                continue;
            }
            count++;
        }

        return count;
    }

    private static int partTwo(int from, int to) {
        int count = 0;

        for (int i = from; i <= to; i++) {
            if (!isPasswordCompliantForPartTwo(i + "")) {
                continue;
            }
            count++;
        }

        return count;
    }

    private static boolean isPasswordCompliantForPartOne(String password) {
        if (password.length() != 6) {
            return false;
        }
        if (!hasTwoAdjacentSameDigits(password)) {
            return false;
        }
        if (!hasOnlyIncreasingOrRepeatingDigits(password)) {
            return false;
        }
        return true;
    }

    private static boolean isPasswordCompliantForPartTwo(String password) {
        if (password.length() != 6) {
            return false;
        }
        if (!hasOnlyTwoAdjacentSameDigits(password)) {
            return false;
        }
        if (!hasOnlyIncreasingOrRepeatingDigits(password)) {
            return false;
        }
        return true;
    }

    private static boolean hasOnlyIncreasingOrRepeatingDigits(String password) {
        for (int i = 0; i < password.length() - 1; i++) {
            if (password.charAt(i) > password.charAt(i + 1)) {
                return false;
            }
        }

        return true;
    }

    private static boolean hasTwoAdjacentSameDigits(String password) {
        int repeats = 0;
        for (int i = 0; i < password.length() - 1; ) {
            if (password.charAt(i) == password.charAt(i + 1)) {
                repeats++;
                i += 2;
            } else {
                i++;
            }
        }

        return repeats % 2 == 1;
    }

    private static boolean hasOnlyTwoAdjacentSameDigits(String password) {
        int repeats = 0;
        for (int i = 0; i < password.length() - 1; i++) {
            if (password.charAt(i) == password.charAt(i + 1)) {
                repeats++;
            } else {
                if (repeats == 1) {
                    return true;
                }
                repeats = 0;
            }
        }

        return repeats == 1;
    }
}
