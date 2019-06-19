package com.tothapplication.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A CCP.
 */
@Entity
@Table(name = "ccp")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CCP implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "title")
    private String title;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "ccp_formation",
               joinColumns = @JoinColumn(name = "ccp_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "formation_id", referencedColumnName = "id"))
    private Set<Formation> formations = new HashSet<>();

    @ManyToMany(mappedBy = "cCPS")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Document> documents = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public CCP title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Formation> getFormations() {
        return formations;
    }

    public CCP formations(Set<Formation> formations) {
        this.formations = formations;
        return this;
    }

    public CCP addFormation(Formation formation) {
        this.formations.add(formation);
        formation.getCCPS().add(this);
        return this;
    }

    public CCP removeFormation(Formation formation) {
        this.formations.remove(formation);
        formation.getCCPS().remove(this);
        return this;
    }

    public void setFormations(Set<Formation> formations) {
        this.formations = formations;
    }

    public Set<Document> getDocuments() {
        return documents;
    }

    public CCP documents(Set<Document> documents) {
        this.documents = documents;
        return this;
    }

    public CCP addDocument(Document document) {
        this.documents.add(document);
        document.getCCPS().add(this);
        return this;
    }

    public CCP removeDocument(Document document) {
        this.documents.remove(document);
        document.getCCPS().remove(this);
        return this;
    }

    public void setDocuments(Set<Document> documents) {
        this.documents = documents;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CCP)) {
            return false;
        }
        return id != null && id.equals(((CCP) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CCP{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            "}";
    }
}
