package model;

public class RegistruTableDate {
	String data;
	double sold;
	int zi;

	public RegistruTableDate() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RegistruTableDate(String data, double sold, int zi) {
		super();
		this.data = data;
		this.sold = sold;
		this.zi = zi;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public double getSold() {
		return sold;
	}

	public void setSold(double sold) {
		this.sold = sold;
	}

	public int getZi() {
		return zi;
	}

	public void setZi(int zi) {
		this.zi = zi;
	}

	@Override
	public String toString() {
		return "RegistruTableDate [data=" + data + ", sold=" + sold + ", zi=" + zi + "]";
	}

}
