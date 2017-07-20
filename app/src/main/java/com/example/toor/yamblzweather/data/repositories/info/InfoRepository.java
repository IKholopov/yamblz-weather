package com.example.toor.yamblzweather.data.repositories.info;

import io.reactivex.Single;

public interface InfoRepository {

    Single<String> getAppVersion();
}
