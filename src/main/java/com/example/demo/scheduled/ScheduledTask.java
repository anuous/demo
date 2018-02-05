package com.example.demo.scheduled;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ScheduledTask {

    private static DateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Scheduled(cron = "0/5 * * * * ?")
    public void doTask(){
        System.out.println(sdf.format(new Date()));
    }
}
