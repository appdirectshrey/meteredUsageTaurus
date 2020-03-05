package com.appdirect.tools.service;

import com.appdirect.tools.MeteredUsageApi;
import com.appdirect.tools.data.PopulateMeteredUSageRequest;
import com.appdirect.tools.dto.MeteredUsageRequestAcceptedDto;
import com.appdirect.tools.dto.MeteredUsageRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

/*import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.file.Files;
import java.nio.file.Paths;*/


@Service
@Slf4j
@ComponentScan
public class MeteredUsageHttpClientImpl {



  @Autowired
  PopulateMeteredUSageRequest populateMeteredUSageRequestDto;
  /*@Autowired
  private MeteredUsageApi meteredUsageApi;*/

  public MeteredUsageHttpClientImpl() {
  }

  public void callApi(String accountId, String UsageCount, int count, MeteredUsageApi meteredUsageApi) {
    try {




      MeteredUsageRequestDto meteredUsageRequestDto = populateMeteredUSageRequestDto
          .getMeteredUsageRequestDto(accountId, UsageCount,count);

      log.info("meteredUsageRequestDto: "+meteredUsageRequestDto.toString());

/*
      String requestJson = new ObjectMapper().writeValueAsString(meteredUsageRequestDto);
      Files.createFile(Paths.get("/Users/chhayasingh/Downloads/requestbig.json"));
      Files.write(Paths.get("/Users/chhayasingh/Downloads/requestbig.json"), requestJson.getBytes());
*/

      log.info("MeteredUsageRequestDto IdempotencyKey- {}  ", meteredUsageRequestDto.getIdempotencyKey());

      MeteredUsageRequestAcceptedDto meteredUsageRequestAcceptedDto =
          meteredUsageApi
              .meteredUsage(meteredUsageRequestDto);
      log.info("#########Response Recieved {}############",meteredUsageRequestAcceptedDto.toString());

    } catch (HttpClientErrorException ex) {
      log.info("error" + String.valueOf(ex.getStatusCode()));
    } catch (Exception ex) {
      log.info("error" + String.valueOf(ex.getMessage()));
    }
  }
}
