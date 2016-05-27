package model;

public class SituatieStocuriTable {
	private String denumire;
	private String um;
	private double cantitateIntrata;
	private double cantitateIesita;
	private double valoareIntrata;
	private double valoareIesita;
	private double stocCurent;
	public SituatieStocuriTable() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getDenumire() {
		return denumire;
	}
	public void setDenumire(String denumire) {
		this.denumire = denumire;
	}
	public String getUm() {
		return um;
	}
	public void setUm(String um) {
		this.um = um;
	}
	public double getCantitateIntrata() {
		return cantitateIntrata;
	}
	public void setCantitateIntrata(double cantitateIntrata) {
		this.cantitateIntrata = cantitateIntrata;
	}
	public double getCantitateIesita() {
		return cantitateIesita;
	}
	public void setCantitateIesita(double cantitateIesita) {
		this.cantitateIesita = cantitateIesita;
	}
	public double getValoareIntrata() {
		return valoareIntrata;
	}
	public void setValoareIntrata(double valoareIntrata) {
		this.valoareIntrata = valoareIntrata;
	}
	public double getValoareIesita() {
		return valoareIesita;
	}
	public void setValoareIesita(double valoareIesita) {
		this.valoareIesita = valoareIesita;
	}
	public double getStocCurent() {
		return stocCurent;
	}
	public void setStocCurent(double stocCurent) {
		this.stocCurent = stocCurent;
	}
	public SituatieStocuriTable(String denumire, String um, double cantitateIntrata, double cantitateIesita,
			double valoareIntrata, double valoareIesita, double stocCurent) {
		super();
		this.denumire = denumire;
		this.um = um;
		this.cantitateIntrata = cantitateIntrata;
		this.cantitateIesita = cantitateIesita;
		this.valoareIntrata = valoareIntrata;
		this.valoareIesita = valoareIesita;
		this.stocCurent = stocCurent;
	}
	public SituatieStocuriTable(String denumire) {
		super();
		this.denumire = denumire;
	}


}
