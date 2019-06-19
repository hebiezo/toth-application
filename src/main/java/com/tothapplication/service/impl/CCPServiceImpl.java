package com.tothapplication.service.impl;

import com.tothapplication.service.CCPService;
import com.tothapplication.domain.CCP;
import com.tothapplication.repository.CCPRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CCP}.
 */
@Service
@Transactional
public class CCPServiceImpl implements CCPService {

    private final Logger log = LoggerFactory.getLogger(CCPServiceImpl.class);

    private final CCPRepository cCPRepository;

    public CCPServiceImpl(CCPRepository cCPRepository) {
        this.cCPRepository = cCPRepository;
    }

    /**
     * Save a cCP.
     *
     * @param cCP the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CCP save(CCP cCP) {
        log.debug("Request to save CCP : {}", cCP);
        return cCPRepository.save(cCP);
    }

    /**
     * Get all the cCPS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CCP> findAll(Pageable pageable) {
        log.debug("Request to get all CCPS");
        return cCPRepository.findAll(pageable);
    }

    /**
     * Get all the cCPS with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<CCP> findAllWithEagerRelationships(Pageable pageable) {
        return cCPRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one cCP by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CCP> findOne(Long id) {
        log.debug("Request to get CCP : {}", id);
        return cCPRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the cCP by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CCP : {}", id);
        cCPRepository.deleteById(id);
    }
}
