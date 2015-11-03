package com.enginemobi.bssuite.web.rest.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the RelatedProduct entity.
 */
public class RelatedProductDTO implements Serializable {

    private Long id;

    private Boolean isSuggested;


    private String comment;


    private Long productId;

    private Long equivalentProductId;

    private Long relationCategoryId;

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


    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getEquivalentProductId() {
        return equivalentProductId;
    }

    public void setEquivalentProductId(Long productId) {
        this.equivalentProductId = productId;
    }

    public Long getRelationCategoryId() {
        return relationCategoryId;
    }

    public void setRelationCategoryId(Long productRelationCategoryId) {
        this.relationCategoryId = productRelationCategoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RelatedProductDTO relatedProductDTO = (RelatedProductDTO) o;

        if ( ! Objects.equals(id, relatedProductDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "RelatedProductDTO{" +
                "id=" + id +
                ", isSuggested='" + isSuggested + "'" +
                ", comment='" + comment + "'" +
                '}';
    }
}
