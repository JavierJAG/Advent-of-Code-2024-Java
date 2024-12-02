package soluciones;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Dia1 {
    public static void main(String[] args) {
        try {
            parteA();
            parteB();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void parteA() throws IOException {

      BufferedReader br = new BufferedReader(new FileReader("src/input/dia1.txt"));
            ArrayList<Integer> izquierda = new ArrayList<>();
            ArrayList<Integer> derecha = new ArrayList<>();
            ArrayList<Integer> distancia = new ArrayList<>();
            String linea;

            // Leer línea por línea del archivo
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();  // Eliminar espacios al inicio y final

                String[] partes = linea.split("\\s+");  // Dividir por espacios
                izquierda.add(Integer.parseInt(partes[0]));
                derecha.add(Integer.parseInt(partes[1]));
            }

            // Verificar que las listas tengan el mismo tamaño
            if (izquierda.size() != derecha.size()) {
                throw new IllegalArgumentException("Las listas 'izquierda' y 'derecha' deben tener el mismo tamaño");
            }

            // Ordenar ambas listas
            Collections.sort(izquierda);
            Collections.sort(derecha);

            // Calcular las distancias y sumarlas
            int sumaDistancia = 0;
            for (int i = 0; i < izquierda.size(); i++) {
                int dist = Math.abs(derecha.get(i) - izquierda.get(i));
                distancia.add(dist);
                sumaDistancia += dist;  // Sumar directamente la distancia
            }

            // Imprimir la suma total
            System.out.println("Solución para A: " + sumaDistancia);
}


    public static void parteB() throws IOException {
            BufferedReader br = new BufferedReader(new FileReader("src/input/dia1.txt"));
            ArrayList<Integer> izquierda = new ArrayList<>();
            ArrayList<Integer> derecha = new ArrayList<>();
            String linea;

            // Leer línea por línea del archivo
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();  // Eliminar espacios al inicio y final

                String[] partes = linea.split("\\s+");  // Dividir por espacios
                izquierda.add(Integer.parseInt(partes[0]));
                derecha.add(Integer.parseInt(partes[1]));
            }

            // Verificar que las listas tengan el mismo tamaño
            if (izquierda.size() != derecha.size()) {
                throw new IllegalArgumentException("Las listas 'izquierda' y 'derecha' deben tener el mismo tamaño");
            }
            int puntuacionSimilitud = 0;

            // Iterar sobre los elementos de la lista izquierda
            for (int i = 0; i < izquierda.size(); i++) {
                int numeroIzquierda = izquierda.get(i);
                // Contar cuántas veces aparece el número de la lista izquierda en la lista derecha
                long frecuencia = derecha.stream().filter(num -> num == numeroIzquierda).count();
                // Multiplicar el número por su frecuencia y añadir al puntaje total
                puntuacionSimilitud += numeroIzquierda * frecuencia;
            }

            // Imprimir la puntuación total de similitud
            System.out.println("Solucion para B: " + puntuacionSimilitud);
        }

}