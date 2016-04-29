package gui;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import controller.FirmaController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Firma;

public class ClientiFurnizoriController implements Initializable {
	Alert alertInfo = new Alert(AlertType.INFORMATION);
	Alert alertError = new Alert(AlertType.ERROR);
	Alert alertConfirm = new Alert(AlertType.CONFIRMATION);
	private int TIP;
	@FXML
	private TextField denumire;
	@FXML
	private TextField cui;
	@FXML
	private TextField j;
	@FXML
	private TextArea detalii;
	@FXML
	private TableView<Firma> tableView1;
	@FXML
	private TableColumn<Firma, String> denumireColumn;
	@FXML
	private TableColumn<Firma, String> cuiColumn;
	@FXML
	private TableColumn<Firma, String> jColumn;
	@FXML
	private TableColumn<Firma, String> detaliiColumn;
	@FXML
	private AnchorPane paneTabel;
	@FXML
	private AnchorPane root;
	@FXML
	private SplitPane splitPane;
	@FXML
	private Label tipLabel;


	@FXML
	private void adaugaAction(ActionEvent event) {
		Firma firma = new Firma();
		firma.setDenumire(denumire.getText());
		firma.setCui(cui.getText());
		firma.setJ(j.getText());
		firma.setDetalii(detalii.getText());
		firma.setTip(TIP);

		new FirmaController().saveObject(firma);

		final Stage stage = (Stage) root.getScene().getWindow();
		if(alertInfo.getOwner()!=stage)
			alertInfo.initOwner(stage);
		alertInfo.setTitle("Firma adaugata");
		alertInfo.setHeaderText(null);
		alertInfo.setContentText("Firma a fost adaugata cu succes!");
		alertInfo.showAndWait();
		initTable();
	}

	@FXML
	public void modificaAction(ActionEvent event) {
		Firma firma = tableView1.getSelectionModel().getSelectedItem();
		if (firma == null) {
			alertError.setTitle("Eroare");
			final Stage stage = (Stage) root.getScene().getWindow();
			if(alertConfirm.getOwner()!=stage)
			alertError.setHeaderText(null);
			alertError.setContentText("Nu ati selectat nicio firma!");
			alertError.showAndWait();
		} else {
			modificaFirma();
			System.out.println("modifica box");
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if(tipLabel.getText().equals("furnizor")){
			TIP=1;
		}else{
			TIP=0;
		}
		initTable();
		modificaFirma();
	}

	public void initTable() {
		denumireColumn.setCellValueFactory(new PropertyValueFactory<>("denumire"));
		denumireColumn.setCellFactory(TextFieldTableCell.forTableColumn());

		cuiColumn.setCellValueFactory(new PropertyValueFactory<>("cui"));
		cuiColumn.setCellFactory(TextFieldTableCell.forTableColumn());

		jColumn.setCellValueFactory(new PropertyValueFactory<>("j"));
		jColumn.setCellFactory(TextFieldTableCell.forTableColumn());

		detaliiColumn.setCellValueFactory(new PropertyValueFactory<>("detalii"));
		detaliiColumn.setCellFactory(TextFieldTableCell.forTableColumn());

		tableView1.setItems(getList());
	}

	private ObservableList<Firma> getList() {
		List<Firma> listaFirme = new FirmaController().selectAll();
		ObservableList<Firma> list = FXCollections.observableArrayList();

		for (Firma f : listaFirme) {
			if (f.getTip() == TIP) {
				list.add(f);
			}
		}
		return list;
	}

	public void modificaFirma() {
		HBox hbox = new HBox();
		hbox.setMinHeight(250);
		TextField denumire = new TextField();
		denumire.setPrefWidth(200);
		TextField cui = new TextField();
		cui.setPrefWidth(80);
		TextField j = new TextField();
		j.setPrefWidth(90);
		TextField detalii = new TextField();
		detalii.setPrefWidth(230);

		Button buttonModifica = new Button("Modifica");
		Button buttonSterge = new Button("Sterge");

		hbox.getChildren().addAll(denumire, cui, j, detalii, buttonModifica,buttonSterge);
		root.getChildren().add(hbox);
		hbox.toBack();
		// hbox.setPadding(new Insets(100,200,1,1));

		tableView1.getSelectionModel().selectedItemProperty().addListener((obs, ov, nv) -> {
			if (nv != null) {
				denumire.setText(nv.getDenumire());
				cui.setText(nv.getCui());
				j.setText(nv.getJ());
				detalii.setText(nv.getDetalii());
			}
		});

		tableView1.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent evt) {
				if (evt.getClickCount() == 2) {
					Insets insets = new Insets(evt.getSceneY()-8, 0, 0, 2);
					alertConfirm.setTitle("Modificare firma");
					final Stage stage = (Stage) root.getScene().getWindow();
					if(alertConfirm.getOwner()!=stage)
					alertConfirm.initOwner(stage);
					alertConfirm.setHeaderText(null);
					alertConfirm.setContentText("Doriti sa modificati firma cu denumirea "
							+ tableView1.getSelectionModel().getSelectedItem().getDenumire() + " ?");

					Optional<ButtonType> result = alertConfirm.showAndWait();
					if (result.get() == ButtonType.OK) {
						hbox.setPadding(insets);
						splitPane.setDisable(true);
						hbox.toFront();
					}
				}
			}
		});

		buttonModifica.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent evt) {

				Firma f = tableView1.getSelectionModel().getSelectedItem();
				f.setDenumire(denumire.getText());
				f.setCui(cui.getText());
				f.setJ(j.getText());
				f.setDetalii(detalii.getText());
				new FirmaController().saveObject(f);
				initTable();
				hbox.toBack();
				splitPane.setDisable(false);
			}
		});

		buttonSterge.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent evt) {
				Firma f = tableView1.getSelectionModel().getSelectedItem();
				alertConfirm.setTitle("Stergere firma");
				final Stage stage = (Stage) root.getScene().getWindow();
				if(alertConfirm.getOwner()!=stage)
				alertConfirm.initOwner(stage);
				alertConfirm.setHeaderText(null);
				alertConfirm.setContentText("Sigur doriti sa stergeti firma cu denumirea "
						+ f.getDenumire() + " ?");

				Optional<ButtonType> result = alertConfirm.showAndWait();
				if (result.get() == ButtonType.OK) {
					new FirmaController().delete(f.getId());
					initTable();
					hbox.toBack();
					splitPane.setDisable(false);

				} else {
					hbox.toBack();
					splitPane.setDisable(false);
				}

			}
		});


	}

}
