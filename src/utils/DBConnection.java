package utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FlushModeType;
import javax.persistence.Persistence;

public class DBConnection {
	private static EntityManagerFactory emf;
	private static DBConnection singleton;
	private static EntityManager em;

	public static DBConnection getInstance() {
		if (singleton == null) {
			singleton = new DBConnection();
		}
		return singleton;
	}

	public EntityManagerFactory getConnection() {
		if(emf==null)
			emf = Persistence.createEntityManagerFactory("ContabilitatePU");
		return emf;
	}

	public void openConnection() {
		em = emf.createEntityManager();
		em.setFlushMode(FlushModeType.COMMIT);
		em.setProperty("org.hibernate.readOnly", false);
		em.getTransaction().begin();
	}

	public void closeConnection() {
		em.getTransaction().commit();
		em.close();
	}

	public static void closeEMF(){
		emf.close();
	}
}
