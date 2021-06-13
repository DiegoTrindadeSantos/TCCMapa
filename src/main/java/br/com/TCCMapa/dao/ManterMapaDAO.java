package br.com.TCCMapa.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

import org.hibernate.HibernateException;

import br.com.TCCMapa.model.MapaUsuario;
import br.com.TCCMapa.model.Usuario;
import br.com.TCCMapa.utils.ConnectionManager;

public class ManterMapaDAO {

	private EntityManagerFactory factory = Persistence
            .createEntityManagerFactory("TCCMapa");
	private EntityManager em = factory.createEntityManager();
	
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
            em.getTransaction().begin();
            MapaUsuario newMapaUsuario = new MapaUsuario();
            newMapaUsuario.setId(this.getNextIdMapaUsuario());  
            newMapaUsuario.setNomeMapa(mapaUsuario.getNomeMapa());
      	  em.persist(newMapaUsuario);
      	  em.getTransaction().commit();
            return true;
        } catch (Exception e) {
              e.printStackTrace();
              return false;
        }
	}
	
	public int getNextIdMapaUsuario() {
		PreparedStatement ps = null;
		try {
			ConnectionManager connectionManager = new ConnectionManager();
			Connection conn = connectionManager.getConnection();
			
			ps = conn.prepareStatement("SELECT nextVal('mapausuario_seq')");
			ResultSet rs = ps.executeQuery();
			if (rs != null) {
				int nextVal = 0 ;
			    while (rs.next()) {
			    	nextVal = rs.getInt(1);
			    }
			    rs.close();
			    return nextVal;
			}
			ps.close();

		} catch (HibernateException ex) {
			ex.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}
	
}
