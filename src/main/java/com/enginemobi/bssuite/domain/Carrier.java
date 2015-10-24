package com.enginemobi.bssuite.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Carrier.
 */
@Entity
@Table(name = "carrier")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "carrier")
public class Carrier implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "account_no")
    private String accountNo;

    @Column(name = "comment")
    private String comment;

    @Column(name = "is_available_on_monday")
    private Boolean isAvailableOnMonday;

    @Column(name = "is_available_on_tuesday")
    private Boolean isAvailableOnTuesday;

    @Column(name = "is_available_on_wednesday")
    private Boolean isAvailableOnWednesday;

    @Column(name = "is_available_on_thursday")
    private Boolean isAvailableOnThursday;

    @Column(name = "is_available_on_friday")
    private Boolean isAvailableOnFriday;

    @Column(name = "is_available_on_saturday")
    private Boolean isAvailableOnSaturday;

    @Column(name = "is_available_on_sunday")
    private Boolean isAvailableOnSunday;

    @Column(name = "docket_ref_no")
    private String docketRefNo;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean getIsAvailableOnMonday() {
        return isAvailableOnMonday;
    }

    public void setIsAvailableOnMonday(Boolean isAvailableOnMonday) {
        this.isAvailableOnMonday = isAvailableOnMonday;
    }

    public Boolean getIsAvailableOnTuesday() {
        return isAvailableOnTuesday;
    }

    public void setIsAvailableOnTuesday(Boolean isAvailableOnTuesday) {
        this.isAvailableOnTuesday = isAvailableOnTuesday;
    }

    public Boolean getIsAvailableOnWednesday() {
        return isAvailableOnWednesday;
    }

    public void setIsAvailableOnWednesday(Boolean isAvailableOnWednesday) {
        this.isAvailableOnWednesday = isAvailableOnWednesday;
    }

    public Boolean getIsAvailableOnThursday() {
        return isAvailableOnThursday;
    }

    public void setIsAvailableOnThursday(Boolean isAvailableOnThursday) {
        this.isAvailableOnThursday = isAvailableOnThursday;
    }

    public Boolean getIsAvailableOnFriday() {
        return isAvailableOnFriday;
    }

    public void setIsAvailableOnFriday(Boolean isAvailableOnFriday) {
        this.isAvailableOnFriday = isAvailableOnFriday;
    }

    public Boolean getIsAvailableOnSaturday() {
        return isAvailableOnSaturday;
    }

    public void setIsAvailableOnSaturday(Boolean isAvailableOnSaturday) {
        this.isAvailableOnSaturday = isAvailableOnSaturday;
    }

    public Boolean getIsAvailableOnSunday() {
        return isAvailableOnSunday;
    }

    public void setIsAvailableOnSunday(Boolean isAvailableOnSunday) {
        this.isAvailableOnSunday = isAvailableOnSunday;
    }

    public String getDocketRefNo() {
        return docketRefNo;
    }

    public void setDocketRefNo(String docketRefNo) {
        this.docketRefNo = docketRefNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Carrier carrier = (Carrier) o;

        if ( ! Objects.equals(id, carrier.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Carrier{" +
            "id=" + id +
            ", code='" + code + "'" +
            ", name='" + name + "'" +
            ", phone='" + phone + "'" +
            ", mobile='" + mobile + "'" +
            ", accountNo='" + accountNo + "'" +
            ", comment='" + comment + "'" +
            ", isAvailableOnMonday='" + isAvailableOnMonday + "'" +
            ", isAvailableOnTuesday='" + isAvailableOnTuesday + "'" +
            ", isAvailableOnWednesday='" + isAvailableOnWednesday + "'" +
            ", isAvailableOnThursday='" + isAvailableOnThursday + "'" +
            ", isAvailableOnFriday='" + isAvailableOnFriday + "'" +
            ", isAvailableOnSaturday='" + isAvailableOnSaturday + "'" +
            ", isAvailableOnSunday='" + isAvailableOnSunday + "'" +
            ", docketRefNo='" + docketRefNo + "'" +
            '}';
    }
}
