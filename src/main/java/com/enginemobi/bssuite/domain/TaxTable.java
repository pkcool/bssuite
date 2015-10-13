package com.enginemobi.bssuite.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A TaxTable.
 */
@Entity
@Table(name = "tax_table")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName="taxtable")
public class TaxTable implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @NotNull        
    @Column(name = "code", nullable = false)
    private String code;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "formula")
    private String formula;
    
    @Column(name = "is_added_to_total")
    private Boolean isAddedToTotal;
    
    @Column(name = "is_subtracted_from_total")
    private Boolean isSubtractedFromTotal;
    
    @Column(name = "is_excluded_on_reporting")
    private Boolean isExcludedOnReporting;
    
    @Column(name = "tax_group")
    private String taxGroup;
    
    @Column(name = "tax_base")
    private String taxBase;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public Boolean getIsAddedToTotal() {
        return isAddedToTotal;
    }

    public void setIsAddedToTotal(Boolean isAddedToTotal) {
        this.isAddedToTotal = isAddedToTotal;
    }

    public Boolean getIsSubtractedFromTotal() {
        return isSubtractedFromTotal;
    }

    public void setIsSubtractedFromTotal(Boolean isSubtractedFromTotal) {
        this.isSubtractedFromTotal = isSubtractedFromTotal;
    }

    public Boolean getIsExcludedOnReporting() {
        return isExcludedOnReporting;
    }

    public void setIsExcludedOnReporting(Boolean isExcludedOnReporting) {
        this.isExcludedOnReporting = isExcludedOnReporting;
    }

    public String getTaxGroup() {
        return taxGroup;
    }

    public void setTaxGroup(String taxGroup) {
        this.taxGroup = taxGroup;
    }

    public String getTaxBase() {
        return taxBase;
    }

    public void setTaxBase(String taxBase) {
        this.taxBase = taxBase;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TaxTable taxTable = (TaxTable) o;

        if ( ! Objects.equals(id, taxTable.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TaxTable{" +
                "id=" + id +
                ", code='" + code + "'" +
                ", name='" + name + "'" +
                ", formula='" + formula + "'" +
                ", isAddedToTotal='" + isAddedToTotal + "'" +
                ", isSubtractedFromTotal='" + isSubtractedFromTotal + "'" +
                ", isExcludedOnReporting='" + isExcludedOnReporting + "'" +
                ", taxGroup='" + taxGroup + "'" +
                ", taxBase='" + taxBase + "'" +
                '}';
    }
}
