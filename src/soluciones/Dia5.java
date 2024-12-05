package soluciones;

import java.io.*;
import java.util.*;

public class Dia5 {
    public static void parteA() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("src/input/dia5.txt"));
        Map<String, List<String>> graph = new HashMap<>();
        int resultA = 0;
        int resultB = 0;
        String linea;
        boolean endRules = false;

        List<String[]> invalidUpdates = new ArrayList<>();

        while ((linea = br.readLine()) != null) {
            linea = linea.trim();

            if (linea.isBlank()) {
                endRules = true;
                continue;
            }

            if (!endRules) {
                // Construir el grafo de reglas
                String[] input = linea.split("\\|");
                graph.putIfAbsent(input[0], new ArrayList<>());
                graph.get(input[0]).add(input[1]);
            } else {
                // Procesar actualizaciones
                String[] updates = linea.split(",");
                if (isValidUpdate(graph, updates)) {
                    resultA += Integer.parseInt(updates[updates.length / 2]);
                } else {
                    invalidUpdates.add(updates);
                }
            }
        }

        // Parte B: Corregir las actualizaciones inválidas
        for (String[] updates : invalidUpdates) {
            String[] correctedOrder = getCorrectOrder(graph, updates);
            resultB += Integer.parseInt(correctedOrder[correctedOrder.length / 2]);
        }

        System.out.println("Resultado parte A: " + resultA);
        System.out.println("Resultado parte B: " + resultB);
    }

    private static boolean isValidUpdate(Map<String, List<String>> graph, String[] updates) {
        Map<String, Integer> indexMap = new HashMap<>();
        for (int i = 0; i < updates.length; i++) {
            indexMap.put(updates[i], i);
        }

        for (Map.Entry<String, List<String>> entry : graph.entrySet()) {
            String from = entry.getKey();
            if (!indexMap.containsKey(from)) continue;
            int fromIndex = indexMap.get(from);

            for (String to : entry.getValue()) {
                if (!indexMap.containsKey(to)) continue;
                int toIndex = indexMap.get(to);
                if (toIndex < fromIndex) {
                    return false;
                }
            }
        }

        return true;
    }

    private static String[] getCorrectOrder(Map<String, List<String>> graph, String[] updates) {
        // Crear un subconjunto de las reglas que aplican a estas páginas
        Set<String> pagesInUpdate = new HashSet<>(Arrays.asList(updates));
        Map<String, List<String>> subGraph = new HashMap<>();

        for (String page : pagesInUpdate) {
            if (graph.containsKey(page)) {
                List<String> filteredDependencies = new ArrayList<>();
                for (String dep : graph.get(page)) {
                    if (pagesInUpdate.contains(dep)) {
                        filteredDependencies.add(dep);
                    }
                }
                subGraph.put(page, filteredDependencies);
            }
        }

        // Ordenar topológicamente las páginas
        List<String> sortedPages = topologicalSort(subGraph);

        // Convertir a un arreglo y devolver
        return sortedPages.toArray(new String[0]);
    }

    private static List<String> topologicalSort(Map<String, List<String>> graph) {
        Map<String, Integer> inDegree = new HashMap<>();
        for (String node : graph.keySet()) {
            inDegree.put(node, 0);
        }
        for (List<String> edges : graph.values()) {
            for (String to : edges) {
                inDegree.put(to, inDegree.getOrDefault(to, 0) + 1);
            }
        }

        Queue<String> queue = new LinkedList<>();
        for (Map.Entry<String, Integer> entry : inDegree.entrySet()) {
            if (entry.getValue() == 0) {
                queue.add(entry.getKey());
            }
        }

        List<String> sorted = new ArrayList<>();
        while (!queue.isEmpty()) {
            String current = queue.poll();
            sorted.add(current);

            if (graph.containsKey(current)) {
                for (String neighbor : graph.get(current)) {
                    inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                    if (inDegree.get(neighbor) == 0) {
                        queue.add(neighbor);
                    }
                }
            }
        }

        return sorted;
    }

    public static void main(String[] args) throws IOException {
        parteA();
    }
}

