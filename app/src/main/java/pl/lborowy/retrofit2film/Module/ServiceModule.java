package pl.lborowy.retrofit2film.Module;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by RENT on 2017-07-07.
 */
@Module
public class ServiceModule {

    private String myBaseUrl;

    public ServiceModule(String url) {
        this.myBaseUrl = url;
    }

    @Provides
    @Singleton
    GsonConverterFactory providesGson() {
        return GsonConverterFactory.create();
    }

    @Provides
    @Singleton
    RxJava2CallAdapterFactory provideAdapterFactory() {
        return RxJava2CallAdapterFactory.create();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(GsonConverterFactory gson, RxJava2CallAdapterFactory rxJava2CallAdapterFactory) {
        return new Retrofit.Builder()
                .addConverterFactory(gson)
                .addCallAdapterFactory(rxJava2CallAdapterFactory)
                .baseUrl(myBaseUrl)
                .build();
    }
}
