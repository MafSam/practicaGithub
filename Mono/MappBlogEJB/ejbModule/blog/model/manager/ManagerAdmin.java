package blog.model.manager;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import blog.dao.model.ManagerDaoBlog;

import blog.model.entities.Blog;
import blog.model.entities.Usuario;

@Stateless
@LocalBean
public class ManagerAdmin {

	@PersistenceContext(unitName = "blogDS")
	private EntityManager em;
	@EJB
	ManagerDaoBlog managerDaoBlog;
	Blog blog;

	@SuppressWarnings("unchecked")
	public List<Usuario> listaAllUsuario() throws Exception {
		List<Usuario> listaUsuario = managerDaoBlog.findWhere(Usuario.class, "o.idUsuario!='admin'", null);
		return listaUsuario;
	}

	public void eliminarUsuario(Usuario usuario) throws Exception {
 	managerDaoBlog.eliminar(Usuario.class, usuario.getIdUsuario());
	}
}
