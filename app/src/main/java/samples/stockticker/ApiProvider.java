package samples.stockticker;

import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by vedant on 31/10/17.
 */

public class ApiProvider {

    private static final String BASE_URL = "https://www.alphavantage.co/query/";

    public static Api createApi() {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClientBuilder.addInterceptor(logging);
        }

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .baseUrl(BASE_URL)
                .client(okHttpClientBuilder.build())
                .build();
        return retrofit.create(Api.class);
    }
}
