package gui.controllers;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import controller.InregistrareFacturaController;
import controller.UserController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.User;
import utils.CriptareMD5;

public class UtilizatoriController implements Initializable {

	@FXML
	ListView<User> list;
	@FXML
	TextField textField1;
	@FXML
	PasswordField passwordField1;
	@FXML
	PasswordField passwordField2;
	@FXML
	TextField textField5;
	@FXML
	TextField textField6;
	@FXML
	TextField textField7;
	@FXML
	PasswordField passwordField3;
	@FXML
	PasswordField passwordField4;
	@FXML
	ChoiceBox<String> choiceBox1;
	@FXML
	ChoiceBox<String> choiceBox2;
	@FXML
	private AnchorPane root;
	@FXML
	AnchorPane anchor1;
	@FXML
	AnchorPane anchor2;

	private Alert alertInfo = new Alert(AlertType.INFORMATION);
	private Alert alertError = new Alert(AlertType.ERROR);
	private Alert alertConfirm = new Alert(AlertType.CONFIRMATION);

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		list.setItems(FXCollections.observableArrayList(new UserController().selectAll()));
	}

	public void editare(ActionEvent event) {
		if (list.getSelectionModel().getSelectedIndex() != -1) {
			User u = list.getSelectionModel().getSelectedItem();
			textField1.setText(u.getName());
			choiceBox1.getSelectionModel().select(u.getType());
			anchor1.setDisable(true);
			anchor2.setVisible(true);

		}
	}

	public void saveEdit(ActionEvent event) {
		User u = list.getSelectionModel().getSelectedItem();
		u.setName(textField1.getText());

		if (passwordField1.getText().equals(passwordField2.getText()) && !passwordField1.getText().equals("")) {
			u.setPassword(CriptareMD5.cryptWithMD5(passwordField1.getText()));
			System.out.println(passwordField1.getText());
		} else {
			final Stage stage = (Stage) root.getScene().getWindow();
			if (alertInfo.getOwner() != stage)
				alertInfo.initOwner(stage);
			alertInfo.setHeaderText(null);
			alertInfo.setContentText("Nu s-a setat o noua parola!");
			alertInfo.show();
		}

		u.setType(choiceBox1.getSelectionModel().getSelectedIndex());

		new UserController().saveObject(u);

		anchor1.setDisable(false);
		anchor2.setVisible(false);

		textField1.clear();
		passwordField1.clear();
		passwordField2.clear();
		list.setItems(FXCollections.observableArrayList(new UserController().selectAll()));
	}

	public void stergeUser(ActionEvent event) {
		if (list.getSelectionModel().getSelectedIndex() != -1) {

			alertConfirm.setTitle("Stergere articol");
			final Stage stage = (Stage) root.getScene().getWindow();
			if (alertConfirm.getOwner() != stage)
				alertConfirm.initOwner(stage);
			alertConfirm.setHeaderText(null);
			alertConfirm.setContentText("Sigur doriti sa stergeti utilizatorul selectat?");

			Optional<ButtonType> result = alertConfirm.showAndWait();
			if (result.get() == ButtonType.OK) {
				new UserController().delete(list.getSelectionModel().getSelectedItem().getId());
				list.setItems(FXCollections.observableArrayList(new UserController().selectAll()));
			}
		}

	}

	public void adaugaUserNou(ActionEvent event) {
		try {

			for (User user : new UserController().selectAll()) {
				if (user.getUsername().equals(textField7.getText()))
					throw new Exception();
			}

			User u = new User();
			u.setName(textField5.getText() + " " + textField6.getText());
			u.setUsername(textField7.getText());

			if (passwordField3.getText().equals(passwordField4.getText()) && !passwordField3.getText().equals("")) {
				u.setPassword(CriptareMD5.cryptWithMD5(passwordField1.getText()));
				System.out.println(passwordField3.getText());
			} else {
				throw new Exception();
			}

			if (choiceBox2.getSelectionModel().getSelectedIndex() == -1) {
				throw new Exception();
			}
			u.setType(choiceBox2.getSelectionModel().getSelectedIndex());
			new UserController().saveObject(u);

			list.setItems(FXCollections.observableArrayList(new UserController().selectAll()));

			final Stage stage = (Stage) root.getScene().getWindow();
			if (alertInfo.getOwner() != stage)
				alertInfo.initOwner(stage);
			alertInfo.setHeaderText(null);
			alertInfo.setContentText("Utilizatorul "+ u.getUsername()+" a fost adaugat!");
			alertInfo.showAndWait();

			textField5.clear();
			textField6.clear();
			textField7.clear();
			passwordField3.clear();
			passwordField4.clear();
			choiceBox2.getSelectionModel().select(-1);


		} catch (Exception e) {
			final Stage stage = (Stage) root.getScene().getWindow();
			if (alertError.getOwner() != stage)
				alertError.initOwner(stage);
			alertError.setHeaderText(null);
			alertError.setContentText("Unul dintre campuri nu a fost completat corect.");
			alertError.show();
		}
	}

}
