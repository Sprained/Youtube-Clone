package com.men.youtube_clone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.men.youtube_clone.R;
import com.men.youtube_clone.model.Item;
import com.men.youtube_clone.model.Video;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

public class AdapterVideo extends RecyclerView.Adapter<AdapterVideo.MyViewHolder> {

    private List<Item> videos = new ArrayList<>();
    private Context context;

    public AdapterVideo(List<Item> videos, Context context) {
        this.videos = videos;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_video, parent, false);
        return new AdapterVideo.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Item video = videos.get(position);
        holder.title.setText(video.snippet.title);

        String url = video.snippet.thumbnails.high.url;
        Picasso.get().load(url).into(holder.capa);
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView descricao;
        TextView date;
        ImageView capa;

        public MyViewHolder(View itemView){
            super(itemView);

            title = itemView.findViewById(R.id.txtTitle);
            capa = itemView.findViewById(R.id.imgThumbnail);
        }
    }

}
