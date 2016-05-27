package gui.controllers;

import java.net.URL;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;

import controller.ContController;
import controller.OperatiuneRegistruController;
import javafx.application.Platform;
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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Cont;
import model.OperatiuneRegistru;
import model.RegistruTableDate;

public class RegistruController implements Initializable {

	@FXML
	private AnchorPane root;
	@FXML
	private TableView<RegistruTableDate> tableView1;
	@FXML
	private TableView<OperatiuneRegistru> tableView2;

	@FXML
	private TableColumn<RegistruTableDate, String> data;
	@FXML
	private TableColumn<RegistruTableDate, String> sold;

	@FXML
	private TableColumn<OperatiuneRegistru, String> numarDocument;
	@FXML
	private TableColumn<OperatiuneRegistru, Cont> contDebit;
	@FXML
	private TableColumn<OperatiuneRegistru, Cont> contCredit;
	@FXML
	private TableColumn<OperatiuneRegistru, String> explicatie;
	@FXML
	private TableColumn<OperatiuneRegistru, String> suma;

	@FXML
	private Button inregistrareNoua;
	@FXML
	private Button stergeInregistrare;
	@FXML
	private Button editeaza;
	@FXML
	private ChoiceBox<String> choiceBoxLuna;
	@FXML
	private ChoiceBox<String> choiceBoxAn;
	@FXML
	private Button adaugaButton;
	@FXML
	private Button saveDataButton;

	private Alert alertInfo = new Alert(AlertType.INFORMATION);
	private Alert alertError = new Alert(AlertType.ERROR);
	private Alert alertConfirm = new Alert(AlertType.CONFIRMATION);

	private ObservableList<OperatiuneRegistru> observableListOperatiuni;
	private ObservableList<RegistruTableDate> obsList = FXCollections.observableArrayList();

	private static int LUNA_CURENTA = Calendar.getInstance().get(Calendar.MONTH);

	private DatePicker datePicker = new DatePicker();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initializareControale();
		initializareDatePicker();

		inregistrareNoua.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				tableView2.getSelectionModel().select(observableListOperatiuni.size() - 1);
				tableView2.setDisable(true);

