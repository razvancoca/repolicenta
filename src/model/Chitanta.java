package model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class Chitanta extends BaseModel{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String nrdoc;
	private Timestamp data;
	private double suma;
	private int tip;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNrdoc() {
		return nrdoc;
	}
	public void setNrdoc(String nrdoc) {
		this.nrdoc = nrdoc;
	}
	public Timestamp getData() {
		return data;
	}
	public void setData(Timestamp data) {
		this.data = data;
	}
	public double getSuma() {
		return suma;
	}
	public void setSuma(double suma) {
		this.suma = suma;
	}
	public int getTip() {
		return tip;
	}
	public void setTip(int tip) {
		this.tip = tip;
	}
	public Chitanta(int id, String nrdoc, Timestamp data, double suma, int tip) {
		super();
		this.id = id;
		this.nrdoc = nrdoc;
		this.data = data;
		this.suma = suma;
		this.tip = tip;
	}
	public Chitanta() {
		super();
		// TODO Auto-generated constructor stub
	}



}
