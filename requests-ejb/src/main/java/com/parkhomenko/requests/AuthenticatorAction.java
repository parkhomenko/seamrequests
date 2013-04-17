package com.parkhomenko.requests;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.jboss.seam.annotations.In;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;

public class AuthenticatorAction implements Authenticator {
	
	@PersistenceContext
	private Session session;
	
	@In
	private Credentials credentials;
	
	@In
	private Identity identity;

	@Override
	public boolean authenticate() {
		try {
			User user = (User)session.createCriteria(User.class)
					.add(Restrictions.eq("name", credentials.getUsername()))
					.add(Restrictions.eq("password", credentials.getPassword()))
					.uniqueResult();
			
			if (user != null) {
				return true;
			} else {
				return false;
			}
		} catch (NoResultException ex) {
			return false;
		}
	}
}