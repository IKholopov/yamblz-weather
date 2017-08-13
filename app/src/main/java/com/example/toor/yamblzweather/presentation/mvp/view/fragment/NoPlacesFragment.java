package com.example.toor.yamblzweather.presentation.mvp.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.toor.yamblzweather.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoPlacesFragment extends Fragment {

    @BindView(R.id.bNavigateToAddPlaces) Button navigateButton;

    public NoPlacesFragment() {
    }

    public static NoPlacesFragment newInstance() {
        return new NoPlacesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_no_places, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstance) {
        ButterKnife.bind(this, view);
        navigateButton.setOnClickListener(button -> openPlacesList());
    }

    private void openPlacesList() {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        String fragmentName = PlacesListFragment.class.getSimpleName();
        Fragment fragment = manager.findFragmentByTag(fragmentName);
        if(fragment == null) {
            fragment = PlacesListFragment.newInstance();
            manager.beginTransaction().replace(R.id.flContent, fragment, fragmentName)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .addToBackStack(null)
                    .commit();
        }
    }
}
