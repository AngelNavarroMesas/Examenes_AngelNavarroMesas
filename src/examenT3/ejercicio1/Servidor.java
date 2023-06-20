package examenT3.ejercicio1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

    private static final int PORT = 4000;

    public static void main(String[] args) {
        try {
            System.out.println("(Servidor): Abriendo conexión");
            ServerSocket socketServidor = new ServerSocket(PORT);
            while (true) {
                System.out.println("(Servidor): Esperando peticiones");
                Socket socketCliente = socketServidor.accept();

                new GestorProcesosTCP(socketCliente).start();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
