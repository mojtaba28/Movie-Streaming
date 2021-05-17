package com.example.moviestreaming.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.moviestreaming.Activity.MovieDetailActivity;
import com.example.moviestreaming.Model.GenreModel;
import com.example.moviestreaming.Model.MovieModel;
import com.example.moviestreaming.R;
import com.example.moviestreaming.Utils.Constant;
import com.example.moviestreaming.databinding.ItemsMoviesBinding;
import com.thekhaeng.pushdownanim.PushDownAnim;


import java.util.List;

public class IMDBTopAdapter extends RecyclerView.Adapter<IMDBTopAdapter.ViewHolder> {

    Context context;
    List<MovieModel> list;
    private final int limit = 10;
    boolean isLimited;
    IMDBItemOnClick imdbItemOnClick;
    public IMDBTopAdapter(Context context, List<MovieModel> list,boolean isLimited,IMDBItemOnClick imdbItemOnClick){
        this.context=context;
        this.list=list;
        this.isLimited=isLimited;
        this.imdbItemOnClick=imdbItemOnClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemsMoviesBinding binding= DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.items_movies,parent,false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final MovieModel model=list.get(position);


                holder.binding.tvRank.setVisibility(View.VISIBLE);

                holder.binding.movieName.setText(model.getName());
                holder.binding.movieGenre.setText(model.getGenre());
                holder.binding.tvRate.setText(model.getRate());
                holder.binding.tvRank.setText(model.getImdb_rank());

                Glide.with(context).load(Constant.MAIN_URL+ Constant.CONTENT+"movies/"+list.get(position).getImage_name())
                        //.transform(new BlurTransformation(context ))
                        .into(holder.binding.imgMovie);


        PushDownAnim.setPushDownAnimTo(holder.itemView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imdbItemOnClick.onItemClick(model);
//                Intent intent=new Intent(context, MovieDetailActivity.class);
//                intent.putExtra("type","top_imdb");
//                intent.putExtra("id",model.getId());
//                intent.putExtra("name",model.getName());
//                intent.putExtra("genre",model.getGenre());
//                intent.putExtra("rate",model.getRate());
//                intent.putExtra("published",model.getPublished());
//                intent.putExtra("time",model.getTime());
//                intent.putExtra("director",model.getDirector());
//                intent.putExtra("budget",model.getBudget());
//                intent.putExtra("box_office",model.getBox_office());
//                intent.putExtra("image_name",model.getImage_name());
//                intent.putExtra("movie_poster",model.getMovie_poster());
//                intent.putExtra("movie_preview",model.getMovie_preview());
//                intent.putExtra("description",model.getDescription());
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(list.size() > limit && isLimited){
            return limit;
        }
        else
        {
            return list.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemsMoviesBinding binding;
        public ViewHolder(@NonNull ItemsMoviesBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
            
        }
    }

    public interface IMDBItemOnClick{

        void onItemClick(MovieModel model);


    }
}
