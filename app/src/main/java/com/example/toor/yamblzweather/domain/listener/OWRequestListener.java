package com.example.toor.yamblzweather.domain.listener;

import com.example.toor.yamblzweather.data.model.OWResponse;

public interface OWRequestListener<T> {

    void onResponse(OWResponse<T> response);

    void onFailure(Throwable t);
}
