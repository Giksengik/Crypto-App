package com.ru.crypto.ui.notifications;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.ru.crypto.MainActivity;
import com.ru.crypto.R;
import com.ru.crypto.databinding.FragmentDialogCreationBinding;
import com.ru.crypto.models.CryptoCurrency;
import com.ru.crypto.models.CryptoCurrencyName;
import com.ru.crypto.models.NotificationData;
import com.ru.crypto.ui.fragments.TimeRangeFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;

public class CreatingNotificationDialogFragment extends DialogFragment {

    public static String TAG = "CreatingNotificationDialog";
    private onCreationNotificationClickListener mOnCreationNotificationClickListener = null;
    FragmentDialogCreationBinding binding;

    String [] currenciesID;

    public interface onCreationNotificationClickListener{
        void onCreationNotificationClick(NotificationData notificationData);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity mainActivity =(MainActivity) context;
        Fragment fragment;
        NavHostFragment navHostFragment = (NavHostFragment) mainActivity.getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        fragment = navHostFragment.getChildFragmentManager().getFragments().get(0);
        if(fragment instanceof onCreationNotificationClickListener) {
            mOnCreationNotificationClickListener = (onCreationNotificationClickListener) fragment;
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDialogCreationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        if(currenciesID != null) {
            binding.autoCompleteIDOfCryptoCurrency.setAdapter(new
                    ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, currenciesID));
        }

        binding.buttcnCancelCreating.setOnClickListener(v -> dismiss());

        binding.buttonCreatingOk.setOnClickListener(v -> {
            if(Arrays.asList(currenciesID).contains(binding.autoCompleteIDOfCryptoCurrency.getText().toString())) {
                if (binding.notificationTypeSpinner.getSelectedItem().equals("Single")) {
                    addSingleNotification(binding.autoCompleteIDOfCryptoCurrency.getText().toString(),
                            getTimeInMilliseconds((String) binding.notificationIntervalSpinner.getSelectedItem()));
                    dismiss();
                }
                else if (binding.notificationTypeSpinner.getSelectedItem().equals("Cyclical")) {
                    addCyclicalNotification(binding.autoCompleteIDOfCryptoCurrency.getText().toString(),
                            getTimeInMilliseconds((String) binding.notificationIntervalSpinner.getSelectedItem()));
                    dismiss();
                }
                else if (binding.notificationTypeSpinner.getSelectedItem().equals("Border")) {
                    if(binding.editTextTextTopBorder.getText().toString().matches("[0-9[,.]]+")
                    && binding.editTextBottomBorder.getText().toString().matches("[0-9[,.]]+"))
                    addBorderNotification();
                    dismiss();
                }
            }else Toast.makeText(getContext(), "Choose currency crypto currency!", Toast.LENGTH_SHORT).show();
        });

        binding.notificationTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if(position != 1) {
                            binding.textNotificationInterval.setVisibility(View.VISIBLE);
                            binding.notificationIntervalSpinner.setVisibility(View.VISIBLE);
                            binding.textBottomBorder.setVisibility(View.GONE);
                            binding.editTextBottomBorder.setVisibility(View.GONE);
                            binding.textTopBorder.setVisibility(View.GONE);
                            binding.editTextTextTopBorder.setVisibility(View.GONE);
                        }
                        else {
                            binding.textNotificationInterval.setVisibility(View.GONE);
                            binding.notificationIntervalSpinner.setVisibility(View.GONE);
                            binding.textBottomBorder.setVisibility(View.VISIBLE);
                            binding.editTextBottomBorder.setVisibility(View.VISIBLE);
                            binding.textTopBorder.setVisibility(View.VISIBLE);
                            binding.editTextTextTopBorder.setVisibility(View.VISIBLE);
                        }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                binding.textNotificationInterval.setVisibility(View.GONE);
                binding.notificationIntervalSpinner.setVisibility(View.GONE);
                binding.textBottomBorder.setVisibility(View.GONE);
                binding.editTextBottomBorder.setVisibility(View.GONE);
                binding.textTopBorder.setVisibility(View.GONE);
                binding.editTextTextTopBorder.setVisibility(View.GONE);
            }
        });

        return root;
    }

    public void setCurrenciesList(List<CryptoCurrencyName> currenciesList) {
        String [] ids = new String[currenciesList.size()];
        int i = 0;
        for(CryptoCurrencyName item : currenciesList) {
            ids[i] = item.getId();
            i++;
        }
        if(currenciesID != null) {
            binding.autoCompleteIDOfCryptoCurrency.setAdapter(new
                    ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, ids));

        }
        currenciesID = ids;
    }

    public void addSingleNotification(String currencyID, long timeInterval) {

        NotificationData singleNotification = new NotificationData();

        singleNotification.setCurrencyID(currencyID);
        singleNotification.setNextNotificationTime(timeInterval + System.currentTimeMillis());
        singleNotification.setNotificationType(NotificationData.TYPE_SINGLE);

        mOnCreationNotificationClickListener.onCreationNotificationClick(singleNotification);
    }

    public void addCyclicalNotification(String currencyID, long timeInterval) {

        NotificationData circularNotification = new NotificationData();

        circularNotification.setCurrencyID(currencyID);
        circularNotification.setNextNotificationTime(timeInterval + System.currentTimeMillis());
        circularNotification.setIntervalValueInMillis(timeInterval);
        circularNotification.setNotificationType(NotificationData.TYPE_CYCLICAL_PRICE);

        mOnCreationNotificationClickListener.onCreationNotificationClick(circularNotification);

    }

    public void addBorderNotification() {
        NotificationData borderNotification = new NotificationData();
        borderNotification.setCurrencyID(binding.autoCompleteIDOfCryptoCurrency.getText().toString());
        if(binding.editTextBottomBorder.getText().toString().length() > 0)
            borderNotification.setBottomBorder(Double.parseDouble(binding.editTextBottomBorder.getText().toString()));
        if(binding.editTextTextTopBorder.getText().toString().length() > 0)
            borderNotification.setTopBorder(Double.parseDouble(binding.editTextTextTopBorder.getText().toString()));
        if(borderNotification.getBottomBorder() != 0 || borderNotification.getTopBorder() != 0) {
            borderNotification.setNotificationType(NotificationData.TYPE_BORDER_EVENT);
            mOnCreationNotificationClickListener.onCreationNotificationClick(borderNotification);
        }
    }


    public long getTimeInMilliseconds(String time) {
        switch(time) {
            case "30 sec":
                return 30 * 1000;
            case "1 min":
                return 60 * 1000;
            case "30 min":
                return 30 * 60 * 1000;
            case "1 hour":
                return 60 * 60 * 1000;
            case "2 hours":
                return 2 * 60 * 60 * 1000;
            case "3 hours":
                return 3 * 60 * 60 * 1000;
            case "6 hours":
                return 6 * 60 * 60 * 1000;
            case "12 hours":
                return 12 * 60 * 60 * 1000;
            case "1d":
                return 24 * 60 * 60 * 1000;
        }
        return 0;
    }
}
