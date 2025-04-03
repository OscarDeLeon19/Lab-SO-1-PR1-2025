package EjemploColaDeMensajes;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

class Cocinero implements Runnable {
    private BlockingQueue<String> cola;

    public Cocinero(BlockingQueue<String> cola) {
        this.cola = cola;
    }

    public void run() {
        try {
            for (int i = 0; i < 5; i++) {
                String hamburguesa = "Hamburguesa No." + (i + 1);
                System.out.println("Preparando " + hamburguesa);
                cola.put(hamburguesa);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Mesero implements Runnable {
    private BlockingQueue<String> cola;

    public Mesero(BlockingQueue<String> cola) {
        this.cola = cola;
    }

    public void run() {
        try {
            while (true) {
                String pedido = cola.take();
                System.out.println("Mesero toma " + pedido);
                Thread.sleep(2000);
                if (cola.isEmpty()) {
                    break;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class Restaurante {
    public static void main(String[] args) {
        BlockingQueue<String> cola = new LinkedBlockingQueue<String>(3);

        Thread cocinero = new Thread(new Cocinero(cola));
        Thread mesero = new Thread(new Mesero(cola));

        cocinero.start();
        mesero.start();
    }
}
