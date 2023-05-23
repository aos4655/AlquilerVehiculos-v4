package org.iesalandalus.programacion.alquilervehiculos.vista.texto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Autobus;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Furgoneta;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.vista.Vista;

public class VistaTexto extends Vista {

	@Override
	public void comenzar() throws OperationNotSupportedException {
		Accion.setVista(this);
		Accion accionDeseada;
		do {
			Consola.mostrarMenuAcciones();
			accionDeseada = Consola.elegirAccion();
			accionDeseada.ejecutar();
		}
		while(!accionDeseada.equals(Accion.SALIR));
	}

	@Override
	public void terminar() {
		getControlador().terminar();
		System.out.println("Adios!");
	}

	public void insertarCliente() throws OperationNotSupportedException {
		Consola.mostrarCabecera("Insertar Cliente");
		try {
			getControlador().insertar(Consola.leerCliente());
			System.out.println("Cliente insertado correctamente");
		} catch (IllegalArgumentException | NullPointerException | OperationNotSupportedException e) {
			System.out.println(e.getMessage());
		}
	}

	public void insertarVehiculo() throws OperationNotSupportedException {
		try {
			Consola.mostrarCabecera("Insertar Vehiculo");
			Vehiculo vehiculoElejido = Consola.leerVehiculo();
			getControlador().insertar(vehiculoElejido);
			if (vehiculoElejido instanceof Turismo) {
				System.out.println("Turismo insertado correctamente");
			}
			if (vehiculoElejido instanceof Autobus) {
				System.out.println("Autobus insertado correctamente");
			}
			if (vehiculoElejido instanceof Furgoneta) {
				System.out.println("Furgoneta insertada correctamente");
			}
		} catch (IllegalArgumentException | NullPointerException | OperationNotSupportedException e) {
			System.out.println(e.getMessage());
		}
	}

