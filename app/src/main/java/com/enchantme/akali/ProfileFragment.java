package com.enchantme.akali;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.enchantme.akali.viewmodel.EditProfileViewModel;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class ProfileFragment extends Fragment {

    //region Variables

    private OnFragmentInteractionListener mListener;
    private NavController navController;
    private EditProfileViewModel profile;

    //endregion

    //region Constructors

    public ProfileFragment() {
    }

    //endregion

    //region Android Lifecycle

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);
        profile = ViewModelProviders.of(getActivity()).get(EditProfileViewModel.class);
        ImageView profileImageView = view.findViewById(R.id.profile_image_view);
        FloatingActionButton editButton = view.findViewById(R.id.edit_profile_button);

        TextView nameTextView = view.findViewById(R.id.profile_name);
        nameTextView.setText(profile.getProfileName().getValue());

        TextView nicknameTextView = view.findViewById(R.id.profile_nickname);
        nicknameTextView.setText(profile.getNickname().getValue());

        TextView emailTextView = view.findViewById(R.id.profile_email);
        emailTextView.setText(profile.getEmail().getValue());

        TextView phoneTextView = view.findViewById(R.id.profile_phone);
        phoneTextView.setText(profile.getPhoneNumber().getValue());

        navController = Navigation.findNavController(getActivity(), R.id.main_content);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.editProfileFragment);
            }
        });
        if (profile.getImagePath() != null) {
            Bitmap btmp = BitmapFactory.decodeFile(profile.getImagePath(), null);
            Glide.with(this).load(btmp).into(profileImageView);
        } else {
            Glide.with(this).load(R.drawable.default_profile_pic).into(profileImageView);
        }
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
