package model;

import java.util.concurrent.ArrayBlockingQueue;

public class GestioneMagazzini {

	private UICallback uiCallback;
	private boolean endOfProduction= false;

	private ArrayBlockingQueue<Mortadella> magazzino1 = new ArrayBlockingQueue<>(10);
	private ArrayBlockingQueue<Mortadella> magazzino2 = new ArrayBlockingQueue<>(15);

    public GestioneMagazzini(UICallback uiCallback) {
		this.uiCallback = uiCallback;
	}


    public void aggiungiMortadella(Mortadella m) throws InterruptedException {

		magazzino1.put(m);
		uiCallback.onPut1();


	}

	public Mortadella ispezionaMortadella() throws InterruptedException {
		Mortadella m= magazzino1.take();
		uiCallback.onTaken1();
		return m;
	}

	public void aggiungiMortadellaIspezionata(Mortadella m) throws InterruptedException {
		magazzino2.put(m);
		uiCallback.onPut2();

	}

	public Mortadella distribuisciMortadella() throws InterruptedException {
		Mortadella m = magazzino2.take();
		uiCallback.onTaken2();
		return m;
	}

	public void notifyEndOfProduction(){
		endOfProduction=true;
	}
	public synchronized boolean isEndOfProductionIsp(){
		return endOfProduction &&magazzino1.isEmpty() ;
	}


	public synchronized boolean isEndOfProductionDis() {
		return endOfProduction &&  magazzino2.isEmpty() && magazzino1.isEmpty() ;
	}
}




