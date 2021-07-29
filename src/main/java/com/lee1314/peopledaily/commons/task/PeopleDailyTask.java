package com.lee1314.peopledaily.commons.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lee1314.peopledaily.entity.SysConfig;
import com.lee1314.peopledaily.reptile.PeopleDailyReptile;
import com.lee1314.peopledaily.service.SysConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author leili
 */
@Slf4j
@Component
public class PeopleDailyTask {

    private final PeopleDailyReptile peopleDailyReptile;
    private final SysConfigService sysConfigService;

    @Autowired
    public PeopleDailyTask(PeopleDailyReptile peopleDailyReptile, SysConfigService sysConfigService) {
        this.peopleDailyReptile = peopleDailyReptile;
        this.sysConfigService = sysConfigService;
    }


    @Scheduled(cron = "00 00 00 * * ?")
    public void excute() {
        ExecutorService executors = null;
        try {
            SysConfig config = sysConfigService.findByBusinessAndCode("peopledaily", "paramvalue");
            JSONArray arrays = JSON.parseArray(config.getContent());
            List<String> seminarIds = arrays.stream().map(obj -> ((JSONObject) obj).getString("id"))
                    .collect(Collectors.toList());
            executors = new ThreadPoolExecutor(seminarIds.size(), seminarIds.size(),
                    60L, TimeUnit.SECONDS,
                    new SynchronousQueue<>(), r -> new Thread(r, "CollectThread"));
            for (String seminarId : seminarIds) {
                executors.execute(() -> peopleDailyReptile.queryLists(seminarId));
            }
        } finally {
            if (executors != null) {
                executors.shutdown();
            }
        }
    }
}
