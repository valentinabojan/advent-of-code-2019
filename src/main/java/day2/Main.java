package day2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class Main {

    public static void main(String[] args) throws IOException {
        String fileName = "src/main/resources/day2/input.txt";

        System.out.println(testPartOne("src/main/resources/day2/input_test_1_1.txt"));
        System.out.println(testPartOne("src/main/resources/day2/input_test_1_2.txt"));
        System.out.println(testPartOne("src/main/resources/day2/input_test_1_3.txt"));
        System.out.println(testPartOne("src/main/resources/day2/input_test_1_4.txt"));
        System.out.println(testPartOne("src/main/resources/day2/input_test_1_5.txt"));

        System.out.println(partOne(fileName).get(0));
        System.out.println(partTwo(fileName));
    }

    private static List<Integer> testPartOne(String fileName) throws IOException {
        List<Integer> program = readProgram(fileName);
        computeIntCode(program);
        return program;
    }

    private static List<Integer> partOne(String fileName) throws IOException {
        List<Integer> program = readProgram(fileName);
        program.set(1, 12);
        program.set(2, 2);

        computeIntCode(program);
        return program;
    }

    private static int partTwo(String fileName) throws IOException {
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                List<Integer> program = readProgram(fileName);
                program.set(1, i);
                program.set(2, j);

                computeIntCode(program);
                if (program.get(0) == 19690720) {
                    return 100 * i + j;
                }
            }
        }

        return -1;
    }

    private static void computeIntCode(List<Integer> program) {
        for (int i = 0; i < program.size(); i += 4) {
            if (program.get(i) == 99) {
                break;
            }

            if (program.get(i) == 1) {
                int op1Position = program.get(i + 1);
                int op2Position = program.get(i + 2);
                int resultPosition = program.get(i + 3);
                program.set(resultPosition, program.get(op1Position) + program.get(op2Position));
                continue;
            }

            if (program.get(i) == 2) {
                int op1Position = program.get(i + 1);
                int op2Position = program.get(i + 2);
                int resultPosition = program.get(i + 3);
                program.set(resultPosition, program.get(op1Position) * program.get(op2Position));
            }
        }
    }

    private static List<Integer> readProgram(String fileName) throws IOException {
        return Files.lines(Paths.get(fileName))
                .flatMap(l -> Arrays.stream(l.split(",")))
                .map(Integer::valueOf)
                .collect(toList());
    }

}
