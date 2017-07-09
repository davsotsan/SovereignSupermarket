package com.sovereign.supermarket.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sovereign.supermarket.R;

import org.jetbrains.annotations.NotNull;

/**
 * Created by JoseV on 7/7/17.
 */

public class HomeActivity extends AppCompatActivity{

    private static final String TAG = "HomeActivity";

    private Button buttonSearch;
    private LatLng coordAddressSearched;

    private Button buttonTest; //Temporal
    private Button buttonTestLogin; //Temporal

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        buttonSearch = (Button) findViewById(R.id.btnSearch);
        buttonTest = (Button) findViewById(R.id.btnTest);
        buttonTestLogin = (Button) findViewById(R.id.btnTestLogin);

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setHint(getString(R.string.searchHint));

        autocompleteFragment.setFilter(new AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS)
                .build());

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: " + place.getName());
                coordAddressSearched = place.getLatLng();
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ListSupermarketsActivity.class);
                Bundle b = new Bundle();

                b.putParcelable("coordAddressSearched", coordAddressSearched);
                intent.putExtras(b);

                if (coordAddressSearched == null) {
                    Toast.makeText(getBaseContext(), R.string.emptySearch, Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(intent);
                }
            }
        });

        buttonTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SupermarketActivity.class);
                startActivity(intent);

            }
        });

        buttonTestLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, EmailPasswordActivity.class);
                startActivity(intent);

            }
        });

    }
}


