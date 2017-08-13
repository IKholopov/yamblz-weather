package com.example.toor.yamblzweather.presentation.mvp.view.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.toor.yamblzweather.R;
import com.example.toor.yamblzweather.presentation.mvp.models.places.PlaceModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class PlacesListAdapter extends RecyclerView.Adapter<PlacesListAdapter.PlaceViewHolder>
        implements ItemTouchAdapter{

    private List<PlaceModel> places;

    private final PublishSubject<PlaceModel> deletionSubject = PublishSubject.create();

    public PlacesListAdapter(List<PlaceModel> placeModels) {
        this.places = placeModels;
    }

    @Override
    @NonNull
    public PlaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_place_entry,
                parent, false);
        PlaceViewHolder viewHolder = new PlaceViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PlaceViewHolder holder, int position) {
        holder.placeName.setText(places.get(position).getName());
        holder.deleteButton.setOnClickListener(button -> {
            final int adapterPosition = holder.getAdapterPosition();
            if(adapterPosition < 0) {
                return;
            }
            deletionSubject.onNext(places.get(adapterPosition));
            places.remove(holder.getAdapterPosition());
            this.notifyItemRemoved(adapterPosition);
        });
    }

    @Override
    public int getItemCount() {
        if(places != null) {
            return places.size();
        }
        return 0;
    }

    @Override
    public boolean onMoved(int fromPosition, int toPosition) {
        if(fromPosition == toPosition) {
            return false;
        }
        PlaceModel model = places.get(fromPosition);
        places.remove(fromPosition);
        places.add(toPosition > fromPosition ? toPosition - 1 : toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    public void updatePlaces(List<PlaceModel> places) {
        this.places = places;
        notifyDataSetChanged();
    }

    public Observable<PlaceModel> getDeletionEvent() {
        return deletionSubject;
    }

    public static class PlaceViewHolder extends RecyclerView.ViewHolder
            implements ItemTouchViewHolder{
        @BindView(R.id.placeNameId) TextView placeName;
        @BindView(R.id.deletePlace) Button deleteButton;

        public PlaceViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onClear() {
            itemView.setBackgroundColor(Color.TRANSPARENT);
        }
    }
}
