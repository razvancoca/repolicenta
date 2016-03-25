package controller;

import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import model.User;
import utils.DBConnection;

public class UserController extends BaseController<User>{
	Logger logger = Logger.getLogger("controller.UserController");

	public UserController(){
		super(User.class);
	}

	public User getUserByUsername(String username){
		EntityManagerFactory emf = DBConnection.getInstance().getConnection();
		EntityManager em = emf.createEntityManager();
		User u = null;

		try{
			u = (User) em.createQuery("select u from User u where username=:username").setParameter("username", username).getSingleResult();
		}catch(Exception e){
 			logger.info("Niciun user gasit cu acest username.");
		}

		return u;
	}

	public User login(String username, String password){
		User u = getUserByUsername(username);

		if(u != null){
			if(u.getPassword().equals(password)){
				return u;
			}
			return null;
		}
		return null;
	}

}
