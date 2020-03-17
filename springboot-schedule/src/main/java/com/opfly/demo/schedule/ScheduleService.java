package com.opfly.demo.schedule;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    @Scheduled(fixedRate = 1000)
    public void reportCurrentTime() {
    	System.out.println("fixedRate 当前时间：" + dateFormat.format(new Date()));
    }
    
    @Scheduled(cron="0/3 * * * * ?")
    public void reportCurrentTime2() {
    	System.out.println("Cron表达式     当前时间：" + dateFormat.format(new Date()));
    }
}
