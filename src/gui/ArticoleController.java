package gui;

import java.net.URL;
import java.util.ResourceBundle;

import controller.ArticolController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ListView.EditEvent;
import javafx.stage.Stage;
import model.Articol;

public class ArticoleController implements Initializable {
	@FXML
	private ListView<Articol> listView;
	@FXML
	private Button modificaButton;
	@FXML
	private Button salveazaButton;
	@FXML
	private Button exit;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		populareListView();

		modificaButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent evt) {
				listView.setEditable(true);
				listView.setOnEditStart(new EventHandler<ListView.EditEvent<Articol>>() {

					@Override
					public void handle(EditEvent<Articol> event) {
						System.out.println("edit start");
					}
				});

				listView.setOnEditCommit(new EventHandler<ListView.EditEvent<Articol>>() {

					@Override
					public void handle(EditEvent<Articol> event) {
						System.out.println("edit commit");
					}
				});

			}
		});

		exit.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Stage stage = (Stage) exit.getScene().getWindow();
			    // do what you have to do
			    stage.close();
			}
		});

	}

	private void populareListView() {
		ObservableList<Articol> obsList = FXCollections.observableArrayList();
		obsList.addAll(new ArticolController().selectAll());
		listView.setItems(obsList);
	}

}
