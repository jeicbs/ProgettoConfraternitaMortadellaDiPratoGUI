package model;

import model.Laboratorio;
import model.StatoMortadella;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

public class Mortadella {


	private static AtomicInteger counter= new AtomicInteger(0);
	private int codice;
	private Laboratorio laboratorio;
	private double peso;
	private LocalDate dataProduzione;
	private StatoMortadella stato;
	
	
	
	public Mortadella(Laboratorio laboratorio, double peso, LocalDate dataProduzione, StatoMortadella stato) {
		super();
		codice = counter.incrementAndGet();


		this.laboratorio = laboratorio;
		this.peso = peso;
		this.dataProduzione = dataProduzione;
		this.stato = stato;

	}



	public Mortadella() {

	}

	public int getCodice() {
		return codice;
	}

	public Laboratorio getLaboratorio() {
		return laboratorio;
	}
	public void setLaboratorio(Laboratorio laboratorio) {
		this.laboratorio = laboratorio;
	}
	public double getPeso() {
		return peso;
	}
	public void setPeso(double peso) {
		this.peso = peso;
	}
	public LocalDate getDataProduzione() {
		return dataProduzione;
	}
	public void setDataProduzione(LocalDate dataProduzione) {
		this.dataProduzione = dataProduzione;
	}
	public StatoMortadella getStato() {
		return stato;
	}
	public void setStato(StatoMortadella stato) {
		this.stato = stato;
	}
	@Override
	public String toString() {
		return "Mortadella [codice=" + codice + ", laboratorio=" + laboratorio + ", peso=" + peso + ", dataProduzione="
				+ dataProduzione + ", stato=" + stato + "]";
	}
	
	
}
