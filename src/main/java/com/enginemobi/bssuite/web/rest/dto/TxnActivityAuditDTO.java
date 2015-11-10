package com.enginemobi.bssuite.web.rest.dto;

import org.joda.time.DateTime;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.enginemobi.bssuite.domain.enumeration.TxnType;
import com.enginemobi.bssuite.domain.enumeration.TxnEditType;

/**
 * A DTO for the TxnActivityAudit entity.
 */
public class TxnActivityAuditDTO implements Serializable {

    private Long id;

    private DateTime editedOn;


    private String txnNumber;


    private TxnType txnType;


    private BigDecimal txnAmount;


    private String bankAcc;


    private TxnEditType editType;


    private Long editedById;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DateTime getEditedOn() {
        return editedOn;
    }

    public void setEditedOn(DateTime editedOn) {
        this.editedOn = editedOn;
    }


    public String getTxnNumber() {
        return txnNumber;
    }

    public void setTxnNumber(String txnNumber) {
        this.txnNumber = txnNumber;
    }


    public TxnType getTxnType() {
        return txnType;
    }

    public void setTxnType(TxnType txnType) {
        this.txnType = txnType;
    }


    public BigDecimal getTxnAmount() {
        return txnAmount;
    }

    public void setTxnAmount(BigDecimal txnAmount) {
        this.txnAmount = txnAmount;
    }


    public String getBankAcc() {
        return bankAcc;
    }

    public void setBankAcc(String bankAcc) {
        this.bankAcc = bankAcc;
    }


    public TxnEditType getEditType() {
        return editType;
    }

    public void setEditType(TxnEditType editType) {
        this.editType = editType;
    }


    public Long getEditedById() {
        return editedById;
    }

    public void setEditedById(Long staffId) {
        this.editedById = staffId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TxnActivityAuditDTO txnActivityAuditDTO = (TxnActivityAuditDTO) o;

        if ( ! Objects.equals(id, txnActivityAuditDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TxnActivityAuditDTO{" +
                "id=" + id +
                ", editedOn='" + editedOn + "'" +
                ", txnNumber='" + txnNumber + "'" +
                ", txnType='" + txnType + "'" +
                ", txnAmount='" + txnAmount + "'" +
                ", bankAcc='" + bankAcc + "'" +
                ", editType='" + editType + "'" +
                '}';
    }
}
