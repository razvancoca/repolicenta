package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainFrameController implements Initializable {

	public void exitPressed() {
		Platform.exit();
	}

	public void clientiPressed(ActionEvent event) {
		try {
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ClientiFurnizori.fxml"));
			fxmlLoader.getNamespace().put("tip", "client");

			final Parent root = fxmlLoader.load();
			stage.setTitle("Clienti");
			stage.setScene(new Scene(root));
			stage.show();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	public void furnizoriPressed(ActionEvent event) {
		try {
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ClientiFurnizori.fxml"));
			fxmlLoader.getNamespace().put("tip", "furnizor");

			final Parent root = fxmlLoader.load();
			stage.setTitle("Furnizori");
			stage.setScene(new Scene(root));
			stage.show();

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
