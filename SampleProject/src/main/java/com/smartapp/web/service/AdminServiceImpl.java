package com.smartapp.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.smartapp.web.dao.jpa.UserDao;

@Service("adminService")
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	@Qualifier("cacheService")
	CacheService cacheService;
	
	@Autowired
	@Qualifier("userDaoImpl")
	UserDao userDao;
	
}