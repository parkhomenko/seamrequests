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
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;

@Stateless
@Name("authenticator")
public class AuthenticatorAction implements Authenticator {

	@In(required=false)
    @Out(required=false, scope=ScopeType.SESSION)
    private User user;
	
	@In
	private Credentials credentials;
	
	@In
	private Identity identity;
	
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
				.add(Restrictions.eq("name", credentials.getUsername()))
				.add(Restrictions.eq("password", credentials.getPassword()))
				.uniqueResult();
		if (user != null) {
			identity.addRole(user.getRole().getName());
			return true;
		}
		else
			return false;
	}
	
	@Override
	public void logout() {
		user = null;
	}
}