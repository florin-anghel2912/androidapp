package com.hardware.store.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hardware.store.R;
import com.hardware.store.recycler.HomeRecycler;

public class HomeFragment extends Fragment {
    private String[] list = {"CPU (Central Processing Unit)",
                            "GPU (Graphics Processing Unit)"};

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }
        View view = inflater.inflate(R.layout.activity_main, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.Adapter adapter = new HomeRecycler(view.getContext(), list);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL));
        return view;
    }
}
