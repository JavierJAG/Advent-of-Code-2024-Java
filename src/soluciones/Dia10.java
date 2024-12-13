package soluciones;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Dia10 {
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/input/dia10.txt"));
            String linea;
            ArrayList<String> map = new ArrayList<>();
            while ((linea = br.readLine()) != null) {
                map.add(linea);
            }
            String[] mapa = map.toArray(new String[0]);

            int[][] newMap = parseMap(mapa);
            // Part A: Total score of trailheads
            int totalScore = calculateTotalTrailheadScore(newMap);
            System.out.println("Total score of all trailheads: " + totalScore);

            // Part B: Total rating of trailheads
            int totalRating = calculateTotalTrailheadRating(newMap);
            System.out.println("Total rating of all trailheads: " + totalRating);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Parse the input map into a 2D integer array
    private static int[][] parseMap(String[] mapInput) {
        int rows = mapInput.length;
        int cols = mapInput[0].length();
        int[][] map = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                map[i][j] = mapInput[i].charAt(j) - '0';
            }
        }
        return map;
    }

    // Calculate the total score of all trailheads (Part A)
    private static int calculateTotalTrailheadScore(int[][] map) {
        int totalScore = 0;
        int rows = map.length;
        int cols = map[0].length;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (map[i][j] == 0) { // Found a trailhead
                    totalScore += calculateTrailheadScore(map, i, j);
                }
            }
        }
        return totalScore;
    }

    // Calculate the score for a single trailhead
    private static int calculateTrailheadScore(int[][] map, int startRow, int startCol) {
        int rows = map.length;
        int cols = map[0].length;
        boolean[][] visited = new boolean[rows][cols];
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{startRow, startCol});
        visited[startRow][startCol] = true;

        int score = 0;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int row = current[0];
            int col = current[1];
            int currentHeight = map[row][col];

            if (currentHeight == 9) {
                score++;
                continue;
            }

            // Explore neighbors
            int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
            for (int[] dir : directions) {
                int newRow = row + dir[0];
                int newCol = col + dir[1];

                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols && !visited[newRow][newCol]) {
                    int neighborHeight = map[newRow][newCol];
                    if (neighborHeight == currentHeight + 1) { // Valid trail step
                        visited[newRow][newCol] = true;
                        queue.add(new int[]{newRow, newCol});
                    }
                }
            }
        }

        return score;
    }

    // Calculate the total rating of all trailheads (Part B)
    private static int calculateTotalTrailheadRating(int[][] map) {
        int totalRating = 0;
        int rows = map.length;
        int cols = map[0].length;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (map[i][j] == 0) { // Found a trailhead
                    totalRating += calculateTrailheadRating(map, i, j);
                }
            }
        }
        return totalRating;
    }

    // Calculate the rating for a single trailhead
    private static int calculateTrailheadRating(int[][] map, int startRow, int startCol) {
        int rows = map.length;
        int cols = map[0].length;
        return dfs(map, startRow, startCol, new boolean[rows][cols], map[startRow][startCol]);
    }

    // Depth-First Search to count distinct trails
    private static int dfs(int[][] map, int row, int col, boolean[][] visited, int currentHeight) {
        int rows = map.length;
        int cols = map[0].length;

        if (row < 0 || row >= rows || col < 0 || col >= cols || visited[row][col] || map[row][col] != currentHeight) {
            return 0;
        }

        if (currentHeight == 9) {
            return 1; // Found a trail ending at height 9
        }

        visited[row][col] = true;

        int trails = 0;
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] dir : directions) {
            trails += dfs(map, row + dir[0], col + dir[1], visited, currentHeight + 1);
        }

        visited[row][col] = false; // Backtrack
        return trails;
    }
}
