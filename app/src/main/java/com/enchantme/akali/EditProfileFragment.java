package com.enchantme.akali;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.enchantme.akali.core.DBConstants;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileFragment extends Fragment {

    //region Variables

    private NavController navController;

    private FirebaseAuth auth;

    private MaterialButton saveProfileButton;

    private TextInputEditText nickName, phone;

    private DatabaseReference db;
    private StorageReference st;

    private Image pickedImage;

    //endregion

    //region Android Lifecycle

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        navController = Navigation.findNavController(getActivity(), R.id.main_content);
        db = FirebaseDatabase.getInstance().getReference();
        st = FirebaseStorage.getInstance().getReference();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.edit_profile_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (auth.getCurrentUser() == null) {
            navController.navigate(R.id.authEmailPasswordFragment);
        }

        FirebaseUser currentUser = auth.getCurrentUser();

        db = db.child(DBConstants.USERS_ROOT).child(currentUser.getUid());
        st = st.child(DBConstants.USERS_ROOT).child(currentUser.getUid()).child(DBConstants.USER_AVATAR);

        saveProfileButton = getView().findViewById(R.id.save_profile_button);

        nickName = getView().findViewById(R.id.profile_nickname_edit);
        db.child(DBConstants.USER_NICKNAME).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    String value = dataSnapshot.getValue().toString();
                    nickName.setText(value);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        phone = getView().findViewById(R.id.profile_phone_edit);
        db.child(DBConstants.USER_PHONE_NUMBER).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null ) {
                    String value = dataSnapshot.getValue().toString();
                    phone.setText(value);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        saveProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.child(DBConstants.USER_NICKNAME).setValue(nickName.getText().toString());
                db.child(DBConstants.USER_PHONE_NUMBER).setValue(phone.getText().toString());
                if (pickedImage != null) {
                try {
                    final InputStream stream = new FileInputStream(new File(pickedImage.getPath()));
                    Log.d("Akali", "onActivityResult: " + new File(pickedImage.getPath()).getName());
                    st.putStream(stream).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("Akali", "onFailure: Upload Failed" + e.getMessage());
                            try {
                                stream.close();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Log.d("Akali", "onSuccess: Upload Success");
                            try {
                                stream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (IOException e) {
                    Log.d("Akali", "onActivityResult: " + e.getMessage());
                }
            }
                navController.navigate(R.id.profileFragment);
            }
        });


        CircleImageView profileImageView = getView().findViewById(R.id.profile_image_edit);

        if (currentUser.getPhotoUrl() != null) {
            Bitmap btmp = BitmapFactory.decodeFile(currentUser.getPhotoUrl().toString(), null);
            Glide.with(this).load(btmp).into(profileImageView);
        } else {
            Glide.with(this).load(R.drawable.default_profile_pic).into(profileImageView);
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

}
