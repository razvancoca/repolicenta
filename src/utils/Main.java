package utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Main {

	public static void main(String[] args) {
//		DBConnection.getInstance().getConnection();
//		DBConnection.getInstance().openConnection();
//		DBConnection.getInstance().closeConnection();


		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		int myMonth=cal.get(Calendar.MONTH);

		System.out.println(myMonth);
		while (myMonth==cal.get(Calendar.MONTH)) {
		  System.out.println(sdf.format(cal.getTime()));
		  cal.add(Calendar.DAY_OF_MONTH, 1);
		}

	}

}
