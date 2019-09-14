package com.gsp.ns.gradskiPrevoz.zone;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gsp.ns.gradskiPrevoz.exceptions.ConflictException;
import com.gsp.ns.gradskiPrevoz.exceptions.ResourceNotFoundException;
@Service
public class ZoneService {
	@Autowired
	ZoneRepository zoneRepo;
	
	public List<Zone> findAll(){
		return zoneRepo.findAll();
	}
	
	public Zone findOne(Long id){
		return zoneRepo.getOne(id);
	}
	
	public Zone save(Zone zone){
		Optional<Zone> zoneDB = zoneRepo.findByName(zone.getName());
		if (zoneDB.isPresent()){
			throw new ConflictException();
		}
		return zoneRepo.save(zone);
	}
	public Zone edit(Zone zone, Zone newZone){
		Optional<Zone> zoneDB = zoneRepo.findByName(newZone.getName());
		if (zoneDB.isPresent() && zoneDB.get().getId() != zone.getId()){
			throw new ConflictException();
		}
		zone.setName(newZone.getName());
		return zoneRepo.save(zone);
		
	}
	
	public void delete(Zone zone){
		System.out.println("\n\n\n\n\n" + zone.getLines().size() + "\n\n\n\n\n\n\n");
		if (zone.getLines().size() == 0){
			zoneRepo.delete(zone);
		}else{
			throw new ConflictException("Neuspesno brisanje! Morate prvo obrisati sve linije koje su povezane sa ovom zonom!");
		}
	}
	
}
