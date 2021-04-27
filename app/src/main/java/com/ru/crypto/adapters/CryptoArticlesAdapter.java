package com.ru.crypto.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ru.crypto.R;
import com.ru.crypto.models.CryptoArticle;

public class CryptoArticlesAdapter extends PagedListAdapter<CryptoArticle, CryptoArticlesAdapter.ViewHolder> {

    public CryptoArticlesAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CryptoArticle article = getItem(position);
        holder.articleSource.setText(article.getSource());
        holder.articleTitle.setText(article.getArticleTitle());
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

    private static DiffUtil.ItemCallback<CryptoArticle> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<CryptoArticle>() {
                @Override
                public boolean areItemsTheSame(@NonNull CryptoArticle oldItem, @NonNull CryptoArticle newItem) {
                    return oldItem.getArticleURL().equals(newItem.getArticleURL());
                }

                @Override
                public boolean areContentsTheSame(@NonNull CryptoArticle oldItem, @NonNull CryptoArticle newItem) {
                    return oldItem.getArticleTitle().equals(newItem.getArticleTitle());
                }

            };
}
