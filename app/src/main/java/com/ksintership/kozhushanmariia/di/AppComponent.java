package com.ksintership.kozhushanmariia.di;

import com.ksintership.kozhushanmariia.fragments.SearchListFragment;
import com.ksintership.kozhushanmariia.fragments.TrackDetailFragment;
import com.ksintership.kozhushanmariia.utils.PreferencesManager;
import com.ksintership.kozhushanmariia.viewmodels.HistorySearchViewModel;
import com.ksintership.kozhushanmariia.viewmodels.SearchListViewModel;
import com.ksintership.kozhushanmariia.viewmodels.TrackDetailViewModel;
import com.ksintership.kozhushanmariia.views.AudioPlayerView;
import com.ksintership.kozhushanmariia.views.FloatingAudioPlayerView;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {
        AppModule.class,
        RepositoryModule.class,
        RestModule.class})
@Singleton
public interface AppComponent {
    //Fragments
    void inject(SearchListFragment searchListFragment);

    void inject(TrackDetailFragment trackDetailFragment);

    //ViewModels
    void inject(SearchListViewModel searchListViewModel);

    void inject(TrackDetailViewModel trackDetailViewModel);

    void inject(HistorySearchViewModel historySearchViewModel);


    void inject(PreferencesManager preferencesManager);

    void inject(AudioPlayerView audioPlayerView);

    void inject(FloatingAudioPlayerView floatingAudioPlayerView);
}
