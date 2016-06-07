package gui.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

import controller.FacturaController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Articol;
import model.Factura;
import model.InregistrareFactura;

public class EstimareTrimestrialaController implements Initializable {
	@FXML
	private AnchorPane root;
	BarChart<String, Number> bc;
	@FXML
	private ChoiceBox<String> trimestru;
	String luna1;
	String luna2;
	String luna3;
	@FXML
	private Label labelLuna1;
	@FXML
	private Label labelLuna2;
	@FXML
	private Label labelLuna3;
	@FXML
	private TextField aprovizionariLuna1;
	@FXML
	private TextField vanzariLuna1;
	@FXML
	private TextField aprovizionariLuna2;
	@FXML
	private TextField vanzariLuna2;
	@FXML
	private TextField aprovizionariLuna3;
	@FXML
	private TextField vanzariLuna3;

	@FXML
	private Label labelAprovizionariAdaugate1;
	@FXML
	private Label labelVanzariAdaugate1;
	@FXML
	private Label labelAprovizionariAdaugate2;
	@FXML
	private Label labelVanzariAdaugate2;
	@FXML
	private Label labelAprovizionariAdaugate3;
	@FXML
	private Label labelVanzariAdaugate3;

	@FXML
	private AnchorPane anchorBackgroundPreviziune;

	private static int TRIM_CURENT = Calendar.getInstance().get(Calendar.MONTH) / 4;

	private double valoareAprovizionariLuna1;
	private double valoareAprovizionariLuna2;
	private double valoareAprovizionariLuna3;
	private double valoareVanzariLuna1;
	private double valoareVanzariLuna2;
	private double valoareVanzariLuna3;

	Alert alertInfo = new Alert(AlertType.INFORMATION);
	Alert alertError = new Alert(AlertType.ERROR);

	private void initializareValori() {
		valoareAprovizionariLuna1 = 0;
		valoareAprovizionariLuna2 = 0;
		valoareAprovizionariLuna3 = 0;
		valoareVanzariLuna1 = 0;
		valoareVanzariLuna2 = 0;
		valoareVanzariLuna3 = 0;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		initializareControlGrafic();
		trimestru.getSelectionModel().select(TRIM_CURENT);
		labelLuna1.setText(luna1);
		labelLuna2.setText(luna2);
		labelLuna3.setText(luna3);
		initializareValori();
	}

