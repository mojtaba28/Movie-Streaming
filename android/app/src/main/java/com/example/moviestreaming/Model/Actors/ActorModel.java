package com.example.moviestreaming.Model.Actors;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ActorModel {

    @SerializedName("actors")
    private List<ActorItems> actorList;

    public List<ActorItems> getActorList() {
        return actorList;
    }

    public void setActorList(List<ActorItems> actorList) {
        this.actorList = actorList;
    }
}
