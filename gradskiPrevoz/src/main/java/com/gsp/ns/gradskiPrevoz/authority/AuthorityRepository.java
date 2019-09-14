package com.gsp.ns.gradskiPrevoz.authority;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long>{
	Authority findByName(String name);
	
}