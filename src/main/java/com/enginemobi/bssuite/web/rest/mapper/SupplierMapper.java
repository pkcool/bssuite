package com.enginemobi.bssuite.web.rest.mapper;

import com.enginemobi.bssuite.domain.*;
import com.enginemobi.bssuite.web.rest.dto.SupplierDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Supplier and its DTO SupplierDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SupplierMapper {

    @Mapping(source = "supplierCategory.id", target = "supplierCategoryId")
    @Mapping(source = "contact.id", target = "contactId")
    SupplierDTO supplierToSupplierDTO(Supplier supplier);

    @Mapping(source = "supplierCategoryId", target = "supplierCategory")
    @Mapping(source = "contactId", target = "contact")
    Supplier supplierDTOToSupplier(SupplierDTO supplierDTO);

    default SupplierCategory supplierCategoryFromId(Long id) {
        if (id == null) {
            return null;
        }
        SupplierCategory supplierCategory = new SupplierCategory();
        supplierCategory.setId(id);
        return supplierCategory;
    }

    default Contact contactFromId(Long id) {
        if (id == null) {
            return null;
        }
        Contact contact = new Contact();
        contact.setId(id);
        return contact;
    }
}
