package com.tothapplication.service;

import com.tothapplication.domain.CCP;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link CCP}.
 */
public interface CCPService {

    /**
     * Save a cCP.
     *
     * @param cCP the entity to save.
     * @return the persisted entity.
     */
    CCP save(CCP cCP);

    /**
     * Get all the cCPS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CCP> findAll(Pageable pageable);


    /**
     * Get the "id" cCP.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CCP> findOne(Long id);

    /**
     * Delete the "id" cCP.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
