package blog.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import blog.model.entities.Articulo;
import blog.model.entities.Blog;
import blog.model.entities.Usuario;
import blog.model.manager.ManagerAdmin;
import blog.view.util.JSFUtil;


@ManagedBean
@SessionScoped
public class ControllerAdmin {
	@EJB
	private ManagerAdmin managerAdmin;
	

    List<Usuario> listaUsuarios; 
    List<Articulo> listaArticulo;

    public List<Articulo> getListaArticulo() {
		return listaArticulo;
	}
	public void setListaArticulo(List<Articulo> listaArticulo) {
		this.listaArticulo = listaArticulo;
	}
	Usuario usuario;
    Blog blog;
    Articulo articulo;
	
	@PostConstruct
	public void iniciar(){

		usuario= new Usuario();
		try {
			listaUsuarios= managerAdmin.listaAllUsuario();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//cambio de texto
			e.printStackTrace();
		}
		
	}
		public void eliminarUsu(Usuario usuario) {
			try {
				
				managerAdmin.eliminarUsuario(usuario);
				listaUsuarios= managerAdmin.listaAllUsuario();
				JSFUtil.crearMensajeInfo("Usuario Elimado");
				
			} catch (Exception e) {
				JSFUtil.crearMensajeInfo("No puede elimanr el Usuario tiene blogs en su cuenta");
				e.printStackTrace();
			}
		}
}
