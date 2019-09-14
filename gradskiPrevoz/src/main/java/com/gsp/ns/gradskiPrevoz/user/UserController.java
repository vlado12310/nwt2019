package com.gsp.ns.gradskiPrevoz.user;

import java.util.ArrayList;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gsp.ns.gradskiPrevoz.authority.Authority.AuthorityNames;
import com.gsp.ns.gradskiPrevoz.exceptions.ResourceNotFoundException;
import com.gsp.ns.gradskiPrevoz.ticket.Ticket;
import com.gsp.ns.gradskiPrevoz.user.User.UserStatus;

@RestController
@RequestMapping(value = "api/users")
public class UserController {

	@Autowired
	UserService userService;
	
	@GetMapping
	public ResponseEntity<List<User>> getAll(){
		return new ResponseEntity<List<User>>(userService.findAll(), HttpStatus.OK);
	}
	
//	@GetMapping(value = "/{email}")
//	public ResponseEntity<User> getByEmail(@PathParam("email") String email){
//		User user = userService.findByEmail(email);
//		if(user==null) {
//			 throw new ResourceNotFoundException(); 
//		}
//		return new ResponseEntity<User>(user, HttpStatus.OK);
//	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteUser(@PathParam("id") Long id){
		User user = userService.getOne(id);
		if(user==null) {
			throw new ResourceNotFoundException();
		} 
		userService.delete(user);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@GetMapping(value="/myTickets", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<List<Ticket>> myTickets(Authentication auth){
		User sessionUser = userService.findByEmail(auth.getName());
		List<Ticket> tickets = sessionUser.getTickets();
		return new ResponseEntity<List<Ticket>>(tickets, HttpStatus.OK);
	}
	
	
	@PostMapping( consumes=MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> regiter(@RequestBody User user){
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(user.getPassword()));
		user.setStatus(UserStatus.STANDARD);
		
		User registeredUser = userService.add(user);
		userService.grantUserAuthority(user.getEmail(), AuthorityNames.USER);
		return new ResponseEntity<>(registeredUser, HttpStatus.OK);
	}
	

}
