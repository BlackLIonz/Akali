package com.enchantme.akali;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.enchantme.akali.viewmodel.EditProfileViewModel;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;

public class ProfileFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private NavController navController;
    private EditProfileViewModel profile;

    public ProfileFragment() {
        // Required empty public constructor
    }

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
        Button editButton = view.findViewById(R.id.edit_profile_button);

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
        final ImagePicker imagePicker = ImagePicker.create(this)
                .single();
        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePicker.start();
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
    public void onActivityResult(int requestCode, final int resultCode, Intent data) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            Image image = ImagePicker.getFirstImageOrNull(data);
            profile.setImagePath(image.getPath());
            Bitmap btmp = BitmapFactory.decodeFile(image.getPath(), null);
            ImageView view = getView().findViewById(R.id.profile_image_view);
            Glide.with(this).load(btmp).into(view);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
