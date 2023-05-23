package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import java.time.LocalDate;
import java.util.List;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controles.FormateadorCeldaFecha;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ListarAlquileres extends Controlador {
	@FXML
	private TableColumn<Alquiler, String> tcDni;

	@FXML
	private TableColumn<Alquiler, LocalDate> tcFechaA;

	@FXML
	private TableColumn<Alquiler, LocalDate> tcFechaD;

	@FXML
	private TableColumn<Alquiler, String> tcMatricula;

	@FXML
	private TableView<Alquiler> tvAlquileres;

	@FXML
	void aceptar() {
		getEscenario().close();
	}

	public void actualizar(List<Alquiler> alquileres) {
		tvAlquileres.setItems(FXCollections.observableArrayList(alquileres));
	}

	@FXML
	void initialize() {
		tcDni.setCellValueFactory(fila -> new SimpleStringProperty(fila.getValue().getCliente().getDni()));
		tcMatricula.setCellValueFactory(fila -> new SimpleStringProperty(fila.getValue().getVehiculo().getMatricula()));
		tcFechaA.setCellValueFactory(new PropertyValueFactory<>("fechaAlquiler"));
		tcFechaA.setCellFactory(columna -> new FormateadorCeldaFecha());
		tcFechaD.setCellValueFactory(fila -> new SimpleObjectProperty<>(
				fila.getValue().getFechaDevolucion()));
		tcFechaD.setCellFactory(columna -> new FormateadorCeldaFecha());
	}
}
