package com.enginemobi.bssuite.web.rest.mapper;

import com.enginemobi.bssuite.domain.*;
import com.enginemobi.bssuite.web.rest.dto.ProductActivityAuditDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ProductActivityAudit and its DTO ProductActivityAuditDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProductActivityAuditMapper {

    @Mapping(source = "createdBy.id", target = "createdById")
    @Mapping(source = "product.id", target = "productId")
    ProductActivityAuditDTO productActivityAuditToProductActivityAuditDTO(ProductActivityAudit productActivityAudit);

    @Mapping(source = "createdById", target = "createdBy")
    @Mapping(source = "productId", target = "product")
    ProductActivityAudit productActivityAuditDTOToProductActivityAudit(ProductActivityAuditDTO productActivityAuditDTO);

    default Staff staffFromId(Long id) {
        if (id == null) {
            return null;
        }
        Staff staff = new Staff();
        staff.setId(id);
        return staff;
    }

    default Product productFromId(Long id) {
        if (id == null) {
            return null;
        }
        Product product = new Product();
        product.setId(id);
        return product;
    }
}
