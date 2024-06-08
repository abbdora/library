package com.example.kpabarenova;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private final List<Read> read;
    public MyAdapter(List<Read> read) {
        this.read = read;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder
                                         holder, int position) {
        holder.bind(read.get(position));


    }
    @Override
    public int getItemCount() {
        return read.size();
    }
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public ViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.item_text);
        }
        public void bind(Read read) {
            textView.setText(read.getName());
        }
    }
}
