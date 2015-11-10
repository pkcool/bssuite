package com.enginemobi.bssuite.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.enginemobi.bssuite.domain.util.CustomDateTimeDeserializer;
import com.enginemobi.bssuite.domain.util.CustomDateTimeSerializer;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
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
@Document(indexName="calendaritem")
public class CalendarItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    
    @Column(name = "title")
    private String title;
    
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "started_on")
    private DateTime startedOn;
    
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "ended_on")
    private DateTime endedOn;
    
    @Column(name = "is_all_day_event")
    private Boolean isAllDayEvent;
    
    @Column(name = "is_remainder_enabled")
    private Boolean isRemainderEnabled;
    
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "remainder_time")
    private DateTime remainderTime;
    
    @Column(name = "web_url")
    private String webUrl;
    
    @Column(name = "is_editable")
    private Boolean isEditable;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "alarm_type")
    private AlarmType alarmType;

    @ManyToOne
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

    public DateTime getStartedOn() {
        return startedOn;
    }

    public void setStartedOn(DateTime startedOn) {
        this.startedOn = startedOn;
    }

    public DateTime getEndedOn() {
        return endedOn;
    }

    public void setEndedOn(DateTime endedOn) {
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

    public DateTime getRemainderTime() {
        return remainderTime;
    }

    public void setRemainderTime(DateTime remainderTime) {
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

        if ( ! Objects.equals(id, calendarItem.id)) return false;

        return true;
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
