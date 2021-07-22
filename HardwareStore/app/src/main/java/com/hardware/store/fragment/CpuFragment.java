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
import com.hardware.store.recycler.CpuRecycler;

public class CpuFragment extends Fragment {
    private String[] list = {"Buy AMD CPU", "Buy Intel CPU"};

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cpu, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_viewC);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.Adapter adapter = new CpuRecycler(view.getContext(), list);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL));
        return view;
    }
}
