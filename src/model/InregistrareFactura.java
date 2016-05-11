package model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class InregistrareFactura extends BaseModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int tip;
	@ManyToOne(cascade = CascadeType.ALL, optional = false)
	@JoinColumn(name = "id_denumire_articol")
	private Articol articol;
	private double cantitate;
	private double pretUnitate;
	private double cotaTVA;
	private String um;

	@ManyToOne(cascade = CascadeType.ALL, optional = false)
	@JoinColumn(name = "id_cont")
	private Cont cont;

	@ManyToOne(cascade = CascadeType.ALL, optional = true)
	@JoinColumn(name = "id_factura")
	private Factura factura;

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

	public Articol getArticol() {
		return articol;
	}

	public void setArticol(Articol articol) {
		this.articol = articol;
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

	public String getUm() {
		return um;
	}

	public void setUm(String um) {
		this.um = um;
	}

	public Cont getCont() {
		return cont;
	}

	public void setCont(Cont cont) {
		this.cont = cont;
	}

	public Factura getFactura() {
		return factura;
	}

	public void setFactura(Factura factura) {
		this.factura = factura;
	}

	public InregistrareFactura(int id, int tip, Articol articol, double cantitate, double pretUnitate, double cotaTVA,
			String um, Cont cont, Factura factura) {
		super();
		this.id = id;
		this.tip = tip;
		this.articol = articol;
		this.cantitate = cantitate;
		this.pretUnitate = pretUnitate;
		this.cotaTVA = cotaTVA;
		this.um = um;
		this.cont = cont;
		this.factura = factura;
	}

	public InregistrareFactura() {
		super();
		// TODO Auto-generated constructor stub
	}


	public double getValoare(){
		return cantitate*pretUnitate;
	}

	public double getValoareTVA(){
		return getValoare()*cotaTVA/100;
	}
	public double getTotal(){
		return getValoare()+getValoareTVA();
	}
}
