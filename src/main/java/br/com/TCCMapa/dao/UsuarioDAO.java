package br.com.TCCMapa.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

import org.hibernate.HibernateException;

import br.com.TCCMapa.model.Usuario;
import br.com.TCCMapa.utils.ConnectionManager;
  
  
public class UsuarioDAO {
  
    private EntityManagerFactory factory = Persistence
                .createEntityManagerFactory("TCCMapa");
    private EntityManager em = factory.createEntityManager();
    
 
    public Usuario getUsuario(String nomeUsuario, String senha) {
 
      try {
        Usuario usuario = (Usuario) em
         .createQuery(
             "SELECT u from Usuario u where u.nomeUsuario = :name and u.senha = :senha")
         .setParameter("name", nomeUsuario)
         .setParameter("senha", senha).getSingleResult();
 
        return usuario;
      } catch (NoResultException e) {
    	  e.printStackTrace();
      }
      return null;
    }
    
    public Usuario getUsuario(String nomeUsuario) {
    	 
        try {
          Usuario usuario = (Usuario) em
           .createQuery(
               "SELECT u from Usuario u where u.nomeUsuario = :name")
           .setParameter("name", nomeUsuario).getSingleResult();
   
          return usuario;
        } catch (NoResultException e) {
              e.printStackTrace();
        }
		return null;
      }
    
    public List<Usuario> listAll(){
    	return this.em.createQuery("SELECT u from Usuario u", Usuario.class).getResultList();
    }
 
  public boolean inserirUsuario(Usuario usuario) {
          try {
              em.getTransaction().begin();
        	  Usuario newUsuario = new Usuario();
              newUsuario.setId(this.getNextIdUsuario());  
              newUsuario.setNomeUsuario(usuario.getNomeUsuario());
              newUsuario.setSenha(usuario.getSenha());
        	  em.persist(newUsuario);
        	  em.getTransaction().commit();
              return true;
          } catch (Exception e) {
                e.printStackTrace();
                return false;
          }
    }
     
    public boolean deletarUsuario(Usuario usuario) {
          try {
              em.getTransaction().begin();  
        	  em.remove(usuario);
        	  em.getTransaction().commit();
              return true;
          } catch (Exception e) {
              e.printStackTrace();
              return false;
          }
    }
    
    public boolean atualizarUsuario(List<Usuario> usuarios) {
        try {
            em.getTransaction().begin();  
      	  	for (Usuario usuario : usuarios) {
				em.merge(usuario);
			}
      	  	em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public int getNextIdUsuario() {
		PreparedStatement ps = null;
		try {
			ConnectionManager connectionManager = new ConnectionManager();
			Connection conn = connectionManager.getConnection();
			
			ps = conn.prepareStatement("SELECT nextVal('usuario_seq')");
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
