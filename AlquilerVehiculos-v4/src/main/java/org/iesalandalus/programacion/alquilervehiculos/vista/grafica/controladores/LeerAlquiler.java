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

public class LeerAlquiler extends Controlador {
	private boolean cancelado;

    @FXML
    private DatePicker dpFecha;
	@FXML
    private ComboBox<Cliente> cbCliente;

    @FXML
    private ComboBox<Vehiculo> cbVehiculo;

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

	@FXML
	void initialize() {
		
	}
	public void actualizar(List<Cliente> clientes, List<Vehiculo> vehiculos) {
		cbCliente.setItems(FXCollections.observableArrayList(clientes));
		cbVehiculo.setItems(FXCollections.observableArrayList(vehiculos));

	}

	public void limpiar() {
		cbCliente.setValue(null);
		cbVehiculo.setValue(null);
		dpFecha.setValue(null);
		cancelado = true;
	}

	public Alquiler getAlquiler() {
		Cliente cliente = cbCliente.getValue();
		Vehiculo vehiculo = cbVehiculo.getValue();
		Alquiler alquiler = null;
		if (cliente != null && vehiculo != null) {
			alquiler = new Alquiler(cliente, vehiculo, dpFecha.getValue());
		}
		return alquiler;
	}


	
}
