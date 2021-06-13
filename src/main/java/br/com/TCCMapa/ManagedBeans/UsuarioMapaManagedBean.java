package br.com.TCCMapa.ManagedBeans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.TCCMapa.dao.ManterMapaDAO;
import br.com.TCCMapa.model.MapaUsuario;

@ManagedBean(name = "userMapMB")
@SessionScoped
public class UsuarioMapaManagedBean {
	private ManterMapaDAO manterMapaDAO = new ManterMapaDAO();
	public List<MapaUsuario> listaMapas = new ArrayList<MapaUsuario>();
	
	@PostConstruct
    public void init() {
		this.listaMapas = manterMapaDAO.retornaListaMapaPorUsuario();
    }

	public ManterMapaDAO getManterMapaDAO() {
		return manterMapaDAO;
	}

	public void setManterMapaDAO(ManterMapaDAO manterMapaDAO) {
		this.manterMapaDAO = manterMapaDAO;
	}

	public List<MapaUsuario> getListaMapas() {
		return listaMapas;
	}

	public void setListaMapas(List<MapaUsuario> listaMapas) {
		this.listaMapas = listaMapas;
	}
	
	
}
