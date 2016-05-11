package controller;

import java.util.List;

import javax.persistence.EntityManager;

import model.Factura;
import model.InregistrareFactura;

public class InregistrareFacturaController extends BaseController<InregistrareFactura>{
	public InregistrareFacturaController(){
		super(InregistrareFactura.class);
	}

	public List<InregistrareFactura> getInregistrariByFactura(Factura factura) {
		EntityManager em = emf.createEntityManager();
		List<InregistrareFactura> inregistratiFactura = null;
		try{
			inregistratiFactura = (List<InregistrareFactura>) em.createQuery("SELECT a FROM InregistrareFactura a WHERE id_factura=:id_factura").setParameter("id_factura", factura.getId()).getResultList();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		em.close();
		return inregistratiFactura;
	}

}
