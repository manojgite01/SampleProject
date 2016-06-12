package com.smartapp.web.dao.jpa;

import com.smartapp.web.domain.User;

public interface UserDao {

	User findByUserName(String username);

}