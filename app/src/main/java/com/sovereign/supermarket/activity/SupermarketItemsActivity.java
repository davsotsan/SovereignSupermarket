package com.sovereign.supermarket.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sovereign.supermarket.DividerItemDecoration;
import com.sovereign.supermarket.R;
import com.sovereign.supermarket.holder.ItemHolder;
import com.sovereign.supermarket.model.Item;

public class SupermarketItemsActivity extends AppCompatActivity {

    private DatabaseReference dbItems;
    private String keySupermarket;
    private String keyCategory;
    private FirebaseRecyclerAdapter mAdapterItem;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supermarket_items);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("CATEGORY X");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Bundle bundle = this.getIntent().getExtras();
        keySupermarket = bundle.getString("keySupermarket");
        keyCategory = bundle.getString("keyCategory");
        dbItems = FirebaseDatabase.getInstance().getReference().child("supermarkets").child("supermarket" + keySupermarket).child("items");


        RecyclerView recycler = (RecyclerView) findViewById(R.id.listItems);
        recycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        recycler.setLayoutManager(new LinearLayoutManager(this));

        mAdapterItem =
                new FirebaseRecyclerAdapter<Item, ItemHolder>(
                        Item.class, R.layout.list_item, ItemHolder.class, dbItems.orderByChild("category").equalTo(keyCategory)) {

                    @Override
                    public void populateViewHolder(ItemHolder itemHolder, Item item, int position) {

                        itemHolder.setName(item.getName());
                        itemHolder.setDescription(item.getDescription());
                        itemHolder.setPrice(String.valueOf(item.getPrice()));

                    }
                };
        recycler.setAdapter(mAdapterItem);

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mAdapterItem.cleanup();
    }

    public boolean onOptionsItemSelected(MenuItem item){
        super.onBackPressed();
        return true;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}