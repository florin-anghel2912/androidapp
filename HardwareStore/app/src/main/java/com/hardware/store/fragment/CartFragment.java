package com.hardware.store.fragment;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hardware.store.R;
import com.hardware.store.database.Item;
import com.hardware.store.recycler.CartRecycler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class CartFragment extends Fragment {
    private static CartFragment INSTANCE;
    private static TextView totalPrice;
    private EditText shippingInformation;
    private AlertDialog.Builder builder;
    private volatile boolean canShow = false;

    private ArrayList<Integer> qList = new ArrayList<>();
    private ArrayList<Item> toBuyList = new ArrayList<>();

    private CartFragment() {

    }

    private CartFragment(int q, Item toBuy) {
        qList.add(q);
        toBuyList.add(toBuy);
    }

    private void addToCart(int q, Item toBuy) {
        qList.add(q);
        toBuyList.add(toBuy);
    }

    public static CartFragment getCartFragment() {
        if (INSTANCE == null) {
            INSTANCE = new CartFragment();
        }
        return INSTANCE;
    }

    static CartFragment getCartFragment(int q, Item toBuy) {
        if (INSTANCE == null) {
            INSTANCE = new CartFragment(q, toBuy);
        }
        else INSTANCE.addToCart(q, toBuy);
        return INSTANCE;
    }

    private static PrintWriter getWriter(Socket socket) throws IOException {
        return new PrintWriter(socket.getOutputStream(), true);
    }

    public static BufferedReader getReader(Socket socket) throws IOException {
        return new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    private class CommunicationAsyncTask extends AsyncTask<AlertDialog.Builder, Void, Void> {

        String order;

        @Override
        protected void onPreExecute() {
            String[] itemList = new String[qList.size()];
            for (int i = 0; i < qList.size(); i++) {
                itemList[i] = "x" + qList.get(i) + " " + toBuyList.get(i).getName();
            }
            int total = 0;
            for (int i = 0; i < qList.size(); i++) {
                total += qList.get(i) * toBuyList.get(i).getPrice();
            }
            order = TextUtils.join("\n", itemList) + "\nTotal price: $" + Integer.toString(total)
                        + "\n" + shippingInformation.getText().toString() + "\n" + "\b";

        }

        @Override
        protected Void doInBackground(AlertDialog.Builder... builder) {
            try {
                Socket socket = new Socket("127.0.0.1", 12345);
                PrintWriter printWriter = getWriter(socket);
                printWriter.println(order);
                builder[0].setMessage("Order sent!");
                BufferedReader reader = getReader(socket);
                builder[0].setTitle("Order #" + reader.readLine());
                canShow = true;
                socket.close();
            }
            catch (IOException e) {
                builder[0].setMessage("Cannot connect!");
                builder[0].setTitle("Server error!");
                canShow = true;
            }
            return null;
        }
    }

    public static void updateTotal(int total) {
        totalPrice.setText("Total: $" + total);
    }

    public ArrayList<Integer> getQList() {
        return this.qList;
    }

    public ArrayList<Item> getToBuyList() {
        return this.toBuyList;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_viewQ);
        recyclerView.setHasFixedSize(false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.Adapter adapter = new CartRecycler(qList, toBuyList);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL));
        int total = 0;
        for (int i = 0; i < qList.size(); i++) {
            total += qList.get(i) * toBuyList.get(i).getPrice();
        }
        totalPrice = view.findViewById(R.id.textViewS);
        totalPrice.setText("Total: $" + Integer.valueOf(total).toString());
        Button buy = view.findViewById(R.id.buttonS);
        shippingInformation = view.findViewById(R.id.submitO);
        builder = new AlertDialog.Builder(view.getContext());
        View.OnClickListener clk = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommunicationAsyncTask communicationAsyncTask = new CommunicationAsyncTask();
                communicationAsyncTask.execute(builder);
                while (true) {
                    if (canShow) {
                        builder.create().show();
                        canShow = false;
                        break;
                    }
                }
            }
        };
        buy.setOnClickListener(clk);
        return view;
    }
}
