package gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

import controller.FacturaController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import model.Articol;
import model.Factura;
import model.Firma;
import model.InregistrareFactura;
import model.SituatieStocuriTable;
import utils.DBConnection;

public class MainFrameController implements Initializable {

	@FXML
	private AnchorPane root;

	// TableView 1
	@FXML
	private TableView<Factura> table1;
	@FXML
	private TableColumn<Factura, String> nrDoc1;
	@FXML
	private TableColumn<Factura, Firma> denumire1;
	@FXML
	private TableColumn<Factura, Timestamp> dataD1;
	@FXML
	private TableColumn<Factura, Timestamp> dataS1;
	@FXML
	private TableColumn<InregistrareFactura, String> totalTable1;

	// TableView 2
	@FXML
	private TableView<Factura> table2;
	@FXML
	private TableColumn<Factura, String> nrDoc2;
	@FXML
	private TableColumn<Factura, Firma> denumire2;
	@FXML
	private TableColumn<Factura, Timestamp> dataD2;
	@FXML
	private TableColumn<Factura, Timestamp> dataS2;
	@FXML
	private TableColumn<InregistrareFactura, String> totalTable2;

	@FXML
	private AnchorPane root2;
	@FXML
	private ChoiceBox<String> trimestru;

	private static int TRIM_CURENT = Calendar.getInstance().get(Calendar.MONTH) / 4;

