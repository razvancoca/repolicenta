package controller;

import java.util.List;

import javax.persistence.EntityManager;

import model.Factura;
import model.InregistrareFactura;

public class FacturaController extends BaseController<Factura> {
	public FacturaController() {
		super(Factura.class);
	}

	public List<Factura> getUltimele20Intrari() {
		EntityManager em = emf.createEntityManager();
		List<Factura> inregistrariFactura = null;
		try {
			inregistrariFactura = (List<Factura>) em.createQuery("SELECT a FROM Factura a where tip=0 order by id DESC")
					.getResultList();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		em.close();
		if (inregistrariFactura.size() > 19)
			return inregistrariFactura.subList(0, 19);
		return inregistrariFactura;
	}

	public List<Factura> getUltimele20Iesiri() {
		EntityManager em = emf.createEntityManager();
		List<Factura> inregistrariFactura = null;
		try {
			inregistrariFactura = (List<Factura>) em
					.createQuery("SELECT a FROM Factura a where tip=1 order by id DESC ").getResultList();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		em.close();
		if (inregistrariFactura.size() > 19)
			return inregistrariFactura.subList(0, 19);
		return inregistrariFactura;
	}
}
