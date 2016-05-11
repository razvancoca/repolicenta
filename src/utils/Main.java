package utils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import controller.InregistrareFacturaController;
import controller.ChitantaController;
import controller.ContController;
import controller.FacturaController;
import controller.FirmaController;
import model.InregistrareFactura;
import model.Chitanta;
import model.Cont;
import model.Factura;
import model.Firma;

public class Main {

	public static void main(String[] args) {

		DBConnection.getInstance().getConnection();
		DBConnection.getInstance().openConnection();

//		List<Articol> Articole = New ArrayList<>();
//		List<Factura>intrari = New ArrayList<>();
//
//
//		Firma Furnizor = New Firma(2, "SC ABC Prest SRL", "RO2132131", "J40/1232/2010", "Fara Detalii", 1);
//		Chitanta Chitanta = New Chitanta(1, "1", New Timestamp(3213213), 130, 1);
//		Factura F = New Factura(2, "1", New Timestamp(32112312), New Timestamp(3211231),1,"1",furnizor,chitanta,articole);
//
//		Cont Cont = New Cont(1,"401","Marfuri");
//
//		//Articol A = New Articol(1, 0, "Paine", 23, 11, 24, "Marfuri", "buc",cont, Intrari);
//		Articol A2 = New Articol(1, 0, "Apa Minerala", 23, 11, 24,  "buc",cont, Intrari);
//
//		//articole.add(a);
//		Articole.add(a2);
//
//		Intrari.add(f);
//
//		New FirmaController().saveObject(furnizor);
//		New ArticolController().saveObject(a2);
//		New ContController().saveObject(cont);
//		New ChitantaController().saveObject(chitanta);
//		New FacturaController().saveObject(f);

		Factura f = new FacturaController().getById(1);
		System.out.println(new InregistrareFacturaController().getInregistrariByFactura(f));

		DBConnection.getInstance().closeConnection();
	}

}
