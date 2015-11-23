package com.enginemobi.bssuite.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A StockFamily.
 */
@Entity
@Table(name = "stock_family")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "stockfamily")
public class StockFamily implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 2)
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "is_diminishing")
    private Boolean isDiminishing;

    @Column(name = "lowest_margin")
    private Double lowestMargin;

    @Column(name = "highest_margin")
    private Double highestMargin;

    @Column(name = "comment")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "price_scale_id")
    private PriceScale priceScale;

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

    public Boolean getIsDiminishing() {
        return isDiminishing;
    }

    public void setIsDiminishing(Boolean isDiminishing) {
        this.isDiminishing = isDiminishing;
    }

    public Double getLowestMargin() {
        return lowestMargin;
    }

    public void setLowestMargin(Double lowestMargin) {
        this.lowestMargin = lowestMargin;
    }

    public Double getHighestMargin() {
        return highestMargin;
    }

    public void setHighestMargin(Double highestMargin) {
        this.highestMargin = highestMargin;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public PriceScale getPriceScale() {
        return priceScale;
    }

    public void setPriceScale(PriceScale priceScale) {
        this.priceScale = priceScale;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StockFamily stockFamily = (StockFamily) o;
        return Objects.equals(id, stockFamily.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "StockFamily{" +
            "id=" + id +
            ", code='" + code + "'" +
            ", name='" + name + "'" +
            ", isDiminishing='" + isDiminishing + "'" +
            ", lowestMargin='" + lowestMargin + "'" +
            ", highestMargin='" + highestMargin + "'" +
            ", comment='" + comment + "'" +
            '}';
    }
}
