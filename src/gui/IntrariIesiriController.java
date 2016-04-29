package gui;

import java.net.URL;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import controller.ArticolController;
import controller.ContController;
import controller.FacturaController;
import controller.FirmaController;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Articol;
import model.Cont;
import model.Factura;
import model.Firma;
import model.InregistrareFactura;

public class IntrariIesiriController implements Initializable {
	private int TIP;
	ObservableList<Factura> observableListFacturi;
	ObservableList<InregistrareFactura> observableListArticole;
	ObservableList<Firma> observableListFirme;
	ObservableList<Cont> observableListConturi;

	private ObjectProperty<TableRow<Factura>> lastSelectedRow = new SimpleObjectProperty<>();
	Alert alertInfo = new Alert(AlertType.INFORMATION);
	Alert alertError = new Alert(AlertType.ERROR);
	Alert alertConfirm = new Alert(AlertType.CONFIRMATION);

	// TableView 1
	@FXML
	private TableView<Factura> table1;
	@FXML
	private TableColumn<Factura, String> nrDoc1;
	@FXML
	private TableColumn<Factura, Firma> denumire1;
	@FXML
	private TableColumn<Factura, Timestamp> dataD;
	@FXML
	private TableColumn<Factura, Timestamp> dataS;
	@FXML
	private AnchorPane root;
	@FXML
	private TableColumn<Factura, String> nrCrt1;
	@FXML
	private TableColumn<InregistrareFactura, String> totalTable1;
	@FXML
	private Label tipLabel;

	// TableView2

	@FXML
	private TableView<InregistrareFactura> table2;
	@FXML
	private TableColumn<InregistrareFactura, Articol> denumire2;
	@FXML
	private TableColumn<InregistrareFactura, Firma> um;
	@FXML
	private TableColumn<InregistrareFactura, Double> cantitate;
	@FXML
	private TableColumn<InregistrareFactura, Double> pretUnitate;
	@FXML
	private TableColumn<InregistrareFactura, Double> valoare;
	@FXML
	private TableColumn<InregistrareFactura, Double> TVA;
	@FXML
	private TableColumn<InregistrareFactura, Double> total;
	@FXML
	private TableColumn<InregistrareFactura, Double> valoareTVA;
	@FXML
	private TableColumn<InregistrareFactura, Cont> cont;
	@FXML
	private Button facturaNouaButton;
	@FXML
	private Button stergeFactura;
	@FXML
	private Button adaugaArticolButton;
	@FXML
	private Button stergeIregistrareButton;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (tipLabel.getText().equals("intrare")) {
			TIP = 1;
		} else {
			TIP = 0;
		}
		initTable1();
		initTable2();
		getSelectedRowFunction();
		TableViewSelectionModel<Factura> model = table1.getSelectionModel();
		model.select(0);

		facturaNouaButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				table1.getSelectionModel().select(observableListFacturi.size() - 1);
				table1.setDisable(true);
				adaugaFactura();
			}
		});

		stergeFactura.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Factura f = table1.getSelectionModel().getSelectedItem();

				alertConfirm.setTitle("Stergere factura");
				final Stage stage = (Stage) root.getScene().getWindow();
				if (alertConfirm.getOwner() != stage)
					alertConfirm.initOwner(stage);
				alertConfirm.setHeaderText(null);
				alertConfirm.setContentText("Sigur doriti sa stergeti factura firmei " + f.getDenumireFirma() + " ?");

				Optional<ButtonType> result = alertConfirm.showAndWait();
				if (result.get() == ButtonType.OK) {
					new FacturaController().delete(f.getId());
					initTable1();
				}

			}
		});

		adaugaArticolButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				table2.getSelectionModel().select(observableListArticole.size() - 1);
				table2.setDisable(true);
				adaugaArticol();
			}
		});

		stergeIregistrareButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				InregistrareFactura a = table2.getSelectionModel().getSelectedItem();

				alertConfirm.setTitle("Stergere articol");
				final Stage stage = (Stage) root.getScene().getWindow();
				if (alertConfirm.getOwner() != stage)
					alertConfirm.initOwner(stage);
				alertConfirm.setHeaderText(null);
				alertConfirm
						.setContentText("Sigur doriti sa stergeti articolul " + a.getArticol().getDenumire() + " ?");

				Optional<ButtonType> result = alertConfirm.showAndWait();
				if (result.get() == ButtonType.OK) {
					table2.getItems().remove(a);
					table1.getSelectionModel().getSelectedItem().getArticole().remove(a);
					new FacturaController().saveObject(table1.getSelectionModel().getSelectedItem());
				}

			}
		});

	}

	private void initTable1() {

		table1.setPlaceholder(new Label("Nicio factura de " + tipLabel.getText() + " introdusa"));
		nrDoc1.setCellValueFactory(new PropertyValueFactory<>("nrdoc"));

		denumire1.setCellValueFactory(new PropertyValueFactory<>("denumireFirma"));

		dataD.setCellValueFactory(new PropertyValueFactory<>("stringDateD"));

		dataS.setCellValueFactory(new PropertyValueFactory<>("stringDateS"));

		nrCrt1.setCellValueFactory(new PropertyValueFactory<>("idInCategorie"));

		totalTable1.setCellValueFactory(new PropertyValueFactory<>("totalFactura"));

		table1.setItems(getListFacturi());

		nrCrt1.setSortable(false);

		table1.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				table2.getSelectionModel().clearSelection();
				if (table1.getSelectionModel().getSelectedItem().getArticole() != null) {
					table2.setItems(getListArticole(table1.getSelectionModel().getSelectedItem().getArticole()));
				}
			}
		});
	}

	private void initTable2() {
		table2.setPlaceholder(new Label("Aceasta factura nu are niciun articol adaugat."));
		denumire2.setCellValueFactory(new PropertyValueFactory<>("articol"));
		um.setCellValueFactory(new PropertyValueFactory<>("um"));
		valoare.setCellValueFactory(new PropertyValueFactory<>("valoare"));
		cantitate.setCellValueFactory(new PropertyValueFactory<>("cantitate"));
		pretUnitate.setCellValueFactory(new PropertyValueFactory<>("pretUnitate"));
		TVA.setCellValueFactory(new PropertyValueFactory<>("cotaTVA"));
		total.setCellValueFactory(new PropertyValueFactory<>("total"));
		valoareTVA.setCellValueFactory(new PropertyValueFactory<>("valoareTVA"));
		cont.setCellValueFactory(new PropertyValueFactory<>("cont"));
	}

	private ObservableList<Factura> getListFacturi() {
		List<Factura> listaFacturi = new FacturaController().selectAll();
		observableListFacturi = FXCollections.observableArrayList();

		for (Factura factura : listaFacturi) {
			if (factura.getTip() == TIP) {
				observableListFacturi.add(factura);
			}
		}
		return observableListFacturi;
	}

	private ObservableList<InregistrareFactura> getListArticole(List<InregistrareFactura> articole) {
		table2.getItems().clear();
		observableListArticole = FXCollections.observableArrayList();
		for (InregistrareFactura a : articole) {
			if (a.getTip() == TIP) {
				observableListArticole.add(a);
			}
		}
		return observableListArticole;
	}

	private ObservableList<Firma> getListFirme() {
		List<Firma> listFirme = new FirmaController().selectAll();
		observableListFirme = FXCollections.observableArrayList();
		for (Firma a : listFirme) {
			if (a.getTip() == TIP) {
				observableListFirme.add(a);
			}
		}
		return observableListFirme;
	}

	public String getMaxIdInCateg() {
		List<Factura> listaFacturi = new FacturaController().selectAll();
		int max = 1;
		for (Factura a : listaFacturi) {
			if (Integer.parseInt(a.getIdInCategorie()) > max)
				max = Integer.parseInt(a.getIdInCategorie());
		}
		max++;
		return max + "";
	}

	private void getSelectedRowFunction() {
		table1.setRowFactory(tableView -> {
			TableRow<Factura> row = new TableRow<Factura>();
			row.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
				if (isNowSelected) {
					lastSelectedRow.set(row);
				}
			});
			return row;
		});
	}

	public void adaugaFactura() {

		HBox hbox = new HBox();
		hbox.setPadding(new Insets(60, 0, 0, 0));
		table1.setDisable(true);
		table1.getItems().add(0, new Factura());

		hbox.toFront();
		// hbox.setMinHeight(250);
		TextField nrCrt = new TextField();
		nrCrt.setPrefWidth(53);
		TextField nrDocument = new TextField();
		nrDocument.setPrefWidth(89);
		ChoiceBox<Firma> choiceBox = new ChoiceBox<>();
		choiceBox.setItems(getListFirme());
		choiceBox.setPrefWidth(250);
		TextField dataDocument = new TextField();
		dataDocument.setPrefWidth(120);
		TextField dataScadenta = new TextField();
		dataScadenta.setPrefWidth(120);

		nrCrt.setText(getMaxIdInCateg());
		nrCrt.setEditable(false);

		Button buttonSalvare = new Button("Salvare");
		Button buttonAnulare = new Button("Anulare");
		Button buttonFirmaNoua = new Button("Firma noua");
		buttonSalvare.setMinWidth(100);
		buttonAnulare.setMinWidth(80);
		buttonFirmaNoua.setMinWidth(114);
		hbox.setMargin(buttonFirmaNoua, new Insets(0, 3, 0, 3));

		java.util.Date date = new java.util.Date();
		Timestamp currentTime = new Timestamp(date.getTime());
		dataDocument.setText(new java.text.SimpleDateFormat("dd.MM.yyyy").format(currentTime));
		dataScadenta.setText(new java.text.SimpleDateFormat("dd.MM.yyyy").format(currentTime));

		buttonSalvare.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent evt) {
				Factura f = new Factura();
				f.setIdInCategorie(nrCrt.getText());
				f.setNrdoc(nrDocument.getText());
				f.setFirma(choiceBox.getSelectionModel().getSelectedItem());

				SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
				try {
					Date date1 = (Date) sdf.parse(dataDocument.getText());
					Date date2 = (Date) sdf.parse(dataScadenta.getText());

					java.sql.Timestamp timeStampDataDocument = new Timestamp(date1.getTime());
					java.sql.Timestamp timeStampDataScadenta = new Timestamp(date2.getTime());

					f.setDataDocument(timeStampDataDocument);
					f.setDataScadenta(timeStampDataScadenta);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				new FacturaController().saveObject(f);
				initTable1();
				table1.setDisable(false);
				hbox.setVisible(false);
				root.toFront();
				//table1.getItems().remove(0);
			}
		});

		buttonAnulare.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent evt) {
				table1.getItems().remove(0);
				table1.setDisable(false);
				hbox.setVisible(false);
				root.toFront();

			}
		});

		buttonFirmaNoua.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent evt) {
				adaugaFirma(choiceBox);

			}
		});

		hbox.getChildren().addAll(nrCrt, nrDocument, choiceBox, buttonFirmaNoua, dataDocument, dataScadenta,
				buttonSalvare, buttonAnulare);

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				nrDocument.requestFocus();
			}
		});
		root.getChildren().add(hbox);

	}

	public void adaugaFirma(ChoiceBox<Firma> choiceBox) {
		HBox hboxFirma = new HBox();
		hboxFirma.setLayoutY(90);
		hboxFirma.setLayoutX(50);
		hboxFirma.setPadding(new Insets(10));
		hboxFirma.setStyle("-fx-background-color: linear-gradient(to bottom, #f2f2f2, #d4d4d4);"
				+ "-fx-border-color: white; -fx-background-radius: 10.0;" + " -fx-border-radius: 15.0");
		hboxFirma.toFront();

		Label labelDenumireFirma = new Label("Denumire firma: ");
		TextField denumireFirma = new TextField();
		denumireFirma.setPrefWidth(100);

		Label labelCui = new Label("Cui: ");
		TextField cui = new TextField();
		cui.setPrefWidth(90);

		Label labelJ = new Label("J: ");
		TextField j = new TextField();
		j.setPrefWidth(90);

		Label labelAlteDetalii = new Label("Alte detalii: ");
		TextField alteDetalii = new TextField();
		alteDetalii.setPrefWidth(180);

		Button buttonSalvare = new Button("Salvare");
		Button buttonAnulare = new Button("Anulare");

		hboxFirma.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
		hboxFirma.setMargin(denumireFirma, new Insets(0, 5, 0, 5));
		hboxFirma.setMargin(cui, new Insets(0, 5, 0, 5));
		hboxFirma.setMargin(j, new Insets(0, 5, 0, 5));
		hboxFirma.setMargin(alteDetalii, new Insets(0, 5, 0, 5));
		hboxFirma.setMargin(buttonSalvare, new Insets(0, 2, 0, 0));
		hboxFirma.setMargin(buttonAnulare, new Insets(0, 2, 0, 0));

		buttonSalvare.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent evt) {
				// actualziare lista firme

				if (denumireFirma.getText().equals("") || cui.getText().equals("")) {
					final Stage stage = (Stage) root.getScene().getWindow();
					if (alertError.getOwner() != stage)
						alertError.initOwner(stage);
					alertError.setHeaderText(null);
					alertError.setContentText(
							"Nu ati completat campurile obligatorii! Firma nu a fost salvata in baza de date.");
					alertError.showAndWait();
					hboxFirma.setVisible(false);

				} else {
					Firma firma = new Firma();
					firma.setDenumire(denumireFirma.getText());
					firma.setCui(cui.getText());
					firma.setJ(j.getText());
					firma.setDetalii(alteDetalii.getText());
					firma.setTip(TIP);

					new FirmaController().saveObject(firma);

					hboxFirma.setVisible(false);
					final Stage stage = (Stage) root.getScene().getWindow();
					if (alertInfo.getOwner() != stage)
						alertInfo.initOwner(stage);
					alertInfo.setTitle("Firma adaugata");
					alertInfo.setHeaderText(null);
					alertInfo.setContentText("Firma a fost adaugata cu succes!");
					alertInfo.showAndWait();
					choiceBox.setItems(getListFirme());
				}
			}
		});

		buttonAnulare.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent evt) {
				hboxFirma.setVisible(false);

			}
		});

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				denumireFirma.requestFocus();
			}
		});

		hboxFirma.getChildren().addAll(labelDenumireFirma, denumireFirma, labelCui, cui, labelJ, j, labelAlteDetalii,
				alteDetalii, buttonAnulare, buttonSalvare);
		root.getChildren().add(hboxFirma);
	}

	public void adaugaArticol() {

		HBox hbox = new HBox();
		hbox.setPadding(new Insets(356, 0, 0, 0));
		table2.setDisable(true);
		table2.getItems().add(0, new InregistrareFactura());

		hbox.toFront();
		// hbox.setMinHeight(250);
		TextField denumireArticol = new TextField();

		denumireArticol.setPrefWidth(372);
		TextField um = new TextField();
		um.setPrefWidth(54);
		TextField cantitate = new TextField();
		cantitate.setPrefWidth(61);
		TextField pretUnitate = new TextField();
		pretUnitate.setPrefWidth(75);
		TextField valoare = new TextField();
		valoare.setPrefWidth(65);
		TextField tva = new TextField("20");
		Label labelTva = new Label("%");
		tva.setPrefWidth(75);
		ChoiceBox<Cont> choiceBox = new ChoiceBox<>();
		choiceBox.setItems(getListCont());
		choiceBox.setPrefWidth(46);

		Button buttonSalvare = new Button("Salvare");
		Button buttonAnulare = new Button("Anulare");
		buttonSalvare.setMinWidth(100);
		buttonAnulare.setMinWidth(80);

		buttonSalvare.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent evt) {
				InregistrareFactura inregistrareFactura = new InregistrareFactura();

				List<Articol> articole = new ArticolController().selectAll();
				for (Articol a : articole) {
					if (a.getDenumire().equals(denumireArticol.getText())) {
						inregistrareFactura.setArticol(a);
					}
				}

				if (inregistrareFactura.getArticol() == null) {
					Articol articol = new Articol();
					articol.setDenumire(denumireArticol.getText());
					inregistrareFactura.setArticol(articol);
					java.util.Date date = new java.util.Date();
					Timestamp currentTime = new Timestamp(date.getTime());
					inregistrareFactura.getArticol().setData(currentTime);
				}

				inregistrareFactura.setUm(um.getText());
				inregistrareFactura.setCantitate(Double.parseDouble(cantitate.getText()));
				inregistrareFactura.setPretUnitate(Double.parseDouble(pretUnitate.getText()));
				inregistrareFactura.setCotaTVA(Double.parseDouble(tva.getText()));
				inregistrareFactura.setCont(choiceBox.getValue());

				Factura factura = table1.getSelectionModel().getSelectedItem();
				factura.getArticole().add(inregistrareFactura);

				// new ArticolController().saveObject(articol);
				new FacturaController().saveObject(factura);

				table2.setItems(getListArticole(table1.getSelectionModel().getSelectedItem().getArticole()));
				table2.setDisable(false);
				hbox.setVisible(false);
				root.toFront();
			}
		});

		buttonAnulare.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent evt) {
				table2.getItems().remove(0);
				table2.setDisable(false);
				hbox.setVisible(false);
				root.toFront();

			}
		});

		pretUnitate.setOnKeyReleased(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent ke) {
				double valoareDouble = Double.parseDouble(pretUnitate.getText())
						* Double.parseDouble(cantitate.getText());
				valoare.setText(valoareDouble + "");
			}
		});

		valoare.setOnKeyReleased(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent ke) {
				double valoareDouble = Double.parseDouble(valoare.getText()) / Double.parseDouble(cantitate.getText());
				pretUnitate.setText(valoareDouble + "");
			}
		});

		hbox.getChildren().addAll(denumireArticol, um, cantitate, pretUnitate, valoare, tva, labelTva, choiceBox,
				buttonSalvare, buttonAnulare);

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				denumireArticol.requestFocus();
			}
		});

		root.getChildren().add(hbox);

	}

	private ObservableList<Cont> getListCont() {
		List<Cont> listCont = new ContController().selectAll();
		observableListConturi = FXCollections.observableArrayList();
		for (Cont a : listCont) {
			observableListConturi.add(a);
		}
		return observableListConturi;
	}
}
