package com.sovereign.supermarket.activity;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sovereign.supermarket.R;
import com.sovereign.supermarket.adapter.SupermarketRecyclerAdapter;
import com.sovereign.supermarket.model.Supermarket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListSupermarketsActivity extends AppCompatActivity {
    private static final String TAGLOG = "firebase-db";

    private DatabaseReference dbLocations;
    private SupermarketRecyclerAdapter supermarketRecyclerAdapter;
    private ArrayList<Supermarket> supermarketsNearby;
    private ValueEventListener eventListenerSupermarket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_supermarkets);

        // Se inicializan las variables y se obtienen los extras (ubicación introducida) de la actividad anterior
        dbLocations = FirebaseDatabase.getInstance().getReference().child("supermarkets");
        supermarketsNearby = new ArrayList<>();
        supermarketRecyclerAdapter = new SupermarketRecyclerAdapter(supermarketsNearby);
        Bundle bundle = this.getIntent().getExtras();
        String addressSearched = bundle.getString("addressSearched");

        // Se obtienen las coordenadas de la ubicación introducida
        double latitudeAddressAux = 0.;
        double longitudeAddressAux = 0.;
        if (Geocoder.isPresent()) {
            try {
                Geocoder gc = new Geocoder(this);
                List<Address> addresses = gc.getFromLocationName(addressSearched, 1);
                Address address = addresses.get(0);
                System.out.println(address.getAddressLine(0));
                System.out.println(address.getAddressLine(1));
                latitudeAddressAux = address.getLatitude();
                longitudeAddressAux = address.getLongitude();
            } catch (IOException e) {
                Log.e(TAGLOG, "No se ha obtenido ninguna dirección", e);
            }
        }
        final double latitudeAddress = latitudeAddressAux;
        final double longitudeAddress = longitudeAddressAux;


        // Se inicializa la RecyclerView
        RecyclerView recView = (RecyclerView) findViewById(R.id.listSupermarket);
        recView.setHasFixedSize(true);
        recView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        // Se añade el adaptador a la Recycler view
        recView.setAdapter(supermarketRecyclerAdapter);

        // Se crea el listener para la referencia a la base de datos
        eventListenerSupermarket = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Se obtienen las coordenadas de cada ubicación y se mide la distancia con la ubicación introducida
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    String name = d.child("info").child("name").getValue().toString();
                    String dbLat = d.child("info").child("location").child("latitude").getValue().toString();
                    String dbLon = d.child("info").child("location").child("longitude").getValue().toString();
                    float[] results = new float[1];

                    Location.distanceBetween(latitudeAddress, longitudeAddress, Double.valueOf(dbLat), Double.valueOf(dbLon), results);
                    float distance = results[0] / (float) 1E3;
                    // Si la distancia es menor a 1km se añade a la lista de supermercados cercanos y se le notifica al adaptador
                    if (distance < 1.) {
                        Supermarket s = new Supermarket(name, dbLat, dbLon);
                        supermarketsNearby.add(s);
                        supermarketRecyclerAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAGLOG, "Error!", databaseError.toException());
            }
        };

        // Se añade el listener a la referencia a la base de datos
        dbLocations.addListenerForSingleValueEvent(eventListenerSupermarket);

    }
}
