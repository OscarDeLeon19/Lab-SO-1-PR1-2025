package EjemploReentrantLock;

import java.util.concurrent.locks.ReentrantLock;

class CuentaBancaria {
    private double saldo;
    private ReentrantLock lock = new ReentrantLock();

    public CuentaBancaria(double saldo) {
        this.saldo = saldo;
    }

    public void retirar(double valor) {
        lock.lock();
        System.out.println("Retirando cuenta bancaria " + Thread.currentThread().getName());
        try {
            if (saldo >= valor) {
                System.out.println("Se retiro " + valor + " de la cuenta bancaria");
                saldo -= valor;
                System.out.println("Saldo atual: " + saldo);
            } else {
                System.out.println("Saldo insuficiente");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            lock.unlock();
        }
    }
}

public class EjemploReentrantLock {

    public static void main(String[] args) {
        CuentaBancaria cb = new CuentaBancaria(200);

        Thread t1 = new Thread(() -> {
            cb.retirar(50);
        }, "Thread 1");
        Thread t2 = new Thread(() -> {
            cb.retirar(75);
        }, "Thread 2");
        Thread t3 = new Thread(() -> {
            cb.retirar(100);
        }, "Thread 3");

        t1.start();
        t2.start();
        t3.start();
    }
}
