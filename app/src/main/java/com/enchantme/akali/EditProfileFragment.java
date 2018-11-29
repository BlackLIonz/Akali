package com.enchantme.akali;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.enchantme.akali.viewmodel.EditProfileViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

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
    }

    //endregion

}
