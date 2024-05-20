package com.example.diplom;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.diplom.Events.DayFragment;
import com.example.diplom.Forum.ChatRoomFragment;
import com.example.diplom.Headline.HeadlinesFragment;
import com.example.diplom.Naviagtion.Navigator;
import com.example.diplom.QR.ScanFragment;
import com.example.diplom.Zametki.Zametki;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FirstScreen extends AppCompatActivity {
    private Navigator navigator;
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_screen);
        navigator = new Navigator(getSupportFragmentManager(), R.id.main_container);
        navigateToZametki();

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_ejednev:
                            selectedFragment = new DayFragment();
                            navigateToDayFragment();
                            break;
                        case R.id.nav_zametki:
                            selectedFragment = new Zametki();
                            navigateToZametki();
                            break;
                        case R.id.nav_novosti:
                            selectedFragment = new HeadlinesFragment();
                            navigateToNewsFragment();
                            break;
                        case R.id.nav_spisok:
                            selectedFragment = new ScanFragment();
                            navigateToScanFragment();
                            break;
                        case R.id.nav_forum:
                            selectedFragment = new ChatRoomFragment();
                            navigateToForumFragment();
                            break;
                    }
                    if (selectedFragment != null) {
                        navigator.navigateTo(selectedFragment, true);
                        return true;
                    }
                    return false;
                }
            };

    public void navigateToZametki() {
        navigator.navigateTo(new Zametki(), true);
    }

    public void navigateToScanFragment() {
        navigator.navigateTo(new ScanFragment(), true);
    }

    public void navigateToNewsFragment() {
        navigator.navigateTo(new HeadlinesFragment(), true);
    }

    public void navigateToForumFragment() {
        navigator.navigateTo(new ChatRoomFragment(), true);
    }

    public void navigateToDayFragment() {navigator.navigateTo(new DayFragment(), true);}
}