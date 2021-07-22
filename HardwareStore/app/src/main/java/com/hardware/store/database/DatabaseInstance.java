package com.hardware.store.database;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import static com.hardware.store.Constants.*;

public class DatabaseInstance {
    private static DatabaseInstance INSTANCE;
    private List<Item> dbitems;

    private DatabaseInstance(ItemDatabase db) {
        ItemDAO itemDAO = db.getItemDAO();
        List<Item> dbitems = itemDAO.getItems();
        if (dbitems.size() == ITEM_COUNT) {
            this.dbitems = itemDAO.getItems();
            return;
        }
        if (dbitems.size() > 0) {
            for (Item item : dbitems) {
                itemDAO.delete(item);
            }
        }
        ArrayList<Item> items = new ArrayList<>();
        items.add(new Item("Ryzen 3 2200G", AMD_CPU_LIST, 100, 4, 4, 3500, 3700, 4));
        items.add(new Item("Ryzen 5 2400G", AMD_CPU_LIST, 170,4, 8, 3600, 3900, 4));
        items.add(new Item("Ryzen 5 2600", AMD_CPU_LIST, 200, 6, 12, 3400, 3900, 16));
        items.add(new Item("Ryzen 5 2600X", AMD_CPU_LIST, 230, 6, 12, 3600, 4200, 16));
        items.add(new Item("Ryzen 7 2700", AMD_CPU_LIST, 300, 8, 16, 3200, 4100, 16));
        items.add(new Item("Ryzen 7 2700X", AMD_CPU_LIST, 330, 8, 16, 3700, 4300, 16));
        items.add(new Item("Pentium G5400", INTEL_CPU_LIST, 70, 2, 4, 3700, 3700, 4));
        items.add(new Item("Core i3 8100", INTEL_CPU_LIST, 120, 4, 4, 3600, 3600, 6));
        items.add(new Item("Core i5 8400", INTEL_CPU_LIST, 190, 6, 6, 2800, 4000, 9));
        items.add(new Item("Core i5 9600K", INTEL_CPU_LIST, 260, 6, 6, 3700, 4600, 9));
        items.add(new Item("Core i7 9700K", INTEL_CPU_LIST, 380, 8, 8, 3600, 4900, 12));
        items.add(new Item("Core i9 9900K", INTEL_CPU_LIST, 500, 8, 16, 3600, 5000, 16));
        items.add(new Item("Radeon RX 550", AMD_GPU_LIST, 80, 10, 640, 1100, 1183, 2 * 1024));
        items.add(new Item("Radeon RX 560", AMD_GPU_LIST, 100, 16, 1024, 1175, 1275, 4 * 1024));
        items.add(new Item("Radeon RX 570", AMD_GPU_LIST, 170, 32, 2048, 1168, 1244, 4 * 1024));
        items.add(new Item("Radeon RX 580 4GB", AMD_GPU_LIST, 200, 36, 2304, 1257, 1340, 4 * 1024));
        items.add(new Item("Radeon RX 580 8GB", AMD_GPU_LIST, 230, 36, 2304, 1257, 1340, 8 * 1024));
        items.add(new Item("Radeon RX 590", AMD_GPU_LIST, 280, 36, 2304 , 1469, 1545, 8 * 1024));
        items.add(new Item("Radeon RX Vega 56", AMD_GPU_LIST, 400, 56, 3584, 1156, 1471, 8 * 1024));
        items.add(new Item("Radeon RX Vega 64", AMD_GPU_LIST, 500, 64, 4096, 1247, 1546, 8 * 1024));
        items.add(new Item("Radeon VII", AMD_GPU_LIST, 700, 60, 3840, 1400, 1750, 16 * 1024));
        items.add(new Item("GeForce GTX 1650", NVIDIA_GPU_LIST, 150, 14, 896, 1485, 1665, 4 * 1024));
        items.add(new Item("GeForce GTX 1660", NVIDIA_GPU_LIST, 220, 22, 1408, 1530, 1785, 6 * 1024));
        items.add(new Item("GeForce GTX 1660 Ti", NVIDIA_GPU_LIST, 280, 24, 1536, 1500, 1770, 6 * 1024));
        items.add(new Item("GeForce RTX 2060", NVIDIA_GPU_LIST, 350, 30, 1920, 1365, 1680, 6 * 1024));
        items.add(new Item("GeForce RTX 2070", NVIDIA_GPU_LIST, 500, 36, 2304, 1410, 1620, 8 * 1024));
        items.add(new Item("GeForce RTX 2080", NVIDIA_GPU_LIST, 700, 46, 2944, 1515, 1710, 8 * 1024));
        items.add(new Item("GeForce RTX 2080 Ti", NVIDIA_GPU_LIST, 1000, 68, 4352, 1350, 1545, 11 * 1024));
        for (Item item : items) {
            itemDAO.insert(item);
        }
        this.dbitems = itemDAO.getItems();
    }

    public List<Item> getList() {
        return dbitems;
    }

    public static DatabaseInstance getDatabaseInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new DatabaseInstance(ItemDatabase.getItemDatabase(context));
        }
        return INSTANCE;
    }
}
