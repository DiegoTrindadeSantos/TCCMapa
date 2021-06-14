package br.com.TCCMapa.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.hibernate.HibernateException;

import br.com.TCCMapa.model.MapaUsuario;
import br.com.TCCMapa.model.Usuario;
import br.com.TCCMapa.utils.ConnectionFactory;

public class ManterMapaDAO {

	private EntityManager em;
	
	public ManterMapaDAO() {
		this.em = ConnectionFactory.getConnection();
	}
	
	@SuppressWarnings("unchecked")
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
	
	public boolean inserirMapaUsuario(MapaUsuario mapaUsuario) {
        try {
        	if(!em.getTransaction().isActive()) {
          	  em.getTransaction().begin();
            }
            MapaUsuario newMapaUsuario = new MapaUsuario();
            newMapaUsuario.setId(this.getNextIdMapaUsuario());  
            newMapaUsuario.setNomeMapa(mapaUsuario.getNomeMapa());
            newMapaUsuario.setUsuario((Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuarioLogado"));
      	  	em.persist(newMapaUsuario);
      	  	em.getTransaction().commit();
            return true;
        } catch (Exception e) {
              e.printStackTrace();
              return false;
        }
	}
	
	 public boolean deletarMapaUsuario(MapaUsuario mapaUsuario) {
		try {
			if(!em.getTransaction().isActive()) {
          	  em.getTransaction().begin();
            }  
			em.remove(mapaUsuario);
			em.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
   }
	
	public int getNextIdMapaUsuario() {
		try {
			Query query = em.createNativeQuery("SELECT nextVal('mapausuario_seq')");
			BigInteger retorno = (BigInteger) query.getSingleResult();
			return retorno.intValue();

		} catch (HibernateException ex) {
			ex.printStackTrace();
		}
		return 0;
	}
	
}
