package org.iesalandalus.programacion.alquilervehiculos.vista.grafica.controladores;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.Map;
import java.util.Map.Entry;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Controlador;
import org.iesalandalus.programacion.alquilervehiculos.vista.grafica.utilidades.Dialogos;
import org.iesalandalus.programacion.alquilervehiculos.vista.texto.TipoVehiculo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.DatePicker;

public class Estadisticas extends Controlador {

	@FXML
	private PieChart estadistico;
	@FXML
	private DatePicker dpMes;

	@FXML
	void initialize() {

	}

	public void actualizar() {
		Map<TipoVehiculo, Integer> mapaInicializado = new EnumMap<>(TipoVehiculo.class);
		for (Alquiler alquiler : VistaGrafica.getInstancia().getControlador().getAlquileres()) {
			LocalDate fechaAlquiler = alquiler.getFechaAlquiler();
			if (fechaAlquiler.getMonth().equals(dpMes.getValue().getMonth())
					&& fechaAlquiler.getYear() == dpMes.getValue().getYear()) {
				TipoVehiculo tipoVehiculo = TipoVehiculo.getVehiculo(alquiler.getVehiculo());
				mapaInicializado.put(tipoVehiculo, mapaInicializado.getOrDefault(tipoVehiculo, 0) + 1);
			}
		}
		ObservableList<PieChart.Data> listaDatos = FXCollections.observableArrayList();
		for (Entry<TipoVehiculo, Integer> entry : mapaInicializado.entrySet()) {
			PieChart.Data data = new PieChart.Data(entry.getKey().toString(), entry.getValue());
			listaDatos.add(data);

		}
		if (listaDatos.isEmpty()) {
			Dialogos.mostrarDialogoAdvertencia("Mostrar estadisticas mensuales",
					"El mes seleccionado no tiene ningun alquiler", getEscenario());
		}
		
		estadistico.setData(listaDatos);
		
	}
	
	@FXML
	void cambiarEstadisticas() {
		actualizar();
	}
}
