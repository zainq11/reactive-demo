package com.zain.reactivedemo.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Response payload from the Service
 */
@Data
@ToString
@AllArgsConstructor
public class FeedItem {
    private String title;
    private String content;
    private String author;
    private Date time;

    public FeedItem(String title) {
        this.title = title;
    }

    /**
     * Arbitrary method to get a List of strings
     *
     * @return
     */
    public List<String> fetchDataVector() {
        return Arrays.asList(title, content, author);
    }
}
