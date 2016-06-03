package gui.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

import controller.FacturaController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import model.Articol;
import model.Factura;
import model.InregistrareFactura;

public class EstimareTrimestrialaController implements Initializable{
	@FXML
	private AnchorPane root;
	BarChart<String, Number> bc;
	@FXML
	private ChoiceBox<String> trimestru;
	private static int TRIM_CURENT = Calendar.getInstance().get(Calendar.MONTH) / 4;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		initializareControlGrafic();
		trimestru.getSelectionModel().select(TRIM_CURENT);

	}

	private void initializareControlGrafic() {

		trimestru.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				TRIM_CURENT = trimestru.getSelectionModel().getSelectedIndex();
				trimestru.getSelectionModel().select(TRIM_CURENT);
				initializareGrafic(TRIM_CURENT + 1);
			}
		});

	}

	public void initializareGrafic(int trimestru) {

		root.getChildren().remove(bc);

		String luna1;
		String luna2;

		double[] valuesLuna1 = getSituatieByLuna((trimestru - 1) * 3 + 1);
		double[] valuesLuna2 = getSituatieByLuna((trimestru - 1) * 3 + 2);
		double[] valuesLuna3 = getSituatieByLuna((trimestru - 1) * 3 + 3);

		String luna3;
		CategoryAxis xAxis = new CategoryAxis();
		NumberAxis yAxis = new NumberAxis();
		bc = new BarChart<>(xAxis, yAxis);
		bc.setPrefWidth(700);
		bc.setPrefHeight(350);
		bc.setLayoutX(30);
		bc.setLayoutY(50);
		bc.setStyle("-fx-background-color:#FFF;-fx-margin:10px");
		xAxis.setLabel("Luna");
		yAxis.setLabel("Valoare");
		bc.setTitle("Sumar situatie trimestriala");

		if (trimestru == 1) {
			luna1 = "Ianuarie";
			luna2 = "Februarie";
			luna3 = "Martie";
		} else if (trimestru == 2) {
			luna1 = "Aprilie";
			luna2 = "Mai";
			luna3 = "Iunie";

		} else if (trimestru == 3) {
			luna1 = "Iulie";
			luna2 = "August";
			luna3 = "Septembrie";

		} else {
			luna1 = "Octombrie";
			luna2 = "Noiembrie";
			luna3 = "Decembrie";

		}

		XYChart.Series series1 = new XYChart.Series();
		series1.setName("Aprovizionari");
		series1.getData().add(new XYChart.Data(luna1, valuesLuna1[0]));
		series1.getData().add(new XYChart.Data(luna2, valuesLuna2[0]));
		series1.getData().add(new XYChart.Data(luna3, valuesLuna3[0]));

		XYChart.Series series2 = new XYChart.Series();
		series2.setName("Vanzari");
		series2.getData().add(new XYChart.Data(luna1, valuesLuna1[1]));
		series2.getData().add(new XYChart.Data(luna2, valuesLuna2[1]));
		series2.getData().add(new XYChart.Data(luna3, valuesLuna3[1]));

		XYChart.Series series3 = new XYChart.Series();
		series3.setName("Sold final luna");
		series3.getData().add(new XYChart.Data(luna1, valuesLuna1[2]));
		series3.getData().add(new XYChart.Data(luna2, valuesLuna2[2]));
		series3.getData().add(new XYChart.Data(luna3, valuesLuna3[2]));

		root.getChildren().add(bc);
		bc.getData().addAll(series1, series2, series3);
	}

	public double[] getSituatieByLuna(int luna) {

		double[] values = { 0, 0, 0 };

		List<InregistrareFactura> inregistrari = new ArrayList<>();
		List<Factura> facturi = new FacturaController().selectAll();
		List<Articol> articole = new ArrayList<>();

		double stoc1, stoc2;

		// preluare facturi din luna curenta
		for (Factura f : facturi) {
			if (f.getDataDocument().getMonth() == luna - 1) {
				inregistrari.addAll(f.getArticole());
			}
		}

		// preluare articole utilizate in luna curenta
		for (InregistrareFactura iff : inregistrari) {
			articole.add(iff.getArticol());
		}

		for (InregistrareFactura iff : inregistrari) {
			// daca e intrare
			if (iff.getTip() == 0) {
				values[0] += iff.getValoare();
			} else {
				values[1] += iff.getValoare();
			}

			values[2] = values[1] - values[0];
		}
		return values;
	}

}
