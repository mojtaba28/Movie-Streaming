package com.example.moviestreaming.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.moviestreaming.Model.MovieModel;
import com.example.moviestreaming.Model.SliderModel;
import com.example.moviestreaming.R;
import com.example.moviestreaming.Utils.Constant;
import com.example.moviestreaming.databinding.ItemsSliderBinding;

import java.util.List;

public class SliderAdapter extends PagerAdapter {

    Context context;
    List<SliderModel> list;
    SliderItemOnClick sliderItemOnClick;

    public SliderAdapter(Context context, List<SliderModel> list,SliderItemOnClick sliderItemOnClick) {
        this.context = context;
        this.list = list;
        this.sliderItemOnClick=sliderItemOnClick;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ItemsSliderBinding binding= DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.items_slider,container,false);

        SliderModel model=list.get(position);

        binding.imgSlider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sliderItemOnClick.onItemClick(model);
            }
        });
        binding.nameSlider.setText(model.getName());
        Glide.with(context).load(Constant.MAIN_URL+ Constant.CONTENT+"slider/"+model.getSlider_poster()).into(binding.imgSlider);

        container.addView(binding.getRoot());
        return binding.getRoot();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);

    }

    public interface SliderItemOnClick{

        void onItemClick(SliderModel model);


    }


}
