package gui.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

import controller.ArticolController;
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
import model.SituatieStocuriTable;

public class SituatieStocuriController implements Initializable {
	@FXML
	private AnchorPane root;

	@FXML
	private TableView<SituatieStocuriTable> tableView;
	@FXML
	private TableColumn<SituatieStocuriTable, String> denumire;
	@FXML
	private TableColumn<SituatieStocuriTable, String> um;
	@FXML
	private TableColumn<SituatieStocuriTable, String> tip;
	@FXML
	private TableColumn<SituatieStocuriTable, String> cantitateIntrata;
	@FXML
	private TableColumn<SituatieStocuriTable, String> cantitateIesita;
	@FXML
	private TableColumn<SituatieStocuriTable, String> valoareIntrata;
	@FXML
	private TableColumn<SituatieStocuriTable, String> valoareIesita;
	@FXML
	private TableColumn<SituatieStocuriTable, String> stocCurent;

	@FXML
	private ChoiceBox<String> choiceBoxLuna;
	@FXML
	private ChoiceBox<String> choiceBoxAn;

	private static int LUNA_CURENTA = Calendar.getInstance().get(Calendar.MONTH);

	ObservableList<SituatieStocuriTable> situatie = FXCollections.observableArrayList();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		initializareTableView();
		initializareChoiceBoxuri();
	}

	private void initializareTableView() {
		tableView.setPlaceholder(new Label("Nu exista date."));
		denumire.setCellValueFactory(new PropertyValueFactory<>("denumire"));
		um.setCellValueFactory(new PropertyValueFactory<>("um"));
		tip.setCellValueFactory(new PropertyValueFactory<>("tip"));
		cantitateIntrata.setCellValueFactory(new PropertyValueFactory<>("cantitateIntrata"));
		cantitateIesita.setCellValueFactory(new PropertyValueFactory<>("cantitateIesita"));
		valoareIntrata.setCellValueFactory(new PropertyValueFactory<>("valoareIntrata"));
		valoareIesita.setCellValueFactory(new PropertyValueFactory<>("valoareIesita"));
		stocCurent.setCellValueFactory(new PropertyValueFactory<>("stocCurent"));
		tableView.setItems(getSituatie(LUNA_CURENTA));
	}

	private void initializareChoiceBoxuri() {
		choiceBoxLuna.getSelectionModel().select(getLunaCurenta());
		choiceBoxAn.getSelectionModel().selectFirst();

		choiceBoxLuna.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				LUNA_CURENTA = choiceBoxLuna.getSelectionModel().getSelectedIndex();
				tableView.getItems().clear();
				tableView.setItems(getSituatie(LUNA_CURENTA));
			}
		});

	}

	private ObservableList<SituatieStocuriTable> getSituatie(int luna) {
		situatie.clear();

		List<InregistrareFactura> inregistrari = new ArrayList<>();
		List<Factura> facturi = new FacturaController().selectAll();
		List<Articol> articole = new ArrayList<>();

		// preluare facturi din luna curenta
		for (Factura f : facturi) {
			if (f.getDataDocument().getMonth() == luna) {
				inregistrari.addAll(f.getArticole());
			}
		}

		// preluare articole utilizate in luna curenta
		for (InregistrareFactura iff : inregistrari) {
			articole.add(iff.getArticol());
		}

		for (InregistrareFactura iff : inregistrari) {
			SituatieStocuriTable s = getSituatieByArticol(iff.getArticol().getDenumire());
			s.setUm(iff.getUm());
			// daca e intrare
			if (iff.getTip() == 0) {
				s.setCantitateIntrata(s.getCantitateIntrata() + iff.getCantitate());
				s.setValoareIntrata(s.getValoareIntrata() + iff.getValoare());
			} else {
				s.setCantitateIesita(s.getCantitateIesita() + iff.getCantitate());
				s.setValoareIesita(s.getValoareIesita() + iff.getValoare());
			}
			s.setStocCurent(s.getCantitateIntrata() - s.getCantitateIesita());
		}

		return situatie;
	}

	private SituatieStocuriTable getSituatieByArticol(String denumire) {
		for (SituatieStocuriTable s : situatie) {
			if (s.getDenumire().equals(denumire)) {
				return s;
			}
		}
		SituatieStocuriTable sitNoua = new SituatieStocuriTable(denumire);
		situatie.add(sitNoua);
		return sitNoua;
	}

	private String getLunaCurenta() {
		int lunaCurenta = Calendar.getInstance().get(Calendar.MONTH);
		String[] monthNames = { "Ianuarie", "Februarie", "Martie", "Aprilie", "Mai", "Iunie", "Iulie", "August",
				"Septembrie", "Octombrie", "Noiembrie", "Decembrie" };
		return String.valueOf(monthNames[lunaCurenta]);
	}

}