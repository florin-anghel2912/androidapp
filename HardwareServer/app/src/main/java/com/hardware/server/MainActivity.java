package com.hardware.server;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hardware.server.database.DatabaseInstance;
import com.hardware.server.database.Order;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DatabaseInstance dinst = null;
    private ServerThread thread = null;
    private Context context = this;
    private int orderCount = 0;
    private int orderTemp;

    public static BufferedReader getReader(Socket socket) throws IOException {
        return new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    private static PrintWriter getWriter(Socket socket) throws IOException {
        return new PrintWriter(socket.getOutputStream(), true);
    }

    private class ServerThread extends Thread {

        private boolean running;
        private ServerSocket socket = null;

        void startServer() {
            running = true;
            start();
        }

        void stopServer() {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        running = false;
                        if (socket != null) {
                            socket.close();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ((TextView) findViewById(R.id.server)).setText(R.string.server_stopped);
                            }
                        });
                    }
                    catch (IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ((TextView) findViewById(R.id.server)).setText(R.string.server_not_stopped);
                            }
                        });
                    }
                }
            });
            thread.start();
        }

        @Override
        public void run() {
            try {
                socket = new ServerSocket(12345);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ((TextView) findViewById(R.id.server)).setText(R.string.server_started);
                    }
                });
                while (running) {
                    Socket sock = socket.accept();
                    BufferedReader reader = getReader(sock);
                    final StringBuilder order = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null && !line.equals("\b")) {
                        order.append(line);
                        order.append("\n");
                    }
                    dinst.getOrderDAO().insert(new Order(orderCount + 1, order.toString()));
                    orderTemp = orderCount;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ((TextView) findViewById(R.id.number)).setText(Integer.toString(orderTemp + 1));
                        }
                    });
                    PrintWriter writer = getWriter(sock);
                    writer.println(orderCount + 1);
                    orderCount++;
                }
            }
            catch (IOException e) {
                if (socket == null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ((TextView) findViewById(R.id.server)).setText(R.string.server_not_started);
                        }
                    });
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((TextView) findViewById(R.id.number)).setText("0");
        dinst = DatabaseInstance.getDatabaseInstance(context);
        List<Order> orderList = dinst.getOrderDAO().getOrders();
        for (Order order : orderList) {
            dinst.getOrderDAO().delete(order);
        }
        final EditText et = findViewById(R.id.editText);
        Button start = findViewById(R.id.start);
        Button stop = findViewById(R.id.stop);
        Button find = findViewById(R.id.find);
        View.OnClickListener clkstart = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (thread == null) {
                    thread = new ServerThread();
                    thread.startServer();
                }
            }
        };
        View.OnClickListener clkstop = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (thread != null) {
                    thread.stopServer();
                    thread = null;
                }
            }
        };
        View.OnClickListener clkfind = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = Integer.parseInt(et.getText().toString());
                List<Order> orderList = dinst.getOrderDAO().getOrders();
                if (index <= 0 || index > orderList.size()) return;
                String text = orderList.get(index - 1).getText();
                ((TextView) findViewById(R.id.order)).setText(text);
            }
        };
        start.setOnClickListener(clkstart);
        stop.setOnClickListener(clkstop);
        find.setOnClickListener(clkfind);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (dinst != null) {
            List<Order> orderList = dinst.getOrderDAO().getOrders();
            for (Order order : orderList) {
                dinst.getOrderDAO().delete(order);
            }
        }
    }
}
