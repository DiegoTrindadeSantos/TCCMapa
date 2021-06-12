package br.com.TCCMapa.ManagedBeans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.TCCMapa.dao.UsuarioDAO;
import br.com.TCCMapa.model.Usuario;

@ManagedBean(name = "cadastroMB")
@SessionScoped
public class CadastroManagedBean {

	public List<Usuario> usuarios;

    public Usuario usuario = new Usuario();

    public UsuarioDAO usuarioDao = new UsuarioDAO();

    @PostConstruct
    public void init() {
    	this.usuarios = new ArrayList<Usuario>();
    }

    public void delete(Usuario usuario) {
    	usuarioDao.deletarUsuario(usuario);
        usuarios.remove(usuario);
    }

    public void add() {
    	usuarioDao.inserirUsuario(usuario);
    	FacesMessage message = new FacesMessage("Usuário ", usuario.getNomeUsuario() + " adicionado com sucesso.");
        FacesContext.getCurrentInstance().addMessage(null, message);
        this.usuarios = usuarioDao.listAll();
        this.usuario = new Usuario();
        
    }
    
    public String manterUsuario () {
    	Usuario usuarioLogado = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuarioLogado");
    	this.usuarios = new ArrayList<Usuario>();
    	this.usuarios.add(usuarioLogado);
    	return "/manterUsuario";
    }
    
    public void update() {
    	usuarioDao.atualizarUsuario(usuarios);
    	FacesMessage message = new FacesMessage("Atualizado com sucesso");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    public String voltar() {
    	return "/main";
    }

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public UsuarioDAO getUsuarioDao() {
		return usuarioDao;
	}

	public void setUsuarioDao(UsuarioDAO usuarioDao) {
		this.usuarioDao = usuarioDao;
	}
    
    
	
}
