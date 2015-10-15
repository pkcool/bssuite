package com.enginemobi.bssuite.web.rest.mapper;

import com.enginemobi.bssuite.domain.*;
import com.enginemobi.bssuite.web.rest.dto.SupplierDiscountRuleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SupplierDiscountRule and its DTO SupplierDiscountRuleDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SupplierDiscountRuleMapper {

    SupplierDiscountRuleDTO supplierDiscountRuleToSupplierDiscountRuleDTO(SupplierDiscountRule supplierDiscountRule);

    SupplierDiscountRule supplierDiscountRuleDTOToSupplierDiscountRule(SupplierDiscountRuleDTO supplierDiscountRuleDTO);
}
