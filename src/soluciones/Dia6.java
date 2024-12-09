package soluciones;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Dia6 {
    public static void main(String[] args) {
        try {
            parteA();
            //AVISO: La parte B tiene un tiempo de ejecución muy elevado (+30min)
            parteB();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void parteA() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("src/input/dia6.txt"));
        String linea;
        ArrayList<String> datosMapa = new ArrayList<String>();
        while ((linea = br.readLine()) != null) {
            datosMapa.add(linea);
        }
        br.close();
        //Crear el mapa
        char[][] mapa = new char[datosMapa.size()][datosMapa.get(0).length()];
        for (int i = 0; i < datosMapa.size(); i++) {
            for (int j = 0; j < datosMapa.get(0).length(); j++) {
                mapa[i][j] = datosMapa.get(i).charAt(j);
            }
        }

        boolean fin = false;
        while (!fin) {
            //Se comrpueban las condiciones del puzzle, cambiamos las casillas que pisamos por X y la siguiente por la
            // flecha en la dirección que toca
            for (int i = 0; i < datosMapa.size(); i++) {
                for (int j = 0; j < datosMapa.get(0).length(); j++) {
                    if (mapa[i][j] != '.' && mapa[i][j] != '#' && mapa[i][j] != 'X') {
                        switch (mapa[i][j]) {
                            case '^':
                                //Salimos del mapa y se acaba
                                if (i == 0) {
                                    mapa[i][j] = 'X';
                                    fin = true;
                                } else if (mapa[i - 1][j] == '#') {  //Tropezamos con obstáculo
                                    mapa[i][j] = '>';
                                } else {
                                    //Avanzar
                                    mapa[i][j] = 'X';
                                    mapa[i - 1][j] = '^';
                                }
                                break;
                            case '<':
                                if (j == 0) {
                                    mapa[i][j] = 'X';
                                    fin = true;
                                } else if (mapa[i][j - 1] == '#') {
                                    mapa[i][j] = '^';
                                } else {
                                    mapa[i][j] = 'X';
                                    mapa[i][j - 1] = '<';
                                }
                                break;
                            case '>':
                                if (j == mapa[0].length - 1) {
                                    mapa[i][j] = 'X';
                                    fin = true;
                                } else if (mapa[i][j + 1] == '#') {
                                    mapa[i][j] = 'v';
                                } else {
                                    mapa[i][j] = 'X';
                                    mapa[i][j + 1] = '>';
                                }
                                break;
                            case 'v':
                                if (i == mapa.length - 1) {
                                    mapa[i][j] = 'X';
                                    fin = true;
                                } else if (mapa[i + 1][j] == '#') {
                                    mapa[i][j] = '<';
                                } else {
                                    mapa[i][j] = 'X';
                                    mapa[i + 1][j] = 'v';
                                }
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
        }
        int casillasCruzadas = 0;
        //Contar las casillas por las que pasamos (con X)
        for (int i = 0; i < datosMapa.size(); i++) {
            for (int j = 0; j < datosMapa.get(0).length(); j++) {
                if (mapa[i][j] == 'X') {
                    casillasCruzadas++;
                }
            }
        }
        System.out.println("Resultado parte A: " + casillasCruzadas);
    }

    //AVISO: Tiempo de ejecución elevado, +30min
    public static void parteB() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("src/input/dia6.txt"));
        String linea;
        ArrayList<String> datosMapa = new ArrayList<String>();
        while ((linea = br.readLine()) != null) {
            datosMapa.add(linea);
        }
        br.close();
        //Crear el mapa
        char[][] mapaInicial = new char[datosMapa.size()][datosMapa.get(0).length()];
        for (int i = 0; i < datosMapa.size(); i++) {
            for (int j = 0; j < datosMapa.get(0).length(); j++) {
                mapaInicial[i][j] = datosMapa.get(i).charAt(j);
            }
        }

        int bucles = 0;
        boolean fin2 = false;

        while (!fin2) {
            //Cada vez que se va a añadir un 0, se crea una copia del mapa para no sobreescribir el original
            for (int a = 0; a < datosMapa.size(); a++) {
                for (int b = 0; b < datosMapa.get(0).length(); b++) {
                    char[][] mapa = new char[mapaInicial.length][mapaInicial[0].length];
                    for (int i = 0; i < mapaInicial.length; i++) {
                        mapa[i] = mapaInicial[i].clone();
                    }
                    if (mapa[a][b] != '.') {
                        continue;
                    } else {
                        System.out.println(a + ", " + b);
                        mapa[a][b] = '0';
                        boolean fin = false;
                        //Se guardan los giros. Si se realiza 2 veces el mismo giro, estamos en un loop, lo contamos y salimos
                        ArrayList<String> giros = new ArrayList<String>();
                        while (!fin) {
                            for (int i = 0; i < datosMapa.size(); i++) {
                                for (int j = 0; j < datosMapa.get(0).length(); j++) {
                                    if (mapa[i][j] != '.' && mapa[i][j] != '#' && mapa[i][j] != '0') {
                                        switch (mapa[i][j]) {
                                            case '^':
                                                if (i == 0) {
                                                    mapa[i][j] = '.';
                                                    fin = true;
                                                } else if (mapa[i - 1][j] == '#' || mapa[i - 1][j] == '0') {
                                                    String puntoGiro = String.valueOf(i) + "," + String.valueOf(j) + "," + String.valueOf('^');
                                                    if (giros.contains(puntoGiro)) {
                                                        bucles++;
                                                        fin = true;
                                                        break;
                                                    } else {
                                                        giros.add(puntoGiro);
                                                    }
                                                    mapa[i][j] = '>';
                                                } else {
                                                    mapa[i][j] = '.';
                                                    mapa[i - 1][j] = '^';
                                                }
                                                break;
                                            case '<':
                                                if (j == 0) {
                                                    mapa[i][j] = '.';
                                                    fin = true;
                                                } else if (mapa[i][j - 1] == '#' || mapa[i][j - 1] == '0') {
                                                    String puntoGiro = String.valueOf(i) + "," + String.valueOf(j) + "," + String.valueOf('<');
                                                    if (giros.contains(puntoGiro)) {
                                                        bucles++;
                                                        fin = true;
                                                        break;
                                                    } else {
                                                        giros.add(puntoGiro);
                                                    }
                                                    mapa[i][j] = '^';
                                                } else {
                                                    mapa[i][j] = '.';
                                                    mapa[i][j - 1] = '<';
                                                }
                                                break;
                                            case '>':
                                                if (j == mapa[0].length - 1) {
                                                    mapa[i][j] = '.';
                                                    fin = true;
                                                } else if (mapa[i][j + 1] == '#' || mapa[i][j + 1] == '0') {
                                                    String puntoGiro = String.valueOf(i) + "," + String.valueOf(j) + "," + String.valueOf('>');
                                                    if (giros.contains(puntoGiro)) {
                                                        bucles++;
                                                        fin = true;
                                                        break;
                                                    } else {
                                                        giros.add(puntoGiro);
                                                    }
                                                    mapa[i][j] = 'v';
                                                } else {
                                                    mapa[i][j] = '.';
                                                    mapa[i][j + 1] = '>';
                                                }
                                                break;
                                            case 'v':
                                                if (i == mapa.length - 1) {
                                                    mapa[i][j] = '.';
                                                    fin = true;
                                                } else if (mapa[i + 1][j] == '#' || mapa[i + 1][j] == '0') {
                                                    String puntoGiro = String.valueOf(i) + "," + String.valueOf(j) + "," + String.valueOf('v');
                                                    if (giros.contains(puntoGiro)) {
                                                        bucles++;
                                                        fin = true;
                                                        break;
                                                    } else {
                                                        giros.add(puntoGiro);
                                                    }
                                                    mapa[i][j] = '<';
                                                } else {
                                                    mapa[i][j] = '.';
                                                    mapa[i + 1][j] = 'v';
                                                }
                                                break;
                                            default:
                                                break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                fin2 = true;
            }
        }
        System.out.println("Resultado parte B: " + bucles);
    }
}