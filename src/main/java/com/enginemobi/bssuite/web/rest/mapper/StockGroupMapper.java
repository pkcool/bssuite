package com.enginemobi.bssuite.web.rest.mapper;

import com.enginemobi.bssuite.domain.*;
import com.enginemobi.bssuite.web.rest.dto.StockGroupDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity StockGroup and its DTO StockGroupDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface StockGroupMapper {

    @Mapping(source = "stockFamily.id", target = "stockFamilyId")
    @Mapping(source = "priceScale.id", target = "priceScaleId")
    @Mapping(source = "taxRate.id", target = "taxRateId")
    StockGroupDTO stockGroupToStockGroupDTO(StockGroup stockGroup);

    @Mapping(source = "stockFamilyId", target = "stockFamily")
    @Mapping(source = "priceScaleId", target = "priceScale")
    @Mapping(source = "taxRateId", target = "taxRate")
    StockGroup stockGroupDTOToStockGroup(StockGroupDTO stockGroupDTO);

    default StockFamily stockFamilyFromId(Long id) {
        if (id == null) {
            return null;
        }
        StockFamily stockFamily = new StockFamily();
        stockFamily.setId(id);
        return stockFamily;
    }

    default PriceScale priceScaleFromId(Long id) {
        if (id == null) {
            return null;
        }
        PriceScale priceScale = new PriceScale();
        priceScale.setId(id);
        return priceScale;
    }

    default TaxTable taxTableFromId(Long id) {
        if (id == null) {
            return null;
        }
        TaxTable taxTable = new TaxTable();
        taxTable.setId(id);
        return taxTable;
    }
}
