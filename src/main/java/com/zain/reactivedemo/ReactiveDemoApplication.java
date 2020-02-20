package com.zain.reactivedemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class ReactiveDemoApplication implements CommandLineRunner {

    private final FeedClient feedClient;
    public ReactiveDemoApplication(FeedClient feedClient) {
        this.feedClient = feedClient;
    }

    public static void main(String[] args) {
        SpringApplication.run(ReactiveDemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("EXECUTING the application");
        feedClient.storeFeedsIntoBag();

        feedClient.storeMonosIntoBag();

    }
}
