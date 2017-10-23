package blog.model.manager;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import blog.model.entities.Articulo;
import blog.model.entities.Blog;
import blog.model.entities.Usuario;

/**
 * Session Bean implementation class ManagerBlog
 */
@Stateless
@LocalBean
public class ManagerBlog {
	@PersistenceContext(unitName = "blogDS")
	private EntityManager em;
	@EJB
	private ManagerUsuarios managerUsuarios;

	public ManagerBlog() {
	}

	/**
	 * Finder para buscar todos los blogs de un usuario especifico.
	 * 
	 * @param idUsuario
	 *            El identificador del usuario.
	 * @return Lista de blogs del usuario.
	 */
	@SuppressWarnings("unchecked")
	public List<Blog> findBlogByUsuario(String idUsuario) {
		String JPQL = "SELECT o FROM Blog o WHERE o.usuario.idUsuario='" + idUsuario + "'";
		Query q;
		List<Blog> lista;
		q = em.createQuery(JPQL);
		lista = q.getResultList();
		return lista;
	}

	/**
	 * Crea un nuevo blog incluyendo su primer articulo de manera obligatoria.
	 * 
	 * @param idUsuario
	 *            El identificador del usuario.
	 * @param nombreBlog
	 *            Nombre del blog.
	 * @param descripcion
	 *            La descripcion del blog.
	 * @param tituloArticulo
	 *            Titulo del primer articulo.
	 * @param contenido
	 *            Contenido del articulo.
	 * @throws Exception
	 */
	public void crearBLog(String idUsuario, String nombreBlog, String descripcion, String tituloArticulo,
			String contenido) throws Exception {
		Usuario u = managerUsuarios.findUsuarioById(idUsuario);
		if (u == null)
			throw new Exception("No existe el usuario " + idUsuario);
		// manejo de datos en cascada (maestro y detalle):
		Blog blog = new Blog();
		blog.setDescripcion(descripcion);
		blog.setNombreBlog(nombreBlog);
		blog.setUsuario(u);
		blog.setArticulos(new ArrayList<Articulo>());// lista vacia
		Articulo articulo = new Articulo();
		articulo.setTitulo(tituloArticulo);
		articulo.setContenido(contenido);
		articulo.setBlog(blog);// relacion bidireccional
		blog.addArticulo(articulo);// relacion bidireccional
		em.persist(blog);
	}

	public void insertarArticulo(Blog blog, String titulo, String contenido) {
		Articulo articulo = new Articulo();
		articulo.setTitulo(titulo);
		articulo.setContenido(contenido);
		articulo.setBlog(blog);
		em.persist(articulo);
	}

	public void eliminarArticulo(Articulo articulo) {
		em.remove(articulo);
	}
}