package com.tothapplication.web.rest;

import com.tothapplication.domain.CCP;
import com.tothapplication.service.CCPService;
import com.tothapplication.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.tothapplication.domain.CCP}.
 */
@RestController
@RequestMapping("/api")
public class CCPResource {

    private final Logger log = LoggerFactory.getLogger(CCPResource.class);

    private static final String ENTITY_NAME = "cCP";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CCPService cCPService;

    public CCPResource(CCPService cCPService) {
        this.cCPService = cCPService;
    }

    /**
     * {@code POST  /ccps} : Create a new cCP.
     *
     * @param cCP the cCP to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cCP, or with status {@code 400 (Bad Request)} if the cCP has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ccps")
    public ResponseEntity<CCP> createCCP(@RequestBody CCP cCP) throws URISyntaxException {
        log.debug("REST request to save CCP : {}", cCP);
        if (cCP.getId() != null) {
            throw new BadRequestAlertException("A new cCP cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CCP result = cCPService.save(cCP);
        return ResponseEntity.created(new URI("/api/ccps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ccps} : Updates an existing cCP.
     *
     * @param cCP the cCP to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cCP,
     * or with status {@code 400 (Bad Request)} if the cCP is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cCP couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ccps")
    public ResponseEntity<CCP> updateCCP(@RequestBody CCP cCP) throws URISyntaxException {
        log.debug("REST request to update CCP : {}", cCP);
        if (cCP.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CCP result = cCPService.save(cCP);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cCP.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ccps} : get all the cCPS.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cCPS in body.
     */
    @GetMapping("/ccps")
    public ResponseEntity<List<CCP>> getAllCCPS(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of CCPS");
        Page<CCP> page = cCPService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ccps/:id} : get the "id" cCP.
     *
     * @param id the id of the cCP to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cCP, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ccps/{id}")
    public ResponseEntity<CCP> getCCP(@PathVariable Long id) {
        log.debug("REST request to get CCP : {}", id);
        Optional<CCP> cCP = cCPService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cCP);
    }

    /**
     * {@code DELETE  /ccps/:id} : delete the "id" cCP.
     *
     * @param id the id of the cCP to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ccps/{id}")
    public ResponseEntity<Void> deleteCCP(@PathVariable Long id) {
        log.debug("REST request to delete CCP : {}", id);
        cCPService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
