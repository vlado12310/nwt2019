package com.gsp.ns.gradskiPrevoz.pricelist;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PricelistRepository extends JpaRepository<Pricelist, Long>{
	public Pricelist findByStartBeforeAndEndAfter(Date date, Date date2);
	
}
