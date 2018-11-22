package com.enchantme.akali;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements ProfileFragment.OnFragmentInteractionListener, QuestsFragment.OnFragmentInteractionListener, ReaderFragment.OnFragmentInteractionListener {

    private BottomNavigationView bottomNavigationView;
    private NavController navController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar mainToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mainToolbar);

        navController = Navigation.findNavController(this, R.id.main_content);

        bottomNavigationView = findViewById(R.id.bottom_main_navigation);

        BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.profile_menu_item:
                        navController.navigate(R.id.profileFragment);
                        return true;
                    case R.id.reader_menu_item:
                        navController.navigate(R.id.readerFragment);
                        return true;
                    case R.id.quests_menu_item:
                        navController.navigate(R.id.questsFragment);
                        return true;
                }
                return false;
            }
        };

        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.appbar_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.about_menu_item:
                navController.navigate(R.id.aboutActivity);
                break;
        }
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
