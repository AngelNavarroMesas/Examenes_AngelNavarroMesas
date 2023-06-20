package examenT3.ejercicio1;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class GestorProcesosTCP extends Thread {
    Socket socketCliente;

    public GestorProcesosTCP(Socket socketCliente) {
        this.socketCliente = socketCliente;
    }

    @Override
    public void run() {
        Proceso();
    }

    private void Proceso() {
        try {
            System.out.println("Conectado al cliente con ip: " + socketCliente.getInetAddress());
            System.out.println("(Servidor): Abriendo flujos de entrada y de salida");
            InputStream inputStream = socketCliente.getInputStream();
            OutputStream outputStream = socketCliente.getOutputStream();

            System.out.println("(Servidor): Leo la operación");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String cadenaLeidaDelCliente = bufferedReader.readLine();
            System.out.println("(Servidor): Operación enviada por el cliente: '" + cadenaLeidaDelCliente + "'");

            System.out.println("(Servidor): Envío resultado al cliente '" + socketCliente.getInetAddress() + "'");
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            String respuesta = String.valueOf(Operacion(cadenaLeidaDelCliente));
            bufferedWriter.write(respuesta);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            inputStream.close();
            outputStream.close();
            inputStreamReader.close();
            bufferedReader.close();
            outputStreamWriter.close();
            bufferedWriter.close();
            System.out.println("(Servidor): Desconectando al cliente con ip: " + socketCliente.getInetAddress());
        } catch (IOException e) {
            System.err.println("Error de entrada/salida en la comunicación con el cliente");
        }
    }

    private int Operacion(String cadena) {
        int respuesta = 0;
        int num1, num2;
        String[] operacion = cadena.split(";");
        try {
            num1 = Integer.parseInt(operacion[0]);
            num2 = Integer.parseInt(operacion[2]);
            switch (operacion[1]) {
                case "+" -> respuesta = num1 + num2;
                case "-" -> respuesta = num1 - num2;
                case "*" -> respuesta = num1 * num2;
                case "/" -> respuesta = num1 / num2;
                default -> throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            System.err.println("La operación '" + cadena + "' enviada por el cliente '" + socketCliente.getInetAddress() + "' no es válida");
        } catch (ArithmeticException e) {
            System.err.println("La operación '" + cadena + "' enviada por el cliente '" + socketCliente.getInetAddress() + "' es errónea");
        }
        return respuesta;
    }
}
