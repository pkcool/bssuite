package com.enginemobi.bssuite.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.ZonedDateTime;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.enginemobi.bssuite.domain.enumeration.AlarmType;

/**
 * A CalendarItem.
 */
@Entity
@Table(name = "calendar_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "calendaritem")
public class CalendarItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "started_on")
    private ZonedDateTime startedOn;

    @Column(name = "ended_on")
    private ZonedDateTime endedOn;

    @Column(name = "is_all_day_event")
    private Boolean isAllDayEvent;

    @Column(name = "is_remainder_enabled")
    private Boolean isRemainderEnabled;

    @Column(name = "remainder_time")
    private ZonedDateTime remainderTime;

    @Column(name = "web_url")
    private String webUrl;

    @Column(name = "is_editable")
    private Boolean isEditable;

    @Enumerated(EnumType.STRING)
    @Column(name = "alarm_type")
    private AlarmType alarmType;

    @ManyToOne
    @JoinColumn(name = "created_by_id")
    private Staff createdBy;

    
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

    public Staff getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Staff staff) {
        this.createdBy = staff;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CalendarItem calendarItem = (CalendarItem) o;
        return Objects.equals(id, calendarItem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CalendarItem{" +
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
