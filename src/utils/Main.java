package utils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import controller.ArticolController;
import controller.ChitantaController;
import controller.ContController;
import controller.FacturaController;
import controller.FirmaController;
import model.Articol;
import model.Chitanta;
import model.Cont;
import model.Factura;
import model.Firma;

public class Main {

	public static void main(String[] args) {

		DBConnection.getInstance().getConnection();
		DBConnection.getInstance().openConnection();

		List<Articol> articole = new ArrayList<>();
		List<Factura>intrari = new ArrayList<>();


		Firma furnizor = new Firma(2, "SC ABC Prest SRL", "RO2132131", "J40/1232/2010", "Fara detalii", 1);
		Chitanta chitanta = new Chitanta(1, "1", new Timestamp(3213213), 130, 1);
		Factura f = new Factura(2, "1", new Timestamp(32112312), new Timestamp(3211231),1,"1",furnizor,chitanta,articole);

		Cont cont = new Cont(1,"401","Marfuri");

		//Articol a = new Articol(1, 0, "Paine", 23, 11, 24, "Marfuri", "buc",cont, intrari);
		Articol a2 = new Articol(1, 0, "Apa minerala", 23, 11, 24,  "buc",cont, intrari);

		//articole.add(a);
		articole.add(a2);

		intrari.add(f);

		new FirmaController().saveObject(furnizor);
		new ArticolController().saveObject(a2);
		new ContController().saveObject(cont);
		new ChitantaController().saveObject(chitanta);
		new FacturaController().saveObject(f);

		DBConnection.getInstance().closeConnection();

	}

}
