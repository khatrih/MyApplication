package com.example.myapplication.map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.databinding.ActivitySearchBinding;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;

import java.util.List;

public class SearchActivity extends AppCompatActivity implements PlacesAutoCompleteAdapter.ClickListener {

    private ActivitySearchBinding searchBinding;
    private Geocoder geocoder;
    public static final String SOURCE = "SOURCE";
    public static final String SOURCE_NAME = "SOURCE_NAME";
    public static final String DESTINATION = "DESTINATION";
    public static final String DESTINATION_NAME = "DESTINATION_NAME";
    private final Intent intent = new Intent();
    private LatLng sourceLatLang;
    private LatLng destinationLatLang;
    private String startLocation;
    private String endLocation;
    private PlacesAutoCompleteAdapter mAutoCompleteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchBinding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(searchBinding.getRoot());

        Places.initialize(this, "AIzaSyATkdQvY9xuNiXYoyTf007Lm7sLbb3ZyFM");

        searchBinding.backArrow.setOnClickListener(v -> onBackPressed());

        searchBinding.swapLocation.setOnClickListener(view -> {
            getStartLocation();
            getEndLocation();
            intent.putExtra(SOURCE_NAME, startLocation);
            intent.putExtra(SOURCE, sourceLatLang);
            intent.putExtra(DESTINATION_NAME, endLocation);
            intent.putExtra(DESTINATION, destinationLatLang);
            setResult(Activity.RESULT_OK, intent);
            finish();
        });

        searchBinding.startLocation.addTextChangedListener(filterTextWatcher);
        //searchBinding.endLocation.addTextChangedListener(filterTextWatcher);

        mAutoCompleteAdapter = new PlacesAutoCompleteAdapter(this);
        searchBinding.placesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAutoCompleteAdapter.setClickListener(this);
        searchBinding.placesRecyclerView.setAdapter(mAutoCompleteAdapter);
        mAutoCompleteAdapter.notifyDataSetChanged();
    }

    private void getStartLocation() {
        startLocation = searchBinding.startLocation.getText().toString();
        searchBinding.startLocation.setMaxLines(1);
        List<Address> addressList = null;
        if (startLocation != null || startLocation.equals("")) {
            geocoder = new Geocoder(SearchActivity.this);
            try {
                addressList = geocoder.getFromLocationName(startLocation, 2);
            } catch (Exception e) {
                e.printStackTrace();
            }
            assert addressList != null;
            Address startAddressLocation = addressList.get(0);
            sourceLatLang = new LatLng(startAddressLocation.getLatitude(), startAddressLocation.getLongitude());
        }
    }

    private void getEndLocation() {
        endLocation = searchBinding.endLocation.getText().toString();
        searchBinding.endLocation.setMaxLines(1);
        List<Address> addressList1 = null;
        if (endLocation != null || endLocation.equals("")) {
            geocoder = new Geocoder(SearchActivity.this);
            try {
                addressList1 = geocoder.getFromLocationName(endLocation, 2);
            } catch (Exception e) {
                e.printStackTrace();
            }
            assert addressList1 != null;
            Address endAddressLocation = addressList1.get(0);
            destinationLatLang = new LatLng(endAddressLocation.getLatitude(), endAddressLocation.getLongitude());
        }
    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED);
        super.onBackPressed();
    }

    private final TextWatcher filterTextWatcher = new TextWatcher() {
        public void afterTextChanged(Editable s) {
            if (!s.toString().equals("")) {
                mAutoCompleteAdapter.getFilter().filter(s.toString());
                if (searchBinding.placesRecyclerView.getVisibility() == View.GONE) {
                    searchBinding.placesRecyclerView.setVisibility(View.VISIBLE);
                }
            } else {
                if (searchBinding.placesRecyclerView.getVisibility() == View.VISIBLE) {
                    searchBinding.placesRecyclerView.setVisibility(View.GONE);
                }
            }
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
    };

    @Override
    public void click(Place place) {
        Toast.makeText(this, place.getAddress() + ", " + place.getLatLng().latitude + place.getLatLng().longitude, Toast.LENGTH_LONG).show();
    }

}