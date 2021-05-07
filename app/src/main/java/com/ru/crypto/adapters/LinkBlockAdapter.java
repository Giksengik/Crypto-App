package com.ru.crypto.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ru.crypto.R;
import com.ru.crypto.models.CurrencyLinks;
import com.ru.crypto.utils.Converters;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class LinkBlockAdapter extends RecyclerView.Adapter<LinkBlockAdapter.ViewHolder> {

    private TreeMap<String, List<String>> linksMap = new TreeMap<>();
    private Context context;

    @NonNull
    @Override
    public LinkBlockAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new LinkBlockAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.link_block_in_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LinkBlockAdapter.ViewHolder holder, int position) {
        Iterator<Map.Entry<String,List<String>>> iterator = linksMap.entrySet().iterator();
        for(int i = 0; i < position; i++)
            iterator.next();
        Map.Entry<String, List<String>> currentLinkBlock = iterator.next();
        holder.description.setText(currentLinkBlock.getKey());

        holder.linksOfDescription.setLayoutManager(new LinearLayoutManager(holder.linksOfDescription.getContext()));
        holder.linksOfDescription.setNestedScrollingEnabled(false);
        LinkAdapter linkAdapter = new LinkAdapter();
        holder.linksOfDescription.setAdapter(linkAdapter);
        linkAdapter.setLinks(currentLinkBlock.getValue());

    }

    @Override
    public int getItemCount() {
        return linksMap.size();
    }

    public void setLinksList(CurrencyLinks links) {
        linksMap = Converters.getLinksMap(links.getLinks());
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView description;
        RecyclerView linksOfDescription;
        ViewHolder(View v) {
            super(v);
            description = v.findViewById(R.id.linksDesctiption);
            linksOfDescription = v.findViewById(R.id.linksOfDescription);
        }

    }
}
