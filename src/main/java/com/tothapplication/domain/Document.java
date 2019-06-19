package com.tothapplication.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.tothapplication.domain.enumeration.TypeDocument;

/**
 * A Document.
 */
@Entity
@Table(name = "document")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Document implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "title")
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TypeDocument type;

    @Column(name = "filename")
    private String filename;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "document_ccp",
               joinColumns = @JoinColumn(name = "document_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "ccp_id", referencedColumnName = "id"))
    private Set<CCP> cCPS = new HashSet<>();

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

    public Document title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public TypeDocument getType() {
        return type;
    }

    public Document type(TypeDocument type) {
        this.type = type;
        return this;
    }

    public void setType(TypeDocument type) {
        this.type = type;
    }

    public String getFilename() {
        return filename;
    }

    public Document filename(String filename) {
        this.filename = filename;
        return this;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Set<CCP> getCCPS() {
        return cCPS;
    }

    public Document cCPS(Set<CCP> cCPS) {
        this.cCPS = cCPS;
        return this;
    }

    public Document addCCP(CCP cCP) {
        this.cCPS.add(cCP);
        cCP.getDocuments().add(this);
        return this;
    }

    public Document removeCCP(CCP cCP) {
        this.cCPS.remove(cCP);
        cCP.getDocuments().remove(this);
        return this;
    }

    public void setCCPS(Set<CCP> cCPS) {
        this.cCPS = cCPS;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Document)) {
            return false;
        }
        return id != null && id.equals(((Document) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Document{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", type='" + getType() + "'" +
            ", filename='" + getFilename() + "'" +
            "}";
    }
}
