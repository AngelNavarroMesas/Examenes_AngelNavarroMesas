package examenT3.ejercicio1;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Cliente {

    private static final int PORT = 4000;

    public static void main(String[] args) {
        try {
            System.out.println("(Cliente): Creación del socket");
            InetAddress direccion = InetAddress.getByName("127.0.0.1");
            Socket socketCliente = new Socket(direccion, PORT);

            System.out.println("(Cliente) Ingrese la operación ('num1;operación;num2')");
            String operacionLeidaPorConsola = leerString();

            System.out.println("(Cliente): Apertura de flujos de entrada y salida");
            InputStream inputStream = socketCliente.getInputStream();
            OutputStream outputStream = socketCliente.getOutputStream();

            System.out.println("(Cliente) Envía la operación al servidor");
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

            bufferedWriter.write(operacionLeidaPorConsola);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            System.out.println("(Cliente): Lee la respuesta del servidor");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            System.out.println("Mensaje enviado por el servidor: ");
            String respuestaServidor = bufferedReader.readLine();
            System.out.println(respuestaServidor);

            System.out.println("(Cliente): Cerramos flujo de lectura y escritura");
            outputStream.close();
            inputStream.close();
            outputStreamWriter.close();
            bufferedWriter.close();
            inputStreamReader.close();
            bufferedReader.close();

            System.out.println("Se cierra la conexión con el servidor");
            socketCliente.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private static String leerString() {
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }
}
