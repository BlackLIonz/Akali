package com.enchantme.akali;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
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


public class AuthEmailPasswordFragment extends Fragment {

    //region Variables

    private OnFragmentInteractionListener mListener;

    private FirebaseAuth auth;

    private TextView emailTextView, passwordTextView, createAccountTextView;
    private MaterialButton signInButton;

    private NavController navController;

    //endregion

    //region Constructors

    public AuthEmailPasswordFragment() {
    }

    //endregion

    //region Android Lifecycle

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_auth_email_password, container, false);
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        navController = Navigation.findNavController(getActivity(), R.id.main_content);
        if (auth.getCurrentUser() != null) {
            navController.navigate(R.id.profileFragment);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        emailTextView = getView().findViewById(R.id.auth_email);
        passwordTextView = getView().findViewById(R.id.auth_password);
        signInButton = getView().findViewById(R.id.auth_sign_in);
        createAccountTextView = getView().findViewById(R.id.auth_create_account);
        createSpannableCreateAccountString();
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(emailTextView.getText().toString(), passwordTextView.getText().toString());
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

    private void createSpannableCreateAccountString() {
        SpannableString ss = new SpannableString(getResources().getString(R.string.auth_create_account));
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                navController.navigate(R.id.authCreateFragment);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(true);
            }
        };

        int startSpan = getResources().getInteger(R.integer.start_create_account_span);
        int endSpan = getResources().getInteger(R.integer.end_create_account_span);

        ss.setSpan(clickableSpan,startSpan, endSpan, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        createAccountTextView.setText(ss);
        createAccountTextView.setMovementMethod(LinkMovementMethod.getInstance());
        createAccountTextView.setHighlightColor(Color.TRANSPARENT);

    }

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

    private void signIn(String email, String password) {
        if (!validateForm()) {
            return;
        }

        //showProgressDialog();

        // [START sign_in_with_email]
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            mListener.showBottomNavigationBar();
                            navController.navigate(R.id.profileFragment);
                        } else {
                            Toast.makeText(getActivity(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        if (!task.isSuccessful()) {
                        //    mStatusTextView.setText(R.string.auth_failed);
                        }
               //         hideProgressDialog();
                    }
                });
    }

    //endregion


    //region Interfaces

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
        void showBottomNavigationBar();
    }

    //endregion
}
