package com.luv2code.springboot.thymeleafdemo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luv2code.springboot.thymeleafdemo.dao.CustomerRepository;
import com.luv2code.springboot.thymeleafdemo.entity.Customer;

@Service
public class CustomerServiceImpl implements CustomerService {

	private CustomerRepository customerRepository;
	
	@Autowired
	public CustomerServiceImpl(CustomerRepository theEmployeeRepository) {
		customerRepository = theEmployeeRepository;
	}
	
	
	@Override
	public List<Customer> findAll() {
		return customerRepository.findAllByOrderByLastNameAsc();
	}

	@Override
	public Customer findById(int theId) {
		Optional<Customer> result = customerRepository.findById(theId);
		
		Customer theEmployee = null;
		
		if (result.isPresent()) {
			theEmployee = result.get();
		}
		else {
			// we didn't find the employee
			throw new RuntimeException("Did not find employee id - " + theId);
		}
		
		return theEmployee;
	}

	@Override
	public void save(Customer theEmployee) {
		customerRepository.save(theEmployee);
	}

	@Override
	public void deleteById(int theId) {
		customerRepository.deleteById(theId);
	}

	@Override
	public List<Customer> searchBy(String theName) {
		
		List<Customer> results = null;
		
		if (theName != null && (theName.trim().length() > 0)) {
			results = customerRepository.findByFirstNameContainsOrLastNameContainsAllIgnoreCase(theName, theName);
		}
		else {
			results = findAll();
		}
		
		return results;
	}

}






