package com.ru.crypto.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ru.crypto.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class CharacteristicAdapter extends RecyclerView.Adapter<CharacteristicAdapter.ViewHolder> {
    TreeMap<String, String> values = new TreeMap<>();

    @NonNull
    @Override
    public CharacteristicAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CharacteristicAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.characteristic_in_list, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CharacteristicAdapter.ViewHolder holder, int position) {
        Iterator<Map.Entry<String,String>> iterator = values.entrySet().iterator();
        for(int i = 0; i < position; i++)
            iterator.next();
        Map.Entry<String, String> currentCharacteristic = iterator.next();
        holder.characteristicKey.setText(currentCharacteristic.getKey() + ":");
        holder.characteristicValue.setText(currentCharacteristic.getValue());
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public void setValues(TreeMap<String, String> values) {
        this.values = values;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView characteristicKey, characteristicValue;
        ViewHolder(View v) {
            super(v);
            characteristicKey = v.findViewById(R.id.charactericticKey);
            characteristicValue = v.findViewById(R.id.characteristicValue);
        }

    }
}
