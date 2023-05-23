package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controles;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LeerCliente extends Controlador{
	@FXML private TextField tfDni;

	@FXML private TextField tfNombre;

	@FXML private TextField tfTelefono;

	private boolean cancelado;
	
	@FXML
	void initialize() {
		tfNombre.textProperty().addListener((ob,ov,nv) -> Controles.validarCampoTexto(Cliente.ER_NOMBRE, tfNombre));
		tfDni.textProperty().addListener((ob,ov,nv) -> Controles.validarCampoTexto(Cliente.ER_DNI, tfDni));
		tfTelefono.textProperty().addListener((ob,ov,nv) -> Controles.validarCampoTexto(Cliente.ER_TELEFONO, tfTelefono));

	}
	
	public void limpiar() {
		tfNombre.setText("");
		tfDni.setText("");
		tfTelefono.setText("");
		cancelado = true;
	}
	
	public Cliente getCliente() {
		String nombre = tfNombre.getText();
		String dni = tfDni.getText();
		String telefono = tfTelefono.getText();

		return cancelado ? null : new Cliente(nombre, dni, telefono);
	}
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
}
