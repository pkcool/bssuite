package com.enginemobi.bssuite.web.rest.mapper;

import com.enginemobi.bssuite.domain.*;
import com.enginemobi.bssuite.web.rest.dto.StaffDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Staff and its DTO StaffDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface StaffMapper {

    @Mapping(source = "store.id", target = "storeId")
    StaffDTO staffToStaffDTO(Staff staff);

    @Mapping(source = "storeId", target = "store")
    Staff staffDTOToStaff(StaffDTO staffDTO);

    default Store storeFromId(Long id) {
        if (id == null) {
            return null;
        }
        Store store = new Store();
        store.setId(id);
        return store;
    }
}
