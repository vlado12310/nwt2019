package com.gsp.ns.gradskiPrevoz.station;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gsp.ns.gradskiPrevoz.exceptions.ResourceNotFoundException;

@Service
public class StationService {
	@Autowired
	private StationRepository stationRepo;
	
	public List<Station> findAll(){
		return stationRepo.findAll();
	}
	
	public Station findOne(long id){
		Optional<Station> station = stationRepo.findById(id);
		if (!station.isPresent()){
			throw new ResourceNotFoundException();
		};
		return station.get();
	}
	
	public Station save(Station newStation){
		
		Station addedStation = stationRepo.save(newStation);
		return addedStation;
		
		
	}
	public void delete(Station stationToDelete){
		stationRepo.delete(stationToDelete);
	}
	public Station change(Station stationFromDb, Station newStation){
		
		stationFromDb.setLocation(newStation.getLocation());
		stationFromDb.setName(newStation.getName());
		Station savedStation = stationRepo.save(stationFromDb);
		return savedStation;
	}
}