	public void exitPressed() {
		Platform.exit();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initTable1();
		initTable2();
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				initializareControlGrafic();
				trimestru.getSelectionModel().select(TRIM_CURENT);
			}
		});


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

	private void initTable1() {
		table1.setPlaceholder(new Label("Nicio factura introdusa"));
		nrDoc1.setCellValueFactory(new PropertyValueFactory<>("nrdoc"));
		denumire1.setCellValueFactory(new PropertyValueFactory<>("denumireFirma"));
		dataD1.setCellValueFactory(new PropertyValueFactory<>("stringDateD"));
		dataS1.setCellValueFactory(new PropertyValueFactory<>("stringDateS"));
		totalTable1.setCellValueFactory(new PropertyValueFactory<>("totalFactura"));
		table1.setItems(getListIntrari());
	}

	private void initTable2() {
		table2.setPlaceholder(new Label("Nicio factura introdusa"));
		nrDoc2.setCellValueFactory(new PropertyValueFactory<>("nrdoc"));
		denumire2.setCellValueFactory(new PropertyValueFactory<>("denumireFirma"));
		dataD2.setCellValueFactory(new PropertyValueFactory<>("stringDateD"));
		dataS2.setCellValueFactory(new PropertyValueFactory<>("stringDateS"));
		totalTable2.setCellValueFactory(new PropertyValueFactory<>("totalFactura"));
		table2.setItems(getListIesiri());
	}

	private ObservableList<Factura> getListIntrari() {
		return FXCollections.observableArrayList(new FacturaController().getUltimele20Intrari());
	}

	private ObservableList<Factura> getListIesiri() {
		return FXCollections.observableArrayList(new FacturaController().getUltimele20Iesiri());

	}

	public void initializareGrafic(int trimestru) {
		String luna1;
		String luna2;

		double[] valuesLuna1 = getSituatieByLuna((trimestru - 1) * 3 + 1);
		double[] valuesLuna2 = getSituatieByLuna((trimestru - 1) * 3 + 2);
		double[] valuesLuna3 = getSituatieByLuna((trimestru - 1) * 3 + 3);

		String luna3;
		CategoryAxis xAxis = new CategoryAxis();
		NumberAxis yAxis = new NumberAxis();
		BarChart<String, Number> bc = new BarChart<>(xAxis, yAxis);
		bc.setPrefWidth(450);
		bc.setPrefHeight(340);
		bc.setLayoutX(40);
		bc.setLayoutY(10);
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

		root2.getChildren().add(bc);
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

	public void clientiPressed(ActionEvent event) {
		try {
			Stage primaryStage = (Stage) root.getScene().getWindow();
			Stage stage = new Stage(StageStyle.UTILITY);
			final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/ClientiFurnizori.fxml"));
			fxmlLoader.getNamespace().put("tip", "client");

			final Parent root = fxmlLoader.load();
			stage.setTitle("Clienti");
			stage.setScene(new Scene(root));
			stage.show();

			stage.setAlwaysOnTop(true);

			primaryStage.setOnHidden(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent onClosing) {
					stage.hide();
				}
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void furnizoriPressed(ActionEvent event) {
		try {
			Stage primaryStage = (Stage) root.getScene().getWindow();
			Stage stage = new Stage(StageStyle.UTILITY);
			final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/ClientiFurnizori.fxml"));
			fxmlLoader.getNamespace().put("tip", "furnizor");

			final Parent root = fxmlLoader.load();
			stage.setTitle("Furnizori");
			stage.setScene(new Scene(root));
			stage.show();

			stage.setAlwaysOnTop(true);

			primaryStage.setOnHidden(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent onClosing) {
					stage.hide();
				}
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void intrariPressed(ActionEvent event) {
		try {
			Stage primaryStage = (Stage) root.getScene().getWindow();
			Stage stage = new Stage(StageStyle.UTILITY);
			final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/IntrariIesiri.fxml"));
			fxmlLoader.getNamespace().put("tip", "intrare");

			final Parent root = fxmlLoader.load();
			stage.setTitle("Intrari");
			stage.setScene(new Scene(root));
			stage.show();

			stage.setAlwaysOnTop(true);

			primaryStage.setOnHidden(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent onClosing) {
					stage.hide();
				}
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void iesiriPressed(ActionEvent event) {
		try {
			Stage primaryStage = (Stage) root.getScene().getWindow();
			Stage stage = new Stage(StageStyle.UTILITY);
			final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/IntrariIesiri.fxml"));
			fxmlLoader.getNamespace().put("tip", "iesire");

			final Parent root = fxmlLoader.load();
			stage.setTitle("Iesiri");
			stage.setScene(new Scene(root));
			stage.show();

			stage.setAlwaysOnTop(true);

			primaryStage.setOnHidden(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent onClosing) {
					stage.hide();
				}
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void articolePressed(ActionEvent event) {
		try {
			Stage primaryStage = (Stage) root.getScene().getWindow();
			Stage stage = new Stage(StageStyle.UTILITY);
			final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/Articole.fxml"));
			final Parent root = fxmlLoader.load();
			stage.setTitle("Articole");
			stage.setScene(new Scene(root));
			stage.show();

			stage.setAlwaysOnTop(true);

			primaryStage.setOnHidden(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent onClosing) {
					stage.hide();
				}
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void conturiPressed(ActionEvent event) {
		try {
			Stage primaryStage = (Stage) root.getScene().getWindow();
			Stage stage = new Stage(StageStyle.UTILITY);
			final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/Conturi.fxml"));
			final Parent root = fxmlLoader.load();
			stage.setTitle("Conturi");
			stage.setScene(new Scene(root));
			stage.show();

			stage.setAlwaysOnTop(true);

			primaryStage.setOnHidden(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent onClosing) {
					stage.hide();
				}
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void registruPressed(ActionEvent event) {
		try {
			Stage primaryStage = (Stage) root.getScene().getWindow();
			Stage stage = new Stage(StageStyle.UTILITY);
			final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/Registru.fxml"));
			final Parent root = fxmlLoader.load();
			stage.setTitle("Registru");
			stage.setScene(new Scene(root));
			stage.show();

			stage.setAlwaysOnTop(true);

			primaryStage.setOnHidden(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent onClosing) {
					stage.hide();
				}
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void situatieStocuriPressed(ActionEvent event) {
		try {
			Stage primaryStage = (Stage) root.getScene().getWindow();
			Stage stage = new Stage(StageStyle.UTILITY);
			final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/SituatieStocuri.fxml"));
			final Parent root = fxmlLoader.load();
			stage.setTitle("Situatie stocuri");
			stage.setScene(new Scene(root));
			stage.show();

			stage.setAlwaysOnTop(true);

			primaryStage.setOnHidden(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent onClosing) {
					stage.hide();
				}
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
