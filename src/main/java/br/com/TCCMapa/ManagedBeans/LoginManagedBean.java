package br.com.TCCMapa.ManagedBeans;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import br.com.TCCMapa.dao.ManterMapaDAO;
import br.com.TCCMapa.dao.UsuarioDAO;
import br.com.TCCMapa.model.Usuario;

@ManagedBean(name = "LoginMB")
@RequestScoped
public class LoginManagedBean {

	private UsuarioDAO usuarioDAO = new UsuarioDAO();
	private Usuario usuario = new Usuario();
	public UsuarioMapaManagedBean userMapMB = new UsuarioMapaManagedBean();
	private ManterMapaDAO manterMapaDAO = new ManterMapaDAO();
	   
	  public String envia() {
	         
	    usuario = usuarioDAO.getUsuario(usuario.getNomeUsuario(), usuario.getSenha());
	    if (usuario == null) {
	      usuario = new Usuario();
	      FacesContext.getCurrentInstance().addMessage(
	         null,
	         new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário não Encontrado ou Senha Incorreta!",
	           "Erro no Login!"));
	      return null;
	    } else {
	    	FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuarioLogado",usuario);
	    	return "/manterMapa";
	    }
	         
	         
	  }
	  
	  public String cadastrar() {
		  return "/cadastrar";
	  }
	 
	  public Usuario getUsuario() {
	    return usuario;
	  }
	 
	  public void setUsuario(Usuario usuario) {
	    this.usuario = usuario;
	  }
	
}
