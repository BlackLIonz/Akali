package com.enchantme.akali;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.enchantme.akali.core.DBConstants;
import com.enchantme.akali.core.GlideApp;
import com.enchantme.akali.viewmodel.ProfileViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class ProfileFragment extends BaseFirebaseAuthFragment {

    //region Variables

    private OnFragmentInteractionListener mListener;

    private DatabaseReference db;
    private StorageReference st;

    private ProfileViewModel profileViewModel;

    //endregion

    //region Constructors

    public ProfileFragment() {
    }

    //endregion

    //region Android Lifecycle

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseDatabase.getInstance().getReference();
        st = FirebaseStorage.getInstance().getReference();
        profileViewModel = ViewModelProviders.of(getActivity()).get(ProfileViewModel.class);
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

        db = db.child(DBConstants.USERS_ROOT).child(currentUser.getUid());

        loadProfileEmailText(view, currentUser);
        loadProfileNameText(view);
        loadProfilePhoneText(view);
        loadImage(view, currentUser);

        FloatingActionButton editButton = view.findViewById(R.id.edit_profile_button);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.editProfileFragment);
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

    //region Private Methods

    private void loadProfileEmailText(final View view, FirebaseUser currentUser) {
        final TextView emailTextView = view.findViewById(R.id.profile_email);
        LiveData<String> profileEmail = profileViewModel.getProfileEmail();

        if (profileEmail.getValue() != null) {
            profileEmail.observe(this, new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    emailTextView.setText(s);
                }
            });
        } else {
            String currentEmail = currentUser.getEmail();
            profileViewModel.setProfileEmail(currentEmail);
            emailTextView.setText(currentEmail);
        }
    }

    private void loadProfileNameText(final View view) {
        final TextView profileNickNameTextView = view.findViewById(R.id.profile_nickname);
        LiveData<String> profileName = profileViewModel.getProfileName();

        if (profileName.getValue() != null) {
            profileName.observe(this, new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    profileNickNameTextView.setText(s);
                }
            });
        } else {
            db.child(DBConstants.USER_NICKNAME).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                        String value = dataSnapshot.getValue().toString();
                        if (TextUtils.isEmpty(value)) {
                            profileNickNameTextView.setText("-");
                            profileViewModel.setProfileName("-");
                        } else {
                            profileNickNameTextView.setText(value);
                            profileViewModel.setProfileName(value);
                        }
                    } else {
                        profileNickNameTextView.setText("-");
                        profileViewModel.setProfileName("-");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    private void loadProfilePhoneText(final View view) {
        final TextView profilePhoneTextView = view.findViewById(R.id.profile_phone);
        LiveData<String> profilePhoneNumber = profileViewModel.getProfilePhoneNumber();

        if (profilePhoneNumber.getValue() != null) {
            profilePhoneNumber.observe(this, new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    profilePhoneTextView.setText(s);
                }
            });
        } else {
            db.child(DBConstants.USER_PHONE_NUMBER).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                        String value = dataSnapshot.getValue().toString();
                        if (TextUtils.isEmpty(value)) {
                            profilePhoneTextView.setText("-");
                            profileViewModel.setProfilePhoneNumber("-");
                        } else {
                            profilePhoneTextView.setText(value);
                            profileViewModel.setProfilePhoneNumber(value);
                        }
                    } else {
                        profilePhoneTextView.setText("-");
                        profileViewModel.setProfilePhoneNumber("-");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }

    private void loadImage(final View view, FirebaseUser currentUser) {
        final ImageView profileImageView = view.findViewById(R.id.profile_image_view);
        LiveData<Drawable> profileImage = profileViewModel.getProfileImage();
        if (profileImage.getValue() != null) {
            GlideApp.with(view).load(profileImage.getValue()).into(profileImageView);
        } else {
            st = st.child(DBConstants.USERS_ROOT).child(currentUser.getUid()).child(DBConstants.USER_AVATAR);
            st.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    GlideApp.with(view).load(st).addListener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            Glide.with(view).load(R.drawable.default_profile_pic).into(profileImageView);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            profileViewModel.setProfileImage(resource);
                            return false;
                        }
                    }).into(profileImageView);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Glide.with(view).load(R.drawable.default_profile_pic).into(profileImageView);
                }
            });
        }
    }

    //endregion


    //region Interfaces

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    //endregion
}
