package com.parkhomenko.requests;

import javax.ejb.Stateless;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.log.Log;

@Stateless
@Name("authenticator")
public class AuthenticatorAction implements Authenticator {

	@In(required=false)   
    @Out(required=false, scope = ScopeType.SESSION)
    private User user;
	
	@Logger
	private Log log;
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	@Override
	public boolean authenticate() {
		log.info("Authenticator", "In authenticate");
		user.setName("Hi man");
		return true;
	}
}