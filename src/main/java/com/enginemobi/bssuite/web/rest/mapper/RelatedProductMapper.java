package com.enginemobi.bssuite.web.rest.mapper;

import com.enginemobi.bssuite.domain.*;
import com.enginemobi.bssuite.web.rest.dto.RelatedProductDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity RelatedProduct and its DTO RelatedProductDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RelatedProductMapper {

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "equivalentProduct.id", target = "equivalentProductId")
    @Mapping(source = "relationCategory.id", target = "relationCategoryId")
    RelatedProductDTO relatedProductToRelatedProductDTO(RelatedProduct relatedProduct);

    @Mapping(source = "productId", target = "product")
    @Mapping(source = "equivalentProductId", target = "equivalentProduct")
    @Mapping(source = "relationCategoryId", target = "relationCategory")
    RelatedProduct relatedProductDTOToRelatedProduct(RelatedProductDTO relatedProductDTO);

    default Product productFromId(Long id) {
        if (id == null) {
            return null;
        }
        Product product = new Product();
        product.setId(id);
        return product;
    }



    default ProductRelationCategory productRelationCategoryFromId(Long id) {
        if (id == null) {
            return null;
        }
        ProductRelationCategory productRelationCategory = new ProductRelationCategory();
        productRelationCategory.setId(id);
        return productRelationCategory;
    }
}
