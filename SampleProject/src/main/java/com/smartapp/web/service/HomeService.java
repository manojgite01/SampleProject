package com.smartapp.web.service;

import com.smartapp.web.domain.Employee;
import com.smartapp.web.domain.form.EmployeeForm;

public interface HomeService {
	
	public Employee getEmployee(EmployeeForm form);
}
