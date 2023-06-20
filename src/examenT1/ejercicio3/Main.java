package examenT1.ejercicio3;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        String comandoNumerosAleatorios = "java src\\examenT1\\ejercicio3\\NumerosAleatorios.java";
        String comandoSumaNumeros = "java src\\examenT1\\ejercicio3\\SumaNumeros.java";
        String comandoMediaNumeros = "java src\\examenT1\\ejercicio3\\MediaNumeros.java";

        ArrayList<Process> procesosNumerosAleatorios = new ArrayList<>();
        ArrayList<Process> procesosMediaNumeros = new ArrayList<>();
        ArrayList<Process> procesosSumaNumeros = new ArrayList<>();

        try {
            File carpetaSalida = new File("src\\examenT1\\ejercicio3\\salidaNumerosAleatorios");
            if (!carpetaSalida.exists()) {
                carpetaSalida.mkdirs();
            }
            long inicio = System.currentTimeMillis();

            for (int i = 0; i < 10; i++) {
                File salida = new File("src\\examenT1\\ejercicio3\\salidaNumerosAleatorios\\"+i+".txt");
                ProcessBuilder pb = new ProcessBuilder(comandoNumerosAleatorios.split(" "));
                pb.redirectError(ProcessBuilder.Redirect.INHERIT);
                pb.redirectOutput(salida);
                procesosNumerosAleatorios.add(pb.start());
            }
            esperarEjecucionProcesos(procesosNumerosAleatorios);
            long fin = System.currentTimeMillis();
            System.out.println("La creación de los números aleatorios ha tardado en ejecutarse: " + (fin-inicio) + " milisegundos.");

            File salidaSumas = new File("src\\examenT1\\ejercicio3\\sumas.txt");
            File salidaMedias = new File("src\\examenT1\\ejercicio3\\medias.txt");
            salidaSumas.delete(); salidaMedias.delete();
            inicio = System.currentTimeMillis();

            for (int i = 0; i < 10; i++) {
                File entrada = new File("src\\examenT1\\ejercicio3\\salidaNumerosAleatorios\\"+i+".txt");
                ProcessBuilder pbSumas = new ProcessBuilder((comandoSumaNumeros + " " + entrada.getName()).split(" "));
                ProcessBuilder pbMedias = new ProcessBuilder((comandoMediaNumeros + " " + entrada.getName()).split(" "));

                pbSumas.redirectError(ProcessBuilder.Redirect.INHERIT);
                pbMedias.redirectError(ProcessBuilder.Redirect.INHERIT);

                pbSumas.redirectInput(entrada);
                pbMedias.redirectInput(entrada);

                pbSumas.redirectOutput(ProcessBuilder.Redirect.appendTo(salidaSumas));
                pbMedias.redirectOutput(ProcessBuilder.Redirect.appendTo(salidaMedias));

                procesosSumaNumeros.add(pbSumas.start());
                procesosMediaNumeros.add(pbMedias.start());
            }
            esperarEjecucionProcesos(procesosSumaNumeros);
            esperarEjecucionProcesos(procesosMediaNumeros);
            fin = System.currentTimeMillis();
            System.out.println("La creación de las sumas y las medias ha tardado en ejecutarse: " + (fin-inicio) + " milisegundos.");

            System.out.println("-- Procesos Números Aleatorios:");
            comprobarSalidaProcesos(procesosNumerosAleatorios);
            System.out.println();
            System.out.println("-- Procesos Suma Números:");
            comprobarSalidaProcesos(procesosSumaNumeros);
            System.out.println();
            System.out.println("-- Procesos Media Números:");
            comprobarSalidaProcesos(procesosMediaNumeros);

        } catch (IOException e) {
            System.err.println("Error durante ejecución del proceso");
            System.exit(1);
        }
    }

    private static void comprobarSalidaProcesos (ArrayList<Process> listaProcesos) {
        for(Process proceso : listaProcesos) {
            int retorno = proceso.exitValue();

            if (retorno == 0) {
                System.out.println("    El proceso ha finalizado correctamente");
            } else {
                System.out.println("    El proceso ha terminado con el siguiente código de error: " + retorno);
            }
        }
    }

    private static void esperarEjecucionProcesos (ArrayList<Process> procesos) {
        int procesosVivos = procesos.size();
        while (procesosVivos > 0) {
            procesosVivos = procesos.size();

            for (Process proceso : procesos) {
                if(!proceso.isAlive()) {
                    procesosVivos--;
                }
            }
        }
    }
}

