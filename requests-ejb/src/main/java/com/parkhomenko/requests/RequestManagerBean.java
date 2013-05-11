package com.parkhomenko.requests;

import java.util.Date;
import java.util.List;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.datamodel.DataModelSelection;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Destroy;

@Stateful
@Scope(ScopeType.SESSION)
@Name("requestManager")
public class RequestManagerBean implements RequestManager {
	
	@DataModel
	private List<Request> requestList;
	
	@DataModelSelection
	@In(required=false)
	@Out(required=false)
	private Request req;
	
	@In
	private User user;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	@Factory("requestList")
	@SuppressWarnings("unchecked")
	public void findRequests() {
		Session session = (Session)entityManager.getDelegate();
		Criteria criteria = session.createCriteria(Request.class)
				.addOrder(Order.desc("requestDate"));
		if (user.getRole().getName().equals("user")) {
			criteria.add(Restrictions.eq("sender", user));
		}
		requestList = criteria.list();
		
		req = null;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void currentRequests() {
		Session session = (Session)entityManager.getDelegate();
		Criteria criteria = session.createCriteria(Request.class)
				.add(Restrictions.eq("finished", false))
				.addOrder(Order.desc("requestDate"));
		if (user.getRole().getName().equals("user")) {
			criteria.add(Restrictions.eq("sender", user));
		}
		requestList = criteria.list();
		
		req = null;
	}
	
	@Override
	public void select() {
	}
	
	@Override
	public void delete() {
		requestList.remove(req);
		entityManager.remove(req);
	}
	
	@Override
	public void accept() {
		req.setResponseDate(new Date());
		req.setAdmin(user);
		req.setFinished(true);
		entityManager.merge(req);
	}
	
	@Override
	public void create() {
		req.setSender(user);
		req.setRequestDate(new Date());
		req.setFinished(false);
		entityManager.persist(req);
		requestList.add(req);
	}
	
	@Override
	@Remove
	@Destroy
	public void destroy() {
	}
}