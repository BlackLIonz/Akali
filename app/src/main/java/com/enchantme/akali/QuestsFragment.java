package com.enchantme.akali;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class QuestsFragment extends BaseFirebaseAuthFragment {

    //region Variables

    private OnFragmentInteractionListener mListener;

    //endregion

    //region Constructors

    public QuestsFragment() {
    }

    //endregion

    //region Android Lifecycle

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_quests, container, false);
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
        void onFragmentInteraction(Uri uri);
    }

    //endregion
}
