package com.gsp.ns.gradskiPrevoz.statusRequest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRequestRepository extends JpaRepository<StatusRequest, Long> {
}
