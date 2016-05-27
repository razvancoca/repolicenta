package controller;

import java.util.List;

import javax.persistence.EntityManager;

import model.Factura;
import model.InregistrareFactura;

public class FacturaController extends BaseController<Factura>{
	public FacturaController(){
		super(Factura.class);
	}

}
