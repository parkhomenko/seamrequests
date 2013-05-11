package com.parkhomenko.requests;

import javax.ejb.Local;

@Local
public interface Authenticator {
	boolean authenticate();
	void logout();
}