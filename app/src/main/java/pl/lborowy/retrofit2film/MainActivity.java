package pl.lborowy.retrofit2film;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
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

//    @BindView(R.id.tv_showFilm)
//    TextView showFilm;

    @BindView(R.id.tv_showFilm)
    ListView showFilm;

    String url = "http://www.json-generator.com/api/json/";

    ServiceComponent serviceComponent;


    private List<Film> filmsList = new ArrayList<>();

    private FilmApi.ServiceAPI serviceAPI;

    private MovieAdapter movieAdapter;

    @Inject
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        injectDagger();
        createAdapter();
    }

    private void injectDagger() {
        serviceComponent = DaggerServiceComponent.builder()
                .serviceModule(new ServiceModule(url))
                .build();

        serviceComponent.inject(this);

        serviceAPI = retrofit.create(FilmApi.ServiceAPI.class);
    }

    private void createAdapter(){
        movieAdapter = new MovieAdapter(getApplicationContext(), R.layout.new_layout, filmsList);
        showFilm.setAdapter(movieAdapter);
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
//                        showFilm.setText(filmsList.get(0).getStory());
                        movieAdapter.notifyDataSetChanged();
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



    public class MovieAdapter extends ArrayAdapter {

        private List<Film> movieModelList;
        private int resource;
        private LayoutInflater inflater;
        public MovieAdapter(Context context, int resource, List<Film> objects) {
            super(context, resource, objects);
            movieModelList = objects;
            this.resource = resource;
            inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;

            if(convertView == null){
                holder = new ViewHolder();
                convertView = inflater.inflate(resource, null);
                holder.ivMovieIcon = (ImageView)convertView.findViewById(R.id.ivIcon);
                holder.tvMovie = (TextView)convertView.findViewById(R.id.t1_movieName);
                holder.tvTagline = (TextView)convertView.findViewById(R.id.t1_tagLine);
                holder.tvYear = (TextView)convertView.findViewById(R.id.t1_year);
                holder.tvDuration = (TextView)convertView.findViewById(R.id.t1_duration);
                holder.tvDirector = (TextView)convertView.findViewById(R.id.t1_director);
                holder.rbMovieRating = (RatingBar)convertView.findViewById(R.id.ratingBar);
                holder.tvCast = (TextView)convertView.findViewById(R.id.t2_tvCast);
                holder.tvStory = (TextView)convertView.findViewById(R.id.t2_story);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            // Then later, when you want to display image
            final ViewHolder finalHolder = holder;

            holder.tvMovie.setText(movieModelList.get(position).getMovie());
            holder.tvTagline.setText(movieModelList.get(position).getTagline());
            holder.tvYear.setText("Year: " + movieModelList.get(position).getYear());
            holder.tvDuration.setText("Duration:" + movieModelList.get(position).getDuration());
            holder.tvDirector.setText("Director:" + movieModelList.get(position).getDirector());
            Picasso.with(getContext()).load(movieModelList.get(position).getImage()).into(holder.ivMovieIcon);
            // rating bar
            holder.rbMovieRating.setRating(movieModelList.get(position).getRating()/2);

//            StringBuffer stringBuffer = new StringBuffer();
//            for(Film.Cast cast : movieModelList.get(position).getCastList()){
//                stringBuffer.append(cast.getName() + ", ");
//            }

//            holder.tvCast.setText("Cast:" + stringBuffer);
//            holder.tvStory.setText(movieModelList.get(position).getStory());
//            return convertView;
            return convertView;
        }


        class ViewHolder{
            private ImageView ivMovieIcon;
            private TextView tvMovie;
            private TextView tvTagline;
            private TextView tvYear;
            private TextView tvDuration;
            private TextView tvDirector;
            private RatingBar rbMovieRating;
            private TextView tvCast;
            private TextView tvStory;
        }

    }
}
