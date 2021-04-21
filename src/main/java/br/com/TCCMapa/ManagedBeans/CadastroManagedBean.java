package br.com.TCCMapa.ManagedBeans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

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
        this.usuarios = usuarioDao.listAll();
    }

    public void delete(Usuario usuario) {
    	usuarioDao.deletarUsuario(usuario);
        usuarios.remove(usuario);
    }

    public void add() {
    	usuarioDao.inserirUsuario(usuario);
        this.usuarios = usuarioDao.listAll();
        this.usuario = new Usuario();
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
