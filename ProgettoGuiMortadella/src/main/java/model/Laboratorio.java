package model;

import java.time.LocalDate;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class Laboratorio extends Thread{

	private static AtomicInteger counter= new AtomicInteger(0);
	private static AtomicInteger counter2= new AtomicInteger(0);

	private String nome;
	private String indirizzo;
	private GestioneMagazzini g;
	private Random r= new Random();
	private UICallback logger;

	private String numeroTelefono;

	public Laboratorio(String nome, String indirizzo, String numeroTelefono, GestioneMagazzini g, UICallback logger) {
		super();
		this.nome = nome;
		this.indirizzo = indirizzo;
		this.numeroTelefono = numeroTelefono;
		this.g =g;
		this.logger = logger;
	}
	

	public void run(){
		try{
//			for(int i=0;i<50;i++){
//				Mortadella m = new Mortadella(this, r.nextDouble(3.0,19.0), LocalDate.now(),StatoMortadella.IN_PRODUZIONE);
//				System.out.println("Producendo: "+ m.toString());
//				g.aggiungiMortadella(m);
//			}
			int current;
			while((current = counter.incrementAndGet()) <= 50) {
				logger.onProdotta(current);
				Mortadella m = new Mortadella(this, r.nextDouble(3.0, 19.0), LocalDate.now(), StatoMortadella.IN_PRODUZIONE);
				logger.log("Producendo: " + m);
				g.aggiungiMortadella(m);
				Thread.sleep(r.nextInt(400, 800));
			}
			if (counter2.incrementAndGet()==1) {
				System.out.println("-----------Fine Produzione-----------");
				g.notifyEndOfProduction();
			}


		} catch(InterruptedException e){
			System.out.println(e.getMessage());
		}

	}

	

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getNumeroTelefono() {
		return numeroTelefono;
	}

	public void setNumeroTelefono(String numeroTelefono) {
		this.numeroTelefono = numeroTelefono;
	}

	@Override
	public String toString() {
		return "Laboratorio [nome=" + nome + ", indirizzo=" + indirizzo + ", numeroTelefono=" + numeroTelefono + "]";
	}

	
}
