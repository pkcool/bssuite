package com.enginemobi.bssuite.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.enginemobi.bssuite.domain.util.CustomLocalDateSerializer;
import com.enginemobi.bssuite.domain.util.ISO8601LocalDateDeserializer;
import com.enginemobi.bssuite.domain.util.CustomDateTimeDeserializer;
import com.enginemobi.bssuite.domain.util.CustomDateTimeSerializer;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;
import org.joda.time.DateTime;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.enginemobi.bssuite.domain.enumeration.InvoiceTxnType;

import com.enginemobi.bssuite.domain.enumeration.InvoiceSource;

/**
 * A Invoice.
 */
@Entity
@Table(name = "invoice")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName="invoice")
public class Invoice implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    
    @Column(name = "invoice_no")
    private String invoiceNo;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "invoice_txn_type")
    private InvoiceTxnType invoiceTxnType;
    
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "txn_date")
    private DateTime txnDate;
    
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
    @Column(name = "due_date")
    private LocalDate dueDate;
    
    @Column(name = "reference")
    private String reference;
    
    @Column(name = "our_ref")
    private String ourRef;
    
    @Column(name = "our_ref2")
    private String ourRef2;
    
    @Column(name = "our_ref3")
    private String ourRef3;
    
    @Column(name = "tax_exemption_code")
    private String taxExemptionCode;
    
    @Column(name = "is_penalty_issued")
    private Boolean isPenaltyIssued;
    
    @Column(name = "freight", precision=10, scale=2)
    private BigDecimal freight;
    
    @Column(name = "handling_charge", precision=10, scale=2)
    private BigDecimal handlingCharge;
    
    @Column(name = "charge2", precision=10, scale=2)
    private BigDecimal charge2;
    
    @Column(name = "is_taxable")
    private Boolean isTaxable;
    
    @Column(name = "is_locked")
    private Boolean isLocked;
    
    @Column(name = "adjust_tax", precision=10, scale=2)
    private BigDecimal adjustTax;
    
    @Column(name = "adjust_tax_exempt", precision=10, scale=2)
    private BigDecimal adjustTaxExempt;
    
    @Column(name = "payment_cash", precision=10, scale=2)
    private BigDecimal paymentCash;
    
    @Column(name = "payment_cheque", precision=10, scale=2)
    private BigDecimal paymentCheque;
    
    @Column(name = "payment_credit_card", precision=10, scale=2)
    private BigDecimal paymentCreditCard;
    
    @Column(name = "payment_direct_deposit", precision=10, scale=2)
    private BigDecimal paymentDirectDeposit;
    
    @Column(name = "payment_voucher", precision=10, scale=2)
    private BigDecimal paymentVoucher;
    
    @Column(name = "payment_direct_debit", precision=10, scale=2)
    private BigDecimal paymentDirectDebit;
    
    @Column(name = "payment1", precision=10, scale=2)
    private BigDecimal payment1;
    
    @Column(name = "payment2", precision=10, scale=2)
    private BigDecimal payment2;
    
    @Column(name = "bank")
    private String bank;
    
    @Column(name = "bank_branch")
    private String bankBranch;
    
    @Column(name = "bank_account")
    private String bankAccount;
    
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
    @Column(name = "date_of_deposit")
    private LocalDate dateOfDeposit;
    
    @Column(name = "drawer_name")
    private String drawerName;
    
    @Column(name = "no_alloc", precision=10, scale=2)
    private BigDecimal NoAlloc;
    
    @Column(name = "coin_round", precision=10, scale=2)
    private BigDecimal coinRound;
    
    @Column(name = "prepayment", precision=10, scale=2)
    private BigDecimal prepayment;
    
    @Column(name = "in2")
    private String in2;
    
    @Column(name = "comment")
    private String comment;
    
    @Column(name = "tax_amount", precision=10, scale=2)
    private BigDecimal taxAmount;
    
    @Column(name = "total", precision=10, scale=2)
    private BigDecimal total;
    
    @Column(name = "cost", precision=10, scale=2)
    private BigDecimal cost;
    
    @Column(name = "is_on_hold")
    private Boolean isOnHold;
    
    @Column(name = "is_lay_by")
    private Boolean isLayBy;
    
    @Column(name = "is_external_txn")
    private Boolean isExternalTxn;
    
    @Column(name = "is_suspended")
    private Boolean isSuspended;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "generated_from")
    private InvoiceSource generatedFrom;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Customer billingCustomer;

    @ManyToOne
    private Contact contact;

    @ManyToOne
    private Contact deliveryContact;

    @ManyToOne
    private Store store;

    @ManyToOne
    private Carrier carrier;

    @ManyToOne
    private Staff salesperson;

    @ManyToOne
    private Promotion promotion;

    @ManyToOne
    private Staff createdBy;

    @OneToMany(mappedBy = "invoice")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<InvoiceLineItem> invoiceLineItemss = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public InvoiceTxnType getInvoiceTxnType() {
        return invoiceTxnType;
    }

    public void setInvoiceTxnType(InvoiceTxnType invoiceTxnType) {
        this.invoiceTxnType = invoiceTxnType;
    }

    public DateTime getTxnDate() {
        return txnDate;
    }

    public void setTxnDate(DateTime txnDate) {
        this.txnDate = txnDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getOurRef() {
        return ourRef;
    }

    public void setOurRef(String ourRef) {
        this.ourRef = ourRef;
    }

    public String getOurRef2() {
        return ourRef2;
    }

    public void setOurRef2(String ourRef2) {
        this.ourRef2 = ourRef2;
    }

    public String getOurRef3() {
        return ourRef3;
    }

    public void setOurRef3(String ourRef3) {
        this.ourRef3 = ourRef3;
    }

    public String getTaxExemptionCode() {
        return taxExemptionCode;
    }

    public void setTaxExemptionCode(String taxExemptionCode) {
        this.taxExemptionCode = taxExemptionCode;
    }

    public Boolean getIsPenaltyIssued() {
        return isPenaltyIssued;
    }

    public void setIsPenaltyIssued(Boolean isPenaltyIssued) {
        this.isPenaltyIssued = isPenaltyIssued;
    }

    public BigDecimal getFreight() {
        return freight;
    }

    public void setFreight(BigDecimal freight) {
        this.freight = freight;
    }

    public BigDecimal getHandlingCharge() {
        return handlingCharge;
    }

    public void setHandlingCharge(BigDecimal handlingCharge) {
        this.handlingCharge = handlingCharge;
    }

    public BigDecimal getCharge2() {
        return charge2;
    }

    public void setCharge2(BigDecimal charge2) {
        this.charge2 = charge2;
    }

    public Boolean getIsTaxable() {
        return isTaxable;
    }

    public void setIsTaxable(Boolean isTaxable) {
        this.isTaxable = isTaxable;
    }

    public Boolean getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(Boolean isLocked) {
        this.isLocked = isLocked;
    }

    public BigDecimal getAdjustTax() {
        return adjustTax;
    }

    public void setAdjustTax(BigDecimal adjustTax) {
        this.adjustTax = adjustTax;
    }

    public BigDecimal getAdjustTaxExempt() {
        return adjustTaxExempt;
    }

    public void setAdjustTaxExempt(BigDecimal adjustTaxExempt) {
        this.adjustTaxExempt = adjustTaxExempt;
    }

    public BigDecimal getPaymentCash() {
        return paymentCash;
    }

    public void setPaymentCash(BigDecimal paymentCash) {
        this.paymentCash = paymentCash;
    }

    public BigDecimal getPaymentCheque() {
        return paymentCheque;
    }

    public void setPaymentCheque(BigDecimal paymentCheque) {
        this.paymentCheque = paymentCheque;
    }

    public BigDecimal getPaymentCreditCard() {
        return paymentCreditCard;
    }

    public void setPaymentCreditCard(BigDecimal paymentCreditCard) {
        this.paymentCreditCard = paymentCreditCard;
    }

    public BigDecimal getPaymentDirectDeposit() {
        return paymentDirectDeposit;
    }

    public void setPaymentDirectDeposit(BigDecimal paymentDirectDeposit) {
        this.paymentDirectDeposit = paymentDirectDeposit;
    }

    public BigDecimal getPaymentVoucher() {
        return paymentVoucher;
    }

    public void setPaymentVoucher(BigDecimal paymentVoucher) {
        this.paymentVoucher = paymentVoucher;
    }

    public BigDecimal getPaymentDirectDebit() {
        return paymentDirectDebit;
    }

    public void setPaymentDirectDebit(BigDecimal paymentDirectDebit) {
        this.paymentDirectDebit = paymentDirectDebit;
    }

    public BigDecimal getPayment1() {
        return payment1;
    }

    public void setPayment1(BigDecimal payment1) {
        this.payment1 = payment1;
    }

    public BigDecimal getPayment2() {
        return payment2;
    }

    public void setPayment2(BigDecimal payment2) {
        this.payment2 = payment2;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBankBranch() {
        return bankBranch;
    }

    public void setBankBranch(String bankBranch) {
        this.bankBranch = bankBranch;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public LocalDate getDateOfDeposit() {
        return dateOfDeposit;
    }

    public void setDateOfDeposit(LocalDate dateOfDeposit) {
        this.dateOfDeposit = dateOfDeposit;
    }

    public String getDrawerName() {
        return drawerName;
    }

    public void setDrawerName(String drawerName) {
        this.drawerName = drawerName;
    }

    public BigDecimal getNoAlloc() {
        return NoAlloc;
    }

    public void setNoAlloc(BigDecimal NoAlloc) {
        this.NoAlloc = NoAlloc;
    }

    public BigDecimal getCoinRound() {
        return coinRound;
    }

    public void setCoinRound(BigDecimal coinRound) {
        this.coinRound = coinRound;
    }

    public BigDecimal getPrepayment() {
        return prepayment;
    }

    public void setPrepayment(BigDecimal prepayment) {
        this.prepayment = prepayment;
    }

    public String getIn2() {
        return in2;
    }

    public void setIn2(String in2) {
        this.in2 = in2;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Boolean getIsOnHold() {
        return isOnHold;
    }

    public void setIsOnHold(Boolean isOnHold) {
        this.isOnHold = isOnHold;
    }

    public Boolean getIsLayBy() {
        return isLayBy;
    }

    public void setIsLayBy(Boolean isLayBy) {
        this.isLayBy = isLayBy;
    }

    public Boolean getIsExternalTxn() {
        return isExternalTxn;
    }

    public void setIsExternalTxn(Boolean isExternalTxn) {
        this.isExternalTxn = isExternalTxn;
    }

    public Boolean getIsSuspended() {
        return isSuspended;
    }

    public void setIsSuspended(Boolean isSuspended) {
        this.isSuspended = isSuspended;
    }

    public InvoiceSource getGeneratedFrom() {
        return generatedFrom;
    }

    public void setGeneratedFrom(InvoiceSource generatedFrom) {
        this.generatedFrom = generatedFrom;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Customer getBillingCustomer() {
        return billingCustomer;
    }

    public void setBillingCustomer(Customer customer) {
        this.billingCustomer = customer;
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

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Carrier getCarrier() {
        return carrier;
    }

    public void setCarrier(Carrier carrier) {
        this.carrier = carrier;
    }

    public Staff getSalesperson() {
        return salesperson;
    }

    public void setSalesperson(Staff staff) {
        this.salesperson = staff;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    public Staff getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Staff staff) {
        this.createdBy = staff;
    }

    public Set<InvoiceLineItem> getInvoiceLineItemss() {
        return invoiceLineItemss;
    }

    public void setInvoiceLineItemss(Set<InvoiceLineItem> invoiceLineItems) {
        this.invoiceLineItemss = invoiceLineItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Invoice invoice = (Invoice) o;

        if ( ! Objects.equals(id, invoice.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                ", invoiceNo='" + invoiceNo + "'" +
                ", invoiceTxnType='" + invoiceTxnType + "'" +
                ", txnDate='" + txnDate + "'" +
                ", dueDate='" + dueDate + "'" +
                ", reference='" + reference + "'" +
                ", ourRef='" + ourRef + "'" +
                ", ourRef2='" + ourRef2 + "'" +
                ", ourRef3='" + ourRef3 + "'" +
                ", taxExemptionCode='" + taxExemptionCode + "'" +
                ", isPenaltyIssued='" + isPenaltyIssued + "'" +
                ", freight='" + freight + "'" +
                ", handlingCharge='" + handlingCharge + "'" +
                ", charge2='" + charge2 + "'" +
                ", isTaxable='" + isTaxable + "'" +
                ", isLocked='" + isLocked + "'" +
                ", adjustTax='" + adjustTax + "'" +
                ", adjustTaxExempt='" + adjustTaxExempt + "'" +
                ", paymentCash='" + paymentCash + "'" +
                ", paymentCheque='" + paymentCheque + "'" +
                ", paymentCreditCard='" + paymentCreditCard + "'" +
                ", paymentDirectDeposit='" + paymentDirectDeposit + "'" +
                ", paymentVoucher='" + paymentVoucher + "'" +
                ", paymentDirectDebit='" + paymentDirectDebit + "'" +
                ", payment1='" + payment1 + "'" +
                ", payment2='" + payment2 + "'" +
                ", bank='" + bank + "'" +
                ", bankBranch='" + bankBranch + "'" +
                ", bankAccount='" + bankAccount + "'" +
                ", dateOfDeposit='" + dateOfDeposit + "'" +
                ", drawerName='" + drawerName + "'" +
                ", NoAlloc='" + NoAlloc + "'" +
                ", coinRound='" + coinRound + "'" +
                ", prepayment='" + prepayment + "'" +
                ", in2='" + in2 + "'" +
                ", comment='" + comment + "'" +
                ", taxAmount='" + taxAmount + "'" +
                ", total='" + total + "'" +
                ", cost='" + cost + "'" +
                ", isOnHold='" + isOnHold + "'" +
                ", isLayBy='" + isLayBy + "'" +
                ", isExternalTxn='" + isExternalTxn + "'" +
                ", isSuspended='" + isSuspended + "'" +
                ", generatedFrom='" + generatedFrom + "'" +
                '}';
    }
}
