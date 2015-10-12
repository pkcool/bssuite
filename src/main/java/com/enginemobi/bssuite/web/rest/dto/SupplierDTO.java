package com.enginemobi.bssuite.web.rest.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.enginemobi.bssuite.domain.enumeration.SupplierAccountType;
import com.enginemobi.bssuite.domain.enumeration.SupplierAgeingMethod;

/**
 * A DTO for the Supplier entity.
 */
public class SupplierDTO implements Serializable {

    private Long id;

    @NotNull
    private String code;


    private String name;


    private String webUrl;


    private String comment;


    private Boolean isOnHold;


    private Boolean isHeadOffice;


    private Integer leadTime;


    private SupplierAccountType accountType;


    private Integer settlementTerms;


    private Double credit;


    private Integer terms;


    private SupplierAgeingMethod ageingMethod;


    private Boolean isEFTPaymentsUsed;


    private String bankBSB;


    private String bankNumber;


    private String bankAccount;


    private Long supplierCategoryId;

    private Long contactId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }


    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    public Boolean getIsOnHold() {
        return isOnHold;
    }

    public void setIsOnHold(Boolean isOnHold) {
        this.isOnHold = isOnHold;
    }


    public Boolean getIsHeadOffice() {
        return isHeadOffice;
    }

    public void setIsHeadOffice(Boolean isHeadOffice) {
        this.isHeadOffice = isHeadOffice;
    }


    public Integer getLeadTime() {
        return leadTime;
    }

    public void setLeadTime(Integer leadTime) {
        this.leadTime = leadTime;
    }


    public SupplierAccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(SupplierAccountType accountType) {
        this.accountType = accountType;
    }


    public Integer getSettlementTerms() {
        return settlementTerms;
    }

    public void setSettlementTerms(Integer settlementTerms) {
        this.settlementTerms = settlementTerms;
    }


    public Double getCredit() {
        return credit;
    }

    public void setCredit(Double credit) {
        this.credit = credit;
    }


    public Integer getTerms() {
        return terms;
    }

    public void setTerms(Integer terms) {
        this.terms = terms;
    }


    public SupplierAgeingMethod getAgeingMethod() {
        return ageingMethod;
    }

    public void setAgeingMethod(SupplierAgeingMethod ageingMethod) {
        this.ageingMethod = ageingMethod;
    }


    public Boolean getIsEFTPaymentsUsed() {
        return isEFTPaymentsUsed;
    }

    public void setIsEFTPaymentsUsed(Boolean isEFTPaymentsUsed) {
        this.isEFTPaymentsUsed = isEFTPaymentsUsed;
    }


    public String getBankBSB() {
        return bankBSB;
    }

    public void setBankBSB(String bankBSB) {
        this.bankBSB = bankBSB;
    }


    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }


    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }


    public Long getSupplierCategoryId() {
        return supplierCategoryId;
    }

    public void setSupplierCategoryId(Long supplierCategoryId) {
        this.supplierCategoryId = supplierCategoryId;
    }

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SupplierDTO supplierDTO = (SupplierDTO) o;

        if ( ! Objects.equals(id, supplierDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SupplierDTO{" +
                "id=" + id +
                ", code='" + code + "'" +
                ", name='" + name + "'" +
                ", webUrl='" + webUrl + "'" +
                ", comment='" + comment + "'" +
                ", isOnHold='" + isOnHold + "'" +
                ", isHeadOffice='" + isHeadOffice + "'" +
                ", leadTime='" + leadTime + "'" +
                ", accountType='" + accountType + "'" +
                ", settlementTerms='" + settlementTerms + "'" +
                ", credit='" + credit + "'" +
                ", terms='" + terms + "'" +
                ", ageingMethod='" + ageingMethod + "'" +
                ", isEFTPaymentsUsed='" + isEFTPaymentsUsed + "'" +
                ", bankBSB='" + bankBSB + "'" +
                ", bankNumber='" + bankNumber + "'" +
                ", bankAccount='" + bankAccount + "'" +
                '}';
    }
}
