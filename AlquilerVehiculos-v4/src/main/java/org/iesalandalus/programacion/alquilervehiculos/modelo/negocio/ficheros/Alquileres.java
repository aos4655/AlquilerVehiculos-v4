package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.naming.OperationNotSupportedException;
import javax.xml.parsers.DocumentBuilder;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Furgoneta;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IAlquileres;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Alquileres implements IAlquileres {

	private File FICHERO_ALQUILERES = new File(
			String.format("%s%s%s", "datos", File.separator, "alquileres.xml"));
	private static final String RAIZ = "alquileres";
	private List<Alquiler> coleccionAlquileres;
	private DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private static final String ALQUILER = "alquiler";
	private static final String CLIENTE = "cliente";
	private static final String VEHICULO = "vehiculo";
	private static final String FECHA_ALQUILER = "fechaAlquiler";
	private static final String FECHA_DEVOLUCION = "fechaDevolucion";
	private static Alquileres instancia;

	private Alquileres() {
		coleccionAlquileres = new ArrayList<>();
	}

	static Alquileres getInstancia() {
		if (instancia == null) {
			instancia = new Alquileres();
		}
		return instancia;
	}

	public void comenzar() {
		Document documentoXml = UtilidadesXml.leerXmlDeFichero(FICHERO_ALQUILERES);// llamo a leerfichero
		if (documentoXml == null) {
			System.out.println("No se ha podido leer el FICHERO_ALQUILERES");
		} else {
			leerDom(documentoXml);
			System.out.println("El documento ALQUILERES ha sido leido correctamente");
		}
	}

	private void leerDom(Document documentoXml) {
		NodeList alquileres = documentoXml.getElementsByTagName(ALQUILER);
		for (int i = 0; i < alquileres.getLength(); i++) {
			Node alquiler = alquileres.item(i);
			if (alquiler.getNodeType() == Node.ELEMENT_NODE) {
				try {
					insertar(getAlquiler((Element) alquiler));
				} catch (Exception e) {
					System.out.println("Error al intentar leer el alquiler numero "+ i + "-->"+e.getMessage());
				}
			}
		}
	}

	private Alquiler getAlquiler(Element elemento) throws OperationNotSupportedException {
		String clienteDNI = elemento.getAttribute(CLIENTE);
		String fechaAlquiler = elemento.getAttribute(FECHA_ALQUILER);
		String vehiculoMat = elemento.getAttribute(VEHICULO);
		Cliente cliente = Clientes.getInstancia().buscar(Cliente.getClienteConDni(clienteDNI));
		if (cliente == null) {
			System.out.println("El cliente no existe");
		}
		Vehiculo vehiculo = Vehiculos.getInstancia().buscar(Vehiculo.getVehiculoConMatricula(vehiculoMat));
		if (vehiculo == null) {
			System.out.println("El vehiculo no existe");
		}
		LocalDate fechaAlq = LocalDate.parse(fechaAlquiler, FORMATO_FECHA);
		Alquiler alquiler = new Alquiler(cliente, vehiculo, fechaAlq);
		if (elemento.hasAttribute(FECHA_DEVOLUCION)) {
			String fechaDevolucion = elemento.getAttribute(FECHA_DEVOLUCION);
			LocalDate fechaDev = LocalDate.parse(fechaDevolucion, FORMATO_FECHA);
			alquiler.devolver(fechaDev);
		}
		return alquiler;
	}

	public void terminar() {
		Document documento = crearDom();
		UtilidadesXml.escribirXmlAFichero(documento, FICHERO_ALQUILERES);
	}

	private Document crearDom() {
		DocumentBuilder constructor = UtilidadesXml.crearConstructorDocumentoXml();
		Document documentoXml = null;
		if (constructor != null) {
			documentoXml = constructor.newDocument();
			documentoXml.appendChild(documentoXml.createElement(RAIZ));
			for (Alquiler alquiler : getInstancia().get()) {
				Element elementoDato = getElemento(documentoXml, alquiler);
				documentoXml.getDocumentElement().appendChild(elementoDato);
			}
		}
		return documentoXml;
	}

	private Element getElemento(Document documentoXml, Alquiler alquiler) {
		Element elementoAlquiler = documentoXml.createElement(ALQUILER);
		elementoAlquiler.setAttribute(CLIENTE, alquiler.getCliente().getDni());
		elementoAlquiler.setAttribute(FECHA_ALQUILER, alquiler.getFechaAlquiler().format(FORMATO_FECHA));
		if (alquiler.getFechaDevolucion() != null) {
			elementoAlquiler.setAttribute(FECHA_DEVOLUCION, alquiler.getFechaDevolucion().format(FORMATO_FECHA));
		}
		elementoAlquiler.setAttribute(VEHICULO, alquiler.getVehiculo().getMatricula());
		return elementoAlquiler;
	}

	@Override
	public List<Alquiler> get() {
		return new ArrayList<>(coleccionAlquileres);
	}

	@Override
	public List<Alquiler> get(Cliente cliente) {
		List<Alquiler> listaAlquileresCliente = new ArrayList<>();
		for (Alquiler alquiler : coleccionAlquileres) {
			if (alquiler.getCliente().equals(cliente)) {
				listaAlquileresCliente.add(alquiler);
			}
		}
		return listaAlquileresCliente;
	}

	@Override
	public List<Alquiler> get(Vehiculo vehiculo) {
		List<Alquiler> listaAlquilerTurismo = new ArrayList<>();
		for (Alquiler alquiler : coleccionAlquileres) {
			if (alquiler.getVehiculo().equals(vehiculo)) {
				listaAlquilerTurismo.add(alquiler);
			}
		}
		return listaAlquilerTurismo;
	}

	@Override
	public void insertar(Alquiler alquiler) throws OperationNotSupportedException {
		if (alquiler == null) {
			throw new NullPointerException("ERROR: No se puede insertar un alquiler nulo.");
		}
		comprobarAlquiler(alquiler.getCliente(), alquiler.getVehiculo(), alquiler.getFechaAlquiler());
		coleccionAlquileres.add(alquiler);
	}

	private void comprobarAlquiler(Cliente cliente, Vehiculo vehiculo, LocalDate fechaAlquiler)
			throws OperationNotSupportedException {
		for (Alquiler alquiler : coleccionAlquileres) {
			Cliente clienteAlquiler = alquiler.getCliente();
			Vehiculo vehiculoAlquiler = alquiler.getVehiculo();
			if (clienteAlquiler.equals(cliente) && alquiler.getFechaDevolucion() == null) {
				throw new OperationNotSupportedException("ERROR: El cliente tiene otro alquiler sin devolver."); // Alquiler
																													// //
																													// cliente
			}
			else if (vehiculoAlquiler.equals(vehiculo) && alquiler.getFechaDevolucion() == null) {
				throw new OperationNotSupportedException("ERROR: El vehículo está actualmente alquilado."); // Alquiler
																											// //
																											// cliente
			}
			else if (clienteAlquiler.equals(cliente) && alquiler.getFechaDevolucion() != null
					&& alquiler.getFechaDevolucion().compareTo(fechaAlquiler) >= 0) {
				throw new OperationNotSupportedException("ERROR: El cliente tiene un alquiler posterior.");

			}
			else if (vehiculoAlquiler.equals(vehiculo) && alquiler.getFechaDevolucion() != null
					&& alquiler.getFechaDevolucion().compareTo(fechaAlquiler) >= 0) {
				throw new OperationNotSupportedException("ERROR: El vehículo tiene un alquiler posterior.");
			}
		}
	}

	@Override
	public void devolver(Cliente cliente, LocalDate fechaDevolucion) throws OperationNotSupportedException {
		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede devolver un alquiler de un cliente nulo.");
		}
		Alquiler alquilerCliente = getAlquilerAbierto(cliente);
		if (alquilerCliente == null) {
			throw new OperationNotSupportedException("ERROR: No existe ningún alquiler abierto para ese cliente.");
		}
		alquilerCliente.devolver(fechaDevolucion);
	}

	public void devolver(Vehiculo vehiculo, LocalDate fechaDevolucion) throws OperationNotSupportedException {
		if (vehiculo == null) {
			throw new NullPointerException("ERROR: No se puede devolver un alquiler de un vehículo nulo.");
		}
		Alquiler alquilerVehiculo = getAlquilerAbierto(vehiculo);
		if (alquilerVehiculo == null) {
			throw new OperationNotSupportedException("ERROR: No existe ningún alquiler abierto para ese vehículo.");
		}
		alquilerVehiculo.devolver(fechaDevolucion);
	}

	@Override
	public void borrar(Alquiler alquiler) throws OperationNotSupportedException {
		if (alquiler == null) {
			throw new NullPointerException("ERROR: No se puede borrar un alquiler nulo.");
		}
		else if (buscar(alquiler) == null) {
			throw new OperationNotSupportedException("ERROR: No existe ningún alquiler igual.");
		}
		coleccionAlquileres.remove(alquiler);
	}

	@Override
	public Alquiler buscar(Alquiler alquiler) {
		if (alquiler == null) {
			throw new NullPointerException("ERROR: No se puede buscar un alquiler nulo.");
		}
		int index = coleccionAlquileres.indexOf(alquiler);
		return index != -1 ? coleccionAlquileres.get(index) : null;
	}

	private Alquiler getAlquilerAbierto(Cliente cliente) {
		Iterator<Alquiler> it = coleccionAlquileres.iterator();
		Alquiler alquilerEncontrado = null;
		while (it.hasNext() && alquilerEncontrado == null) {
			Alquiler temporal = it.next();
			if (temporal.getCliente().equals(cliente) && temporal.getFechaDevolucion() == null) {
				alquilerEncontrado = temporal;
			}
		}
		return alquilerEncontrado;
	}

	private Alquiler getAlquilerAbierto(Vehiculo vehiculo) {
		Iterator<Alquiler> it = coleccionAlquileres.iterator();
		Alquiler alquilerEncontrado = null;
		while (it.hasNext() && alquilerEncontrado == null) {
			Alquiler temporal = it.next();
			if (temporal.getVehiculo().equals(vehiculo) && temporal.getFechaDevolucion() == null) {
				alquilerEncontrado = temporal;
			}
		}
		return alquilerEncontrado;
	}

}
