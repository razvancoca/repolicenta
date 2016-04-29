package model;

import java.sql.Timestamp;
import java.text.DecimalFormat;
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
public class Factura extends BaseModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String nrdoc;
	private Timestamp dataDocument;
	private Timestamp dataScadenta;
	private int tip;
	private String idInCategorie;

	@ManyToOne(cascade = CascadeType.ALL, optional = false)
	@JoinColumn(name = "id_firma")
	private Firma firma;

	@ManyToOne(cascade = CascadeType.ALL, optional = true)
	@JoinColumn(name = "id_chitanta")
	private Chitanta chitanta;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	List<Articol> articole;


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

	public Timestamp getDataDocument() {
		return dataDocument;
	}

	public void setDataDocument(Timestamp dataDocument) {
		this.dataDocument = dataDocument;
	}

	public Timestamp getDataScadenta() {
		return dataScadenta;
	}

	public void setDataScadenta(Timestamp dataScadenta) {
		this.dataScadenta = dataScadenta;
	}

	public int getTip() {
		return tip;
	}

	public void setTip(int tip) {
		this.tip = tip;
	}

	public String getIdInCategorie() {
		return idInCategorie;
	}

	public void setIdInCategorie(String idInCategorie) {
		this.idInCategorie = idInCategorie;
	}

	public Firma getFirma() {
		return firma;
	}

	public void setFirma(Firma firma) {
		this.firma = firma;
	}

	public String getDetalii() {
		if (firma != null)
			return firma.getDetalii();
		return "---";
	}

	public String getDenumireFirma() {
		if (firma != null)
			return firma.getDenumire();
		return "---";
	}

	public Chitanta getChitanta() {
		return chitanta;
	}

	public void setChitanta(Chitanta chitanta) {
		this.chitanta = chitanta;
	}

	public List<Articol> getArticole() {
		return articole;
	}

	public void setArticole(List<Articol> articole) {
		this.articole = articole;
	}

	public Factura(int id, String nrdoc, Timestamp dataDocument, Timestamp dataScadenta, int tip, String idInCategorie,
			Firma firma, Chitanta chitanta, List<Articol> articole) {
		super();
		this.id = id;
		this.nrdoc = nrdoc;
		this.dataDocument = dataDocument;
		this.dataScadenta = dataScadenta;
		this.tip = tip;
		this.idInCategorie = idInCategorie;
		this.firma = firma;
		this.chitanta = chitanta;
		this.articole = articole;
	}

	public Factura() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Factura [id=" + id + ", nrdoc=" + nrdoc + ", dataDocument=" + dataDocument + ", dataScadenta="
				+ dataScadenta + ", tip=" + tip + ", firma=" + firma + ", chitanta=" + chitanta + ", articole="
				+ articole + "]";
	}


	public String getStringDateD(){
		if(dataDocument==null){
			 java.util.Date date= new java.util.Date();
			 dataDocument = new Timestamp(date.getTime());
		}
		return new java.text.SimpleDateFormat("dd.MM.yyyy").format(dataDocument);
	}

	public String getStringDateS(){
		if(dataScadenta==null){
			 java.util.Date date= new java.util.Date();
			 dataScadenta = new Timestamp(date.getTime());
		}
		return new java.text.SimpleDateFormat("dd.MM.yyyy").format(dataScadenta);
	}

	public String getTotalFactura(){
		double total=0;
		DecimalFormat df = new DecimalFormat("0.00");
		if(articole!=null){
			for(Articol a:articole){
				total+=a.getTotal();
			}
		}
		return df.format(total);
	}
}