	public void insertarAlquiler() throws OperationNotSupportedException {
		try {
			Alquiler alquiler = Consola.leerAlquiler();
			getControlador().insertar(alquiler);
			System.out.println("Alquiler insertado correctamente");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public void buscarCliente() {
		try {
			Consola.mostrarCabecera("Buscar cliente");
			System.out.println(getControlador().buscar(Consola.leerClienteDni()));
		} catch (IllegalArgumentException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}

	public void buscarVehiculo() {
		try {
			Consola.mostrarCabecera("Buscar turismo");
			System.out.println(getControlador().buscar(Consola.leerVehiculoMatricula()));
		} catch (IllegalArgumentException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}

	public void buscarAlquiler() {
		try {
			Consola.mostrarCabecera("Buscar alquiler");

			Alquiler alquiler = Consola.leerAlquiler();
			System.out.println(alquiler);

			System.out.println(getControlador().buscar(alquiler));
		} catch (IllegalArgumentException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}

	public void modificarCliente() throws OperationNotSupportedException {
		try {
			Consola.mostrarCabecera("Modificar cliente");
			getControlador().modificar(Consola.leerClienteDni(), Consola.leerNombre(), Consola.leerTelefono());
			System.out.println("Cliente modificado correctamente");
		} catch (IllegalArgumentException | NullPointerException | OperationNotSupportedException e) {
			System.out.println(e.getMessage());
		}
	}

	public void devolverAlquilerCliente() throws OperationNotSupportedException {
		try {
			Consola.mostrarCabecera("Devolver alquiler");
			getControlador().devolver(Consola.leerClienteDni(), Consola.leerFechaDevolucion());
		} catch (IllegalArgumentException | NullPointerException | OperationNotSupportedException e) {
			System.out.println(e.getMessage());
		}
	}

	public void devolverAlquilerVehiculo() throws OperationNotSupportedException {
		try {
			Consola.mostrarCabecera("Devolver alquiler");
			getControlador().devolver(Consola.leerVehiculoMatricula(), Consola.leerFechaDevolucion());
		} catch (IllegalArgumentException | NullPointerException | OperationNotSupportedException e) {
			System.out.println(e.getMessage());
		}
	}

	public void borrarCliente() throws OperationNotSupportedException {
		try {
			Consola.mostrarCabecera("Borrar cliente");
			getControlador().borrar(Consola.leerClienteDni());
			System.out.println("Cliente borrado correctamente");
		} catch (IllegalArgumentException | NullPointerException | OperationNotSupportedException e) {
			System.out.println(e.getMessage());
		}

	}

	public void borrarVehiculo() throws OperationNotSupportedException {
		try {
			Consola.mostrarCabecera("Borrar turismo");
			getControlador().borrar(Consola.leerVehiculoMatricula());
			System.out.println("Turismo borrado correctamente");
		} catch (IllegalArgumentException | NullPointerException | OperationNotSupportedException e) {
			System.out.println(e.getMessage());
		}
	}

	public void borrarAlquiler() throws OperationNotSupportedException {
		try {
			Consola.mostrarCabecera("Borrar alquiler");
			getControlador().borrar(Consola.leerAlquiler());
			System.out.println("Alquiler borrado correctamente");
		} catch (IllegalArgumentException | NullPointerException | OperationNotSupportedException e) {
			System.out.println(e.getMessage());
		}
	}

	public void listarClientes() {
		try {
			Consola.mostrarCabecera("Listado Clientes");
			List<Cliente> listaClienteOrdenada = getControlador().getClientes();
			listaClienteOrdenada.sort(Comparator.comparing(Cliente::getNombre).thenComparing(Cliente::getDni));
			System.out.println(listaClienteOrdenada);
		} catch (IllegalArgumentException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}

	public void listarVehiculo() {
		try {
			Consola.mostrarCabecera("Listado Turismos");
			List<Vehiculo> listaVehiculoOrdenada = getControlador().getVehiculos();
			listaVehiculoOrdenada.sort(Comparator.comparing(Vehiculo::getMarca).thenComparing(Vehiculo::getModelo).thenComparing(Vehiculo::getMatricula));
			System.out.println(listaVehiculoOrdenada);
		} catch (IllegalArgumentException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}

	public void listarAlquileres() {
		try {
			Consola.mostrarCabecera("Listado Alquileres");
			List<Alquiler> listaAlquileresOrdenada = getControlador().getAlquileres();
			Comparator<Cliente> comparadorClientes = Comparator.comparing(Cliente::getNombre).thenComparing(Cliente::getDni);
			listaAlquileresOrdenada.sort(Comparator.comparing(Alquiler::getFechaAlquiler).thenComparing(Alquiler::getCliente, comparadorClientes));
			System.out.println(listaAlquileresOrdenada);		
		} catch (IllegalArgumentException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}

	public void listarAlquileresCliente() {
		try {
			Cliente cliente = Consola.leerClienteDni();
			Consola.mostrarCabecera("Listado Alquileres del cliente con dni " + cliente.getDni());
			List<Alquiler> listaAlquileresClienteOrdenada = getControlador().getAlquileres(cliente);
			listaAlquileresClienteOrdenada.sort(Comparator.comparing(Alquiler::getFechaAlquiler));
			System.out.println(listaAlquileresClienteOrdenada);
		} catch (IllegalArgumentException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}

	public void listarAlquileresVehiculo() {
		try {
			Vehiculo turismo = Consola.leerVehiculoMatricula();
			Consola.mostrarCabecera("Listado Alquileres del turismo con matricula " + turismo.getMatricula());
			List<Alquiler> listaAlquileresVehiculoOrdenada = getControlador().getAlquileres(turismo);
			Comparator<Cliente> comparadorClientes = Comparator.comparing(Cliente::getNombre).thenComparing(Cliente::getDni);
			listaAlquileresVehiculoOrdenada.sort(Comparator.comparing(Alquiler::getFechaAlquiler).thenComparing(Alquiler::getCliente, comparadorClientes));
			System.out.println(listaAlquileresVehiculoOrdenada);
		} catch (IllegalArgumentException | NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}

	public void mostrarEstadisticasMensualesTipoVehiculo() {
		LocalDate fecha = null;
		do{
			fecha = Consola.leerMes();
		}while(fecha == null);
		Map<TipoVehiculo, Integer> mapaInicializado = inicializarEstadisticas();
		for (Alquiler alquiler : getControlador().getAlquileres()) {
			LocalDate fechaAlquiler = alquiler.getFechaAlquiler();
			if (fechaAlquiler.getMonth().equals(fecha.getMonth()) && fechaAlquiler.getYear() == fecha.getYear()) {
				TipoVehiculo tipoVehiculo = TipoVehiculo.getVehiculo(alquiler.getVehiculo());
				mapaInicializado.put(tipoVehiculo, mapaInicializado.get(tipoVehiculo)+1);
			}
		}
		StringBuilder cadena = new StringBuilder();;
		for (Entry<TipoVehiculo, Integer> mapa : mapaInicializado.entrySet()) {
			cadena.append(String.format("[%s - %d veces]", mapa.getKey(), mapa.getValue()));
		}
		System.out.println(cadena.toString());

	}

	private Map<TipoVehiculo, Integer> inicializarEstadisticas() {
		TreeMap<TipoVehiculo, Integer> mapa = new TreeMap<>();
		for (TipoVehiculo tipoVehiculo : TipoVehiculo.values()) {
			mapa.put(tipoVehiculo, 0);
		}
		return mapa;
	}

}
