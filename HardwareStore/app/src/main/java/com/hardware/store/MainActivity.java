package com.hardware.store;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.android.material.navigation.NavigationView;
import com.hardware.store.fragment.CartFragment;
import com.hardware.store.fragment.HomeFragment;
import com.hardware.store.fragment.ListFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ActionBarDrawerToggle toggle = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.drawer_layout);
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setDisplayHomeAsUpEnabled(true);
        }
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.activity, new HomeFragment());
        ft.commit();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        toggle = new ActionBarDrawerToggle(this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_main);
        final CheckBox amd_cpu, intel_cpu, amd_gpu, nvidia_gpu;
        amd_cpu = headerView.findViewById(R.id.amd_cpu);
        intel_cpu = headerView.findViewById(R.id.intel_cpu);
        amd_gpu = headerView.findViewById(R.id.amd_gpu);
        nvidia_gpu = headerView.findViewById(R.id.nvidia_gpu);
        final EditText low = headerView.findViewById(R.id.price_low);
        final EditText high = headerView.findViewById(R.id.price_high);
        Button button = headerView.findViewById(R.id.search);
        View.OnClickListener clk = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean is_amd_cpu, is_intel_cpu, is_amd_gpu, is_nvidia_gpu;
                is_amd_cpu = amd_cpu.isChecked();
                is_intel_cpu = intel_cpu.isChecked();
                is_amd_gpu = amd_gpu.isChecked();
                is_nvidia_gpu = nvidia_gpu.isChecked();
                int plow, phigh;
                try {
                    plow = Integer.valueOf(low.getText().toString());
                    phigh = Integer.valueOf(high.getText().toString());
                }
                catch (NumberFormatException e) {
                    return;
                }
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.activity, new ListFragment(is_amd_cpu, is_intel_cpu, is_amd_gpu, is_nvidia_gpu, plow, phigh));
                ft.addToBackStack(null);
                ft.commit();
                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        };
        button.setOnClickListener(clk);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.right_side_menu, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        if (menuItem.getItemId() == R.id.shopping_cart) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.activity, CartFragment.getCartFragment());
            ft.addToBackStack(null);
            ft.commit();
            return true;
        }
        return toggle.onOptionsItemSelected(menuItem) | super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
        }
        else {
            super.onBackPressed();
        }
    }
}
