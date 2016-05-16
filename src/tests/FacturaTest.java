package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import controller.FacturaController;
import model.Factura;

public class FacturaTest {
	List<Factura> facturi;
	Factura factura;

	private Factura getPrimaFactura() {
		Factura f = new FacturaController().getById(1);
		return f;
	}

	private List<Factura> getFacturi() {
		List<Factura> facturi = new FacturaController().selectAll();

		return facturi;
	}

	@Test
	public void testFacturi() {
		factura = getPrimaFactura();
		facturi = getFacturi();

		System.out.println(factura.getDenumireFirma());
		System.out.println(facturi.get(0).getDenumireFirma());
		assertEquals(factura, facturi.get(0));
	}

	@Test
	public void testDenumiriFacturi() {
		factura = getPrimaFactura();
		facturi = getFacturi();

		System.out.println(factura.getDenumireFirma());
		System.out.println(facturi.get(0).getDenumireFirma());
		assertEquals(factura.getDenumireFirma(), facturi.get(0).getDenumireFirma());
	}

	@Test
	public void testIsEmpty() {
		facturi = new ArrayList<>();
		assertTrue(facturi.isEmpty());
	}

	@Test
	public void testEgalitateFacturi(){
		factura = new Factura();
		assertFalse(factura.equals(new Factura()));
	}
	
	@Test
	public void testDataDocumentVsScadenta(){
		factura = getPrimaFactura();
		assertEquals(factura.getDataDocument(), factura.getDataScadenta());
	}
}
