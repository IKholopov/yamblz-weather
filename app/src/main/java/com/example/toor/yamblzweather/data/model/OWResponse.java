package com.example.toor.myopenweather.model;

public class OWResponse<T> {
    private final T body;

    public OWResponse(T body) {
        this.body = body;
    }

    public T body() {
        return body;
    }
}
