package com.example.memorableplaces;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private ListView LV_Location;
    private HashMap<String, LatLng> LocationHashMap = new HashMap<>();
    private String selectedLocation;
    private Boolean isNew = true;
    private Intent newIntent,oldIntent;
    private ArrayList<String> keys;



    public void goToMap(View view){
        newIntent = new Intent(getApplicationContext(),MapsActivity.class);
        if (!isNew){
            newIntent.putExtra("Latitude",LocationHashMap.get(selectedLocation).latitude);
            newIntent.putExtra("Longitude",LocationHashMap.get(selectedLocation).longitude);

        }

        newIntent.putExtra("isNew",isNew);

        startActivity(newIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LocationHashMap.put("Velizy",new LatLng(48,2));



        LV_Location= findViewById(R.id.LV_Locations);
        update();

        keys = new ArrayList<>(LocationHashMap.keySet());



        final ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,keys);
        LV_Location.setAdapter(arrayAdapter);
        LV_Location.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // TODO Auto-generated method stub
                selectedLocation=((String )arrayAdapter.getItem(position));
                isNew = false;
                goToMap(LV_Location);

            }
        });
    }

    public void update(){
        oldIntent = getIntent();
        String Address= "";
        try {
            Address =  oldIntent.getStringExtra("Address");
            Toast.makeText(this, Address, Toast.LENGTH_SHORT).show();

        }catch (Exception e){
            Address = null;
        }
        if (Address!= null){
            LocationHashMap.put(oldIntent.getStringExtra("Address"),new LatLng(oldIntent.getDoubleExtra("Latitude",0),oldIntent.getDoubleExtra("Longitude",0)));
        }
        keys = new ArrayList<>(LocationHashMap.keySet());

        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,keys);
        LV_Location.setAdapter(arrayAdapter);

    }//UPDATE




}
