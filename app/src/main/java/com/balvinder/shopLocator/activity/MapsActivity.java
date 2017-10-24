package com.balvinder.shopLocator.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.balvinder.shopLocator.util.ApiInterface;
import com.balvinder.shopLocator.util.MyApplication;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;
import com.balvinder.shopLocator.model.Route;
import com.balvinder.shopLocator.model.RoutesResponse;
import com.balvinder.shopLocator.R;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MapsActivity extends FragmentActivity implements
        OnMapReadyCallback{

    private GoogleMap mMap;
    private double currentLat;
    private double currentLong;
    private double destinationLat;
    private double destinationLong;
    SupportMapFragment mapFragment;
    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;

    List<Route> routes;

    @Inject
    Retrofit retrofit;

    private List<LatLng> routeNodes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ((MyApplication)getApplication()).getNetComponent().inject(this);
        Bundle extras = getIntent().getExtras();

        if(extras!=null){
            currentLat = extras.getDouble("currentLat");
            currentLong = extras.getDouble("currentLong");
            destinationLat = extras.getDouble("destinationLat");
            destinationLong = extras.getDouble("destinationLng");
        }
        routeNodes = new ArrayList<LatLng>();
        ApiInterface apiService =
                retrofit.create(ApiInterface.class);
        String API_KEY = getResources().getString(R.string.API_KEY);
        Call<RoutesResponse> call = apiService.getDirections(currentLat+","+currentLong,destinationLat+","+destinationLong,API_KEY);
        call.enqueue(new Callback<RoutesResponse>() {
            @Override
            public void onResponse(Call<RoutesResponse> call, Response<RoutesResponse> response) {

                routes = response.body().getRoutes();

                for(int i=0;i<routes.size();i++){
                    String polyline = routes.get(i).getOverviewPolyline().getPoints();
                    routeNodes.addAll(PolyUtil.decode(polyline));
                }

                mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                mapFragment.getMapAsync(MapsActivity.this);

            }

            @Override
            public void onFailure(Call<RoutesResponse> call, Throwable t) {

            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.


    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng current = new LatLng(currentLat, currentLong);
        mMap.addMarker(new MarkerOptions().position(current).title("Origin"));
        LatLng destination = new LatLng(destinationLat,destinationLong);
        mMap.addMarker(new MarkerOptions().position(destination).title("Destination"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current,10));
       // mMap.moveCamera(CameraUpdateFactory.newLatLng(destination));
        PolylineOptions po = new PolylineOptions();
        po.addAll(routeNodes);
        po.width(10).color(Color.BLUE);
        Polyline line = mMap.addPolyline(po);
        CameraPosition cameraPosition = CameraPosition.builder()
                .target(current)
                .zoom(15)
                .bearing(90)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition),
                2000, null);
    }


}
