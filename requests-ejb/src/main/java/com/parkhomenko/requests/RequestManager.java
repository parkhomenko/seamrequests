package com.parkhomenko.requests;

import javax.ejb.Local;

@Local
public interface RequestManager {
	public void findRequests();
	public void currentRequests();
	public void select();
	public void delete();
	public void accept();
	public void create();
	public void destroy();
}