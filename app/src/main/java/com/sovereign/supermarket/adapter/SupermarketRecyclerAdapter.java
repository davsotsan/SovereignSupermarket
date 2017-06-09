package com.sovereign.supermarket.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sovereign.supermarket.R;
import com.sovereign.supermarket.holder.SupermarketHolder;
import com.sovereign.supermarket.model.Supermarket;

import java.util.ArrayList;


public class SupermarketRecyclerAdapter extends RecyclerView.Adapter<SupermarketHolder> {

    private ArrayList<Supermarket> supermarkets;

    public SupermarketRecyclerAdapter(ArrayList<Supermarket> supermarkets) {
        this.supermarkets = supermarkets;
    }


    @Override
    public SupermarketHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_supermarket, viewGroup, false);

        SupermarketHolder tvh = new SupermarketHolder(itemView);

        return tvh;
    }

    @Override
    public void onBindViewHolder(SupermarketHolder viewHolder, int pos) {
        Supermarket supermarket = supermarkets.get(pos);

        viewHolder.setName(supermarket.getName());
        viewHolder.setLocation("Lat: "+String.valueOf(supermarket.getLatitude()) + " Lon: "+String.valueOf(supermarket.getLongitude()));
    }

    @Override
    public int getItemCount() {
        return supermarkets.size();
    }

    public ArrayList<Supermarket> getItems() {
        return supermarkets;
    }

}