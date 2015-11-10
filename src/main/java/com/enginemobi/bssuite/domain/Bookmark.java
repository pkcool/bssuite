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

import com.enginemobi.bssuite.domain.enumeration.BookmarkType;

import com.enginemobi.bssuite.domain.enumeration.BookmarkArea;

/**
 * A Bookmark.
 */
@Entity
@Table(name = "bookmark")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName="bookmark")
public class Bookmark implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "created_on")
    private DateTime createdOn;
    
    @Column(name = "txn_number")
    private String txnNumber;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "bookmark_type")
    private BookmarkType bookmarkType;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "bookmark_area")
    private BookmarkArea bookmarkArea;
    
    @Column(name = "key_code")
    private String keyCode;
    
    @Column(name = "title")
    private String title;
    
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "last_edited_on")
    private DateTime lastEditedOn;
    
    @Column(name = "open_count")
    private Integer openCount;

    @ManyToOne
    private Staff createdBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(DateTime createdOn) {
        this.createdOn = createdOn;
    }

    public String getTxnNumber() {
        return txnNumber;
    }

    public void setTxnNumber(String txnNumber) {
        this.txnNumber = txnNumber;
    }

    public BookmarkType getBookmarkType() {
        return bookmarkType;
    }

    public void setBookmarkType(BookmarkType bookmarkType) {
        this.bookmarkType = bookmarkType;
    }

    public BookmarkArea getBookmarkArea() {
        return bookmarkArea;
    }

    public void setBookmarkArea(BookmarkArea bookmarkArea) {
        this.bookmarkArea = bookmarkArea;
    }

    public String getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(String keyCode) {
        this.keyCode = keyCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public DateTime getLastEditedOn() {
        return lastEditedOn;
    }

    public void setLastEditedOn(DateTime lastEditedOn) {
        this.lastEditedOn = lastEditedOn;
    }

    public Integer getOpenCount() {
        return openCount;
    }

    public void setOpenCount(Integer openCount) {
        this.openCount = openCount;
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

        Bookmark bookmark = (Bookmark) o;

        if ( ! Objects.equals(id, bookmark.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Bookmark{" +
                "id=" + id +
                ", createdOn='" + createdOn + "'" +
                ", txnNumber='" + txnNumber + "'" +
                ", bookmarkType='" + bookmarkType + "'" +
                ", bookmarkArea='" + bookmarkArea + "'" +
                ", keyCode='" + keyCode + "'" +
                ", title='" + title + "'" +
                ", lastEditedOn='" + lastEditedOn + "'" +
                ", openCount='" + openCount + "'" +
                '}';
    }
}
