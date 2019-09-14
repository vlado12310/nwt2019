package com.gsp.ns.gradskiPrevoz.user;



import java.util.List;


import com.gsp.ns.gradskiPrevoz.exceptions.ForbiddenException;
import org.hibernate.usertype.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.gsp.ns.gradskiPrevoz.authority.Authority;
import com.gsp.ns.gradskiPrevoz.authority.AuthorityService;
import com.gsp.ns.gradskiPrevoz.exceptions.ResourceNotFoundException;
import com.gsp.ns.gradskiPrevoz.exceptions.UserAlreadyExists;
import com.gsp.ns.gradskiPrevoz.authority.Authority.AuthorityNames;
import com.gsp.ns.gradskiPrevoz.security.UserAuthority;


@Service
public class UserService {

	@Autowired
	UserRepository repo;
	
	@Autowired
	AuthorityService authService;
	
	public List<User> findAll(){
		return repo.findAll();
	}
	public User getOne(Long id) {
		return repo.getOne(id);
	}

	public User update(User user) {
		return repo.save(user);
	}
	
	@PreAuthorize("hasRole('ADMIN') or (!hasRole('USER') and !hasRole('CONDUCTER'))")
	public User add(User user) {
		if(findByEmail(user.getEmail())!=null){
			throw new UserAlreadyExists();
		}
		return repo.save(user);
	}
	
	public void delete(User user) {
		repo.delete(user);
	}
	
	public User findByEmail(String email) {
		return repo.findByEmail(email);
	}
	
	@PreAuthorize("hasRole('ADMIN') or #aName.toString() ==  'USER'")
	public void grantUserAuthority(String email, AuthorityNames aName) {
		//User already has authority
		String authName = aName.toString();
		User user = findByEmail(email);
		if(user==null) {
			throw new ResourceNotFoundException();
		}
		if (user.getUserAuthorities().stream().filter(auth -> auth.getAuthority().getName() == authName).findFirst().isPresent()) {
			return;
		}
		Authority authority = authService.findByName(authName);
		UserAuthority userAuthority = new UserAuthority();
		if(authority==null) {
			authority = new Authority();
			authority.setName(authName);
		} 
		userAuthority.setAuthority(authority);
		userAuthority.setUser(user);
		user.getUserAuthorities().add(userAuthority);
		authority.getUserAuthorities().add(userAuthority);
		repo.save(user);
		authService.saveAuthority(authority);
	}
	
	public User getLoggedUser(){
		try {
			return findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
		} catch (Exception e) {
			if(e instanceof NullPointerException ) {
				return null;
			} 
			e.printStackTrace();
			throw e;
		}
	}
	public User setUserAuthority(User user, User.UserStatus status){
		if(status == User.UserStatus.STUDENT){
			user.setStatus(User.UserStatus.STUDENT);
		}
		else if(status ==User.UserStatus.ELDERLY){
			user.setStatus(User.UserStatus.ELDERLY);
		}
		else if(status ==User.UserStatus.STANDARD){
			user.setStatus(User.UserStatus.STANDARD);
		}
		else{
			throw new ForbiddenException();
		}
		return update(user);
	}
}
