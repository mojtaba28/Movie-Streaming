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
import com.example.moviestreaming.Activity.MovieActivity;
import com.example.moviestreaming.Model.GenreModel;
import com.example.moviestreaming.R;
import com.example.moviestreaming.RoomDB.Favorites;
import com.example.moviestreaming.Utils.Constant;
import com.example.moviestreaming.databinding.ItemsGenreBinding;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.List;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.ViewHolder> {

    Context context;
    List<GenreModel> list;
    GenreClick genreClick;

    public GenreAdapter(Context context, List<GenreModel> list,GenreClick genreClick){
        this.context=context;
        this.list=list;
        this.genreClick=genreClick;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemsGenreBinding binding= DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.items_genre,parent,false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final GenreModel model=list.get(position);

        holder.binding.tvGenre.setText(model.getName());

        Glide.with(context).load(Constant.MAIN_URL+ Constant.CONTENT+"genre/"+list.get(position).getImage_name())
                .into(holder.binding.imgGenre);

        PushDownAnim.setPushDownAnimTo(holder.itemView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genreClick.onItemClick(model);

//                Intent intent=new Intent(context, MovieActivity.class);
//                intent.putExtra("type","genre");
//                intent.putExtra("name",model.getName());
//                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {

        return list.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemsGenreBinding binding;
        public ViewHolder(@NonNull ItemsGenreBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }

    public interface GenreClick{

        void onItemClick(GenreModel genreModel);


    }
}
