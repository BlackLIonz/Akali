package com.enchantme.akali;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.enchantme.akali.dto.ProfileDTO;
import com.enchantme.akali.viewmodel.EditProfileViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class MainActivity extends AppCompatActivity implements
        ProfileFragment.OnFragmentInteractionListener,
        QuestsFragment.OnFragmentInteractionListener,
        ReaderFragment.OnFragmentInteractionListener {

    //region Variables

    private NavController navController;
    private EditProfileViewModel mViewModel;

    //endregion

    //region Android Lifecycle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar mainToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mainToolbar);
        mViewModel = ViewModelProviders.of(this).get(EditProfileViewModel.class);

        loadProfile();

        navController = Navigation.findNavController(this, R.id.main_content);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_main_navigation);

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
        saveProfile();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.appbar_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.about_menu_item:
                navController.navigate(R.id.aboutActivity);
                break;
        }
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    //endregion

    //region Private Methods

    private void saveProfile() {
        try {
            FileOutputStream fos = this.openFileOutput("profile", Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            ProfileDTO profileDTO = new ProfileDTO(mViewModel.getProfileName().getValue(), mViewModel.getNickname().getValue(), mViewModel.getPhoneNumber().getValue(), mViewModel.getEmail().getValue());
            if (mViewModel.getImagePath() != null) {
                profileDTO.setImagePath(mViewModel.getImagePath());
            }
            os.writeObject(profileDTO);
            os.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("Akali", "onDestroy: error " + e.getMessage());
        }
        Log.d("Akali", "onDestroy: save complete");
        Log.d("Akali", "onStop: " + mViewModel.getNickname().getValue());
    }

    private void loadProfile() {
        try {
            FileInputStream fis = this.openFileInput("profile");
            ObjectInputStream is = new ObjectInputStream(fis);
            ProfileDTO profileDTO = (ProfileDTO) is.readObject();
            mViewModel.setProfileName(profileDTO.getProfileName());
            mViewModel.setNickName(profileDTO.getProfileNickName());
            mViewModel.setEmail(profileDTO.getProfileEmail());
            mViewModel.setPhoneNumber(profileDTO.getProfilePhone());
            if (profileDTO.getImagePath() != null) {
                mViewModel.setImagePath(profileDTO.getImagePath());
            }
            is.close();
            fis.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //endregion
}
