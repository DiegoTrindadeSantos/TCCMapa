package br.com.TCCMapa.dao;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.hibernate.HibernateException;

import br.com.TCCMapa.model.Usuario;
import br.com.TCCMapa.utils.ConnectionFactory;
  
  
public class UsuarioDAO {
  
    private EntityManager em;
    
    public UsuarioDAO() {
    	this.em = ConnectionFactory.getConnection();
    }
 
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
              if(!em.getTransaction().isActive()) {
            	  em.getTransaction().begin();
              }
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
        	  if(!em.getTransaction().isActive()) {
            	  em.getTransaction().begin();
              }
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
        	if(!em.getTransaction().isActive()) {
          	  em.getTransaction().begin();
            } 
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
		try {
			Query query = em.createNativeQuery("SELECT nextVal('usuario_seq')");
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
