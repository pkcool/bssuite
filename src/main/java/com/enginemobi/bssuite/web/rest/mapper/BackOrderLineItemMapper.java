package com.enginemobi.bssuite.web.rest.mapper;

import com.enginemobi.bssuite.domain.*;
import com.enginemobi.bssuite.web.rest.dto.BackOrderLineItemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity BackOrderLineItem and its DTO BackOrderLineItemDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BackOrderLineItemMapper {

    @Mapping(source = "salesOrderLineItem.id", target = "salesOrderLineItemId")
    BackOrderLineItemDTO backOrderLineItemToBackOrderLineItemDTO(BackOrderLineItem backOrderLineItem);

    @Mapping(source = "salesOrderLineItemId", target = "salesOrderLineItem")
    BackOrderLineItem backOrderLineItemDTOToBackOrderLineItem(BackOrderLineItemDTO backOrderLineItemDTO);

    default SalesOrderLineItem salesOrderLineItemFromId(Long id) {
        if (id == null) {
            return null;
        }
        SalesOrderLineItem salesOrderLineItem = new SalesOrderLineItem();
        salesOrderLineItem.setId(id);
        return salesOrderLineItem;
    }
}
