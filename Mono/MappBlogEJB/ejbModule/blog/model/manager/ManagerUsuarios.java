package blog.model.manager;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import blog.model.util.ModelUtil;

import blog.model.entities.Blog;
import blog.model.entities.Usuario;

/**
 * Session Bean implementation class ManagerUsuarios
 */
@Stateless
@LocalBean
public class ManagerUsuarios {
	@PersistenceContext(unitName = "blogDS")
	private EntityManager em;

	public ManagerUsuarios() {

	}

	public List<Usuario> findAllUsuario() {
		Query q;
		List<Usuario> listado;
		String sentenciaSQL;
		sentenciaSQL = "SELECT u FROM Usuario u ORDER BY u.idUsuario";
		q = em.createQuery(sentenciaSQL);
		listado = q.getResultList();
		return listado;
	}

	public boolean comprobarUsuario(String idUsuario, String clave) throws Exception {
		Usuario u = em.find(Usuario.class, idUsuario);
		if (u == null)
			throw new Exception("No existe el usuario " + idUsuario);
		if (!u.getActivo())
			throw new Exception("El usuario no está activo.");
		if (u.getClave().equals(clave))
			return true;
		throw new Exception("Contraseña no válida.");
	}

	public Usuario findUsuarioById(String idUsuario) {
		Usuario u = em.find(Usuario.class, idUsuario);
		return u;
	}

	public void registrarNuevoBlogger(String idUsuario, String clave, String confirmacionClave, String correo)
			throws Exception {
		if (ModelUtil.isEmpty(idUsuario))
			throw new Exception("Debe especificar un ID de usuario.");
		// verificamos la contraseña:
		if (ModelUtil.isEmpty(clave))
			throw new Exception("Debe especificar una clave.");
		if (!clave.equals(confirmacionClave))
			throw new Exception("No coinciden la clave y la confirmación.");
		// verificamos correo:
		if (ModelUtil.isEmpty(correo))
			throw new Exception("Debe especificar un correo válido.");
		// TODO: tambien validar el formato del correo

		// Verificar la existencia del usuario:
		Usuario u = findUsuarioById(idUsuario);
		if (u != null)
			throw new Exception("Ya existe un usuario " + idUsuario);
		// finalmente se puede guardar el nuevo usuario:

		u = new Usuario();
		u.setIdUsuario(idUsuario);
		u.setClave(clave);
		u.setCorreo(correo);
		u.setActivo(true);
		em.persist(u);
	}

	public void eliminarUsuario(String idUsuario) throws Exception {
		Usuario u = findUsuarioById(idUsuario);
	
		if (u.getIdUsuario().equals("admin"))
			throw new Exception("El usuario a eliminar es administrador");
		
		if (u.getIdUsuario()!= "admin" && u.getBlogs()!= null)
			throw new Exception("El usuario tiene blogs");
		
		if (u.getIdUsuario()!= "admin" && u.getBlogs()== null)
			em.remove(u);

	
	
	}
}
