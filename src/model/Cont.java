package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class Cont extends BaseModel{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column(unique = true)
	private String cont;
	private String denumireCont;
	double sumaDebit;
	double sumaCredit;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCont() {
		return cont;
	}
	public void setCont(String cont) {
		this.cont = cont;
	}
	public String getDenumireCont() {
		return denumireCont;
	}
	public void setDenumireCont(String denumireCont) {
		this.denumireCont = denumireCont;
	}
	public double getSumaDebitoare() {
		return sumaDebit;
	}
	public void setSumaDebitoare(double sumaDebitoare) {
		this.sumaDebit = sumaDebitoare;
	}
	public double getSumaCreditoare() {
		return sumaCredit;
	}
	public void setSumaCreditoare(double sumaCreditoare) {
		this.sumaCredit = sumaCreditoare;
	}
	public Cont() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Cont(int id, String cont, String denumireCont, double sumaDebitoare, double sumaCreditoare) {
		super();
		this.id = id;
		this.cont = cont;
		this.denumireCont = denumireCont;
		this.sumaDebit = sumaDebitoare;
		this.sumaCredit = sumaCreditoare;
	}
	@Override
	public String toString() {
		return cont+ " "+denumireCont;
	}

	public void creditare(double suma){
		sumaCredit+=suma;
	}

	public void debitare(double suma){
		sumaDebit+=suma;
	}
}
