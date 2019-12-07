package day5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println(partOne("src/main/resources/day5/input_test_1_1.txt"));
        System.out.println(partOne("src/main/resources/day5/input_test_1_2.txt"));

        String fileName = "src/main/resources/day5/input.txt";
        partOne(fileName);  // 11193703
        partTwo(fileName);  // 12410607
    }

    private static List<Integer> partOne(String fileName) throws IOException {
        List<Integer> program = readProgram(fileName);

        for (int i = 0; i < program.size(); ) {
            int opCode = program.get(i) % 100;

            if (opCode == 99) {
                break;
            } else if (opCode == 1) {
                i = add(i, program);
            } else if (opCode == 2) {
                i = multiply(i, program);
            } else if (opCode == 3) {
                i = input(i, 1, program);
            } else if (opCode == 4) {
                i = output(i, program);
            }
        }

        return program;
    }

    private static List<Integer> partTwo(String fileName) throws IOException {
        List<Integer> program = readProgram(fileName);

        for (int i = 0; i < program.size(); ) {
            int opCode = program.get(i) % 100;

            if (opCode == 99) {
                break;
            } else if (opCode == 1) {
                i = add(i, program);
            } else if (opCode == 2) {
                i = multiply(i, program);
            } else if (opCode == 3) {
                i = input(i, 5, program);
            } else if (opCode == 4) {
                i = output(i, program);
            } else if (opCode == 5) {
                i = jumpIfTrue(i, program);
            } else if (opCode == 6) {
                i = jumpIfFalse(i, program);
            } else if (opCode == 7) {
                i = lessThan(i, program);
            } else if (opCode == 8) {
                i = equals(i, program);
            }
        }

        return program;
    }

    private static int add(int instructionPointer, List<Integer> program) {
        int param1 = getParam(1, instructionPointer, program);
        int param2 = getParam(2, instructionPointer, program);
        int resultPosition = program.get(instructionPointer + 3);

        program.set(resultPosition, param1 + param2);

        return instructionPointer + 4;
    }

    private static int multiply(int instructionPointer, List<Integer> program) {
        int param1 = getParam(1, instructionPointer, program);
        int param2 = getParam(2, instructionPointer, program);
        int resultPosition = program.get(instructionPointer + 3);

        program.set(resultPosition, param1 * param2);

        return instructionPointer + 4;
    }

    private static int input(int instructionPointer, int inputValue, List<Integer> program) {
        int param1 = program.get(instructionPointer + 1);
        program.set(param1, inputValue);

        return instructionPointer + 2;
    }

    private static int output(int instructionPointer, List<Integer> program) {
        int param1 = program.get(instructionPointer + 1);
        System.out.println(program.get(param1));

        return instructionPointer + 2;
    }

    private static int jumpIfTrue(int instructionPointer, List<Integer> program) {
        int param1 = getParam(1, instructionPointer, program);
        int param2 = getParam(2, instructionPointer, program);

        if (param1 != 0) {
            return param2;
        } else {
            return instructionPointer + 3;
        }
    }

    private static int jumpIfFalse(int instructionPointer, List<Integer> program) {
        int param1 = getParam(1, instructionPointer, program);
        int param2 = getParam(2, instructionPointer, program);

        if (param1 == 0) {
            return param2;
        } else {
            return instructionPointer + 3;
        }
    }

    private static int lessThan(int instructionPointer, List<Integer> program) {
        int param1 = getParam(1, instructionPointer, program);
        int param2 = getParam(2, instructionPointer, program);
        int resultPosition = program.get(instructionPointer + 3);

        if (param1 < param2) {
            program.set(resultPosition, 1);
        } else {
            program.set(resultPosition, 0);
        }

        return instructionPointer + 4;
    }

    private static int equals(int instructionPointer, List<Integer> program) {
        int param1 = getParam(1, instructionPointer, program);
        int param2 = getParam(2, instructionPointer, program);
        int resultPosition = program.get(instructionPointer + 3);

        if (param1 == param2) {
            program.set(resultPosition, 1);
        } else {
            program.set(resultPosition, 0);
        }

        return instructionPointer + 4;
    }

    private static int getParam(int parameterIndex, int instructionPointer, List<Integer> program) {
        int paramMode = (int) (program.get(instructionPointer) / (100 * Math.pow(10, parameterIndex - 1)) % 10);
        return paramMode == 0 ? program.get(program.get(instructionPointer + parameterIndex)) : program.get(instructionPointer + parameterIndex);
    }

    private static List<Integer> readProgram(String fileName) throws IOException {
        return Files.lines(Paths.get(fileName))
                .flatMap(l -> Arrays.stream(l.split(",")))
                .map(Integer::valueOf)
                .collect(toList());
    }

}
