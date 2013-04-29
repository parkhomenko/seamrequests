package com.parkhomenko.requests;

import java.util.List;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.datamodel.DataModelSelection;
import org.jboss.seam.annotations.Out;

@Stateful
@Scope(ScopeType.SESSION)
@Name("requestManager")
public class RequestManagerBean implements RequestManager {

	@DataModel
	private List<Request> requestList;
	
	@DataModelSelection
	@Out(required=false)
	private Request request;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	@Factory("requestList")
	@SuppressWarnings("unchecked")
	public void findRequests() {
		Session session = (Session)entityManager.getDelegate();
		requestList = session.createCriteria(Request.class)
				.addOrder(Order.desc("requestDate"))
				.list();
	}
	
	@Override
	public void delete() {
		requestList.remove(request);
		entityManager.remove(request);
	}

	@Override
	@Remove
	public void destroy() {
	}
}