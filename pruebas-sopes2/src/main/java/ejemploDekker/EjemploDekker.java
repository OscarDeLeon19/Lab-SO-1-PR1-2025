package ejemploDekker;

class AlgoritmoDekker{
    private volatile boolean[] flag = {false, false};
    private volatile int turn = 0;

    public AlgoritmoDekker(){}

    public void proceso(int id){
        int other = 1 -id;

        // Se indica que el algoritmo quiere entrar en la sección
        flag[id] = true;

        // Espera mientras el otro algoritmo y cede el paso
        while(flag[other]){
            if(turn == other){
                flag[id] = false;

                while(turn == other){}

                flag[id] = true;
            }
        }

        // Inicio de la seccion critica
        System.out.println("Proceso " + id + " accede a la seccion critica");
        try{
            Thread.sleep(1000);
        } catch(InterruptedException e){
            e.printStackTrace();
        }

        // Fin de la sección critica
        turn = other;
        flag[id] = false;
    }
}

public class EjemploDekker {

    public static void main(String[] args) {
        AlgoritmoDekker dekker = new AlgoritmoDekker();

        Thread p1 = new Thread(() -> dekker.proceso(0));
        Thread p2 = new Thread(() -> dekker.proceso(1));

        p1.start();
        p2.start();
    }
}
