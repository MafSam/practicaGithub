package blog.controller;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import blog.model.entities.Articulo;
import blog.model.entities.Blog;
import blog.model.manager.ManagerBlog;
import blog.view.util.JSFUtil;

@ManagedBean
@SessionScoped
public class ControllerBlog {
	private List<Blog> listaBlogs;
	private String nombreBlo;
	private String descripcionBlog;
	private String tituloArticulo;
	private String contenidoArticulo;
	private Blog blogActual;
	private String cambio;

	@ManagedProperty(value = "#{controllerUsuario}")
	private ControllerUsuario controllerUsuario;
	@EJB
	private ManagerBlog managerBlog;

	@PostConstruct
	public void iniciar() {
		listaBlogs = managerBlog.findBlogByUsuario(controllerUsuario.getIdUsuario());
		JSFUtil.crearMensajeInfo("Blogs: " + listaBlogs.size());
	}

	public void actionListenerConsultarBlogs() {
		listaBlogs = managerBlog.findBlogByUsuario(controllerUsuario.getIdUsuario());
		JSFUtil.crearMensajeInfo("Blogs: " + listaBlogs.size());
	}

	public void actionListenerCrearBlog() {
		String idUsuario = controllerUsuario.getIdUsuario();
		try {
			managerBlog.crearBLog(idUsuario, nombreBlog, descripcionBlog, tituloArticulo, contenidoArticulo);
			JSFUtil.crearMensajeInfo("Blog creado.");
			listaBlogs = managerBlog.findBlogByUsuario(controllerUsuario.getIdUsuario());
		} catch (Exception e) {
			JSFUtil.crearMensajeError(e.getMessage());
			e.printStackTrace();
		}
	}

	public void actionListenerInsertarArticulo() {
		managerBlog.insertarArticulo(blogActual, tituloArticulo, contenidoArticulo);
	}

	public void actionListenerEliminarArticulo(Articulo articulo) {
		managerBlog.eliminarArticulo(articulo);
	}

	public List<Blog> getListaBlogs() {
		return listaBlogs;
	}

	public void setListaBlogs(List<Blog> listaBlogs) {
		this.listaBlogs = listaBlogs;

	}

	public String getNombreBlog() {
		return nombreBlog;
	}

	public void setNombreBlog(String nombreBlog) {
		this.nombreBlog = nombreBlog;
	}

	public String getDescripcionBlog() {
		return descripcionBlog;
	}

	public void setDescripcionBlog(String descripcionBlog) {
		this.descripcionBlog = descripcionBlog;
	}

	public ControllerUsuario getControllerUsuario() {
		return controllerUsuario;
	}

	public void setControllerUsuario(ControllerUsuario controllerUsuario) {
		this.controllerUsuario = controllerUsuario;
	}

	public String getTituloArticulo() {
		return tituloArticulo;
	}

	public void setTituloArticulo(String tituloArticulo) {
		this.tituloArticulo = tituloArticulo;
	}

	public String getContenidoArticulo() {
		return contenidoArticulo;
	}

	public void setContenidoArticulo(String contenidoArticulo) {
		this.contenidoArticulo = contenidoArticulo;
	}

	public Blog getBlogActual() {
		return blogActual;
	}

	public void setBlogActual(Blog blogActual) {
		this.blogActual = blogActual;
	}

}