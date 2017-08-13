package com.example.toor.yamblzweather.presentation.mvp.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.toor.yamblzweather.R;
import com.example.toor.yamblzweather.presentation.mvp.models.places.PlaceModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by igor on 7/25/17.
 */

public class CityNameAdapter extends android.support.v7.widget.RecyclerView.Adapter<CityNameAdapter.CityViewHolder>{

    private List<PlaceModel> data;

    private final PublishSubject<PlaceModel> dataSubject = PublishSubject.create();

    public CityNameAdapter() {
        this.data = null;
    }

    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_city_name,
                parent, false);
        CityViewHolder viewHolder = new CityViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CityViewHolder holder, int position) {
        holder.cityName.setText(data.get(position).getName());
        holder.itemView.setOnClickListener(view -> dataSubject.onNext(data.get(position)));
    }

    @Override
    public int getItemCount() {
        if(data == null) {
            return 0;
        }
        return data.size();
    }

    static class CityViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cityName)
        TextView cityName;

        CityViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public Observable<PlaceModel> getSelectedPlace() {
        return dataSubject;
    }

    public void updatePlaces(List<PlaceModel> data) {
        this.data = data;
        notifyDataSetChanged();
    }
}
