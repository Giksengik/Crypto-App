package com.ru.crypto.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ru.crypto.utils.Converters;
import com.ru.crypto.R;
import com.ru.crypto.models.CryptoArticle;

import java.util.ArrayList;
import java.util.List;

public class CryptoArticlesAdapter extends RecyclerView.Adapter<CryptoArticlesAdapter.ViewHolder> {
    List<CryptoArticle> articles = new ArrayList<>();

    public CryptoArticlesAdapter(OnArticleClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    private final OnArticleClickListener onClickListener;

    public interface OnArticleClickListener{
        void onArticleClick(CryptoArticle cryptoArticle, int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.arcticle_in_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CryptoArticle article = articles.get(position);
        holder.articleSource.setText(article.getSource());
        holder.articleTitle.setText(article.getArticleTitle());
        holder.articleIcon.setImageBitmap(Converters.decodeBase64(article.getIconString()));
        holder.itemView.setOnClickListener(v -> onClickListener.onArticleClick(article,position));
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public void setArticles(List<CryptoArticle> articles) {
        this.articles = articles;
        notifyDataSetChanged();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView articleTitle, articleSource;
        ImageView articleIcon;
        ViewHolder(View v) {
            super(v);
            articleIcon = v.findViewById(R.id.articleIcon);
            articleTitle = v.findViewById(R.id.articleTitle);
            articleSource = v.findViewById(R.id.articleSource);
        }

    }
}
