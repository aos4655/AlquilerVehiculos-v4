package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controladores;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Dialogos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Borrar extends Controlador {

	private boolean cancelado;

	void borrarCliente() {
		cancelado = false;
		getEscenario().close();
	}

	void borrarVehiculo() {
		cancelado = false;
		getEscenario().close();
	}

	void borrarAlquiler() {
		cancelado = false;
		try {
			if (cbSelector.getValue() != null) {
				VistaGrafica.getInstancia().getControlador().borrar((Alquiler) cbSelector.getValue());
				limpiar();
				Dialogos.mostrarDialogoAdvertencia("Eliminar alquiler", "Alquiler eliminado correctamente",
						getEscenario());
			}
		} catch (Exception e) {
			Dialogos.mostrarDialogoError("Eliminar Alquiler", e.getMessage(), getEscenario());
		}
	}

	public void actualizarCliente(List<Cliente> clientes) {
		lbDatos.setText("Borrar Cliente");
		lbTitulo.setText("Selecciona");
		getEscenario().setTitle("Eliminar Cliente");
		cbSelector.setItems(FXCollections.observableArrayList(clientes));
		btBorrar.setOnAction(event -> {
			borrarCliente();
		});

	}

	public void actualizarVehiculo(List<Vehiculo> vehiculos) {
		lbDatos.setText("Borrar vehiculo");
		lbTitulo.setText("Selecciona");
		getEscenario().setTitle("Eliminar Vehiculo");
		cbSelector.setItems(
				FXCollections.observableArrayList(vehiculos));
		btBorrar.setOnAction(event -> {
			borrarVehiculo();
		});

	}

	public void actualizarAlquiler(List<Alquiler> alquileres) {
		lbTitulo.setText("Borrar Alquiler");
		lbDatos.setText("Selecciona");
		getEscenario().setTitle("Eliminar Alquiler");
		cbSelector.setItems(
				FXCollections.observableArrayList(alquileres));
		btBorrar.setOnAction(event -> {
			borrarAlquiler();
		});
	}

	void limpiar() {
		cbSelector.getSelectionModel().clearSelection();
	}

	@FXML
	private Button btBorrar;

	@FXML
	private ComboBox<Object> cbSelector;

	@FXML
	private Label lbDatos;

	@FXML
	private Label lbTitulo;

	@FXML
	void cancelar() {
		cancelado = true;
		getEscenario().close();
	}

	public Object getSelector() {
		return cbSelector.getValue();
	}
}
