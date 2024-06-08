package com.example.kpabarenova;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private SimpleAdapter myAdapter;
    private Context Context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        recyclerView = view.findViewById(R.id.my_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        boolean isSwitchPressed = sharedPreferences.getBoolean("CHILD_MODE", false);
        if (isSwitchPressed) {
            String[] books = getResources().getStringArray(R.array.LibraryCh);
            String[] genreList = getResources().getStringArray(R.array.GenreCh);
            MyBookAdapter myAdapter2 = new MyBookAdapter(Arrays.asList(books), Arrays.asList(genreList));
            recyclerView.setAdapter(myAdapter2);
        } else {
            String[] books = getResources().getStringArray(R.array.Library);
            String[] genreList = getResources().getStringArray(R.array.Genre);
            MyBookAdapter myAdapter2 = new MyBookAdapter(Arrays.asList(books), Arrays.asList(genreList));
            recyclerView.setAdapter(myAdapter2);
        }

        return view;
    }

}