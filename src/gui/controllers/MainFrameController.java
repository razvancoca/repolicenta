package gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class MainFrameController implements Initializable {

	@FXML
	private AnchorPane root;

	@FXML
	private AnchorPane root2;

	public void exitPressed() {
		Platform.exit();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		situatieByTrimestru(2);

	}

	public void situatieByTrimestru(int trimestru) {

		String luna1 = "Aprilie";
		String luna2 = "Mai";
		String luna3 = "Iunie";
		CategoryAxis xAxis = new CategoryAxis();
		NumberAxis yAxis = new NumberAxis();
		BarChart<String, Number> bc = new BarChart<>(xAxis, yAxis);
		bc.setPrefWidth(400);
		bc.setPrefHeight(300);
		bc.setLayoutX(20);
		bc.setLayoutY(60);
		bc.setStyle("-fx-background-color:#FFF;-fx-margin:10px");
		xAxis.setLabel("Luna");
		yAxis.setLabel("Valoare");

		if (trimestru == 1) {
			bc.setTitle("Situatie trimestru I");
		} else if (trimestru == 2) {
			bc.setTitle("Situatie trimestru II");

		} else if (trimestru == 2) {
			bc.setTitle("Situatie trimestru III");

		} else {
			bc.setTitle("Situatie trimestru IV");

		}

		XYChart.Series series1 = new XYChart.Series();
		series1.setName("Aprovizionari");
		series1.getData().add(new XYChart.Data(luna1, 25601.34));
		series1.getData().add(new XYChart.Data(luna2, 20148.82));
		series1.getData().add(new XYChart.Data(luna3, 10000));

		XYChart.Series series2 = new XYChart.Series();
		series2.setName("Vanzari");
		series2.getData().add(new XYChart.Data(luna1, 57401.85));
		series2.getData().add(new XYChart.Data(luna2, 41941.19));
		series2.getData().add(new XYChart.Data(luna3, 45263.37));

		XYChart.Series series3 = new XYChart.Series();
		series3.setName("Sold final luna");
		series3.getData().add(new XYChart.Data(luna1, 25601.34));
		series3.getData().add(new XYChart.Data(luna2, 20148.82));
		series3.getData().add(new XYChart.Data(luna3, 10000));

		root2.getChildren().add(bc);
		bc.getData().addAll(series1, series2, series3);
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
