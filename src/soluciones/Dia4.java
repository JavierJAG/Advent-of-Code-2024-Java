package soluciones;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Dia4 {
    public static void main(String[] args) {
        try {
            parteA();
            parteB();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void parteA() throws IOException {
        // Leer el archivo y cargarlo en un ArrayList
        BufferedReader br = new BufferedReader(new FileReader("src/input/dia4.txt"));
        String linea;
        ArrayList<String> input = new ArrayList<>();
        while ((linea = br.readLine()) != null) {
            input.add(linea);
        }
        br.close();

        // Convertir el ArrayList en una matriz 2D
        String[][] matriz = new String[input.size()][input.get(0).length()];
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(0).length(); j++) {
                matriz[i][j] = String.valueOf(input.get(i).charAt(j));
            }
        }

        // Direcciones para buscar
        int[][] directions = {
                {0, 1},   // Derecha
                {0, -1},  // Izquierda
                {1, 0},   // Abajo
                {-1, 0},  // Arriba
                {1, 1},   // Diagonal abajo-derecha
                {-1, -1}, // Diagonal arriba-izquierda
                {1, -1},  // Diagonal abajo-izquierda
                {-1, 1}   // Diagonal arriba-derecha
        };

        String word = "XMAS";
        int count = 0;

        // Buscar la palabra "XMAS" en la matriz
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                // Si la celda actual coincide con la primera letra de la palabra
                if (matriz[i][j].equals("X")) {
                    // Buscar en todas las direcciones
                    for (int[] dir : directions) {
                        if (checkWord(matriz, word, i, j, dir[0], dir[1])) {
                            count++;
                        }
                    }
                }
            }
        }
        System.out.println("Solución parte A: " + count);
    }

    public static boolean checkWord(String[][] grid, String word, int startRow, int startCol, int dx, int dy) {
        int rows = grid.length;
        int cols = grid[0].length;

        for (int i = 0; i < word.length(); i++) {
            int newRow = startRow + i * dx;
            int newCol = startCol + i * dy;

            // Verificar que la nueva posición está dentro de los límites
            if (newRow < 0 || newRow >= rows || newCol < 0 || newCol >= cols) {
                return false;
            }

            // Verificar que el carácter coincide
            if (!grid[newRow][newCol].equals(String.valueOf(word.charAt(i)))) {
                return false;
            }
        }

        return true; // Se encontró la palabra
    }

    public static void parteB() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("src/input/dia4.txt"));
        String linea;
        ArrayList<String> input = new ArrayList<>();
        while ((linea = br.readLine()) != null) {
            input.add(linea);
        }
        br.close();


        char[][] grid = new char[input.size()][input.get(0).length()];
        for (int i = 0; i < input.size(); i++) {
            grid[i] = input.get(i).toCharArray();
        }


        int count = countXMAS(grid);
        System.out.println("Solución parte B: " + count);
    }

    public static int countXMAS(char[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        int count = 0;


        for (int i = 1; i < rows - 1; i++) {
            for (int j = 1; j < cols - 1; j++) {

                if (grid[i][j] == 'A' && isXMAS(grid, i, j)) {
                    count++;
                }
            }
        }
        return count;
    }

    public static boolean isXMAS(char[][] grid, int row, int col) {

        char topLeft = grid[row - 1][col - 1];
        char bottomLeft = grid[row + 1][col - 1];
        char topRight = grid[row - 1][col + 1];
        char bottomRight = grid[row + 1][col + 1];


        boolean topLeftBottomRight = (topLeft == 'M' && bottomRight == 'S') || (topLeft == 'S' && bottomRight == 'M');
        boolean bottomLeftTopRight = (bottomLeft == 'M' && topRight == 'S') || (bottomLeft == 'S' && topRight == 'M');


        return topLeftBottomRight && bottomLeftTopRight;
    }
}
