package com.appdirect.tools.service;

import com.appdirect.tools.data.PopulateUsageItem;
import com.appdirect.tools.dto.MeteredUsageItemDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ReadInput {

  @Autowired
  private PopulateUsageItem populateUsageItemDto;

  public Map<String, String> processInputFile(String inputFilePath) {
    List<MeteredUsageItemDto> inputList = new ArrayList<MeteredUsageItemDto>();
    Map<String, String> accountIdMap = new HashMap<>();
    try {
      File inputF = new File(inputFilePath);
      InputStream inputFS = new FileInputStream(inputF);
      BufferedReader br = new BufferedReader(new InputStreamReader(inputFS));
      // skip the header of the csv
      accountIdMap = br.lines().map(i -> i.split(","))
          .collect(Collectors.toMap(a -> a[0], a -> a[1]));
      br.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return accountIdMap;
  }
}
