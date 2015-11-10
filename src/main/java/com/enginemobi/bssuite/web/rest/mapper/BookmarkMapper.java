package com.enginemobi.bssuite.web.rest.mapper;

import com.enginemobi.bssuite.domain.*;
import com.enginemobi.bssuite.web.rest.dto.BookmarkDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Bookmark and its DTO BookmarkDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BookmarkMapper {

    @Mapping(source = "createdBy.id", target = "createdById")
    BookmarkDTO bookmarkToBookmarkDTO(Bookmark bookmark);

    @Mapping(source = "createdById", target = "createdBy")
    Bookmark bookmarkDTOToBookmark(BookmarkDTO bookmarkDTO);

    default Staff staffFromId(Long id) {
        if (id == null) {
            return null;
        }
        Staff staff = new Staff();
        staff.setId(id);
        return staff;
    }
}
