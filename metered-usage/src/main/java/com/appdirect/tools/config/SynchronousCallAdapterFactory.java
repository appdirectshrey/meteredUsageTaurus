package com.appdirect.tools.config;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.CallAdapter.Factory;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

public class SynchronousCallAdapterFactory extends Factory {

  public static Factory create() {
    return new SynchronousCallAdapterFactory();
  }

  @Override
  public CallAdapter<Object, Object> get(final Type returnType, Annotation[] annotations,
                                         Retrofit retrofit) {

    if (Factory.getRawType(returnType) == Call.class) {
      return null;
    }

    return new CallAdapter<Object, Object>() {
      @Override
      public Type responseType() {
        return returnType;
      }

      @Override
      public Object adapt(Call<Object> call) {
        Response resp;
        try {
          resp = call.execute();

        } catch (Exception e) {
          e.printStackTrace();
          throw new RuntimeException(e); // do something better
        }

        if (resp.isSuccessful()) {
          return resp.body();
        } else {
          throw new RuntimeException("Request failure");
        }
      }
    };
  }
}