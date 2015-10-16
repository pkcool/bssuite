package com.enginemobi.bssuite.web.rest.mapper;

import com.enginemobi.bssuite.domain.*;
import com.enginemobi.bssuite.web.rest.dto.InvoiceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Invoice and its DTO InvoiceDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface InvoiceMapper {

    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "billingCustomer.id", target = "billingCustomerId")
    @Mapping(source = "contact.id", target = "contactId")
    @Mapping(source = "deliveryContact.id", target = "deliveryContactId")
    @Mapping(source = "store.id", target = "storeId")
    @Mapping(source = "carrier.id", target = "carrierId")
    @Mapping(source = "salesperson.id", target = "salespersonId")
    @Mapping(source = "promotion.id", target = "promotionId")
    @Mapping(source = "createdBy.id", target = "createdById")
    InvoiceDTO invoiceToInvoiceDTO(Invoice invoice);

    @Mapping(source = "customerId", target = "customer")
    @Mapping(source = "billingCustomerId", target = "billingCustomer")
    @Mapping(source = "contactId", target = "contact")
    @Mapping(source = "deliveryContactId", target = "deliveryContact")
    @Mapping(source = "storeId", target = "store")
    @Mapping(source = "carrierId", target = "carrier")
    @Mapping(source = "salespersonId", target = "salesperson")
    @Mapping(source = "promotionId", target = "promotion")
    @Mapping(source = "createdById", target = "createdBy")
    @Mapping(target = "invoiceLineItemss", ignore = true)
    Invoice invoiceDTOToInvoice(InvoiceDTO invoiceDTO);



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
