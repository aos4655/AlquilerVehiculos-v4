package org.iesalandalus.programacion.alquilervehiculos.vista.texto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Autobus;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Furgoneta;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.utilidades.Entrada;

public class Consola {
	private static final String PATRON_FECHA = "dd/MM/yyyy";
	private static final String PATRON_MES = "MM/yyyy";
	private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern(PATRON_FECHA);

	private Consola() {

	}

	public static void mostrarCabecera(String mensaje) {
		StringBuilder cadena = new StringBuilder();
		for (int i = 0; i < mensaje.length(); i++) {
		    cadena.append("-");
		}
		System.out.println(mensaje);
		System.out.print(cadena.toString());
	}

	public static void mostrarMenuAcciones() {
		mostrarCabecera("Gestion Alquileres");
		System.out.println("Menu de opciones");
		for (int i = 0; i < Accion.values().length; i++) {
			System.out.println(Accion.values()[i]);
		}

	}

	private static String leerCadena(String mensaje) {
		System.out.print(mensaje);
		return Entrada.cadena();
	}

	private static Integer leerEntero(String mensaje) {
		System.out.print(mensaje);
		return Entrada.entero();
	}

	private static LocalDate leerFecha(String mensaje, String patron) {
		LocalDate fechaLeida = null;
		try {
			String cadenaLeida = leerCadena(mensaje + " (" + patron + ") ");
			if (patron.equals(PATRON_MES)) {
				cadenaLeida = "01/" + cadenaLeida;
			}
			fechaLeida = LocalDate.parse(cadenaLeida, FORMATO_FECHA);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return fechaLeida;
	}

	public static Accion elegirAccion() {
		Accion op = null;
		do {
			try {
				op = Accion.get(leerEntero("Elije una opcion: "));
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}while(op == null);
		return op;
	}

	public static Cliente leerCliente() {
		return new Cliente(leerNombre(), leerCadena("Introduce un dni: "), leerTelefono());
	}

	public static Cliente leerClienteDni() {
		return Cliente.getClienteConDni(leerCadena("Introduce dni del cliente: "));
	}

	public static String leerNombre() {
		return leerCadena("Introduce nombre del cliente: ");
	}

	public static String leerTelefono() {
		return leerCadena("Introduce telefono del cliente: ");
	}

	public static Vehiculo leerVehiculo() {
		mostrarMenuTiposVehiculos();
		return leerVehiculo(elegirTipoVehiculo());
	}

	private static void mostrarMenuTiposVehiculos() {
		mostrarCabecera("Eleccion tipo de vehiculo");
		for (TipoVehiculo tipoVehiculo : TipoVehiculo.values()) {
			System.out.printf("%d - %s",tipoVehiculo.ordinal(), tipoVehiculo);
		}
	}

	private static TipoVehiculo elegirTipoVehiculo() {
		TipoVehiculo tipoVehiculo = null;
		do {
			try {
				tipoVehiculo = TipoVehiculo.get(leerEntero("Elije una opcion: "));
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}while(tipoVehiculo == null);
		return tipoVehiculo;
	}

	private static Vehiculo leerVehiculo(TipoVehiculo tipoVehiculo) {
		Vehiculo vehiculo = null;
		String marca = leerCadena("Introduce la marca: ");
		String modelo = leerCadena("Introduce el modelo: ");
		String matricula = leerCadena("Introduce la matricula: ");

		switch (tipoVehiculo) {
			case TURISMO: {
				vehiculo = new Turismo(marca, modelo,
						leerEntero("Introduce la cilindrada: "), matricula);
				break;
			}
			case AUTOBUS: {
				vehiculo = new Autobus(marca, modelo,
						leerEntero("Introduce el numero de plazas: "), matricula);
				break;
			}
			case FURGONETA: {
				vehiculo = new Furgoneta(marca, modelo,
						leerEntero("Introduce el pma: "),leerEntero("Introduce el numero de plazas: "), matricula);
				break;
			}
		}
		return vehiculo;
	}

	public static Vehiculo leerVehiculoMatricula() {
		return Vehiculo.getVehiculoConMatricula(leerCadena("Introduce la matricula del vehiculo: "));
	}

	public static Alquiler leerAlquiler() {
		return new Alquiler(leerClienteDni(), leerVehiculoMatricula(), leerFecha("Introduce una fecha de Alquiler", PATRON_FECHA));
	}

	public static LocalDate leerFechaDevolucion() {
		return leerFecha("Introduce la fecha de devolucion: ", PATRON_FECHA);
	}

	public static LocalDate leerMes() {
		return leerFecha("Introduce la fecha para obtener el mes: ", PATRON_MES);
	}
}
