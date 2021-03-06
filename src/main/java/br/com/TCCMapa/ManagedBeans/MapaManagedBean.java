package br.com.TCCMapa.ManagedBeans;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import br.com.TCCMapa.dao.MapaLayerDAO;
import br.com.TCCMapa.dao.ManterMapaDAO;
import br.com.TCCMapa.model.MapaUsuario;
import br.com.TCCMapa.model.Usuario;

@ManagedBean(name = "MapaMB")
@SessionScoped
public class MapaManagedBean {
	private UploadedFile file;
	private MapaLayerDAO arquivosDAO = new MapaLayerDAO();
	private ManterMapaDAO manterMapaDAO = new ManterMapaDAO();
	public List<MapaUsuario> listaMapas = new ArrayList<MapaUsuario>();
	public LoginManagedBean loginMB = new LoginManagedBean();
	public int idInserido;
	
	@PostConstruct
    public void init() {
		this.listaMapas = manterMapaDAO.retornaListaMapaPorUsuario();
    }

	public UploadedFile getFile() {
        return file;
    }
 
    public void setFile(UploadedFile file) {
        this.file = file;
    }
 
    public void upload() {
    	if (file != null) {
        	FacesMessage message = new FacesMessage("Successful"+ file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
    
    public void recebeJsonFormas() {
    	Map<String, String> requestParamMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
    	String geoJson = requestParamMap.get("geoJson");
    	arquivosDAO.salvarMapaLayer(geoJson);
    }
    
    public void salvar() {
    	arquivosDAO.excluirMapaLayerForMapaId();
    	PrimeFaces.current().executeScript("Salvar()");
    	FacesMessage msg = new FacesMessage("Salvo com sucesso.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
	public void handleFileUpload(FileUploadEvent event) {
		FacesMessage msg = new FacesMessage("Successful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void carregar() {
        List<String> listaLayers = arquivosDAO.listarLayersPorMapaId();
        for (String layer : listaLayers) {
        	PrimeFaces.current().executeScript("Carregar('"+layer+"')");			
		}
        FacesMessage msg = new FacesMessage("Carregado com sucesso.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public String limparMapa() {
    	arquivosDAO.excluirMapaLayerForMapaId();
    	FacesMessage msg = new FacesMessage("Todas as informa??es foram exclu?das.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    	return "/main";
    }
    
    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    public String listarMapas() {
    	this.listaMapas = manterMapaDAO.retornaListaMapaPorUsuario();
    	return "/manterMapa";
    }
    
    public List<MapaUsuario> getListaMapas() {
		return listaMapas;
	}

	public void setListaMapas(List<MapaUsuario> listaMapas) {
		this.listaMapas = listaMapas;
	}

	public String sair() {
    	return "/main";
    }
	
	public String voltar() {
		loginMB.setUsuario((Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuarioLogado"));
		return "/manterMapa";
    }

	public int getIdInserido() {
		return idInserido;
	}

	public void setIdInserido(int idInserido) {
		this.idInserido = idInserido;
	}
    
    
}
