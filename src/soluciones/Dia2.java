package soluciones;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Dia2 {
    public static void main(String[] args) {
        try {
            parteA();
            parteB();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void parteA() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("src/input/dia2.txt"))) {
            int safeReports = 0;
            String line;
            while ((line = br.readLine()) != null) {
                int[] levels = parseReport(line);
                if (isSafe(levels)) {
                    safeReports++;
                }
            }
            System.out.println("Solución parte A: " + safeReports);
        }
    }

    public static void parteB() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("src/input/dia2.txt"))) {
            int safeReports = 0;
            String line;
            while ((line = br.readLine()) != null) {
                int[] levels = parseReport(line);
                if (isSafe(levels) || canBeMadeSafe(levels)) {
                    safeReports++;
                }
            }
            System.out.println("Solución parte B: " + safeReports);
        }
    }

    /**
     * Obtener los valores de los reports como array de enteros
     * @param line
     * @return
     */
    private static int[] parseReport(String line) {
        String[] reportData = line.trim().split(" ");
        int[] levels = new int[reportData.length];
        for (int i = 0; i < reportData.length; i++) {
            levels[i] = Integer.parseInt(reportData[i]);
        }
        return levels;
    }

    /**
     * Comprobar que se cumplan las condiciones para que sea seguro
     * @param levels
     * @return
     */
    private static boolean isSafe(int[] levels) {
        boolean increasing = true;
        boolean decreasing = true;

        for (int i = 0; i < levels.length - 1; i++) {
            int difference = levels[i + 1] - levels[i];
            if (Math.abs(difference) < 1 || Math.abs(difference) > 3) {
                return false;
            }
            if (difference > 0) {
                decreasing = false;
            } else if (difference < 0) {
                increasing = false;
            }
        }

        return increasing || decreasing;
    }

    /**
     * Para el apartado B, comprobar si eliminando un número se puede hacer seguro el report
     * @param levels
     * @return
     */
    private static boolean canBeMadeSafe(int[] levels) {
        for (int i = 0; i < levels.length; i++) {
            if (isSafeAfterRemoving(levels, i)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Comprueba si es seguro tras eliminar un número
     * @param levels
     * @param indexToRemove
     * @return
     */
    private static boolean isSafeAfterRemoving(int[] levels, int indexToRemove) {
        int[] modifiedLevels = new int[levels.length - 1];
        int pos = 0;
        for (int i = 0; i < levels.length; i++) {
            if (i != indexToRemove) {
                modifiedLevels[pos++] = levels[i];
            }
        }
        return isSafe(modifiedLevels);
    }
}
