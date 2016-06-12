package com.smartapp.web.service;

import com.smartapp.web.domain.Employee;
import com.smartapp.web.domain.form.EmployeeForm;
import com.smartapp.web.domain.form.UserDetailsForm;
import com.smartapp.web.domain.form.UserForm;

public interface HomeService {
	
	public Employee getEmployee(EmployeeForm form);
	public boolean validateUser(UserForm form);
	public boolean validateAndCreateUser(UserDetailsForm form);
}
