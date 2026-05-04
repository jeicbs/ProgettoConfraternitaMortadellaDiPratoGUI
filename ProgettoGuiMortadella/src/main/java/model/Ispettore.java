package model;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Ispettore extends Thread {

	private Random r= new Random();
	private GestioneMagazzini g;
	private static AtomicInteger counterAccettate= new AtomicInteger(0);
	private static AtomicInteger counterRifiutate= new AtomicInteger(0);
	private UICallback uiCallback;
	
	public Ispettore(GestioneMagazzini g, String nome, UICallback uiCallBack) {
		this.g = g;
		this.uiCallback = uiCallBack;
		setName(nome);
	}



	@Override
	public void run() {
		try{
			while (!g.isEndOfProductionIsp()) {
				Mortadella m = g.ispezionaMortadella();

				uiCallback.logMag(Thread.currentThread().getName()+" sta ispezionando: "+m.toString());
				if (m.getPeso()>=5&&m.getPeso()<=15) {
					m.setStato(StatoMortadella.APPROVATA);
					uiCallback.logMag(Thread.currentThread().getName()+" ha approvato: "+m.toString());
					uiCallback.onApprovata(counterAccettate.incrementAndGet());
					g.aggiungiMortadellaIspezionata(m);
				} else{
					m.setStato(StatoMortadella.SCARTATA);
					uiCallback.onScartata(counterRifiutate.incrementAndGet());
					uiCallback.logMag(Thread.currentThread().getName()+" ha rifiutato: "+m.toString());
				}
				Thread.sleep(r.nextInt(1500, 3000));
			}
			System.out.println(Thread.currentThread().getName()+" terminato");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

    public AtomicInteger getCounterRifiutate() {
        return counterRifiutate;
    }

    public void setCounterRifiutate(AtomicInteger counterRifiutate) {
        this.counterRifiutate = counterRifiutate;
    }

    public AtomicInteger getCounterAccettate() {
        return counterAccettate;
    }

    public void setCounterAccettate(AtomicInteger counterAccettate) {
        this.counterAccettate = counterAccettate;
    }
}