				if (tableView1.getSelectionModel().getSelectedIndex() != -1)
					adaugaOperatiune();
				else {
					final Stage stage = (Stage) root.getScene().getWindow();
					if (alertError.getOwner() != stage)
						alertError.initOwner(stage);
					alertError.setHeaderText(null);
					alertError.setContentText("Nu ati selectat nicio zi!");
					alertError.show();
					tableView2.setDisable(false);
					root.toFront();
				}
			}
		});

		editeaza.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				tableView2.getSelectionModel().select(observableListOperatiuni.size() - 1);
				tableView2.setDisable(true);

				if (tableView1.getSelectionModel().getSelectedIndex() != -1)
					editeazaOperatiune(tableView2.getSelectionModel().getSelectedItem());
				else {
					final Stage stage = (Stage) root.getScene().getWindow();
					if (alertError.getOwner() != stage)
						alertError.initOwner(stage);
					alertError.setHeaderText(null);
					alertError.setContentText("Nu ati selectat nicio zi!");
					alertError.show();
					tableView2.setDisable(false);
					root.toFront();
				}
			}
		});

		stergeInregistrare.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				OperatiuneRegistru operatiuneRegistru = tableView2.getSelectionModel().getSelectedItem();

				alertConfirm.setTitle("Stergere operatiune");
				final Stage stage = (Stage) root.getScene().getWindow();
				if (alertConfirm.getOwner() != stage)
					alertConfirm.initOwner(stage);
				alertConfirm.setHeaderText(null);
				alertConfirm.setContentText("Sigur doriti sa stergeti operatiunea cu nr. de document "
						+ operatiuneRegistru.getNrDocument() + " ?");

				Optional<ButtonType> result = alertConfirm.showAndWait();
				if (result.get() == ButtonType.OK) {
					new OperatiuneRegistruController().delete(operatiuneRegistru.getId());
					initializareControale();
				}
			}
		});

		adaugaButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				saveDataButton.setVisible(true);
				datePicker.setVisible(true);
				adaugaButton.setDisable(true);
			}
		});

		saveDataButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				adaugaData();
				saveDataButton.setVisible(false);
				datePicker.setVisible(false);
				adaugaButton.setDisable(false);
			}
		});
	}

	private void adaugaData() {
		try {

			if (datePicker.getValue().getMonth().getValue() - 1 != LUNA_CURENTA) {
				final Stage stage = (Stage) root.getScene().getWindow();
				if (alertError.getOwner() != stage)
					alertError.initOwner(stage);
				alertError.setHeaderText(null);
				alertError.setContentText("Data selectata nu face parte din luna curenta!");
				alertError.show();
			} else {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
				String dateString = datePicker.getValue().format(formatter);
				obsList.add(new RegistruTableDate(dateString, 0, datePicker.getValue().getDayOfMonth()));
			}
		} catch (Exception e) {
			final Stage stage = (Stage) root.getScene().getWindow();
			if (alertError.getOwner() != stage)
				alertError.initOwner(stage);
			alertError.setHeaderText(null);
			alertError.setContentText("Data nu este corecta!");
			alertError.show();
		}

	}

	private void initializareControale() {
		initializareTableView1(LUNA_CURENTA);
		initializareTableView2();
		initializareChoiceBoxuri();
	}

	private String getAnCurent() {
		int an = Calendar.getInstance().get(Calendar.YEAR);
		return an + "";
	}

	private String getLunaCurenta() {
		int lunaCurenta = Calendar.getInstance().get(Calendar.MONTH);
		String[] monthNames = { "Ianuarie", "Februarie", "Martie", "Aprilie", "Mai", "Iunie", "Iulie", "August",
				"Septembrie", "Octombrie", "Noiembrie", "Decembrie" };
		return String.valueOf(monthNames[lunaCurenta]);
	}

	private void initializareTableView1(int luna) {
		tableView1.setPlaceholder(new Label("Nicio operatiune inregistrata in aceasta luna"));
		data.setCellValueFactory(new PropertyValueFactory<>("data"));
		sold.setCellValueFactory(new PropertyValueFactory<>("sold"));
		tableView1.setItems(getRegistruTableDatesByLuna(luna));

		tableView1.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				tableView2.getSelectionModel().clearSelection();
				if (tableView1.getSelectionModel().getSelectedItem().getData() != null) {
					tableView2.setItems(getOperatiuniByZi(tableView1.getSelectionModel().getSelectedItem().getZi()));
				}
			}
		});

		tableView1.getSelectionModel().select(0);
	}

	private void initializareTableView2() {
		tableView2.setPlaceholder(new Label("Nicio operatiune inregistrata in aceasta zi"));

		numarDocument.setCellValueFactory(new PropertyValueFactory<>("nrDocument"));
		contDebit.setCellValueFactory(new PropertyValueFactory<>("contDebit"));
		contCredit.setCellValueFactory(new PropertyValueFactory<>("contCredit"));
		suma.setCellValueFactory(new PropertyValueFactory<>("suma"));
		explicatie.setCellValueFactory(new PropertyValueFactory<>("explicatie"));
	}

	private void initializareChoiceBoxuri() {
		choiceBoxLuna.getSelectionModel().select(getLunaCurenta());
		choiceBoxAn.getSelectionModel().selectFirst();

		choiceBoxLuna.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				LUNA_CURENTA = choiceBoxLuna.getSelectionModel().getSelectedIndex();
				initializareTableView1(LUNA_CURENTA);
				if (tableView1.getSelectionModel().isEmpty()) {
					tableView2.setItems(null);
				} else {
					tableView1.getSelectionModel().select(0);
				}
			}
		});

	}

	private void initializareDatePicker() {
		datePicker.setPrefWidth(111);
		datePicker.setLayoutX(83);
		datePicker.setLayoutY(545);
		datePicker.setVisible(false);
		root.getChildren().add(datePicker);
	}

	private ObservableList<RegistruTableDate> getListByLuna(int luna) {
		ObservableList<RegistruTableDate> list = FXCollections.observableArrayList();
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);

		while (luna == cal.get(Calendar.MONTH)) {
			RegistruTableDate rtd = new RegistruTableDate(sdf.format(cal.getTime()), 0, cal.getTime().getDay());
			list.add(rtd);
			cal.add(Calendar.DAY_OF_MONTH, 1);
		}
		return list;

	}

	public ObservableList<OperatiuneRegistru> getOperatiuniByZi(int zi) {
		observableListOperatiuni = FXCollections.observableArrayList();
		List<OperatiuneRegistru> operatiuni = new OperatiuneRegistruController().selectAll();
		for (OperatiuneRegistru op : operatiuni) {
			if (op.getData().getDay() == zi && op.getData().getMonth() == LUNA_CURENTA)
				observableListOperatiuni.add(op);
		}
		return observableListOperatiuni;
	}

	private ObservableList<Cont> getListCont() {
		return FXCollections.observableArrayList(new ContController().selectAll());
	}

	public void adaugaOperatiune() {
		HBox hbox = new HBox();
		hbox.setPadding(new Insets(70, 0, 0, 295));
		tableView2.setDisable(true);

		if (tableView2.getItems() == null) {
			tableView2.setItems(FXCollections.observableArrayList());
		}
		tableView2.getItems().add(0, new OperatiuneRegistru());

		hbox.toFront();
		TextField nrDocument = new TextField();
		nrDocument.setPrefWidth(65);
		ChoiceBox<Cont> choiceBoxContDebit = new ChoiceBox<>();
		choiceBoxContDebit.setItems(getListCont());
		choiceBoxContDebit.setPrefWidth(75);

		Button buttonContDebit = new Button("");
		buttonContDebit.setMinWidth(25);
		buttonContDebit.setStyle("-fx-background-image: url('icons/info_icon.png'); -fx-focus-color: transparent;");

		ChoiceBox<Cont> choiceBoxContCredit = new ChoiceBox<>();
		choiceBoxContCredit.setItems(getListCont());
		choiceBoxContCredit.setPrefWidth(75);

		Button buttonContCredit = new Button("");
		buttonContCredit.setMinWidth(25);
		buttonContCredit.setStyle("-fx-background-image: url('icons/info_icon.png');-fx-focus-color: transparent;");

		TextField suma = new TextField();
		suma.setPrefWidth(100);

		TextField explicatie = new TextField();
		explicatie.setPrefWidth(145);

		Button buttonSalvare = new Button("Salvare");
		Button buttonAnulare = new Button("Anulare");

		Button buttonFirmaNoua = new Button("Firma noua");
		buttonSalvare.setMinWidth(70);
		buttonAnulare.setMinWidth(70);
		buttonFirmaNoua.setMinWidth(114);
		hbox.setMargin(buttonFirmaNoua, new Insets(0, 3, 0, 3));

		choiceBoxContCredit.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if (newValue.equals(choiceBoxContDebit.getSelectionModel().getSelectedIndex())) {
					final Stage stage = (Stage) root.getScene().getWindow();
					if (alertError.getOwner() != stage)
						alertError.initOwner(stage);
					alertError.setHeaderText(null);
					alertError.setContentText("Nu puteti folosi acelasi cont ca si la debit!");
					alertError.show();
					choiceBoxContCredit.getSelectionModel().select(-1);
				}
			}

		});

		choiceBoxContDebit.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if (newValue.equals(choiceBoxContCredit.getSelectionModel().getSelectedIndex())) {
					final Stage stage = (Stage) root.getScene().getWindow();
					if (alertError.getOwner() != stage)
						alertError.initOwner(stage);
					alertError.setHeaderText(null);
					alertError.setContentText("Nu puteti folosi acelasi cont ca si la credit!");
					alertError.show();
					choiceBoxContDebit.getSelectionModel().select(-1);
				}
			}

		});

		suma.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

				try {
					Double.parseDouble(newValue);
					suma.setText(newValue);
				} catch (Exception e) {
					Platform.runLater(() -> {
						suma.clear();
					});
					final Stage stage = (Stage) root.getScene().getWindow();
					if (alertError.getOwner() != stage)
						alertError.initOwner(stage);
					alertError.setHeaderText(null);
					alertError.setContentText("Nu puteti introduce decat valori numerice!");
					alertError.show();
				}
			}
		});

		buttonSalvare.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent evt) {

				if (choiceBoxContCredit.getSelectionModel().getSelectedItem() != null
						&& choiceBoxContDebit.getSelectionModel().getSelectedItem() != null
						&& !nrDocument.getText().equals("") && !suma.getText().equals("")) {

					SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

					java.util.Date date = null;
					try {
						date = sdf.parse(tableView1.getSelectionModel().getSelectedItem().getData());
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					OperatiuneRegistru op = new OperatiuneRegistru();
					op.setContCredit(choiceBoxContCredit.getSelectionModel().getSelectedItem());
					op.setContDebit(choiceBoxContDebit.getSelectionModel().getSelectedItem());
					op.setExplicatie(explicatie.getText());
					op.setNrDocument(nrDocument.getText());
					op.setData(new Timestamp(date.getTime()));
					try {
						op.setSuma(Double.parseDouble(suma.getText()));

					} catch (NumberFormatException nfe) {
						final Stage stage = (Stage) root.getScene().getWindow();
						if (alertError.getOwner() != stage)
							alertError.initOwner(stage);
						alertError.setHeaderText(null);
						alertError.setContentText("Nu ati completat o suma valida!");
						alertError.show();
					}

					new OperatiuneRegistruController().saveObject(op);

					tableView2.setItems(getOperatiuniByZi(tableView1.getSelectionModel().getSelectedItem().getZi()));
					initializareTableView1(LUNA_CURENTA);
					tableView2.setDisable(false);
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
				tableView2.getItems().remove(0);
				tableView2.setDisable(false);
				hbox.setVisible(false);
				root.toFront();

			}
		});

		buttonContCredit.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent evt) {
				if (choiceBoxContCredit.getSelectionModel().getSelectedIndex() != -1) {
					Cont cont = choiceBoxContCredit.getSelectionModel().getSelectedItem();
					final Stage stage = (Stage) root.getScene().getWindow();
					if (alertInfo.getOwner() != stage)
						alertInfo.initOwner(stage);
					alertInfo.setTitle("Detalii cont " + cont.getCont());
					alertInfo.setHeaderText(null);
					alertInfo.setContentText(
							"Contul selectat: " + cont.getCont() + "\nDenumirea: " + cont.getDenumireCont());
					alertInfo.show();
				} else {
					final Stage stage = (Stage) root.getScene().getWindow();
					if (alertError.getOwner() != stage)
						alertError.initOwner(stage);
					alertError.setHeaderText(null);
					alertError.setContentText("Nu ati selectat niciun cont!");
					alertError.show();
				}

			}
		});

		buttonContDebit.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent evt) {
				if (choiceBoxContDebit.getSelectionModel().getSelectedIndex() != -1) {
					Cont cont = choiceBoxContDebit.getSelectionModel().getSelectedItem();
					final Stage stage = (Stage) root.getScene().getWindow();
					if (alertInfo.getOwner() != stage)
						alertInfo.initOwner(stage);
					alertInfo.setTitle("Detalii cont " + cont.getCont());
					alertInfo.setHeaderText(null);
					alertInfo.setContentText(
							"Contul selectat: " + cont.getCont() + "\nDenumirea: " + cont.getDenumireCont());
					alertInfo.show();
				} else {
					final Stage stage = (Stage) root.getScene().getWindow();
					if (alertError.getOwner() != stage)
						alertError.initOwner(stage);
					alertError.setHeaderText(null);
					alertError.setContentText("Nu ati selectat niciun cont!");
					alertError.show();
				}

			}
		});

		hbox.getChildren().addAll(nrDocument, choiceBoxContCredit, buttonContCredit, choiceBoxContDebit,
				buttonContDebit, suma, explicatie, buttonSalvare, buttonAnulare);

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				nrDocument.requestFocus();
			}
		});
		root.getChildren().add(hbox);
	}

	public ObservableList<RegistruTableDate> getRegistruTableDatesByLuna(int luna) {
		obsList.clear();
		// returnare a lunilor
		List<OperatiuneRegistru> list = new OperatiuneRegistruController().selectAll();
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		Set<String> set = new HashSet<>();
		Date date;

		for (OperatiuneRegistru op : list) {
			if (op.getData().getMonth() == luna) {
				date = new Date(op.getData().getTime());
				String dataFormatata = sdf.format(date);
				if (set.contains(dataFormatata)) {
					for (RegistruTableDate rtd : obsList) {
						if (rtd.getData().equals(dataFormatata))
							rtd.setSold(rtd.getSold() + op.getSuma());
					}
				} else {
					set.add(dataFormatata);
					obsList.add(new RegistruTableDate(dataFormatata, op.getSuma(), op.getData().getDay()));
				}

			}
		}
		return obsList;
	}

	public void editeazaOperatiune(OperatiuneRegistru op) {
		HBox hbox = new HBox();
		hbox.setPadding(new Insets(70, 0, 0, 295));
		tableView2.setDisable(true);

		if (tableView2.getItems() == null) {
			tableView2.setItems(FXCollections.observableArrayList());
		}
		tableView2.getItems().add(0, new OperatiuneRegistru());

		hbox.toFront();
		TextField nrDocument = new TextField();
		nrDocument.setPrefWidth(65);
		nrDocument.setText(op.getNrDocument());
		ChoiceBox<Cont> choiceBoxContDebit = new ChoiceBox<>();
		choiceBoxContDebit.setItems(getListCont());
		choiceBoxContDebit.setPrefWidth(75);

		Button buttonContDebit = new Button("");
		buttonContDebit.setMinWidth(25);
		buttonContDebit.setStyle("-fx-background-image: url('icons/info_icon.png'); -fx-focus-color: transparent;");

		ChoiceBox<Cont> choiceBoxContCredit = new ChoiceBox<>();
		choiceBoxContCredit.setItems(getListCont());
		choiceBoxContCredit.setPrefWidth(75);
		Button buttonContCredit = new Button("");
		buttonContCredit.setMinWidth(25);
		buttonContCredit.setStyle("-fx-background-image: url('icons/info_icon.png');-fx-focus-color: transparent;");

		TextField suma = new TextField();
		suma.setPrefWidth(100);
		suma.setText(op.getSuma() + "");
		TextField explicatie = new TextField();
		explicatie.setPrefWidth(145);
		explicatie.setText(op.getExplicatie());

		Button buttonSalvare = new Button("Salvare");
		Button buttonAnulare = new Button("Anulare");

		Button buttonFirmaNoua = new Button("Firma noua");
		buttonSalvare.setMinWidth(70);
		buttonAnulare.setMinWidth(70);
		buttonFirmaNoua.setMinWidth(114);
		hbox.setMargin(buttonFirmaNoua, new Insets(0, 3, 0, 3));

		choiceBoxContCredit.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if (newValue.equals(choiceBoxContDebit.getSelectionModel().getSelectedIndex())) {
					final Stage stage = (Stage) root.getScene().getWindow();
					if (alertError.getOwner() != stage)
						alertError.initOwner(stage);
					alertError.setHeaderText(null);
					alertError.setContentText("Nu puteti folosi acelasi cont ca si la debit!");
					alertError.show();
					choiceBoxContCredit.getSelectionModel().select(-1);
				}
			}

		});

		choiceBoxContDebit.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if (newValue.equals(choiceBoxContCredit.getSelectionModel().getSelectedIndex())) {
					final Stage stage = (Stage) root.getScene().getWindow();
					if (alertError.getOwner() != stage)
						alertError.initOwner(stage);
					alertError.setHeaderText(null);
					alertError.setContentText("Nu puteti folosi acelasi cont ca si la credit!");
					alertError.show();
					choiceBoxContDebit.getSelectionModel().select(-1);
				}
			}

		});

		suma.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

				try {
					Double.parseDouble(newValue);
					suma.setText(newValue);
				} catch (Exception e) {
					Platform.runLater(() -> {
						suma.clear();
					});
					final Stage stage = (Stage) root.getScene().getWindow();
					if (alertError.getOwner() != stage)
						alertError.initOwner(stage);
					alertError.setHeaderText(null);
					alertError.setContentText("Nu puteti introduce decat valori numerice!");
					alertError.show();
				}
			}
		});

		buttonSalvare.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent evt) {

				if (choiceBoxContCredit.getSelectionModel().getSelectedItem() != null
						&& choiceBoxContDebit.getSelectionModel().getSelectedItem() != null
						&& !nrDocument.getText().equals("") && !suma.getText().equals("")) {

					SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

					java.util.Date date = null;
					try {
						date = sdf.parse(tableView1.getSelectionModel().getSelectedItem().getData());
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					op.setContCredit(choiceBoxContCredit.getSelectionModel().getSelectedItem());
					op.setContDebit(choiceBoxContDebit.getSelectionModel().getSelectedItem());
					op.setExplicatie(explicatie.getText());
					op.setNrDocument(nrDocument.getText());
					op.setData(new Timestamp(date.getTime()));
					try {
						op.setSuma(Double.parseDouble(suma.getText()));

					} catch (NumberFormatException nfe) {
						final Stage stage = (Stage) root.getScene().getWindow();
						if (alertError.getOwner() != stage)
							alertError.initOwner(stage);
						alertError.setHeaderText(null);
						alertError.setContentText("Nu ati completat o suma valida!");
						alertError.show();
					}

					new OperatiuneRegistruController().saveObject(op);

					tableView2.setItems(getOperatiuniByZi(tableView1.getSelectionModel().getSelectedItem().getZi()));
					initializareTableView1(LUNA_CURENTA);
					tableView2.setDisable(false);
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
				tableView2.getItems().remove(0);
				tableView2.setDisable(false);
				hbox.setVisible(false);
				root.toFront();

			}
		});

		buttonContCredit.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent evt) {
				if (choiceBoxContCredit.getSelectionModel().getSelectedIndex() != -1) {
					Cont cont = choiceBoxContCredit.getSelectionModel().getSelectedItem();
					final Stage stage = (Stage) root.getScene().getWindow();
					if (alertInfo.getOwner() != stage)
						alertInfo.initOwner(stage);
					alertInfo.setTitle("Detalii cont " + cont.getCont());
					alertInfo.setHeaderText(null);
					alertInfo.setContentText(
							"Contul selectat: " + cont.getCont() + "\nDenumirea: " + cont.getDenumireCont());
					alertInfo.show();
				} else {
					final Stage stage = (Stage) root.getScene().getWindow();
					if (alertError.getOwner() != stage)
						alertError.initOwner(stage);
					alertError.setHeaderText(null);
					alertError.setContentText("Nu ati selectat niciun cont!");
					alertError.show();
				}

			}
		});

		buttonContDebit.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent evt) {
				if (choiceBoxContDebit.getSelectionModel().getSelectedIndex() != -1) {
					Cont cont = choiceBoxContDebit.getSelectionModel().getSelectedItem();
					final Stage stage = (Stage) root.getScene().getWindow();
					if (alertInfo.getOwner() != stage)
						alertInfo.initOwner(stage);
					alertInfo.setTitle("Detalii cont " + cont.getCont());
					alertInfo.setHeaderText(null);
					alertInfo.setContentText(
							"Contul selectat: " + cont.getCont() + "\nDenumirea: " + cont.getDenumireCont());
					alertInfo.show();
				} else {
					final Stage stage = (Stage) root.getScene().getWindow();
					if (alertError.getOwner() != stage)
						alertError.initOwner(stage);
					alertError.setHeaderText(null);
					alertError.setContentText("Nu ati selectat niciun cont!");
					alertError.show();
				}

			}
		});

		hbox.getChildren().addAll(nrDocument, choiceBoxContCredit, buttonContCredit, choiceBoxContDebit,
				buttonContDebit, suma, explicatie, buttonSalvare, buttonAnulare);

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				nrDocument.requestFocus();
			}
		});
		root.getChildren().add(hbox);
	}
}
