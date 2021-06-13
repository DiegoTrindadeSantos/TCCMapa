package br.com.TCCMapa.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ConnectionFactory {

	private static EntityManagerFactory factory = Persistence
            .createEntityManagerFactory("TCCMapa");
	
	public static EntityManager getConnection()  {
		return factory.createEntityManager();
	}
}
