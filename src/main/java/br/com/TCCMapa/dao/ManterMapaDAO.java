package br.com.TCCMapa.dao;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

import br.com.TCCMapa.model.MapaUsuario;
import br.com.TCCMapa.model.Usuario;

public class ManterMapaDAO {

	private EntityManagerFactory factory = Persistence
            .createEntityManagerFactory("TCCMapa");
	private EntityManager em = factory.createEntityManager();
	
	public List<MapaUsuario> retornaListaMapaPorUsuario(){

		Usuario usuarioLogado = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuarioLogado");
	      try {
	    	  List<MapaUsuario> mapaUsuario = new ArrayList<MapaUsuario>();
	    	  mapaUsuario = (ArrayList<MapaUsuario>) em
	         .createQuery(
	             "SELECT mu from MapaUsuario mu where mu.usuario = :usuario")
	         .setParameter("usuario", usuarioLogado).getResultList();
	 
	        return mapaUsuario;
	      } catch (NoResultException e) {
	    	  e.printStackTrace();
	      }
	      return null;
	    
	}
	
}
