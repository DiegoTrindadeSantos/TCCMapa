package br.com.TCCMapa.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

import br.com.TCCMapa.model.Usuario;
  
  
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
                em.persist(usuario);
                return true;
          } catch (Exception e) {
                e.printStackTrace();
                return false;
          }
    }
     
    public boolean deletarUsuario(Usuario usuario) {
          try {
                em.remove(usuario);
                return true;
          } catch (Exception e) {
                e.printStackTrace();
                return false;
          }
    }
  
}
