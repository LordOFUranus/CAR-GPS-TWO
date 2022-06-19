package com.sattazalyk.car_gps2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.sattazalyk.car_gps2.model.Post;
import com.squareup.picasso.Picasso;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder>{

    private ArrayList<Post> posts;

    public FeedAdapter(ArrayList<Post> posts) {
        this.posts = posts;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_row, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.tv_post_header.setText(posts.get(position).getHeader());
        holder.tv_post_text.setText(posts.get(position).getText());
        if(posts.get(position).getPhotoURL()!="") Picasso.get().load(posts.get(position).getPhotoURL()).into(holder.iv_photo_post);

    }

    @Override
    public int getItemCount() {

        return posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_post_header = itemView.findViewById(R.id.tv_post_header);
        TextView tv_post_text = itemView.findViewById(R.id.tv_post_text);
        ImageView iv_photo_post = itemView.findViewById(R.id.iv_post_image);
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
        }
    }
}
