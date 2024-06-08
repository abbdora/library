package com.example.kpabarenova;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;


public class ProfileFragment extends Fragment {

    private static final String ARG_EMAIL = "email";

    private Switch switch1;
    private String email;


    public static ProfileFragment newInstance(String email) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_EMAIL, email);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            email = getArguments().getString(ARG_EMAIL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        switch1 = view.findViewById(R.id.switch1);

        TextView emailTextView = view.findViewById(R.id.emailText);
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        // Установка начального состояния Switch из SharedPreferences
        boolean isChildModeEnabled = sharedPreferences.getBoolean("CHILD_MODE", false);

        // Установка слушателя для Switch
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChildModeEnabled) {
                if (isChildModeEnabled) {
                    // Сохранение состояния в SharedPreferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("CHILD_MODE", true);
                    editor.apply();
                } else {
                    // Удаление состояния из SharedPreferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("CHILD_MODE");
                    editor.apply();
                }
            }
        });
        switch1.setChecked(isChildModeEnabled);
        String emails = sharedPreferences.getString("Email", "Вы не зашли в аккаунт");
        if (emails.equals("Вы не зашли в аккаунт")){
            emailTextView.setText("Вы не зашли в аккаунт");
        } else {
            emailTextView.setText("Почта: " + emails);
        }


        Button logButton = view.findViewById(R.id.but_log);
        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LogActivity.class);
                startActivity(intent);
            }
        });

        Button buttonExit = view.findViewById(R.id.buttonExit);
        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbHelper = new DBHelper(getActivity());
                // Получаем экземпляр базы данных
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                // Удаляем все данные из таблицы
                db.delete("readName", null, null);
                db.delete("favName", null, null);
                // Закрываем базу данных
                db.close();

                // Удаление данных
                SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                // Удаление данных по ключу
                editor.remove("Email");
                // Удаление всех данных
                editor.clear();
                // Применение изменений
                editor.apply();
                TextView emailTextView = view.findViewById(R.id.emailText);
                emailTextView.setText("Вы не зашли в аккаунт");
            }
        });

        return view;
    }

}