package gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.DBConnection;

public class Registru extends Application{

	@Override
	public void start(Stage stage) {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("fxml/Registru.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setTitle("Registru");
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void stop() throws Exception {
		super.stop();
	}


}