	private void initializareControlGrafic() {

		trimestru.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				TRIM_CURENT = trimestru.getSelectionModel().getSelectedIndex();
				trimestru.getSelectionModel().select(TRIM_CURENT);
				initializareGrafic(TRIM_CURENT + 1);
				labelLuna1.setText(luna1);
				labelLuna2.setText(luna2);
				labelLuna3.setText(luna3);
				initializareValori();
			}
		});

	}

	public void initializareGrafic(int trimestru) {

		root.getChildren().remove(bc);

		double[] valuesLuna1 = getSituatieByLuna((trimestru - 1) * 3 + 1);
		double[] valuesLuna2 = getSituatieByLuna((trimestru - 1) * 3 + 2);
		double[] valuesLuna3 = getSituatieByLuna((trimestru - 1) * 3 + 3);

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
		series1.getData().add(new XYChart.Data(luna1, valuesLuna1[0] + valoareAprovizionariLuna1));
		series1.getData().add(new XYChart.Data(luna2, valuesLuna2[0] + valoareAprovizionariLuna2));
		series1.getData().add(new XYChart.Data(luna3, valuesLuna3[0] + valoareAprovizionariLuna3));

		XYChart.Series series2 = new XYChart.Series();
		series2.setName("Vanzari");
		series2.getData().add(new XYChart.Data(luna1, valuesLuna1[1] + valoareVanzariLuna1));
		series2.getData().add(new XYChart.Data(luna2, valuesLuna2[1] + valoareVanzariLuna2));
		series2.getData().add(new XYChart.Data(luna3, valuesLuna3[1] + valoareVanzariLuna3));

		XYChart.Series series3 = new XYChart.Series();
		series3.setName("Sold final luna");

		double sold1 = valuesLuna1[2] + valoareVanzariLuna1 - valoareAprovizionariLuna1;
		double sold2 = valuesLuna2[2] + valoareVanzariLuna2 - valoareAprovizionariLuna2;
		double sold3 = valuesLuna3[2] + valoareVanzariLuna3 - valoareAprovizionariLuna3;

		series3.getData().add(new XYChart.Data(luna1, sold1));
		series3.getData().add(new XYChart.Data(luna2, sold2));
		series3.getData().add(new XYChart.Data(luna3, sold3));

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

	public void adaugaLuna1Pressed(ActionEvent event) {
		try {
			if (!vanzariLuna1.getText().equals("")) {
				valoareVanzariLuna1 += Double.parseDouble(vanzariLuna1.getText());
				labelVanzariAdaugate1.setText("Vanzari adaugate: " + valoareVanzariLuna1);
				vanzariLuna1.setText("");
			}

			if (!aprovizionariLuna1.getText().equals("")) {
				valoareAprovizionariLuna1 += Double.parseDouble(aprovizionariLuna1.getText());
				labelAprovizionariAdaugate1.setText("Aprovizionari adaugate: " + valoareAprovizionariLuna1);
				aprovizionariLuna1.setText("");
			}
		} catch (Exception nfe) {
			nfe.printStackTrace();
			final Stage stage = (Stage) root.getScene().getWindow();
			if (alertError.getOwner() != stage)
				alertError.initOwner(stage);
			alertError.setHeaderText(null);
			alertError.setContentText("Suma nu este valida. Reincercati!");
			alertError.show();
		}
	}

	public void adaugaLuna2Pressed(ActionEvent event) {
		try {
			if (!vanzariLuna2.getText().equals("")) {
				valoareVanzariLuna2 += Double.parseDouble(vanzariLuna2.getText());
				labelVanzariAdaugate2.setText("Vanzari adaugate: " + valoareVanzariLuna2);
				vanzariLuna2.setText("");
			}

			if (!aprovizionariLuna2.getText().equals("")) {
				valoareAprovizionariLuna2 += Double.parseDouble(aprovizionariLuna2.getText());
				labelAprovizionariAdaugate2.setText("Aprovizionari adaugate: " + valoareAprovizionariLuna2);
				aprovizionariLuna2.setText("");
			}
		} catch (Exception nfe) {
			nfe.printStackTrace();
			final Stage stage = (Stage) root.getScene().getWindow();
			if (alertError.getOwner() != stage)
				alertError.initOwner(stage);
			alertError.setHeaderText(null);
			alertError.setContentText("Suma nu este valida. Reincercati!");
			alertError.show();
		}
	}

	public void adaugaLuna3Pressed(ActionEvent event) {
		try {
			if (!vanzariLuna3.getText().equals("")) {
				valoareVanzariLuna3 += Double.parseDouble(vanzariLuna3.getText());
				labelVanzariAdaugate3.setText("Vanzari adaugate: " + valoareVanzariLuna3);
				vanzariLuna3.setText("");
			}

			if (!aprovizionariLuna3.getText().equals("")) {
				valoareAprovizionariLuna3 += Double.parseDouble(aprovizionariLuna3.getText());
				labelAprovizionariAdaugate3.setText("Aprovizionari adaugate: " + valoareAprovizionariLuna3);
				aprovizionariLuna3.setText("");
			}
		} catch (Exception nfe) {
			final Stage stage = (Stage) root.getScene().getWindow();
			if (alertError.getOwner() != stage)
				alertError.initOwner(stage);
			alertError.setHeaderText(null);
			alertError.setContentText("Suma nu este valida. Reincercati!");
			alertError.show();
		}
	}

	public void estimeazaPressed(ActionEvent event) {
		anchorBackgroundPreviziune.setVisible(true);
		initializareGrafic(TRIM_CURENT + 1);
	}

	public void reseteazaPressed(ActionEvent event) {
		initializareValori();
		anchorBackgroundPreviziune.setVisible(false);
		TRIM_CURENT = trimestru.getSelectionModel().getSelectedIndex();
		trimestru.getSelectionModel().select(TRIM_CURENT);
		initializareGrafic(TRIM_CURENT + 1);
		labelLuna1.setText(luna1);
		labelLuna2.setText(luna2);
		labelLuna3.setText(luna3);
		labelVanzariAdaugate1.setText("Vanzari adaugate: " + valoareAprovizionariLuna1);
		labelVanzariAdaugate2.setText("Vanzari adaugate: " + valoareAprovizionariLuna2);
		labelVanzariAdaugate3.setText("Vanzari adaugate: " + valoareAprovizionariLuna3);
		labelAprovizionariAdaugate1.setText("Aprovizionari adaugate: " + valoareVanzariLuna1);
		labelAprovizionariAdaugate2.setText("Aprovizionari adaugate: " + valoareVanzariLuna2);
		labelAprovizionariAdaugate3.setText("Aprovizionari adaugate: " + valoareVanzariLuna3);

	}

	public void printPressed(ActionEvent event) {
		PrinterJob job = PrinterJob.createPrinterJob();

		if (job != null && job.showPrintDialog(root.getScene().getWindow())) {
			boolean success = job.printPage(root);
			if (success) {
				job.endJob();
			}
		}
	}

}
