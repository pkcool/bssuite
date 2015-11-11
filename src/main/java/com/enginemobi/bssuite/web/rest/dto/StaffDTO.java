package com.enginemobi.bssuite.web.rest.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Staff entity.
 */
public class StaffDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 2)
    private String code;

    private String name;

    private String comment;

    private Double commission;

    private String occupation;

    private String workPhone;

    private String homePhone;

    private String workMobile;

    private String homeMobile;

    private String webEmail;

    private String homeEmail;

    private LocalDate birthday;

    private Boolean isLockedToStore;

    private Boolean isTechnical;

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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Double getCommission() {
        return commission;
    }

    public void setCommission(Double commission) {
        this.commission = commission;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getWorkMobile() {
        return workMobile;
    }

    public void setWorkMobile(String workMobile) {
        this.workMobile = workMobile;
    }

    public String getHomeMobile() {
        return homeMobile;
    }

    public void setHomeMobile(String homeMobile) {
        this.homeMobile = homeMobile;
    }

    public String getWebEmail() {
        return webEmail;
    }

    public void setWebEmail(String webEmail) {
        this.webEmail = webEmail;
    }

    public String getHomeEmail() {
        return homeEmail;
    }

    public void setHomeEmail(String homeEmail) {
        this.homeEmail = homeEmail;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Boolean getIsLockedToStore() {
        return isLockedToStore;
    }

    public void setIsLockedToStore(Boolean isLockedToStore) {
        this.isLockedToStore = isLockedToStore;
    }

    public Boolean getIsTechnical() {
        return isTechnical;
    }

    public void setIsTechnical(Boolean isTechnical) {
        this.isTechnical = isTechnical;
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

        StaffDTO staffDTO = (StaffDTO) o;

        if ( ! Objects.equals(id, staffDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "StaffDTO{" +
            "id=" + id +
            ", code='" + code + "'" +
            ", name='" + name + "'" +
            ", comment='" + comment + "'" +
            ", commission='" + commission + "'" +
            ", occupation='" + occupation + "'" +
            ", workPhone='" + workPhone + "'" +
            ", homePhone='" + homePhone + "'" +
            ", workMobile='" + workMobile + "'" +
            ", homeMobile='" + homeMobile + "'" +
            ", webEmail='" + webEmail + "'" +
            ", homeEmail='" + homeEmail + "'" +
            ", birthday='" + birthday + "'" +
            ", isLockedToStore='" + isLockedToStore + "'" +
            ", isTechnical='" + isTechnical + "'" +
            '}';
    }
}
