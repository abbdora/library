package com.example.kpabarenova;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_log);
        DBHelper dbHelper = new DBHelper(this);
        EditText emailEdit = findViewById(R.id.username);
        EditText passEdit = findViewById(R.id.password);
        Button sigupButton = findViewById(R.id.sigupButton);
        sigupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEdit.getText().toString();
                String password = passEdit.getText().toString();
                if (email.equals("") || password.equals("")){
                    Toast.makeText(LogActivity.this, "Заполните все поля", Toast.LENGTH_LONG).show();
                } else {
                    Cursor cursor = dbHelper.checkEmail(email);
                    // Проверка наличия результата
                    if(cursor.moveToFirst()) {
                        Toast.makeText(LogActivity.this, "Пользователь существует! Пожалуйста, выполните вход", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        if (dbHelper.addlog(new Profiles(0, email, password))) {
                            Toast.makeText(LogActivity.this, "Регистрация прошла успешно!", Toast.LENGTH_SHORT).show();
                            next(email);
                        }else{
                            Toast.makeText(LogActivity.this, "Упс! Что-то пошло не так", Toast.LENGTH_SHORT).show();

                        }
                    }

                }
            }
        });
        Button buttonLogin = findViewById(R.id.loginButton);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEdit.getText().toString();
                String password = passEdit.getText().toString();
                if (email.equals("") || password.equals("")){
                    Toast.makeText(LogActivity.this, "Заполните все поля", Toast.LENGTH_LONG).show();
                } else {
                    Cursor cursor = dbHelper.checkEmailPassword(email, password);
                    if (cursor != null && cursor.moveToFirst()) {
                        Toast.makeText(LogActivity.this, "Вы зашли в аккаунт!", Toast.LENGTH_SHORT).show();
                        next(email);
                        cursor.close(); // Закрываем курсор после использования
                    } else {
                        Toast.makeText(LogActivity.this, "Упс! Что-то пошло не так", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    public void next(String email) {
        Intent intent = new Intent();
        intent.putExtra("email", email);
        setResult(RESULT_OK, intent);
        finish();
        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Email", email);
        editor.apply();
    }
}