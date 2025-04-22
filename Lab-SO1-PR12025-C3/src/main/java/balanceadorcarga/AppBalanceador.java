package balanceadorcarga;

import java.util.ArrayList;
import java.util.List;

class Servidor {
    private String nombre;
    private boolean disponible;

    public Servidor(String nombre) {
        this.nombre = nombre;
        this.disponible = true;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean estaDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public void procesarSolicitud(String solicitud) {
        if (disponible) {
            System.out.println("Solicitud \"" + solicitud + "\" atendida por: " + nombre);
        } else {
            System.out.println("¡El servidor " + nombre + " está caído!");
        }
    }
}

class BalanceadorCarga {
    private List<Servidor> servidores;
    private int indiceActual;

    public BalanceadorCarga() {
        servidores = new ArrayList<>();
        indiceActual = 0;
    }

    public void agregarServidor(Servidor servidor) {
        servidores.add(servidor);
    }

    public void distribuirSolicitud(String solicitud) {
        if (servidores.isEmpty()) {
            System.out.println("¡No hay servidores para atender solicitudes!");
            return;
        }

        int intentos = 0;

        while (intentos < servidores.size()) {
            Servidor servidor = servidores.get(indiceActual);
            indiceActual = (indiceActual + 1) % servidores.size();

            if (servidor.estaDisponible()) {
                servidor.procesarSolicitud(solicitud);
                return;
            }

            intentos++;
        }

        System.out.println("¡Todos los servidores están caídos! No se pudo atender: " + solicitud);
    }
}

public class AppBalanceador {
    public static void main(String[] args) {
        System.out.println("Iniciando balanceador de carga...");

        BalanceadorCarga balanceador = new BalanceadorCarga();

        // Agregar servidores
        Servidor srv1 = new Servidor("Servidor-1");
        Servidor srv2 = new Servidor("Servidor-2");
        Servidor srv3 = new Servidor("Servidor-3");

        balanceador.agregarServidor(srv1);
        balanceador.agregarServidor(srv2);
        balanceador.agregarServidor(srv3);

        // Distribuir solicitudes
        System.out.println("Distribuyendo solicitudes iniciales...");
        for (int i = 1; i <= 6; i++) {
            balanceador.distribuirSolicitud("Solicitud " + i);
        }

        // Simular caída de un servidor
        System.out.println("\nServidor-2 deja de estar disponible.");
        srv2.setDisponible(false);

        for (int i = 7; i <= 10; i++) {
            balanceador.distribuirSolicitud("Solicitud " + i);
        }

        // Restaurar servidor
        System.out.println("\nServidor-2 vuelve a estar disponible.");
        srv2.setDisponible(true);

        for (int i = 11; i <= 14; i++) {
            balanceador.distribuirSolicitud("Solicitud " + i);
        }
    }

}
