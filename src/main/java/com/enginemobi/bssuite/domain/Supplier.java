package com.enginemobi.bssuite.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.enginemobi.bssuite.domain.enumeration.SupplierAccountType;

import com.enginemobi.bssuite.domain.enumeration.SupplierAgeingMethod;

/**
 * A Supplier.
 */
@Entity
@Table(name = "supplier")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "supplier")
public class Supplier implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "web_url")
    private String webUrl;

    @Column(name = "comment")
    private String comment;

    @Column(name = "is_on_hold")
    private Boolean isOnHold;

    @Column(name = "is_head_office")
    private Boolean isHeadOffice;

    @Column(name = "lead_time")
    private Integer leadTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type")
    private SupplierAccountType accountType;

    @Column(name = "settlement_terms")
    private Integer settlementTerms;

    @Column(name = "credit")
    private Double credit;

    @Column(name = "terms")
    private Integer terms;

    @Enumerated(EnumType.STRING)
    @Column(name = "ageing_method")
    private SupplierAgeingMethod ageingMethod;

    @Column(name = "is_eftpayments_used")
    private Boolean isEFTPaymentsUsed;

    @Column(name = "bank_bsb")
    private String bankBSB;

    @Column(name = "bank_number")
    private String bankNumber;

    @Column(name = "bank_account")
    private String bankAccount;

    @ManyToOne
    private SupplierCategory supplierCategory;

    @OneToOne    private Contact contact;

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

    public SupplierCategory getSupplierCategory() {
        return supplierCategory;
    }

    public void setSupplierCategory(SupplierCategory supplierCategory) {
        this.supplierCategory = supplierCategory;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Supplier supplier = (Supplier) o;

        if ( ! Objects.equals(id, supplier.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Supplier{" +
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
