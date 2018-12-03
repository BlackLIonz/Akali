package com.enchantme.akali;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;


public class AuthCreateFragment extends Fragment {

    //region Variables

    private TextView emailTextView, passwordTextView;
    private MaterialButton createAccountButton;

    private FirebaseAuth auth;

    private NavController navController;


    private OnFragmentInteractionListener mListener;

    //endregion

    //region Constructors
    public AuthCreateFragment() {
    }
    //endregion


    //region Android Lifecycle

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_auth_create, container, false);
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        navController = Navigation.findNavController(getActivity(), R.id.main_content);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        emailTextView = getView().findViewById(R.id.create_account_email);
        passwordTextView = getView().findViewById(R.id.create_account_password);
        createAccountButton = getView().findViewById(R.id.create_account_button);

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount(emailTextView.getText().toString(), passwordTextView.getText().toString());
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    //endregion

    //region Private Methods

    private boolean validateForm() {
        boolean valid = true;

        String email = emailTextView.getText().toString();
        if (TextUtils.isEmpty(email)) {
            emailTextView.setError("Required.");
            valid = false;
        } else {
            emailTextView.setError(null);
        }

        String password = passwordTextView.getText().toString();
        if (TextUtils.isEmpty(password)) {
            passwordTextView.setError("Required.");
            valid = false;
        } else {
            passwordTextView.setError(null);
        }

        return valid;
    }


    private void createAccount(String email, String password) {
        if (!validateForm()) {
            return;
        }

   //     showProgressDialog();

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            navController.navigate(R.id.authEmailPasswordFragment);
                          //  updateUI(user);
                        } else {
                            Log.w("Akali", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getActivity(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // [START_EXCLUDE]
                       // hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
    }


    //endregion

    //region Interfaces

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    //endregion
}
