package com.gsp.ns.gradskiPrevoz.statusRequest;

import com.gsp.ns.gradskiPrevoz.exceptions.*;
import com.gsp.ns.gradskiPrevoz.fileStorage.FileProperties;
import com.gsp.ns.gradskiPrevoz.ticket.Ticket;
import com.gsp.ns.gradskiPrevoz.ticketType.TicketType;
import com.gsp.ns.gradskiPrevoz.user.User;
import com.gsp.ns.gradskiPrevoz.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Paths;
import java.util.List;
@RestController
@RequestMapping(value = "/api/statusRequest")
public class StatusRequestController {

    @Autowired
    StatusRequestService statusRequestService;
    @Autowired
    UserService userService;
    @Autowired
    FileProperties fileStorageProperties;


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<StatusRequest> sendRequest(@RequestBody StatusRequest request, Authentication auth){
        try{
            User sessionUser = userService.findByEmail(auth.getName());
            request.setUser(sessionUser);
            request.setApproved(false);
            String url = Paths.get(Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize().toString(),
                    sessionUser.getIdUser()+"").toString();
            request.setImgUrl(url);
            StatusRequest req = statusRequestService.sendRequest(request);
            return new ResponseEntity<StatusRequest>(req, HttpStatus.CREATED);
        }catch(NoFundsException e){
            throw e;
        }catch(ResourceNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch(RequiredStatusException e){
            throw e;
        }
    }
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<StatusRequest>> getRequests(){
        List<StatusRequest> requests = statusRequestService.findAll();
        return new ResponseEntity<List<StatusRequest>>(requests, HttpStatus.OK);
    }
    @GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StatusRequest> getRequestById(@PathVariable("id") Long id){
        try{
            StatusRequest request = statusRequestService.getOne(id);
            return new ResponseEntity<StatusRequest>(request, HttpStatus.OK);
        }catch(ResourceNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
    @DeleteMapping(value="/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteRequest(@PathVariable("id") Long id, Authentication auth){
        try{
            StatusRequest req = statusRequestService.getOne(id);
            statusRequestService.delete(req);
            return new ResponseEntity<>( HttpStatus.OK);
        }catch(ResourceNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch(ForbiddenException e){
            return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
        }

    }
    @PutMapping(value = "/approve", produces = MediaType.APPLICATION_JSON_VALUE , consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StatusRequest> approveRequest(@RequestBody StatusRequest request, Authentication auth){
        try{
            StatusRequest req = statusRequestService.getOne(request.getId());
            User user = userService.getOne(request.user.getIdUser());
            userService.setUserAuthority(user, request.type);
            StatusRequest retVal = statusRequestService.approve(req);
            return new ResponseEntity<StatusRequest>(retVal, HttpStatus.OK);
        }catch(ResourceNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
