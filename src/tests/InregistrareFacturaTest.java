package tests;

import static org.junit.Assert.*;

import java.text.DecimalFormat;
import java.util.List;

import org.junit.Test;

import controller.InregistrareFacturaController;
import model.InregistrareFactura;

public class InregistrareFacturaTest {

	@Test
	public void testValoareInregistrare() {
		InregistrareFactura inregistrare = getInregistrareFactura();

		double cantitate = inregistrare.getCantitate();
		double pretUnitate = inregistrare.getPretUnitate();

		double valoareAsteptata = cantitate * pretUnitate;

		assertEquals(valoareAsteptata, inregistrare.getValoare());
	}

	@Test
	public void testValoareInregistrareZecimale() {
		InregistrareFactura inregistrare = getInregistrareFactura();

		DecimalFormat df = new DecimalFormat("0.00");

		double cantitate = inregistrare.getCantitate();
		double pretUnitate = inregistrare.getPretUnitate();

		double valoareAsteptata = cantitate * pretUnitate;

		String expected = df.format(valoareAsteptata);
		String actual = df.format(inregistrare.getValoare());

		assertEquals(expected, actual);
	}

	@Test
	public void testNulitateUm(){
		InregistrareFactura inregistrare = new InregistrareFactura();
		inregistrare.setUm("");

		assertTrue(inregistrare.getUm().isEmpty());
	}

	public InregistrareFactura getInregistrareFactura(){
		List<InregistrareFactura> iff=new InregistrareFacturaController().selectAll();
		return iff.get(0);
	}

}
