package com.hardware.store.recycler;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hardware.store.R;

public class DetailRecycler extends RecyclerView.Adapter<DetailRecycler.RecyclerHolder> {
    private String[] list;

    static class RecyclerHolder extends RecyclerView.ViewHolder {
        TextView textView;
        RecyclerHolder(LinearLayout v) {
            super(v);
            textView = v.findViewById(R.id.textView);
        }
    }

    public DetailRecycler(String[] list) {
        this.list = list;
    }

    @Override
    @NonNull
    public DetailRecycler.RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout v;
        v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view, parent, false);
        return new RecyclerHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, final int position) {
        holder.textView.setText(list[position]);
    }

    @Override
    public int getItemCount() {
        return list.length;
    }
}
