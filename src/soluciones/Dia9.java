package soluciones;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Dia9 {
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/input/dia9.txt"));
            String linea;
            String lastLine = ""; // Variable para almacenar la última línea leída
            while ((linea = br.readLine()) != null) {
                lastLine = linea; // Almacenar la última línea
            }

            // Paso 1: Expandir el disco con la última línea leída
            char[] disk = expandDisk(lastLine);

            // Parte A: Compactar archivos individuales hacia la izquierda
            compactDisk(disk);

            // Calcular el checksum para la Parte A
            long checksumA = calculateChecksum(disk);
            System.out.println("Checksum final (Parte A): " + checksumA);

            // Parte B: Compactar archivos completos hacia la izquierda
            disk = expandDisk(lastLine); // Re-expandimos el disco para la Parte B
            compactDiskWholeFiles(disk);

            // Calcular el checksum para la Parte B
            long checksumB = calculateChecksum(disk);
            System.out.println("Checksum final (Parte B): " + checksumB);

        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo", e);
        }
    }


    // Convierte la representación densa en una representación expandida
    private static char[] expandDisk(String input) {
        List<Character> expanded = new ArrayList<>();
        boolean isFile = true; // Alterna entre archivo y espacio libre
        int fileID = 0; // ID inicial para los archivos
        for (char c : input.toCharArray()) {
            int length = c - '0'; // Longitud actual
            char symbol = isFile ? (char) ('0' + fileID) : '.'; // Archivo o espacio libre

            for (int i = 0; i < length; i++) {
                expanded.add(symbol);
            }

            if (isFile) {
                fileID++; // Incrementa el ID del archivo después de procesarlo
            }
            isFile = !isFile; // Alterna entre archivo y espacio libre
        }

        // Convierte la lista en un array de caracteres
        char[] result = new char[expanded.size()];
        for (int i = 0; i < expanded.size(); i++) {
            result[i] = expanded.get(i);
        }
        return result;
    }

    // Compacta los archivos hacia la izquierda
    private static void compactDisk(char[] disk) {
        // Recorremos el disco de derecha a izquierda
        for (int readIndex = disk.length - 1; readIndex > 0; readIndex--) {
            // Índice donde se escribirá el siguiente bloque de archivo
            int writeIndex = 0;
            while (writeIndex <= readIndex) {
                if (disk[readIndex] != '.') {  // Si encontramos un bloque de archivo
                    // Escribimos el bloque en el espacio vacío a la izquierda
                    if (disk[writeIndex] == '.') {
                        disk[writeIndex] = disk[readIndex];
                        disk[readIndex] = '.';
                    }
                }
                writeIndex++;
            }
        }
    }

    // Calcula el checksum del disco compacto
    private static long calculateChecksum(char[] disk) {
        long checksum = 0;

        for (int i = 0; i < disk.length; i++) {
            if (disk[i] != '.') { // Solo se cuentan los bloques ocupados
                int fileID = disk[i] - '0'; // Convertir el carácter al valor numérico del archivo (si es un número)
                checksum += (long) i * fileID; // Índice * ID del archivo
            }
        }

        return checksum;
    }

    // Compacta archivos completos hacia la izquierda (Parte B)
    private static void compactDiskWholeFiles(char[] disk) {
        // Procesar de derecha a izquierda para mover archivos completos
        for (int i = disk.length - 1; i >= 0; i--) {
            if (disk[i] != '.') {
                // Identificar el archivo actual
                char fileID = disk[i];
                int start = i;

                // Encontrar el inicio del archivo
                while (start > 0 && disk[start - 1] == fileID) {
                    start--;
                }

                // Determinar la longitud del archivo
                int fileLength = i - start + 1;

                // Buscar el espacio más a la izquierda donde pueda encajar
                int targetIndex = -1;
                for (int j = 0; j <= start - fileLength; j++) {
                    boolean canFit = true;

                    // Verificar si el espacio es contiguo y libre
                    for (int k = 0; k < fileLength; k++) {
                        if (disk[j + k] != '.') {
                            canFit = false;
                            break;
                        }
                    }

                    if (canFit) {
                        targetIndex = j;
                        break;
                    }
                }

                // Mover el archivo si se encontró un espacio
                if (targetIndex != -1) {
                    for (int k = 0; k < fileLength; k++) {
                        disk[targetIndex + k] = fileID;
                        disk[start + k] = '.';
                    }
                }

                // Actualizar la posición para seguir buscando
                i = start;
            }
        }
    }
}

