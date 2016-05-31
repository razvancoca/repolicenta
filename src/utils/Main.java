package utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import controller.FacturaController;
import controller.InregistrareFacturaController;
import model.Factura;
import model.InregistrareFactura;

public class Main {

	public static void main(String[] args) {
		DBConnection.getInstance().getConnection();
		DBConnection.getInstance().openConnection();
		Factura f = new FacturaController().getById(1);
		InregistrareFactura iff = f.getArticole().get(0);

		System.out.println(iff);

		System.out.println(f.getDataDocument().getMonth());
		DBConnection.getInstance().closeConnection();

//
//		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
//		Calendar cal = Calendar.getInstance();
//		cal.set(Calendar.DAY_OF_MONTH, 1);
//		int myMonth=cal.get(Calendar.MONTH);
//
//		System.out.println(myMonth);
//		while (myMonth==cal.get(Calendar.MONTH)) {
//		  System.out.println(sdf.format(cal.getTime()));
//		  cal.add(Calendar.DAY_OF_MONTH, 1);
//		}

	}

}
