package com.enginemobi.bssuite.web.rest.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the Store entity.
 */
public class StoreDTO implements Serializable {

    private Long id;

    private String code;

    private String name;

    private String address1;

    private String address2;

    private String suburb;

    private String state;

    private String postcode;

    private String country;

    private String phone;

    private String fax;

    private String email;

    private String webUrl;

    private LocalDate inBusinessSince;

    private Boolean isArchived;

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

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public LocalDate getInBusinessSince() {
        return inBusinessSince;
    }

    public void setInBusinessSince(LocalDate inBusinessSince) {
        this.inBusinessSince = inBusinessSince;
    }

    public Boolean getIsArchived() {
        return isArchived;
    }

    public void setIsArchived(Boolean isArchived) {
        this.isArchived = isArchived;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StoreDTO storeDTO = (StoreDTO) o;

        if ( ! Objects.equals(id, storeDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "StoreDTO{" +
            "id=" + id +
            ", code='" + code + "'" +
            ", name='" + name + "'" +
            ", address1='" + address1 + "'" +
            ", address2='" + address2 + "'" +
            ", suburb='" + suburb + "'" +
            ", state='" + state + "'" +
            ", postcode='" + postcode + "'" +
            ", country='" + country + "'" +
            ", phone='" + phone + "'" +
            ", fax='" + fax + "'" +
            ", email='" + email + "'" +
            ", webUrl='" + webUrl + "'" +
            ", inBusinessSince='" + inBusinessSince + "'" +
            ", isArchived='" + isArchived + "'" +
            '}';
    }
}
