package com.enginemobi.bssuite.web.rest.mapper;

import com.enginemobi.bssuite.domain.*;
import com.enginemobi.bssuite.web.rest.dto.PurchaseOrderDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PurchaseOrder and its DTO PurchaseOrderDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PurchaseOrderMapper {

    @Mapping(source = "supplier.id", target = "supplierId")
    @Mapping(source = "supplierContact.id", target = "supplierContactId")
    @Mapping(source = "supplierDeliveryContact.id", target = "supplierDeliveryContactId")
    @Mapping(source = "store.id", target = "storeId")
    @Mapping(source = "salesOrder.id", target = "salesOrderId")
    @Mapping(source = "updatedByStaff.id", target = "updatedByStaffId")
    @Mapping(source = "createdBy.id", target = "createdById")
    PurchaseOrderDTO purchaseOrderToPurchaseOrderDTO(PurchaseOrder purchaseOrder);

    @Mapping(source = "supplierId", target = "supplier")
    @Mapping(source = "supplierContactId", target = "supplierContact")
    @Mapping(source = "supplierDeliveryContactId", target = "supplierDeliveryContact")
    @Mapping(source = "storeId", target = "store")
    @Mapping(source = "salesOrderId", target = "salesOrder")
    @Mapping(source = "updatedByStaffId", target = "updatedByStaff")
    @Mapping(source = "createdById", target = "createdBy")
    @Mapping(target = "purchaseOrderLineItemss", ignore = true)
    PurchaseOrder purchaseOrderDTOToPurchaseOrder(PurchaseOrderDTO purchaseOrderDTO);

    default Supplier SupplierFromId(Long id) {
        if (id == null) {
            return null;
        }
        Supplier Supplier = new Supplier();
        Supplier.setId(id);
        return Supplier;
    }



    default Contact contactFromId(Long id) {
        if (id == null) {
            return null;
        }
        Contact contact = new Contact();
        contact.setId(id);
        return contact;
    }

    default Store storeFromId(Long id) {
        if (id == null) {
            return null;
        }
        Store store = new Store();
        store.setId(id);
        return store;
    }

    default SalesOrder salesOrderFromId(Long id) {
        if (id == null) {
            return null;
        }
        SalesOrder salesOrder = new SalesOrder();
        salesOrder.setId(id);
        return salesOrder;
    }


    default Staff staffFromId(Long id) {
        if (id == null) {
            return null;
        }
        Staff staff = new Staff();
        staff.setId(id);
        return staff;
    }
}
