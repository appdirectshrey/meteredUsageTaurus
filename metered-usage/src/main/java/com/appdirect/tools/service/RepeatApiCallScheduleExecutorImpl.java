package com.appdirect.tools.service;

import com.appdirect.tools.MeteredUsageApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

@Service
@Slf4j
public class RepeatApiCallScheduleExecutorImpl {

  @Autowired
  MeteredUsageHttpClientImpl meteredUsageHttpClientImpl;
  private Map<String, String> accountIds;
  static int count =0;

  public void scheduledApiCallAtFixedRate(
          Map<String, String> accountIdMap, MeteredUsageApi meteredUsageApi) { //throws InterruptedException {


//    ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
    Iterator<Entry<String, String>> iterator = accountIdMap.entrySet().iterator();

    /*ScheduledFuture<?> scheduledFuture = ses
        .scheduleAtFixedRate(new ApiCall(iterator, ses), 0, 5, TimeUnit.SECONDS);*/

    while (iterator.hasNext()) {

      Entry entry = iterator.next();
      log.info(
              "================Running...meteredUsageApiCall - AccountId :{}  and  UsageListCount : {}================== ",
              entry.getKey(), entry.getValue());
      meteredUsageHttpClientImpl
              .callApi(entry.getKey().toString(), entry.getValue().toString(),count++, meteredUsageApi);

    }
      /*else {
        if(!executor.awaitTermination(60, TimeUnit.SECONDS)){
          executor.shutdown();
        }
      }*/

  }

  /*class ApiCall implements Runnable {

    private Iterator<Entry<String, String>> itr;
    private ExecutorService executor;

    ApiCall(Iterator<Entry<String, String>> itr, ExecutorService executor) {
      this.itr = itr;
      this.executor = executor;
    }

    @Override
    public void run() {
      if (itr.hasNext()) {

        Map.Entry entry = itr.next();
        log.info(
            "MAIN================Running...meteredUsageApiCall - AccountId :{}  and  UsageListCount : {}================== ",
            entry.getKey(), entry.getValue());
        meteredUsageHttpClientImpl
            .callApi(entry.getKey().toString(), entry.getValue().toString(),count++);

      }
      *//*else {
        if(!executor.awaitTermination(60, TimeUnit.SECONDS)){
          executor.shutdown();
        }
      }*//*
    }
  }*/
}
