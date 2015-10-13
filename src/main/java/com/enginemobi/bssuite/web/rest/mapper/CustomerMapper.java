package com.enginemobi.bssuite.web.rest.mapper;

import com.enginemobi.bssuite.domain.*;
import com.enginemobi.bssuite.web.rest.dto.CustomerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Customer and its DTO CustomerDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomerMapper {

    @Mapping(source = "customerCategory.id", target = "customerCategoryId")
    @Mapping(source = "contact.id", target = "contactId")
    @Mapping(source = "deliveryContact.id", target = "deliveryContactId")
    @Mapping(source = "salesPrimaryContact.id", target = "salesPrimaryContactId")
    @Mapping(source = "salesSecondaryContact.id", target = "salesSecondaryContactId")
    @Mapping(source = "accountPrimaryContact.id", target = "accountPrimaryContactId")
    @Mapping(source = "accountSecondaryContact.id", target = "accountSecondaryContactId")
    @Mapping(source = "rep.id", target = "repId")
    @Mapping(source = "centralBillingAccount.id", target = "centralBillingAccountId")
    @Mapping(source = "store.id", target = "storeId")
    CustomerDTO customerToCustomerDTO(Customer customer);

    @Mapping(source = "customerCategoryId", target = "customerCategory")
    @Mapping(source = "contactId", target = "contact")
    @Mapping(source = "deliveryContactId", target = "deliveryContact")
    @Mapping(source = "salesPrimaryContactId", target = "salesPrimaryContact")
    @Mapping(source = "salesSecondaryContactId", target = "salesSecondaryContact")
    @Mapping(source = "accountPrimaryContactId", target = "accountPrimaryContact")
    @Mapping(source = "accountSecondaryContactId", target = "accountSecondaryContact")
    @Mapping(source = "repId", target = "rep")
    @Mapping(source = "centralBillingAccountId", target = "centralBillingAccount")
    @Mapping(source = "storeId", target = "store")
    Customer customerDTOToCustomer(CustomerDTO customerDTO);

    default CustomerCategory customerCategoryFromId(Long id) {
        if (id == null) {
            return null;
        }
        CustomerCategory customerCategory = new CustomerCategory();
        customerCategory.setId(id);
        return customerCategory;
    }

    default Contact contactFromId(Long id) {
        if (id == null) {
            return null;
        }
        Contact contact = new Contact();
        contact.setId(id);
        return contact;
    }


    default Staff staffFromId(Long id) {
        if (id == null) {
            return null;
        }
        Staff staff = new Staff();
        staff.setId(id);
        return staff;
    }

    default Customer customerFromId(Long id) {
        if (id == null) {
            return null;
        }
        Customer customer = new Customer();
        customer.setId(id);
        return customer;
    }

    default Store storeFromId(Long id) {
        if (id == null) {
            return null;
        }
        Store store = new Store();
        store.setId(id);
        return store;
    }
}
