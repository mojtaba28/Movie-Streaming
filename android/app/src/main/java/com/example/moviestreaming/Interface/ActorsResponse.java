package com.example.moviestreaming.Interface;

import com.example.moviestreaming.Model.Actors.ActorItems;

import java.util.List;

public interface ActorsResponse {

    void getActors(List<ActorItems> list);
}
