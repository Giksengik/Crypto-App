package com.ru.crypto.ui.news;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ru.crypto.R;
import com.ru.crypto.adapters.CryptoArticlesAdapter;
import com.ru.crypto.models.CryptoArticle;

import java.util.List;

public class NewsFragment extends Fragment {

    private NewsViewModel mNewsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mNewsViewModel =
                new ViewModelProvider(this).get(NewsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_news, container, false);
        CryptoArticlesAdapter adapter = new CryptoArticlesAdapter((cryptoArticle, position) -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(cryptoArticle.getArticleURL()));
            startActivity(browserIntent);
        });
        RecyclerView recyclerView = root.findViewById(R.id.articlesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        mNewsViewModel.getArticles().observe(getViewLifecycleOwner(), adapter::setArticles);
        return root;
    }
}