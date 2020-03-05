package com.appdirect.tools.data;

import com.appdirect.tools.dto.MeteredUsageItemDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PopulateMeteredUsageItem {

  @Autowired
  private PopulateUsageItem populateUsageItemDto;

  public List<MeteredUsageItemDto> getMeteredUsageItemDto(String accountId, String usageCount, int count) {
    List<MeteredUsageItemDto> meteredUsageItemDtoList = new ArrayList<>();
    meteredUsageItemDtoList.add(MeteredUsageItemDto.builder()
        .accountId(accountId)
        .usageList(populateUsageItemDto.getUsageItemDto(usageCount,count))
        .build());
    return meteredUsageItemDtoList;
  }
}
