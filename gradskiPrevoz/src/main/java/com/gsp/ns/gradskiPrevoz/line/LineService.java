package com.gsp.ns.gradskiPrevoz.line;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gsp.ns.gradskiPrevoz.exceptions.ConflictException;
import com.gsp.ns.gradskiPrevoz.exceptions.ResourceNotFoundException;
import com.gsp.ns.gradskiPrevoz.zone.Zone;
import com.gsp.ns.gradskiPrevoz.zone.ZoneRepository;
@Service
public class LineService {
	@Autowired
	private LineRepository lineRepository;
	@Autowired ZoneRepository zoneRepo;
	
	public List<Line> findAll(){
		return lineRepository.findAll();
	}
	
	public Line findOne(long id) {
		Optional<Line> line = lineRepository.findById(id);
		if (!line.isPresent()){
			throw new ResourceNotFoundException();
		};
		return line.get();
	}
	
	public Line save(Line newLine) {
		Line addedLine = lineRepository.save(newLine);
		return addedLine;
	}
	
	public Line addOne(Line newLine) {
		Optional<Line> line = lineRepository.findByName(newLine.getName());
		if (line.isPresent()){
			throw new ConflictException();
		};

		Optional<Zone> zone = zoneRepo.findById(newLine.getZone().getId());
		zone.get().getLines().add(newLine);
		zoneRepo.save(zone.get());
		newLine.setZone(zone.get());
		return save(newLine);
	}
	
	public void delete(Line line) {
		lineRepository.delete(line);
	}
	
	public Line change(Line fromDbLine, Line newLine) {
		fromDbLine.setStations(newLine.getStations());
		Line savedLine = lineRepository.save(fromDbLine);
		
		return savedLine;
	}
}
