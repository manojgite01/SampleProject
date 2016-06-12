package com.smartapp.web.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.smartapp.web.dao.jpa.UserDao;
import com.smartapp.web.domain.Employee;
import com.smartapp.web.domain.User;
import com.smartapp.web.domain.UserDetails;
import com.smartapp.web.domain.UserRole;
import com.smartapp.web.domain.form.EmployeeForm;
import com.smartapp.web.domain.form.UserDetailsForm;
import com.smartapp.web.domain.form.UserForm;

@Service("homeService")
public class HomeServiceImpl implements HomeService {
	
	@Autowired
	@Qualifier("cacheService")
	CacheService cacheService;
	
	@Autowired
	@Qualifier("userDaoImpl")
	UserDao userDao;
	
	public Employee getEmployee(EmployeeForm form) {
		Employee employee = cacheService.getEmployeeMap().get(form.getId() == null?1:form.getId());
		return employee;
	}
	
	public boolean validateUser(UserForm form) {
		User user = userDao.findByUserName(form.getUsername().trim());
		if(user != null){
			PasswordEncoder encoder = new BCryptPasswordEncoder();
			if(encoder.matches(form.getPassword().trim(), user.getPassword())){
				if(user.isEnabled()){
					return true;
				}else{
					form.setErrorMsg("Account locked!");
				}
			}else{
				form.setErrorMsg("Invalid password.");
			}
		}else{
			form.setErrorMsg("Invalid username.");
		}
		return false;
	}
	
	@Transactional(value="transactionManager", propagation=Propagation.REQUIRED)
	public boolean validateAndCreateUser(UserDetailsForm form) {
		List<User> userList = cacheService.getUsersList();
		if(!CollectionUtils.isEmpty(userList)){
			for(User user : userList){
				if(user.getUsername().equalsIgnoreCase(form.getUsername().trim())){
					form.setErrorMsg("Username already taken. Please chose different username.");
					return false;
				}else if(user.getUserDetails().getEmail().equalsIgnoreCase(form.getEmail().trim())){
					form.setErrorMsg("Email already present. Please chose different email to signup.");
					return false;
				}
			}
		}
		Set<UserRole> userRoles = new HashSet<UserRole>();
		User newUser = new User();
		UserDetails details = new UserDetails();
		UserRole role = new UserRole();
		role.setRole("ROLE_USER");
		role.setUser(newUser);
		userRoles.add(role);
		details.setFname(form.getFname().trim());
		details.setLname(form.getLname().trim());
		details.setGender(form.getGender());
		/*details.setAddress(form.getAddress().trim());
		details.setCity(form.getCity().trim());
		details.setCountry(form.getCountry().trim());*/
		details.setEmail(form.getEmail().trim());
		details.setUser(newUser);
		/*details.setPhoneNumberId(form.getPhoneNumberId());
		details.setPostCode(form.getPostCode());*/
		newUser.setUserDetails(details);
		newUser.setUserRole(userRoles);
		newUser.setEnabled(false);
		newUser.setPassword(new BCryptPasswordEncoder().encode(form.getPassword().trim()));
		newUser.setUsername(form.getUsername().trim());
		userDao.save(newUser);
		cacheService.refreshCache();
		return true;
	}
}