package com.hardware.store.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hardware.store.database.DatabaseInstance;
import com.hardware.store.R;
import com.hardware.store.database.Item;
import com.hardware.store.recycler.ListRecycler;

import java.util.List;

import static com.hardware.store.Constants.*;

public class ListFragment extends Fragment {
    private int type;
    private boolean is_amd_cpu, is_intel_cpu, is_amd_gpu, is_nvidia_gpu;
    private int plow, phigh;

    public ListFragment(int type) {
        this.type = type;
    }

    public ListFragment(boolean is_amd_cpu, boolean is_intel_cpu, boolean is_amd_gpu, boolean is_nvidia_gpu, int plow, int phigh) {
        this.type = CUSTOM_SEARCH;
        this.is_amd_cpu = is_amd_cpu;
        this.is_intel_cpu = is_intel_cpu;
        this.is_amd_gpu = is_amd_gpu;
        this.is_nvidia_gpu = is_nvidia_gpu;
        this.plow = plow;
        this.phigh = phigh;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        TextView tv = view.findViewById(R.id.textStartL);
        switch (type) {
            case AMD_CPU_LIST:
                tv.setText(R.string.AMD_CPU);
                break;
            case INTEL_CPU_LIST:
                tv.setText(R.string.INTEL_CPU);
                break;
            case AMD_GPU_LIST:
                tv.setText(R.string.AMD_GPU);
                break;
            case NVIDIA_GPU_LIST:
                tv.setText(R.string.NVIDIA_GPU);
                break;
            case CUSTOM_SEARCH:
                tv.setText(R.string.CUSTOM_SEARCH);
        }
        DatabaseInstance dinst = DatabaseInstance.getDatabaseInstance(getContext());
        List<Item> dblist = dinst.getList();
        int count = 0;
        String[] list;
        int[] price;
        if (type == CUSTOM_SEARCH) {
            for (Item item : dblist) {
                if (is_amd_cpu && item.getType() == AMD_CPU_LIST && item.getPrice() >= plow && item.getPrice() <= phigh)
                    count++;
                if (is_intel_cpu && item.getType() == INTEL_CPU_LIST && item.getPrice() >= plow && item.getPrice() <= phigh)
                    count++;
                if (is_amd_gpu && item.getType() == AMD_GPU_LIST && item.getPrice() >= plow && item.getPrice() <= phigh)
                    count++;
                if (is_nvidia_gpu && item.getType() == NVIDIA_GPU_LIST && item.getPrice() >= plow && item.getPrice() <= phigh)
                    count++;
            }
            list = new String[count];
            price = new int[count];
            int i = 0;
            for (Item item : dblist) {
                if (is_amd_cpu && item.getType() == AMD_CPU_LIST && item.getPrice() >= plow && item.getPrice() <= phigh) {
                    list[i] = item.getName();
                    price[i] = item.getPrice();
                    i++;
                }
                if (is_intel_cpu && item.getType() == INTEL_CPU_LIST && item.getPrice() >= plow && item.getPrice() <= phigh) {
                    list[i] = item.getName();
                    price[i] = item.getPrice();
                    i++;
                }
                if (is_amd_gpu && item.getType() == AMD_GPU_LIST && item.getPrice() >= plow && item.getPrice() <= phigh) {
                    list[i] = item.getName();
                    price[i] = item.getPrice();
                    i++;
                }
                if (is_nvidia_gpu && item.getType() == NVIDIA_GPU_LIST && item.getPrice() >= plow && item.getPrice() <= phigh) {
                    list[i] = item.getName();
                    price[i] = item.getPrice();
                    i++;
                }
            }
        }
        else {
            for (Item item : dblist) {
                if (item.getType() == this.type) {
                    count++;
                }
            }
            list = new String[count];
            price = new int[count];
            int i = 0;
            for (Item item : dblist) {
                if (item.getType() == this.type) {
                    list[i] = item.getName();
                    price[i] = item.getPrice();
                    i++;
                }
            }
        }
        RecyclerView recyclerView = view.findViewById(R.id.recycler_viewL);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.Adapter adapter = new ListRecycler(view.getContext(), list, price);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL));
        return view;
    }
}
