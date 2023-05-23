package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import java.time.LocalDate;
import java.util.List;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

public class DevolverAlquiler extends Controlador {

	private boolean cancelado;

	@FXML
	private ComboBox<Object> cbDato;

	@FXML
	private DatePicker dpFechaDevolucion;

	@FXML
	private Label lbDato;

	@FXML
	private Label lbTitulo;

	@FXML
	void aceptar() {
		cancelado = false;
		getEscenario().close();
	}

	@FXML
	void cancelar() {
		cancelado = true;
		getEscenario().close();
	}

	public void actualizarAlquiler(List<Alquiler> alquileres) {
		lbTitulo.setText("Devolver Alquiler");
		lbDato.setText("Alquileres");
		cbDato.setItems(FXCollections.observableArrayList(alquileres));
	}
	public void actualizarCliente(List<Cliente> clientes) {
		lbTitulo.setText("Devolver Alquiler Cliente");
		lbDato.setText("Clientes");
		cbDato.setItems(FXCollections.observableArrayList(clientes));
	}
	public void actualizarVehiculo(List<Vehiculo> vehiculos) {
		lbTitulo.setText("Devolver Alquiler Vehiculo");
		lbDato.setText("Vehiculos");
		cbDato.setItems(FXCollections.observableArrayList(vehiculos));
	}

	public void limpiar() {
		cbDato.setValue(null);
		// dpFechaDevolucion.setValue(null);
		cancelado = true;
	}

	public Alquiler getAlquiler() {
		return cancelado ? null : (Alquiler) cbDato.getValue();
	}
	public Cliente getCliente() {
		return cancelado ? null : (Cliente) cbDato.getValue();
	}
	public Vehiculo getVehiculo() {
		return cancelado ? null : (Vehiculo) cbDato.getValue();
	}

	public LocalDate getFechaDevolucion() {
		return cancelado ? null : dpFechaDevolucion.getValue();
	}
}
