package com.java.test.junior.tasks;

import com.java.test.junior.mapper.product.ProductMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@AllArgsConstructor
public class DatabaseCleaner {

    private static final Logger log = LoggerFactory.getLogger(DatabaseCleaner.class);
    private final ProductMapper productMapper;

    @Scheduled(fixedRateString = "${cleaner.fixedRate.in.milliseconds:86400000}")
    public void deleteNullLikedRecords(){
        int deletedRowsCount = productMapper.deleteNullLikes();
        log.info("Deleted {} records with null liked value @ {}", deletedRowsCount, Instant.now());
    }
}
