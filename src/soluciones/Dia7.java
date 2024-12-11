package soluciones;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Dia7 {
    public static void main(String[] args) {
        try {
            parteA();
            parteB();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void parteA() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("src/input/dia7.txt"));
        String linea;
        long resultA = 0;
        while ((linea = br.readLine()) != null) {
            String[] input = linea.trim().split(":");
            long testValue = Long.parseLong(input[0].trim());
            String[] numbers = input[1].trim().split(" ");
            int[] parsedNumbers = new int[numbers.length];
            for (int i = 0; i < numbers.length; i++) {
                parsedNumbers[i] = Integer.parseInt(numbers[i]);
            }

            if (matchTestValue(testValue, parsedNumbers)) {
                resultA += testValue;
            }
        }
        br.close();
        System.out.println("Resultado parte A: " + resultA);
    }

    public static void parteB() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("src/input/dia7.txt"));
        String linea;
        long resultB = 0;
        while ((linea = br.readLine()) != null) {
            String[] input = linea.trim().split(":");
            long testValue = Long.parseLong(input[0].trim());
            String[] numbers = input[1].trim().split(" ");
            int[] parsedNumbers = new int[numbers.length];
            for (int i = 0; i < numbers.length; i++) {
                parsedNumbers[i] = Integer.parseInt(numbers[i]);
            }

            if (matchTestValueWithConcat(testValue, parsedNumbers)) {
                resultB += testValue;
            }
        }
        br.close();
        System.out.println("Resultado parte B: " + resultB);
    }

    public static boolean matchTestValue(long testValue, int[] numbers) {
        return evaluate(numbers, 0, numbers[0], testValue);
    }

    public static boolean matchTestValueWithConcat(long testValue, int[] numbers) {
        return evaluateWithConcat(numbers, 0, numbers[0], testValue);
    }

    private static boolean evaluate(int[] numbers, int index, long currentResult, long testValue) {
        if (index == numbers.length - 1) {
            return currentResult == testValue;
        }

        // Add the next number
        if (evaluate(numbers, index + 1, currentResult + numbers[index + 1], testValue)) {
            return true;
        }

        // Multiply by the next number
        if (evaluate(numbers, index + 1, currentResult * numbers[index + 1], testValue)) {
            return true;
        }

        return false;
    }

    private static boolean evaluateWithConcat(int[] numbers, int index, long currentResult, long testValue) {
        if (index == numbers.length - 1) {
            return currentResult == testValue;
        }

        // Add the next number
        if (evaluateWithConcat(numbers, index + 1, currentResult + numbers[index + 1], testValue)) {
            return true;
        }

        // Multiply by the next number
        if (evaluateWithConcat(numbers, index + 1, currentResult * numbers[index + 1], testValue)) {
            return true;
        }

        // Concatenate the next number
        long concatenated = Long.parseLong(currentResult + "" + numbers[index + 1]);
        if (evaluateWithConcat(numbers, index + 1, concatenated, testValue)) {
            return true;
        }

        return false;
    }
}
