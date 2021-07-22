package com.hardware.store.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hardware.store.R;
import com.hardware.store.database.Item;
import com.hardware.store.fragment.CartFragment;

import java.util.ArrayList;

public class CartRecycler extends RecyclerView.Adapter<CartRecycler.RecyclerHolder> {
    private ArrayList<Integer> q;
    private ArrayList<Item> items;

    static class RecyclerHolder extends RecyclerView.ViewHolder {
        TextView textView, textViewP, textViewQ;
        Button button;
        RecyclerHolder(LinearLayout v) {
            super(v);
            textView = v.findViewById(R.id.textView);
            textViewQ = v.findViewById(R.id.textViewQ);
            textViewP = v.findViewById(R.id.textViewP);
            button = v.findViewById(R.id.deleteButton);
        }
    }

    public CartRecycler(ArrayList<Integer> q, ArrayList<Item> items) {
        this.q = q;
        this.items = items;
    }

    @Override
    @NonNull
    public CartRecycler.RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout v;
        v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_cart, parent, false);
        return new RecyclerHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, final int position) {
        holder.textViewQ.setText("x" + q.get(position).toString());
        holder.textView.setText(items.get(position).getName());
        holder.textViewP.setText("$" + Integer.valueOf(q.get(position) * items.get(position).getPrice()).toString());
        View.OnClickListener clk = new View.OnClickListener() {
            public void onClick(View view) {
                q.remove(position);
                items.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, q.size());
                int total = 0;
                for (int i = 0; i < q.size(); i++) {
                    total += q.get(i) * items.get(i).getPrice();
                }
                CartFragment.updateTotal(total);
            }
        };
        holder.button.setOnClickListener(clk);
    }

    @Override
    public int getItemCount() {
        return q.size();
    }
}
