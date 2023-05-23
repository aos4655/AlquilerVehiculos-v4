package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Autobus;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Furgoneta;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controles;
import org.iesalandalus.programacion.alquilervehiculos.vista.texto.TipoVehiculo;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class LeerVehiculo extends Controlador {
	private boolean cancelado;

	@FXML
	private ComboBox<TipoVehiculo> cbTipo;

	@FXML
	private TextField tfCilindrada;

	@FXML
	private TextField tfMarca;

	@FXML
	private TextField tfMatricula;

	@FXML
	private TextField tfModelo;

	@FXML
	private TextField tfPlazas;

	@FXML
	private TextField tfPma;

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
		tfMarca.textProperty().addListener((ob, ov, nv) -> Controles.validarCampoTexto(Vehiculo.ER_MARCA, tfMarca));
		tfMatricula.textProperty()
				.addListener((ob, ov, nv) -> Controles.validarCampoTexto(Vehiculo.ER_MATRICULA, tfMatricula));
		cbTipo.setItems(FXCollections.observableArrayList(TipoVehiculo.values()));

	}

	public void limpiar() {
		tfMarca.setText("");
		tfMatricula.setText("");
		tfModelo.setText("");
		tfCilindrada.setText("");
		tfPlazas.setText("");
		tfPma.setText("");
		cancelado = true;
	}

	public Vehiculo getVehiculo() {
		String marca = tfMarca.getText();
		String modelo = tfModelo.getText();
		String matricula = tfMatricula.getText();
		TipoVehiculo tipo = cbTipo.getValue();
		Vehiculo vehiculo = null;
		if (tipo != null) {
			if (tipo.equals(TipoVehiculo.TURISMO)) {
				String cilindrada = tfCilindrada.getText();
				vehiculo = new Turismo(marca, modelo, Integer.parseInt(cilindrada), matricula);
			}
			else if (tipo.equals(TipoVehiculo.AUTOBUS)) {
				String plazas = tfPlazas.getText();
				vehiculo = new Autobus(marca, modelo, Integer.parseInt(plazas), matricula);
			}
			else if (tipo.equals(TipoVehiculo.FURGONETA)) {
				String plazas = tfPlazas.getText();
				String pma = tfPma.getText();
				vehiculo = new Furgoneta(marca, modelo, Integer.parseInt(pma), Integer.parseInt(plazas),matricula);
			}
		}
		
		return vehiculo;
	}

	@FXML
	void elegirTipo() {
		if (cbTipo.getValue().equals(TipoVehiculo.TURISMO)) {
			tfPlazas.setDisable(true);
			tfPma.setDisable(true);
			tfCilindrada.setDisable(false);

		}
		else if (cbTipo.getValue().equals(TipoVehiculo.AUTOBUS)) {
			tfCilindrada.setDisable(true);
			tfPma.setDisable(true);
			tfPlazas.setDisable(false);
		}
		else {
			tfCilindrada.setDisable(true);
			tfPlazas.setDisable(false);
			tfPma.setDisable(false);
		}
	}
}
