package com.enchantme.akali;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.enchantme.akali.core.DBConstants;
import com.enchantme.akali.core.GlideApp;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class ProfileFragment extends Fragment {

    //region Variables

    private FirebaseAuth auth;

    private OnFragmentInteractionListener mListener;
    private NavController navController;

    private DatabaseReference db;
    private StorageReference st;

    //endregion

    //region Constructors

    public ProfileFragment() {
    }

    //endregion

    //region Android Lifecycle

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference();
        st = FirebaseStorage.getInstance().getReference();
    }

    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser == null) {
            navController.navigate(R.id.authEmailPasswordFragment);
        }

        db = db.child(DBConstants.USERS_ROOT).child(currentUser.getUid());

        final ImageView profileImageView = view.findViewById(R.id.profile_image_view);
        FloatingActionButton editButton = view.findViewById(R.id.edit_profile_button);

        TextView emailTextView = view.findViewById(R.id.profile_email);
        emailTextView.setText(currentUser.getEmail());

        final TextView nicknameTextView = view.findViewById(R.id.profile_nickname);
        db.child(DBConstants.USER_NICKNAME).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    String value = dataSnapshot.getValue().toString();
                    if (TextUtils.isEmpty(value)) {
                        nicknameTextView.setText("-");
                    } else {
                        nicknameTextView.setText(value);
                    }
                } else {
                    nicknameTextView.setText("-");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final TextView phoneTextView = view.findViewById(R.id.profile_phone);
        db.child(DBConstants.USER_PHONE_NUMBER).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    String value = dataSnapshot.getValue().toString();
                    if (TextUtils.isEmpty(value)) {
                        phoneTextView.setText("-");
                    } else {
                        phoneTextView.setText(value);
                    }
                } else {
                    phoneTextView.setText("-");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        navController = Navigation.findNavController(getActivity(), R.id.main_content);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.editProfileFragment);
            }
        });
        st = st.child(DBConstants.USERS_ROOT).child(currentUser.getUid()).child(DBConstants.USER_AVATAR);
        st.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                GlideApp.with(getView()).load(st).into(profileImageView);
           //     GlideApp.with(getView()).load(st).into(profileImageView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Glide.with(getView()).load(R.drawable.default_profile_pic).into(profileImageView);
            }
        });
        return view;
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


    //region Interfaces

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    //endregion
}
