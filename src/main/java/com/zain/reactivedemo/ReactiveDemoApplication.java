package com.zain.reactivedemo;

import com.zain.reactivedemo.service.FeedService;
import com.zain.reactivedemo.service.FeedsBag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication
@Slf4j
public class ReactiveDemoApplication implements CommandLineRunner {
    private final FeedService feedService = new FeedService();

    public static void main(String[] args) {
        SpringApplication.run(ReactiveDemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("EXECUTING the application");

        log.info("Case: Fetch feeds and collect them into a map by key");
        /**
         * 0-n (getFeeds: Generate FeedItem)
         * 1 (doOnSubscribe: Log subscription)
         * 0-n (doOnNext: Log FeedItem)
         * 1 (collectMap: Collect FeedItems into map keyed by item name)
         * 1 (doOnNext: Log map size)
         * 1 (map: Transform feedMap into a FeedsBag)
         * 1 (subscribe: Terminal operation that logs feedsBag)
         */
        feedService
                .getFeeds()
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
