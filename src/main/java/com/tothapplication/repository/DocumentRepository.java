package com.tothapplication.repository;

import com.tothapplication.domain.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Document entity.
 */
@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    @Query(value = "select distinct document from Document document left join fetch document.cCPS",
        countQuery = "select count(distinct document) from Document document")
    Page<Document> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct document from Document document left join fetch document.cCPS")
    List<Document> findAllWithEagerRelationships();

    @Query("select document from Document document left join fetch document.cCPS where document.id =:id")
    Optional<Document> findOneWithEagerRelationships(@Param("id") Long id);

}
