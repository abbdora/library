package com.example.kpabarenova;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.List;


public class LikeFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_like, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.like_list);
        DBHelper DBHelper1 = new DBHelper(getActivity());
        List<Like> contacts = DBHelper1.getAllData();
        SimpleAdapter adapter = new SimpleAdapter(contacts);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        contacts = DBHelper1.getAllData(); // Загружаем обновленный список
        adapter = new SimpleAdapter(contacts);
        recyclerView.setAdapter(adapter);
        ImageButton readbutton = view.findViewById(R.id.bookReadA);
        readbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ReadActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

}