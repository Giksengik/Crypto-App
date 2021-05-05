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
import com.ru.crypto.models.CurrencyCharacteristic;

import java.util.ArrayList;

public class CharacteristicsBlockAdapter extends RecyclerView.Adapter<CharacteristicsBlockAdapter.ViewHolder> {

    ArrayList<CurrencyCharacteristic> currencyCharacteristics;

    Context context;

    @NonNull
    @Override
    public CharacteristicsBlockAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new CharacteristicsBlockAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.currency_characterictic_block_in_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CharacteristicsBlockAdapter.ViewHolder holder, int position) {
        holder.title.setText(currencyCharacteristics.get(position).getTitle());
        LinearLayoutManager manager = new LinearLayoutManager(context);
        holder.characteristicsList.setLayoutManager(manager);
        holder.characteristicsList.setHasFixedSize(true);
        CharacteristicAdapter adapter = new CharacteristicAdapter();
        holder.characteristicsList.setAdapter(adapter);
        adapter.setValues(currencyCharacteristics.get(position).getCharacteristics());
    }

    public void setCurrencyCharacteristics(ArrayList<CurrencyCharacteristic> currencyCharacteristics) {
        this.currencyCharacteristics = currencyCharacteristics;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return currencyCharacteristics.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        RecyclerView characteristicsList;
        ViewHolder(View v) {
            super(v);
            title = v.findViewById(R.id.characterishicTitle);
            characteristicsList = v.findViewById(R.id.characteristicList);
        }
    }

}
