package com.gsp.ns.gradskiPrevoz.authority;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorityService {
	@Autowired
	AuthorityRepository repo;
	
	public List<Authority> findAll(){
		return repo.findAll();
	}
	public Authority getOne(Long id) {
		return repo.getOne(id);
	}
	
	public void saveAuthority(Authority authority) {
		repo.save(authority);
	}
	public Authority findByName(String name) {
		return repo.findByName(name);
	}
}


