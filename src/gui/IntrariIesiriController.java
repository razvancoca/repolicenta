package gui;

import java.net.URL;
import java.sql.Timestamp;
import java.util.List;
import java.util.ResourceBundle;

import controller.FacturaController;
import controller.FirmaController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import model.Factura;
import model.Firma;

public class IntrariIesiriController implements Initializable {

	@FXML
	private TableView<Factura> table1;
	@FXML
	private TableView<Factura> table2;
	@FXML
	private TableColumn<String, String> nrCrt1;
	@FXML
	private TableColumn<Factura, String> nrDoc1;
	@FXML
	private TableColumn<Factura, Firma> denumire1;
	@FXML
	private TableColumn<Factura, Timestamp> dataD;
	@FXML
	private TableColumn<Factura, Timestamp> dataS;

	@FXML
	private void facturaNouaAction(ActionEvent ev) {

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		initTable();

	}

	public void initTable() {
		nrDoc1.setCellValueFactory(new PropertyValueFactory<>("nrdoc"));
		nrDoc1.setCellFactory(TextFieldTableCell.forTableColumn());

		denumire1.setCellValueFactory(new PropertyValueFactory<>("firma"));

		nrCrt1.setCellValueFactory(new PropertyValueFactory<>("id"));

		// dataD.setCellValueFactory(new
		// PropertyValueFactory<>("dataDocument"));

		// dataS.setCellValueFactory(new
		// PropertyValueFactory<>("dataScadenta"));

		table1.setItems(getList());
	}

	private ObservableList<Factura> getList() {
		List<Factura> listaFacturi = new FacturaController().selectAll();
		ObservableList<Factura> list = FXCollections.observableArrayList();

		Factura f = new Factura();
		f.setNrdoc("2131");

		Firma firma = new Firma();
		firma.setDenumire("abC");

		f.setFirma(firma);
		f.setId(123);


		System.out.println(f.getFirma());
		list.add(f);
		return list;
	}

}
