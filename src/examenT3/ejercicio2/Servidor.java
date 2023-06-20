package examenT3.ejercicio2;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Servidor {

    private static final String RUTA_FICHERO = "src\\examenT3\\ejercicio2\\Alumnos.txt";
    private static final File FICHERO = new File(RUTA_FICHERO);
    private static final int PORT = 50000;

    public static void main(String[] args) {
        try {
            System.out.println("Creación del socket");
            DatagramSocket socket = new DatagramSocket(PORT);

            while (true) {
                System.out.println("Esperando mensaje...");
                DatagramPacket datagramaEntrada = leerMensaje(socket);
                String mensajeRecibido = new String(datagramaEntrada.getData()).trim();
                System.out.println("Mensaje Recibido: " + mensajeRecibido);
                String respuesta;
                String[] msgRecibidoPartes = mensajeRecibido.split(" ");
                switch (msgRecibidoPartes[0].toUpperCase()) {
                    case "CREATE" -> respuesta = createAlumno(msgRecibidoPartes);
                    case "SELECT" -> respuesta = selectAlumno();
                    default -> respuesta = "ERROR, operación '" + mensajeRecibido + "' inválida (válidos 'SELECT' o 'CREATE')";
                }
                try {
                    enviarMensaje(socket, respuesta, datagramaEntrada.getAddress(), datagramaEntrada.getPort());
                } catch (IOException e) {
                    System.err.println("Error al enviar el mensaje al cliente");
                }
            }
        } catch (SocketException e) {
            System.err.println("Error en la creación del socket");
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.err.println("Error en la recuperación del paquete");
            throw new RuntimeException(e);
        }
    }

    private static DatagramPacket leerMensaje(DatagramSocket socket) throws IOException {
        byte[] bufferEntrada = new byte[64];

        DatagramPacket packetEntrada = new DatagramPacket(bufferEntrada, bufferEntrada.length);
        socket.receive(packetEntrada);

        return packetEntrada;
    }

    private static void enviarMensaje(DatagramSocket socket, String mensaje, InetAddress address, int port) throws IOException {
        byte[] bufferSalida = mensaje.getBytes();
        DatagramPacket packetSalida1 = new DatagramPacket(bufferSalida, bufferSalida.length, address, port);
        socket.send(packetSalida1);
    }

    private static String selectAlumno() {
        String respuesta;
        try {
            BufferedReader br = new BufferedReader(new FileReader(FICHERO));
            respuesta = "";
            String line = br.readLine();
            while (line != null) {
                respuesta += line + System.lineSeparator();
                line = br.readLine();
            }
        } catch (FileNotFoundException e) {
            respuesta = "ERROR al realizar la operación";
            System.err.println("Error, no se ha encontrado el fichero");
        } catch (IOException e) {
            respuesta = "ERROR al realizar la operación";
            System.err.println("Error, de entrada y salida al leer el fichero");
        }
        return respuesta;
    }

    private static String createAlumno(String[] msgRecibidoPartes) {
        String respuesta = "ERROR al realizar la operación";
        int codAlumno;
        String nombreAlumno;
        try {
            codAlumno = Integer.parseInt(msgRecibidoPartes[1]);
            nombreAlumno = msgRecibidoPartes[2];
            respuesta = escribirAlumno(codAlumno, nombreAlumno);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Error en el formato recibido");
        } catch (NumberFormatException e) {
            System.err.println("Error en el formato del código del alumno");
        } catch (IOException e) {
            System.err.println("Error al intentar escribir el alumno en el fichero");
        }
        return respuesta;
    }

    private static String escribirAlumno(int codigo, String nombre) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(FICHERO, true));
        bw.write(String.valueOf(codigo));
        bw.write(" ");
        bw.write(nombre);
        bw.newLine();
        bw.close();
        return "Alumno introducido correctamente";
    }
}