package com.appdirect.tools.data;

import com.appdirect.tools.dto.UsageItemDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;


@Service
@Slf4j
public class PopulateUsageItem {

  @Autowired
  PopulateAttributeItem populateAttributeItemDto;

  List<String> priceUnitNames = new ArrayList<>(Arrays.asList("COMPUTER", "USER", "DEVICE"));
  List<String> customUnitNames = new ArrayList<>(Arrays
      .asList("9MG5B7V4UUU2WPAV_OCTOBER", "YPA9G6CYTXQSCQGJ_OCTOBER", "DJEKB32FV4HNZJZY_OCTOBER",
          "CRW7FVXN6SAFJCTU_OCTOBER"));
  List<String> currencyNames = new ArrayList<>(Arrays.asList("EUR", "USD"));



  OffsetDateTime odt1 = OffsetDateTime.parse( "2019-11-30T00:00Z");

  ZonedDateTime zd2= ZonedDateTime.parse("2019-11-30T05:30+05:30[Asia/Kolkata]");
  OffsetDateTime odt2 = zd2.withZoneSameInstant(ZoneId.of("Asia/Kolkata")).toOffsetDateTime();

  ZonedDateTime zd3= ZonedDateTime.parse("2019-11-30T00:00Z[GMT]");
  OffsetDateTime odt3 = zd3.withZoneSameInstant(ZoneId.of("GMT")).toOffsetDateTime();


  ZonedDateTime zd4= ZonedDateTime.parse("2019-11-30T01:00+01:00[CET]");
  OffsetDateTime odt4 = zd4.withZoneSameInstant(ZoneId.of("CET")).toOffsetDateTime();

  //  2019-11-30T00:00Z  2019-11-30T05:30+05:30  2019-11-30T00:00Z   2019-11-30T01:00+01:00

 List<OffsetDateTime> offsetDateTimeList = new ArrayList<OffsetDateTime>(Arrays.asList(odt1,odt2,odt3,odt4));
  public List<UsageItemDto> getUsageItemDto(String usageCount, int count) {
    List<UsageItemDto> usageItemDtoList = new ArrayList<>();
    //log.info("DATE {}  {} {} {}",odt1,odt2,odt3,odt4);

    for (int i = 0; i < Integer.parseInt(usageCount); i++) {
      usageItemDtoList.add(UsageItemDto.builder()
          .eventId(UUID.randomUUID().toString())
          .currency(currencyNames.get(1))
          .eventDate(OffsetDateTime.now())
          .customUnit(customUnitNames.get(new Random().nextInt(4)))
          .pricingUnit(null)
          .description(
              priceUnitNames.get(new Random().nextInt(3)) + "- AmazonSNS - DataTransfer-In-Bytes")
          .quantity(Double.toString(new Random().nextDouble()))
          .unitPrice(Double.toString(new Random().nextDouble()))
          .attributes(null)
          .build());
    }
    log.info("************UsageItemDtoList Created*************"+ String.valueOf(usageItemDtoList.size()));
    return usageItemDtoList;
  }
}
