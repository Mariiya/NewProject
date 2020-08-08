package com.ksintership.kozhushanmariia.di;

import com.ksintership.kozhushanmariia.fragments.FragmentSettings;
import com.ksintership.kozhushanmariia.fragments.SearchListFragment;
import com.ksintership.kozhushanmariia.fragments.TrackDetailFragment;
import com.ksintership.kozhushanmariia.presenter.SearchHistoryPresenter;
import com.ksintership.kozhushanmariia.presenter.SearchListPresenter;
import com.ksintership.kozhushanmariia.presenter.TrackDetailPresenter;
import com.ksintership.kozhushanmariia.utils.PreferencesManager;
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

    void inject(FragmentSettings fragmentSettings);

    void inject(TrackDetailFragment trackDetailFragment);

    //Presenters
    void inject(SearchListPresenter searchListPresenter);

    void inject(TrackDetailPresenter trackDetailPresenter);

    void inject(SearchHistoryPresenter searchHistoryPresenter);


    void inject(PreferencesManager preferencesManager);

    void inject(AudioPlayerView audioPlayerView);

    void inject(FloatingAudioPlayerView floatingAudioPlayerView);
}
