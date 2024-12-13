package soluciones;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Dia8 {
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/input/dia8.txt"));
            String linea;
            ArrayList<String> mapa = new ArrayList<>();
            while ((linea = br.readLine()) != null) {
                mapa.add(linea);
            }
            String[] map = mapa.toArray(new String[0]);
            System.out.println("Parte A: " + ParteA(map));
            System.out.println("Parte B: " + ParteB(map));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static int ParteA(String[] map) {
        int rows = map.length;
        int cols = map[0].length();
        Map<Character, List<int[]>> antennaPositions = new HashMap<>();

        // Parse the map to find antenna positions
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                char ch = map[r].charAt(c);
                if (ch != '.') {
                    antennaPositions.putIfAbsent(ch, new ArrayList<>());
                    antennaPositions.get(ch).add(new int[]{r, c});
                }
            }
        }

        Set<String> uniqueAntinodes = new HashSet<>();

        // Calculate antinodes for each frequency
        for (Map.Entry<Character, List<int[]>> entry : antennaPositions.entrySet()) {
            List<int[]> positions = entry.getValue();
            int n = positions.size();

            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    int[] p1 = positions.get(i);
                    int[] p2 = positions.get(j);

                    // Calculate potential antinodes
                    int dr = p2[0] - p1[0];
                    int dc = p2[1] - p1[1];

                    // Antinode 1 (closer to p1)
                    int antinodeR1 = p1[0] - dr;
                    int antinodeC1 = p1[1] - dc;
                    if (isValid(antinodeR1, antinodeC1, rows, cols)) {
                        uniqueAntinodes.add(antinodeR1 + "," + antinodeC1);
                    }

                    // Antinode 2 (closer to p2)
                    int antinodeR2 = p2[0] + dr;
                    int antinodeC2 = p2[1] + dc;
                    if (isValid(antinodeR2, antinodeC2, rows, cols)) {
                        uniqueAntinodes.add(antinodeR2 + "," + antinodeC2);
                    }
                }
            }
        }

        return uniqueAntinodes.size();
    }

    public static int ParteB(String[] map) {
        int rows = map.length;
        int cols = map[0].length();
        Map<Character, List<int[]>> antennaPositions = new HashMap<>();

        // Parse the map to find antenna positions
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                char ch = map[r].charAt(c);
                if (ch != '.') {
                    antennaPositions.putIfAbsent(ch, new ArrayList<>());
                    antennaPositions.get(ch).add(new int[]{r, c});
                }
            }
        }

        Set<String> uniqueAntinodes = new HashSet<>();

        // Calculate antinodes for each frequency
        for (Map.Entry<Character, List<int[]>> entry : antennaPositions.entrySet()) {
            List<int[]> positions = entry.getValue();
            int n = positions.size();

            for (int i = 0; i < n; i++) {
                int[] p1 = positions.get(i);
                // Add the antenna's own position as an antinode if it aligns with others
                uniqueAntinodes.add(p1[0] + "," + p1[1]);

                for (int j = i + 1; j < n; j++) {
                    int[] p2 = positions.get(j);

                    // Calculate all grid points exactly in line with p1 and p2
                    int dr = p2[0] - p1[0];
                    int dc = p2[1] - p1[1];

                    // Move along the line defined by p1 and p2
                    int k = 1;
                    while (true) {
                        int antinodeR1 = p1[0] - k * dr;
                        int antinodeC1 = p1[1] - k * dc;
                        int antinodeR2 = p2[0] + k * dr;
                        int antinodeC2 = p2[1] + k * dc;

                        boolean added = false;
                        if (isValid(antinodeR1, antinodeC1, rows, cols)) {
                            uniqueAntinodes.add(antinodeR1 + "," + antinodeC1);
                            added = true;
                        }

                        if (isValid(antinodeR2, antinodeC2, rows, cols)) {
                            uniqueAntinodes.add(antinodeR2 + "," + antinodeC2);
                            added = true;
                        }

                        if (!added) {
                            break;
                        }

                        k++;
                    }
                }
            }
        }

        return uniqueAntinodes.size();
    }

    private static boolean isValid(int r, int c, int rows, int cols) {
        return r >= 0 && r < rows && c >= 0 && c < cols;
    }
}


