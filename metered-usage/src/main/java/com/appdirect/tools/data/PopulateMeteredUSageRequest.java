package com.appdirect.tools.data;

//import com.appdirect.tools.retrofit.dto.MeteredUsageRequestDto;

import com.appdirect.tools.dto.MeteredUsageRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PopulateMeteredUSageRequest {

  @Autowired
  PopulateMeteredUsageItem populateMeteredUsageItemDto;

  public MeteredUsageRequestDto getMeteredUsageRequestDto(String accountId, String usageCount, int count) {
    System.out.println("in PopulateMeteredUSageRequest");
    return MeteredUsageRequestDto.builder()
        .idempotencyKey(UUID.randomUUID().toString())
//        .sourceType("azure_csp_metered_usage")
        .billable(false)
        .usages(populateMeteredUsageItemDto.getMeteredUsageItemDto(accountId, usageCount,count))
        .build();
  }
}
