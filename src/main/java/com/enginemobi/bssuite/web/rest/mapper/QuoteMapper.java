package com.enginemobi.bssuite.web.rest.mapper;

import com.enginemobi.bssuite.domain.*;
import com.enginemobi.bssuite.web.rest.dto.QuoteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Quote and its DTO QuoteDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface QuoteMapper {

    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "contact.id", target = "contactId")
    @Mapping(source = "deliveryContact.id", target = "deliveryContactId")
    @Mapping(source = "store.id", target = "storeId")
    @Mapping(source = "quoteByStaff.id", target = "quoteByStaffId")
    @Mapping(source = "createdBy.id", target = "createdById")
    QuoteDTO quoteToQuoteDTO(Quote quote);

    @Mapping(source = "customerId", target = "customer")
    @Mapping(source = "contactId", target = "contact")
    @Mapping(source = "deliveryContactId", target = "deliveryContact")
    @Mapping(source = "storeId", target = "store")
    @Mapping(source = "quoteByStaffId", target = "quoteByStaff")
    @Mapping(source = "createdById", target = "createdBy")
    @Mapping(target = "quoteLineItemss", ignore = true)
    Quote quoteDTOToQuote(QuoteDTO quoteDTO);

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

    default Staff staffFromId(Long id) {
        if (id == null) {
            return null;
        }
        Staff staff = new Staff();
        staff.setId(id);
        return staff;
    }
}
