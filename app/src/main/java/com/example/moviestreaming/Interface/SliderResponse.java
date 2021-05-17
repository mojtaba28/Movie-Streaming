package com.example.moviestreaming.Interface;

import com.example.moviestreaming.Model.GenreModel;
import com.example.moviestreaming.Model.SliderModel;

import java.util.List;

public interface SliderResponse {

    void getSliderList(List<SliderModel> list,int number);
}
