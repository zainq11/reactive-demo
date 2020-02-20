package com.zain.reactivedemo;

import com.zain.reactivedemo.service.FeedItem;
import com.zain.reactivedemo.service.FeedServer;
import com.zain.reactivedemo.service.FeedsBag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    public void storeMonosIntoBag() {
        log.info("Case: Fetch Mono<FeedItem> and collect them into a bag");
        Iterable<Mono<FeedItem>> iterable = Arrays.asList("1", "2", "3", "4", "5")
                .stream()
                .map(feedServer::feed)
                .collect(Collectors.toList());

        Mono.zip(iterable, (items) -> {
            return FeedsBag.builder().name("The Mono Bag").creationDate(new Date()).feedMap(collectToMap(items)).build();
        }).subscribe(feedsBag -> log.info("In the subscriber with a bag {}", feedsBag.toString()));
    }

    protected static Map<String, FeedItem> collectToMap(Object[] feedItems) {
        return Arrays
                .stream(feedItems)
                .map(item -> (FeedItem) item)
                .collect(
                        Collectors.toMap(
                                FeedItem::getTitle,
                                Function.identity()
                        )
                );
    }
}
