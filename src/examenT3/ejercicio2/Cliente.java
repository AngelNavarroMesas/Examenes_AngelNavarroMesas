package examenT3.ejercicio2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Cliente {
    private static final int PORT = 50000;

    public static void main(String[] args) {
        try {
            InetAddress direccion = InetAddress.getByName("127.0.0.1");
            String cadenaLeida;
            String respuesta;

            System.out.println("Creación del socket");
            DatagramSocket socket = new DatagramSocket();

            System.out.println("Ingrese una cadena ('CREATE/SELECT  códigoAlumno  nombreAlumno')");
            cadenaLeida = leerString();

            System.out.println("Inicio de envío de datos");
            enviarMensaje(socket, cadenaLeida, direccion, PORT);
            System.out.println("Fin de envío de datos");

            System.out.println("Esperando respuesta...");
            respuesta = leerMensaje(socket);
            System.out.println("Respuesta del servidor:");
            System.out.println(respuesta);

            System.out.println("Cierre de conexiones");
            socket.close();
        } catch (IOException e) {
            System.err.println("Error al enviar el paquete");
            throw new RuntimeException(e);
        }
    }

    private static String leerString() {
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    private static void enviarMensaje(DatagramSocket socket, String mensaje, InetAddress address, int port) throws IOException {
        byte[] bufferSalida = mensaje.getBytes();
        DatagramPacket packetSalida1 = new DatagramPacket(bufferSalida, bufferSalida.length, address, port);
        socket.send(packetSalida1);
    }

    private static String leerMensaje(DatagramSocket socket) throws IOException {
        String mensaje = "";
        byte[] bufferEntrada = new byte[64];

        DatagramPacket packetEntrada = new DatagramPacket(bufferEntrada, bufferEntrada.length);
        socket.receive(packetEntrada);

        mensaje = new String(packetEntrada.getData());
        return mensaje.trim();
    }
}
