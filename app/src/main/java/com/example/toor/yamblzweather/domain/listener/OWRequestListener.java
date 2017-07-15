package com.example.toor.myopenweather.listener;

import com.example.toor.myopenweather.model.OWResponse;

public interface OWRequestListener<T> {
    void onResponse(OWResponse<T> response);

    void onFailure(Throwable t);
}
