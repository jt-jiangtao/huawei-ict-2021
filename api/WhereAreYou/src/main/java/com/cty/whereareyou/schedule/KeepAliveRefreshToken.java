package com.cty.whereareyou.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: jiangtao
 * @Date: 2021/11/15 22:16
 */
@Component
@EnableScheduling
@Slf4j
public class KeepAliveRefreshToken {
    ExecutorService service = Executors.newFixedThreadPool(5);

    @Scheduled(cron = "0/2 * * * * ?")
    public void timerToNow(){
        service.execute(() -> {
            log.info("deal success, time:" + new Date().toString());
            try {
                Thread.sleep(1000 * 5);//改成异步执行后，就算你再耗时也不会印象到后续任务的定时调度了
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

}
