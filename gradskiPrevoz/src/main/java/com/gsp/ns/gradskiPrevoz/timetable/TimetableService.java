package com.gsp.ns.gradskiPrevoz.timetable;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gsp.ns.gradskiPrevoz.exceptions.ResourceNotFoundException;
import com.gsp.ns.gradskiPrevoz.line.LineRepository;

@Service
public class TimetableService {
	@Autowired
	TimetableRepository repo;
	
	@Autowired
	LineRepository repo2;
	
	public List<Timetable> findAll(){
		return repo.findAll();
	}
	
	public Timetable getOne(Long id) {
		Optional<Timetable> timetable = repo.findById(id);
		if(timetable.isPresent()) {
			return repo.getOne(id);
		}else {
			throw new ResourceNotFoundException();
		}
	}
	
	public void delete(Timetable table) {
		//proveri da li se koristi u nekoj od 'Line' ako ne 
		/*List<Line> list = repo2.findAll();
		boolean inUse = false;
		for (Line line : list) {
			if(line.table1.getId() == table.getId()) {
				inUse = true;
			}
			if(line.table2.getId() == table.getId()) {
				inUse = true;
			}
		}
		if(inUse) {
			throw new ConflictException();
		}*/
		repo.delete(table);
	}
	
	public Timetable save(Timetable table) {
		return repo.save(table);
	}
}	
	

