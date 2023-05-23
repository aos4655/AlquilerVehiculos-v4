package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.naming.OperationNotSupportedException;
import javax.xml.parsers.DocumentBuilder;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IClientes;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Clientes implements IClientes {

	private static final File FICHERO_CLIENTES = new File(
			String.format("%s%s%s", "datos", File.separator, "clientes.xml"));
	private static final String RAIZ = "clientes";
	private static final String CLIENTE = "cliente";
	private static final String NOMBRE = "nombre";
	private static final String DNI = "dni";
	private static final String TELEFONO = "telefono";
	private static Clientes instancia;

	private List<Cliente> coleccionClientes;

	private Clientes() {
		coleccionClientes = new ArrayList<>();
	}

	static Clientes getInstancia() {
		if (instancia == null) {
			instancia = new Clientes();
		}
		return instancia;
	}

	public void comenzar() {
			Document documentoXml = UtilidadesXml.leerXmlDeFichero(FICHERO_CLIENTES);// llamo a leerfichero
			if (documentoXml == null) {
				System.out.println("No se ha podido leer el FICHERO_CLIENTES");
			} else {
				leerDom(documentoXml);
				System.out.println("El documento CLIENTES ha sido leido correctamente");
			}
	}

	private void leerDom(Document documentoXml) {
		NodeList clientes = documentoXml.getElementsByTagName(CLIENTE);
		for (int i = 0; i < clientes.getLength(); i++) {
			Node cliente = clientes.item(i);
			if (cliente.getNodeType() == Node.ELEMENT_NODE) {
				try {
					insertar(getCliente((Element) cliente));
				} catch (Exception e) {
					System.out.println("Error al procesar el cliente con numero " + i + " ==> " + e.getMessage());
				}
			}
		}
	}

	private Cliente getCliente(Element elemento) {
		String nombre = elemento.getAttribute(NOMBRE);
		String dni = elemento.getAttribute(DNI);
		String telefono = elemento.getAttribute(TELEFONO);
		return new Cliente(nombre, dni, telefono);
	}

	public void terminar() {
		Document documento = crearDom();
		UtilidadesXml.escribirXmlAFichero(documento, FICHERO_CLIENTES);
	}

	private Document crearDom() {
		DocumentBuilder constructor = UtilidadesXml.crearConstructorDocumentoXml();
		Document documentoXml = null;
		if (constructor != null) {
			documentoXml = constructor.newDocument();
			documentoXml.appendChild(documentoXml.createElement(RAIZ));
			for (Cliente cliente : getInstancia().get()) {
				Element elementoDato = getElemento(documentoXml, cliente);
				documentoXml.getDocumentElement().appendChild(elementoDato);
			}
		}
		return documentoXml;
	}

	private Element getElemento(Document documentoXml, Cliente cliente) {
		Element elementoCliente = documentoXml.createElement(CLIENTE);
		elementoCliente.setAttribute(NOMBRE, cliente.getNombre());
		elementoCliente.setAttribute(DNI, cliente.getDni());
		elementoCliente.setAttribute(TELEFONO, cliente.getTelefono());

		return elementoCliente;
	}

	@Override
	public List<Cliente> get() {
		return new ArrayList<>(coleccionClientes);
	}

	@Override
	public void insertar(Cliente cliente) throws OperationNotSupportedException {
		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede insertar un cliente nulo.");
		}
		if (buscar(cliente) != null) {
			throw new OperationNotSupportedException("ERROR: Ya existe un cliente con ese DNI.");
		}
		coleccionClientes.add(cliente);
	}

	@Override
	public void borrar(Cliente cliente) throws OperationNotSupportedException {
		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede borrar un cliente nulo.");
		}
		if (buscar(cliente) == null) {
			throw new OperationNotSupportedException("ERROR: No existe ningún cliente con ese DNI.");
		}
		coleccionClientes.remove(cliente);
	}

	@Override
	public Cliente buscar(Cliente cliente) {
		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede buscar un cliente nulo.");
		}
		return coleccionClientes.indexOf(cliente) != -1 ? coleccionClientes.get(coleccionClientes.indexOf(cliente))
				: null;
	}

	@Override
	public void modificar(Cliente cliente, String nombre, String telefono) throws OperationNotSupportedException {
		if (cliente == null) {
			throw new NullPointerException("ERROR: No se puede modificar un cliente nulo.");
		}
		Cliente clienteBuscado = buscar(cliente);
		if (clienteBuscado == null) {
			throw new OperationNotSupportedException("ERROR: No existe ningún cliente con ese DNI.");
		}
		if (nombre != null && !nombre.isBlank()) {
			clienteBuscado.setNombre(nombre);
		}
		if (telefono != null && !telefono.isBlank()) {
			clienteBuscado.setTelefono(telefono);
		}
	}

}
