package com.sovereign.supermarket.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sovereign.supermarket.R;


public class SupermarketHomeFragment extends Fragment {

    private static final String TAGLOG = "firebase-db";

    private DatabaseReference dbInfo;
    private ValueEventListener eventListenerInfo;
    private String keySupermarket;
    private TextView lblName;
    private TextView lblAddress;


    public static SupermarketHomeFragment newInstance(String keySupermarket) {
        SupermarketHomeFragment fragment = new SupermarketHomeFragment();
        Bundle b = new Bundle();
        b.putString("keySupermarket", keySupermarket);
        fragment.setArguments(b);

        return fragment;
    }


    public SupermarketHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = this.getArguments();
        keySupermarket = b.getString("keySupermarket");
        dbInfo = FirebaseDatabase.getInstance().getReference().child("supermarkets").child("supermarket" + keySupermarket).child("info");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View viewFragment = inflater.inflate(R.layout.fragment_supermarket_info, container, false);

        lblName = (TextView) viewFragment.findViewById(R.id.lblName);
        lblAddress = (TextView) viewFragment.findViewById(R.id.lblAddress);

        eventListenerInfo = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lblName.setText(dataSnapshot.child("name").getValue().toString());
                lblAddress.setText(dataSnapshot.child("location").child("address").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAGLOG, "Error!", databaseError.toException());
            }
        };
        dbInfo.addListenerForSingleValueEvent(eventListenerInfo);

        return viewFragment;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}