package br.com.TCCMapa.dao;

import java.math.BigInteger;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.HibernateException;

import br.com.TCCMapa.model.MapaLayers;
import br.com.TCCMapa.model.MapaUsuario;
import br.com.TCCMapa.utils.ConnectionFactory;

public class MapaLayerDAO {

	private EntityManager em;
	
	public MapaLayerDAO() {
		this.em = ConnectionFactory.getConnection();
	}
	
	public void excluirMapaLayerForMapaId() {
		MapaUsuario mapaUsuario = (MapaUsuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("mapaUsuario");
		try {
			List<MapaLayers> mapaLayer = this.obterMapaLayersPorMapaId(mapaUsuario);
			if(!em.getTransaction().isActive()) {
          	  em.getTransaction().begin();
            }
			for (MapaLayers mapaLayers : mapaLayer) {
				em.remove(mapaLayers);
			}
			em.getTransaction().commit();
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    
	}

	public List<MapaLayers> obterMapaLayersPorMapaId(MapaUsuario mapaId) {
		
		try {
			List<MapaLayers> listaMapas = em
				.createQuery("Select m from MapaLayers m where m.mapaUsuario = :mapaId", MapaLayers.class)
				.setParameter("mapaId", mapaId).getResultList();
			return listaMapas;
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
		
	}
	
	public void salvarMapaLayer(String geoJsonLayer) {
		MapaUsuario mapaUsuario = (MapaUsuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("mapaUsuario");
		try {
			
			if(!em.getTransaction().isActive()) {
          	  em.getTransaction().begin();
            }
			MapaLayers newLayer = new MapaLayers();
			newLayer.setId(getNextIdMapaLayer());
			newLayer.setGeoJsonLayer(geoJsonLayer);
			newLayer.setMapaUsuario(mapaUsuario);
			em.persist(newLayer);
			em.getTransaction().commit();
			
		} catch (HibernateException ex) {
			ex.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public List<String> listarLayersPorMapaId() {
		MapaUsuario mapaUsuario = (MapaUsuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("mapaUsuario");
		try {
			
			List<String> layers = em.createQuery(
					"Select m.geoJsonLayer from MapaLayers m where m.mapaUsuario = :mapaId",String.class)
					.setParameter("mapaId", mapaUsuario).getResultList();
		    return layers;

		} catch (HibernateException ex) {
			ex.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int getNextIdMapaLayer() {
		try {
			Query query = em.createNativeQuery("SELECT nextVal('mapalayer_seq')");
			BigInteger retorno = (BigInteger) query.getSingleResult();
			return retorno.intValue();

		} catch (HibernateException ex) {
			ex.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
}
