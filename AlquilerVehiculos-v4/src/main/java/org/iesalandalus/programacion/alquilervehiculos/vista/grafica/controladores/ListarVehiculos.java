package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import java.util.List;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Autobus;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Furgoneta;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ListarVehiculos extends Controlador {
	@FXML
	private TableColumn<Vehiculo, String> tcCilindrada;

	@FXML
	private TableColumn<Vehiculo, String> tcMarca;

	@FXML
	private TableColumn<Vehiculo, String> tcMatricula;

	@FXML
	private TableColumn<Vehiculo, String> tcModelo;

	@FXML
	private TableColumn<Vehiculo, String> tcPlazas;

	@FXML
	private TableColumn<Vehiculo, String> tcPma;

	@FXML
	private TableView<Vehiculo> tvVehiculos;

	@FXML
	void initialize() {
		tcMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
		tcMatricula.setCellValueFactory(fila -> new SimpleStringProperty(fila.getValue().getMatricula()));
		tcModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));

		tcPlazas.setCellValueFactory(fila -> new SimpleStringProperty(
				fila.getValue() instanceof Furgoneta furgoneta ? Integer.toString(furgoneta.getPlazas()) : fila.getValue() 
						instanceof Autobus autobus ? Integer.toString(autobus.getPlazas()) : ""));
		
		tcPma.setCellValueFactory(fila -> new SimpleStringProperty(fila.getValue() instanceof 
				Furgoneta furgoneta ?  Integer.toString(furgoneta.getPma()) : "" ));
		
		tcCilindrada.setCellValueFactory(fila -> new SimpleStringProperty(fila.getValue() instanceof 
				Turismo turismo ?  Integer.toString(turismo.getCilindrada()) : "" ));
	}

	public void actualizar(List<Vehiculo> vehiculos) {
		tvVehiculos.setItems(FXCollections.observableArrayList(vehiculos));
	}
	@FXML
	void aceptar() {
		getEscenario().close();
	}
}
