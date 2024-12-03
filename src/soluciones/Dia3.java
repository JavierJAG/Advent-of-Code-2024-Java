package soluciones;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Dia3 {
    public static void main(String[] args) {
        try {
            parteA();
            parteB();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void parteA() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("src/input/dia3.txt"));
        int result = 0;
        String pattern = "mul\\(([0-9]{1,3}),([0-9]{1,3})\\)";
        Pattern compiledPattern = Pattern.compile(pattern);
        String line;
        while ((line = br.readLine()) != null) {
            Matcher matcher = compiledPattern.matcher(line);
            while (matcher.find()) {
                //Una forma de obtener los números es usando grupos, que los cogen porque están entre paréntesis
                String input = matcher.group(1);
                String input2 = matcher.group(2);
                result += Integer.parseInt(input) * Integer.parseInt(input2);
            }
        }
        System.out.println("Resultado parte A: " + result);
    }

    public static void parteB() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("src/input/dia3.txt"));
        String pattern = "mul\\(([0-9]{1,3}),([0-9]{1,3})\\)|do\\(\\)|don't\\(\\)";
        Pattern compiledPattern = Pattern.compile(pattern);

        boolean mulEnabled = true;
        int result = 0;
        String line;
        while ((line = br.readLine()) != null) {
            Matcher matcher = compiledPattern.matcher(line);
            while (matcher.find()) {
                String match = matcher.group();

                if (match.startsWith("do()")) {
                    mulEnabled = true;
                } else if (match.startsWith("don't()")) {
                    mulEnabled = false;
                } else if (match.startsWith("mul(") && mulEnabled) {
                    //Otra forma de obtener los números, con substring y split
                    String[] numbers = match.substring(4, match.length() - 1).split(",");
                    int x = Integer.parseInt(numbers[0]);
                    int y = Integer.parseInt(numbers[1]);

                    result += x * y;
                }
            }
        }
        System.out.println("Resultado parte B: " + result);
    }
}

