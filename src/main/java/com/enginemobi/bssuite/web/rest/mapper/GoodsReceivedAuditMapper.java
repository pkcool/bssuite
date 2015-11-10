package com.enginemobi.bssuite.web.rest.mapper;

import com.enginemobi.bssuite.domain.*;
import com.enginemobi.bssuite.web.rest.dto.GoodsReceivedAuditDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity GoodsReceivedAudit and its DTO GoodsReceivedAuditDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GoodsReceivedAuditMapper {

    @Mapping(source = "receivedBy.id", target = "receivedById")
    @Mapping(source = "supplier.id", target = "supplierId")
    @Mapping(source = "purchaseOrder.id", target = "purchaseOrderId")
    @Mapping(source = "product.id", target = "productId")
    GoodsReceivedAuditDTO goodsReceivedAuditToGoodsReceivedAuditDTO(GoodsReceivedAudit goodsReceivedAudit);

    @Mapping(source = "receivedById", target = "receivedBy")
    @Mapping(source = "supplierId", target = "supplier")
    @Mapping(source = "purchaseOrderId", target = "purchaseOrder")
    @Mapping(source = "productId", target = "product")
    GoodsReceivedAudit goodsReceivedAuditDTOToGoodsReceivedAudit(GoodsReceivedAuditDTO goodsReceivedAuditDTO);

    default Staff staffFromId(Long id) {
        if (id == null) {
            return null;
        }
        Staff staff = new Staff();
        staff.setId(id);
        return staff;
    }

    default Supplier supplierFromId(Long id) {
        if (id == null) {
            return null;
        }
        Supplier supplier = new Supplier();
        supplier.setId(id);
        return supplier;
    }

    default PurchaseOrder purchaseOrderFromId(Long id) {
        if (id == null) {
            return null;
        }
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setId(id);
        return purchaseOrder;
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
