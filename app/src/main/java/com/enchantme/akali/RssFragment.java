package com.enchantme.akali;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.enchantme.akali.core.rss.Feed;
import com.enchantme.akali.core.rss.FeedAdapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RssFragment extends Fragment {

    //region Variables

    private OnFragmentInteractionListener mListener;

    //endregion

    //region Constructors

    public RssFragment() {
    }

    //endregion

    //region Android Lifecycle

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final RecyclerView recyclerView = getView().findViewById(R.id.feed_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getView().getContext()));

        String path = PreferenceManager.getDefaultSharedPreferences(getActivity()).getString(getResources().getString(R.string.rss_key), null);
        Log.d("Akali", "onActivityCreated: " + path);
        Log.d("Akali", "onActivityCreated: " + App.getRssApi().getItems(path).request().url().toString());
        App.getRssApi().getItems(path).enqueue(new Callback<Feed>() {
            @Override
            public void onResponse(Call<Feed> call, Response<Feed> response) {
                FeedAdapter adapter = new FeedAdapter(getView().getContext(), response.body());
                recyclerView.setAdapter(adapter);
                Toast.makeText(getActivity(), response.body().toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Feed> call, Throwable t) {
                Toast.makeText(getActivity(), "fail", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rss, container, false);
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
