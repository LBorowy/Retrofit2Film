package pl.lborowy.retrofit2film.Component;

import javax.inject.Singleton;

import dagger.Component;
import pl.lborowy.retrofit2film.MainActivity;
import pl.lborowy.retrofit2film.Module.ServiceModule;

/**
 * Created by RENT on 2017-07-07.
 */

@Singleton
@Component(modules = {ServiceModule.class})
public interface ServiceComponent {
    void inject(MainActivity activity);
}
