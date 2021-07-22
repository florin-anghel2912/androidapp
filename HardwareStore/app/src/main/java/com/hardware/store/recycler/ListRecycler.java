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
import com.hardware.store.fragment.DetailFragment;

public class ListRecycler extends RecyclerView.Adapter<ListRecycler.RecyclerHolder> {
    private Context context;
    private String[] list;
    private int[] price;

    static class RecyclerHolder extends RecyclerView.ViewHolder {
        TextView textView, textViewP;
        RecyclerHolder(LinearLayout v) {
            super(v);
            textView = v.findViewById(R.id.textView);
            textViewP = v.findViewById(R.id.textViewP);
        }
    }

    public ListRecycler(Context context, String[] list, int[] price) {
        this.context = context;
        this.list = list;
        this.price = price;
    }

    @Override
    @NonNull
    public ListRecycler.RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout v;
            v = (LinearLayout) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycler_list, parent, false);
        return new RecyclerHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, final int position) {
        holder.textView.setText(list[position]);
        holder.textViewP.setText("$" + Integer.valueOf(price[position]).toString());
        View.OnClickListener clk = new View.OnClickListener() {
            public void onClick(View view) {
                FragmentManager fm = ((AppCompatActivity) context).getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.activity, new DetailFragment(context, list[position], price[position]));
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
