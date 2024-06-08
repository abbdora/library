package com.example.kpabarenova;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


public class LogFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_log, container, false);
        DBHelper dbHelper = new DBHelper(getActivity());
        EditText emailEdit = view.findViewById(R.id.username);
        EditText passEdit = view.findViewById(R.id.password);
        Button sigupButton = view.findViewById(R.id.sigupButton);
        sigupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEdit.getText().toString();
                String password = passEdit.getText().toString();
                if (email.equals("") || password.equals("")){
                    Toast.makeText(getActivity(), "Заполните все поля", Toast.LENGTH_LONG).show();
                } else {
                    Cursor cursor = dbHelper.checkEmail(email);
                    // Проверка наличия результата
                    if(cursor.moveToFirst()) {
                        Toast.makeText(getActivity(), "User already exists! Please login", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        if (dbHelper.addlog(new Profiles(0, email, password))) {
                            Toast.makeText(getActivity(), "Signup Successfully!", Toast.LENGTH_SHORT).show();
                            next();
                        }else{
                            Toast.makeText(getActivity(), "Signup Failed!", Toast.LENGTH_SHORT).show();

                        }
                    }

                }
            }
        });
        Button buttonLogin = view.findViewById(R.id.loginButton);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEdit.getText().toString();
                String password = passEdit.getText().toString();
                if (email.equals("") || password.equals("")){
                    Toast.makeText(getActivity(), "Заполните все поля", Toast.LENGTH_LONG).show();
                } else {
                    Cursor cursor = dbHelper.checkEmailPassword(email, password);
                    if(cursor.moveToFirst()) {
                        Toast.makeText(getActivity(), "Login Successfully!", Toast.LENGTH_SHORT).show();
                        next();
                    }else{
                        Toast.makeText(getActivity(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return view;
    }
    public void next(){

    }
}