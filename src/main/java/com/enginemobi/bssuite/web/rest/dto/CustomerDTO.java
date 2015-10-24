package com.enginemobi.bssuite.web.rest.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.enginemobi.bssuite.domain.enumeration.CustomerAgeingMethod;
import com.enginemobi.bssuite.domain.enumeration.CreditCardType;
import com.enginemobi.bssuite.domain.enumeration.CustomerAccountType;
import com.enginemobi.bssuite.domain.enumeration.CustomerInvoiceDeliveryMethod;
import com.enginemobi.bssuite.domain.enumeration.CustomerStatementDeliveryMethod;

/**
 * A DTO for the Customer entity.
 */
public class CustomerDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 2)
    private String code;

    private String name;

    private String tradingName;

    private String abn;

    private String deliveryInstruction;

    private String webUrl;

    private Boolean isOnHold;

    private Boolean isOrderNoRequired;

    private Boolean isBlacklisted;

    private Boolean isBackorderAllowed;

    private Boolean isArchived;

    private Boolean isHeadOfficeAccount;

    private CustomerAgeingMethod ageingMethod;

    private Boolean isWebaccessAllowed;

    private String bankName;

    private String bsb;

    private String bankAccountNo;

    private String bankSuburb;

    private String bankAccountName;

    private Boolean isCreditCardBillingActive;

    private String creditCardNo;

    private String creditCardHolderName;

    private LocalDate creditCardExpiryDate;

    private CreditCardType creditCardType;

    private CustomerAccountType accountType;

    private Double creditAmount;

    private Integer terms;

    private Integer settlementTerms;

    private CustomerInvoiceDeliveryMethod invoiceDeliveryMethod;

    private CustomerStatementDeliveryMethod statementDeliveryMethod;

    private String invoiceEmailAddress;

    private String invoiceFaxNo;

    private String statementEmailAddress;

    private String statementFaxNo;

    private Boolean isPenaltyIssued;

    private Long customerCategoryId;
    private Long contactId;
    private Long deliveryContactId;
    private Long salesPrimaryContactId;
    private Long salesSecondaryContactId;
    private Long accountPrimaryContactId;
    private Long accountSecondaryContactId;
    private Long repId;
    private Long centralBillingAccountId;
    private Long storeId;
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

    public String getTradingName() {
        return tradingName;
    }

    public void setTradingName(String tradingName) {
        this.tradingName = tradingName;
    }

    public String getAbn() {
        return abn;
    }

    public void setAbn(String abn) {
        this.abn = abn;
    }

    public String getDeliveryInstruction() {
        return deliveryInstruction;
    }

    public void setDeliveryInstruction(String deliveryInstruction) {
        this.deliveryInstruction = deliveryInstruction;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public Boolean getIsOnHold() {
        return isOnHold;
    }

    public void setIsOnHold(Boolean isOnHold) {
        this.isOnHold = isOnHold;
    }

    public Boolean getIsOrderNoRequired() {
        return isOrderNoRequired;
    }

    public void setIsOrderNoRequired(Boolean isOrderNoRequired) {
        this.isOrderNoRequired = isOrderNoRequired;
    }

    public Boolean getIsBlacklisted() {
        return isBlacklisted;
    }

    public void setIsBlacklisted(Boolean isBlacklisted) {
        this.isBlacklisted = isBlacklisted;
    }

    public Boolean getIsBackorderAllowed() {
        return isBackorderAllowed;
    }

    public void setIsBackorderAllowed(Boolean isBackorderAllowed) {
        this.isBackorderAllowed = isBackorderAllowed;
    }

    public Boolean getIsArchived() {
        return isArchived;
    }

    public void setIsArchived(Boolean isArchived) {
        this.isArchived = isArchived;
    }

    public Boolean getIsHeadOfficeAccount() {
        return isHeadOfficeAccount;
    }

    public void setIsHeadOfficeAccount(Boolean isHeadOfficeAccount) {
        this.isHeadOfficeAccount = isHeadOfficeAccount;
    }

    public CustomerAgeingMethod getAgeingMethod() {
        return ageingMethod;
    }

    public void setAgeingMethod(CustomerAgeingMethod ageingMethod) {
        this.ageingMethod = ageingMethod;
    }

    public Boolean getIsWebaccessAllowed() {
        return isWebaccessAllowed;
    }

    public void setIsWebaccessAllowed(Boolean isWebaccessAllowed) {
        this.isWebaccessAllowed = isWebaccessAllowed;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBsb() {
        return bsb;
    }

    public void setBsb(String bsb) {
        this.bsb = bsb;
    }

    public String getBankAccountNo() {
        return bankAccountNo;
    }

    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }

    public String getBankSuburb() {
        return bankSuburb;
    }

    public void setBankSuburb(String bankSuburb) {
        this.bankSuburb = bankSuburb;
    }

    public String getBankAccountName() {
        return bankAccountName;
    }

    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }

    public Boolean getIsCreditCardBillingActive() {
        return isCreditCardBillingActive;
    }

    public void setIsCreditCardBillingActive(Boolean isCreditCardBillingActive) {
        this.isCreditCardBillingActive = isCreditCardBillingActive;
    }

    public String getCreditCardNo() {
        return creditCardNo;
    }

    public void setCreditCardNo(String creditCardNo) {
        this.creditCardNo = creditCardNo;
    }

    public String getCreditCardHolderName() {
        return creditCardHolderName;
    }

    public void setCreditCardHolderName(String creditCardHolderName) {
        this.creditCardHolderName = creditCardHolderName;
    }

    public LocalDate getCreditCardExpiryDate() {
        return creditCardExpiryDate;
    }

    public void setCreditCardExpiryDate(LocalDate creditCardExpiryDate) {
        this.creditCardExpiryDate = creditCardExpiryDate;
    }

    public CreditCardType getCreditCardType() {
        return creditCardType;
    }

    public void setCreditCardType(CreditCardType creditCardType) {
        this.creditCardType = creditCardType;
    }

    public CustomerAccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(CustomerAccountType accountType) {
        this.accountType = accountType;
    }

    public Double getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(Double creditAmount) {
        this.creditAmount = creditAmount;
    }

    public Integer getTerms() {
        return terms;
    }

    public void setTerms(Integer terms) {
        this.terms = terms;
    }

    public Integer getSettlementTerms() {
        return settlementTerms;
    }

    public void setSettlementTerms(Integer settlementTerms) {
        this.settlementTerms = settlementTerms;
    }

    public CustomerInvoiceDeliveryMethod getInvoiceDeliveryMethod() {
        return invoiceDeliveryMethod;
    }

    public void setInvoiceDeliveryMethod(CustomerInvoiceDeliveryMethod invoiceDeliveryMethod) {
        this.invoiceDeliveryMethod = invoiceDeliveryMethod;
    }

    public CustomerStatementDeliveryMethod getStatementDeliveryMethod() {
        return statementDeliveryMethod;
    }

    public void setStatementDeliveryMethod(CustomerStatementDeliveryMethod statementDeliveryMethod) {
        this.statementDeliveryMethod = statementDeliveryMethod;
    }

    public String getInvoiceEmailAddress() {
        return invoiceEmailAddress;
    }

    public void setInvoiceEmailAddress(String invoiceEmailAddress) {
        this.invoiceEmailAddress = invoiceEmailAddress;
    }

    public String getInvoiceFaxNo() {
        return invoiceFaxNo;
    }

    public void setInvoiceFaxNo(String invoiceFaxNo) {
        this.invoiceFaxNo = invoiceFaxNo;
    }

    public String getStatementEmailAddress() {
        return statementEmailAddress;
    }

    public void setStatementEmailAddress(String statementEmailAddress) {
        this.statementEmailAddress = statementEmailAddress;
    }

    public String getStatementFaxNo() {
        return statementFaxNo;
    }

    public void setStatementFaxNo(String statementFaxNo) {
        this.statementFaxNo = statementFaxNo;
    }

    public Boolean getIsPenaltyIssued() {
        return isPenaltyIssued;
    }

    public void setIsPenaltyIssued(Boolean isPenaltyIssued) {
        this.isPenaltyIssued = isPenaltyIssued;
    }

    public Long getCustomerCategoryId() {
        return customerCategoryId;
    }

    public void setCustomerCategoryId(Long customerCategoryId) {
        this.customerCategoryId = customerCategoryId;
    }
    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }
    public Long getDeliveryContactId() {
        return deliveryContactId;
    }

    public void setDeliveryContactId(Long contactId) {
        this.deliveryContactId = contactId;
    }
    public Long getSalesPrimaryContactId() {
        return salesPrimaryContactId;
    }

    public void setSalesPrimaryContactId(Long contactId) {
        this.salesPrimaryContactId = contactId;
    }
    public Long getSalesSecondaryContactId() {
        return salesSecondaryContactId;
    }

    public void setSalesSecondaryContactId(Long contactId) {
        this.salesSecondaryContactId = contactId;
    }
    public Long getAccountPrimaryContactId() {
        return accountPrimaryContactId;
    }

    public void setAccountPrimaryContactId(Long contactId) {
        this.accountPrimaryContactId = contactId;
    }
    public Long getAccountSecondaryContactId() {
        return accountSecondaryContactId;
    }

    public void setAccountSecondaryContactId(Long contactId) {
        this.accountSecondaryContactId = contactId;
    }
    public Long getRepId() {
        return repId;
    }

    public void setRepId(Long staffId) {
        this.repId = staffId;
    }
    public Long getCentralBillingAccountId() {
        return centralBillingAccountId;
    }

    public void setCentralBillingAccountId(Long customerId) {
        this.centralBillingAccountId = customerId;
    }
    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CustomerDTO customerDTO = (CustomerDTO) o;

        if ( ! Objects.equals(id, customerDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CustomerDTO{" +
            "id=" + id +
            ", code='" + code + "'" +
            ", name='" + name + "'" +
            ", tradingName='" + tradingName + "'" +
            ", abn='" + abn + "'" +
            ", deliveryInstruction='" + deliveryInstruction + "'" +
            ", webUrl='" + webUrl + "'" +
            ", isOnHold='" + isOnHold + "'" +
            ", isOrderNoRequired='" + isOrderNoRequired + "'" +
            ", isBlacklisted='" + isBlacklisted + "'" +
            ", isBackorderAllowed='" + isBackorderAllowed + "'" +
            ", isArchived='" + isArchived + "'" +
            ", isHeadOfficeAccount='" + isHeadOfficeAccount + "'" +
            ", ageingMethod='" + ageingMethod + "'" +
            ", isWebaccessAllowed='" + isWebaccessAllowed + "'" +
            ", bankName='" + bankName + "'" +
            ", bsb='" + bsb + "'" +
            ", bankAccountNo='" + bankAccountNo + "'" +
            ", bankSuburb='" + bankSuburb + "'" +
            ", bankAccountName='" + bankAccountName + "'" +
            ", isCreditCardBillingActive='" + isCreditCardBillingActive + "'" +
            ", creditCardNo='" + creditCardNo + "'" +
            ", creditCardHolderName='" + creditCardHolderName + "'" +
            ", creditCardExpiryDate='" + creditCardExpiryDate + "'" +
            ", creditCardType='" + creditCardType + "'" +
            ", accountType='" + accountType + "'" +
            ", creditAmount='" + creditAmount + "'" +
            ", terms='" + terms + "'" +
            ", settlementTerms='" + settlementTerms + "'" +
            ", invoiceDeliveryMethod='" + invoiceDeliveryMethod + "'" +
            ", statementDeliveryMethod='" + statementDeliveryMethod + "'" +
            ", invoiceEmailAddress='" + invoiceEmailAddress + "'" +
            ", invoiceFaxNo='" + invoiceFaxNo + "'" +
            ", statementEmailAddress='" + statementEmailAddress + "'" +
            ", statementFaxNo='" + statementFaxNo + "'" +
            ", isPenaltyIssued='" + isPenaltyIssued + "'" +
            '}';
    }
}
