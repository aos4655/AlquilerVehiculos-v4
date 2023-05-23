package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.recursos.LocalizadorRecursos;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controladores;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Dialogos;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class VentanaVehiculo extends Controlador {
	@FXML
	private ImageView ivVehiculos;

	@FXML
	void devolverAlquiler() {
		DevolverAlquiler devolverAlquiler = (DevolverAlquiler) Controladores.get("vistas/DevolverAlquiler.fxml",
				"Devolver Alquiler", getEscenario());
		devolverAlquiler.limpiar();
		List<Vehiculo> vehiculoConAlquilerAbierto = new ArrayList<>();
		for (Vehiculo vehiculo : VistaGrafica.getInstancia().getControlador().getVehiculos()) {
			if (VistaGrafica.getInstancia().getControlador().getAlquileres(vehiculo) != null) {
				for (Alquiler alquiler : VistaGrafica.getInstancia().getControlador().getAlquileres(vehiculo)) {
					if (alquiler.getFechaDevolucion() == null) {
						vehiculoConAlquilerAbierto.add(vehiculo);
						break;
					}
				}
			}
		}
		if (!vehiculoConAlquilerAbierto.isEmpty()) {
			devolverAlquiler.actualizarVehiculo(vehiculoConAlquilerAbierto);
			devolverAlquiler.getEscenario().showAndWait();

			try {
				Vehiculo vehiculo = devolverAlquiler.getVehiculo();
				LocalDate fechaDev = devolverAlquiler.getFechaDevolucion();
				if (vehiculo != null) {
					VistaGrafica.getInstancia().getControlador().devolver(vehiculo, fechaDev);
					Dialogos.mostrarDialogoAdvertencia("Devolver alquiler vehiculo",
							"Alquiler del vehiculo devuelto correctamente", getEscenario());
				}
			} catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
				Dialogos.mostrarDialogoError("Devolver alquiler vehiculo", e.getMessage(), getEscenario());
			}
		} else {
			Dialogos.mostrarDialogoError("Devolver Alquiler Vehiculo",
					"No hay vehiculos con alquileres abiertos para devolver", getEscenario());
		}
	}

	@FXML
	void insertar() {
		LeerVehiculo leerVehiculo = (LeerVehiculo) Controladores.get("vistas/LeerVehiculo.fxml", "Leer Vehiculo",
				getEscenario());
		leerVehiculo.limpiar();
		leerVehiculo.getEscenario().showAndWait();

		try {
			Vehiculo vehiculo = leerVehiculo.getVehiculo();
			if (vehiculo != null) {
				VistaGrafica.getInstancia().getControlador().insertar(vehiculo);
				Dialogos.mostrarDialogoAdvertencia("Insertar Vehiculo", "Vehiculo insertado correctamente",
						getEscenario());
			}
		} catch (OperationNotSupportedException | IllegalArgumentException | NullPointerException e) {
			System.out.println("He entrado aqui");
			Dialogos.mostrarDialogoError("Insertar Vehiculo", e.getMessage(), getEscenario());
		}
	}

	@FXML
	void listar() {
		ListarVehiculos listarVehiculos = (ListarVehiculos) Controladores.get("vistas/ListarVehiculos.fxml",
				"Listar Vehiculos", getEscenario());
		listarVehiculos.actualizar(VistaGrafica.getInstancia().getControlador().getVehiculos());
		listarVehiculos.getEscenario().showAndWait();
	}

	@FXML
	void buscar() {
		Buscar buscar = (Buscar) Controladores.get("vistas/Buscar.fxml", "", getEscenario());
		try {
			buscar.actualizarVehiculo();
			buscar.getEscenario().showAndWait();
		} catch (Exception e) {
			Dialogos.mostrarDialogoError("Buscar Vehiculo", e.getMessage(), getEscenario());
		}

	}

	@FXML
	void borrar() {
		Borrar borrar = (Borrar) Controladores.get("vistas/Borrar.fxml", "", getEscenario());
		borrar.getEscenario().setResizable(false);
		borrar.actualizarVehiculo(VistaGrafica.getInstancia().getControlador().getVehiculos());
		borrar.getEscenario().showAndWait();
		try {
			if (borrar.getSelector() != null) {
				VistaGrafica.getInstancia().getControlador().borrar((Vehiculo) borrar.getSelector());
				Dialogos.mostrarDialogoAdvertencia("Eliminar vehiculo", "Vehiculo eliminado correctamente",
						getEscenario());
			}
			borrar.limpiar();

		} catch (Exception e) {
			Dialogos.mostrarDialogoError("Eliminar Vehiculo", e.getMessage(), getEscenario());
		}
	}

	@FXML
	private void initialize() {
		ivVehiculos.setImage(new Image(LocalizadorRecursos.class.getResourceAsStream("imagenes/coche.png")));
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
