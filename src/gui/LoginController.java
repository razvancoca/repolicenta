package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import controller.UserController;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;
import utils.DBConnection;
import utils.ServiceProgressIndicator;

public class LoginController implements Initializable {

	@FXML
	private Label label2;
	@FXML
	private TextField txtUsername;
	@FXML
	private TextField txtPassword;

	@FXML
	private void btnLoginAction(ActionEvent event) {
		User user;
		if (!txtUsername.getText().equals("") && !txtPassword.getText().equals("")) {
			user = new UserController().login(txtUsername.getText(), txtPassword.getText());
			if (user != null) {
				try {
					((Node) event.getSource()).getScene().getWindow().hide();
					Parent parent = FXMLLoader.load(getClass().getResource("MainFrame.fxml"));
					Scene scene = new Scene(parent);
					Stage stage = new Stage();
					stage.setScene(scene);
					stage.setTitle("MainFrame");
					stage.show();

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				label2.setText("Userul/parola introduse gresit.");
			}
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		DBConnection.getInstance().getConnection();
	}
}
