package model;

import java.util.ArrayList;

public class PuntoVendita {

	
	private String nome;
	private String indirizzo;
	private ArrayList<Mortadella> mortadelle= new ArrayList<Mortadella>();
	
	
	public PuntoVendita(String nome, String indirizzo) {
		super();
		this.nome = nome;
		this.indirizzo = indirizzo;
	}

	public synchronized void addMortadella(Mortadella m){
		mortadelle.add(m);
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
	@Override
	public String toString() {
		return "PuntoVendita [nome=" + nome + ", indirizzo=" + indirizzo + "]";
	}
	

	
	
}
