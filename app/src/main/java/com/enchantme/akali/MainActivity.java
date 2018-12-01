package com.enchantme.akali;

import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class MainActivity extends AppCompatActivity implements
        ProfileFragment.OnFragmentInteractionListener,
        QuestsFragment.OnFragmentInteractionListener,
        ReaderFragment.OnFragmentInteractionListener,
        AboutFragment.OnFragmentInteractionListener,
        AuthEmailPasswordFragment.OnFragmentInteractionListener,
        AuthCreateFragment.OnFragmentInteractionListener,
        SettingsFragment.OnFragmentInteractionListener {

    //region Variables

    private MenuItem logoutMenuItem;
    private MenuItem settingsMenuItem;

    private NavController navController;

    private FirebaseAuth auth;

    private BottomNavigationView bottomNavigationView;

    //endregion

    //region Android Lifecycle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        Toolbar mainToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mainToolbar);

        navController = Navigation.findNavController(this, R.id.main_content);

        bottomNavigationView = findViewById(R.id.bottom_main_navigation);

        if (auth.getCurrentUser() == null) {
            bottomNavigationView.setVisibility(View.INVISIBLE);
        }

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
    protected void onStop() {
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.appbar_menu, menu);
        logoutMenuItem = menu.findItem(R.id.logout_menu_item);
        settingsMenuItem = menu.findItem(R.id.settings_menu_item);
        if (auth.getCurrentUser() == null) {
            logoutMenuItem.setVisible(false);
            settingsMenuItem.setVisible(false);
        }
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.about_menu_item:
                navController.navigate(R.id.aboutFragment);
                break;
            case R.id.logout_menu_item:
                auth.signOut();
                hideAuth();
                navController.navigate(R.id.authEmailPasswordFragment);
                break;
            case R.id.settings_menu_item:
                navController.navigate(R.id.settingsFragment);
                break;
        }
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private void hideAuth() {
        bottomNavigationView.setVisibility(View.INVISIBLE);
        logoutMenuItem.setVisible(false);
        settingsMenuItem.setVisible(false);
    }

    @Override
    public void showBottomNavigationBar() {
        bottomNavigationView.setVisibility(View.VISIBLE);
        logoutMenuItem.setVisible(true);
        settingsMenuItem.setVisible(true);
    }

    //endregion

}
