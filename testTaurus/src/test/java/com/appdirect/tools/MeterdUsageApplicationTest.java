package com.appdirect.tools;

import com.appdirect.billing.platform.retrofit.BaseServiceProperties;
import com.appdirect.billing.platform.retrofit.interceptor.ExceptionTranslatorInterceptor;
import com.appdirect.tools.config.ServicesProperties;
import com.appdirect.tools.config.SynchronousCallAdapterFactory;
import com.appdirect.tools.dto.MeteredUsageRequestAcceptedDto;
import com.appdirect.tools.dto.MeteredUsageRequestDto;
import com.appdirect.tools.service.ReadInput;
import com.appdirect.tools.service.RepeatApiCallScheduleExecutorImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;
import se.akerfeldt.okhttp.signpost.SigningInterceptor;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = {MeteredUsageTaurusApplication.class})
@ContextConfiguration(classes = {MeteredUsageTaurusApplication.class})
@SpringBootTest
@Slf4j
@DirtiesContext
public class MeterdUsageApplicationTest {

//    @Value("${path.propertyfilepath}")
//    private String path = "/testTaurus/src/test/resources/Account_Id.csv";
    private String path = "/src/test/resources/Account_Id.csv";

//    @Autowired
    private MeteredUsageApi meteredUsageApi;

    @Autowired
    private RepeatApiCallScheduleExecutorImpl scheduleExecutorRepeatApiCall;

    @Autowired
    private ReadInput readInput;

    private static final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    private static HttpLoggingInterceptor logging = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BASIC);
    private static final String CLIENT = "meteredusage-client";

//    @Autowired
//    ServicesProperties.AppdirectServiceProperties mpProperties;

    public <S> S createService(Class<S> serviceClass, String client) {

        System.out.println("0.75");
//        String baseUrl = mpProperties.getClients().get(client).getBaseUrl();
//        String baseUrl = "https://od-f3z3swnw6cancom.od22.appdirectondemand.com/";
        String baseUrl = "https://testcancom.appdirect.com/";
        System.out.println("baseUrl" + baseUrl);
//        String consumerKey = mpProperties.getClients().get(client).getSecurity().getOauth1().getConsumerKey();
//        String consumerKey = "0MoTYU4IRx";
        String consumerKey = "7TokO41CxC";
        System.out.println("consumerKey" + consumerKey);
//        String consumerSecret = mpProperties.getClients().get(client).getSecurity().getOauth1().getConsumerSecret();
//        String consumerSecret = "HfgFmnzEwwmZKaCMarwu";
        String consumerSecret = "C82Jzg8NlsVjhlmazEUU";
        System.out.println("consumerSecret" + consumerSecret);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .addCallAdapterFactory(SynchronousCallAdapterFactory.create());

//        BaseServiceProperties baseServiceProperties = mpProperties.getDefaultConfiguration();
        httpClient.addInterceptor(new ExceptionTranslatorInterceptor());

        OkHttpOAuthConsumer consumer = new OkHttpOAuthConsumer(consumerKey, consumerSecret);

        httpClient.interceptors().add(new SigningInterceptor(consumer));
//        httpClient.connectTimeout(baseServiceProperties.getConnectTimeout(), TimeUnit.SECONDS);
//        httpClient.readTimeout(baseServiceProperties.getReadTimeout(), TimeUnit.SECONDS);
//        httpClient.writeTimeout(baseServiceProperties.getWriteTimeout(), TimeUnit.SECONDS);
        // Enable zipking tracing

    /*HttpTracing httpTracing = HttpTracing.create(tracing).clientOf(client);
    httpClient.dispatcher(new Dispatcher(
        httpTracing.tracing().currentTraceContext()
            .executorService(new Dispatcher().executorService())
    )).addNetworkInterceptor(TracingInterceptor.create(httpTracing));
*/
        httpClient.addInterceptor(logging);
        builder.client(httpClient.build());

        Retrofit retrofit = builder.build();
        return retrofit.create(serviceClass);
    }

    /*@Test
    public void testCase() {
        System.out.println("Reached!");
    }*/

    @Test
    public void testCase1() {
        System.out.println("Reached! path: "+System.getProperty("user.dir")+path);

        MeteredUsageApi meteredUsageApi = this.createService(MeteredUsageApi.class, CLIENT);

        Map<String, String> accountIdMap = readInput.processInputFile(System.getProperty("user.dir")+path);

        accountIdMap.forEach((k, v) -> System.out.println((k + ":" + v)));

        scheduleExecutorRepeatApiCall.scheduledApiCallAtFixedRate(accountIdMap, meteredUsageApi);

    }

}
