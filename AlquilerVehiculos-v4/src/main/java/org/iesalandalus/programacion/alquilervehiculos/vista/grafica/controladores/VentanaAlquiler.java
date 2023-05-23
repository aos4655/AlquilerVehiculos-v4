package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.recursos.LocalizadorRecursos;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controladores;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Dialogos;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class VentanaAlquiler extends Controlador{
	@FXML
	private ImageView ivAlquileres;

	@FXML
	void devolverAlquiler() {
		DevolverAlquiler devolverAlquiler = (DevolverAlquiler) Controladores.get("vistas/DevolverAlquiler.fxml",
				"Devolver Alquiler", getEscenario());
		devolverAlquiler.limpiar();

		List<Alquiler> alquileresSinDevolver = new ArrayList<>();
		for (Alquiler alquiler : VistaGrafica.getInstancia().getControlador().getAlquileres()) {
			if (alquiler.getFechaDevolucion() == null) {
				alquileresSinDevolver.add(alquiler);
			}
		}
		if (!alquileresSinDevolver.isEmpty()) {
			devolverAlquiler.actualizarAlquiler(alquileresSinDevolver);
			devolverAlquiler.getEscenario().showAndWait();
			try {
				Alquiler alquiler = devolverAlquiler.getAlquiler();
				LocalDate fechaDev = devolverAlquiler.getFechaDevolucion();
				if (alquiler != null) {
					VistaGrafica.getInstancia().getControlador().devolver(alquiler.getCliente(), fechaDev);
					Dialogos.mostrarDialogoAdvertencia("Devolver alquiler", "Alquiler devuelto correctamente",
							getEscenario());
				}
			} catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
				Dialogos.mostrarDialogoError("Devolver alquiler", e.getMessage(), getEscenario());
			}
		}
		else {
			Dialogos.mostrarDialogoError("Devolver Alquiler", "No hay alquileres abiertos para devolver", getEscenario());
		}
	}

	@FXML
	void insertar() {
		LeerAlquiler leerAlquiler = (LeerAlquiler) Controladores.get("vistas/LeerAlquiler.fxml", "Leer Alquiler",
				getEscenario());
		leerAlquiler.limpiar();
		leerAlquiler.actualizar(FXCollections.observableArrayList(VistaGrafica.getInstancia().getControlador().getClientes()), FXCollections.observableArrayList(VistaGrafica.getInstancia().getControlador().getVehiculos()));
		leerAlquiler.getEscenario().showAndWait();
		try {
			Alquiler alquiler = leerAlquiler.getAlquiler();
			if (alquiler != null) {
				VistaGrafica.getInstancia().getControlador().insertar(alquiler);
				Dialogos.mostrarDialogoAdvertencia("Insertar alquiler", "Alquiler insertado correctamente",
						getEscenario());
			}
		} catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
			Dialogos.mostrarDialogoError("Insertar Alquiler", e.getMessage(), getEscenario());
		}
	}

	@FXML
	void listar() {
		ListarAlquileres listarAlquileres = (ListarAlquileres) Controladores.get("vistas/ListarAlquileres.fxml",
				"Listar Alquileres", getEscenario());
		listarAlquileres.actualizar(VistaGrafica.getInstancia().getControlador().getAlquileres());
		listarAlquileres.getEscenario().showAndWait();
	}

	@FXML
	void buscar() {
		Buscar buscar = (Buscar) Controladores.get("vistas/Buscar.fxml",
				"", getEscenario());
		try {
			buscar.actualizarAlquiler();
			buscar.getEscenario().showAndWait();
		} catch (Exception e) {
			Dialogos.mostrarDialogoError("Buscar Alquiler", e.getMessage(), getEscenario());
		}
		
	}
	@FXML
	void borrar() {
		Borrar borrar = (Borrar) Controladores.get("vistas/Borrar.fxml", "", getEscenario());
		borrar.actualizarAlquiler(VistaGrafica.getInstancia().getControlador().getAlquileres());
		borrar.getEscenario().showAndWait();
		try {
			VistaGrafica.getInstancia().getControlador().borrar((Alquiler) borrar.getSelector());
			borrar.limpiar();
			Dialogos.mostrarDialogoAdvertencia("Eliminar Alquiler", "Alquiler eliminado correctamente", getEscenario());
		} catch (Exception e) {
			Dialogos.mostrarDialogoError("Eliminar Alquiler", e.getMessage(), getEscenario());
		}
	}
	@FXML
	private void initialize() {
		ivAlquileres.setImage(new Image(LocalizadorRecursos.class.getResourceAsStream("imagenes/alquilerVehiculo.png")));
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
		Estadisticas estadisticas = (Estadisticas) Controladores.get("vistas/Estadisticas.fxml", "Estadisticas Mensuales", getEscenario());
		estadisticas.getEscenario().showAndWait();
	}
}
	
