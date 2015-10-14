package com.enginemobi.bssuite.web.rest.mapper;

import com.enginemobi.bssuite.domain.*;
import com.enginemobi.bssuite.web.rest.dto.CustomerDiscountRuleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CustomerDiscountRule and its DTO CustomerDiscountRuleDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomerDiscountRuleMapper {

    CustomerDiscountRuleDTO customerDiscountRuleToCustomerDiscountRuleDTO(CustomerDiscountRule customerDiscountRule);

    CustomerDiscountRule customerDiscountRuleDTOToCustomerDiscountRule(CustomerDiscountRuleDTO customerDiscountRuleDTO);
}
