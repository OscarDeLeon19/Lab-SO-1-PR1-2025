package ejemploSemaforoBinario;

import java.util.concurrent.Semaphore;

class Impresora{
    private Semaphore semaforo = new Semaphore(1);

    public void imprimir(String nombre){
        try{
            // El hilo adquiere el servicio
            semaforo.acquire();
            System.out.println(nombre + " esta imprimiendo");
            Thread.sleep(2000);
            System.out.println(nombre + " termino de imprimir");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // El semaforo libera el permiso
            semaforo.release();
        }
    }
}

public class SemaforoBinario {

    public static void main(String[] args) {
        Impresora imp = new Impresora();

        Thread usuario1 = new Thread(() -> imp.imprimir("usuario1"));
        Thread usuario2 = new Thread(() -> imp.imprimir("usuario2"));
        Thread usuario3 = new Thread(() -> imp.imprimir("usuario3"));

        usuario1.start();
        usuario2.start();
        usuario3.start();
    }
}
