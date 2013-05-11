package com.parkhomenko.requests;

import javax.ejb.Local;

@Local
public interface UserManager {
	void findUsers();
	void destroy();
}