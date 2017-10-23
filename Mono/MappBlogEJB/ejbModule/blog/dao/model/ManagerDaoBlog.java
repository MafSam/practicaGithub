package blog.dao.model;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Session Bean implementation class ManagerActFij
 */
@Stateless(mappedName = "managerDaoBlog")
@LocalBean
public class ManagerDaoBlog {

	@PersistenceContext(unitName = "blogDS")
	private EntityManager em;

	public ManagerDaoBlog() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * finder Generico que devuelve todos las entidades de una tabla.
	 * 
	 * @param clase
	 *            La clase que se desea consultar. Por ejemplo:
	 *            <ul>
	 *            <li>Usuario.class</li>
	 *            </ul>
	 * @param orderBy
	 *            Expresión que indica la propiedad de la entidad por la que se
	 *            desea ordenar la consulta. Debe utilizar el alias "o" para
	 *            nombrar a la(s) propiedad(es) por la que se va a ordenar. por
	 *            ejemplo:
	 *            <ul>
	 *            <li>o.nombre</li>
	 *            <li>o.codigo,o.nombre</li>
	 *            </ul>
	 *            Puede aceptar null o una cadena vacia, en este caso no
	 *            ordenara el resultado.
	 * @return Listado resultante.
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List findAll(Class clase, String orderBy) throws Exception {
		Query q;
		List listado;
		String sentenciaSQL;

		if (orderBy == null || orderBy.length() == 0)
			sentenciaSQL = "SELECT o FROM " + clase.getSimpleName() + " o";
		else
			sentenciaSQL = "SELECT o FROM " + clase.getSimpleName() + " o ORDER BY " + orderBy;

		q = em.createQuery(sentenciaSQL);
		listado = q.getResultList();
		// mLog.MostrarLog(this.getClass(),"findAll",sentenciaSQL);
		return listado;
	}

	/**
	 * finder Generico que devuelve todos las entidades de una tabla.
	 * 
	 * @param clase
	 *            La clase que se desea consultar. Por ejemplo:
	 *            <ul>
	 *            <li>Usuario.class</li>
	 *            </ul>
	 * 
	 * @return Listado resultante.
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List findAll(Class clase) throws Exception {
		return findAll(clase, null);
	}

	/**
	 * Finder genérico para buscar un objeto especifico.
	 * 
	 * @param clase
	 *            La clase sobre la que se desea consultar, ejemplo:
	 *            Usuario.class
	 * @param pID
	 *            Identificador (la clave primaria) que permitira la busqueda.
	 * @return El objeto solicitado (si existiera).
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object findById(Class clase, Object pID) throws Exception {
		if (pID == null)
			throw new Exception("Debe especificar el código para buscar el dato.");
		Object o;
		try {
			o = em.find(clase, pID);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al buscar la información especificada (" + pID + ") : " + e.getMessage());
		}
		return o;
	}

	/**
	 * Finder generico que permite aplicar clausulas where y order by.
	 * 
	 * @param clase
	 *            La entidad sobre la que se desea consultar. Ej: Usuario.class
	 * @param pClausulaWhere
	 *            Clausula where de tipo JPQL (sin la palabra reservada WHERE).
	 *            Ejemplo:
	 *            <ul>
	 *            <li>o.nombre='Antonio'</li>
	 *            <li>o.nombre='Antonio' and o.telefono='0444-434'</li>
	 *            <li>o.nombre like 'Ant%'</li>
	 *            </ul>
	 * @param pOrderBy
	 *            Clausula order by de tipo JPQL (sin la palabra reservada ORDER
	 *            BY). Puede ser null para no ordenar. por ejemplo:
	 *            <ul>
	 *            <li>o.nombre</li>
	 *            <li>o.codigo,o.nombre</li>
	 *            </ul>
	 *            Tanto para la clausula <b>where</b> como <b>order by</b> debe
	 *            utilizarse el alias de entidad "o".
	 * @return Listado resultante.
	 */
	@SuppressWarnings("rawtypes")
	public List findWhere(Class clase, String pClausulaWhere, String pOrderBy) {
		// mostrarLog("findAll(where): "+clase.getSimpleName());
		Query q;
		List listado;
		String sentenciaSQL;

		if (pOrderBy == null || pOrderBy.length() == 0)
			sentenciaSQL = "SELECT o FROM " + clase.getSimpleName() + " o WHERE " + pClausulaWhere;

		else
			sentenciaSQL = "SELECT o FROM " + clase.getSimpleName() + " o WHERE " + pClausulaWhere + " ORDER BY "
					+ pOrderBy;
		q = em.createQuery(sentenciaSQL);
		listado = q.getResultList();
		// managerLog.MostrarLog(this.getClass(),"findWhere",sentenciaSQL);
		return listado;
	}

	/**
	 * Almacena un objeto en la persistencia.
	 * 
	 * @param pObjeto
	 *            El objeto a insertar.
	 * @throws Exception
	 */
	public void insertar(Object pObjeto) throws Exception {
		if (pObjeto == null)
			throw new Exception("No se puede insertar un objeto null.");
		try {
			em.persist(pObjeto);
			// mostrarLog("Objeto insertado:
			// "+pObjeto.getClass().getSimpleName());
		} catch (Exception e) {
			// mostrarLog("No se pudo insertar el objeto especificado:
			// "+pObjeto.getClass().getSimpleName());
			throw new Exception("No se pudo insertar el objeto especificado: " + e.getMessage());
		}
	}

	/**
	 * Elimina un objeto de la persistencia.
	 * 
	 * @param clase
	 *            La clase correspondiente al objeto que se desea eliminar.
	 * @param pID
	 *            El identificador del objeto que se desea eliminar.
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public void eliminar(Class clase, Object pID) throws Exception {
		if (pID == null) {
			// mostrarLog("Debe especificar un identificador para eliminar el
			// dato solicitado: "+clase.getSimpleName());
			throw new Exception("Debe especificar un identificador para eliminar el dato solicitado.");
		}
		Object o = findById(clase, pID);
		try {
			em.remove(o);
			// mostrarLog("Dato eliminado: "+clase.getSimpleName()+" "
			// +pID.toString());
		} catch (Exception e) {
			// mostrarLog("No se pudo eliminar el dato:
			// "+clase.getSimpleName());
			throw new Exception("No se pudo eliminar el dato (" + pID + ") : " + e.getMessage());
		}
	}

	/**
	 * Actualiza la información de un objeto en la persistencia.
	 * 
	 * @param pObjeto
	 *            Objeto que contiene la información que se debe actualizar.
	 * @throws Exception
	 */
	public void actualizar(Object pObjeto) throws Exception {
		if (pObjeto == null)
			throw new Exception("No se puede actualizar un dato null");
		try {
			em.merge(pObjeto);
		} catch (Exception e) {
			throw new Exception("No se pudo actualizar el dato: " + e.getMessage());
		}

	}

	public Long obtenerSeqPeticionPago() throws Exception {
		Query q;
		String sentenciaSQL;
		BigDecimal codigo;
		try {
			sentenciaSQL = "SELECT MAX(NRO_PETICION) FROM PETICION";
			q = em.createNativeQuery(sentenciaSQL);
			if (q.getSingleResult() == null) {
				codigo = new BigDecimal(0);
			} else {
				codigo = (BigDecimal) q.getSingleResult();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al obtener valor de la Peticion de Pago anterior: " + e.getCause().getMessage());
		}
		return codigo.longValue();
	}
}
