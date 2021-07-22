package com.hardware.store.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.hardware.store.R;
import com.hardware.store.fragment.ListFragment;

import static com.hardware.store.Constants.*;

public class GpuRecycler extends RecyclerView.Adapter<GpuRecycler.RecyclerHolder> {
    private Context context;
    private String[] list;

    static class RecyclerHolder extends RecyclerView.ViewHolder {
        TextView textView;
        RecyclerHolder(LinearLayout v) {
            super(v);
            textView = v.findViewById(R.id.textView);
        }
    }

    public GpuRecycler(Context context, String[] list) {
        this.context = context;
        this.list = list;
    }

    @Override
    @NonNull
    public GpuRecycler.RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout v;
        v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view, parent, false);
        return new RecyclerHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, final int position) {
        holder.textView.setText(list[position]);
        View.OnClickListener clk = new View.OnClickListener() {
            public void onClick(View view) {
                FragmentManager fm = ((AppCompatActivity) context).getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                switch (position) {
                    case AMD_GPU:
                        ft.replace(R.id.activity, new ListFragment(AMD_GPU_LIST));
                        break;
                    case NVIDIA_GPU:
                        ft.replace(R.id.activity, new ListFragment(NVIDIA_GPU_LIST));
                        break;
                }
                ft.addToBackStack(null);
                ft.commit();
            }
        };
        holder.textView.setOnClickListener(clk);
    }

    @Override
    public int getItemCount() {
        return list.length;
    }
}
