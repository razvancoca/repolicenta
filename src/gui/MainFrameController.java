package gui;

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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class MainFrameController implements Initializable {

	@FXML
	private AnchorPane root;

	public void exitPressed() {
		Platform.exit();
	}

	public void clientiPressed(ActionEvent event) {
		try {
			Stage primaryStage = (Stage) root.getScene().getWindow();
			Stage stage = new Stage(StageStyle.UTILITY);
			final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ClientiFurnizori.fxml"));
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
			final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ClientiFurnizori.fxml"));
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
			final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("IntrariIesiri.fxml"));
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
			final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("IntrariIesiri.fxml"));
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
			final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Articole.fxml"));
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

}
