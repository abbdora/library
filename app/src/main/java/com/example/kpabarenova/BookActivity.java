package com.example.kpabarenova;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

public class BookActivity extends AppCompatActivity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_book);
        textView = findViewById(R.id.book_name);
        // Получаем переданное значение selectedItem из Intent
        String selectedItem = getIntent().getStringExtra("selectedItem");
        // Устанавливаем значение selectedItem в TextView
        textView.setText(selectedItem);
        setImage(selectedItem);
        saveDataFotImage(selectedItem);
        RatingBar ratingBar = findViewById(R.id.star);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                float stars = (float) ratingBar.getRating();
                SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                final SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putFloat(selectedItem + "_star", stars);
                editor.apply();
            }
        });
        // Получаем ActionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Настройки ActionBar
            actionBar.setDisplayHomeAsUpEnabled(true); // Показать кнопку назад
            actionBar.setTitle("Чтение"); // Установить заголовок
            ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#6D77FA"));
            actionBar.setBackgroundDrawable(colorDrawable);
        }
        //Кнопка для избрабранного
        ImageButton lik_button = findViewById(R.id.likeButton);
        lik_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                String isLog = sharedPreferences.getString("Email", "1");
                if (isLog.equals("1")){
                    showDialog();
                } else {
                    // Загрузка предыдущего состояния Image Button для конкретной книги
                    boolean isButtonPressed = sharedPreferences.getBoolean(selectedItem + "_heart", false);
                    if (isButtonPressed) {
                        deleteFromLike();
                    } else {
                        addToLike();
                    }
                }

            }
        });

        ImageButton buttom_read = findViewById(R.id.reabButton);
        buttom_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                String isLog = sharedPreferences.getString("Email", "1");
                if (isLog.equals("1")){
                    showDialog();
                } else {
                    // Загрузка предыдущего состояния Image Button для конкретной книги
                    boolean isButtonPressed = sharedPreferences.getBoolean(selectedItem + "_read", false);
                    if (isButtonPressed) {
                        deleteFromRead();
                    } else {
                        addToRead();
                    }
                }
            }
        });

//        Кнопка для появления описания
        ImageButton button_irrow = findViewById(R.id.irrow);
        button_irrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView addText = findViewById(R.id.add_text);
                String mainText = textView.getText().toString();
                v.setSelected(!v.isSelected());
                if (v.isSelected()){
                    textForInformation(mainText);
                }
                else {
                    addText.setText("");
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_nav_munu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
            return true;
    }
//    Метод для добавления в избранное
    public void addToLike(){
        DBHelper dbHelper = new DBHelper(this);
        String mainText = textView.getText().toString();
        if (dbHelper.addLike(new Like(0, mainText)))
        {
            ImageButton lik_button = findViewById(R.id.likeButton);
            lik_button.setImageResource(R.drawable.like_red);
            SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
            final SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(mainText + "_heart", true);
            editor.apply();
        } else {
            Toast.makeText(this, "Попробуйте еще раз!",
                    Toast.LENGTH_SHORT).show();
        }
    }
    public void deleteFromLike(){
        DBHelper dbHelper = new DBHelper(BookActivity.this);
        String mainText = textView.getText().toString();
        dbHelper.deleteData(mainText);
        ImageButton lik_button = findViewById(R.id.likeButton);
        lik_button.setImageResource(R.drawable.like_for_fav_book);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        // Удаление данных по ключу
        editor.remove(mainText + "_heart");
        // Применение изменений
        editor.apply();
    }
    public void textForInformation (String condition){
        DatabaseHelper databaseHelper = new DatabaseHelper(BookActivity.this);
        Cursor cursor = databaseHelper.getSpecificInfo(condition);
        // Проверка наличия результата
        if(cursor.moveToFirst()) {
            String info = cursor.getString(0); // Получаем значение столбца "info" (индекс 0)
            // Устанавливаем значение в TextView
            TextView textView = findViewById(R.id.add_text);
            textView.setText(info);
        } else {
            // Обработка случая, когда информация не найдена
            TextView textView = findViewById(R.id.add_text);
            textView.setText("Информация не найдена.");
        }
        cursor.close();
    }
    public void setImage(String condition){
        DatabaseHelper databaseHelper = new DatabaseHelper(BookActivity.this);
        Cursor cursor = databaseHelper.getSpecificPhoto(condition);
        if(cursor.moveToFirst()) {
            String imgPath = cursor.getString(0); // Получаем значение столбца
            Glide.with(this)
                    .asBitmap()
                    .load(imgPath)
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            // Установите изображение в ImageView
                            ImageView imageView = findViewById(R.id.imageBook);
                            imageView.setImageBitmap(resource);
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {

                        }
                    });
        } else {
        }
        cursor.close();
    }

    public void addToRead(){
        DBHelper dbHelper = new DBHelper(this);
        String mainText = textView.getText().toString();
        if (dbHelper.addRead(new Read(0, mainText)))
        {
            ImageButton raed_button = findViewById(R.id.reabButton);
            raed_button.setImageResource(R.drawable.book_read);
            SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
            final SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(mainText + "_read", true);
            editor.apply();
        } else {
            Toast.makeText(this, "Попробуйте еще раз",
                    Toast.LENGTH_SHORT).show();
        }
    }
    public void deleteFromRead(){
        DBHelper dbHelper = new DBHelper(BookActivity.this);
        String mainText = textView.getText().toString();
        dbHelper.deleteRead(mainText);
        ImageButton read_button = findViewById(R.id.reabButton);
        read_button.setImageResource(R.drawable.book_ne_read);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        // Удаление данных по ключу
        editor.remove(mainText + "_read");
        // Применение изменений
        editor.apply();
    }
    public void saveDataFotImage(String selectedItem){
        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);

        final ImageButton imageButton = findViewById(R.id.likeButton);
        final ImageButton imageButton2 = findViewById(R.id.reabButton);

        // Загрузка предыдущего состояния Image Button для конкретной книги
        boolean isButtonPressed = sharedPreferences.getBoolean(selectedItem + "_heart", false);
        if (isButtonPressed) {
            imageButton.setImageResource(R.drawable.like_red); // Устанавливаем сохраненную картинку
        } else {
            imageButton.setImageResource(R.drawable.like_for_fav_book);
        }

        boolean isButtonPressed2 = sharedPreferences.getBoolean(selectedItem + "_read", false);
        if (isButtonPressed2) {
            imageButton2.setImageResource(R.drawable.book_read); // Устанавливаем сохраненную картинку
        } else {
            imageButton2.setImageResource(R.drawable.book_ne_read);
        }

        RatingBar ratingBar = findViewById(R.id.star);
        float amountStar = sharedPreferences.getFloat(selectedItem + "_star", 0);
        if (amountStar > 0){
            ratingBar.setRating(amountStar);
        }
    }
    public void showDialog(){
        // Создание строителя диалоговых окон
        AlertDialog.Builder builder = new AlertDialog.Builder(BookActivity.this);
        // Установка заголовка и сообщения диалогового окна
        builder.setTitle("Вы не зарегистрированы!");
        builder.setMessage("Хотите зарегистрироваться?");
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        // Установка кнопки "Да" и ее обработчика
        builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(BookActivity.this, LogActivity.class);
                        startActivity(intent);
                    }
                });
        // Установка кнопки "Отмена" и ее обработчика
        builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Обработка отмены действия
                        dialog.dismiss();
                    }
                });
        // Создание и отображение AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }


}