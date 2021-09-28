package com.luv2code.springboot.thymeleafdemo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.luv2code.springboot.thymeleafdemo.entity.CrmUser;
import com.luv2code.springboot.thymeleafdemo.entity.User;


public interface UserService extends UserDetailsService {
	User findByUserName(String userName);

    void save(CrmUser crmUser);
}
