package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import utils.DBConnection;

public class DBConnectionTest {

	@Test
	public void testGetInstance() {
		DBConnection expected = DBConnection.getInstance();
		assertEquals(expected, DBConnection.getInstance());
	}

	@Test
	public void testGetInstanceFail() {
		DBConnection expected = null;
		assertEquals(expected, DBConnection.getInstance());
	}



}
