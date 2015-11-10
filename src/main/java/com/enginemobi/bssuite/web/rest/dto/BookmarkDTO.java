package com.enginemobi.bssuite.web.rest.dto;

import org.joda.time.DateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.enginemobi.bssuite.domain.enumeration.BookmarkType;
import com.enginemobi.bssuite.domain.enumeration.BookmarkArea;

/**
 * A DTO for the Bookmark entity.
 */
public class BookmarkDTO implements Serializable {

    private Long id;

    private DateTime createdOn;


    private String txnNumber;


    private BookmarkType bookmarkType;


    private BookmarkArea bookmarkArea;


    private String keyCode;


    private String title;


    private DateTime lastEditedOn;


    private Integer openCount;


    private Long createdById;

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

        BookmarkDTO bookmarkDTO = (BookmarkDTO) o;

        if ( ! Objects.equals(id, bookmarkDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BookmarkDTO{" +
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
