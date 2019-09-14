package com.gsp.ns.gradskiPrevoz.user;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepository.findByEmail(email);

    if (user == null) {
      throw new UsernameNotFoundException(String.format("No user found with username '%s'.", email));
    } else {
	
    	List<GrantedAuthority> grantedAuthorities = user.getUserAuthorities().
    			stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthority().getName()))
                .collect(Collectors.toList());
    	
    	return new org.springframework.security.core.userdetails.User(
    		  user.getEmail(),
    		  user.getPassword(),
    		  grantedAuthorities);
    }
  }

}
