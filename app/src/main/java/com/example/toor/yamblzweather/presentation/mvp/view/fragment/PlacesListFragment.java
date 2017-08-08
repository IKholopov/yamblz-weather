package com.example.toor.yamblzweather.presentation.mvp.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.toor.yamblzweather.R;
import com.example.toor.yamblzweather.presentation.di.App;
import com.example.toor.yamblzweather.presentation.mvp.models.places.PlaceModel;
import com.example.toor.yamblzweather.presentation.mvp.presenter.PlacesListPresenter;
import com.example.toor.yamblzweather.presentation.mvp.view.PlacesListView;
import com.example.toor.yamblzweather.presentation.mvp.view.activity.OnBackBehaviour;
import com.example.toor.yamblzweather.presentation.mvp.view.activity.drawer.DrawerLocker;
import com.example.toor.yamblzweather.presentation.mvp.view.adapter.CityNameAdapter;
import com.example.toor.yamblzweather.presentation.mvp.view.adapter.PlacesListAdapter;
import com.example.toor.yamblzweather.presentation.mvp.view.fragment.common.BaseFragment;
import com.example.toor.yamblzweather.presentation.mvp.view.recyclerCallback.DragNDropCallback;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlacesListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlacesListFragment extends BaseFragment implements PlacesListView{

    private static final String STATE_KEY = "state_key";

    private Unbinder unbinder;

    private enum State {
        LIST, ADD
    }

    private State state;
    private CityNameAdapter suggestionAdapter;
    private PlacesListAdapter placesAdapter;
    private ArrayList<Disposable> disposables;
    private ItemTouchHelper touchHelper;

    @Inject
    PlacesListPresenter presenter;

    @BindView(R.id.etSearchPlace) EditText searchPlace;
    @BindView(R.id.bAddPlace) Button addPlaceButton;
    @BindView(R.id.rvPlaces) RecyclerView placesList;
    @BindView(R.id.rvSuggestions) RecyclerView suggestionsList;



    public PlacesListFragment() {
    }

    public static PlacesListFragment newInstance() {
        PlacesListFragment fragment = new PlacesListFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_places_list, container, false);
        disposables = new ArrayList<>();
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, view);
        presenter.onAttach(this);
        switchFragmentState(State.LIST);
        suggestionAdapter = new CityNameAdapter();
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        suggestionsList.setLayoutManager(llm);
        suggestionsList.setAdapter(suggestionAdapter);
        addPlaceButton.setOnClickListener(view1 -> switchFragmentState(State.ADD));
        presenter.subscribeOnPlaceNameChanges(RxTextView.textChanges(searchPlace));
        disposables.add(suggestionAdapter.getSelectedPlace().subscribe(placeModel -> {
            presenter.fetchAndSaveCityDetails(placeModel).subscribe(place -> {
                placesAdapter.addPlace(place);
            });
            switchFragmentState(State.LIST);
        }));
        disposables.add(presenter.getPlaces().toList().subscribe(this::setUpPlaceList));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        for(Disposable d: disposables) {
            d.dispose();
        }
    }

    @Override
    public void onSaveInstanceState(final  Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(STATE_KEY, state);
    }

    @Override
    public void onActivityCreated(Bundle savedState) {
        super.onActivityCreated(savedState);
        if(savedState != null) {
            switchFragmentState((State) savedState.get(STATE_KEY));
        }
    }

    @Override
    public void updatePlacesSuggestionList(List<PlaceModel> suggestions) {
        suggestionAdapter.updatePlaces(suggestions);
    }

    @Override
    protected void setTitle() {
        getActivity().setTitle(R.string.places);
    }

    @Override
    protected void setDrawableEnabled() {
        ((DrawerLocker) getActivity()).setDrawerEnable(false);
    }

    @Override
    protected void inject() {
        App.getInstance().plusActivityComponent().inject(this);
    }

    private void switchFragmentState(State newState){
        //TODO: Insert check if views still exist
        if(state == newState) {
            return;
        }
        state = newState;
        if(state == State.LIST) {
            searchPlace.setVisibility(View.INVISIBLE);
            suggestionsList.setVisibility(View.INVISIBLE);
            addPlaceButton.setVisibility(View.VISIBLE);
            placesList.setVisibility(View.VISIBLE);
            FragmentActivity activity = getActivity();
            if(activity != null && activity instanceof OnBackBehaviour) {
                ((OnBackBehaviour)activity).setOnBackBehaviour(null);
            }
        } else {
            searchPlace.setVisibility(View.VISIBLE);
            suggestionsList.setVisibility(View.VISIBLE);
            addPlaceButton.setVisibility(View.INVISIBLE);
            placesList.setVisibility(View.INVISIBLE);
            FragmentActivity activity = getActivity();
            if(activity != null && activity instanceof OnBackBehaviour) {
                ((OnBackBehaviour)activity).setOnBackBehaviour(() -> switchFragmentState(State.LIST));
            }
        }
    }

    private void setUpPlaceList(List<PlaceModel> places) {
        placesAdapter = new PlacesListAdapter(places);
        LinearLayoutManager placesLayoutManager = new LinearLayoutManager(getContext());
        placesLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        placesList.setLayoutManager(placesLayoutManager);
        disposables.add(
                placesAdapter.getDeletionEvent()
                        .subscribe(placeModel -> presenter.deletePlace(placeModel)
                                .subscribe(deleted -> {})));
        placesList.setAdapter(placesAdapter);
        ItemTouchHelper.Callback dragCallback = new DragNDropCallback(placesAdapter);
        touchHelper = new ItemTouchHelper(dragCallback);
        touchHelper.attachToRecyclerView(placesList);
    }
}

