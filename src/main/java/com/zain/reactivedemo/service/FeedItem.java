package com.zain.reactivedemo.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Response payload from the Service
 */
@Getter
@AllArgsConstructor
@ToString
public class FeedItem {
    private final String title;
    private final String content;
    private final String author;
    private final Date time;

    /**
     * Arbitrary method to get a List of strings
     * @return
     */
    public List<String> fetchLinks() {
        return Arrays.asList(title + hashCode(), content + hashCode(), author + hashCode());
    }
}
