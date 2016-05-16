package utils;

import model.BaseModel;
import model.Cont;
import model.Factura;
import model.User;

public class ModelFactory {
	public BaseModel getModel(String tipModel) {
		if (tipModel.equals("factura"))
			return new Factura();
		else if (tipModel.equals("cont"))
			return new Cont();
		else if (tipModel.equals("user"))
			return new User();
		return null;
	}
}
