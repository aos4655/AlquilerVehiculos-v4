package org.iesalandalus.programacion.alquilervehiculos.vista.texto;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Autobus;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Furgoneta;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;

public enum TipoVehiculo {

	TURISMO("Turismo"),
	AUTOBUS("Autobus"),
	FURGONETA("Furgoneta");
	
	private String cadenaAMostrar;

	private static boolean esOrdinalValido(int ordinal) {	
		return ordinal < Accion.values().length && ordinal >= 0;
	}
	public static TipoVehiculo get(int ordinal) {
		TipoVehiculo vehiculo = null;
		if(esOrdinalValido(ordinal)) {
			vehiculo = TipoVehiculo.values()[ordinal];
		}
		else {
			throw new NullPointerException("Error: El ordinal pasado no es valido");
		}
		return vehiculo;
	}
	public static TipoVehiculo getVehiculo(Vehiculo vehiculo) {
		TipoVehiculo vehiculoDevolver = null;
		if (vehiculo instanceof Turismo) {
			vehiculoDevolver = TipoVehiculo.TURISMO;
		}
		if (vehiculo instanceof Furgoneta) {
			vehiculoDevolver = TipoVehiculo.FURGONETA;
		}
		if (vehiculo instanceof Autobus) {
			vehiculoDevolver = TipoVehiculo.AUTOBUS;
		}
		return vehiculoDevolver;
	}
	private TipoVehiculo(String cadenaAMostrar) {
		this.cadenaAMostrar = cadenaAMostrar;
	}
	
	@Override
	public String toString() {
		return String.format("%s", cadenaAMostrar);
	}
}
