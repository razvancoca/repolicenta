package utils;

import org.apache.log4j.BasicConfigurator;

public class Main {

	public static void main(String[] args) {

		DBConnection.getInstance().getConnection();
		DBConnection.getInstance().openConnection();
		DBConnection.getInstance().closeConnection();

	}

}
