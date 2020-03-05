package com.appdirect.tools.config;

import com.appdirect.billing.platform.retrofit.BaseServiceProperties;
import com.appdirect.billing.platform.retrofit.ServiceProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class ServicesProperties {

  @Data
  @Configuration("mpApiServiceProperties")
  @ConfigurationProperties(AppdirectServiceProperties.CONFIGURATION_PROPERTY_PREFIX)
  public class AppdirectServiceProperties {

    public static final String CONFIGURATION_PROPERTY_PREFIX = "appdirect.services";

    BaseServiceProperties defaultConfiguration;
    private Map<String, ServiceProperties> clients = new HashMap<>();

    public Set<String> getServiceNames() {
      return clients.keySet();
    }
  }
}
