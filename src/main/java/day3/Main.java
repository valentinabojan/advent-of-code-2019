package day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class Main {

    public static void main(String[] args) throws IOException {
        String fileName = "src/main/resources/day3/input.txt";

        System.out.println(partOne("src/main/resources/day3/input_test_1_1.txt"));
        System.out.println(partOne("src/main/resources/day3/input_test_1_2.txt"));
//        System.out.println(testPartOne("src/main/resources/day2/input_test_1_3.txt"));
//        System.out.println(testPartOne("src/main/resources/day2/input_test_1_4.txt"));
//        System.out.println(testPartOne("src/main/resources/day2/input_test_1_5.txt"));

//        System.out.println(partOne(fileName).get(0));
//        System.out.println(partTwo(fileName));
    }

    private static int partOne(String fileName) throws IOException {
        List<List<Position>> wires = readWires(fileName);
        Position centralPort = new Position(0, 0);

        return wires.get(0).stream()
                .filter(wires.get(1)::contains)
                .filter(position -> !position.equals(centralPort))
                .mapToInt(position -> getManhattanDistance(position, centralPort))
                .min()
                .getAsInt();
    }

    private static int getManhattanDistance(Position point1, Position point2) {
        return Math.abs(point1.getX() - point2.getX()) +
                Math.abs(point1.getY() - point2.getY());
    }

    private static List<List<Position>> readWires(String fileName) throws IOException {
        return Files.lines(Paths.get(fileName))
                .map(wireString -> {
                    List<Position> wire = new ArrayList<>();
                    wire.add(new Position(0, 0));

                    for (String positionCode : wireString.split(",")) {
                        String code = positionCode.substring(0, 1);
                        Integer distance = Integer.valueOf(positionCode.substring(1));

                        int lastX = wire.get(wire.size() - 1).getX();
                        int lastY = wire.get(wire.size() - 1).getY();

                        if (positionCode.startsWith("R")) {
                            for (int i = lastX + 1; i <= lastX + distance; i++) {
                                wire.add(new Position(i, lastY));
                            }
                        } else if (positionCode.startsWith("L")) {
                            for (int i = lastX - 1; i >= lastX - distance; i--) {
                                wire.add(new Position(i, lastY));
                            }
                        } else if (positionCode.startsWith("U")) {
                            for (int i = lastY + 1; i <= lastY + distance; i++) {
                                wire.add(new Position(lastX, i));
                            }
                        } else if (positionCode.startsWith("D")) {
                            for (int i = lastY - 1; i >= lastY - distance; i--) {
                                wire.add(new Position(lastX, i));
                            }
                        }
                    }

                    return wire;
                })
                .collect(toList());
    }

//    private static int partTwo(String fileName) throws IOException {
//        for (int i = 0; i < 100; i++) {
//            for (int j = 0; j < 100; j++) {
//                List<Integer> program = readProgram(fileName);
//                program.set(1, i);
//                program.set(2, j);
//
//                computeIntCode(program);
//                if (program.get(0) == 19690720) {
//                    return 100 * i + j;
//                }
//            }
//        }
//
//        return -1;
//    }

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

}
