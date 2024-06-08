package com.example.kpabarenova;

import android.content.Context;
import android.content.Intent;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyBookAdapter extends RecyclerView.Adapter<MyBookAdapter.MyViewHolder> {

    private List<String> dataList;
    private List<String> genreList;
    static Context context;

    public MyBookAdapter(List<String> dataList, List<String> genreList) {
        this.dataList = dataList;
        this.genreList = genreList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_imem_books, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        String data = dataList.get(position);
        String genre = genreList.get(position);
        holder.bindData(data, genre);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private TextView textView2;


        public MyViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.name);
            textView2 = itemView.findViewById(R.id.genre);
        }

        public void bindData(final String data, final String genre) {
            textView.setText(data);
            textView2.setText(genre);




            // Добавляем обработчик нажатий
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Создаем Intent для открытия новой активности
                    Intent intent = new Intent(v.getContext(), BookActivity.class);
                    // Передаем данные в новую активность
                    intent.putExtra("selectedItem", data);
                    // Запускаем новую активность
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}
