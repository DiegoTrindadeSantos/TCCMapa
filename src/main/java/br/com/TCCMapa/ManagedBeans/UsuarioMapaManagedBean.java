package br.com.TCCMapa.ManagedBeans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import br.com.TCCMapa.dao.ManterMapaDAO;
import br.com.TCCMapa.model.MapaUsuario;

@ManagedBean(name = "userMapMB")
@RequestScoped
public class UsuarioMapaManagedBean {
	private ManterMapaDAO manterMapaDAO = new ManterMapaDAO();
	public List<MapaUsuario> listaMapas = new ArrayList<MapaUsuario>();
	public MapaUsuario mapaUsuario = new MapaUsuario();
	
	@PostConstruct
    public void init() {
		this.listaMapas = manterMapaDAO.retornaListaMapaPorUsuario();
    }
	
	public String edit(MapaUsuario mapaUsuario) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("mapaUsuario",mapaUsuario);
		return "/index";
	}
	
	public void add() {
		manterMapaDAO.inserirMapaUsuario(mapaUsuario);
    	FacesMessage message = new FacesMessage("Mapa "+ mapaUsuario.getNomeMapa() + " adicionado com sucesso.");
        FacesContext.getCurrentInstance().addMessage(null, message);
        this.listaMapas = manterMapaDAO.retornaListaMapaPorUsuario();
        this.mapaUsuario = new MapaUsuario();
        
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

	public MapaUsuario getMapaUsuario() {
		return mapaUsuario;
	}

	public void setMapaUsuario(MapaUsuario mapaUsuario) {
		this.mapaUsuario = mapaUsuario;
	}
	
	
	
	
}
