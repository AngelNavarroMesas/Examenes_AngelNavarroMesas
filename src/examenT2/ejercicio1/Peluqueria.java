package examenT2.ejercicio1;

import java.util.concurrent.Semaphore;

public class Peluqueria extends Thread {

    private static Semaphore sillas = new Semaphore(4);

    private static Semaphore barberos = new Semaphore(2);

    @Override
    public void run() {
        colaSillas();
    }

    public void colaBarberos(){
        try {
            barberos.acquire();
            System.out.println("El " + Thread.currentThread().getName() + " está siendo afeitado");
            Thread.sleep((int) (Math.random()*1000 +10000));
            System.out.println("El " + Thread.currentThread().getName() + " ha terminado su afeitado" );
            barberos.release();
            sillas.release();
        }catch (InterruptedException er){
            System.out.println("Se ha interrumpido");
        }
    }

    public void colaSillas(){
        try {
            if (sillas.availablePermits()>0){
                sillas.acquire();
                System.out.println("El " +Thread.currentThread().getName()+ " se ha sentado");
                colaBarberos();

            }else {
                System.out.println("El " +Thread.currentThread().getName()+ " no tiene asiento");
                Thread.interrupted();
                System.out.println("El " +Thread.currentThread().getName()+ " se ha marchado");
            }
        }catch (InterruptedException er){
            System.out.println("Error de Interrupcion");
        }


    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Peluqueria peluqueria = new Peluqueria();
            peluqueria.setName("Cliente "+ i);
            peluqueria.start();

            try {
                Thread.sleep((int) (Math.random()*1 +1000));
            }catch (InterruptedException er) {
                System.out.println("Se ha interrumpido el hilo");
            }
        }
    }
}