package com.enchantme.akali;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class BaseFirebaseAuthFragment extends Fragment {

    //region Variables

    protected FirebaseAuth auth;
    protected NavController navController;

    //endregion


    //region Android Lifecycle

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        navController = Navigation.findNavController(getActivity(), R.id.main_content);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (auth.getCurrentUser() == null) {
            navController.navigate(R.id.authEmailPasswordFragment);
        }
    }

    //endregion
}
