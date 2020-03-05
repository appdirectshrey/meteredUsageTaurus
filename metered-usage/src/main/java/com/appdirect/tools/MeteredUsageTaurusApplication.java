package com.appdirect.tools;

import com.appdirect.billing.platform.retrofit.BaseServiceProperties;
import com.appdirect.billing.platform.retrofit.interceptor.ExceptionTranslatorInterceptor;
import com.appdirect.tools.config.ServicesProperties;
import com.appdirect.tools.config.SynchronousCallAdapterFactory;
import com.appdirect.tools.service.ReadInput;
import com.appdirect.tools.service.RepeatApiCallScheduleExecutorImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;
import se.akerfeldt.okhttp.signpost.SigningInterceptor;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class MeteredUsageTaurusApplication implements CommandLineRunner {

	private RepeatApiCallScheduleExecutorImpl scheduleExecutorRepeatApiCall;
	private ReadInput readInput;

	private static final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
	private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
	private static HttpLoggingInterceptor logging = new HttpLoggingInterceptor()
			.setLevel(HttpLoggingInterceptor.Level.BASIC);
	private static final String CLIENT = "meteredusage-client";

	@Value("${path.propertyfilepath}")
	private String path;

	@Autowired
	public MeteredUsageTaurusApplication(ReadInput readInput,
								  RepeatApiCallScheduleExecutorImpl scheduleExecutorRepeatApiCall) {
		this.readInput = readInput;
		this.scheduleExecutorRepeatApiCall = scheduleExecutorRepeatApiCall;
	}

	@Autowired
	ServicesProperties.AppdirectServiceProperties mpProperties;

	public <S> S createService(Class<S> serviceClass, String client) {

		String baseUrl = mpProperties.getClients().get(client).getBaseUrl();
		System.out.println("baseUrl" + baseUrl);
		String consumerKey = mpProperties.getClients().get(client).getSecurity().getOauth1()
				.getConsumerKey();
		System.out.println("consumerKey" + consumerKey);
		String consumerSecret = mpProperties.getClients().get(client).getSecurity().getOauth1()
				.getConsumerSecret();
		System.out.println("consumerSecret" + consumerSecret);

		Retrofit.Builder builder = new Retrofit.Builder()
				.baseUrl(baseUrl)
				.addConverterFactory(JacksonConverterFactory.create(objectMapper))
				.addCallAdapterFactory(SynchronousCallAdapterFactory.create());

		BaseServiceProperties baseServiceProperties = mpProperties.getDefaultConfiguration();
		httpClient.addInterceptor(new ExceptionTranslatorInterceptor());

		OkHttpOAuthConsumer consumer = new OkHttpOAuthConsumer(consumerKey, consumerSecret);

		httpClient.interceptors().add(new SigningInterceptor(consumer));
		httpClient.connectTimeout(baseServiceProperties.getConnectTimeout(), TimeUnit.SECONDS);
		httpClient.readTimeout(baseServiceProperties.getReadTimeout(), TimeUnit.SECONDS);
		httpClient.writeTimeout(baseServiceProperties.getWriteTimeout(), TimeUnit.SECONDS);
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

	public static void main(String[] args) {

//		SpringApplication.run(MeteredUsageTaurusApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		/*System.out.println("Path: "+path);

		MeteredUsageApi meteredUsageApi = this.createService(MeteredUsageApi.class, CLIENT);

    	Map<String, String> accountIdMap = readInput.processInputFile(System.getProperty("user.dir")+path);
    	accountIdMap.forEach((k, v) -> System.out.println((k + ":" + v)));
    	scheduleExecutorRepeatApiCall.scheduledApiCallAtFixedRate(accountIdMap, meteredUsageApi);*/
	}
}
