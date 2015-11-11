package com.enginemobi.bssuite.web.rest.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.enginemobi.bssuite.domain.enumeration.AlarmType;

/**
 * A DTO for the CalendarItem entity.
 */
public class CalendarItemDTO implements Serializable {

    private Long id;

    private String title;

    private ZonedDateTime startedOn;

    private ZonedDateTime endedOn;

    private Boolean isAllDayEvent;

    private Boolean isRemainderEnabled;

    private ZonedDateTime remainderTime;

    private String webUrl;

    private Boolean isEditable;

    private AlarmType alarmType;

    private Long createdById;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ZonedDateTime getStartedOn() {
        return startedOn;
    }

    public void setStartedOn(ZonedDateTime startedOn) {
        this.startedOn = startedOn;
    }

    public ZonedDateTime getEndedOn() {
        return endedOn;
    }

    public void setEndedOn(ZonedDateTime endedOn) {
        this.endedOn = endedOn;
    }

    public Boolean getIsAllDayEvent() {
        return isAllDayEvent;
    }

    public void setIsAllDayEvent(Boolean isAllDayEvent) {
        this.isAllDayEvent = isAllDayEvent;
    }

    public Boolean getIsRemainderEnabled() {
        return isRemainderEnabled;
    }

    public void setIsRemainderEnabled(Boolean isRemainderEnabled) {
        this.isRemainderEnabled = isRemainderEnabled;
    }

    public ZonedDateTime getRemainderTime() {
        return remainderTime;
    }

    public void setRemainderTime(ZonedDateTime remainderTime) {
        this.remainderTime = remainderTime;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public Boolean getIsEditable() {
        return isEditable;
    }

    public void setIsEditable(Boolean isEditable) {
        this.isEditable = isEditable;
    }

    public AlarmType getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(AlarmType alarmType) {
        this.alarmType = alarmType;
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

        CalendarItemDTO calendarItemDTO = (CalendarItemDTO) o;

        if ( ! Objects.equals(id, calendarItemDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CalendarItemDTO{" +
            "id=" + id +
            ", title='" + title + "'" +
            ", startedOn='" + startedOn + "'" +
            ", endedOn='" + endedOn + "'" +
            ", isAllDayEvent='" + isAllDayEvent + "'" +
            ", isRemainderEnabled='" + isRemainderEnabled + "'" +
            ", remainderTime='" + remainderTime + "'" +
            ", webUrl='" + webUrl + "'" +
            ", isEditable='" + isEditable + "'" +
            ", alarmType='" + alarmType + "'" +
            '}';
    }
}
