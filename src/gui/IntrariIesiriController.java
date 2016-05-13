package gui;

import java.net.URL;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import org.hibernate.type.TimestampType;

import controller.ArticolController;
import controller.ContController;
import controller.FacturaController;
import controller.FirmaController;
import controller.InregistrareFacturaController;
import controller.UserController;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.DatePicker;
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
			TIP = 0;
		} else {
			TIP = 1;
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
					new InregistrareFacturaController().delete(a.getId());
					table1.setItems(getListFacturi());
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
					table2.setItems(getListArticole(table1.getSelectionModel().getSelectedItem()));
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
		total.setCellValueFactory(new PropertyValueFactory<>("totalString"));
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

	private ObservableList<InregistrareFactura> getListArticole(Factura f) {
		table2.getItems().clear();
		observableListArticole = FXCollections.observableArrayList();
		List<InregistrareFactura> inregistrariFactura = new InregistrareFacturaController().getInregistrariByFactura(f);
		for (InregistrareFactura iff : inregistrariFactura) {
			if (iff.getTip() == TIP) {
				observableListArticole.add(iff);
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
		DatePicker dataDocument = new DatePicker();
		dataDocument.setPrefWidth(120);
		DatePicker dataScadenta = new DatePicker();
		dataScadenta.setPrefWidth(120);

		nrCrt.setText(observableListFacturi.size() + "");
		nrCrt.setEditable(false);

		Button buttonSalvare = new Button("Salvare");
		Button buttonAnulare = new Button("Anulare");
		Button buttonFirmaNoua = new Button("Firma noua");
		buttonSalvare.setMinWidth(100);
		buttonAnulare.setMinWidth(80);
		buttonFirmaNoua.setMinWidth(114);
		hbox.setMargin(buttonFirmaNoua, new Insets(0, 3, 0, 3));

		dataDocument.setValue(LocalDate.now());
		dataScadenta.setValue(LocalDate.now());

		buttonSalvare.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent evt) {
				Factura f = new Factura();

				if (choiceBox.getSelectionModel().getSelectedItem() != null && !nrDocument.getText().equals("")) {
					f.setIdInCategorie(nrCrt.getText());
					f.setNrdoc(nrDocument.getText());
					f.setFirma(choiceBox.getSelectionModel().getSelectedItem());

					f.setDataDocument(Timestamp.valueOf(dataDocument.getValue().atStartOfDay()));
					f.setDataScadenta(Timestamp.valueOf(dataScadenta.getValue().atStartOfDay()));
					f.setUser(new UserController().getById(1));
					f.setTip(TIP);
					new FacturaController().saveObject(f);
					initTable1();
					table1.setDisable(false);
					hbox.setVisible(false);
					root.toFront();
				} else {
					final Stage stage = (Stage) root.getScene().getWindow();
					if (alertError.getOwner() != stage)
						alertError.initOwner(stage);
					alertError.setHeaderText(null);
					alertError.setContentText("Nu ati completat campurile obligatorii!");
					alertError.show();
				}

				// table1.getItems().remove(0);
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
					alertError.setContentText("Nu ati completat campurile obligatorii!");
					alertError.showAndWait();
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
		valoare.setPrefWidth(75);
		TextField tva = new TextField("20");
		Label labelTva = new Label("  %");
		labelTva.setPrefWidth(45);
		tva.setPrefWidth(35);
		ChoiceBox<Cont> choiceBox = new ChoiceBox<>();
		choiceBox.setItems(getListCont());
		choiceBox.setPrefWidth(46);

		Button buttonSalvare = new Button("Salvare");
		Button buttonAnulare = new Button("Anulare");
		buttonSalvare.setMinWidth(100);
		buttonAnulare.setMinWidth(80);

		buttonSalvare.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent evt) {

				if (!denumireArticol.getText().equals("") && !cantitate.getText().equals("")
						&& !valoare.getText().equals("")) {
					InregistrareFactura inregistrareFactura = new InregistrareFactura();
					Factura factura = table1.getSelectionModel().getSelectedItem();

					List<Articol> articole = new ArticolController().selectAll();
					for (Articol a : articole) {
						if (a.getDenumire().equals(denumireArticol.getText())) {
							inregistrareFactura.setArticol(a);
						}
					}

					if (inregistrareFactura.getArticol() == null) {
						Articol articol = new Articol();
						articol.setDenumire(denumireArticol.getText());
						java.util.Date date = new java.util.Date();
						Timestamp currentTime = new Timestamp(date.getTime());
						articol.setData(currentTime);
						inregistrareFactura.setArticol(articol);
					}

					inregistrareFactura.setUm(um.getText());
					inregistrareFactura.setCantitate(Double.parseDouble(cantitate.getText()));
					inregistrareFactura.setPretUnitate(Double.parseDouble(pretUnitate.getText()));
					inregistrareFactura.setCotaTVA(Double.parseDouble(tva.getText()));
					inregistrareFactura.setCont(choiceBox.getValue());
					inregistrareFactura.setFactura(factura);
					inregistrareFactura.setTip(TIP);

					new InregistrareFacturaController().saveObject(inregistrareFactura);

					table2.setItems(getListArticole(table1.getSelectionModel().getSelectedItem()));
					table2.setDisable(false);
					hbox.setVisible(false);
					table1.setItems(getListFacturi());
					root.toFront();
				} else {
					final Stage stage = (Stage) root.getScene().getWindow();
					if (alertError.getOwner() != stage)
						alertError.initOwner(stage);
					alertError.setHeaderText(null);
					alertError.setContentText("Nu ati completat corect datele inregistrarii!");
					alertError.show();
				}

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
				try {
					if (!pretUnitate.getText().equals("")) {
						double valoareDouble = Double.parseDouble(pretUnitate.getText())
								* Double.parseDouble(cantitate.getText());
						valoare.setText(valoareDouble + "");
					}

				} catch (NumberFormatException ex) {
					pretUnitate.setText("0");
					final Stage stage = (Stage) root.getScene().getWindow();
					if (alertError.getOwner() != stage)
						alertError.initOwner(stage);
					alertError.setHeaderText("Pret unitate");
					alertError.setContentText("Nu puteti introduce decat valori numerice!");
					alertError.show();
				}
			}
		});

		valoare.setOnKeyReleased(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent ke) {
				try {
					double valoareDouble = Double.parseDouble(valoare.getText())
							/ Double.parseDouble(cantitate.getText());
					pretUnitate.setText(valoareDouble + "");
				} catch (NumberFormatException ex) {
					valoare.setText("0");
					final Stage stage = (Stage) root.getScene().getWindow();
					if (alertError.getOwner() != stage)
						alertError.initOwner(stage);
					alertError.setHeaderText("Valoare");
					alertError.setContentText("Nu puteti introduce decat valori numerice!");
					alertError.show();
				}
			}
		});

		// pretUnitate.textProperty().addListener(new ChangeListener<String>() {
		// @Override
		// public void changed(ObservableValue<? extends String> observable,
		// String oldValue, String newValue) {
		//
		// try {
		// Double.parseDouble(newValue);
		// pretUnitate.setText(newValue);
		// double valoareDouble = Double.parseDouble(newValue) *
		// Double.parseDouble(cantitate.getText());
		// valoare.setText(valoareDouble + "");
		// } catch (Exception e) {
		// Platform.runLater(() -> {
		// pretUnitate.clear();
		// });
		// final Stage stage = (Stage) root.getScene().getWindow();
		// if (alertError.getOwner() != stage)
		// alertError.initOwner(stage);
		// alertError.setHeaderText(null);
		// alertError.setContentText("Nu puteti introduce decat valori
		// numerice!");
		// }
		// }
		// });
		//
		// valoare.textProperty().addListener(new ChangeListener<String>() {
		// @Override
		// public void changed(ObservableValue<? extends String> observable,
		// String oldValue, String newValue) {
		//
		// try {
		// Double.parseDouble(newValue);
		// valoare.setText(newValue);
		// double valoareDouble = Double.parseDouble(newValue) /
		// Double.parseDouble(cantitate.getText());
		// pretUnitate.setText(valoareDouble + "");
		// } catch (Exception e) {
		// Platform.runLater(() -> {
		// valoare.clear();
		// });
		// final Stage stage = (Stage) root.getScene().getWindow();
		// if (alertError.getOwner() != stage)
		// alertError.initOwner(stage);
		// alertError.setHeaderText(null);
		// alertError.setContentText("Nu puteti introduce decat valori
		// numerice!");
		// }
		// }
		// });

		cantitate.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

				try {
					Double.parseDouble(newValue);
					cantitate.setText(newValue);
				} catch (Exception e) {
					Platform.runLater(() -> {
						cantitate.clear();
					});
					final Stage stage = (Stage) root.getScene().getWindow();
					if (alertError.getOwner() != stage)
						alertError.initOwner(stage);
					alertError.setHeaderText(null);
					alertError.setContentText("Nu puteti introduce decat valori numerice!");
				}
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
