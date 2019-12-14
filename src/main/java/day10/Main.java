package day10;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class Main {

    public static void main(String[] args) throws IOException {
        String fileName = "src/main/resources/day10/input.txt";

        System.out.println(partOne("src/main/resources/day10/input_test_1_1.txt"));
        System.out.println(partOne("src/main/resources/day10/input_test_1_2.txt"));
        System.out.println(partOne("src/main/resources/day10/input_test_1_3.txt"));
        System.out.println(partOne("src/main/resources/day10/input_test_1_4.txt"));
        System.out.println(partOne("src/main/resources/day10/input_test_1_5.txt"));
        System.out.println(partOne(fileName));

        System.out.println(partTwo("src/main/resources/day10/input_test_2_1.txt", 9));
        System.out.println(partTwo("src/main/resources/day10/input_test_1_5.txt", 1));
        System.out.println(partTwo("src/main/resources/day10/input_test_1_5.txt", 2));
        System.out.println(partTwo("src/main/resources/day10/input_test_1_5.txt", 3));
        System.out.println(partTwo("src/main/resources/day10/input_test_1_5.txt", 10));
        System.out.println(partTwo("src/main/resources/day10/input_test_1_5.txt", 20));
        System.out.println(partTwo("src/main/resources/day10/input_test_1_5.txt", 50));
        System.out.println(partTwo("src/main/resources/day10/input_test_1_5.txt", 100));
        System.out.println(partTwo("src/main/resources/day10/input_test_1_5.txt", 199));
        System.out.println(partTwo("src/main/resources/day10/input_test_1_5.txt", 200));
        System.out.println(partTwo("src/main/resources/day10/input_test_1_5.txt", 201));
        System.out.println(partTwo("src/main/resources/day10/input_test_1_5.txt", 299));

        Position solutionPart2 = partTwo(fileName, 200);
        System.out.println(solutionPart2.getX() * 100 + solutionPart2.getY());
    }

    private static Position partTwo(String fileName, int magicNumber) throws IOException {
        char[][] map = readMap(fileName);
        List<Position> asteroids = getAllAsteroids(map);
        Position station = findStation(asteroids);
        int counter = 0;

        while (counter < magicNumber) {
            List<Position> visibleAsteroids = getAllVisibleAsteroidsSortedClockwise(station, asteroids);

            for (Position asteroid : visibleAsteroids) {
                asteroids.remove(asteroid);
                counter++;

                if (counter == magicNumber) {
                    return asteroid;
                }
            }
        }

        return null;
    }

    private static List<Position> getAllVisibleAsteroidsSortedClockwise(Position station, List<Position> asteroids) {
        List<Position> visibleAsteroids = getVisibleAsteroids(station, asteroids);

        List<Position> quarter1 = visibleAsteroids.stream().filter(a -> a.getX() >= station.getX() && a.getY() < station.getY()).collect(toList());
        List<Position> quarter2 = visibleAsteroids.stream().filter(a -> a.getX() >= station.getX() && a.getY() >= station.getY()).collect(toList());
        List<Position> quarter3 = visibleAsteroids.stream().filter(a -> a.getX() < station.getX() && a.getY() >= station.getY()).collect(toList());
        List<Position> quarter4 = visibleAsteroids.stream().filter(a -> a.getX() < station.getX() && a.getY() < station.getY()).collect(toList());

        Comparator<Position> comparator1 = (p1, p2) -> {
            float result = ((float) (p1.getX() - station.getX()) / (float) (station.getY() - p1.getY())) - ((float) (p2.getX() - station.getX()) / (float) (station.getY() - p2.getY()));
            if (result < 0) {
                return -1;
            } else if (result == 0) {
                return 0;
            } else {
                return 1;
            }
        };
        Comparator<Position> comparator2 = (p1, p2) -> {
            float result = ((float) (p1.getY() - station.getY()) / (float) (p1.getX() - station.getX())) - ((float) (p2.getY() - station.getY()) / (float) (p2.getX() - station.getX()));
            if (result < 0) {
                return -1;
            } else if (result == 0) {
                return 0;
            } else {
                return 1;
            }
        };
        Comparator<Position> comparator3 = (p1, p2) -> {
            float result = ((float) (station.getX() - p1.getX()) / (float) (p1.getY() - station.getY())) - ((float) (station.getX() - p2.getX()) / (float) (p2.getY() - station.getY()));
            if (result < 0) {
                return -1;
            } else if (result == 0) {
                return 0;
            } else {
                return 1;
            }
        };
        Comparator<Position> comparator4 = (p1, p2) -> {
            float result = ((float) (station.getY() - p1.getY()) / (float) (station.getX() - p1.getX())) - ((float) (station.getY() - p2.getY()) / (float) (station.getX() - p2.getX()));
            if (result < 0) {
                return -1;
            } else if (result == 0) {
                return 0;
            } else {
                return 1;
            }
        };

        quarter1.sort(comparator1);
        quarter2.sort(comparator2);
        quarter3.sort(comparator3);
        quarter4.sort(comparator4);

        visibleAsteroids = new ArrayList<>();
        visibleAsteroids.addAll(quarter1);
        visibleAsteroids.addAll(quarter2);
        visibleAsteroids.addAll(quarter3);
        visibleAsteroids.addAll(quarter4);

        return visibleAsteroids;
    }

    private static Position findStation(List<Position> asteroids) {
        int maxAsteroids = 0;
        Position station = null;
        for (Position source : asteroids) {
            List<Position> visibleAsteroids = getVisibleAsteroids(source, asteroids);
            if (visibleAsteroids.size() > maxAsteroids) {
                maxAsteroids = visibleAsteroids.size();
                station = source;
            }
        }

        return station;
    }

    private static int partOne(String fileName) throws IOException {
        char[][] map = readMap(fileName);
        List<Position> asteroids = getAllAsteroids(map);

        int maxAsteroids = 0;
        for (Position source : asteroids) {
            List<Position> visibleAsteroids = getVisibleAsteroids(source, asteroids);
            if (visibleAsteroids.size() > maxAsteroids) {
                maxAsteroids = visibleAsteroids.size();
                System.out.println(source);
            }
        }

        return maxAsteroids;
    }

    private static List<Position> getVisibleAsteroids(Position source, List<Position> asteroids) {
        List<Position> visibleAsteroids = new ArrayList<>();

        for (Position destination : asteroids) {
            if (source.equals(destination)) {
                continue;
            }

            boolean hidden = false;
            for (Position asteroid : asteroids) {
                if (source.equals(asteroid) || destination.equals(asteroid)) {
                    continue;
                }

                if (asteroid.getX() > Math.max(source.getX(), destination.getX()) || asteroid.getY() > Math.max(source.getY(), destination.getY()) ||
                        asteroid.getX() < Math.min(source.getX(), destination.getX()) || asteroid.getY() < Math.min(source.getY(), destination.getY())) {
                    continue;
                }

                if (collinear(source, asteroid, destination)) {
                    hidden = true;
                    break;
                }
            }

            if (!hidden) {
                visibleAsteroids.add(destination);
            }
        }

        return visibleAsteroids;
    }

    private static boolean collinear(Position p1, Position p2, Position p3) {
        return (p1.getY() - p2.getY()) * (p1.getX() - p3.getX()) == (p1.getY() - p3.getY()) * (p1.getX() - p2.getX());
    }

    private static List<Position> getAllAsteroids(char[][] map) {
        List<Position> asteroids = new ArrayList<>();
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if (map[y][x] == '#') {
                    asteroids.add(new Position(x, y));
                }
            }
        }
        return asteroids;
    }

    private static char[][] readMap(String fileName) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(fileName));

        char[][] map = new char[lines.size()][lines.get(0).length()];
        for (int i = 0; i < lines.size(); i++) {
            for (int j = 0; j < lines.get(0).length(); j++) {
                map[i][j] = lines.get(i).charAt(j);
            }
        }
        return map;
    }
}
