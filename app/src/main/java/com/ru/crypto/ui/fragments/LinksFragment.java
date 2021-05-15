package com.ru.crypto.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ru.crypto.R;
import com.ru.crypto.adapters.LinkBlockAdapter;
import com.ru.crypto.databinding.FragmentLinksBinding;
import com.ru.crypto.models.CurrencyLinks;
import com.ru.crypto.models.Links;

import java.util.zip.Inflater;

public class LinksFragment extends Fragment {

    private FragmentLinksBinding binding;
    private LinkBlockAdapter linkBlockAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentLinksBinding.inflate(inflater);
        View root = binding.getRoot();

        binding.linksList.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.linksList.setNestedScrollingEnabled(false);
        linkBlockAdapter = new LinkBlockAdapter();
        binding.linksList.setAdapter(linkBlockAdapter);

        return root;
    }

    public void setLinksData(CurrencyLinks links) {
        linkBlockAdapter.setLinksList(links);
    }

}
