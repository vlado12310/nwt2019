package com.gsp.ns.gradskiPrevoz.line;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LineRepository extends JpaRepository<Line, Long>{
	Optional<Line> findByName(String name);

}
