package EjemploSincronizacion;


class Contador {
    private int count = 0;

    void incrementar() {
        count++;
    }

    int getCount() {
        return count;
    }
}

/**
 * Clase que no utiliza sincronización
 */
class WithoutSynchronization {

    public WithoutSynchronization() {
    }

    public void execute() throws InterruptedException {
        Contador contador = new Contador();

        Thread hilo1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                contador.incrementar();
            }
        });

        Thread hilo2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                contador.incrementar();
            }
        });

        hilo1.start();
        hilo2.start();
        hilo1.join();
        hilo2.join();

        System.out.println("Valor final sin sinchronyzed: " + contador.getCount());
    }
}

class WithSynchronization {
    public WithSynchronization() {
    }

    public void execute() throws InterruptedException {
        Contador contador = new Contador();

        Thread hilo1 = new Thread(() -> {
            synchronized (contador) {
                for (int i = 0; i < 1000; i++) {
                    contador.incrementar();
                }
            }

        });

        Thread hilo2 = new Thread(() -> {
            synchronized (contador) {
                for (int i = 0; i < 1000; i++) {
                    contador.incrementar();
                }
            }

        });

        hilo1.start();
        hilo2.start();
        hilo1.join();
        hilo2.join();

        System.out.println("Valor final con synchronyzed: " + contador.getCount());
    }
}

public class ExampleSynchronyzed {

    public static void main(String[] args) throws InterruptedException {

        WithoutSynchronization test1 = new WithoutSynchronization();
        WithSynchronization test2 = new WithSynchronization();
        test1.execute();
        test2.execute();
    }
}

