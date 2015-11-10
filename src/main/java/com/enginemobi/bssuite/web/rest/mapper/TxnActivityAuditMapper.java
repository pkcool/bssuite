package com.enginemobi.bssuite.web.rest.mapper;

import com.enginemobi.bssuite.domain.*;
import com.enginemobi.bssuite.web.rest.dto.TxnActivityAuditDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TxnActivityAudit and its DTO TxnActivityAuditDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TxnActivityAuditMapper {

    @Mapping(source = "editedBy.id", target = "editedById")
    TxnActivityAuditDTO txnActivityAuditToTxnActivityAuditDTO(TxnActivityAudit txnActivityAudit);

    @Mapping(source = "editedById", target = "editedBy")
    TxnActivityAudit txnActivityAuditDTOToTxnActivityAudit(TxnActivityAuditDTO txnActivityAuditDTO);

    default Staff staffFromId(Long id) {
        if (id == null) {
            return null;
        }
        Staff staff = new Staff();
        staff.setId(id);
        return staff;
    }
}
