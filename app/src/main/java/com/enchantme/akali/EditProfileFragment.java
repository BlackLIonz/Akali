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
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.enchantme.akali.core.Utils;
import com.enchantme.akali.viewmodel.EditProfileViewModel;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileFragment extends Fragment {

    //region Variables

    private EditProfileViewModel mViewModel;

    //endregion

    //region Android Lifecycle

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.edit_profile_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(EditProfileViewModel.class);
        EditText profileName = getView().findViewById(R.id.profile_name_edit);
        profileName.setText(mViewModel.getProfileName().getValue());
        profileName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mViewModel.setProfileName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        EditText nickName = getView().findViewById(R.id.profile_nickname_edit);
        nickName.setText(mViewModel.getNickname().getValue());
        nickName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mViewModel.setNickName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        EditText phone = getView().findViewById(R.id.profile_phone_edit);
        phone.setText(mViewModel.getPhoneNumber().getValue());
        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mViewModel.setPhoneNumber(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        EditText email = getView().findViewById(R.id.profile_email_edit);
        email.setText(mViewModel.getEmail().getValue());
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mViewModel.setEmail(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        CircleImageView profileImageView = getView().findViewById(R.id.profile_image_edit);

        if (mViewModel.getImagePath() != null) {
            Bitmap btmp = BitmapFactory.decodeFile(mViewModel.getImagePath(), null);
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
            Image image = ImagePicker.getFirstImageOrNull(data);
            mViewModel.setImagePath(image.getPath());
            Bitmap btmp = BitmapFactory.decodeFile(image.getPath(), null);
            CircleImageView view = getView().findViewById(R.id.profile_image_edit);
            Glide.with(this).load(btmp).into(view);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //endregion

}
