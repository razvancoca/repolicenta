package model;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class OperatiuneRegistru extends BaseModel{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	Timestamp data;
	String nrDocument;
	@ManyToOne(cascade = CascadeType.ALL, optional = false)
	@JoinColumn(name = "id_cont_debit")
	Cont contDebit;
	@ManyToOne(cascade = CascadeType.ALL, optional = false)
	@JoinColumn(name = "id_cont_credit")
	Cont contCredit;
	double suma;
	String explicatie;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Timestamp getData() {
		return data;
	}
	public void setData(Timestamp data) {
		this.data = data;
	}
	public String getNrDocument() {
		return nrDocument;
	}
	public void setNrDocument(String nrDocument) {
		this.nrDocument = nrDocument;
	}
	public Cont getContDebit() {
		return contDebit;
	}
	public void setContDebit(Cont contDebit) {
		this.contDebit = contDebit;
	}
	public Cont getContCredit() {
		return contCredit;
	}
	public void setContCredit(Cont contCredit) {
		this.contCredit = contCredit;
	}
	public double getSuma() {
		return suma;
	}
	public void setSuma(double suma) {
		this.suma = suma;
	}
	public OperatiuneRegistru() {
	}
	public String getExplicatie() {
		return explicatie;
	}
	public void setExplicatie(String explicatie) {
		this.explicatie = explicatie;
	}
	public OperatiuneRegistru(int id, Timestamp data, String nrDocument, Cont contDebit, Cont contCredit, double suma,
			String explicatie) {
		super();
		this.id = id;
		this.data = data;
		this.nrDocument = nrDocument;
		this.contDebit = contDebit;
		this.contCredit = contCredit;
		this.suma = suma;
		this.explicatie = explicatie;
	}

}
