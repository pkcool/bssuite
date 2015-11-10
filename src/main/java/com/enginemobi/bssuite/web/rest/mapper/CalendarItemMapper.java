package com.enginemobi.bssuite.web.rest.mapper;

import com.enginemobi.bssuite.domain.*;
import com.enginemobi.bssuite.web.rest.dto.CalendarItemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CalendarItem and its DTO CalendarItemDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CalendarItemMapper {

    @Mapping(source = "createdBy.id", target = "createdById")
    CalendarItemDTO calendarItemToCalendarItemDTO(CalendarItem calendarItem);

    @Mapping(source = "createdById", target = "createdBy")
    CalendarItem calendarItemDTOToCalendarItem(CalendarItemDTO calendarItemDTO);

    default Staff staffFromId(Long id) {
        if (id == null) {
            return null;
        }
        Staff staff = new Staff();
        staff.setId(id);
        return staff;
    }
}
