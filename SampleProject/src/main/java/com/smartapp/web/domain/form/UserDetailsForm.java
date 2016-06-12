package com.smartapp.web.domain.form;

import java.io.Serializable;

import com.smartapp.web.domain.User;

public class UserDetailsForm implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer userDetId;
	private User user;
	private String fname;
	private String lname;
	private char gender;
	private String email;
	private Integer phoneNumberId;
	private String address;
	private String city;
	private String country;
	private Integer postCode;
	private String username;
	private String password;
	private String errorMsg;
	
	public Integer getUserDetId() {
		return userDetId;
	}
	public void setUserDetId(Integer userDetId) {
		this.userDetId = userDetId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public char getGender() {
		return gender;
	}
	public void setGender(char gender) {
		this.gender = gender;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getPhoneNumberId() {
		return phoneNumberId;
	}
	public void setPhoneNumberId(Integer phoneNumberId) {
		this.phoneNumberId = phoneNumberId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public Integer getPostCode() {
		return postCode;
	}
	public void setPostCode(Integer postCode) {
		this.postCode = postCode;
	}
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
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
}