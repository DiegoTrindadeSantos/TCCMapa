package br.com.TCCMapa.ManagedBeans;

import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import br.com.TCCMapa.dao.ArquivosDAO;

@ManagedBean(name = "MapaMB")
@SessionScoped
public class MapaManagedBean {
	private UploadedFile file;
	private ArquivosDAO arquivosDAO = new ArquivosDAO();
	public int idInserido;
	
	

	public UploadedFile getFile() {
        return file;
    }
 
    public void setFile(UploadedFile file) {
        this.file = file;
    }
 
    public void upload() {
    	if (file != null) {
        	FacesMessage message = new FacesMessage("Successful", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
    
    public void recebeJsonFormas() {
    	Map<String, String> requestParamMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
    	String geoJson = requestParamMap.get("geoJson");
    	arquivosDAO.salvarGeoJsonLayer(geoJson);
    }
    
    public void salvar() {
    	arquivosDAO.excluirGeoJsonUsuario();
    	PrimeFaces.current().executeScript("Salvar()");
    	FacesMessage msg = new FacesMessage("Salvo com sucesso.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

	public void handleFileUpload(FileUploadEvent event) {
		FacesMessage msg = new FacesMessage("Successful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void carregar() {
        List<String> listaGeoFormas = arquivosDAO.obterGeoJsonFormas();
        for (String geoForma : listaGeoFormas) {
        	PrimeFaces.current().executeScript("Carregar('"+geoForma+"')");			
		}
        FacesMessage msg = new FacesMessage("Carregado com sucesso.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public String limparMapa() {
    	arquivosDAO.excluirGeoJsonUsuario();
    	FacesMessage msg = new FacesMessage("Todas as informações foram excluídas.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    	return "/main";
    }
    
    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    public String sair() {
    	return "/main";
    }

	public int getIdInserido() {
		return idInserido;
	}

	public void setIdInserido(int idInserido) {
		this.idInserido = idInserido;
	}
    
    
}
