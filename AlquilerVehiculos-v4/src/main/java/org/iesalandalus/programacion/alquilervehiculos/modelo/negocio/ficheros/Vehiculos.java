package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.naming.OperationNotSupportedException;
import javax.xml.parsers.DocumentBuilder;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Autobus;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Furgoneta;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IVehiculos;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Vehiculos implements IVehiculos {

	private List<Vehiculo> coleccionVehiculos;
	private File FICHERO_VEHICULOS = new File(String.format("%s%s%s", "datos", File.separator, "vehiculos.xml"));
	private static final String RAIZ = "vehiculos";
	private static final String VEHICULO = "vehiculo";
	private static final String MARCA = "marca";
	private static final String MODELO = "modelo";
	private static final String MATRICULA = "matricula";
	private static String CILINDRADA = "cilindrada";
	private static final String PLAZAS = "plazas";
	private static final String PMA = "pma";
	private static final String TIPO = "tipo";
	private static final String TURISMO = "turismo";
	private static final String AUTOBUS = "autobus";
	private static final String FURGONETA = "furgoneta";
	private static Vehiculos instancia;

	private Vehiculos() {
		coleccionVehiculos = new ArrayList<>();
	}

	static Vehiculos getInstancia() {
		if (instancia == null) {
			instancia = new Vehiculos();
		}
		return instancia;
	}

	public void comenzar() {
			Document documentoXml = UtilidadesXml.leerXmlDeFichero(FICHERO_VEHICULOS);// llamo a leerfichero
			if (documentoXml == null) {
				System.out.println("No se ha podido leer el FICHERO_VEHICULOS");
			} else {
				leerDom(documentoXml);// cOMPRUEBO SI ES NULL O NO Y SI ES NULL MUESTRO MENSAJE NULLO
				System.out.println("El documento VEHICULOS ha sido leido correctamente");
			}
	}

	private void leerDom(Document documentoXml)  {
		NodeList vehiculos = documentoXml.getElementsByTagName(VEHICULO);
		for (int i = 0; i < vehiculos.getLength(); i++) {
			Node vehiculo = vehiculos.item(i);
			if (vehiculo.getNodeType() == Node.ELEMENT_NODE) {
				try {
					insertar(getVehiculo((Element) vehiculo));
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}

			}
		}
	}

	private Vehiculo getVehiculo(Element elemento) {
		String marca = elemento.getAttribute(MARCA);
		String modelo = elemento.getAttribute(MODELO);
		String matricula = elemento.getAttribute(MATRICULA);
		String tipo = elemento.getAttribute(TIPO);
		Vehiculo vehiculo = null;
		if (tipo.equals(TURISMO)) {
			int cilindrada = Integer.parseInt(elemento.getAttribute(CILINDRADA));
			vehiculo = new Turismo(marca, modelo, cilindrada, matricula);
		}
		if (tipo.equals(AUTOBUS)) {
			int plazas = Integer.parseInt(elemento.getAttribute(PLAZAS));
			vehiculo = new Autobus(marca, modelo, plazas, matricula);
		}
		if (tipo.equals(FURGONETA)) {
			int pma = Integer.parseInt(elemento.getAttribute(PMA));
			int plazas = Integer.parseInt(elemento.getAttribute(PLAZAS));
			vehiculo = new Furgoneta(marca, modelo, pma, plazas, matricula);
		}
		return vehiculo;

	}

	public void terminar() {
		Document documento = crearDom();
		UtilidadesXml.escribirXmlAFichero(documento, FICHERO_VEHICULOS);
	}

	private Document crearDom() {
		DocumentBuilder constructor = UtilidadesXml.crearConstructorDocumentoXml();
		Document documentoXml = null;
		if (constructor != null) {
			documentoXml = constructor.newDocument();
			documentoXml.appendChild(documentoXml.createElement(RAIZ));
			for (Vehiculo vehiculo : getInstancia().get()) {
				Element elementoDato = getElemento(documentoXml, vehiculo);
				documentoXml.getDocumentElement().appendChild(elementoDato);
			}
		}
		return documentoXml;

	}

	private Element getElemento(Document documentoXml, Vehiculo vehiculo) {
		Element elementoVehiculo = documentoXml.createElement(VEHICULO);
		elementoVehiculo.setAttribute(MARCA, vehiculo.getMarca());
		elementoVehiculo.setAttribute(MATRICULA, vehiculo.getMatricula());
		elementoVehiculo.setAttribute(MODELO, vehiculo.getModelo());
		if (vehiculo instanceof Turismo turismo) {
			elementoVehiculo.setAttribute(CILINDRADA, String.valueOf(turismo.getCilindrada()));
			elementoVehiculo.setAttribute(TIPO, TURISMO);
		}
		if (vehiculo instanceof Autobus autobus) {
			elementoVehiculo.setAttribute(PLAZAS, String.valueOf(autobus.getPlazas()));
			elementoVehiculo.setAttribute(TIPO, AUTOBUS);
		}
		if (vehiculo instanceof Furgoneta furgoneta) {
			elementoVehiculo.setAttribute(PLAZAS, String.valueOf(furgoneta.getPlazas()));
			elementoVehiculo.setAttribute(PMA, String.valueOf(furgoneta.getPma()));
			elementoVehiculo.setAttribute(TIPO, FURGONETA);
		}
		return elementoVehiculo;
	}

	@Override
	public List<Vehiculo> get() {
		return new ArrayList<>(coleccionVehiculos);
	}

	@Override
	public void insertar(Vehiculo vehiculo) throws OperationNotSupportedException {
		if (vehiculo == null) {
			throw new NullPointerException("ERROR: No se puede insertar un vehículo nulo.");
		}
		if (buscar(vehiculo) != null) {
			throw new OperationNotSupportedException("ERROR: Ya existe un vehículo con esa matrícula.");
		}
		coleccionVehiculos.add(vehiculo);
	}

	@Override
	public void borrar(Vehiculo vehiculo) throws OperationNotSupportedException {
		if (vehiculo == null) {
			throw new NullPointerException("ERROR: No se puede borrar un vehículo nulo.");
		}
		if (buscar(vehiculo) == null) {
			throw new OperationNotSupportedException("ERROR: No existe ningún vehículo con esa matrícula.");
		}
		coleccionVehiculos.remove(vehiculo);
	}

	@Override
	public Vehiculo buscar(Vehiculo vehiculo) {
		if (vehiculo == null) {
			throw new NullPointerException("ERROR: No se puede buscar un vehículo nulo.");
		}
		return coleccionVehiculos.indexOf(vehiculo) != -1 ? coleccionVehiculos.get(coleccionVehiculos.indexOf(vehiculo))
				: null;
	}

}
