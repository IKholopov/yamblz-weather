package com.example.toor.yamblzweather.presentation.mvp.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.toor.yamblzweather.R;
import com.example.toor.yamblzweather.presentation.mvp.models.places.PlaceModel;
import com.example.toor.yamblzweather.presentation.mvp.view.activity.drawer.ViewNavigationController;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by igor on 8/11/17.
 */

public class DrawerListAdapter extends RecyclerView.Adapter<DrawerListAdapter.ViewHolder> {

    private List<PlaceModel> places;
    private ViewNavigationController controller;

    public DrawerListAdapter(@NonNull List<PlaceModel> places,
                             @NonNull ViewNavigationController controller) {
        this.places = places;
        this.controller = controller;
    }

    @Override
    public DrawerListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_place_drawer_entry,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DrawerListAdapter.ViewHolder holder, int position) {
        holder.placeName.setText(places.get(position).getName());
        holder.itemView.setOnClickListener(view -> {
            if(position >= 0) {
                controller.switchToView(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    public void setPlaces(@NonNull List<PlaceModel> places) {
        this.places = places;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvPlaceName) TextView placeName;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
