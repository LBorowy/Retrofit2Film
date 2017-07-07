package pl.lborowy.retrofit2film;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import pl.lborowy.retrofit2film.Component.DaggerServiceComponent;
import pl.lborowy.retrofit2film.Component.ServiceComponent;
import pl.lborowy.retrofit2film.Model.Film;
import pl.lborowy.retrofit2film.Module.ServiceModule;
import pl.lborowy.retrofit2film.Service.FilmApi;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv_showFilm)
    TextView showFilm;

    String url = "http://www.json-generator.com/api/json/";

    ServiceComponent serviceComponent;


    private List<Film> filmsList = new ArrayList<>();

    private FilmApi.ServiceAPI serviceAPI;

    @Inject
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        injectDagger();
    }

    private void injectDagger() {
        serviceComponent = DaggerServiceComponent.builder()
                .serviceModule(new ServiceModule(url))
                .build();

        serviceComponent.inject(this);

        serviceAPI = retrofit.create(FilmApi.ServiceAPI.class);
    }

    private void fetchFilm2() {
        serviceAPI.fetchFilm()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.Observer<List<Film>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull List<Film> films) {
                        filmsList.addAll(films);
                        showFilm.setText(filmsList.get(0).getStory());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @OnClick(R.id.button_show)
    public void showFilm() {
        fetchFilm2();

    }
    
}
