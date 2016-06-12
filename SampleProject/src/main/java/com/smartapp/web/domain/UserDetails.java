package com.smartapp.web.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user_details")
public class UserDetails implements Serializable{
	
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

	public UserDetails() {
	}

	
	public UserDetails(User user, String fname, String lname,char gender, String email, Integer phoneNumberId,
			String address, String city, String country, Integer postCode) {
		super();
		this.user = user;
		this.fname = fname;
		this.lname = lname;
		this.gender = gender;
		this.email = email;
		this.phoneNumberId = phoneNumberId;
		this.address = address;
		this.city = city;
		this.country = country;
		this.postCode = postCode;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "user_det_id", unique = true, nullable = false)
	public Integer getUserDetId() {
		return this.userDetId;
	}

	public void setUserDetId(Integer userDetId) {
		this.userDetId = userDetId;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "username", nullable = false)
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "fname", nullable = false, length = 100)
	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	@Column(name = "lname", nullable = false, length = 100)
	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	@Column(name = "gender", nullable = false)
	public char getGender() {
		return gender;
	}


	public void setGender(char gender) {
		this.gender = gender;
	}


	@Column(name = "email", nullable = false, length = 45)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "phone_number_id", nullable = true)
	public Integer getPhoneNumberId() {
		return phoneNumberId;
	}

	public void setPhoneNumberId(Integer phoneNumberId) {
		this.phoneNumberId = phoneNumberId;
	}

	@Column(name = "address", nullable = true, length = 255)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "city", nullable = true, length = 255)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "country", nullable = true, length = 255)
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Column(name = "post_code", nullable = true)
	public Integer getPostCode() {
		return postCode;
	}

	public void setPostCode(Integer postCode) {
		this.postCode = postCode;
	}
}