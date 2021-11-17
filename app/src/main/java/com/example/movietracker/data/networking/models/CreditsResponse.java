package com.example.movietracker.data.networking.models;

import java.util.List;

public class CreditsResponse {
    private int id;
    private List<CastMember> cast;

    public CreditsResponse() {
    }

    public int getId() {
        return id;
    }

    public CreditsResponse setId(int id) {
        this.id = id;
        return this;
    }

    public List<CastMember> getCast() {
        return cast;
    }

    public void setCast(List<CastMember> cast) {
        this.cast = cast;
    }

}
