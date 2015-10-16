package com.enginemobi.bssuite.web.rest.dto;

import org.joda.time.LocalDate;
import org.joda.time.DateTime;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.enginemobi.bssuite.domain.enumeration.InvoiceTxnType;
import com.enginemobi.bssuite.domain.enumeration.InvoiceSource;

/**
 * A DTO for the Invoice entity.
 */
public class InvoiceDTO implements Serializable {

    private Long id;

    private String invoiceNo;


    private InvoiceTxnType invoiceTxnType;


    private DateTime txnDate;


    private LocalDate dueDate;


    private String reference;


    private String ourRef;


    private String ourRef2;


    private String ourRef3;


    private String taxExemptionCode;


    private Boolean isPenaltyIssued;


    private BigDecimal freight;


    private BigDecimal handlingCharge;


    private BigDecimal charge2;


    private Boolean isTaxable;


    private Boolean isLocked;


    private BigDecimal adjustTax;


    private BigDecimal adjustTaxExempt;


    private BigDecimal paymentCash;


    private BigDecimal paymentCheque;


    private BigDecimal paymentCreditCard;


    private BigDecimal paymentDirectDeposit;


    private BigDecimal paymentVoucher;


    private BigDecimal paymentDirectDebit;


    private BigDecimal payment1;


    private BigDecimal payment2;


    private String bank;


    private String bankBranch;


    private String bankAccount;


    private LocalDate dateOfDeposit;


    private String drawerName;


    private BigDecimal NoAlloc;


    private BigDecimal coinRound;


    private BigDecimal prepayment;


    private String in2;


    private String comment;


    private BigDecimal totalTaxAmount;


    private BigDecimal totalSellPrice;


    private BigDecimal totalCost;


    private Boolean isOnHold;


    private Boolean isLayBy;


    private Boolean isExternalTxn;


    private Boolean isSuspended;


    private InvoiceSource generatedFrom;


    private Long customerId;

    private Long billingCustomerId;

    private Long contactId;

    private Long deliveryContactId;

    private Long storeId;

    private Long carrierId;

    private Long salespersonId;

    private Long promotionId;

    private Long createdById;

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


    public BigDecimal getTotalTaxAmount() {
        return totalTaxAmount;
    }

    public void setTotalTaxAmount(BigDecimal totalTaxAmount) {
        this.totalTaxAmount = totalTaxAmount;
    }


    public BigDecimal getTotalSellPrice() {
        return totalSellPrice;
    }

    public void setTotalSellPrice(BigDecimal totalSellPrice) {
        this.totalSellPrice = totalSellPrice;
    }


    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
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


    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getBillingCustomerId() {
        return billingCustomerId;
    }

    public void setBillingCustomerId(Long customerId) {
        this.billingCustomerId = customerId;
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

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Long getCarrierId() {
        return carrierId;
    }

    public void setCarrierId(Long carrierId) {
        this.carrierId = carrierId;
    }

    public Long getSalespersonId() {
        return salespersonId;
    }

    public void setSalespersonId(Long staffId) {
        this.salespersonId = staffId;
    }

    public Long getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Long promotionId) {
        this.promotionId = promotionId;
    }

    public Long getCreatedById() {
        return createdById;
    }

    public void setCreatedById(Long staffId) {
        this.createdById = staffId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InvoiceDTO invoiceDTO = (InvoiceDTO) o;

        if ( ! Objects.equals(id, invoiceDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "InvoiceDTO{" +
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
                ", totalTaxAmount='" + totalTaxAmount + "'" +
                ", totalSellPrice='" + totalSellPrice + "'" +
                ", totalCost='" + totalCost + "'" +
                ", isOnHold='" + isOnHold + "'" +
                ", isLayBy='" + isLayBy + "'" +
                ", isExternalTxn='" + isExternalTxn + "'" +
                ", isSuspended='" + isSuspended + "'" +
                ", generatedFrom='" + generatedFrom + "'" +
                '}';
    }
}
