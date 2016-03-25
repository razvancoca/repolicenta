package controller;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FlushModeType;

import model.BaseModel;
import utils.DBConnection;

public class BaseController <T extends BaseModel> {
	private final Class<T> clazz;

	EntityManagerFactory emf = DBConnection.getInstance().getConnection();

	public BaseController(Class<T> clazz) {
		this.clazz = clazz;
	}

	public void saveObject(T object) {
		EntityManager em = emf.createEntityManager();
		em.setFlushMode(FlushModeType.COMMIT);
		;
		em.setProperty("org.hibernate.readOnly", true);
		em.getTransaction().begin();

		if (em.contains(object)) {
			em.merge(object);
		} else {
			try {
				object = em.merge(object);
				em.persist(object);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		em.flush();
		em.getTransaction().commit();
		em.close();
	}
	public T getById(int id) {
		EntityManager em = emf.createEntityManager();
		T object = null;
		try {
			object = (T) em
					.createQuery(
							"SELECT e FROM " + clazz.getSimpleName()
									+ " e WHERE id=:id").setParameter("id", id)
					.getSingleResult();

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			em.close();
		}

		return object;
	}

	public List<T> selectAll() {
		EntityManager em = emf.createEntityManager();
		List<T> list = null;
		try {
			list = em.createQuery(
					"SELECT e from " + clazz.getSimpleName() + " e")
					.getResultList();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		em.close();
		return list;
	}

	public void delete(int id) {
		EntityManager em = emf.createEntityManager();
		em.setFlushMode(FlushModeType.COMMIT);
		em.setProperty("org.hibernate.readOnly", true);
		em.getTransaction().begin();
		try {
			T t = em.find(clazz, id);
			if (t != null) {
				em.createQuery(
						"DELETE from " + clazz.getSimpleName()
								+ " where id=:id").setParameter("id", id)
						.executeUpdate();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}finally{
			em.flush();
			em.getTransaction().commit();
			em.close();
		}
	}
}
