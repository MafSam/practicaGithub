package blog.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import blog.model.entities.Blog;
import blog.model.entities.Usuario;
import blog.model.manager.ManagerUsuarios;
import blog.model.util.ModelUtil;
import blog.view.util.JSFUtil;

@ManagedBean
@SessionScoped
public class ControllerUsuario {

	private String idUsuario;
	private String clave;
	private String correo;
	private String confirmacionClave;
	private boolean confirmadoLogin;
	private Blog blog;
	private Usuario usuario;
	private List<Usuario> listaUsuarios;

	@EJB
	private ManagerUsuarios managerUsuarios;

	@PostConstruct
	public void iniciar() {
		listaUsuarios = managerUsuarios.findAllUsuario();
	}

	public String actionLogin() {
		try {
			confirmadoLogin = managerUsuarios.comprobarUsuario(idUsuario, clave);
			JSFUtil.crearMensajeInfo("Se a Logeado correctamente");
			// verificamos si el acceso es con admin:
			if (idUsuario.equals("admin")) {
				return "admin/index"; 
			}
			return "blog/index";
		} catch (Exception e) {
			JSFUtil.crearMensajeError(e.getMessage());
			e.printStackTrace();
		}
		return "";
	}

	public String actionRegistrarBlogger() {
		try {
			managerUsuarios.registrarNuevoBlogger(idUsuario, clave, confirmacionClave, correo);
			JSFUtil.crearMensajeInfo("Nuevo blogger registrado.");
			return "blog/index";
		} catch (Exception e) {
			JSFUtil.crearMensajeError(e.getMessage());
			e.printStackTrace();
		}
		return "";
	}

	public String actionEliminarUsuario() {
		try {
			managerUsuarios.eliminarUsuario(idUsuario);
			JSFUtil.crearMensajeInfo("Usuario Eliminado");
			return "admin/index";
		} catch (Exception e) {
			JSFUtil.crearMensajeError(e.getMessage());
			e.printStackTrace();
		}
		return "";
	}

	public String actionSalir() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "/login?faces-redirect=true";
	}

	public void actionComprobarLogin() {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		try {
			String path = ec.getRequestPathInfo();
			System.out.println("getRequestContextPath(): " + ec.getRequestContextPath());
			System.out.println("getRequestPathInfo(): " + path);
			System.out.println("Id usuario: " + idUsuario);
			if (path.equals("/login.xhtml"))
				return;
			if (ModelUtil.isEmpty(idUsuario))
				ec.redirect(ec.getRequestContextPath() + "/faces/login.xhtml");
			if (!confirmadoLogin) {
				ec.redirect(ec.getRequestContextPath() + "/faces/login.xhtml");
			} else {
				// si hizo login, verificamos que acceda a paginas permitidas:
				if (idUsuario.equals("admin")) {
					if (!path.contains("/admin/"))
						ec.redirect(ec.getRequestContextPath() + "/faces/login.xhtml");
					else
						return;
				}
				// caso contrario es un blogger:
				if (!path.contains("/blog/"))
					ec.redirect(ec.getRequestContextPath() + "/faces/login.xhtml");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getConfirmacionClave() {
		return confirmacionClave;
	}

	public void setConfirmacionClave(String confirmacionClave) {
		this.confirmacionClave = confirmacionClave;
	}

	public boolean isConfirmadoLogin() {
		return confirmadoLogin;
	}

	public Blog getBlog() {
		return blog;
	}

	public void setBlog(Blog blog) {
		this.blog = blog;
	}

	public List<Usuario> getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(List<Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	

}