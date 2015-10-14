package com.enginemobi.bssuite.web.rest.mapper;

import com.enginemobi.bssuite.domain.*;
import com.enginemobi.bssuite.web.rest.dto.ProductDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Product and its DTO ProductDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProductMapper {

    @Mapping(source = "stockGroup.id", target = "stockGroupId")
    @Mapping(source = "supplier.id", target = "supplierId")
    @Mapping(source = "store.id", target = "storeId")
    @Mapping(source = "taxRate.id", target = "taxRateId")
    ProductDTO productToProductDTO(Product product);

    @Mapping(source = "stockGroupId", target = "stockGroup")
    @Mapping(source = "supplierId", target = "supplier")
    @Mapping(source = "storeId", target = "store")
    @Mapping(source = "taxRateId", target = "taxRate")
    Product productDTOToProduct(ProductDTO productDTO);

    default StockGroup stockGroupFromId(Long id) {
        if (id == null) {
            return null;
        }
        StockGroup stockGroup = new StockGroup();
        stockGroup.setId(id);
        return stockGroup;
    }

    default Supplier supplierFromId(Long id) {
        if (id == null) {
            return null;
        }
        Supplier supplier = new Supplier();
        supplier.setId(id);
        return supplier;
    }

    default Store storeFromId(Long id) {
        if (id == null) {
            return null;
        }
        Store store = new Store();
        store.setId(id);
        return store;
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
