package com.tothapplication.service;

import com.tothapplication.domain.Document;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

/**
 * Service Interface for managing {@link Document}.
 */
public interface DocumentService {

    /**
     * Save a document.
     *
     * @param document the entity to save.
     * @return the persisted entity.
     */
    Document save(Document document);

    /**
     * Get all the documents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Document> findAll(Pageable pageable);

    /**
     * Get all the documents with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<Document> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" document.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Document> findOne(Long id);

    /**
     * Delete the "id" document.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    String storeFile(Long id, MultipartFile file);

    Resource loadFileAsResource(Long id);

}
