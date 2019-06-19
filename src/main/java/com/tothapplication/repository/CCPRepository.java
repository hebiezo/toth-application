package com.tothapplication.repository;

import com.tothapplication.domain.CCP;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CCP entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CCPRepository extends JpaRepository<CCP, Long> {

}
