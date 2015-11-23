package com.enginemobi.bssuite.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
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
 * A Customer.
 */
@Entity
@Table(name = "customer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "customer")
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 2)
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "trading_name")
    private String tradingName;

    @Column(name = "abn")
    private String abn;

    @Column(name = "delivery_instruction")
    private String deliveryInstruction;

    @Column(name = "web_url")
    private String webUrl;

    @Column(name = "is_on_hold")
    private Boolean isOnHold;

    @Column(name = "is_order_no_required")
    private Boolean isOrderNoRequired;

    @Column(name = "is_blacklisted")
    private Boolean isBlacklisted;

    @Column(name = "is_backorder_allowed")
    private Boolean isBackorderAllowed;

    @Column(name = "is_archived")
    private Boolean isArchived;

    @Column(name = "is_head_office_account")
    private Boolean isHeadOfficeAccount;

    @Enumerated(EnumType.STRING)
    @Column(name = "ageing_method")
    private CustomerAgeingMethod ageingMethod;

    @Column(name = "is_webaccess_allowed")
    private Boolean isWebaccessAllowed;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "bsb")
    private String bsb;

    @Column(name = "bank_account_no")
    private String bankAccountNo;

    @Column(name = "bank_suburb")
    private String bankSuburb;

    @Column(name = "bank_account_name")
    private String bankAccountName;

    @Column(name = "is_credit_card_billing_active")
    private Boolean isCreditCardBillingActive;

    @Column(name = "credit_card_no")
    private String creditCardNo;

    @Column(name = "credit_card_holder_name")
    private String creditCardHolderName;

    @Column(name = "credit_card_expiry_date")
    private LocalDate creditCardExpiryDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "credit_card_type")
    private CreditCardType creditCardType;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type")
    private CustomerAccountType accountType;

    @Column(name = "credit_amount")
    private Double creditAmount;

    @Column(name = "terms")
    private Integer terms;

    @Column(name = "settlement_terms")
    private Integer settlementTerms;

    @Enumerated(EnumType.STRING)
    @Column(name = "invoice_delivery_method")
    private CustomerInvoiceDeliveryMethod invoiceDeliveryMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "statement_delivery_method")
    private CustomerStatementDeliveryMethod statementDeliveryMethod;

    @Column(name = "invoice_email_address")
    private String invoiceEmailAddress;

    @Column(name = "invoice_fax_no")
    private String invoiceFaxNo;

    @Column(name = "statement_email_address")
    private String statementEmailAddress;

    @Column(name = "statement_fax_no")
    private String statementFaxNo;

    @Column(name = "is_penalty_issued")
    private Boolean isPenaltyIssued;

    @ManyToOne
    @JoinColumn(name = "customer_category_id")
    private CustomerCategory customerCategory;

    @ManyToOne
    @JoinColumn(name = "contact_id")
    private Contact contact;

    @ManyToOne
    @JoinColumn(name = "delivery_contact_id")
    private Contact deliveryContact;

    @ManyToOne
    @JoinColumn(name = "sales_primary_contact_id")
    private Contact salesPrimaryContact;

    @ManyToOne
    @JoinColumn(name = "sales_secondary_contact_id")
    private Contact salesSecondaryContact;

    @ManyToOne
    @JoinColumn(name = "account_primary_contact_id")
    private Contact accountPrimaryContact;

    @ManyToOne
    @JoinColumn(name = "account_secondary_contact_id")
    private Contact accountSecondaryContact;

    @ManyToOne
    @JoinColumn(name = "rep_id")
    private Staff rep;

    @ManyToOne
    @JoinColumn(name = "central_billing_account_id")
    private Customer centralBillingAccount;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

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

    public CustomerCategory getCustomerCategory() {
        return customerCategory;
    }

    public void setCustomerCategory(CustomerCategory customerCategory) {
        this.customerCategory = customerCategory;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Contact getDeliveryContact() {
        return deliveryContact;
    }

    public void setDeliveryContact(Contact contact) {
        this.deliveryContact = contact;
    }

    public Contact getSalesPrimaryContact() {
        return salesPrimaryContact;
    }

    public void setSalesPrimaryContact(Contact contact) {
        this.salesPrimaryContact = contact;
    }

    public Contact getSalesSecondaryContact() {
        return salesSecondaryContact;
    }

    public void setSalesSecondaryContact(Contact contact) {
        this.salesSecondaryContact = contact;
    }

    public Contact getAccountPrimaryContact() {
        return accountPrimaryContact;
    }

    public void setAccountPrimaryContact(Contact contact) {
        this.accountPrimaryContact = contact;
    }

    public Contact getAccountSecondaryContact() {
        return accountSecondaryContact;
    }

    public void setAccountSecondaryContact(Contact contact) {
        this.accountSecondaryContact = contact;
    }

    public Staff getRep() {
        return rep;
    }

    public void setRep(Staff staff) {
        this.rep = staff;
    }

    public Customer getCentralBillingAccount() {
        return centralBillingAccount;
    }

    public void setCentralBillingAccount(Customer customer) {
        this.centralBillingAccount = customer;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Customer{" +
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
