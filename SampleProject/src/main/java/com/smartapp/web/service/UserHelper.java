package com.smartapp.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.smartapp.web.dao.jpa.UserDao;
import com.smartapp.web.domain.User;
import com.smartapp.web.domain.UserRole;

@Service("userHelper")
public class UserHelper {
	
	@Autowired
	@Qualifier("userDaoImpl")
	UserDao userDao;
	
	public User findByUserName(String username) {
		return userDao.findByUserName(username);
	}
	
	public boolean isAuthorizedAdmin(User user){
		if(user != null && !CollectionUtils.isEmpty(user.getUserRole())){
			for(UserRole role: user.getUserRole()){
				if(role.getRole().equals("ROLE_ADMIN")){
					return true;
				}
			}
		}
		return false;
	}
}
