package com.zain.reactivedemo;

import com.zain.reactivedemo.service.FeedServer;
import com.zain.reactivedemo.service.FeedsBag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 *
 */
@Component
@Slf4j
public class FeedClient {

    private final FeedServer feedServer;

    public FeedClient(FeedServer feedServer) {
        this.feedServer = feedServer;
    }

    public void storeFeedsIntoBag() {
        log.info("Case: Fetch feeds and collect them into a map by key");

        /**
         * 0-n (feeds: Generate FeedItem)
         * 1 (doOnSubscribe: Log subscription)
         * 0-n (doOnNext: Log FeedItem)
         * 1 (collectMap: Collect FeedItems into map keyed by item name)
         * 1 (doOnNext: Log map size)
         * 1 (map: Transform feedMap into a FeedsBag)
         * 1 (subscribe: Terminal operation that logs feedsBag)
         */
        feedServer
                .feeds()
                .doOnSubscribe(subscription -> log.info("Checkout subscription {}", subscription))
                .doOnNext(feedItem -> log.info(feedItem.toString()))
                .collectMap((feedItem -> feedItem.getTitle()))
                .doOnNext(feedMap -> log.info("Incoming map of size {}", feedMap.size()))
                .map(feedMap -> FeedsBag
                        .builder()
                        .feedMap(feedMap)
                        .creationDate(new Date())
                        .name("Feed Bag").build())
                .subscribe(feedsBag -> log.info("In the subscriber with a bag {}", feedsBag.toString()));
    }
}
