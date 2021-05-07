package com.ru.crypto.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ru.crypto.R;

import java.util.ArrayList;
import java.util.List;

public class LinkAdapter extends RecyclerView.Adapter<LinkAdapter.ViewHolder> {
    List<String> linkList  = new ArrayList<>();
    @NonNull
    @Override
    public LinkAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LinkAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.link_in_list,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LinkAdapter.ViewHolder holder, int position) {
        holder.link.setText(linkList.get(position));
    }

    @Override
    public int getItemCount() {
        return linkList.size();
    }

    public void setLinks(List<String> links) {
        linkList = links;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView link;
        ViewHolder(View v) {
            super(v);
            link = v.findViewById(R.id.link);
        }

    }
}
