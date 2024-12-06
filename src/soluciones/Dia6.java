package soluciones;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Dia6 {
    public static void main(String[] args) {
        try {
            parteA();
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
        char[][] mapa = new char[datosMapa.size()][datosMapa.get(0).length()];
        for (int i = 0; i < datosMapa.size(); i++) {
            for (int j = 0; j < datosMapa.get(0).length(); j++) {
                mapa[i][j] = datosMapa.get(i).charAt(j);
            }
        }
        boolean fin = false;
        while (!fin) {
            for (int i = 0; i < datosMapa.size(); i++) {
                for (int j = 0; j < datosMapa.get(0).length(); j++) {
                    if (mapa[i][j] != '.' && mapa[i][j] != '#' && mapa[i][j] != 'X') {
                        switch (mapa[i][j]) {
                            case '^':
                                if (i==0) {
                                    mapa[i][j] = 'X';
                                    fin = true;
                                } else if (mapa[i - 1][j] == '#') {
                                    mapa[i][j] = '>';
                                } else {
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
        for (int i = 0; i < datosMapa.size(); i++) {
            for (int j = 0; j < datosMapa.get(0).length(); j++) {
                if (mapa[i][j]=='X'){
                    casillasCruzadas++;
                }
            }
        }
        System.out.println("Resultado parte A: "+casillasCruzadas);
    }

    public static void parteB() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("src/input/inputPrueba.txt"));
        String linea;
    }
}
