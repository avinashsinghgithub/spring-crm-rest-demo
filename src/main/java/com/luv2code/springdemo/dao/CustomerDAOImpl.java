package com.luv2code.springdemo.dao;

import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.luv2code.springdemo.entity.Customer;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

	// need to inject the session factory
	@Autowired
	private SessionFactory sessionFactory;
	private Logger logger = Logger.getLogger(getClass().getName());		
	@Override
	public List<Customer> getCustomers(String filter) {
		logger.info("In getCustomers() of CustomerDAOImpl with search text"+filter);
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		Query<Customer> theQuery;
		if(filter == null || filter == "") {
			// create a query  ... sort by last name
			theQuery = 
					currentSession.createQuery("from Customer order by lastName",
												Customer.class);
		}else {
			theQuery = 
					currentSession.createQuery("from Customer where lower(firstName) like :filter "
							+ " or lower(lastName) like :filter order by lastName",
												Customer.class);
			theQuery.setParameter("filter", "%"+filter.toLowerCase()+"%");
		}
		
		
		// execute query and get result list
		List<Customer> customers = theQuery.getResultList();
				
		// return the results		
		return customers;
	}

	@Override
	public void saveCustomer(Customer theCustomer) {

		// get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// save/upate the customer ... finally LOL
		currentSession.saveOrUpdate(theCustomer);
		
	}

	@Override
	public Customer getCustomer(int theId) {

		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// now retrieve/read from database using the primary key
		Customer theCustomer = currentSession.get(Customer.class, theId);
		
		return theCustomer;
	}

	@Override
	public void deleteCustomer(int theId) {

		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// delete object with primary key
		Query theQuery = 
				currentSession.createQuery("delete from Customer where id=:customerId");
		theQuery.setParameter("customerId", theId);
		
		theQuery.executeUpdate();		
	}

}











