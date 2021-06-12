package br.com.TCCMapa.ManagedBeans;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import br.com.TCCMapa.dao.UsuarioDAO;
import br.com.TCCMapa.model.Usuario;

@ManagedBean(name = "LoginMB")
@RequestScoped
public class LoginManagedBean {

	private UsuarioDAO usuarioDAO = new UsuarioDAO();
	private Usuario usuario = new Usuario();
	private MapaManagedBean mapaMB = new MapaManagedBean();
	   
	  public String envia() {
	         
	    usuario = usuarioDAO.getUsuario(usuario.getNomeUsuario(), usuario.getSenha());
	    if (usuario == null) {
	      usuario = new Usuario();
	      FacesContext.getCurrentInstance().addMessage(
	         null,
	         new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usu�rio n�o Encontrado ou Senha Incorreta!",
	           "Erro no Login!"));
	      return null;
	    } else {
	    	FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuarioLogado",usuario);
	    	mapaMB.init();
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
