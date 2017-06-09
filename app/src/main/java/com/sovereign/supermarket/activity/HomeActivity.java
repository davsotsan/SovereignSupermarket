package com.sovereign.supermarket.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sovereign.supermarket.R;


public class HomeActivity extends AppCompatActivity {

    private Button buttonSearch;
    private EditText textSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        buttonSearch = (Button) findViewById(R.id.btnSearch);
        textSearch = (EditText) findViewById(R.id.txtAddressSearched);

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ListSupermarketsActivity.class);
                Bundle b = new Bundle();

                b.putString("addressSearched", textSearch.getText().toString());
                intent.putExtras(b);

                if (textSearch.getText().toString().isEmpty()){
                    Toast.makeText(getBaseContext(), R.string.emptySearch, Toast.LENGTH_SHORT).show();
                }else{
                    startActivity(intent);
                }
            }
        });
    }
}
