package gui.controllers;

import java.net.URL;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

import controller.FacturaController;
import controller.InregistrareFacturaController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Articol;
import model.Factura;
import model.InregistrareFactura;

public class SituatieAprovizionariController implements Initializable {

	@FXML
	private TableView<InregistrareFactura> tableView;
	@FXML
	private TableColumn<Factura, Articol> denumire;
	@FXML
	private AnchorPane root;
	@FXML
	private TableColumn<InregistrareFactura, Double> total;
	@FXML
	private TableColumn<InregistrareFactura, Double> valoare;
	@FXML
	private TableColumn<InregistrareFactura, Double> pretUnitate;
	@FXML
	private TableColumn<InregistrareFactura, Double> cantitate;
	@FXML
	private Label tipLabel;
	@FXML
	private ChoiceBox<String> choiceBoxLuna;
	@FXML
	private ChoiceBox<String> choiceBoxAn;
	@FXML
	private Label totalAprovizionari;

	private static int LUNA_CURENTA = Calendar.getInstance().get(Calendar.MONTH);
	private double TOTAL_Aprovizionari = 0;
	private DecimalFormat df = new DecimalFormat("0.00");

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initializareTable();
		initializareChoiceBoxuri();
	}

	public void initializareTable() {
		tableView.setPlaceholder(new Label("Nu exista date"));
		denumire.setCellValueFactory(new PropertyValueFactory<>("articol"));
		total.setCellValueFactory(new PropertyValueFactory<>("total"));
		valoare.setCellValueFactory(new PropertyValueFactory<>("valoare"));
		pretUnitate.setCellValueFactory(new PropertyValueFactory<>("pretUnitate"));
		cantitate.setCellValueFactory(new PropertyValueFactory<>("cantitate"));
		tableView.setItems(getFacturiByLuna(LUNA_CURENTA));
		totalAprovizionari.setText("TOTAL APROVIZIONARI: "+df.format(TOTAL_Aprovizionari));


	}

	private void initializareChoiceBoxuri() {
		choiceBoxLuna.getSelectionModel().select(getLunaCurenta());
		choiceBoxAn.getSelectionModel().selectFirst();

		choiceBoxLuna.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				LUNA_CURENTA = choiceBoxLuna.getSelectionModel().getSelectedIndex();
				tableView.getItems().clear();
				tableView.setItems(getFacturiByLuna(LUNA_CURENTA));
				totalAprovizionari.setText("TOTAL APROVIZIONARI: "+df.format(TOTAL_Aprovizionari));
			}
		});

	}

	private String getLunaCurenta() {
		int lunaCurenta = Calendar.getInstance().get(Calendar.MONTH);
		String[] monthNames = { "Ianuarie", "Februarie", "Martie", "Aprilie", "Mai", "Iunie", "Iulie", "August",
				"Septembrie", "Octombrie", "Noiembrie", "Decembrie" };
		return String.valueOf(monthNames[lunaCurenta]);
	}

	public ObservableList<InregistrareFactura> getFacturiByLuna(int luna) {
		TOTAL_Aprovizionari = 0;
		List<InregistrareFactura> inregistrari = new ArrayList<>();
		List<Factura> facturi = new FacturaController().selectAll();

		// preluare facturi din luna curenta
		for (Factura f : facturi) {
			if (f.getDataDocument().getMonth() == luna && f.getTip() == 0) {
				inregistrari.addAll(f.getArticole());
			}
		}

		for (InregistrareFactura iff : inregistrari) {
			TOTAL_Aprovizionari += iff.getTotal();
		}

		return FXCollections.observableArrayList(inregistrari);
	}

}
