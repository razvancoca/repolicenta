package gui.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import controller.ContController;
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
import model.Cont;

public class ConturiController implements Initializable {
	@FXML
	private TableView<Cont> tableView;
	@FXML
	private Button modificaButton;
	@FXML
	private Button salveazaButton;
	@FXML
	private Button adaugaContNou;
	@FXML
	private Button exit;
	@FXML
	private TextField textField;
	@FXML
	private Label label;
	@FXML
	private TableColumn<Cont, String> id;
	@FXML
	private TableColumn<Cont, String> denumire;
	@FXML
	private TableColumn<Cont, String> cont;
	@FXML
	private TextField contNou;
	@FXML
	private TextField denumireContNou;

	ObservableList<Cont> obsList = FXCollections.observableArrayList();

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
				Cont a = tableView.getSelectionModel().getSelectedItem();
				a.setDenumireCont(textField.getText());
				new ContController().saveObject(a);
				populareTableView();
			}
		});

		adaugaContNou.setOnAction(new EventHandler<ActionEvent>() {
			private List<Cont> conturi = new ContController().selectAll();
			@Override
			public void handle(ActionEvent event) {
				Cont c = new Cont();
				c.setCont(contNou.getText());
				c.setDenumireCont(denumireContNou.getText());

				new ContController().saveObject(c);
				populareTableView();
			}
		});


	}

	private void populareTableView() {
		if (!obsList.isEmpty())
			obsList = FXCollections.observableArrayList();
		obsList.addAll(new ContController().selectAll());
		tableView.setItems(obsList);
	}

	private void initTable() {
		tableView.setPlaceholder(new Label("Niciun articol introdus"));
		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		denumire.setCellValueFactory(new PropertyValueFactory<>("denumireCont"));
		cont.setCellValueFactory(new PropertyValueFactory<>("cont"));

		tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if(tableView.getSelectionModel().getSelectedItem()!=null)
			textField.setText(tableView.getSelectionModel().getSelectedItem().getDenumireCont());
		});
	}

}
