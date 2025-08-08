package com.example.newsnow;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kwabenaberko.newsapilib.models.Article;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.NewsViewHolder>{

    List<Article> articleList;
    NewsRecyclerAdapter(List<Article> articleList){
        this.articleList = articleList;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_recycler_row,parent,false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        Article article = articleList.get(position);

        holder.titleTextView.setText(article.getTitle() != null ? article.getTitle() : "No Title");

        if (article.getSource() != null && article.getSource().getName() != null) {
            holder.sourceTextView.setText(article.getSource().getName());
        } else {
            holder.sourceTextView.setText("Unknown Source");
        }

        if (article.getUrlToImage() != null && !article.getUrlToImage().isEmpty()) {
            Picasso.get()
                    .load(article.getUrlToImage())
                    .placeholder(R.drawable.no_image_icon)
                    .error(R.drawable.no_image_icon)
                    .into(holder.imageView);
        } else {
            holder.imageView.setImageResource(R.drawable.no_image_icon);
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), NewsFullActivity.class);
            intent.putExtra("url", article.getUrl());
            v.getContext().startActivity(intent);
        });
    }


    void updateData(List<Article> data){
        if (data == null) return;
        articleList.clear();
        articleList.addAll(data);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return articleList.size();
    }

    class NewsViewHolder extends RecyclerView.ViewHolder{

        TextView titleTextView,sourceTextView;
        ImageView imageView;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.article_title);
            sourceTextView = itemView.findViewById(R.id.article_source);
            imageView = itemView.findViewById(R.id.article_image_view);
        }
    }
}
