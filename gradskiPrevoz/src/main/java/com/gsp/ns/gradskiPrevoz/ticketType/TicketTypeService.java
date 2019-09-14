package com.gsp.ns.gradskiPrevoz.ticketType;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gsp.ns.gradskiPrevoz.exceptions.ForbiddenException;
import com.gsp.ns.gradskiPrevoz.exceptions.InvalidParametersException;
import com.gsp.ns.gradskiPrevoz.exceptions.ResourceNotFoundException;

@Service
public class TicketTypeService {
	@Autowired
	private TicketTypeRepository repository;
	
	public TicketTypeService(TicketTypeRepository repo){
		this.repository = repo;
	}
	
	public List<TicketType> findAll(){
		return  repository.findAll();
	}
	public TicketType findById(Long id){
		Optional<TicketType> t = repository.findById(id);
		if (t.isPresent()){
			return t.get();
		}else{
			throw new ResourceNotFoundException();
		}
		
		
	}
	public TicketType addNew(TicketType ticketType){
		if (ticketType == null || 
				ticketType.getDurationInHours() <= 0 || 
				ticketType.getName() == "" ||
				ticketType.getZones().size() == 0 ||
				ticketType.getRequiredStatus() == null){
			throw new InvalidParametersException();
		}
		return repository.save(ticketType);
	}
	public void delete(TicketType ticketType){
		if (ticketType.getPricelistItems().size() == 0){
			repository.delete(ticketType);
		}else{
			throw new ForbiddenException();
		}
	}
	public TicketType modify(TicketType typeFromDb, TicketType typeToModify){
		if (typeToModify == null || 
				typeToModify.getDurationInHours() <= 0 ||
				typeToModify.getName() == "" ||
				typeToModify.getZones().size() == 0 ||
				typeToModify.getRequiredStatus() == null){
			throw new InvalidParametersException();
		}
		typeFromDb.setRequiredStatus(typeToModify.getRequiredStatus());
		typeFromDb.setZones(typeToModify.getZones());
		typeFromDb.setDurationInHours(typeToModify.getDurationInHours());
		typeFromDb.setName(typeToModify.getName());
		return repository.save(typeFromDb);
	}
}
