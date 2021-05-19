package com.example.moviestreaming.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviestreaming.Model.ActorModel;
import com.example.moviestreaming.R;
import com.example.moviestreaming.Utils.Constant;
import com.example.moviestreaming.databinding.ItemsActorsBinding;

import java.util.List;

public class ActorsAdapter extends RecyclerView.Adapter<ActorsAdapter.ViewHolder> {

    Context context;
    List<ActorModel> list;
    public ActorsAdapter(Context context, List<ActorModel> list){
        this.context=context;
        this.list=list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemsActorsBinding binding= DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.items_actors,parent,false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final ActorModel model=list.get(position);

       holder.binding.tvName.setText(model.getName());

        Glide.with(context).load(Constant.MAIN_URL+ Constant.CONTENT+"actors/"+list.get(position).getImage_name())
                .into(holder.binding.imgActor);

    }

    @Override
    public int getItemCount() {

        return list.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemsActorsBinding binding;
        public ViewHolder(@NonNull ItemsActorsBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
