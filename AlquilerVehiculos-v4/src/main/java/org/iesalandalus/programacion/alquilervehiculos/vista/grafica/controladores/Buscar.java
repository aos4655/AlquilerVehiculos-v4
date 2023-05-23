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
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Buscar extends Controlador {

	private boolean cancelado;

	void buscarCliente() {
		cancelado = false;
		ListarClientes listarClientes = (ListarClientes) Controladores.get("vistas/ListarClientes.fxml",
				"Listar Cliente Buscado", getEscenario());
		try {
			List<Cliente> cliente = new ArrayList<>();
			cliente.add(
					VistaGrafica.getInstancia().getControlador().buscar(Cliente.getClienteConDni(tfObjeto.getText())));
			if (!cliente.isEmpty()) {
				listarClientes.actualizar(cliente);
				listarClientes.getEscenario().showAndWait();
			}
			else {
				Dialogos.mostrarDialogoError("Buscar Cliente", "El cliente no existe en la base de datos", getEscenario());
			}
			
		} catch (Exception e) {
			Dialogos.mostrarDialogoError("Buscar Cliente", e.getMessage(), getEscenario());
		}

	}

	void buscarVehiculo() {
		cancelado = false;
		ListarVehiculos listarVehiculos = (ListarVehiculos) Controladores.get("vistas/ListarVehiculos.fxml",
				"Listar Vehiculo Buscado", getEscenario());
		try {
			List<Vehiculo> vehiculo = new ArrayList<>();
			vehiculo.add(VistaGrafica.getInstancia().getControlador()
					.buscar(Vehiculo.getVehiculoConMatricula(tfObjeto.getText())));
			if (!vehiculo.isEmpty()) {
				listarVehiculos.actualizar(vehiculo);
				listarVehiculos.getEscenario().showAndWait();
			}
			else {
				Dialogos.mostrarDialogoError("Buscar Vehiculo", "El vehiculo no existe en la base de datos", getEscenario());
			}
		} catch (Exception e) {
			Dialogos.mostrarDialogoError("Buscar Vehiculo", e.getMessage(), getEscenario());
		}
		
	}

	void buscarAlquiler() {
		cancelado = false;
		ListarAlquileres listarAlquileres = (ListarAlquileres) Controladores.get("vistas/ListarAlquileres.fxml",
				"Listar Alquileres Buscado", getEscenario());
		try {
			List<Alquiler> alquiler = new ArrayList<>();
			if (cbCliente.getValue() != null) {
				List<Alquiler> alquilerCliente = seleccionarAlquiler(
						VistaGrafica.getInstancia().getControlador().getAlquileres(cbCliente.getValue()),
						dpFecha.getValue());
				alquiler.addAll(alquilerCliente);
			} else if (cbVehiculo.getValue() != null) {
				List<Alquiler> alquilerVehiculo = seleccionarAlquiler(
						VistaGrafica.getInstancia().getControlador().getAlquileres(cbVehiculo.getValue()),
						dpFecha.getValue());
				alquiler.addAll(alquilerVehiculo);
			}
			if (!alquiler.isEmpty()) {
				listarAlquileres.actualizar(alquiler);
				listarAlquileres.getEscenario().showAndWait();
			}
			else {
				Dialogos.mostrarDialogoError("Buscar Alquiler", "No existe ese alquiler en la base de datos", getEscenario());
			}
			
		} catch (Exception e) {
			Dialogos.mostrarDialogoError("Buscar alquiler", e.getMessage(), getEscenario());
		}
		
	}

	private List<Alquiler> seleccionarAlquiler(List<Alquiler> alquileres, LocalDate fecha) {
		List<Alquiler> lista = new ArrayList<>();
		for (Alquiler alquiler : alquileres) {
			if (alquiler.getFechaAlquiler().equals(fecha)) {
				lista.add(alquiler);
			}
		}
		return lista;
	}

	public void actualizarCliente() {
		lbDatos.setText("Introduce Dni");
		lbTitulo.setText("Buscar Cliente");
		hbAlquiler.setVisible(false);
		getEscenario().setTitle("Buscador Cliente");
		hbAlquiler.toFront();
		vbVentana.setPrefHeight(150);
		btBuscar.setOnAction(event -> {
			buscarCliente();
		});
	}

	public void actualizarVehiculo() {
		lbDatos.setText("Introduce Matricula");
		lbTitulo.setText("Buscar Vehiculo");
		hbAlquiler.setVisible(false);
		getEscenario().setTitle("Buscador Vehiculo");
		hbAlquiler.toFront();
		vbVentana.setPrefHeight(150);
		btBuscar.setOnAction(event -> {
			buscarVehiculo();
		});
	}

	public void actualizarAlquiler() {
		hbCliVe.setVisible(false);
		lbTitulo.setText("Buscar Alquiler");
		lbDatos2.setText("Selecciona");
		getEscenario().setTitle("Buscador Vehiculo");
		cbCliente.setItems(
				FXCollections.observableArrayList(VistaGrafica.getInstancia().getControlador().getClientes()));
		cbVehiculo.setItems(
				FXCollections.observableArrayList(VistaGrafica.getInstancia().getControlador().getVehiculos()));
		hbCliVe.toFront();
		vbVentana.setPrefHeight(210);
		btBuscar.setOnAction(event -> {
			buscarAlquiler();
		});
	}

	@FXML
	private VBox vbVentana;

	@FXML
	private TextField tfObjeto;
	@FXML
	private Label lbDatos;
	@FXML
	private Label lbDatos2;
	@FXML
	private HBox hbAlquiler;
	@FXML
	private Label lbTitulo;
	@FXML
	private HBox hbCliVe;
	@FXML
	private ComboBox<Cliente> cbCliente;

	@FXML
	private ComboBox<Vehiculo> cbVehiculo;
	@FXML
	private Button btBuscar;
	@FXML
	private DatePicker dpFecha;

	@FXML
	void elijoCliente() {
		if (cbVehiculo.getValue() != null && cbCliente.getValue() != null) {
			cbVehiculo.getSelectionModel().clearSelection();
		}
	}

	@FXML
	void elijoVehiculo() {
		if (cbCliente.getValue() != null && cbVehiculo.getValue() != null) {
			cbCliente.getSelectionModel().clearSelection();
		}
	}

	@FXML
	void cancelar() {
		cancelado = true;
		getEscenario().close();
	}

}
