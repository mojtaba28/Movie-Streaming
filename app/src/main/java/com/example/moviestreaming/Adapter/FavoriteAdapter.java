package com.example.moviestreaming.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviestreaming.Activity.FavoriteActivity;
import com.example.moviestreaming.Activity.MovieDetailActivity;
import com.example.moviestreaming.R;
import com.example.moviestreaming.RoomDB.Favorites;
import com.example.moviestreaming.Utils.Constant;
import com.example.moviestreaming.databinding.ItemsFavMoviesBinding;
import com.example.moviestreaming.viewmodel.FavoriteViewModel;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    Context context;
    List<Favorites> list;
    private final int limit = 10;
    boolean isLimited;
    FavoriteClick favoriteClick;
    public FavoriteAdapter(Context context, List<Favorites> list, boolean isLimited,FavoriteClick favoriteClick){
        this.context=context;
        this.list=list;
        this.isLimited=isLimited;
        this.favoriteClick=favoriteClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemsFavMoviesBinding binding= DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.items_fav_movies,parent,false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final Favorites favorites=list.get(position);

        holder.binding.movieName.setText(favorites.name);
        holder.binding.movieGenre.setText(favorites.genre);
        holder.binding.tvRate.setText(favorites.rate);

        Glide.with(context).load(Constant.MAIN_URL+ Constant.CONTENT+"movies/"+list.get(position).image_name)
                .into(holder.binding.imgMovie);

        PushDownAnim.setPushDownAnimTo(holder.binding.imgDelete)
                .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                favoriteClick.onDeleteClick(favorites,position);

            }
        });

        PushDownAnim.setPushDownAnimTo(holder.itemView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favoriteClick.onItemClick(favorites);
            }
        });

    }

    public boolean notifyChange(int position){
        list.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, list.size());
        if (list.size()==0){
            return true;

        } else return false;
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
        ItemsFavMoviesBinding binding;
        public ViewHolder(@NonNull ItemsFavMoviesBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }

    public interface FavoriteClick{

        void onDeleteClick(Favorites favorites,int position);
        void onItemClick(Favorites favorites);


    }
}
