package EjemploSemaforoContador;

import java.util.Random;
import java.util.concurrent.Semaphore;

class Estacionamiento {
    private Semaphore semaforo;

    public Estacionamiento(int espacios) {
        this.semaforo = new Semaphore(espacios);
    }

    public void estacionar(String auto) {
        try {
            semaforo.acquire();
            System.out.println(auto + " ha entrado al estacionamiento.");
            // Tiempo aleatorio del automovil en el estacionamiento
            Random random = new Random();
            int numero = random.nextInt(5) + 1;
            numero = numero *1000;
            Thread.sleep(numero);

            System.out.println(auto + " ha salido del estacionamiento.");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaforo.release();
        }
    }
}

public class SemaforoContador {
    public static void main(String[] args) {
        Estacionamiento estacionamiento = new Estacionamiento(2);

        Thread auto1 = new Thread(() -> estacionamiento.estacionar("Auto 1"));
        Thread auto2 = new Thread(() -> estacionamiento.estacionar("Auto 2"));
        Thread auto3 = new Thread(() -> estacionamiento.estacionar("Auto 3"));
        Thread auto4 = new Thread(() -> estacionamiento.estacionar("Auto 4"));

        auto1.start();
        auto2.start();
        auto3.start();
        auto4.start();
    }
}
