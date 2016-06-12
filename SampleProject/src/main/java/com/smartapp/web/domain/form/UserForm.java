package com.smartapp.web.domain.form;

import java.io.Serializable;
import java.util.Set;

import com.smartapp.web.domain.UserRole;

public class UserForm implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String username;
	private String password;
	private boolean enabled;
	private Set<UserRole> userRole;
	private String errorMsg;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public Set<UserRole> getUserRole() {
		return userRole;
	}
	public void setUserRole(Set<UserRole> userRole) {
		this.userRole = userRole;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
}
