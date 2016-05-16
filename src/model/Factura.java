package model;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
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

import controller.InregistrareFacturaController;

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

	@ManyToOne(cascade = CascadeType.ALL, optional = false)
	@JoinColumn(name = "id_user")
	private User user;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	List<Chitanta> chitante;


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



	public Factura() {
		super();
		// TODO Auto-generated constructor stub
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
		List<InregistrareFactura> list = new InregistrareFacturaController().selectAll();
		double sum=0;
		for(InregistrareFactura iff:list){
			if(iff.getFactura().getId()==id){
				sum+=iff.getTotal();
			}
		}
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(sum);
	}


	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Factura(int id, String nrdoc, Timestamp dataDocument, Timestamp dataScadenta, int tip, String idInCategorie,
			Firma firma, User user, List<Chitanta> chitante) {
		super();
		this.id = id;
		this.nrdoc = nrdoc;
		this.dataDocument = dataDocument;
		this.dataScadenta = dataScadenta;
		this.tip = tip;
		this.idInCategorie = idInCategorie;
		this.firma = firma;
		this.user = user;
		this.chitante = chitante;
	}

	public List<Chitanta> getChitante() {
		return chitante;
	}

	public void setChitante(List<Chitanta> chitante) {
		this.chitante = chitante;
	}


	public List<InregistrareFactura> getArticole(){
		List<InregistrareFactura> temp = new InregistrareFacturaController().selectAll();
		List<InregistrareFactura> list = new ArrayList<InregistrareFactura>();

		for(InregistrareFactura i: list){
			if(i.getFactura().equals(this)){
				list.add(i);
			}
		}
		return list;

	}

	@Override
	public String toString() {
		return "Factura [id=" + id + ", nrdoc=" + nrdoc + ", dataDocument=" + dataDocument + ", dataScadenta="
				+ dataScadenta + ", tip=" + tip + ", idInCategorie=" + idInCategorie + ", firma=" + firma + ", user="
				+ user + ", chitante=" + chitante + "]";
	}


}
