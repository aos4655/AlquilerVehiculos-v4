package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.recursos.LocalizadorRecursos;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controladores;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Dialogos;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class VentanaCliente extends Controlador {
	@FXML
	private ImageView ivClientes;

	@FXML
	void devolverAlquiler() {
		DevolverAlquiler devolverAlquiler = (DevolverAlquiler) Controladores.get("vistas/DevolverAlquiler.fxml",
				"Devolver Alquiler", getEscenario());
		devolverAlquiler.limpiar();
		List<Cliente> clienteConAlquilerAbierto = new ArrayList<>();
		for (Cliente cliente : VistaGrafica.getInstancia().getControlador().getClientes()) {
			if (VistaGrafica.getInstancia().getControlador().getAlquileres(cliente) != null) {
				for (Alquiler alquiler : VistaGrafica.getInstancia().getControlador().getAlquileres(cliente)) {
					if (alquiler.getFechaDevolucion() == null) {
						clienteConAlquilerAbierto.add(cliente);
						break;
					}
				}
			}
		}
		if (!clienteConAlquilerAbierto.isEmpty()) {
			devolverAlquiler.actualizarCliente(clienteConAlquilerAbierto);
			devolverAlquiler.getEscenario().showAndWait();

			try {
				Cliente cliente = devolverAlquiler.getCliente();
				LocalDate fechaDev = devolverAlquiler.getFechaDevolucion();
				if (cliente != null) {
					VistaGrafica.getInstancia().getControlador().devolver(cliente, fechaDev);
					Dialogos.mostrarDialogoAdvertencia("Devolver alquiler cliente",
							"Alquiler del cliente devuelto correctamente", getEscenario());
				}
			} catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
				Dialogos.mostrarDialogoError("Devolver Alquiler Cliente", e.getMessage(), getEscenario());
			}
		} else {
			Dialogos.mostrarDialogoError("Devolver Alquiler Clientes",
					"No hay clientes con alquileres abiertos para devolver", getEscenario());
		}
	}

	@FXML
	void insertar() {
		LeerCliente leerCliente = (LeerCliente) Controladores.get("vistas/LeerCliente.fxml", "Leer Cliente",
				getEscenario());
		leerCliente.limpiar();
		leerCliente.getEscenario().showAndWait();
		try {
			Cliente cliente = leerCliente.getCliente();
			if (cliente != null) {
				VistaGrafica.getInstancia().getControlador().insertar(cliente);
				Dialogos.mostrarDialogoAdvertencia("Insertar cliente", "Cliente insertado correctamente",
						getEscenario());
			}
		} catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
			Dialogos.mostrarDialogoError("Insertar Cliente", e.getMessage(), getEscenario());
		}
	}

	@FXML
	void listar() {
		ListarClientes listarClientes = (ListarClientes) Controladores.get("vistas/ListarClientes.fxml",
				"ListarClientes", getEscenario());
		listarClientes.actualizar(VistaGrafica.getInstancia().getControlador().getClientes());
		listarClientes.getEscenario().showAndWait();
	}

	@FXML
	void buscar() {
		Buscar buscar = (Buscar) Controladores.get("vistas/Buscar.fxml", "", getEscenario());
		try {
			buscar.actualizarCliente();
			buscar.getEscenario().showAndWait();
		} catch (Exception e) {
			Dialogos.mostrarDialogoError("Buscar Cliente", e.getMessage(), getEscenario());
		}

	}

	@FXML
	void modificar() {
		Modificar modificar = (Modificar) Controladores.get("vistas/Modificar.fxml", "", getEscenario());
		modificar.actualizar(
				FXCollections.observableArrayList(VistaGrafica.getInstancia().getControlador().getClientes()));
		modificar.getEscenario().showAndWait();
		try {
			if (modificar.getCliente() != null) {
				VistaGrafica.getInstancia().getControlador().modificar(modificar.getCliente(), modificar.getNombre(),
						modificar.getTelefono());
				Dialogos.mostrarDialogoAdvertencia("Modificar Cliente", "Cliente modificado correctamente",
						getEscenario());
			}
			modificar.limpiar();
		} catch (Exception e) {
			Dialogos.mostrarDialogoError("Modificar Cliente", e.getMessage(), getEscenario());
		}
	}

	@FXML
	void borrar() {
		Borrar borrar = (Borrar) Controladores.get("vistas/Borrar.fxml", "", getEscenario());
		borrar.actualizarCliente(VistaGrafica.getInstancia().getControlador().getClientes());
		borrar.limpiar();
		borrar.getEscenario().showAndWait();
		try {
			if (borrar.getSelector() != null) {
				VistaGrafica.getInstancia().getControlador().borrar((Cliente) borrar.getSelector());
				Dialogos.mostrarDialogoAdvertencia("Eliminar cliente", "Cliente eliminado correctamente",
						getEscenario());

			}
		} catch (Exception e) {
			Dialogos.mostrarDialogoError("Borrar Cliente", e.getMessage(), getEscenario());
		}
	}

	@FXML
	private void initialize() {
		ivClientes.setImage(new Image(LocalizadorRecursos.class.getResourceAsStream("imagenes/cliente.png")));
	}

	@FXML
	void acercaDe() {
		AcercaDe acercaDe = (AcercaDe) Controladores.get("vistas/AcercaDe.fxml", "Acerca De", getEscenario());
		acercaDe.getEscenario().showAndWait();
	}

	@FXML
	void confirmarSalida() {
		if (Dialogos.mostrarDialogoConfirmacion("Salir", "Estas seguro de que quieres salir de la aplicacion?",
				getEscenario())) {
			getEscenario().close();
		}
	}

	@FXML
	void mostrarEstadisticas() {
		Estadisticas estadisticas = (Estadisticas) Controladores.get("vistas/Estadisticas.fxml",
				"Estadisticas Mensuales", getEscenario());
		estadisticas.getEscenario().showAndWait();
	}
}
