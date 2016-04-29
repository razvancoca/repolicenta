package gui;

import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;

import controller.ArticolController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Articol;

public class ArticoleController implements Initializable {
	@FXML
	private TableView<Articol> tableView;
	@FXML
	private Button modificaButton;
	@FXML
	private Button salveazaButton;
	@FXML
	private Button exit;
	@FXML
	private TextField textField;
	@FXML
	private Label label;
	@FXML
	private TableColumn<Articol, String> id;
	@FXML
	private TableColumn<Articol, String> denumire;
	@FXML
	private TableColumn<Articol, String> data;

	ObservableList<Articol> obsList = FXCollections.observableArrayList();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initTable();
		populareTableView();

		modificaButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent evt) {
				salveazaButton.setVisible(true);
				label.setVisible(true);
				textField.setVisible(true);
			}
		});

		salveazaButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				salveazaButton.setVisible(false);
				label.setVisible(false);
				textField.setVisible(false);
				Articol a = tableView.getSelectionModel().getSelectedItem();
				a.setDenumire(textField.getText());
				java.util.Date date = new java.util.Date();
				Timestamp currentTime = new Timestamp(date.getTime());
				a.setData(currentTime);
				new ArticolController().saveObject(a);
				populareTableView();
			}
		});

	}

	private void populareTableView() {
		if (!obsList.isEmpty())
			obsList = FXCollections.observableArrayList();
		obsList.addAll(new ArticolController().selectAll());
		tableView.setItems(obsList);
	}

	private void initTable() {
		tableView.setPlaceholder(new Label("Niciun articol introdus"));
		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		denumire.setCellValueFactory(new PropertyValueFactory<>("denumire"));
		data.setCellValueFactory(new PropertyValueFactory<>("data"));

		tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if(tableView.getSelectionModel().getSelectedItem()!=null)
			textField.setText(tableView.getSelectionModel().getSelectedItem().getDenumire());
		});
	}

}
