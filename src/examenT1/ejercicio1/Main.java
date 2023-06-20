package examenT1.ejercicio1;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    int opcionSeleccionada, num1, num2;
    String comando = "java src\\examenT1\\ejercicio1\\Calculadora.java";

    mostrarMenu();
    opcionSeleccionada = validarOpcion(1, 4);
    comando += " " + opcionSeleccionada;

    System.out.println();
    System.out.println("Introduzca el primer numero:");
    num1 = sc.nextInt();
    System.out.println("Introduzca el segundo numero:");
    num2 = sc.nextInt();

    comando += " " + num1 + " " + num2;

    ProcessBuilder pb = new ProcessBuilder(comando.split(" "));
    pb.inheritIO();

    try {
        Process process = pb.start();

        int salidaProceso = process.waitFor();
        if (salidaProceso == 0) {
            System.out.println("El proceso ha finalizado con éxito");
        } else {
            System.out.println("El proceso ha finalizado con el código de error: " + salidaProceso);
        }

    } catch (IOException e) {
        System.err.println("Error durante la ejecución del proceso");
        System.exit(1);

    } catch (InterruptedException e) {
        System.err.println("Proceso interrumpido");
        System.exit(2);
    }
}
    private static void mostrarMenu() {
        System.out.println();
        System.out.println("Elija una opcion:");
        System.out.println("1. SUMA");
        System.out.println("2. RESTA");
        System.out.println("3. MULTIPLICACIÓN");
        System.out.println("4. DIVISIÓN");
    }

    private static int validarOpcion(int min, int max) {
        Scanner sc = new Scanner(System.in);
        int opc;
        do {
            opc = sc.nextInt();
        } while (opc < min || opc > max);
        return opc;
    }
}
