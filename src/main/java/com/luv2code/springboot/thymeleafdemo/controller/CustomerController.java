package com.luv2code.springboot.thymeleafdemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.luv2code.springboot.thymeleafdemo.entity.Customer;
import com.luv2code.springboot.thymeleafdemo.service.CustomerService;

@Controller
@RequestMapping("/employees")
public class CustomerController {

	
	private CustomerService customerService;
	@Autowired
	public CustomerController(CustomerService theEmployeeService) {
		customerService = theEmployeeService;
	}
	
	// add mapping for "/list"

	@GetMapping("/list")
	public String listEmployees(Model theModel) {
		
		// get employees from db
		List<Customer> theEmployees = customerService.findAll();
		
		
		// add to the spring model
		theModel.addAttribute("employees", theEmployees);
		
		return "/employees/list-employees";
	}
	
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		
		// create model attribute to bind form data
		Customer theEmployee = new Customer();
		
		theModel.addAttribute("employee", theEmployee);
		
		return "/employees/employee-form";
	}

	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("employeeId") int theId,
									Model theModel) {
		
		// get the employee from the service
		Customer theEmployee = customerService.findById(theId);
		
		// set employee as a model attribute to pre-populate the form
		theModel.addAttribute("employee", theEmployee);
		
		// send over to our form
		return "/employees/employee-form";			
	}
	
	
	@PostMapping("/save")
	public String saveEmployee(@ModelAttribute("employee") Customer theEmployee) {
		
		// save the employee
		customerService.save(theEmployee);
		
		// use a redirect to prevent duplicate submissions
		return "redirect:/employees/list";
	}
	
	
	@GetMapping("/delete")
	public String delete(@RequestParam("employeeId") int theId) {
		
		// delete the employee
		customerService.deleteById(theId);
		
		// redirect to /employees/list
		return "redirect:/employees/list";
		
	}
	
	@GetMapping("/search")
	public String delete(@RequestParam("employeeName") String theName,
						 Model theModel) {
		
		// delete the employee
		List<Customer> theEmployees = customerService.searchBy(theName);
		
		// add to the spring model
		theModel.addAttribute("employees", theEmployees);
		
		// send to /employees/list
		return "/employees/list-employees";
		
	}
	
}


















