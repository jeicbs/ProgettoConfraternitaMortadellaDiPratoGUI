package model;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Distributore  extends Thread{

    private GestioneMagazzini g;
    private Gestione gestione;
    private Random random= new Random();
    private UICallback uiCallback;
    private static AtomicInteger counterRifiutate=new AtomicInteger(0);


    public Distributore(GestioneMagazzini g, String nome, Gestione gestione, UICallback uiCallback) {
        this.g = g;
        this.gestione = gestione;
        this.uiCallback = uiCallback;
        setName(nome);
    }

    @Override
    public void run() {
        try{
            while (!g.isEndOfProductionDis()) {
                Mortadella m = g.distribuisciMortadella();


                PuntoVendita p = null;
                int r = random.nextInt(0, gestione.getPuntiVendita().length);
                p= gestione.getPuntiVendita()[r];
                m.setStato(StatoMortadella.DISTRIBUITA);
                uiCallback.logMag(Thread.currentThread().getName()+" sta distribuendo: "+ m.toString() +" a: " +p.toString() );
                p.addMortadella(m);
                uiCallback.onDistribuita(counterRifiutate.incrementAndGet(), p);
                Thread.sleep(random.nextInt(2000,4000));
            }
            System.out.println(Thread.currentThread().getName()+" terminato");
        } catch (InterruptedException e){
            e.printStackTrace();
        }

    }


}
