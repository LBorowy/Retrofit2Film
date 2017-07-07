package pl.lborowy.retrofit2film.Service;



import java.util.List;

import io.reactivex.Observable;
import pl.lborowy.retrofit2film.Model.Film;
import retrofit2.http.GET;

/**
 * Created by RENT on 2017-07-07.
 */

public class FilmApi {
    public interface ServiceAPI {

        @GET("get/bSciNrOTCa?indent=2")
        Observable<List<Film>> fetchFilm();

    }
}
