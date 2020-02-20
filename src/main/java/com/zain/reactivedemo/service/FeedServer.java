package com.zain.reactivedemo.service;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Date;

/**
 * Service that returns Feed
 */
@Component
public class FeedServer {

    public Flux<FeedItem> feeds() {
        return Flux.fromStream(() ->
                Arrays.stream(new FeedItem[]{
                        new FeedItem("f1", "This is the feed 1", "John Doe", new Date()),
                        new FeedItem("f2", "This is the feed 2", "Jane Doe", new Date()),
                        new FeedItem("f3", "This is the feed 3", "Jack Doe", new Date()),
                        new FeedItem("f4", "This is the feed 4", "John Doe", new Date()),
                        new FeedItem("f5", "This is the feed 5", "Jane Doe", new Date())
                })
        );
    }

    public Mono<FeedItem> feed(String title) {
        return Mono.fromCallable(() -> new FeedItem(title));
    }


}
