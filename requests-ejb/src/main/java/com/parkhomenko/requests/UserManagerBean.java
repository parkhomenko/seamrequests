package com.parkhomenko.requests;

import java.util.List;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;

/**
 * 
 * Manipulates with the users
 *
 */
@Stateful
@Scope(ScopeType.SESSION)
@Name("userManager")
public class UserManagerBean implements UserManager {
	
	@DataModel
	private List<User> usersList;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	/**
	 * Shows all registered users to an administrator
	 */
	@Override
	@Factory("usersList")
	@SuppressWarnings("unchecked")
	public void findUsers() {
		Session session = (Session)entityManager.getDelegate();
		usersList = session.createCriteria(User.class)
				.createAlias("role", "r")
				.add(Restrictions.eq("r.id", 2L))
				.list();
	}
	
	@Override
	@Remove
	@Destroy
	public void destroy() {
	}
}