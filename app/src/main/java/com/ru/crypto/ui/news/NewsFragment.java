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
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ru.crypto.R;
import com.ru.crypto.adapters.CryptoArticlesAdapter;

public class NewsFragment extends Fragment implements LifecycleOwner {

    private NewsViewModel mNewsViewModel;

    public void initializeToolbar() {
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        String toolbarName = "News";
        TextView toolbarTitle  = getActivity().findViewById(R.id.toolbarTitle);
        if(toolbar != null) {
            toolbar.getMenu().getItem(0).setVisible(true);
            toolbarTitle.setText(toolbarName);
        }
    }

    private final CryptoArticlesAdapter mArticleAdapter = new CryptoArticlesAdapter((cryptoArticle, position) -> {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(cryptoArticle.getArticleURL()));
        startActivity(browserIntent);
    });

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNewsViewModel =
                new ViewModelProvider(this).get(NewsViewModel.class);
        initializeToolbar();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_news, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.articlesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mArticleAdapter);
        mNewsViewModel.getArticles().observe(getViewLifecycleOwner(), mArticleAdapter::setArticles);

        getLifecycle().addObserver(mNewsViewModel);

        return root;
    }
}