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
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.ru.crypto.MainActivity;
import com.ru.crypto.R;
import com.ru.crypto.models.CryptoCurrency;
import com.ru.crypto.models.CryptoCurrencyName;
import com.ru.crypto.models.NotificationData;
import com.ru.crypto.ui.fragments.TimeRangeFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreatingNotificationDialogFragment extends DialogFragment {

    public static String TAG = "CreatingNotificationDialog";

    private Spinner notificationTypeSpinner;
    private TextView  textChooseTimeInterval;
    private Spinner notificationTimeIntervalSpinner;
    private AutoCompleteTextView currenciesIDList;
    private onCreationNotificationClickListener mOnCreationNotificationClickListener = null;

    private Button buttonOk;
    private Button buttonCancel;

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
        View root = LayoutInflater.from(inflater.getContext()).inflate(R.layout.fragment_dialog_creation, container, false);

        notificationTimeIntervalSpinner = root.findViewById(R.id.notificationIntervalSpinner);
        textChooseTimeInterval = root.findViewById(R.id.textNotificationInterval);
        notificationTypeSpinner = root.findViewById(R.id.notificationTypeSpinner);
        currenciesIDList = root.findViewById(R.id.autoCompleteIDOfCryptoCurrency);
        buttonOk = root.findViewById(R.id.buttonCreatingOk);
        buttonCancel = root.findViewById(R.id.buttcnCancelCreating);



        if(currenciesID != null) {
            currenciesIDList.setAdapter(new
                    ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, currenciesID));
        }

        buttonCancel.setOnClickListener(v -> dismiss());

        buttonOk.setOnClickListener(v -> {
            if(Arrays.asList(currenciesID).contains(currenciesIDList.getText().toString()))
            if(notificationTypeSpinner.getSelectedItem().equals("Single")) {
                addSingleNotification(currenciesIDList.getText().toString(),
                        getTimeInMilliseconds((String)notificationTimeIntervalSpinner.getSelectedItem()));
                dismiss();
            }
        });

        notificationTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if(position != 1) {
                            textChooseTimeInterval.setVisibility(View.VISIBLE);
                            notificationTimeIntervalSpinner.setVisibility(View.VISIBLE);
                        }
                        else {
                            textChooseTimeInterval.setVisibility(View.GONE);
                            notificationTimeIntervalSpinner.setVisibility(View.GONE);
                        }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                textChooseTimeInterval.setVisibility(View.GONE);
                notificationTimeIntervalSpinner.setVisibility(View.GONE);
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
        if(currenciesIDList != null) {
            currenciesIDList.setAdapter(new
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
