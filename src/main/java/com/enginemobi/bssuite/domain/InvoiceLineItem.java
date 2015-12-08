package com.enginemobi.bssuite.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A InvoiceLineItem.
 */
@Entity
@Table(name = "invoice_line_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "invoicelineitem")
public class InvoiceLineItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "cost", precision=10, scale=2)
    private BigDecimal cost;

    @Column(name = "sold_for", precision=10, scale=2)
    private BigDecimal soldFor;

    @Column(name = "qty_ordered")
    private Double qtyOrdered;

    @Column(name = "qty_sold")
    private Double qtySold;

    @Column(name = "qty_returned")
    private Double qtyReturned;

    @Column(name = "qty_picked")
    private Double qtyPicked;

    @Column(name = "total_tax_charge", precision=10, scale=2)
    private BigDecimal totalTaxCharge;

    @Column(name = "discount_percentage", precision=10, scale=2)
    private BigDecimal discountPercentage;

    @Column(name = "discount_description")
    private String discountDescription;

    @Column(name = "discount_price_group_code")
    private String discountPriceGroupCode;

    @Column(name = "line_no")
    private Integer lineNo;

    @Column(name = "list_price", precision=10, scale=2)
    private BigDecimal listPrice;

    @Column(name = "list_price_discount", precision=10, scale=2)
    private BigDecimal listPriceDiscount;

    @Column(name = "cost2", precision=10, scale=2)
    private BigDecimal cost2;

    @Column(name = "is_hidden")
    private Boolean isHidden;

    @Column(name = "ref1")
    private String Ref1;

    @Column(name = "ref2")
    private String Ref2;

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "tax_rate_id")
    private TaxTable taxRate;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public BigDecimal getSoldFor() {
        return soldFor;
    }

    public void setSoldFor(BigDecimal soldFor) {
        this.soldFor = soldFor;
    }

    public Double getQtyOrdered() {
        return qtyOrdered;
    }

    public void setQtyOrdered(Double qtyOrdered) {
        this.qtyOrdered = qtyOrdered;
    }

    public Double getQtySold() {
        return qtySold;
    }

    public void setQtySold(Double qtySold) {
        this.qtySold = qtySold;
    }

    public Double getQtyReturned() {
        return qtyReturned;
    }

    public void setQtyReturned(Double qtyReturned) {
        this.qtyReturned = qtyReturned;
    }

    public Double getQtyPicked() {
        return qtyPicked;
    }

    public void setQtyPicked(Double qtyPicked) {
        this.qtyPicked = qtyPicked;
    }

    public BigDecimal getTotalTaxCharge() {
        return totalTaxCharge;
    }

    public void setTotalTaxCharge(BigDecimal totalTaxCharge) {
        this.totalTaxCharge = totalTaxCharge;
    }

    public BigDecimal getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(BigDecimal discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public String getDiscountDescription() {
        return discountDescription;
    }

    public void setDiscountDescription(String discountDescription) {
        this.discountDescription = discountDescription;
    }

    public String getDiscountPriceGroupCode() {
        return discountPriceGroupCode;
    }

    public void setDiscountPriceGroupCode(String discountPriceGroupCode) {
        this.discountPriceGroupCode = discountPriceGroupCode;
    }

    public Integer getLineNo() {
        return lineNo;
    }

    public void setLineNo(Integer lineNo) {
        this.lineNo = lineNo;
    }

    public BigDecimal getListPrice() {
        return listPrice;
    }

    public void setListPrice(BigDecimal listPrice) {
        this.listPrice = listPrice;
    }

    public BigDecimal getListPriceDiscount() {
        return listPriceDiscount;
    }

    public void setListPriceDiscount(BigDecimal listPriceDiscount) {
        this.listPriceDiscount = listPriceDiscount;
    }

    public BigDecimal getCost2() {
        return cost2;
    }

    public void setCost2(BigDecimal cost2) {
        this.cost2 = cost2;
    }

    public Boolean getIsHidden() {
        return isHidden;
    }

    public void setIsHidden(Boolean isHidden) {
        this.isHidden = isHidden;
    }

    public String getRef1() {
        return Ref1;
    }

    public void setRef1(String Ref1) {
        this.Ref1 = Ref1;
    }

    public String getRef2() {
        return Ref2;
    }

    public void setRef2(String Ref2) {
        this.Ref2 = Ref2;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public TaxTable getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(TaxTable taxTable) {
        this.taxRate = taxTable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InvoiceLineItem invoiceLineItem = (InvoiceLineItem) o;
        return Objects.equals(id, invoiceLineItem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "InvoiceLineItem{" +
            "id=" + id +
            ", description='" + description + "'" +
            ", cost='" + cost + "'" +
            ", soldFor='" + soldFor + "'" +
            ", qtyOrdered='" + qtyOrdered + "'" +
            ", qtySold='" + qtySold + "'" +
            ", qtyReturned='" + qtyReturned + "'" +
            ", qtyPicked='" + qtyPicked + "'" +
            ", totalTaxCharge='" + totalTaxCharge + "'" +
            ", discountPercentage='" + discountPercentage + "'" +
            ", discountDescription='" + discountDescription + "'" +
            ", discountPriceGroupCode='" + discountPriceGroupCode + "'" +
            ", lineNo='" + lineNo + "'" +
            ", listPrice='" + listPrice + "'" +
            ", listPriceDiscount='" + listPriceDiscount + "'" +
            ", cost2='" + cost2 + "'" +
            ", isHidden='" + isHidden + "'" +
            ", Ref1='" + Ref1 + "'" +
            ", Ref2='" + Ref2 + "'" +
            '}';
    }
}
