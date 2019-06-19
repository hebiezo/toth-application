package com.tothapplication.web.rest;

import com.tothapplication.TothApplicationApp;
import com.tothapplication.domain.CCP;
import com.tothapplication.repository.CCPRepository;
import com.tothapplication.service.CCPService;
import com.tothapplication.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.tothapplication.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link CCPResource} REST controller.
 */
@SpringBootTest(classes = TothApplicationApp.class)
public class CCPResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    @Autowired
    private CCPRepository cCPRepository;

    @Autowired
    private CCPService cCPService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restCCPMockMvc;

    private CCP cCP;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CCPResource cCPResource = new CCPResource(cCPService);
        this.restCCPMockMvc = MockMvcBuilders.standaloneSetup(cCPResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CCP createEntity(EntityManager em) {
        CCP cCP = new CCP()
            .title(DEFAULT_TITLE);
        return cCP;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CCP createUpdatedEntity(EntityManager em) {
        CCP cCP = new CCP()
            .title(UPDATED_TITLE);
        return cCP;
    }

    @BeforeEach
    public void initTest() {
        cCP = createEntity(em);
    }

    @Test
    @Transactional
    public void createCCP() throws Exception {
        int databaseSizeBeforeCreate = cCPRepository.findAll().size();

        // Create the CCP
        restCCPMockMvc.perform(post("/api/ccps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cCP)))
            .andExpect(status().isCreated());

        // Validate the CCP in the database
        List<CCP> cCPList = cCPRepository.findAll();
        assertThat(cCPList).hasSize(databaseSizeBeforeCreate + 1);
        CCP testCCP = cCPList.get(cCPList.size() - 1);
        assertThat(testCCP.getTitle()).isEqualTo(DEFAULT_TITLE);
    }

    @Test
    @Transactional
    public void createCCPWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cCPRepository.findAll().size();

        // Create the CCP with an existing ID
        cCP.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCCPMockMvc.perform(post("/api/ccps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cCP)))
            .andExpect(status().isBadRequest());

        // Validate the CCP in the database
        List<CCP> cCPList = cCPRepository.findAll();
        assertThat(cCPList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCCPS() throws Exception {
        // Initialize the database
        cCPRepository.saveAndFlush(cCP);

        // Get all the cCPList
        restCCPMockMvc.perform(get("/api/ccps?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cCP.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())));
    }
    
    @Test
    @Transactional
    public void getCCP() throws Exception {
        // Initialize the database
        cCPRepository.saveAndFlush(cCP);

        // Get the cCP
        restCCPMockMvc.perform(get("/api/ccps/{id}", cCP.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cCP.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCCP() throws Exception {
        // Get the cCP
        restCCPMockMvc.perform(get("/api/ccps/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCCP() throws Exception {
        // Initialize the database
        cCPService.save(cCP);

        int databaseSizeBeforeUpdate = cCPRepository.findAll().size();

        // Update the cCP
        CCP updatedCCP = cCPRepository.findById(cCP.getId()).get();
        // Disconnect from session so that the updates on updatedCCP are not directly saved in db
        em.detach(updatedCCP);
        updatedCCP
            .title(UPDATED_TITLE);

        restCCPMockMvc.perform(put("/api/ccps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCCP)))
            .andExpect(status().isOk());

        // Validate the CCP in the database
        List<CCP> cCPList = cCPRepository.findAll();
        assertThat(cCPList).hasSize(databaseSizeBeforeUpdate);
        CCP testCCP = cCPList.get(cCPList.size() - 1);
        assertThat(testCCP.getTitle()).isEqualTo(UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void updateNonExistingCCP() throws Exception {
        int databaseSizeBeforeUpdate = cCPRepository.findAll().size();

        // Create the CCP

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCCPMockMvc.perform(put("/api/ccps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cCP)))
            .andExpect(status().isBadRequest());

        // Validate the CCP in the database
        List<CCP> cCPList = cCPRepository.findAll();
        assertThat(cCPList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCCP() throws Exception {
        // Initialize the database
        cCPService.save(cCP);

        int databaseSizeBeforeDelete = cCPRepository.findAll().size();

        // Delete the cCP
        restCCPMockMvc.perform(delete("/api/ccps/{id}", cCP.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<CCP> cCPList = cCPRepository.findAll();
        assertThat(cCPList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CCP.class);
        CCP cCP1 = new CCP();
        cCP1.setId(1L);
        CCP cCP2 = new CCP();
        cCP2.setId(cCP1.getId());
        assertThat(cCP1).isEqualTo(cCP2);
        cCP2.setId(2L);
        assertThat(cCP1).isNotEqualTo(cCP2);
        cCP1.setId(null);
        assertThat(cCP1).isNotEqualTo(cCP2);
    }
}
