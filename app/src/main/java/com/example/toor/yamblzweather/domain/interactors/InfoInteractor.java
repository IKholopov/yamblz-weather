package com.example.toor.yamblzweather.domain.interactors;

import com.example.toor.yamblzweather.data.repositories.info.InfoRepository;

import io.reactivex.Single;

public class InfoInteractor {

    private InfoRepository repository;

    public InfoInteractor(InfoRepository repository) {
        this.repository = repository;
    }

    public Single<String> getAppVersion() {
        return repository.getAppVersion();
    }
}
