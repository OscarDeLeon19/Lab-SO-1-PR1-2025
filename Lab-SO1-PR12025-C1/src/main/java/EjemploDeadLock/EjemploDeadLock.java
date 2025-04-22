package EjemploDeadLock;

import java.util.concurrent.locks.ReentrantLock;

class Recurso {

    private ReentrantLock lock = new ReentrantLock();
    private String nombre;

    public Recurso(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public ReentrantLock getLock() {
        return lock;
    }

}

class DeadLockThread extends Thread {
    private Recurso recurso1;
    private Recurso recurso2;

    public DeadLockThread(Recurso recurso1, Recurso recurso2) {
        this.recurso1 = recurso1;
        this.recurso2 = recurso2;
    }

    public void run() {
        recurso1.getLock().lock();
        System.out.println("El " + Thread.currentThread().getName() + " esta usando " + recurso1.getNombre());

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        recurso2.getLock().lock();
        System.out.println("El " + Thread.currentThread().getName() + " esta usando " + recurso2.getNombre());

        recurso1.getLock().unlock();
        recurso2.getLock().unlock();
    }
}

class SecureThread extends Thread {
    private Recurso recurso1;
    private Recurso recurso2;

    public SecureThread(Recurso recurso1, Recurso recurso2) {
        this.recurso1 = recurso1;
        this.recurso2 = recurso2;
    }

    public void run() {

        while (true) {
            boolean bloqueo1 = recurso1.getLock().tryLock();
            boolean bloqueo2 = recurso2.getLock().tryLock();

            if (bloqueo1 && bloqueo2) {
                try {
                    System.out.println("El " + Thread.currentThread().getName() + " esta usando " + recurso1.getNombre() + " y " + recurso2.getNombre());
                    break;
                } finally {
                    recurso1.getLock().unlock();
                    recurso2.getLock().unlock();
                }
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }
        }


    }
}

public class EjemploDeadLock {

    public static void main(String[] args) {
        Recurso recurso1 = new Recurso("Recurso 1");
        Recurso recurso2 = new Recurso("Recurso 2");

        // Hilos con deadLock
        DeadLockThread thread1 = new DeadLockThread(recurso1, recurso2);
          DeadLockThread thread2 = new DeadLockThread(recurso2, recurso1);

        // Hilos con deadlock
//        SecureThread thread1 = new SecureThread(recurso1, recurso2);
//        SecureThread thread2 = new SecureThread(recurso2, recurso1);

        thread1.start();
        thread2.start();
    }
}
