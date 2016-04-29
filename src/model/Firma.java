package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Firma extends BaseModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(unique = true)
	private String denumire;
	@Column(unique = true)
	private String cui;
	private String j;
	private String detalii;
	private int tip;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDenumire() {
		return denumire;
	}

	public void setDenumire(String denumire) {
		this.denumire = denumire;
	}

	public String getCui() {
		return cui;
	}

	public void setCui(String cui) {
		this.cui = cui;
	}

	public String getJ() {
		return j;
	}

	public void setJ(String j) {
		this.j = j;
	}

	public String getDetalii() {
		return detalii;
	}

	public void setDetalii(String detalii) {
		this.detalii = detalii;
	}

	public int getTip() {
		return tip;
	}

	public void setTip(int tip) {
		this.tip = tip;
	}

	public Firma(int id, String denumire, String cui, String j, String detalii, int tip) {
		super();
		this.id = id;
		this.denumire = denumire;
		this.cui = cui;
		this.j = j;
		this.detalii = detalii;
		this.tip = tip;
	}

	public Firma() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return denumire;
	}

}
