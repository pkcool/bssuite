package com.enginemobi.bssuite.web.rest.mapper;

import com.enginemobi.bssuite.domain.*;
import com.enginemobi.bssuite.web.rest.dto.SalesOrderDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SalesOrder and its DTO SalesOrderDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SalesOrderMapper {

    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "contact.id", target = "contactId")
    @Mapping(source = "deliveryContact.id", target = "deliveryContactId")
    @Mapping(source = "store.id", target = "storeId")
    @Mapping(source = "carrier.id", target = "carrierId")
    @Mapping(source = "salesperson.id", target = "salespersonId")
    @Mapping(source = "promotion.id", target = "promotionId")
    @Mapping(source = "createdBy.id", target = "createdById")
    SalesOrderDTO salesOrderToSalesOrderDTO(SalesOrder salesOrder);

    @Mapping(source = "customerId", target = "customer")
    @Mapping(source = "contactId", target = "contact")
    @Mapping(source = "deliveryContactId", target = "deliveryContact")
    @Mapping(source = "storeId", target = "store")
    @Mapping(source = "carrierId", target = "carrier")
    @Mapping(source = "salespersonId", target = "salesperson")
    @Mapping(source = "promotionId", target = "promotion")
    @Mapping(source = "createdById", target = "createdBy")
    @Mapping(target = "salesOrderLineItemss", ignore = true)
    SalesOrder salesOrderDTOToSalesOrder(SalesOrderDTO salesOrderDTO);

    default Customer customerFromId(Long id) {
        if (id == null) {
            return null;
        }
        Customer customer = new Customer();
        customer.setId(id);
        return customer;
    }

    default Contact contactFromId(Long id) {
        if (id == null) {
            return null;
        }
        Contact contact = new Contact();
        contact.setId(id);
        return contact;
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

    default Carrier carrierFromId(Long id) {
        if (id == null) {
            return null;
        }
        Carrier carrier = new Carrier();
        carrier.setId(id);
        return carrier;
    }

    default Staff staffFromId(Long id) {
        if (id == null) {
            return null;
        }
        Staff staff = new Staff();
        staff.setId(id);
        return staff;
    }

    default Promotion promotionFromId(Long id) {
        if (id == null) {
            return null;
        }
        Promotion promotion = new Promotion();
        promotion.setId(id);
        return promotion;
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
