package com.zain.reactivedemo.service;

import lombok.Builder;
import lombok.ToString;

import java.util.Date;
import java.util.Map;

/**
 * Bag holds feed and other metadata
 */
@Builder
@ToString
public class FeedsBag {
    private final String name;
    private final Map<String, FeedItem> feedMap;
    private final Date creationDate;
}
