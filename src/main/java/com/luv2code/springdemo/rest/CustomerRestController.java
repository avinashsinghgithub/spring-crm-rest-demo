package com.luv2code.springdemo.rest;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.luv2code.springdemo.entity.Customer;
import com.luv2code.springdemo.service.CustomerService;

@RestController
@RequestMapping("/api")
public class CustomerRestController {
	
	@Autowired
	private CustomerService customerService;
	private Logger logger = Logger.getLogger(getClass().getName());
	@GetMapping("/customers")
	public List<Customer> getCustomers(@RequestParam(required=false) String theSearchName){
		logger.info("Logging the controller getCustomers() method call start");
		return customerService.getCustomers(theSearchName);
	}
	@GetMapping("/customers/{customerId}")
	public Customer getCustomers(@PathVariable int customerId){
		
		Customer theCustomer = customerService.getCustomer(customerId);
		
		if(theCustomer == null ) {
			throw new CustomerNotFoundException("Customer id not found - "+customerId);
		}
		return theCustomer;
	}
	
	// add mapping for POST /customers - add new customer
	@PostMapping("/customers")
	public Customer addCustomers(@RequestBody Customer theCustomer) {
		// for to save the customer
		theCustomer.setId(0);
		customerService.saveCustomer(theCustomer);
		
		return theCustomer;
	}
	
	@PutMapping("/customers")
	public Customer updateCustomer(@RequestBody Customer theCustomer) {
		customerService.saveCustomer(theCustomer);
		return theCustomer;
	}
	@DeleteMapping("/customers/{customerId}")
	public String deleteCustomer(@PathVariable int customerId){
		
		Customer tempCustomer = customerService.getCustomer(customerId);
		if(tempCustomer == null) {
			throw new CustomerNotFoundException("Customer id not found - "+customerId); 
		}
		customerService.deleteCustomer(customerId);
		return "Deleted customer id - "+customerId;
	}
}
