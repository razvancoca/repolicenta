package model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
@Entity
public class Articol extends BaseModel{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private int tip;
	private String denumire;
	private double cantitate;
	private double pretUnitate;
	private double cotaTVA;
	private String tipArticol;
	private String um;

	@ManyToMany(mappedBy="articole", targetEntity=Factura.class,cascade=CascadeType.ALL)
	private List<Factura> facturi;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTip() {
		return tip;
	}

	public void setTip(int tip) {
		this.tip = tip;
	}

	public String getDenumire() {
		return denumire;
	}

	public void setDenumire(String denumire) {
		this.denumire = denumire;
	}

	public double getCantitate() {
		return cantitate;
	}

	public void setCantitate(double cantitate) {
		this.cantitate = cantitate;
	}

	public double getPretUnitate() {
		return pretUnitate;
	}

	public void setPretUnitate(double pretUnitate) {
		this.pretUnitate = pretUnitate;
	}

	public double getCotaTVA() {
		return cotaTVA;
	}

	public void setCotaTVA(double cotaTVA) {
		this.cotaTVA = cotaTVA;
	}

	public String getTipArticol() {
		return tipArticol;
	}

	public void setTipArticol(String tipArticol) {
		this.tipArticol = tipArticol;
	}

	public String getUm() {
		return um;
	}

	public void setUm(String um) {
		this.um = um;
	}

	public List<Factura> getFacturi() {
		return facturi;
	}

	public void setFacturi(List<Factura> facturi) {
		this.facturi = facturi;
	}

	public Articol(int id, int tip, String denumire, double cantitate, double pretUnitate, double cotaTVA,
			String tipArticol, String um, List<Factura> facturi) {
		super();
		this.id = id;
		this.tip = tip;
		this.denumire = denumire;
		this.cantitate = cantitate;
		this.pretUnitate = pretUnitate;
		this.cotaTVA = cotaTVA;
		this.tipArticol = tipArticol;
		this.um = um;
		this.facturi = facturi;
	}

	public Articol() {
		super();
		// TODO Auto-generated constructor stub
	}


}
