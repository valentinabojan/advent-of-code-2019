package day8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class Main {

    public static void main(String[] args) throws IOException {
        String fileName = "src/main/resources/day8/input.txt";

        System.out.println(partOne("src/main/resources/day8/input_test_1.txt", 3, 2));
        System.out.println(partOne(fileName, 25, 6));

        partTwo("src/main/resources/day8/input_test_2.txt", 2, 2);
        partTwo(fileName, 25, 6);
    }

    private static long partOne(String fileName, int width, int height) throws IOException {
        List<Integer> image = readImage(fileName);

        long zeros = Integer.MAX_VALUE;
        long ones = 0;
        long twos = 0;

        for (int i = 0; i < image.size(); i += width * height) {
            List<Integer> layer = image.subList(i, i + width * height);
            long currentZeros = layer.stream().filter(p -> p == 0).count();

            if (currentZeros < zeros) {
                zeros = currentZeros;
                ones = layer.stream().filter(p -> p == 1).count();
                twos = layer.stream().filter(p -> p == 2).count();
            }
        }

        return ones * twos;
    }

    private static void partTwo(String fileName, int width, int height) throws IOException {
        List<Integer> rawImage = readImage(fileName);
        int layersCount = rawImage.size() / (width * height);

        int[][] image = new int[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                for (int layer = 0; layer < layersCount; layer++) {
                    int pixel = rawImage.get(layer * width * height + i * width + j);
                    if (pixel != 2) {
                        image[i][j] = pixel;
                        break;
                    }
                }
            }
        }

        printImage(image, width, height);
    }

    private static void printImage(int[][] image, int width, int height) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (image[i][j] == 1) {
                    System.out.print("*");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }


    private static List<Integer> readImage(String fileName) throws IOException {
        return Files.lines(Paths.get(fileName))
                .flatMap(l -> Arrays.stream(l.split("")).filter(c -> !c.isEmpty()))
                .map(Integer::valueOf)
                .collect(toList());
    }
}
