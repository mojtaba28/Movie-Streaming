package com.example.moviestreaming.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.moviestreaming.Model.CommentModel;
import com.example.moviestreaming.R;
import com.example.moviestreaming.Utils.Constant;
import com.example.moviestreaming.databinding.ItemsCommentBinding;
import com.example.moviestreaming.databinding.ItemsDetailCommentBinding;

import java.util.List;

public class CommentDetailAdapter extends RecyclerView.Adapter<CommentDetailAdapter.ViewHolder> {

    Context context;
    List<CommentModel> list;

    public CommentDetailAdapter(Context context, List<CommentModel> list){
        this.context=context;
        this.list=list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemsDetailCommentBinding binding= DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.items_detail_comment,parent,false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final CommentModel model=list.get(position);

       holder.binding.tvName.setText(model.getName());
       holder.binding.tvDate.setText(model.getDate());
       holder.binding.tvComment.setText(model.getComment());
        if (!model.getUserImage().isEmpty()){
            Glide.with(context).load(Constant.MAIN_URL+model.getUserImage())
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(holder.binding.imgProfile);
        }
       if (model.getSpoil()==1){

           holder.binding.tvSpoil.setVisibility(View.VISIBLE);
       }else {
           holder.binding.tvSpoil.setVisibility(View.GONE);
       }

    }

    @Override
    public int getItemCount() {

        return list.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemsDetailCommentBinding binding;
        public ViewHolder(@NonNull ItemsDetailCommentBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
