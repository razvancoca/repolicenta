package model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
@Entity
public class Cont extends BaseModel{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String cont;
	private String denumireCont;

	public Cont(int id, String cont, String denumireCont) {
		super();
		this.id = id;
		this.cont = cont;
		this.denumireCont = denumireCont;
	}
	public Cont() {
		super();
		// TODO Auto-generated constructor stub
	}
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
	@Override
	public String toString() {
		return cont;
	}


}
