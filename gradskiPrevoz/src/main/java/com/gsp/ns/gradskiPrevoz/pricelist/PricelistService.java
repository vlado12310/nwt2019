package com.gsp.ns.gradskiPrevoz.pricelist;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gsp.ns.gradskiPrevoz.exceptions.ConflictException;
import com.gsp.ns.gradskiPrevoz.exceptions.InvalidParametersException;
import com.gsp.ns.gradskiPrevoz.exceptions.PricelistNotFoundException;
import com.gsp.ns.gradskiPrevoz.exceptions.ResourceNotFoundException;
@Service
public class PricelistService {
	//Proveri sa asistentkinjom da li cenovnik uopste moze da se menja.
	@Autowired
	private PricelistRepository repository;
	
	//injection
	public PricelistService(PricelistRepository repo){
		this.repository = repo;
	}
	public List<Pricelist> findAll(){
		return repository.findAll();
	}
	public Pricelist findOne(long id){
		Optional<Pricelist> plOptional = repository.findById(id);
		if (!plOptional.isPresent()){
			throw new ResourceNotFoundException();
		}
		return plOptional.get();
	}
	public Pricelist save(Pricelist pricelist){
		if (pricelist.getStart().after(pricelist.getEnd()) || pricelist.getStart().equals(pricelist.getEnd())){
			throw new InvalidParametersException();
		}
		if (datesMatch(pricelist)){
			throw new ConflictException();
		}
		
		
		return repository.save(pricelist);
	}
	public Pricelist modify(Pricelist plFromDb, Pricelist plToChange){
		plFromDb.setStart(plToChange.getStart());
		plFromDb.setEnd(plToChange.getEnd());
		Pricelist modifiedPl = save(plFromDb);
		return modifiedPl;
		
	}
	public void delete(Pricelist pricelist){
		if (pricelist.getStart().after(new Date()) || pricelist.getPricelistItems().size() == 0){
			repository.delete(pricelist);
		}else{
			throw new ConflictException();
		}
		
	}
	public Pricelist getActivePricelist(){
		List<Pricelist> pricelists = findAll();
		Date dateNow = new Date();
		Pricelist currentPL = null;
		for (Pricelist pl : pricelists){
			if (pl.getStart().before(dateNow) &&  pl.getEnd().after(dateNow)){
				currentPL = pl;
			}
		}
		if (currentPL != null){
			return currentPL;
		}else{
			throw new PricelistNotFoundException();
		}
	}
	public Pricelist getPricelistByDate(Date date){
		return repository.findByStartBeforeAndEndAfter(date, date);
	}
	// proverava da li se datumi poklapaju sa datumima postojecih cenovnika
	private boolean datesMatch(Pricelist pricelist){
		
		
		
		List<Pricelist> priceLists = findAll();
		for (Pricelist pr: priceLists){
			if (pricelist.getId() == null){
				
				if (pr.getStart().before(pricelist.getStart()) && pricelist.getStart().before(pr.getEnd()) ||
						pr.getStart().before(pricelist.getEnd()) && pricelist.getEnd().before(pr.getEnd()) ||
						(pr.getStart().getTime() == pricelist.getEnd().getTime() || pr.getEnd().getTime() == pricelist.getStart().getTime()) ||
						 pr.getStart().getTime() == pricelist.getStart().getTime() || pr.getEnd().getTime() == pricelist.getEnd().getTime()){

					return true;
				}
			}else if(pricelist.getId() != null && pr.getId() != pricelist.getId()){
				if (pr.getStart().before(pricelist.getStart()) && pricelist.getStart().before(pr.getEnd()) ||
						pr.getStart().before(pricelist.getEnd()) && pricelist.getEnd().before(pr.getEnd()) ||
						(pr.getStart().getTime() == pricelist.getEnd().getTime() || pr.getEnd().getTime() == pricelist.getStart().getTime()) ||
						 pr.getStart().getTime() == pricelist.getStart().getTime() || pr.getEnd().getTime() == pricelist.getEnd().getTime()){
					return true;
				}
			}
		}
		return false;
	}
	
}
