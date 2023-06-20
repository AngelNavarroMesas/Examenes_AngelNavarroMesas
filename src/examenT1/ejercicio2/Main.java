package examenT1.ejercicio2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        File alumnosInput = new File("src\\examenT1\\ejercicio2\\alumnos.txt");
        File alumnosOutput = new File("src\\examenT1\\ejercicio2\\alumnos_mayores.txt");

        String comando1 = "java src\\examenT1\\ejercicio2\\MayoresEdad.java";
        String comando2 = "java src\\examenT1\\ejercicio2\\OrdenaNombres.java";

        ProcessBuilder pb1 = new ProcessBuilder(comando1.split(" "));
        ProcessBuilder pb2 = new ProcessBuilder(comando2.split(" "));

        pb1.redirectInput(alumnosInput);
        pb2.redirectOutput(alumnosOutput);

        pb1.redirectError(ProcessBuilder.Redirect.INHERIT);
        pb2.redirectError(ProcessBuilder.Redirect.INHERIT);

        List<ProcessBuilder> lpb = new ArrayList<>();
        lpb.add(pb1);
        lpb.add(pb2);

        try {
            List<Process> processes = ProcessBuilder.startPipeline(lpb);

            int salidaProceso1 = processes.get(0).waitFor();
            int salidaProceso2 = processes.get(1).waitFor();

            if (salidaProceso1 == 0) {
                System.out.println("El proceso 1 ha finalizado con éxito");
            } else {
                System.out.println("El proceso 1 ha finalizado con el código de error: " + salidaProceso1);
            }

            if (salidaProceso2 == 0) {
                System.out.println("El proceso 2 ha finalizado con éxito");
            } else {
                System.out.println("El proceso 2 ha finalizado con el código de error: " + salidaProceso2);
            }

        } catch (IOException e) {
            System.err.println("Error durante la ejecución del proceso");
            System.exit(1);
        } catch (InterruptedException e) {
            System.err.println("Proceso interrumpido");
            System.exit(2);
        }
    }
}
