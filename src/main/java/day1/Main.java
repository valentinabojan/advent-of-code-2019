package day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws IOException {
        String part1_test_fileName = "src/main/resources/day1/input_test.txt";
        String fileName = "src/main/resources/day1/input.txt";

        System.out.println(partOne(part1_test_fileName));
        System.out.println(partOne(fileName));

        System.out.println(partTwo(fileName));
    }

    private static int partOne(String fileName) throws IOException {
        return Files.lines(Paths.get(fileName))
                .mapToInt(Integer::valueOf)
                .map(Main::computeFuelFormMass)
                .reduce(0, Integer::sum);
    }

    private static int partTwo(String fileName) throws IOException {
        return Files.lines(Paths.get(fileName))
                .mapToInt(Integer::valueOf)
                .map(Main::computeFuelFormMassRecursively)
                .reduce(0, Integer::sum);
    }

    private static int computeFuelFormMass(int mass) {
        return mass / 3 - 2;
    }

    private static int computeFuelFormMassRecursively(int mass) {
        int fuel = computeFuelFormMass(mass);

        if (fuel <= 0) {
            return 0;
        }

        return fuel + computeFuelFormMassRecursively(fuel);
    }
}
