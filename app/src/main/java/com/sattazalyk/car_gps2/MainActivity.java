package com.sattazalyk.car_gps2;

import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private Button btn_frag_map, btb_frag_feed, btb_wv;
    private Toolbar tb_main;
    private NavigationView nv_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_frag_map = findViewById(R.id.btn_frag_map);
        btb_frag_feed = findViewById(R.id.btn_news);
        btb_frag_feed.setClickable(false);
        btb_wv = findViewById(R.id.btn_wv);
        tb_main = findViewById(R.id.tb_main);
        nv_main = findViewById(R.id.nv_main);
        nv_main.setItemIconTintList(null);

        /*NavController navController = Navigation.findNavController(this,R.id.fragment);
        NavigationUI.setupWithNavController(nv_main,navController);*/

        feed_fragment feedFragment = new feed_fragment();
        FragmentTransaction StartTransaction = getSupportFragmentManager().beginTransaction();
        StartTransaction.replace(R.id.fragmentContainer, feedFragment);
        StartTransaction.commit();

        tb_main.setTitle("Лента");
        btn_frag_map.setClickable(true);
        btb_wv.setClickable(true);

        btn_frag_map.setOnClickListener(view -> {
            MapFragment mapFragment = new MapFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer, mapFragment);
            fragmentTransaction.commit();

            tb_main.setTitle("Карта");
            btn_frag_map.setClickable(false);
            btb_frag_feed.setClickable(true);
            btb_wv.setClickable(true);
        });

        btb_frag_feed.setOnClickListener(view -> {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer, feedFragment);
            fragmentTransaction.commit();

            tb_main.setTitle("Лента");
            btn_frag_map.setClickable(true);
            btb_frag_feed.setClickable(false);
            btb_wv.setClickable(true);
        });

        btb_wv.setOnClickListener(view -> {
            wialon wialon = new wialon();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer, wialon);
            fragmentTransaction.commit();

            tb_main.setTitle("WIALON");
            btn_frag_map.setClickable(true);
            btb_frag_feed.setClickable(true);
            btb_wv.setClickable(false);

        });
    }
}