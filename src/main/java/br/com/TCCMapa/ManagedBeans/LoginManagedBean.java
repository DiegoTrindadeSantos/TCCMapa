package br.com.TCCMapa.ManagedBeans;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import br.com.TCCMapa.dao.UsuarioDAO;
import br.com.TCCMapa.model.MapaUsuario;
import br.com.TCCMapa.model.Usuario;
import br.com.TCCMapa.utils.Cripto;

@ManagedBean(name = "LoginMB")
@RequestScoped
public class LoginManagedBean {

	private UsuarioDAO usuarioDAO = new UsuarioDAO();
	public Usuario usuario = new Usuario();
	public MapaUsuario mapaUsuario = new MapaUsuario();
	   
	public String envia() {
	    
	  usuario = usuarioDAO.getUsuario(usuario.getNomeUsuario(), Cripto.Md5(usuario.getSenha()));
	  if (usuario == null) {
		  usuario = new Usuario();
	      FacesContext.getCurrentInstance().addMessage(
	         null,
	         new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usu?rio n?o Encontrado ou Senha Incorreta!",
	           "Erro no Login!"));
	      return null;
	  } else {
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuarioLogado",usuario);
		  return "/manterMapa";
	  }
	         
	         
	}
	  
	public String edit(MapaUsuario mapaUsuario) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("mapaUsuario",mapaUsuario);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuarioLogado",mapaUsuario.getUsuario());
		this.setMapaUsuario(mapaUsuario);
		this.setUsuario(usuario);
		return "/index";
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

	public MapaUsuario getMapaUsuario() {
		return mapaUsuario;
	}

	public void setMapaUsuario(MapaUsuario mapaUsuario) {
		this.mapaUsuario = mapaUsuario;
	}
	  
	
	
}
