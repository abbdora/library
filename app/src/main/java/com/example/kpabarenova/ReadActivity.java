package com.example.kpabarenova;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ReadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_read);

        // Получаем ActionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Настройки ActionBar
            actionBar.setDisplayHomeAsUpEnabled(true); // Показать кнопку назад
            actionBar.setTitle("Список прочитанного"); // Установить заголовок
            ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#6D77FA"));
            actionBar.setBackgroundDrawable(colorDrawable);
            // Другие настройки ActionBar
        }

        RecyclerView recyclerView = findViewById(R.id.read_list);
        DBHelper DBHelper1 = new DBHelper(this);
        List<Read> bookname = DBHelper1.getAllRead();
        MyAdapter adapter = new MyAdapter(bookname);
        recyclerView.setLayoutManager(new LinearLayoutManager(ReadActivity.this));
        recyclerView.setAdapter(adapter);
        bookname = DBHelper1.getAllRead(); // Загружаем обновленный список
        adapter = new MyAdapter(bookname);
        recyclerView.setAdapter(adapter);
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
}