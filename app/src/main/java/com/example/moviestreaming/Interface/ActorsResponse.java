package com.example.moviestreaming.Interface;


import com.example.moviestreaming.Model.ActorModel;

import java.util.List;

public interface ActorsResponse {

    void getActors(List<ActorModel> list);
}
