package com.hardware.store.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hardware.store.database.DatabaseInstance;
import com.hardware.store.R;
import com.hardware.store.database.Item;
import com.hardware.store.recycler.DetailRecycler;

import java.util.List;

import static com.hardware.store.Constants.AMD_GPU_LIST;
import static com.hardware.store.Constants.ARG_COUNT;
import static com.hardware.store.Constants.NVIDIA_GPU_LIST;

public class DetailFragment extends Fragment {
    private Context context;
    private String name;
    private int price;

    public DetailFragment(Context context, String name, int price) {
        this.context = context;
        this.name = name;
        this.price = price;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_detail, container, false);
        TextView tv = view.findViewById(R.id.textStartD);
        tv.setText(this.name + "\n" + "Price: $" + this.price);
        DatabaseInstance dinst = DatabaseInstance.getDatabaseInstance(getContext());
        List<Item> dblist = dinst.getList();
        String[] list = new String[ARG_COUNT];
        Item itm = null;
        for (Item item : dblist) {
            if (item.getName().equals(this.name)) {
                itm = item;
                if (item.getType() == AMD_GPU_LIST  || item.getType() == NVIDIA_GPU_LIST) {
                    list[0] = "Compute Units: " + Integer.valueOf(item.getCores()).toString();
                    list[1] = "GPU Cores: " + Integer.valueOf(item.getThreads()).toString();
                    list[2] = "Base Clock: " + Integer.valueOf(item.getBaseFreq()).toString() + " MHz";
                    list[3] = "Boost Clock: " + Integer.valueOf(item.getBoostFreq()).toString() + " MHz";
                    list[4] = "Memory: " + Integer.valueOf(item.getMemory()).toString() + " MB";
                }
                else {
                    list[0] = "Cores: " + Integer.valueOf(item.getCores()).toString();
                    list[1] = "Threads: " + Integer.valueOf(item.getThreads()).toString();
                    list[2] = "Base Clock: " + Integer.valueOf(item.getBaseFreq()).toString() + " MHz";
                    list[3] = "Boost Clock: " + Integer.valueOf(item.getBoostFreq()).toString() + " MHz";
                    list[4] = "L3 Cache: " + Integer.valueOf(item.getMemory()).toString() + " MB";
                }
            }
        }
        final Item toBuy = itm;
        RecyclerView recyclerView = view.findViewById(R.id.recycler_viewD);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.Adapter adapter = new DetailRecycler(list);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL));
        Button button = view.findViewById(R.id.button);
        View.OnClickListener clk = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et = view.findViewById(R.id.editText);
                int q;
                try {
                    q = Integer.valueOf(et.getText().toString());
                } catch (NumberFormatException e) {
                    return;
                }
                FragmentManager fm = ((AppCompatActivity) context).getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.activity, CartFragment.getCartFragment(q, toBuy));
                ft.addToBackStack(null);
                ft.commit();
            }
        };
        button.setOnClickListener(clk);
        return view;
    }
}
