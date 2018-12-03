package com.enchantme.akali;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.enchantme.akali.core.DBConstants;
import com.enchantme.akali.core.GlideApp;
import com.enchantme.akali.viewmodel.ProfileViewModel;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileFragment extends BaseFirebaseAuthFragment {

    //region Variables

    private MaterialButton saveProfileButton;

    private TextInputEditText nickNameTextView, profilePhoneTextView;

    private DatabaseReference db;
    private StorageReference st;

    private Image pickedImage;

    private ProfileViewModel profileViewModel;

    private boolean isSaved;
    private boolean isChanged;

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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.edit_profile_fragment, container, false);

        FirebaseUser currentUser = auth.getCurrentUser();

        db = db.child(DBConstants.USERS_ROOT).child(currentUser.getUid());
        st = st.child(DBConstants.USERS_ROOT).child(currentUser.getUid()).child(DBConstants.USER_AVATAR);

        saveProfileButton = view.findViewById(R.id.save_profile_button);

        loadProfileNickNameText(view);
        loadProfilePhoneNumberText(view);

        saveProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.child(DBConstants.USER_NICKNAME).setValue(nickNameTextView.getText().toString());
                db.child(DBConstants.USER_PHONE_NUMBER).setValue(profilePhoneTextView.getText().toString());
                if (pickedImage != null) {
                    try {
                        final InputStream stream = new FileInputStream(new File(pickedImage.getPath()));
                        st.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                st.putStream(stream).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        try {
                                            stream.close();
                                        } catch (IOException e1) {
                                            e1.printStackTrace();
                                        }
                                    }
                                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        try {
                                            stream.close();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        navController.navigate(R.id.profileFragment);
                                    }
                                });
                            }
                        });
                    } catch (IOException e) {
                        Log.d("Akali", "onActivityResult: " + e.getMessage());
                    }
                } else {
                    navController.navigate(R.id.profileFragment);
                }
            }
        });

        loadProfileImage(view);

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, final int resultCode, Intent data) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            pickedImage = ImagePicker.getFirstImageOrNull(data);
       //     mViewModel.setImagePath(image.getPath());
            Bitmap btmp = BitmapFactory.decodeFile(pickedImage.getPath(), null);
            CircleImageView view = getView().findViewById(R.id.profile_image_edit);
            Glide.with(this).load(btmp).into(view);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //endregion

    //region Private Methods

    private void loadProfilePhoneNumberText(final View view) {
        profilePhoneTextView = view.findViewById(R.id.profile_phone_edit);
        final LiveData<String> profilePhone = profileViewModel.getProfilePhoneNumber();

        if (profilePhone.getValue() != null) {
            profilePhone.observe(this, new Observer<String>() {
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

    private void loadProfileNickNameText(final View view) {
        nickNameTextView = view.findViewById(R.id.profile_nickname_edit);
        LiveData<String> profileName = profileViewModel.getProfileName();

        if (profileName.getValue() != null) {
            profileName.observe(this, new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    nickNameTextView.setText(s);
                }
            });
        } else {
            db.child(DBConstants.USER_NICKNAME).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                        String value = dataSnapshot.getValue().toString();
                        if (TextUtils.isEmpty(value)) {
                            nickNameTextView.setText("-");
                            profileViewModel.setProfileName("-");
                        } else {
                            nickNameTextView.setText(value);
                            profileViewModel.setProfileName(value);
                        }
                    } else {
                        nickNameTextView.setText("-");
                        profileViewModel.setProfileName("-");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    private void loadProfileImage(final View view) {
        final CircleImageView profileImageView = view.findViewById(R.id.profile_image_edit);
        LiveData<Drawable> profileImage = profileViewModel.getProfileImage();
        if (profileImage.getValue() != null) {
            GlideApp.with(view).load(profileImage.getValue()).into(profileImageView);
        } else {
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
                    //     GlideApp.with(getView()).load(st).into(profileImageView);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Glide.with(view).load(R.drawable.default_profile_pic).into(profileImageView);
                }
            });
        }

        final ImagePicker imagePicker = ImagePicker.create(this)
                .single();
        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePicker.start();
            }
        });
    }

    //endregion


}
