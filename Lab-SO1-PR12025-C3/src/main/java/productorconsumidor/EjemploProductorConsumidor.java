package productorconsumidor;

public class EjemploProductorConsumidor {

    public static void main(String[] args) throws InterruptedException {
        ClaseCompartida compartida = new ClaseCompartida();

        Productor productor1 = new Productor(compartida);
        Consumidor consumidor1 = new Consumidor(compartida);

        productor1.start();
        consumidor1.start();

        productor1.join();
        consumidor1.join();

        System.out.println("Finalizo la ejecución");
    }
}

class ClaseCompartida{
    private int valor = 0;
    private final int LIMITE = 10;

    public synchronized void accionDeProducir() throws InterruptedException {
        while (valor >= LIMITE) {
            System.out.println("El productor espera porque el Buffer está lleno");
            wait();
        }

        valor++;
        System.out.println("El productor añadio un elemento. Valor actual: " + valor);
        notifyAll();
    }

    public synchronized void accionDeConsumir() throws InterruptedException {
        while (valor <= 0) {
            System.out.println("El consumidor espera porque el Buffer está vacio");
            wait();
        }

        valor--;
        System.out.println("El consumidor consumio un elemento. Valor actual: " + valor);
        notifyAll();
    }
}

class Productor extends Thread {
    private final ClaseCompartida compartida;

    public Productor(ClaseCompartida compartida) {
        this.compartida = compartida;
    }

    @Override
    public void run() {
        try{
            while(true){
                compartida.accionDeProducir();
                Thread.sleep((long)(Math.random() * 5000 + 1000));
            }
        } catch(InterruptedException e){
            System.out.println("Productor interrumpido");
        }
    }
}

class Consumidor extends Thread {
    private final ClaseCompartida compartida;
    public Consumidor(ClaseCompartida compartida) {
        this.compartida = compartida;
    }

    @Override
    public void run() {
        try{
            while(true){
                compartida.accionDeConsumir();
                Thread.sleep((long)(Math.random() * 5000 + 1000));
            }
        } catch(InterruptedException e){
            System.out.println("Consumidor interrumpido");
        }
    }
}
