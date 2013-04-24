package com.parkhomenko.requests;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;

@Stateless
@Name("authenticator")
public class AuthenticatorAction implements Authenticator {

	@In(required=false)
    @Out(required=false, scope=ScopeType.SESSION)
    private User user;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	@Override
	public boolean authenticate() {
		Session session = (Session)entityManager.getDelegate();
		user = (User)session.createCriteria(User.class)
				.add(Restrictions.eq("name", "John Wayne"))
				.uniqueResult();
		return true;
	}
}