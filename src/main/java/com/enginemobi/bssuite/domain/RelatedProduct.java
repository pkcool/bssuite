package com.enginemobi.bssuite.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A RelatedProduct.
 */
@Entity
@Table(name = "related_product")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "relatedproduct")
public class RelatedProduct implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "is_suggested")
    private Boolean isSuggested;

    @Column(name = "comment")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "equivalent_product_id")
    private Product equivalentProduct;

    @ManyToOne
    @JoinColumn(name = "relation_category_id")
    private ProductRelationCategory relationCategory;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsSuggested() {
        return isSuggested;
    }

    public void setIsSuggested(Boolean isSuggested) {
        this.isSuggested = isSuggested;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Product getEquivalentProduct() {
        return equivalentProduct;
    }

    public void setEquivalentProduct(Product product) {
        this.equivalentProduct = product;
    }

    public ProductRelationCategory getRelationCategory() {
        return relationCategory;
    }

    public void setRelationCategory(ProductRelationCategory productRelationCategory) {
        this.relationCategory = productRelationCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RelatedProduct relatedProduct = (RelatedProduct) o;
        return Objects.equals(id, relatedProduct.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "RelatedProduct{" +
            "id=" + id +
            ", isSuggested='" + isSuggested + "'" +
            ", comment='" + comment + "'" +
            '}';
    }
}
