package com.gsp.ns.gradskiPrevoz.statusRequest;


import com.gsp.ns.gradskiPrevoz.exceptions.ConflictException;
import com.gsp.ns.gradskiPrevoz.exceptions.ResourceNotFoundException;
import jdk.net.SocketFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatusRequestService {
    @Autowired
    StatusRequestRepository repo;

    public List<StatusRequest> findAll() { return repo.findAll(); }

    public StatusRequest getOne(Long id){
        Optional<StatusRequest> request = repo.findById(id);
        if(request.isPresent()){
            return repo.getOne(id);
        }else{
            throw new ResourceNotFoundException();
        }
    }

    public StatusRequest save(StatusRequest request) { return repo.save(request); }

    public void delete(StatusRequest request) { repo.delete(request); }

    public StatusRequest approve(StatusRequest request) {
        if(request.isApproved()){
            throw new ConflictException();
        }
        request.setApproved(true);
        StatusRequest retVal = save(request);
        return retVal;
    }

    public StatusRequest sendRequest(StatusRequest request) {
        return save(request);
    }
}
