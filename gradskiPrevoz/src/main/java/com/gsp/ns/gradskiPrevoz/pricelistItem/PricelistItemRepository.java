package com.gsp.ns.gradskiPrevoz.pricelistItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface PricelistItemRepository extends JpaRepository<PricelistItem, Long>{
	public PricelistItem findById(PricelistItemId id);
	
}
